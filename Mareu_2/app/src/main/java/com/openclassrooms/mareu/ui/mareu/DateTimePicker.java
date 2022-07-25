package com.openclassrooms.mareu.ui.mareu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.SupportActivity;
import android.view.LayoutInflater;

import com.openclassrooms.mareu.R;

public class DateTimePicker extends DialogFragment {

    public interface DialogDateTimePickerListener {

        public void onDialogValidateClick(DateTimePicker picker);
    }

     private DialogDateTimePickerListener pickerListener;

    public DialogDateTimePickerListener getPickerListener() {
        return pickerListener;
    }

    public void setPickerListener(DialogDateTimePickerListener pickerListener) {
        this.pickerListener = pickerListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.date_time_picker,null))
                .setMessage("Date et heure de la réunion")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        pickerListener.onDialogValidateClick(DateTimePicker.this);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DateTimePicker.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

}
