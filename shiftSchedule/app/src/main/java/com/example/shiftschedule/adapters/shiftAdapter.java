package com.example.shiftschedule.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shiftschedule.R;
import com.example.shiftschedule.employeeDetails;
import com.example.shiftschedule.listItem;
import com.example.shiftschedule.viewShiftDetails;

import java.util.List;

public class shiftAdapter extends RecyclerView.Adapter<shiftAdapter.ViewHolder> {
    /*
    shiftAdapter class created by Alex Creencia
    this adapter utilizes a RecyclerView to list shift objects to a RecyclerView (using listItem code + xml as a base display) and sets onClickListeners to each. Determines which object was clicked based on position (int) in the list.

     */
    private Context context;
    private List<listItem> itemList;

    public shiftAdapter(Context context, List listItem) {
        this.context = context;
        this.itemList = listItem;
    }

    @NonNull
    @Override
    public shiftAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull shiftAdapter.ViewHolder holder, int position) {
        // position keeps track of where we are in the list. this is like Pagination
        listItem item = itemList.get(position);

        holder.name.setText(item.getName());
        holder.description.setText(item.getDescription());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView name;
        public TextView description;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = itemView.findViewById(R.id.LRtitle);
            description = itemView.findViewById(R.id.LRdescription);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            listItem item = itemList.get(position);
            Intent intent = new Intent((context), viewShiftDetails.class);
            intent.putExtra("shift_id", item.getName());
            intent.putExtra("dayOfWeek", item.getDescription());
            context.startActivity(intent);

        }
    }
}

