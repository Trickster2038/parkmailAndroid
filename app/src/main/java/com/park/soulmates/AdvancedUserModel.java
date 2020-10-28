package com.park.soulmates;

public class AdvancedUserModel {
    public String name, surname, bio;
    boolean romanticSearch;
    String birthdate;
    InterestsModel interests;

    public AdvancedUserModel(){
        this.name = "no name";
        this.surname = "no surname";
        this.bio = "no bio";
        birthdate = "01.01.2000";
        interests = new InterestsModel();
    }

    public AdvancedUserModel(String iName, String iSurname, String iBio, String iBirth, Boolean iRomanticSearch,  Boolean[] iInterests){
        this.name = iName;
        this.surname = iSurname;
        this.bio = iBio;
        this.birthdate = iBirth;
        this.romanticSearch = iRomanticSearch;
        this.interests = new InterestsModel(iInterests);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public boolean isRomanticSearch() {
        return romanticSearch;
    }

    public void setRomanticSearch(boolean romanticSearch) {
        this.romanticSearch = romanticSearch;
    }

    public InterestsModel getInterests() {
        return interests;
    }

    public void setInterests(InterestsModel interests) {
        this.interests = interests;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }
}
