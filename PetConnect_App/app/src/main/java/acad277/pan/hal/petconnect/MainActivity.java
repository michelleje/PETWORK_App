package acad277.pan.hal.petconnect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_LOGIN ="EXTRA_LOGIN";
//    boolean login;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        Boolean login = i.getBooleanExtra(EXTRA_LOGIN, false);
        if(login=true){
            setContentView(R.layout.activity_main);
        }
        if(login=false){
            Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
        }



        }



}


