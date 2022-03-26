package co.tournam.ui.list;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import co.tournam.models.TeamModel;

import co.tournam.schedule.R;

public class AdapterTeam extends RecyclerView.Adapter<AdapterTeam.MyHolder>{

    Context context;
    FirebaseAuth firebaseAuth;
    String tName;

    public AdapterTeam(Context context, List<TeamModel> list) {
        this.context = context;
        this.list = list;
        firebaseAuth = FirebaseAuth.getInstance();
        tName = firebaseAuth.getUid();
    }

    List<TeamModel> list;

    @NonNull
    @Override
    public AdapterTeam.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_team, parent, false);
        return new AdapterTeam.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTeam.MyHolder holder, final int position) {
        Bitmap teamImage = list.get(position).getImage();
        String teamName = list.get(position).getName();
        holder.name.setText(teamName);
        try {
            Glide.with(context).load(teamImage).into(holder.profiletv);
        } catch (Exception e) {
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        ImageView profiletv;
        TextView name;
        Button remove;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            profiletv = itemView.findViewById(R.id.imaget);
            name = itemView.findViewById(R.id.namet);
            remove = itemView.findViewById(R.id.button2);

        }
    }
}

