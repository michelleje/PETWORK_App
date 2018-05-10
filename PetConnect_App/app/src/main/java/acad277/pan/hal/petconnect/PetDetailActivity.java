package acad277.pan.hal.petconnect;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import acad277.pan.hal.petconnect.model.Pet;

import static acad277.pan.hal.petconnect.MainActivity.API_KEY;
import static acad277.pan.hal.petconnect.MainActivity.OPERATION;


public class PetDetailActivity extends AppCompatActivity {

    public static final String INDEX = "INDEX";
    public static final String PET_OBJECT = "PET_OBJECT";
    public static final String GET_URL = "http://api.petfinder.com/pet.get?format=json&key=";
    public static final String GET_SHELTER_URL = "http://api.petfinder.com/shelter.get?format=json&key=";

    public static final String SHELTER_EMAIL = "SHELTER_EMAIL";


    private ImageView imagePet;
    private TextView textMainName;
    private TextView textBreed;
    private TextView textAge;
    private TextView textSex;
    private TextView textSize;
    private TextView textLocation;
    private TextView textNotes;
    //    private TextView textGoodWith;
    private TextView textHealth;
    private TextView textMeetName;
    private TextView textMeet;
    private Button buttonFoster;

    private Pet thisPet;
    private int Index;
    private boolean photoFound = false;
    private boolean altered = false;
    private boolean hasShots = false;
    private boolean houseTrained = false;
    private boolean noDogs = false;
    private boolean noCats = false;
    private boolean noKids = false;

    private Toolbar toolbar;


    private String health = "None";
    private String notes = "None";

    //Volley
    private RequestQueue queue;
    private String searchLink;

    private String shelterEmail = "";

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.menu_detail_profile:
                Intent i = new Intent(getApplicationContext(), SigninActivity.class); //open profile page
                i.putExtra(OPERATION, "profile");
                i.putExtra(INDEX, 0);
                startActivity(i);

                break;
            case R.id.menu_detail_feed:
                Intent c = new Intent(getApplicationContext(), PhotoActivity.class); //open photo feed
                c.putExtra(OPERATION, "feed");
                c.putExtra(INDEX, 0);
                startActivity(c);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

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
        setContentView(R.layout.pet_detail);

        imagePet = findViewById(R.id.image_pet);
        textMainName = findViewById(R.id.text_main_name);
        textBreed = findViewById(R.id.text_breed);
        textAge = findViewById(R.id.text_age);
        textSex = findViewById(R.id.text_sex);
        textSize = findViewById(R.id.text_size);
        textLocation = findViewById(R.id.text_location);
        textNotes = findViewById(R.id.text_notes_display);
//        textGoodWith = findViewById(R.id.text_good_with_display);
        textHealth = findViewById(R.id.text_health_display);
        textMeetName = findViewById(R.id.text_meet_name);
        textMeet = findViewById(R.id.text_meet_display);
        buttonFoster = findViewById(R.id.button_foster);

        //TODO listener on the foster button that then passes in shelter content into the intent
        buttonFoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), FormActivity.class);
                i.putExtra(SHELTER_EMAIL, shelterEmail); //passes in entire object
                startActivity(i);
            }
        });

        //TODO read intent (should have the pet object)
        Intent i = getIntent();
        thisPet = (Pet) i.getSerializableExtra(PET_OBJECT);
        Index = i.getIntExtra(INDEX, -1);

        //TODO unpack pet object, retrieve id
        String petID = thisPet.getId();

        //TODO unpack existing values
        textMainName.setText(thisPet.getName());
        textBreed.setText(thisPet.getBreed());
        textMeetName.setText("Meet " + thisPet.getName());

        String size = thisPet.getSize();
        if (size.equalsIgnoreCase("L")){
            textSize.setText("Large");
        } else if (size.equalsIgnoreCase("M")){
            textSize.setText("Medium");
        } else if (size.equalsIgnoreCase("S")){
            textSize.setText("Small");
        }

        String age = thisPet.getAge() + " | ";
        textAge.setText(age);

        String petDescription = thisPet.getDescrip();
//        if (petDescription.length() >= 300) {
//            petDescription = petDescription.substring(0, 299);
//        }
        textMeet.setText(petDescription);


        //TODO make request to database for pet's specific id
        //Set up Volley queue
        queue = Volley.newRequestQueue(this);
        searchLink = GET_URL + API_KEY + "&id=" + petID;
        requestJSONParse(searchLink);


// TODO: Find Toolbar from View and set as ActionBar.
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar); //tells android "He, here's the toolbar"
        ActionBar actionBar = getSupportActionBar(); //for compatibility, get the action bar again?

    }
    //TODO fill in data from pet request
    public void requestJSONParse(String reqURL) {
        JsonObjectRequest req = new JsonObjectRequest
                (Request.Method.GET, reqURL, null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    //all the JSON-y code needs to be in the TRY block ( you can put other code
                                    //in the try block
                                    JSONObject jsonPetfinder = response.getJSONObject("petfinder");
                                    JSONObject jsonPet = jsonPetfinder.getJSONObject("pet");

                                    //SEX
                                    JSONObject jsonSex = jsonPet.getJSONObject("sex");
                                    String petSex = jsonSex.getString("$t");
                                    if (petSex.equalsIgnoreCase("M")){
                                        textSex.setText("Male | ");
                                    }else{
                                        textSex.setText("Female | ");
                                    }

                                    //TODO HEALTH (parse array, read through array looking for stuff, then fill text as appropriate)
                                    //NOTE "options" --> "option" array/ or could be null
//                                    "altered"  "hasShots" "housetrained" "noDogs" "noCats" "noKids"

                                    JSONObject jsonOptions = jsonPet.getJSONObject("options");
//                                    JSONArray jsonOptionA = jsonOptions.getJSONArray("option");
//                                    if (jsonOptionA != null){
//                                        for (int i = 0; i < jsonOptionA.length(); i++){
//                                            JSONObject currentObject = jsonOptionA.getJSONObject(i);
//                                            String currentValue = currentObject.getString("$t");
//
//
//                                        }
//
//                                    }

                                    if (!jsonOptions.isNull("option")) { //check to make sure something is there
                                        Object item = jsonOptions.get("option"); //get the thing that is there
                                        if (item instanceof JSONArray) {  // `instanceof` tells us whether the object can be cast to a specific type
                                            JSONArray optionArray = (JSONArray) item;
                                            for (int a = 0; a < optionArray.length(); a++){
                                                JSONObject currentOptionO = optionArray.getJSONObject(a);
                                                String currentOptionS = currentOptionO.getString("$t");

                                                if (currentOptionS.equalsIgnoreCase("altered")){
                                                    if (health.equalsIgnoreCase("None")){
                                                        health = "";
                                                    }
                                                    health = "Spayed/neutered.";
                                                }
                                                if (currentOptionS.equalsIgnoreCase("hasShots")){
                                                    if (!health.equalsIgnoreCase("None")){
                                                        health = "Vaccinations up to date. " + health;
                                                    } else{
                                                        health = "Vaccinations up to date.";
                                                    }

                                                }
                                                if (currentOptionS.equalsIgnoreCase("housetrained")){
                                                    notes = "House trained. ";
                                                }
                                                if (currentOptionS.equalsIgnoreCase("noDogs")){
                                                    if (!health.equalsIgnoreCase("None")){
                                                        notes = notes + "No dogs. ";
                                                    } else{
                                                        notes = "No dogs. ";
                                                    }

                                                }
                                                if (currentOptionS.equalsIgnoreCase("noCats")){
                                                    if (!notes.equalsIgnoreCase("None")){
                                                        notes = notes + "No cats. ";
                                                    } else{
                                                        notes = "No cats. ";
                                                    }
                                                }
                                                if (currentOptionS.equalsIgnoreCase("noKids")){
                                                    if (!notes.equalsIgnoreCase("None")){
                                                        notes = notes + "No kids. ";
                                                    } else{
                                                        notes = "No kids.";
                                                    }
                                                }
                                                textHealth.setText(health);
                                                textNotes.setText(notes);
                                                //display
                                            }
                                        }
                                        else {// since it's not an array, it's an object
                                            JSONObject optionObject = (JSONObject) item;
                                            String breed = optionObject.getString("$t");
                                            //display what it is

                                        }
                                    }


                                    //TODO SHELTER -- should add in UI
                                    //SHELTER EMAIL
                                    JSONObject contact = jsonPet.getJSONObject("contact");
                                    if (!jsonOptions.isNull("email")) {
                                        JSONObject email = contact.getJSONObject("email");
                                        shelterEmail = email.getString("$t");
                                    }

                                    //City
                                    JSONObject city = contact.getJSONObject("city");
                                    String cityName = city.getString("$t");
                                    String location = cityName + ", " + thisPet.getLocation();
                                    textLocation.setText(location);

                                    //PHOTO
                                    JSONObject currentMedia = jsonPet.getJSONObject("media");
                                    JSONObject currentPhotos = currentMedia.getJSONObject("photos");
                                    JSONArray currentPhotosJSONArray = currentPhotos.getJSONArray("photo");
                                    while (!photoFound){
                                        for (int i1 = 0; i1 < currentPhotosJSONArray.length();i1++){
                                            JSONObject currentPhoto = currentPhotosJSONArray.getJSONObject(i1);
                                            String size1 = currentPhoto.getString("@size");
                                            if (size1.equalsIgnoreCase("pn")){
                                                String imageLinkLG = currentPhoto.getString("$t");
                                                Picasso.get().load(imageLinkLG).into(imagePet);
                                                photoFound = true;
                                            }
                                        }
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();

                                }

                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });

// Access the RequestQueue through your singleton class.
        queue.add(req); // important to make the thing complete

    }

}
