package com.example.iotcommunication;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class MyAdapter extends FragmentStatePagerAdapter {
    Context context;
    int totalTabs;
    //final static int BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT=1;

    public MyAdapter(Context c,FragmentManager fm, int totalTabs) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context=c;
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                PatientDetails patientDetails=new PatientDetails();
                return patientDetails;
            case 1:
                PatientMessage patientMessage = new PatientMessage();
                return patientMessage;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
