package com.example.covid_19tracker.ui.timeline;

public class IndiaTimeline {
    String mDate, mTotalTimelineConfirm, mTotalTimelineDeaths;

    public IndiaTimeline(String mDate, String mTotalTimelineConfirm, String mTotalTimelineDeaths) {
        this.mDate = mDate;
        this.mTotalTimelineConfirm = mTotalTimelineConfirm;
        this.mTotalTimelineDeaths = mTotalTimelineDeaths;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmTotalTimelineConfirm() {
        return mTotalTimelineConfirm;
    }

    public String getmTotalTimelineDeaths() {
        return mTotalTimelineDeaths;
    }
}
