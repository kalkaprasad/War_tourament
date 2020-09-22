package com.app.wartournament;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;


public class UpComingGameAdapter extends  RecyclerView.Adapter<UpComingGameAdapter.Exampleiewholder>{
    public List<Upcominggamemodek> emExampleList;
    public Context mcontext;
    public Boolean bg_change = false;
    public  int id = 0;
    ProgressDialog progressDialog;
    AlertDialog alertDialog;


    public  static  class  Exampleiewholder extends  RecyclerView.ViewHolder {
        CardView cardView;
        TextView fee;
        ImageView img;
        Button joinBtn;
        TextView map;
        ProgressBar materialProgressBar;
        TextView perkill;
        RelativeLayout privateTextArea;
        TextView prize;
        LinearLayout prizePoolLL;
        TextView size;
        TextView sponsorText;
        RelativeLayout sponsorTextArea;
        TextView spot;
        TextView timedate;
        TextView title;
        ImageView topImage;
        TextView type;
        TextView version,spots;

        public Exampleiewholder(@NonNull View itemView) {
            super(itemView);
            this.cardView = (CardView) itemView.findViewById(R.id.mainCard);
            this.img = (ImageView) itemView.findViewById(R.id.img);
            this.title = (TextView) itemView.findViewById(R.id.title);
            this.timedate = (TextView) itemView.findViewById(R.id.timedate);
            this.prize = (TextView) itemView.findViewById(R.id.winPrize);
            this.prizePoolLL = (LinearLayout) itemView.findViewById(R.id.prizePoolLL);
            this.perkill = (TextView) itemView.findViewById(R.id.perKill);
            this.fee = (TextView) itemView.findViewById(R.id.entryFee);
            this.spot = (TextView) itemView.findViewById(R.id.spots);
            this.size = (TextView) itemView.findViewById(R.id.size);
            this.materialProgressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
            this.joinBtn = (Button) itemView.findViewById(R.id.joinButton);
            this.sponsorTextArea = (RelativeLayout) itemView.findViewById(R.id.sponsorTextArea);
            this.sponsorText = (TextView) itemView.findViewById(R.id.sponsorText);
            this.type = (TextView) itemView.findViewById(R.id.matchType);
            this.version = (TextView) itemView.findViewById(R.id.matchVersion);
            this.map = (TextView) itemView.findViewById(R.id.matchMap);
            this.spots = (TextView) itemView.findViewById(R.id.spots);
            this.topImage = (ImageView) itemView.findViewById(R.id.mainTopBanner);
            this.privateTextArea = (RelativeLayout) itemView.findViewById(R.id.privateTextArea);

        }
    }
    public UpComingGameAdapter(Context context, List<Upcominggamemodek> examplelist) {
        mcontext=context;
        emExampleList=examplelist;
    }

    @NonNull
    @Override
    public Exampleiewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_play,parent,false);
        Exampleiewholder exampleiewholder = new Exampleiewholder(view);
        return exampleiewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Exampleiewholder holder, int position) {
        final Upcominggamemodek currentitem = emExampleList.get(position);
         holder.title.setText(currentitem.getTitle());
         holder.timedate.setText(currentitem.getTime());
         holder.prize.setText(currentitem.getTotalprize());
         holder.perkill.setText(currentitem.getPer_kill());
         holder.fee.setText(currentitem.getEntry_fee());
         holder.type.setText(currentitem.getEntry_type());
         holder.version.setText(currentitem.getVersion());
         holder.map.setText(currentitem.getMap());
         holder.size.setText(currentitem.getRoom_size());
         holder.spots.setText("Only "+(Integer.parseInt(currentitem.getRoom_size())-Integer.parseInt(currentitem.getParticipants()))+"spots left");
         holder.materialProgressBar.setMax(Integer.parseInt(currentitem.getRoom_size()));
         holder.materialProgressBar.setProgress(Integer.parseInt(currentitem.getParticipants()));

         Picasso.get().load("http://www.wartournament.com/Content/SliderImage/"+currentitem.getImage()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                holder.topImage.setBackground(new BitmapDrawable(bitmap));
            }
            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });

         holder.cardView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent i = new Intent(mcontext,GameDetailsActivity.class);
                 i.putExtra("title",currentitem.getTitle());
                 i.putExtra("type",currentitem.getEntry_type());
                 i.putExtra("version",currentitem.getVersion());
                 i.putExtra("map",currentitem.getMap());
                 i.putExtra("matchtype",currentitem.getMatchType2());
                 i.putExtra("fee",currentitem.getEntry_fee());
                 i.putExtra("startdate",currentitem.getTime());
                 i.putExtra("PerKill",currentitem.getPer_kill());
                 i.putExtra("winner",currentitem.getWinner());
                 i.putExtra("Runnner1",currentitem.getRunnner1());
                 i.putExtra("Runnner2",currentitem.getRunner2());
                 i.putExtra("totalprize",currentitem.getTotalprize());
                 i.putExtra("aboutmatch",currentitem.getMatch_desc());
                 i.putExtra("percoin",currentitem.getCoin());
                 i.putExtra("img",currentitem.getImage());
                 mcontext.startActivity(i);
             }
         });

        holder.prizePoolLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showmydailog(currentitem.getCoin(),currentitem.getWinner(),currentitem.getRunnner1(),currentitem.getRunner2(),currentitem.getPer_kill(),currentitem.getTitle());
            }
        });

       holder.joinBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              showjoindailog(currentitem.getId());
           }
       });


    }
    @Override
    public int getItemCount() {
        return emExampleList.size();
    }

    private void showmydailog(String coin, String winner, String runnner1, String runner2, String per_kill,String title) {
        LayoutInflater factory = LayoutInflater.from(mcontext);
        final View package_detail_dialog = factory.inflate(R.layout.bottom_sheet_prizepool, null);
        final android.app.AlertDialog detailsDialog = new android.app.AlertDialog.Builder(mcontext).create();
        detailsDialog.setView(package_detail_dialog);
        detailsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        detailsDialog.show();
        TextView prizePoolText = package_detail_dialog.findViewById(R.id.prizePoolText);
        prizePoolText.setText("1 coin = "+coin+" Rupees\n\n"+"Winner - "+
                winner+" PlayCoins\nRunner Up 1 - "+
                runnner1+" PlayCoins\nRunner Up 2 - "
                +runner2+" PlayCoins\nPerKill - "+
                per_kill+" PlayCoins");
        TextView tite = package_detail_dialog.findViewById(R.id.matchID);
        tite.setText(""+title);
        ImageView closeBtn = package_detail_dialog.findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            detailsDialog.dismiss();
            }
        });



    }

    private void showjoindailog(String id) {
        LayoutInflater factory = LayoutInflater.from(mcontext);
        final View package_detail_dialog = factory.inflate(R.layout.dialog_joinprompt_solo, null);
        final android.app.AlertDialog detailsDialog = new android.app.AlertDialog.Builder(mcontext).create();
        detailsDialog.setView(package_detail_dialog);
        detailsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final EditText gamename1 = package_detail_dialog.findViewById(R.id.et_game_name1);
        final EditText charcter_id1 = package_detail_dialog.findViewById(R.id.et_charcter_id1);
        Button next = package_detail_dialog.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gamename1.getText().toString().isEmpty()){
                    Toast.makeText(mcontext, "You can't left any field Empty", Toast.LENGTH_SHORT).show();
                }
                else if(charcter_id1.getText().toString().isEmpty()){
                    Toast.makeText(mcontext, "You can't left any field Empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    SharedPreferences sharedPreferences = mcontext.getSharedPreferences("logininfo",Context.MODE_PRIVATE);
                    String  username  = sharedPreferences.getString("UserName","");
                    Toast.makeText(mcontext, ""+charcter_id1.getText().toString()+"    "+gamename1.getText().toString()+"    "+username, Toast.LENGTH_SHORT).show();
                }

            }
        });
        detailsDialog.show();
    }
}
