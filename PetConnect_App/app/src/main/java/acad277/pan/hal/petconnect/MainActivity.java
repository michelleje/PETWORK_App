package acad277.pan.hal.petconnect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import acad277.pan.hal.petconnect.model.Pet;
import acad277.pan.hal.petconnect.model.Pet_Browse;

import static acad277.pan.hal.petconnect.SignupActivity.LOGGED_IN;
import static acad277.pan.hal.petconnect.SignupActivity.USER_ZIPCODE;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_LOGIN ="EXTRA_LOGIN";
//    boolean login;
public static final String BREED_URL = "http://api.petfinder.com/pet.find?format=json&key=";
    public static final String API_KEY = "991a2ad1bcef0f526b388e9d0acef1ab";
    private final String API_SECRET = "695b82d6d5a05a505934500f0c0ea37c";
    private static final int DETAIL_ACTIVITY_REQUEST_CODE = 100;
    public static final String OPERATION = "OPERATION";
    public static final String INDEX = "INDEX";


    //variables for screen items
    private Button buttonSearch;
    private EditText editBreed;
    private EditText editLocation;
    private ListView listPets;
    private RelativeLayout layout;
    private Toolbar toolbar;

    //variables for Volley and parsing
    private String searchLink;
    private boolean photoFound = false;
    private String displayBreed;

    //variables for Filtering
    private String petLocation = "90007";

    //variable for Toolbar
    private int mIndex;

    Pet_Browse adapter;
    ArrayList<Pet> pets;

    //TODO Volley
    private RequestQueue queue;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ALMOST always the same
        //just change the first parameter of the next line
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //this is the method to perform actions with the menu is clicked
    //aka the listener for button clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.menu_detail_profile:
                Intent i = new Intent(getApplicationContext(), SigninActivity.class); //open profile page
                i.putExtra(OPERATION, "profile");
                i.putExtra(INDEX, mIndex);
                startActivity(i);

                break;
            case R.id.menu_detail_feed:
                Intent c = new Intent(getApplicationContext(), PhotoActivity.class); //open photo feed
                c.putExtra(OPERATION, "feed");
                c.putExtra(INDEX, mIndex);
                startActivity(c);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                petLocation = data.getStringExtra(USER_ZIPCODE);

                //this might not fly
                searchLink = BREED_URL + API_KEY + "&location=" + petLocation;
                requestJSONParse(searchLink);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Intent i = getIntent();
//        boolean login = i.getBooleanExtra(LOGGED_IN, false);
//
//        if(!login){
//            Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
//            startActivityForResult(intent, 0);
//        }

        listPets = (ListView) findViewById(R.id.list_pet);

        //the adapter that uses the above arraylist
        pets = new ArrayList<>();
        adapter = new Pet_Browse(
                this,
                R.layout.list_row_browse_profile,
                pets);
        listPets.setAdapter(adapter); //the view need to be called to action


        //TODO set click listeners for click list
        listPets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), PetDetailActivity.class);
                Pet p = pets.get(position);
                i.putExtra(PetDetailActivity.PET_OBJECT, p); //passes in entire object
                i.putExtra(PetDetailActivity.INDEX, position);

                startActivityForResult(i, DETAIL_ACTIVITY_REQUEST_CODE);
            }
        });


        //TODO set up Volley queue
        queue = Volley.newRequestQueue(this);

        searchLink = BREED_URL + API_KEY + "&location=" + petLocation;
        requestJSONParse(searchLink);

//        buttonSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String petBreed = editBreed.getText().toString();
//                String petLocation = editLocation.getText().toString();
//                searchLink = BREED_URL + API_KEY + "&location=" + petLocation;
//
////                searchLink = BREED_URL + API_KEY + "&location=" + petLocation + "&breed=" + petBreed;
//                requestJSONParse(searchLink);
//
//            }
//        });

        // TODO: Find Toolbar from View and set as ActionBar.
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar); //tells android "He, here's the toolbar"
        ActionBar actionBar = getSupportActionBar(); //for compatibility, get the action bar again?


        }

    //TODO This will contact the Volley request for String data
    public void requestString(String reqURL) {

    }
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
                                    JSONObject jsonPets = jsonPetfinder.getJSONObject("pets");
                                    JSONArray jsonPet = jsonPets.getJSONArray("pet");
                                    int length = jsonPet.length();

                                    pets.clear();

                                    for (int i = 0; i < length; i++){
                                        //make a blank pet object
                                        Pet currentPetObject = new Pet();
                                        displayBreed = "";
                                        JSONObject currentPet = jsonPet.getJSONObject(i);

                                        //getting the NAME from api and setting into object
                                        JSONObject currentName = currentPet.getJSONObject("name");
                                        String name = currentName.getString("$t");
                                        currentPetObject.setName(name);

                                        //ID NUMBER
                                        JSONObject currentID = currentPet.getJSONObject("id");
                                        String id = currentID.getString("$t");
                                        currentPetObject.setId(id);

                                        //BREED -- can be object or array
                                        JSONObject currentBreeds = currentPet.getJSONObject("breeds");
//                                        JSONObject currentBreed = currentBreeds.getJSONObject("breed");
                                        if (!currentBreeds.isNull("breed")) { //check to make sure something is there
                                            Object item = currentBreeds.get("breed"); //get the thing that is there
                                            if (item instanceof JSONArray) {  // `instanceof` tells us whether the object can be cast to a specific type
                                                JSONArray breedArray = (JSONArray) item;
                                                for (int a = 0; a < breedArray.length(); a++){
                                                    JSONObject currentBreedO = breedArray.getJSONObject(a);
                                                    String currentBreedS = currentBreedO.getString("$t");
                                                    displayBreed = displayBreed + currentBreedS + " | ";
                                                }
                                            }
                                            else {// since it's not an array, it's an object
                                                JSONObject breedObject = (JSONObject) item;
                                                String breed = breedObject.getString("$t");
                                                displayBreed = breed;

                                            }
                                        }
                                        currentPetObject.setBreed(displayBreed);

                                        //LOCATION
                                        JSONObject currentContact = currentPet.getJSONObject("contact");
                                        JSONObject currentZip = currentContact.getJSONObject("zip");
                                        String zip = currentZip.getString("$t");
                                        currentPetObject.setLocation(zip);

                                        //SIZE
                                        JSONObject currentSize = currentPet.getJSONObject("size");
                                        String size = currentSize.getString("$t");
                                        currentPetObject.setSize(size);

                                        //AGE
                                        JSONObject currentAge = currentPet.getJSONObject("age");
                                        String age = currentAge.getString("$t");
                                        currentPetObject.setAge(age);

                                        //DESCRIPTION
                                        JSONObject currentDescription = currentPet.getJSONObject("description");
                                        String description = currentDescription.getString("$t");
                                        currentPetObject.setDescrip(description);

                                        //IMAGE (SM)
                                        JSONObject currentMedia = currentPet.getJSONObject("media");
                                        JSONObject currentPhotos = currentMedia.getJSONObject("photos");
                                        JSONArray currentPhotosJSONArray = currentPhotos.getJSONArray("photo");
                                        while (!photoFound){
                                            for (int i1 = 0; i1 < currentPhotosJSONArray.length();i1++){
                                                JSONObject currentPhoto = currentPhotosJSONArray.getJSONObject(i1);
                                                String size1 = currentPhoto.getString("@size");
                                                if (size1.equalsIgnoreCase("fpm")){
                                                    String imageLinkSM = currentPhoto.getString("$t");
                                                    currentPetObject.setImageLinksm(imageLinkSM);
                                                    photoFound = true;
                                                }
                                            }
                                        }
                                        pets.add(currentPetObject);
                                        photoFound = false; //reset photo for each entry
                                        adapter.notifyDataSetChanged();
                                    }
//                                    adapter.notifyDataSetChanged(); // let the adapter know that the data inside has changed.


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


