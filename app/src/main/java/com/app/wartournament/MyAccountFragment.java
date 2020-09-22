package com.app.wartournament;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;

public class MyAccountFragment extends Fragment {
    CardView logOutCard;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myaccount, container, false);
        logOutCard = view.findViewById(R.id.logOutCard);
        logOutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("logininfo",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("logged",false);
                editor.commit();
                Intent i = new Intent(getActivity(),LoginAcitivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });
        return view;
    }
}