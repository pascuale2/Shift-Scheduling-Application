package com.example.shiftschedule.bottomLayouts;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.shiftschedule.Available.Available;
import com.example.shiftschedule.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class availabilityBottomSheetDialog extends BottomSheetDialogFragment {
    private BottomSheetListener mListener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_layout, container, false);

        Bundle mArgs = getArguments();
        final String day = mArgs.getString("day");
        //Toast.makeText(getContext(), day, Toast.LENGTH_LONG).show();
        RadioButton canOpen = view.findViewById(R.id.openRadioButton);
        RadioButton canClose = view.findViewById(R.id.closingRadioButton);
        RadioButton canAllday = view.findViewById(R.id.alldayRadioButton);
        RadioButton cannot = view.findViewById(R.id.cannotRadioButton);
        //final Toast toast = Toast.makeText(getContext(), "Availability Saved", Toast.LENGTH_SHORT);
        canOpen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mListener.onRadioButtonClicked(Available.OPENING, day);
                //toast.show();
                dismiss();
            }
        });
        canClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onRadioButtonClicked(Available.CLOSING, day);
                //toast.show();
                dismiss();
            }
        });
        canAllday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onRadioButtonClicked(Available.ALLDAY, day);
                dismiss();
            }
        });
        cannot.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mListener.onRadioButtonClicked(Available.CANNOT, day);
                dismiss();
            }
        });
        return view;
    }
    public interface BottomSheetListener {
        void onRadioButtonClicked(Available available, String day);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
            + " must implement BottomSheetListener");
        }
    }
}
