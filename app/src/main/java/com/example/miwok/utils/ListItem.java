package com.example.miwok.utils;

public class ListItem {

    private final String mDefaultTranslation;
    private final String mMiwokTranslation;
    private final int mImageResourceID;
    private final int mAudioResourceID;

    public ListItem(String defaultTranslation, String miwokTranslation, int mAudioResourceID) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResourceID = -1;
        this.mAudioResourceID = mAudioResourceID;
    }

    public ListItem(String defaultTranslation, String miwokTranslation, int mImageResourceID, int mAudioResourceID) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        this.mImageResourceID = mImageResourceID;
        this.mAudioResourceID = mAudioResourceID;
    }

    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    public int getImageResourceID() {
        return mImageResourceID;
    }

    public int getAudioResourceID() {
        return mAudioResourceID;
    }

    public boolean hasImage() {
        return mImageResourceID != -1;
    }
}