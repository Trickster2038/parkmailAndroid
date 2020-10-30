package com.park.soulmates;

public class UserModel {
    private String mUid, mName, mSurname, mBio;
    private boolean mRomanticSearch, mGender;
    // mGender: false - female, true - male
    // FIXME: fix troubles with birthdate input
    private String mBirthdate, mContacts;
    private InterestsModel mInterests;

    public UserModel() {
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

    public UserModel(String uid, String name, String surname, String bio, String birth,
                     String contacts, Boolean romanticSearch, Boolean gender, Boolean[] interests) {
        this.mUid = uid;
        this.mName = name;
        this.mSurname = surname;
        this.mBio = bio;
        this.mBirthdate = birth;
        this.mContacts = contacts;
        this.mRomanticSearch = romanticSearch;
        this.mGender = gender;
        this.mInterests = new InterestsModel(interests);
    }

    public boolean getGender() {
        return mGender;
    }

    public void setGender(boolean mGender) {
        this.mGender = mGender;
    }

    public String getUid() {
        return mUid;
    }

    public void setUid(String mUid) {
        this.mUid = mUid;
    }

    public String getContacts() {
        return mContacts;
    }

    public void setContacts(String mContacts) {
        this.mContacts = mContacts;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getSurname() {
        return mSurname;
    }

    public void setSurname(String mSurname) {
        this.mSurname = mSurname;
    }

    public String getBio() {
        return mBio;
    }

    public void setBio(String mBio) {
        this.mBio = mBio;
    }

    public boolean getRomanticSearch() {
        return mRomanticSearch;
    }

    public void setRomanticSearch(boolean mRomanticSearch) {
        this.mRomanticSearch = mRomanticSearch;
    }

    public InterestsModel getInterests() {
        return mInterests;
    }

    public void setInterests(InterestsModel mInterests) {
        this.mInterests = mInterests;
    }

    public String getBirthdate() {
        return mBirthdate;
    }

    public void setBirthdate(String mBirthdate) {
        this.mBirthdate = mBirthdate;
    }
}
