package com.grupo13.parqueo.comments;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import com.grupo13.parqueo.R;

import java.util.ArrayList;

public class    RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<String> nImagesNames = new ArrayList<>();
    private ArrayList<String> nImages = new ArrayList<>();
    private ArrayList<String> nComments = new ArrayList<>();
    private Context context;

    public RecyclerViewAdapter(Context context, ArrayList<String> nImagesNames, ArrayList<String> nImages, ArrayList<String> nComments) {
        this.nImagesNames = nImagesNames;
        this.nImages = nImages;
        this.nComments = nComments;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView image;
        TextView imageName, imageComment;
        RelativeLayout parentLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.comment_profile_image);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            imageName = itemView.findViewById(R.id.comment_username);
            imageComment = itemView.findViewById(R.id.comment);

        }
    }
}
