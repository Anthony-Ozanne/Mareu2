package com.openclassrooms.mareu.ui.mareu;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.di.DI;
import com.openclassrooms.mareu.events.DeleteMeetingEvent;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.Salle;
import com.openclassrooms.mareu.service.DummySalleGenerator;
import com.openclassrooms.mareu.service.MeetingApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListMeetingActivity extends AppCompatActivity implements CustomDialogDate.DialogDatePickerListener, CustomDialogSalle.DialogFilterSalleListener {

    // UI Component

    @BindView(R.id.recyclerView_list_meeting)
    RecyclerView mRecyclerView;
/*    @BindView(R.id.spinner_salle)
    Spinner spinner_salle;*/


    private MeetingApiService mApiService;
    private List<Meeting> mMeetings;
    private CustomDialogDate dialogDateFragment;
    private CustomDialogSalle dialogSalleFragment;


    /**
     * Permet d'initialiser la vue de la liste des réunions
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_meeting);

        ButterKnife.bind(this);
        //1 - Configuration de la Toolbar
        this.configureToolbar();

        mApiService = DI.getMeetingApiService();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //2 - Agrandissez le menu et ajoutez-le à la toolbar
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }


    private void configureToolbar(){
        // Obtenir la vue de la toolbar dans l'activité
        Toolbar toolbar = findViewById(R.id.activity_main_toolbar);
        // Définit la toolbar
        setSupportActionBar(toolbar);
    }
    // Activity's overrided method used to set the menu file
  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }*/
    // Activity's overrided method used to perform click events on menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
// Handle action bar item clicks here. The action bar will
// automatically handle clicks on the Home/Up button, so long
// as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//noinspection SimplifiableIfStatement
// Display menu item's title by using a Toast.
       /* if (id == R.id.menu_activity_main_search) {
            Toast.makeText(getApplicationContext(), "filtre..", Toast.LENGTH_SHORT).show();
            return true;
        } else*/
        if (id == R.id.filtreByDate) {
            Toast.makeText(getApplicationContext(), "filtre par date", Toast.LENGTH_SHORT).show();
            loadFilterPerDate();

            return true;

        } else if (id == R.id.filtreBySalle) {
            Toast.makeText(getApplicationContext(), "filtre par salle", Toast.LENGTH_SHORT).show();
            loadFilterPerSalle();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadFilterPerDate(){
        //1/ appeler un dialog qui contient un picker date
        dialogDateFragment = new CustomDialogDate();
        Bundle args = new Bundle();
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        dialogDateFragment.setPickerDateListener(ListMeetingActivity.this);
        // Show:
        dialogDateFragment.show(fragmentManager, "Dialog");

        //2/ choisir la date
        //3/ toast: afficher la date selectionnée
    }

    public void loadFilterPerSalle(){
        //1/ appeler un dialog qui contient un spinner de la liste salle
        dialogSalleFragment = new CustomDialogSalle();
        Bundle args = new Bundle();
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        dialogSalleFragment.setFilterSalleListener(ListMeetingActivity.this);
        // Show:
        dialogSalleFragment.show(fragmentManager, "Dialog");
        //2/ choisir la salle
        //3/ toast: afficher la salle selectionnée
    }

    /**
     * Bouton d'ajout de réunion
     */
    @OnClick(R.id.add_meeting)
    void addMeeting() {
        AddMeetingActivity.navigate(this);
    }

    /**
     * Init the List of meetings
     */
    private void initList() {
        mMeetings = mApiService.getMeetings();
        mRecyclerView.setAdapter(new MyMeetingRecyclerViewAdapter(mMeetings));


    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /**
     * Lancé si l'utilisateur clique sur le bouton de suppression
     * @param event
     */
    @Subscribe
    public void onDeleteMeeting(DeleteMeetingEvent event) {
        mApiService.deleteMeeting(event.meeting);
        initList();
    }

    @Override
    public void onDialogDateValidateClick(CustomDialogDate picker) {

        DatePicker datePicker = (DatePicker) dialogDateFragment.getDialog().findViewById(R.id.selected_date);
        int mDay = datePicker.getDayOfMonth();
        int mMonth = datePicker.getMonth()+1;
        int mYear = datePicker.getYear();

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat(pattern, new Locale("fr", "FR"));

        Date selectedDate ;
    try {
         selectedDate = simpleDateFormat.parse(mYear + "-" + mMonth + "-" + mDay);
    } catch(ParseException ex){
        ex.printStackTrace();
        selectedDate = new Date();
    }
        mMeetings = mApiService.getMeetings();
        List <Meeting> result = new ArrayList<Meeting>();
        for (Meeting m : mMeetings) {
            System.out.println(simpleDateFormat.format(m.getTime()));
            System.out.println(simpleDateFormat.format(selectedDate));
            if (simpleDateFormat.format(m.getTime()).equals(simpleDateFormat.format(selectedDate)))
                result.add(m);
        }

        mRecyclerView.setAdapter(new MyMeetingRecyclerViewAdapter(result));

    }

    @Override
    public void onDialogSalleValidateClick(CustomDialogSalle customDialogSalle) {

        Spinner spinner_salle = (Spinner) customDialogSalle.getDialog().findViewById(R.id.spinner_salle_filter);

        Salle salle = (Salle) spinner_salle.getSelectedItem();

        mMeetings = mApiService.getMeetings();
        List <Meeting> result = new ArrayList<Meeting>();
        for (Meeting m : mMeetings) {
            if (m.getSalle().equals(salle))
                result.add(m);
        }

        mRecyclerView.setAdapter(new MyMeetingRecyclerViewAdapter(result));

    }
}
