package acad277.pan.hal.petconnect;

    import android.app.Activity;
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.os.Bundle;
    import android.preference.PreferenceManager;
    import android.support.annotation.Nullable;
    import android.support.v7.app.ActionBar;
    import android.support.v7.app.AppCompatActivity;
    import android.support.v7.widget.Toolbar;
    import android.view.KeyEvent;
    import android.view.Menu;
    import android.view.MenuItem;
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

    private Toolbar toolbar;


//    private FirebaseAuth mAuth;
        private TextView username;
        private String zipcode;
////        private DatabaseReference mDatabase;
//        DatabaseReference dbRefCode;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ALMOST always the same
        //just change the first parameter of the next line
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return super.onCreateOptionsMenu(menu);
    }


        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_profile);

//            mAuth = FirebaseAuth.getInstance(); // important Call
            signout = (Button)findViewById(R.id.signout);
            username = (TextView) findViewById(R.id.tvName);
            feed=(Button) findViewById(R.id.explore);
            from=findViewById(R.id.etLocation);
            numpet=findViewById(R.id.etNumpet);
            initial=findViewById(R.id.initial);
            userzip=findViewById(R.id.creationtime);
            useremail=findViewById(R.id.tvEmail);

            Intent i = getIntent();
            zipcode= i.getStringExtra(SignupActivity.EXTRA_ZIP);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//            SharedPreferences.Editor editor = preferences.edit();
//            editor.putString("Zipcode",zipcode);
//            editor.apply();

//            preferences = PreferenceManager.getDefaultSharedPreferences(this);
//            String city = preferences.getString("City", "");
//            String npet = preferences.getString("Npet", "");
//            if(!city.equalsIgnoreCase(""))
//            {
//                city=city+from.getText().toString();
//            }
//            if(!npet.equalsIgnoreCase(""))
//            {
//                npet=npet+numpet.getText().toString();
//            }

//            from.setText(city);
//            numpet.setText(npet);



//            final FirebaseDatabase database = FirebaseDatabase.getInstance();


//Again check if the user is Already Logged in or Not
//            if(mAuth.getCurrentUser() == null)
//            {
////User NOT logged In
//                finish();
//                startActivity(new Intent(getApplicationContext(),SignupActivity.class));
//            }

//read selected zipcode
//            String ref=username.getText().toString();
//            dbRefCode = database.getReference("Wu Angela");
//            if (dbRefCode != null) { // this is an existing note
//                dbRefCode.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        //this is where you add code for when you successfully read from the database
//                        Userinfo u= dataSnapshot.getValue(Userinfo.class);
//
//                        userzip.setText(u.getZipcode());
//                        from.setText(u.getCity());
//                        numpet.setText(u.getNumpet());
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
//            }

//Fetch the Display name of current User
//            FirebaseUser user = mAuth.getCurrentUser();
//            String name=user.getDisplayName();
//            char c = name.charAt(0);
//            preferences = PreferenceManager.getDefaultSharedPreferences(this);
//            String zip = preferences.getString("Zipcode", "");
//            if(!zip.equalsIgnoreCase(""))
//            {
//                zip=zip+zipcode;
//            }
//
//
//            if (user != null) {
//                initial.setText(Character.toString(c));
//                username.setText(user.getDisplayName());
//                useremail.setText(user.getEmail());
//                userzip.setText("ZIPCODE: "+zip);
////                mDatabase.child("zipcode").child(user.getUid()).setValue("Zipcode: "+zipcode);
//
//
//            }

            from.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {


                    String city = from.getText().toString();

                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SigninActivity.this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("City",city);
                    editor.apply();

                    return false;
                }
            });

            numpet.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                    String pet = numpet.getText().toString();

                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SigninActivity.this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("Npet",pet);
                    editor.apply();

                    return false;
                }
            });

            signout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    mAuth.signOut();
                    finish();
                }
            });
            feed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    startActivity(new Intent(getApplicationContext(), FeedActivity.class));
                    finish();
                }
            });

            // TODO: Find Toolbar from View and set as ActionBar.
            toolbar = findViewById(R.id.my_toolbar);
            setSupportActionBar(toolbar); //tells android "He, here's the toolbar"
            ActionBar actionBar = getSupportActionBar(); //for compatibility, get the action bar again?


        }


}
