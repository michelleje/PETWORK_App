package acad277.pan.hal.petconnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Date;

import static acad277.pan.hal.petconnect.PetDetailActivity.SHELTER_EMAIL;

public class FormActivity extends AppCompatActivity {

    private SeekBar seekbar;
    private TextView timelength;
    private EditText name;
    private EditText contact;
    private EditText numpet;
    private EditText experience;
    private EditText numppl;
    private EditText living;
    private EditText location;
    private Button submit;
    private Toolbar toolbar;
    private String email;

    String content;
    private FirebaseAuth mAuth;
    int counter=0;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ALMOST always the same
        //just change the first parameter of the next line
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        mAuth = FirebaseAuth.getInstance();
        name=findViewById(R.id.username);
        contact=findViewById(R.id.usercontact);
        numpet=findViewById(R.id.numofpet);
        experience=findViewById(R.id.fosterexperiences);
        numppl=findViewById(R.id.familymembers);
        living=findViewById(R.id.livingsituation);
        location=findViewById(R.id.location_value);
        submit=findViewById(R.id.submitbutton);

        seekbar = (SeekBar) findViewById(R.id.seekBar_timelength);
        timelength = (TextView) findViewById(R.id.textview_timelength_value);

        //UNPACK INTENT
        Intent c = getIntent();
        email = c.getStringExtra(SHELTER_EMAIL);
        if (email.equalsIgnoreCase("")){
            email = "hal.h.pan@gmail.com";
        }

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            String un = preferences.getString("Name", "");
            String uc= preferences.getString("Contact", "");
            String pet= preferences.getString("NPet", "");
            String ppl= preferences.getString("NPpl", "");
            String ex= preferences.getString("Experience", "");
            String ls= preferences.getString("Living", "");
            String lo= preferences.getString("Location", "");
            if(!un.equalsIgnoreCase(""))
            {
                un = un + name.getText().toString();  /* Edit the value here*/
                uc=uc+contact.getText().toString();
                pet=uc+numpet.getText().toString();
                ppl=uc+numppl.getText().toString();
                ex=uc+experience.getText().toString();
                ls=uc+living.getText().toString();
                lo=uc+location.getText().toString();
            }

            name.setText(un);
            contact.setText(uc);
            numpet.setText(pet);
            numppl.setText(ppl);
            experience.setText(ex);
            living.setText(ls);
            location.setText(lo);


        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                timelength.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(FormActivity.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Name",name.getText().toString());
                editor.putString("Contact",contact.getText().toString());
                editor.putString("NPet",numpet.getText().toString());
                editor.putString("NPpl",numppl.getText().toString());
                editor.putString("Experience",experience.getText().toString());
                editor.putString("Living",living.getText().toString());
                editor.putString("Location",location.getText().toString());
                editor.apply();
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                FirebaseUser user = mAuth.getCurrentUser();
//                String name=user.getDisplayName();
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{email}); //shelter's email address
                i.putExtra(Intent.EXTRA_SUBJECT, name.getText().toString()+" is interested in fostering");
                i.putExtra(Intent.EXTRA_TEXT, "Hi, this is "+ name.getText().toString() +" from "+location.getText().toString()+". I'm very interested in fostering one of your pets for "+timelength.getText().toString()+" weeks. For me about me: there are "
                        +numppl.getText().toString()+" people in my family and "+numpet.getText().toString()+" pets. "+" About my previous foster experiences: "+experience.getText().toString()+". Here's my contact info: "+contact.getText().toString()+". \n Hope to hear from you soon. \n Thank you");
                try {
                    counter++;
                startActivity(Intent.createChooser(i, "Send mail..."));


                } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(FormActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }

                finish();

            }




        });

        // TODO: Find Toolbar from View and set as ActionBar.
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar); //tells android "He, here's the toolbar"
        ActionBar actionBar = getSupportActionBar(); //for compatibility, get the action bar again?


    }
}

//import android.content.Intent;
//        import android.os.Bundle;
//        import android.support.v7.app.AppCompatActivity;
//        import android.view.View;
//        import android.widget.Button;
//        import android.widget.Toast;
//
//public class MainActivity extends AppCompatActivity {
//    Button send;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        send=findViewById(R.id.sendbutton);
//
//        send.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(Intent.ACTION_SEND);
//                i.setType("text/plain");
//                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"angelasunwu@gmail.com"});
//                i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
//                i.putExtra(Intent.EXTRA_TEXT   , "body of email");
//                try {
//                    startActivity(Intent.createChooser(i, "Send mail..."));
//                } catch (android.content.ActivityNotFoundException ex) {
//                    Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
//
//    }

