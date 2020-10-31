package com.park.soulmates;

public class AdvancedUserModel {
    private String mUid, mName, mSurname, mBio, mBirthdate, mContacts;
    private InterestsModel mInterests;
    private boolean mRomanticSearch, mGender; //mGender: false - female, true - male
    // FIXME: fix troubles with birthdate input


    public AdvancedUserModel() {
        this.mUid = "noUID";
        this.mName = "no name";
        this.mSurname = "no surname";
        this.mContacts = "no contacts";
        this.mRomanticSearch = false;
        this.mGender = false;
        this.mBio = "no bio";
        this.mBirthdate = "01.01.2000";
        this.mInterests = new InterestsModel();
    }

    public AdvancedUserModel(String uid, String name, String surname, String bio, String birthdate,
                             String contacts, Boolean romanticSearch, Boolean gender, Boolean[] interests) {
        this.mUid = uid;
        this.mName = name;
        this.mSurname = surname;
        this.mBio = bio;
        this.mBirthdate = birthdate;
        this.mContacts = contacts;
        this.mRomanticSearch = romanticSearch;
        this.mGender = gender;
        this.mInterests = new InterestsModel(interests);
    }

    public boolean getGender() {
        return mGender;
    }

    public void setGender(boolean male) {
        this.mGender = male;
    }

    public String getUid() {
        return mUid;
    }

    public void setUid(String uid) {
        this.mUid = uid;
    }

    public String getContacts() {
        return mContacts;
    }

    public void setContacts(String contacts) {
        this.mContacts = contacts;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getSurname() {
        return mSurname;
    }

    public void setSurname(String surname) {
        this.mSurname = surname;
    }

    public String getBio() {
        return mBio;
    }

    public void setBio(String bio) {
        this.mBio = bio;
    }

    public boolean getRomanticSearch() {
        return mRomanticSearch;
    }

    public void setRomanticSearch(boolean romanticSearch) {
        this.mRomanticSearch = romanticSearch;
    }

    public InterestsModel getInterests() {
        return mInterests;
    }

    public void setInterests(InterestsModel interests) {
        this.mInterests = interests;
    }

    public String getBirthdate() {
        return mBirthdate;
    }

    public void setBirthdate(String birthdate) {
        this.mBirthdate = birthdate;
    }
}
