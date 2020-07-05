package com.grupo13.parqueo.comments;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import com.bumptech.glide.Glide;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_comments, parent,
                false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        //Modificar para la obtencion de fotos de perfil.
        Glide.with(context).asBitmap().load(nImages.get(position)).into(holder.image);
        holder.imageName.setText(nImagesNames.get(position));
        holder.imageComment.setText(nComments.get(position));

      /*  holder.parentLayout.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Log.d(TAG, "onClick: clicked on" + nImagesNames.get(position));
                Toast.makeText(context, nImagesNames.get(position), Toast.LENGTH_SHORT).show();
            }
        });

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on" + nComments.get(position));
                Toast.makeText(context, nComments.get(position), Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return nComments.size();
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
