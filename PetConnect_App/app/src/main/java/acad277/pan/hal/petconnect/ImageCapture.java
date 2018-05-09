package acad277.pan.hal.petconnect;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Date;

public class ImageCapture extends AppCompatActivity {

    public static final String EXTRA_URL = "itp341.firebase.EXTRA_URL";
    public static final String NOTES = "Notes";
    public static final String NOTE_COUNT = "Note Count";

    private Button button_take_img;
    private ImageView imgview_upload;

    private static final int CAMERA_REQUEST_CODE = 1;

    private ListView list;

    private StorageReference mStorage;

    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_capture);

        list = findViewById(R.id.listNotes);


        mStorage = FirebaseStorage.getInstance().getReference();

        button_take_img = findViewById(R.id.button_take_image);
        imgview_upload = findViewById(R.id.img_photo_one);

        mProgress = new ProgressDialog(this);

        button_take_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(intent, CAMERA_REQUEST_CODE);

            }
        });

    }


//NEW CODE


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
//set the progress dialog
            mProgress.setMessage("Loading...");
            mProgress.show();

//get the camera image
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] dataBAOS = baos.toByteArray();

//set the image into imageview
            imgview_upload.setImageBitmap(bitmap);

            /*************** UPLOADS THE PIC TO FIREBASE***************/
            //Firebase storage folder where you want to put the images
            StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://projectmilestoneone.appspot.com");

//name of the image file (add time to have different files to avoid rewrite on the same file)

            StorageReference imagesRef = storageRef.child("filename" + new Date().getTime());

//upload image

            UploadTask uploadTask = imagesRef.putBytes(dataBAOS);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(getApplicationContext(), "Sending failed", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.

//handle success


                    mProgress.dismiss();
                }
            });
        }
    }
}