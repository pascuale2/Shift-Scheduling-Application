package com.example.shiftschedule.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shiftschedule.R;
import com.example.shiftschedule.employee.Employee;
import com.example.shiftschedule.employee.screens.employeeDetails;
import com.example.shiftschedule.employee.screens.employeePage;
import com.example.shiftschedule.listItem;
import com.example.shiftschedule.shiftScreens.viewShiftDetails;
import com.example.shiftschedule.shifts.WeekdayShifts;
import com.example.shiftschedule.shifts.WeekendShifts;
import com.google.gson.Gson;

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
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            name = itemView.findViewById(R.id.LRtitle);
            description = itemView.findViewById(R.id.LRdescription);
        }

        protected int checkShiftType(String json) {
            String[] types = {"Holiday", "Weekend", "Weekday"};
            int i;
            for (i = 0; i < types.length; i++) {
                if (json.contains(types[i])) break;
            }
            switch (i) {
                case 0:
                    return 0;
                case 1:
                    return 1;
                default:
                    return 2;
            }
        }

        // Function method for handling adding employees.

        @Override
        public void onClick(View v) {
            // Get position of the row/item clicked to know which one was clicked.
            int position = getAdapterPosition();
            listItem item = itemList.get(position);
            if (context instanceof employeePage) {
                //Toast.makeText(context, "This is selected from employeePage", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent((context), employeeDetails.class);
                intent.putExtra("email", item.getName());
                intent.putExtra("closing", item.getClosing());
                intent.putExtra("opening", item.getOpening());
                Log.d("Before Details", "before Starting Details");
                context.startActivity(intent);
                ((employeePage) context).finish();
            } else if (context instanceof viewShiftDetails) {
                //notifyDataSetChanged();
                // Perhaps delete employee if they click on it from the shift.
                SharedPreferences shiftStorage = context.getSharedPreferences("shifts", Context.MODE_PRIVATE);
                ;
                SharedPreferences employeeStorage = context.getSharedPreferences("employeeStorage", Context.MODE_PRIVATE);
                String employeeJson = employeeStorage.getString(item.getName(), "");
                SharedPreferences.Editor shiftEditor = shiftStorage.edit();
                Gson gson = new Gson();
                Employee employee = gson.fromJson(employeeJson, Employee.class);
                String shiftJson = shiftStorage.getString(item.getHiddenInfo(), "");
                int checkFlag = checkShiftType(shiftJson);
                if (checkFlag == 2) {
                    WeekdayShifts weekday = gson.fromJson(shiftJson, WeekdayShifts.class);
                    weekday.getEmployeeList().remove(employee);
                    shiftJson = gson.toJson(weekday);
                } else {
                    WeekendShifts weekend = gson.fromJson(shiftJson, WeekendShifts.class);
                    weekend.getEmployeeList().remove(employee);
                    shiftJson = gson.toJson(weekend);
                    //notifyDataSetChanged();
                }
                shiftEditor.putString(item.getHiddenInfo(), shiftJson);
                shiftEditor.commit();
                itemList.remove(getAdapterPosition());
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getItemCount());
            } else {
                // this is for shiftAddEmployees

                SharedPreferences shiftStorage = context.getSharedPreferences("shifts", Context.MODE_PRIVATE);
                ;
                SharedPreferences employeeStorage = context.getSharedPreferences("employeeStorage", Context.MODE_PRIVATE);
                SharedPreferences.Editor employeeEditor = employeeStorage.edit();
                SharedPreferences.Editor shiftEditor = shiftStorage.edit();
                Gson gson = new Gson();
                // I don't have the shift_id in this context.
                String shiftJson = shiftStorage.getString(item.getHiddenInfo(), "");
                String employeeJson = employeeStorage.getString(item.getName(), "");
                Employee employee = gson.fromJson(employeeJson, Employee.class);

                int checkFlag = checkShiftType(shiftJson);
                //TODO: Need to verify whether there are enough people ( I set a toast message, you can decide whether thats enough or not.
                // Also need to check if at least one person is trained to work that shift (if AllDay then we need someone trained for both opening and closing)
                if (checkFlag == 2) {
                    WeekdayShifts weekday = gson.fromJson(shiftJson, WeekdayShifts.class);
                    String shift_time_block = weekday.getShiftID().split("-")[1];
                    Log.i("ShiftDuplicate", "This is the EmployeeList before adding again" + weekday.getEmployeeList().toString());
                    Log.i("containsCheck", String.valueOf(weekday.getEmployeeList().contains(employee)));

                    if (weekday.getEmployeeList().size() == 0) {

                        if (employee.isTrainedOpening() && (shift_time_block.matches("OPENING"))) {

                            System.out.println("MASSOCHISM");

                            weekday.addEmployee(employee, context);

                            // also need to save shift changes.

                            //Toast.makeText(context, "Adding " + item.getName() + " to work this shift.", Toast.LENGTH_SHORT).show();
                            Log.i("ShiftEmployee", "This is the shift employeeList: " + weekday.getEmployeeList().toString());
                            // saving changes to file
                            shiftJson = gson.toJson(weekday);
                            shiftEditor.putString(item.getHiddenInfo(), shiftJson);
                            shiftEditor.commit();
                            itemList.remove(getAdapterPosition());
                            notifyDataSetChanged();

                        }

                        if ((employee.isTrainedClosing() && (shift_time_block.matches("CLOSING")))) {
                            System.out.println("MASSOCHISM2");

                            weekday.addEmployee(employee, context);

                            // also need to save shift changes.

                            //Toast.makeText(context, "Adding " + item.getName() + " to work this shift.", Toast.LENGTH_SHORT).show();
                            Log.i("ShiftEmployee", "This is the shift employeeList: " + weekday.getEmployeeList().toString());
                            // saving changes to file
                            shiftJson = gson.toJson(weekday);
                            shiftEditor.putString(item.getHiddenInfo(), shiftJson);
                            shiftEditor.commit();
                            itemList.remove(getAdapterPosition());
                            notifyDataSetChanged();
                        }

                        if (shift_time_block.matches("ALLDAY")) {
                            System.out.println("MASSOCHISM3");

                            boolean Open = false;
                            boolean Close = false;
                            if ((employee.isTrainedClosing())) {
                                Open = true;
                            }

                            if (employee.isTrainedOpening()) {
                                Close = true;
                            }

                            if (Open && Close) {
                                weekday.addEmployee(employee, context);

                                // also need to save shift changes.

                                //Toast.makeText(context, "Adding " + item.getName() + " to work this shift.", Toast.LENGTH_SHORT).show();
                                Log.i("ShiftEmployee", "This is the shift employeeList: " + weekday.getEmployeeList().toString());
                                // saving changes to file
                                shiftJson = gson.toJson(weekday);
                                shiftEditor.putString(item.getHiddenInfo(), shiftJson);
                                shiftEditor.commit();
                                itemList.remove(getAdapterPosition());
                                notifyDataSetChanged();
                            }

                            else if (Open || Close){
                                weekday.addEmployee(employee, context);

                                // also need to save shift changes.

                                //Toast.makeText(context, "Adding " + item.getName() + " to work this shift.", Toast.LENGTH_SHORT).show();
                                Log.i("ShiftEmployee", "This is the shift employeeList: " + weekday.getEmployeeList().toString());
                                // saving changes to file
                                shiftJson = gson.toJson(weekday);
                                shiftEditor.putString(item.getHiddenInfo(), shiftJson);
                                shiftEditor.commit();
                                itemList.remove(getAdapterPosition());
                                notifyDataSetChanged();
                            }
                        }
                        }

                    else{

                        boolean close = false;
                        boolean open = false;

                        if (weekday.getEmployeeList().get(0).isTrainedClosing()) {
                            close = true;
                        }

                        if (weekday.getEmployeeList().get(0).isTrainedOpening()) {
                            open = true;
                        }

                        if (!close && employee.isTrainedClosing()) {
                            weekday.addEmployee(employee, context);
                            // also need to save shift changes.
                            //Toast.makeText(context, "Adding " + item.getName() + " to work this shift.", Toast.LENGTH_SHORT).show();
                            Log.i("ShiftEmployee", "This is the shift employeeList: " + weekday.getEmployeeList().toString());
                            // saving changes to file
                            shiftJson = gson.toJson(weekday);
                            shiftEditor.putString(item.getHiddenInfo(), shiftJson);
                            shiftEditor.commit();
                            itemList.remove(getAdapterPosition());
                            notifyDataSetChanged();
                        }

                        if (!open && employee.isTrainedOpening()) {
                            weekday.addEmployee(employee, context);
                            // also need to save shift changes.
                            //Toast.makeText(context, "Adding " + item.getName() + " to work this shift.", Toast.LENGTH_SHORT).show();
                            Log.i("ShiftEmployee", "This is the shift employeeList: " + weekday.getEmployeeList().toString());
                            // saving changes to file
                            shiftJson = gson.toJson(weekday);
                            shiftEditor.putString(item.getHiddenInfo(), shiftJson);
                            shiftEditor.commit();
                            itemList.remove(getAdapterPosition());
                            notifyDataSetChanged();
                        }

                        if (open && close) {
                            weekday.addEmployee(employee, context);
                            // also need to save shift changes.
                            //Toast.makeText(context, "Adding " + item.getName() + " to work this shift.", Toast.LENGTH_SHORT).show();
                            Log.i("ShiftEmployee", "This is the shift employeeList: " + weekday.getEmployeeList().toString());
                            // saving changes to file
                            shiftJson = gson.toJson(weekday);
                            shiftEditor.putString(item.getHiddenInfo(), shiftJson);
                            shiftEditor.commit();
                            itemList.remove(getAdapterPosition());
                            notifyDataSetChanged();
                        }

                    }

                    }
                     else {


                        WeekendShifts weekend = gson.fromJson(shiftJson, WeekendShifts.class);
                        System.out.println("MASSOCHISM3");

                        if (weekend.getEmployeeList().size() == 0) {

                            if ((employee.isTrainedClosing()) && employee.isTrainedOpening()) {
                                weekend.addEmployee(employee, context);
                                // also need to save shift changes.
                                //Toast.makeText(context, "Adding " + item.getName() + " to work this shift.", Toast.LENGTH_SHORT).show();
                                Log.i("ShiftEmployee", "This is the shift employeeList: " + weekend.getEmployeeList().toString());
                                // saving changes to file
                                shiftJson = gson.toJson(weekend);
                                shiftEditor.putString(item.getHiddenInfo(), shiftJson);
                                shiftEditor.commit();
                                itemList.remove(getAdapterPosition());
                                notifyDataSetChanged();
                            } else if (employee.isTrainedOpening() || employee.isTrainedClosing()) {
                                weekend.addEmployee(employee, context);
                                // also need to save shift changes.
                                //Toast.makeText(context, "Adding " + item.getName() + " to work this shift.", Toast.LENGTH_SHORT).show();
                                Log.i("ShiftEmployee", "This is the shift employeeList: " + weekend.getEmployeeList().toString());
                                // saving changes to file
                                shiftJson = gson.toJson(weekend);
                                shiftEditor.putString(item.getHiddenInfo(), shiftJson);
                                shiftEditor.commit();
                                itemList.remove(getAdapterPosition());
                                notifyDataSetChanged();
                            }

                        } else {
                            for (int i = 0; i < weekend.getEmployeeList().size(); i++) {
                                boolean close = false;
                                boolean open = false;

                                if (weekend.getEmployeeList().get(i).isTrainedClosing()) {
                                    close = true;
                                }

                                if (weekend.getEmployeeList().get(i).isTrainedOpening()) {
                                    open = true;
                                }

                                if (!close && employee.isTrainedClosing()) {
                                    weekend.addEmployee(employee, context);
                                    // also need to save shift changes.
                                    //Toast.makeText(context, "Adding " + item.getName() + " to work this shift.", Toast.LENGTH_SHORT).show();
                                    Log.i("ShiftEmployee", "This is the shift employeeList: " + weekend.getEmployeeList().toString());
                                    // saving changes to file
                                    shiftJson = gson.toJson(weekend);
                                    shiftEditor.putString(item.getHiddenInfo(), shiftJson);
                                    shiftEditor.commit();
                                    itemList.remove(getAdapterPosition());
                                    notifyDataSetChanged();
                                }

                                if (!open && employee.isTrainedOpening()) {
                                    weekend.addEmployee(employee, context);
                                    // also need to save shift changes.
                                    //Toast.makeText(context, "Adding " + item.getName() + " to work this shift.", Toast.LENGTH_SHORT).show();
                                    Log.i("ShiftEmployee", "This is the shift employeeList: " + weekend.getEmployeeList().toString());
                                    // saving changes to file
                                    shiftJson = gson.toJson(weekend);
                                    shiftEditor.putString(item.getHiddenInfo(), shiftJson);
                                    shiftEditor.commit();
                                    itemList.remove(getAdapterPosition());
                                    notifyDataSetChanged();
                                }

                                if (open && close) {
                                    weekend.addEmployee(employee, context);
                                    // also need to save shift changes.
                                    //Toast.makeText(context, "Adding " + item.getName() + " to work this shift.", Toast.LENGTH_SHORT).show();
                                    Log.i("ShiftEmployee", "This is the shift employeeList: " + weekend.getEmployeeList().toString());
                                    // saving changes to file
                                    shiftJson = gson.toJson(weekend);
                                    shiftEditor.putString(item.getHiddenInfo(), shiftJson);
                                    shiftEditor.commit();
                                    itemList.remove(getAdapterPosition());
                                    notifyDataSetChanged();
                                }


                            }
                        }

                    }

                }
            }
        }
    }



