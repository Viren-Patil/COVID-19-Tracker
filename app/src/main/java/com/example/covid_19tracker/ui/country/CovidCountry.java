package com.example.covid_19tracker.ui.country;

import android.os.Parcel;
import android.os.Parcelable;

public class CovidCountry implements Parcelable {
    String mCovidCountry, mTodayCases, mTodayDeaths, mCritical, mFlags;
    int mCases, mDeaths, mRecovered, mActive;

    public CovidCountry(String mCovidCountry, String mTodayCases, int mDeaths, String mTodayDeaths, int mRecovered, int mActive, String mCritical, String mFlags, int mCases) {
        this.mCovidCountry = mCovidCountry;
        this.mTodayCases = mTodayCases;
        this.mDeaths = mDeaths;
        this.mTodayDeaths = mTodayDeaths;
        this.mRecovered = mRecovered;
        this.mActive = mActive;
        this.mCritical = mCritical;
        this.mFlags = mFlags;
        this.mCases = mCases;
    }

    public String getmCovidCountry() {
        return mCovidCountry;
    }

    public String getmTodayCases() {
        return mTodayCases;
    }

    public String getmTodayDeaths() {
        return mTodayDeaths;
    }

    public String getmCritical() {
        return mCritical;
    }

    public String getmFlags() {
        return mFlags;
    }

    public int getmCases() {
        return mCases;
    }

    public int getmDeaths() {
        return mDeaths;
    }

    public int getmRecovered() {
        return mRecovered;
    }

    public int getmActive() {
        return mActive;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mCovidCountry);
        dest.writeString(this.mTodayCases);
        dest.writeString(this.mTodayDeaths);
        dest.writeString(this.mCritical);
        dest.writeString(this.mFlags);
        dest.writeInt(this.mCases);
        dest.writeInt(this.mDeaths);
        dest.writeInt(this.mRecovered);
        dest.writeInt(this.mActive);
    }

    protected CovidCountry(Parcel in) {
        this.mCovidCountry = in.readString();
        this.mTodayCases = in.readString();
        this.mTodayDeaths = in.readString();
        this.mCritical = in.readString();
        this.mFlags = in.readString();
        this.mCases = in.readInt();
        this.mDeaths = in.readInt();
        this.mRecovered = in.readInt();
        this.mActive = in.readInt();
    }

    public static final Creator<CovidCountry> CREATOR = new Creator<CovidCountry>() {
        @Override
        public CovidCountry createFromParcel(Parcel source) {
            return new CovidCountry(source);
        }

        @Override
        public CovidCountry[] newArray(int size) {
            return new CovidCountry[size];
        }
    };
}