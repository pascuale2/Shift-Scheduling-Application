package com.example.shiftschedule;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class employeeAdapter extends RecyclerView.Adapter<employeeAdapter.ViewHolder> {

    private Context context;
    private List<listItem> itemList;
    public employeeAdapter(Context context, List listItem) {
        this.context = context;
        this.itemList = listItem;
    }

    // This creates an employee adapter. We attach/inflate it to the format of our list_row, which is a relative layout
    @NonNull
    @Override
    public employeeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new ViewHolder(view);
    }

    // as we scroll through the RecycleView, we replace non visible Employees with new Employees instead of having to create TextViews for all of them
    // This saves memory and efficiency, and this is exactly what onBindViewHolder does. Loads the data into the same slots when needed.
    @Override
    public void onBindViewHolder(@NonNull employeeAdapter.ViewHolder holder, int position) {
        // position keeps track of where we are in the list. this is like Pagination
        listItem item = itemList.get(position);

        holder.name.setText(item.getName());
        holder.description.setText(item.getDescription());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // Describes an item view and meta data about the item within the Recycle View (this describes the item being displayed in Recycle view)
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
            // Get position of the row/item clicked to know which one was clicked.
            int position = getAdapterPosition();
            listItem item = itemList.get(position);
            Intent intent = new Intent((context), employeeDetails.class);
            intent.putExtra("email", item.getName());
            intent.putExtra("closing", item.getClosing());
            intent.putExtra("opening", item.getOpening());
            Log.d("Before Details", "before Starting Details");
            context.startActivity(intent);
            //Toast.makeText(context, item.getName(), Toast.LENGTH_LONG).show();

        }
    }
}
