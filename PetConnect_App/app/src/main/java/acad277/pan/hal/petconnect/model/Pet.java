package acad277.pan.hal.petconnect.model;

import java.io.Serializable;

/**
 * Created by Helen23 on 5/9/18.
 */

public class Pet implements Serializable {
    private String id;
    private String name;
    private String age;
    private String gender;
    private String breed;
    private String size;
    private String location;
    private String coatLength;
    private String houseTrained;
    private String goodWith;
    private String health;
    private String descrip;
    private String imageLinksm;
    private String imageLinklg;
    private String shelterID;

    //default constructor
    public Pet(){

    }

    //constructor
    public Pet(String id, String name, String age, String gender, String breed, String size, String location, String coatLength, String houseTrained, String goodWith, String health, String descrip, String imageLinksm, String imageLinklg, String shelterID) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.breed = breed;
        this.size = size;
        this.location = location;
        this.coatLength = coatLength;
        this.houseTrained = houseTrained;
        this.goodWith = goodWith;
        this.health = health;
        this.descrip = descrip;
        this.imageLinksm = imageLinksm;
        this.imageLinklg = imageLinklg;
        this.shelterID = shelterID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCoatLength() {
        return coatLength;
    }

    public void setCoatLength(String coatLength) {
        this.coatLength = coatLength;
    }

    public String getHouseTrained() {
        return houseTrained;
    }

    public void setHouseTrained(String houseTrained) {
        this.houseTrained = houseTrained;
    }

    public String getGoodWith() {
        return goodWith;
    }

    public void setGoodWith(String goodWith) {
        this.goodWith = goodWith;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getImageLinksm() {
        return imageLinksm;
    }

    public void setImageLinksm(String imageLinksm) {
        this.imageLinksm = imageLinksm;
    }

    public String getImageLinklg() {
        return imageLinklg;
    }

    public void setImageLinklg(String imageLinklg) {
        this.imageLinklg = imageLinklg;
    }

    public String getShelterID() {
        return shelterID;
    }

    public void setShelterID(String shelterID) {
        this.shelterID = shelterID;
    }
}
