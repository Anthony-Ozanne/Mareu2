package com.openclassrooms.mareu.model;


import java.util.Date;
import java.util.List;

/**
 * Model object representing a Meeting
 */

public class Meeting {

    /** heure de la réunion */
    private Date time;

    /** Sujet de la réunion */
    private String subject;

    /** Lieu de la réunion */
    private Salle salle; // Salle salle

    /** liste des participants */
    private List<String> emails; // list des email des participants




    /**
     * Constructor
     * @param salle
     * @param subject
     * @param emails
     * @param time
     */
    public Meeting(Salle salle, String subject,  List<String> emails, Date time) {
        this.salle = salle;
        this.subject = subject;
        this.emails = emails;
        this.time = time;
    }




    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }



    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public List<String> getEmails() { return emails; }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }
}

