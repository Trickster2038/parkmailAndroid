package com.park.soulmates;

public class AdvancedUserModel {
    public String uid, name, surname, bio;
    boolean romanticSearch, male;
    // FIXME: fix troubles with birthdate input
    String birthdate;
    String contacts;
    InterestsModel interests;

    public AdvancedUserModel(){
        this.uid = "noUID";
        this.name = "no name";
        this.surname = "no surname";
        this.contacts = "no contacts";
        this.romanticSearch = false;
        this.male = false;
        this.bio = "no bio";
        this.birthdate = "01.01.2000";
        this.interests = new InterestsModel();
    }

    public AdvancedUserModel(String iUID, String iName, String iSurname, String iBio, String iBirth,
                             String iContacts, Boolean iRomanticSearch, Boolean iMale, Boolean[] iInterests){
        this.uid = iUID;
        this.name = iName;
        this.surname = iSurname;
        this.bio = iBio;
        this.birthdate = iBirth;
        this.contacts = iContacts;
        this.romanticSearch = iRomanticSearch;
        this.male = iMale;
        this.interests = new InterestsModel(iInterests);
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
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
