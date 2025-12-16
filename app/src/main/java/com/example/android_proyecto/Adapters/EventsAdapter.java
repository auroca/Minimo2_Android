package com.example.android_proyecto.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android_proyecto.Models.Event;
import com.example.android_proyecto.R;

import java.util.ArrayList;
import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventViewHolder> {

    public interface OnRegisterClickListener {
        void onRegisterClick(Event event);
    }

    private final List<Event> events = new ArrayList<>();
    private final OnRegisterClickListener listener;

    public EventsAdapter(OnRegisterClickListener listener) {
        this.listener = listener;
    }

    public void setEvents(List<Event> newEvents) {
        events.clear();
        if (newEvents != null) events.addAll(newEvents);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event e = events.get(position);

        holder.tvName.setText(e.getName());
        holder.tvDesc.setText(e.getDescription());
        holder.tvDates.setText("Start: " + e.getStartDate() + " | End: " + e.getEndDate());

        android.util.Log.d("EVENTS", "imageUrl=" + e.getImageUrl());

        // imageUrl viene del backend (puede ser URL completa)
        Glide.with(holder.itemView.getContext())
                .load(e.getImageUrl())
                .placeholder(android.R.drawable.ic_menu_report_image)
                .error(android.R.drawable.stat_notify_error)
                .centerCrop()
                .into(holder.imgEvent);

        holder.btnRegister.setOnClickListener(v -> {
            if (listener != null) listener.onRegisterClick(e);
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {
        ImageView imgEvent;
        TextView tvName, tvDesc, tvDates;
        Button btnRegister;

        EventViewHolder(@NonNull View itemView) {
            super(itemView);
            imgEvent = itemView.findViewById(R.id.imgEvent);
            tvName = itemView.findViewById(R.id.tvEventName);
            tvDesc = itemView.findViewById(R.id.tvEventDesc);
            tvDates = itemView.findViewById(R.id.tvEventDates);
            btnRegister = itemView.findViewById(R.id.btnRegister);
        }
    }
}
