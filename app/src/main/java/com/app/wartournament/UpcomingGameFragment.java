package com.app.wartournament;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UpcomingGameFragment extends Fragment {
    LinearLayout referandearncard;
    List<Upcominggamemodek> upcomingdata;
    RecyclerView recyclerView;
    ShimmerFrameLayout shimmer_view_container;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming_game, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        shimmer_view_container = view.findViewById(R.id.shimmer_view_container);
        loadupcomingmatch();
        return view;
    }

    public void loadupcomingmatch() {
        String url = "http://www.wartournament.com/Api/Manage/GameList";
        // Creating string request with post method.
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        try{
                            JSONObject obj = new JSONObject(ServerResponse);
                            upcomingdata = new ArrayList<>();
                            if(obj.getString("Message").trim().equals("Success")) {
                                JSONArray jsonArray = new JSONArray(obj.getString("ResultRows"));
                                for (int i = 0; i<jsonArray.length();i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    Log.d("serverrescheck",object.getString("Images"));
                                    Upcominggamemodek model = new Upcominggamemodek();
                                    model.setId(object.getString("Id"));
                                    model.setMatch_id(object.getString("MatchId"));
                                    model.setTitle(object.getString("GameCat"));
                                    model.setEntry_type(object.getString("Type"));
                                    model.setVersion(object.getString("Version"));
                                    model.setMap(object.getString("Map"));
                                    model.setMatchType2(object.getString("MatchType"));
                                    model.setEntry_fee(object.getString("EntryFee"));
                                    model.setRoom_size(object.getString("MatchSize"));
                                    model.setPer_kill(object.getString("PerKill"));
                                    model.setWinner(object.getString("Winner"));
                                    model.setRunnner1(object.getString("RunnerUp"));
                                    model.setRunner2(object.getString("RunnerUp1"));
                                    model.setCoin(object.getString("PerCoin"));
                                    model.setTotalprize(object.getString("TotalPrize"));
                                    model.setMatch_desc(object.getString("AboutMatch"));
                                    model.setTime(object.getString("MatchDate"));
                                    model.setImage(object.getString("Images"));
                                    if(!object.getString("TotalParticipates").equals("null")){
                                        model.setParticipants(object.getString("TotalParticipates"));
                                    }
                                    else{
                                        model.setParticipants("0");

                                    }
                                    upcomingdata.add(model);
                                }
                                Collections.reverse(upcomingdata);
                                shimmer_view_container.setVisibility(View.GONE);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                                recyclerView.setLayoutManager(linearLayoutManager);
                                UpComingGameAdapter adapter = new UpComingGameAdapter(getActivity(),upcomingdata);
                                recyclerView.setAdapter(adapter);
                            }

                        }
                        catch (Exception e) {
                            Toast.makeText(getActivity(), "ex : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("serverrescheck","error:  "+e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        if(volleyError.getMessage() == null) {
                            Log.d("checkign","error : call from null");
                        }
                        else{
                        }

                        Toast.makeText(getActivity(), "error :  "+volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }


}