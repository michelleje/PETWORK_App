package acad277.pan.hal.petconnect;

        import android.content.Intent;

        import android.content.SharedPreferences;
        import android.os.Parcelable;
        import android.preference.PreferenceManager;
        import android.support.annotation.NonNull;

        import android.support.v7.app.AppCompatActivity;

        import android.os.Bundle;

        import android.util.Log;

        import android.view.KeyEvent;
        import android.view.View;

        import android.widget.Button;

        import android.widget.EditText;

        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.android.gms.tasks.OnCompleteListener;

        import com.google.android.gms.tasks.Task;

        import com.google.firebase.auth.AuthResult;

        import com.google.firebase.auth.FirebaseAuth;

        import com.google.firebase.auth.FirebaseUser;

        import com.google.firebase.auth.UserProfileChangeRequest;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
        public static final String EXTRA_ZIP ="EXTRA_ZIP";
        public static final String LOGGED_IN = "LOGGED_IN";
        public static final String USER_EMAIL = "USER_EMAIL";
        public static final String USER_PASSWORD = "USER_PASSWORD";
        public static final String USER_ZIPCODE = "USER_ZIPCODE";
        public static final String USER_NAME = "USER_NAME";



    private boolean login = false;
    private FirebaseAuth mAuth;


    private EditText email,password,name,zipcode;

    private Button signin, signup;
    DatabaseReference dbRefNoteToEdit;
    DatabaseReference dbParent;
    DatabaseReference dbNewLocation;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance(); // important Call
        FirebaseDatabase database = FirebaseDatabase.getInstance();

//        dbRefNoteToEdit = database.get;

        signin = (Button)findViewById(R.id.signin);

        signup = (Button)findViewById(R.id.signup);

        email = (EditText)findViewById(R.id.etEmail);

        password = (EditText)findViewById(R.id.etPassword);

        name = (EditText)findViewById(R.id.etName);
        zipcode=findViewById(R.id.etzipcode);


        zipcode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                String input=zipcode.getText().toString();

                dbNewLocation =dbParent.push();
                //store message into database
                dbNewLocation.setValue(input);

                return false;
            }
        });

        //Check if User is Already LoggedIn

        if(mAuth.getCurrentUser() != null) {

            //User NOT logged In

            finish();

            startActivity(new Intent(getApplicationContext(),SigninActivity.class));

        }



        signin.setOnClickListener(new View.OnClickListener()

        {

            @Override

            public void onClick (View v){

                String getemail = email.getText().toString().trim();

                String getepassword = password.getText().toString().trim();
//                String zip = zipcode.getText().toString();

                callsignin(getemail, getepassword);

//                Intent i = new Intent();
//                i.putExtra(USER_ZIPCODE, zip);
//                i.putExtra(LOGGED_IN, true);
//                Log.d("TESTING", "Sign up Successful");
//                setResult(RESULT_OK);
//                finish();


            }

        });



        signup.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                String getemail = email.getText().toString().trim();
                String getepassword = password.getText().toString().trim();



                callsignup(getemail,getepassword);

//                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//                mDatabase.child("users").child().child("username").setValue(name);

//                if(dbRefNoteToEdit == null){ // new note
//                    DatabaseReference dbNewNote = dbRefNotes.push(); // getting a child NODE from main NOTES branch
//                    dbNewNote.setValue(n);
//                } else{ //existing note
//                    dbRefNoteToEdit.setValue(n);
//                }


            }

        });



    }



    //Create Account

    private void callsignup(String email,String password) {

        mAuth.createUserWithEmailAndPassword(email, password)

                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override

                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Log.d("TESTING", "Sign up Successful" + task.isSuccessful());



                        // If sign in fails, display a message to the user. If sign in succeeds

                        // the auth state listener will be notified and logic to handle the

                        // signed in user can be handled in the listener.

                        if (!task.isSuccessful()) {

                            Toast.makeText(SignupActivity.this, "Signed up Failed", Toast.LENGTH_SHORT).show();

                        }

                        else

                        {

                            userProfile();

                            Toast.makeText(SignupActivity.this, "Created Account", Toast.LENGTH_SHORT).show();

                            Log.d("TESTING", "Created Account");

                        }

                    }

                });

    }



    //Set UserDisplay Name

    private void userProfile()

    {

        FirebaseUser user = mAuth.getCurrentUser();

        if(user!= null)

        {

            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()

                    .setDisplayName(name.getText().toString().trim())

                    //.setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))  // here you can set image link also.

                    .build();



            user.updateProfile(profileUpdates)

                    .addOnCompleteListener(new OnCompleteListener<Void>() {

                        @Override

                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {

                                Log.d("TESTING", "User profile updated.");

                            }

                        }

                    });

        }

    }





    //Now start Sign In Process

    //SignIn Process

    private void callsignin(String email,String password) {



        mAuth.signInWithEmailAndPassword(email, password)

                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override

                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Log.d("TESTING", "sign In Successful:" + task.isSuccessful());



                        // If sign in fails, display a message to the user. If sign in succeeds

                        // the auth state listener will be notified and logic to handle the

                        // signed in user can be handled in the listener.

                        if (!task.isSuccessful()) {

                            Log.w("TESTING", "signInWithEmail:failed", task.getException());

                            Toast.makeText(SignupActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                        }

                        else {

                            Intent i = new Intent(SignupActivity.this,SignupActivity.class);
                            i.putExtra(EXTRA_ZIP, zipcode.getText().toString());
                            i.putExtra(LOGGED_IN, true);

//                            Log.d("TESTING", "Sign up Successful");
                            finish();

                            startActivity(i);

                        }

                    }

                });



    }





}






//package acad277.pan.hal.petconnect;
//
//import android.app.Activity;
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.nfc.Tag;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;


//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.auth.UserProfileChangeRequest;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
///**
// * Created by wuangela on 5/6/18.
// */
//
//public class SignupActivity extends AppCompatActivity{
//
//    public static final String EXTRA_LOGIN = "EXTRA_LOGIN";
//    public static final String EXTRA_UNAME = "EXTRA_UNAME";
//    public static final String EXTRA_ZIPCODE= "EXTRA_ZIPCODE";
//    public static final String TAG = "EmailPassword";
//    DatabaseReference dbSignup;
//    DatabaseReference dbNewLocation;
//    String email;
//    boolean loggedin;
//    private TextView mStatusTextView;
//    private TextView mDetailTextView;
//
//    private EditText username;
//    private EditText useremail;
//    private EditText zipcode;
//    private EditText password;
//    private Button getstart;
//    private Button login;
//    private FirebaseAuth mAuth;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_signup);
//
//        FirebaseDatabase firebase = FirebaseDatabase.getInstance();
//        dbSignup = firebase.getReference("Petwork Signup");
//
//        mStatusTextView = findViewById(R.id.status);
//        mDetailTextView = findViewById(R.id.detail);
//
//        username = (EditText) findViewById(R.id.editText_name);
//        useremail = (EditText) findViewById(R.id.editText_email);
//        zipcode = (EditText) findViewById(R.id.editText_zipcode);
//        password = (EditText) findViewById(R.id.editText_password);
//        getstart = (Button) findViewById(R.id.start_button);
//        login = (Button) findViewById(R.id.login_button);
//
//        mAuth = FirebaseAuth.getInstance();
//
//        if(mAuth.getCurrentUser() != null)
//        {
////User NOT logged In
//            finish();
//            startActivity(new Intent(getApplicationContext(),SigninActivity.class));
//        }
//
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String getemail = useremail.getText().toString().trim();
//                String getepassword = password.getText().toString().trim();
//                callsignin(getemail, getepassword);
//            }
//        });
//
//        getstart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "clicked", Toast.LENGTH_SHORT).show();
//
////                String getemail = useremail.getText().toString().trim();
////                String getepassword = password.getText().toString().trim();
////                callsignup(getemail,getepassword);
//
//            }
//        });
//
////        getstart.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                createAccount(useremail.getText().toString(), password.getText().toString());
////                loggedin=true;
////                Intent intent = new Intent();
////                intent.putExtra(EXTRA_LOGIN,loggedin);
////                intent.putExtra(EXTRA_UNAME,username.getText().toString());
////                intent.putExtra(EXTRA_ZIPCODE,zipcode.getText().toString());
////                setResult(Activity.RESULT_OK, intent);
////                finish();
////            }
////
////        });
////
////        login.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                signIn(useremail.getText().toString(), password.getText().toString());
////                loggedin=true;
////                Intent intent = new Intent();
////                intent.putExtra(EXTRA_LOGIN,loggedin);
////                setResult(Activity.RESULT_OK, intent);
////                intent.putExtra(EXTRA_UNAME,username.getText().toString());
////                intent.putExtra(EXTRA_ZIPCODE,zipcode.getText().toString());
////                finish();
////            }
////        });
//    }
//    //Create Account
//    private void callsignup(String email,String password) {
//
//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Log.d("TESTING", "Sign up Successful" + task.isSuccessful());
//
//// If sign in fails, display a message to the user. If sign in succeeds
//// the auth state listener will be notified and logic to handle the
//// signed in user can be handled in the listener.
//                        if (!task.isSuccessful()) {
//                            Toast.makeText(getApplicationContext(), "Signed up Failed", Toast.LENGTH_SHORT).show();
//                        }
//                        else
//                        {
//                            userProfile();
//                            Toast.makeText(getApplicationContext(), "Created Account", Toast.LENGTH_SHORT).show();
//                            Log.d("TESTING", "Created Account");
//                        }
//                    }
//                });
//    }
//
//    //Set UserDisplay Name
//    private void userProfile()
//    {
//        FirebaseUser user = mAuth.getCurrentUser();
//        if(user!= null)
//        {
//            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                    .setDisplayName(username.getText().toString().trim())
////.setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg")) // here you can set image link also.
//                    .build();
//
//            user.updateProfile(profileUpdates)
//                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()) {
//                                Log.d("TESTING", "User profile updated.");
//                            }
//                        }
//                    });
//        }
//    }
//
//    //SignIn Process
//    private void callsignin(String email,String password) {
//
//        mAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Log.d("TESTING", "sign In Successful:" + task.isSuccessful());
//
//                        if (!task.isSuccessful()) {
//                            Log.w("TESTING", "signInWithEmail:failed", task.getException());
//                            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
//                        }
//                        else {
//                            Intent i = new Intent(getApplicationContext(), SigninActivity.class);
//                            finish();
//                            startActivity(i);
//                        }
//                    }
//                });
//
//    }
//
//
//
////
////
////
////
////
////    public void onStart() {
////            super.onStart();
////            // Check if user is signed in (non-null) and update UI accordingly.
////            FirebaseUser currentUser = mAuth.getCurrentUser();
////            updateUI(currentUser);
////        }
////
////        private void createAccount( String email, String password) {
////            Log.d(TAG, "createAccount:" + email);
//////            if (!validateForm()) {
//////                return;
//////            }
////            showProgressDialog();
////
////            // [START create_user_with_email]
////            mAuth.createUserWithEmailAndPassword(email, password)
////                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
////                        @Override
////                        public void onComplete(@NonNull Task<AuthResult> task) {
////                            if (task.isSuccessful()) {
////                                // Sign in success, update UI with the signed-in user's information
////                                Log.d(TAG, "createUserWithEmail:success");
////                                FirebaseUser user = mAuth.getCurrentUser();
////                                updateUI(user);
////                            } else {
////                                // If sign in fails, display a message to the user.
////                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
////                                Toast.makeText(SignupActivity.this, "Authentication failed.",
////                                        Toast.LENGTH_SHORT).show();
////                                updateUI(null);
////                            }
////
////                            // [START_EXCLUDE]
////                            hideProgressDialog();
////
////                            // [END_EXCLUDE]
////                        }
////                    });
////            // [END create_user_with_email]
////        }
////
////    private void signIn(String email, String password) {
////        Log.d(TAG, "signIn:" + email);
////        showProgressDialog();
////
////        // [START sign_in_with_email]
////        mAuth.signInWithEmailAndPassword(email, password)
////                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
////                    @Override
////                    public void onComplete(@NonNull Task<AuthResult> task) {
////                        if (task.isSuccessful()) {
////                            // Sign in success, update UI with the signed-in user's information
////                            Log.d(TAG, "signInWithEmail:success");
////                            FirebaseUser user = mAuth.getCurrentUser();
////                            updateUI(user);
////                        } else {
////                            // If sign in fails, display a message to the user.
////                            Log.w(TAG, "signInWithEmail:failure", task.getException());
////                            Toast.makeText(SignupActivity.this, "Authentication failed.",
////                                    Toast.LENGTH_SHORT).show();
////                            updateUI(null);
////                        }
////
////                        // [START_EXCLUDE]
////                        if (!task.isSuccessful()) {
////                            mStatusTextView.setText("auth_failed");
////                        }
////                        hideProgressDialog();
////                        // [END_EXCLUDE]
////                    }
////                });
////        // [END sign_in_with_email]
////        }
////
////
////
////        private void updateUI(FirebaseUser user) {
////            hideProgressDialog();
////            if (user != null) {
////                mStatusTextView.setText("Email User:(user.getEmail())");
////                mDetailTextView.setText("Firebase User:(user.getUid())");
////
////                getstart.setVisibility(View.GONE);
////                findViewById(R.id.editText_email).setVisibility(View.GONE);
////                login.setVisibility(View.VISIBLE);
////
////            } else {
////                mStatusTextView.setText("Please sign in");
////                mDetailTextView.setText(null);
////
////                getstart.setVisibility(View.VISIBLE);
////                findViewById(R.id.editText_email).setVisibility(View.VISIBLE);
////                login.setVisibility(View.GONE);
////            }
////        }
////    @Override
////    public void onClick(View v) {
////        int i = v.getId();
////        if (i == R.id.start_button) {
////            createAccount(useremail.getText().toString(), password.getText().toString());
////            loggedin=true;
////                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
////                intent.putExtra(EXTRA_LOGIN,loggedin);
////                setResult(Activity.RESULT_OK, intent);
////                finish();
////
////        } else {
////            signIn(useremail.getText().toString(), password.getText().toString());
////            loggedin=true;
////            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
////            intent.putExtra(EXTRA_LOGIN,loggedin);
////            setResult(Activity.RESULT_OK, intent);
////            finish();
////        }
////
////    }
//
//
//
//
//
//
//
//    }
//
//
