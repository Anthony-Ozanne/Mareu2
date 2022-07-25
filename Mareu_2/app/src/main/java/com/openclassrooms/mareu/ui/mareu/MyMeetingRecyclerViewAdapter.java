package com.openclassrooms.mareu.ui.mareu;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.events.DeleteMeetingEvent;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.Salle;


import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyMeetingRecyclerViewAdapter extends RecyclerView.Adapter<MyMeetingRecyclerViewAdapter.ViewHolder> {

    private final List<Meeting> mMeetings;

    /**
     * Défini qu'une case correspond à une réunion
     * @param items  1 réunion correspond a une item
     */
    public MyMeetingRecyclerViewAdapter(List<Meeting> items) {
        mMeetings = items;
    }

    /**
     * Défini la vue où seront listés les réunions
     * @param parent
     * @param viewType
     * @return ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meeting_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Meeting meeting = mMeetings.get(position);
        holder.mMeetingName.setText(meeting.getSalle().getNom());
        holder.mCircleColor.getDrawable().setTint(Color.parseColor(meeting.getSalle().getCouleur()));

        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new DeleteMeetingEvent(meeting));
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            /**
             * Créé une activité et remplis les champs de celle-ci avec les données des réunions puis lance la vue
             * @param v   la vue
             */
            @Override
            public void onClick(View v) {
                // Creer une activity
                Intent intent = new Intent(v.getContext(), AddMeetingActivity.class);

                // Remplir les champs de la vue detail avec les données de la réunion.
                intent.putExtra("Salle", meeting.getSalle().getNom());
                intent.putExtra("Subject", meeting.getSubject());
                intent.putExtra("ColorCircle", meeting.getSalle().getCouleur());
                intent.putExtra("Participants", meeting.getEmails().getClass());
                intent.putExtra("Date and Time", meeting.getTime());

                // lancer la vue detail
                ContextCompat.startActivity(v.getContext(),intent,null);


            }
    });
    }


    /**
     * @return mMeetings size
     */
    @Override
    public int getItemCount() {
        return mMeetings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_color)
        public ImageView mCircleColor;
        @BindView(R.id.item_salle)
        public TextView mMeetingName;
        @BindView(R.id.item_delete_button)
        public ImageButton mDeleteButton;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
