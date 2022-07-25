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
import android.view.LayoutInflater;

import com.openclassrooms.mareu.R;

public class AddParticipant extends DialogFragment{

    public interface DialogAddParticipantListener {

        void onDialogParticipantValidateClick(AddParticipant view);
    }

    private DialogAddParticipantListener addParticipantListener;

    public DialogAddParticipantListener getAddParticipantListener() {
        return addParticipantListener;
    }

    public void setAddParticipantListener(DialogAddParticipantListener addParticipantListener) {
        this.addParticipantListener = addParticipantListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.edit_text_participant,null))
                .setMessage("Sélectionner un participant")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addParticipantListener.onDialogParticipantValidateClick (AddParticipant.this);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AddParticipant.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

}
