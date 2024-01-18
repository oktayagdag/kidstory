package dev.akdag.kidsstory;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StoryListAdapter extends RecyclerView.Adapter<StoryListAdapter.MyViewHolder> implements Filterable {
    private ArrayList<StoryList> stories;
    private ArrayList<StoryList> storiessFull;
    Activity activity;


    public StoryListAdapter(ArrayList<StoryList> stories , Activity activity) {
        this.stories = stories;
        this.activity= activity;
        storiessFull=new ArrayList<>(stories);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.story_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  MyViewHolder holder, @SuppressLint("RecyclerView") int i) {

        int ig= stories.get(i).getImageResource();
        int color=stories.get(i).getColorResource();
        String storyNames=stories.get(i).getStoryName();

        holder.storyName.setText(storyNames);
        holder.rowLayout.setBackgroundResource(color);
        holder.storyImage.setImageResource(ig);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addClick(activity);
                int count = getClicksCount(activity);
                if (count%3==0){
                    if (Admob.mInterstitialAd!=null){
                        Admob.mInterstitialAd.show(activity);
                        Admob.mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                super.onAdDismissedFullScreenContent();
                                Admob.mInterstitialAd =null;
                                Admob.loadAd(activity);

                                holder.itemView.setClickable(false);
                                Intent intent = new Intent(activity, ListenStory.class);
                                intent.putExtra("subject",stories.get(i).getId());
                                intent.putExtra("sarkiadi",stories.get(i).getStoryName());
                                activity.startActivity(intent);
                                holder.itemView.setClickable(true);
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                super.onAdFailedToShowFullScreenContent(adError);
                                Admob.mInterstitialAd =null;
                                Admob.loadAd(activity);

                                holder.itemView.setClickable(false);
                                Intent intent = new Intent(activity, ListenStory.class);
                                intent.putExtra("subject",stories.get(i).getId());
                                intent.putExtra("sarkiadi",stories.get(i).getStoryName());
                                activity.startActivity(intent);
                                holder.itemView.setClickable(true);

                            }
                        });
                    }else {
                        holder.itemView.setClickable(false);
                        Intent intent = new Intent(activity, ListenStory.class);
                        intent.putExtra("subject",stories.get(i).getId());
                        intent.putExtra("sarkiadi",stories.get(i).getStoryName());
                        activity.startActivity(intent);
                        holder.itemView.setClickable(true);

                    }
                }
                else{
                    holder.itemView.setClickable(false);
                    Intent intent = new Intent(activity, ListenStory.class);
                    intent.putExtra("subject",stories.get(i).getId());
                    intent.putExtra("sarkiadi",stories.get(i).getStoryName());
                    activity.startActivity(intent);
                    holder.itemView.setClickable(true);

                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    public static class  MyViewHolder extends  RecyclerView.ViewHolder {
        TextView storyName;
        ImageView storyImage;
        LinearLayout rowLayout;
        public  MyViewHolder (@NonNull View itemView) {
            super(itemView);
            storyImage=itemView.findViewById(R.id.storyImage);
            storyName=itemView.findViewById(R.id.storyName);
            rowLayout=itemView.findViewById(R.id.rowLayout);
        }
    }

    @Override
    public Filter getFilter(){
        return musicsFilter;
    }

    private final Filter musicsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<StoryList> filteredList= new ArrayList<>();
            if (constraint == null || constraint.length() ==0){
                filteredList.addAll(storiessFull);
            }else{
                String filterPattern=constraint.toString().toLowerCase().trim();
                for (StoryList item: storiessFull){
                    if (item.getStoryName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results =  new FilterResults();
            results.values= filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            stories.clear();
            stories.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };

    private void addClick(Activity activity) {
        int click = getClicksCount(activity)+1;
        SharedPreferences preferences = activity.getSharedPreferences("click_count", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("count", click);
        editor.apply();
    }

    private int getClicksCount(Activity activity){
        SharedPreferences preferences = activity.getSharedPreferences("click_count",Context.MODE_PRIVATE);
        return preferences.getInt("count",0);
    }
}
