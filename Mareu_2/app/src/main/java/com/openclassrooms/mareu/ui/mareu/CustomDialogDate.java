package com.openclassrooms.mareu.ui.mareu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;

import com.openclassrooms.mareu.R;

public class CustomDialogDate extends DialogFragment {

    public interface DialogDatePickerListener {

        public void onDialogDateValidateClick(CustomDialogDate picker);
    }

    private DialogDatePickerListener pickerDateListener;

    public DialogDatePickerListener getPickerDateListener() { return pickerDateListener; }

    public void setPickerDateListener(DialogDatePickerListener pickerDateListener) {
        this.pickerDateListener = pickerDateListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.date_picker,null))
                .setMessage("Date et heure de la réunion")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        pickerDateListener.onDialogDateValidateClick(CustomDialogDate.this);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CustomDialogDate.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
