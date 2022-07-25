package com.openclassrooms.mareu.ui.mareu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.di.DI;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.Salle;
import com.openclassrooms.mareu.service.DummySalleGenerator;
import com.openclassrooms.mareu.service.MeetingApiService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddMeetingActivity extends AppCompatActivity implements DateTimePicker.DialogDateTimePickerListener,AddParticipant.DialogAddParticipantListener {

    @BindView(R.id.colorCircle)
    ImageView colorCircle;
    @BindView(R.id.spinner_salle)
    Spinner spinner_salle;
    @BindView(R.id.EditTextParticipant)
    TextView emails;
    @BindView(R.id.ViewDateHeure)
    TextView dateHeure;
    @BindView(R.id.buttonChoisir)
    Button buttonChoisir;
    @BindView(R.id.buttonAddParticipant)
    FloatingActionButton buttonAddParticipant;
    @BindView(R.id.EditTextSubject)
    EditText subject;
    @BindView(R.id.create)
    MaterialButton createButton;

    private MeetingApiService mApiService;
    private DialogFragment dialogFragment;
    private DialogFragment dialogFragmentParticipant;
    private List<String> participant;
    public String mColorCircle;
    private Date dateMeeting ;

    /**
     * Permet d'initialiser la vue d'ajout d'une réunion
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mApiService = DI.getMeetingApiService();
        init();

        buttonChoisir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonShowPickerDateTime();
            }
        });
        buttonAddParticipant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonShowAddParticipant();
            }
        });

    }

    private void buttonShowAddParticipant() {

        dialogFragmentParticipant = new AddParticipant();

        FragmentManager fragmentManager = this.getSupportFragmentManager();

        // Show:
        dialogFragmentParticipant.show(fragmentManager, "Dialog");

    }

    // User click on "Show Dialog" button.
    private void buttonShowPickerDateTime()  {

        // Create PickerDateTime DialogFragment
        dialogFragment = new DateTimePicker();
        Bundle args = new Bundle();
        FragmentManager fragmentManager = this.getSupportFragmentManager();

        // Show:
        dialogFragment.show(fragmentManager, "Dialog");
    }

    /**
     * @param item
     * @return item
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home : {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        mColorCircle = randomImage();
     participant = new ArrayList<String>();

        CustomAdapter adapter = new CustomAdapter(this,
                R.layout.spinner_item_layout_resource,
                R.id.name,
                R.id.color,
                DummySalleGenerator.DUMMY_SALLES);
        spinner_salle.setAdapter(adapter);
        spinner_salle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Salle salle = DummySalleGenerator.DUMMY_SALLES.get(position);
                colorCircle.getDrawable().setTint(Color.parseColor(salle.getCouleur()));


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     *  Permet de créer une réunion en cliquant sur le bouton
     */
    @OnClick(R.id.create)
    void addMeeting() {

        Salle salle = DummySalleGenerator.DUMMY_SALLES.get(spinner_salle.getSelectedItemPosition());
        System.out.println(salle.toString());
        String libSubject = subject.getText().toString();
        List<String> listEmails = participant;
        listEmails.add(emails.getText().toString());
        Date date = dateMeeting;

        Meeting meeting = new Meeting(salle,libSubject, listEmails,date);

        mApiService.createMeeting(meeting);
        finish();
    }

    /**
     * Generate a random image. Useful to mock circle color
     * @return String
     */
    String randomImage() { return String.valueOf((R.drawable.ic_color_circle)+ System.currentTimeMillis()); }

    /**
     * Used to navigate to this activity
     * @param activity
     */
    public static void navigate(FragmentActivity activity) {
        Intent intent = new Intent(activity, AddMeetingActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof DateTimePicker) {
            DateTimePicker dateTimePicker= (DateTimePicker) fragment;
            dateTimePicker.setPickerListener(this);
        }
        if (fragment instanceof AddParticipant) {
            AddParticipant addParticipant= (AddParticipant) fragment;
            addParticipant.setAddParticipantListener(this);
        }
    }

    @Override
    public void onDialogValidateClick(DateTimePicker picker) {


   DatePicker datePicker = (DatePicker) dialogFragment.getDialog().findViewById(R.id.date_picker);
    int mDay = datePicker.getDayOfMonth();
    int mMonth = datePicker.getMonth()+1;
    int mYear = datePicker.getYear();

    TimePicker timePicker = (TimePicker) dialogFragment.getDialog().findViewById(R.id.time_picker);
    int heure = timePicker.getCurrentHour();
    int minute = timePicker.getCurrentMinute();

       String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat(pattern, new Locale("fr", "FR"));
        try {
            dateMeeting = simpleDateFormat.parse(mYear + "-" + mMonth + "-" + mDay + " " + heure + ":" + minute + ":00");
        }catch(ParseException ex){
            ex.printStackTrace();;
            dateMeeting = new Date();
        }
        System.out.println(dateMeeting);


        Toast.makeText(this, simpleDateFormat.format(dateMeeting), Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onDialogParticipantValidateClick(AddParticipant view) {


        TextView text = (TextView) dialogFragmentParticipant.getDialog().findViewById(R.id.EditTextPopUp);
        emails.setText(text.getText().toString());
        participant.add(text.getText().toString());

    }


}
