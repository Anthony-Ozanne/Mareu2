package com.openclassrooms.mareu.ui.mareu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.model.Salle;
import com.openclassrooms.mareu.service.DummySalleGenerator;

public class CustomDialogSalle extends DialogFragment {

    public interface DialogFilterSalleListener {

        public void onDialogSalleValidateClick(CustomDialogSalle spinner);
    }

    private DialogFilterSalleListener filterSalleListener;

    public DialogFilterSalleListener getFilterSalleListener() { return filterSalleListener; }

    public void setFilterSalleListener(DialogFilterSalleListener filterSalleListener) {
        this.filterSalleListener = filterSalleListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View contentDialog = inflater.inflate(R.layout.salle_filter,null);
        CustomAdapter adapter = new CustomAdapter(this.getActivity(),
                R.layout.spinner_item_layout_resource,
                R.id.name,
                R.id.color,
                DummySalleGenerator.DUMMY_SALLES);
        Spinner spinner_salle = contentDialog.findViewById(R.id.spinner_salle_filter);
        spinner_salle.setAdapter(adapter);
        spinner_salle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Salle salle = DummySalleGenerator.DUMMY_SALLES.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        builder.setView(contentDialog)
                .setMessage("Salle de la réunion")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        filterSalleListener.onDialogSalleValidateClick(CustomDialogSalle.this);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CustomDialogSalle.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

}



