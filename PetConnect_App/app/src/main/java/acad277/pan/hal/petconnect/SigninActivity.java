package acad277.pan.hal.petconnect;

    import android.content.Intent;
    import android.os.Bundle;
    import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
    import android.view.KeyEvent;
    import android.view.View;
import android.widget.Button;
    import android.widget.EditText;
    import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.ValueEventListener;

    import acad277.pan.hal.petconnect.model.Userinfo;


public class SigninActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
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
    private DatabaseReference mDatabase;
    DatabaseReference dbRefCode;


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

            final FirebaseDatabase database = FirebaseDatabase.getInstance();


//Again check if the user is Already Logged in or Not
            if(mAuth.getCurrentUser() == null)
            {
//User NOT logged In
                finish();
                startActivity(new Intent(getApplicationContext(),SignupActivity.class));
            }

//read selected zipcode
//            String ref=username.getText().toString();
            dbRefCode = database.getReference("Wu Angela");
            if (dbRefCode != null) { // this is an existing note
                dbRefCode.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //this is where you add code for when you successfully read from the database
                        Userinfo u= dataSnapshot.getValue(Userinfo.class);

                        userzip.setText(u.getZipcode());
                        from.setText(u.getCity());
                        numpet.setText(u.getNumpet());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
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
//                mDatabase.child("zipcode").child(user.getUid()).setValue("Zipcode: "+zipcode);


            }

            from.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                    String input=from.getText().toString();

//                    dbNewLocation =dbParent.push();
//                    //store message into database
//                    dbNewLocation.setValue(input);
//
//
//                    editText.setText("");

                    Userinfo u = new Userinfo(); //object we will save into Firebase

                    String zip = userzip.getText().toString();
                    String city = from.getText().toString();
                    String pet = numpet.getText().toString();

                    u.setZipcode(zip);
                    u.setCity(city);
                    u.setNumpet(pet);

                    if(dbRefCode == null){ // new note
                        DatabaseReference dbNewNote = dbRefCode.push(); // getting a child NODE from main NOTES branch
                        dbNewNote.setValue(u);
                    } else{ //existing note
                        dbRefCode.setValue(u);
                    }

                    return false;
                }
            });



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
