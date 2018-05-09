package acad277.pan.hal.petconnect;

    import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
    import android.widget.EditText;
    import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.storage.StorageMetadata;
    import com.google.firebase.storage.StorageReference;

    import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


public class SigninActivity extends AppCompatActivity {
        Button signout;
        Button feed;
        EditText from;
        EditText numpet;
        TextView initial;
        TextView userzip;
        TextView useremail;

        private FirebaseAuth mAuth;
        TextView username;
        String zipcode;


        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_profile);

            mAuth = FirebaseAuth.getInstance(); // important Call
            signout = (Button)findViewById(R.id.signout);
            username = (TextView) findViewById(R.id.tvName);
            feed=(Button) findViewById(R.id.explore);
            from=findViewById(R.id.etLocation);
            numpet=findViewById(R.id.etNumpet);
            initial=findViewById(R.id.initial);
            userzip=findViewById(R.id.creationtime);
            useremail=findViewById(R.id.tvEmail);

            Intent i = getIntent();
            zipcode=i.getStringExtra(SignupActivity.EXTRA_ZIP);


//Again check if the user is Already Logged in or Not
            if(mAuth.getCurrentUser() == null)
            {
//User NOT logged In
                finish();
                startActivity(new Intent(getApplicationContext(),SignupActivity.class));
            }

//Fetch the Display name of current User
            FirebaseUser user = mAuth.getCurrentUser();
            String name=user.getDisplayName();
            char c = name.charAt(0);

            if (user != null) {
                initial.setText(Character.toString(c));
                username.setText(user.getDisplayName());
                useremail.setText(user.getEmail());
                userzip.setText("Zipcode: "+zipcode);

            }



            signout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAuth.signOut();
                    finish();
                    startActivity(new Intent(getApplicationContext(), SignupActivity.class));
                }
            });
            feed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    startActivity(new Intent(getApplicationContext(), FeedActivity.class));
                    finish();
                }
            });


        }


}
