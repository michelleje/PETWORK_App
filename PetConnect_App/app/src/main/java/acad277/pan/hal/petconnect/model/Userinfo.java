package acad277.pan.hal.petconnect.model;

/**
 * Created by wuangela on 5/9/18.
 */

public class Userinfo {

    String zipcode;
    String city;
    String numpet;

    // default constructor is needed by Firebase!
    public Userinfo() {
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNumpet() {
        return numpet;
    }

    public void setNumpet(String numpet) {
        this.numpet = numpet;
    }
}
