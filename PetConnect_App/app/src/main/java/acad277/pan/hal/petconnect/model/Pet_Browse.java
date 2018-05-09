package acad277.pan.hal.petconnect.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import acad277.pan.hal.petconnect.R;

/**
 * Created by Helen23 on 5/9/18.
 */

public class Pet_Browse extends ArrayAdapter<Pet> {
    //CONSTANTS
    private final static int MAX_LENGTH_DESCRIPTION = 70;

    int resources;


    public Pet_Browse(Context context, int resource, ArrayList<Pet> pets) {
        super(context, resource, pets);
        resources = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //inflate the view
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row_browse_profile, parent, false);
        }

        //getting the position
        Pet pet = getItem(position);

        //listeners for the view items
        TextView breed = (TextView) convertView.findViewById(R.id.list_breed);
        TextView name = (TextView) convertView.findViewById(R.id.list_item_name);
        TextView location = (TextView) convertView.findViewById(R.id.list_item_location);
        TextView size = (TextView) convertView.findViewById(R.id.list_item_size);
        TextView description = (TextView) convertView.findViewById(R.id.list_item_meet);
        TextView age = (TextView) convertView.findViewById(R.id.list_item_age);
        ImageView image = (ImageView) convertView.findViewById(R.id.imageView);

        //populate that sweet sweet data from the Pets class
        breed.setText(pet.getBreed());
        name.setText(pet.getName());

        String displayAge = pet.getAge() + " | ";
        age.setText(displayAge);


        String displayLocation = pet.getLocation() + " | ";
        location.setText(displayLocation);

        size.setText(pet.getSize());

        //Description of pet (limited size)
        String displayDescription = pet.getDescrip();
        if (displayDescription.length() >= MAX_LENGTH_DESCRIPTION){
            displayDescription = displayDescription.substring(0, (MAX_LENGTH_DESCRIPTION-1)) + "...";
        }
        description.setText(displayDescription);

        //image
        Picasso.get().load(pet.getImageLinksm()).into(image);

        //this will return the view and render on the screen
//        return super.getView(position, convertView, parent);
        return convertView;
    }
}
