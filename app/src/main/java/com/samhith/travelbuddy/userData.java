package com.samhith.travelbuddy;

/**
 * Created by samhith on 21/3/16.
 */
public class userData {

    private String name;
    private String city;
    private int expert;

    public userData() {
      /*Blank default constructor essential for Firebase*/
    }
    //Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;

    }


    public int getExpert() {
        return expert;
    }

    public void setExpert(int name) {
        this.expert = name;
    }
}



