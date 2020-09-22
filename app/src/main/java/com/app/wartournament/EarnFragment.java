package com.app.wartournament;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

public class EarnFragment extends Fragment {
    LinearLayout referandearncard;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_earn, container, false);
        referandearncard = view.findViewById(R.id.referandearncard);
        referandearncard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),RefereEarnActivity.class);
                startActivity(i);
            }
        });
        return view;
    }
}