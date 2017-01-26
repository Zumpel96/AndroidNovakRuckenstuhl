package com.hgbjg14.cahproject;

import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class AboutActivity extends Fragment implements View.OnClickListener  {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentActivity faActivity = (FragmentActivity)super.getActivity();

        RelativeLayout rlLayout = (RelativeLayout)inflater.inflate(R.layout.activity_about, container, false);

        Button button = null;
        button = (Button)rlLayout.findViewById(R.id.about_back_button);
        button.setOnClickListener(this);
        return rlLayout;
    }

    @Override
    public void onClick(View v) {

    }
}
