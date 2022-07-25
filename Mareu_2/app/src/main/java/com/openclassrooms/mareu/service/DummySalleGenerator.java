package com.openclassrooms.mareu.service;

import com.openclassrooms.mareu.model.Salle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DummySalleGenerator {

    public static List<Salle> DUMMY_SALLES = Arrays.asList(

            //Ajouter une liste des salles avec code couleur

            new Salle(  1, "#0287c3", "Réunion A"),
            new Salle(  2, "#03A9F4", "Réunion B"),
            new Salle(  3, "#bbd46a", "Réunion C"),
            new Salle(  4, "#0f056b", "Réunion D"),
            new Salle(  5, "#e0af1f", "Réunion E"),
            new Salle(  6, "#00c3ff", "Réunion F"),
            new Salle(  7, "#373f1a", "Réunion G"),
            new Salle(  8, "#a6767c", "Réunion H"),
            new Salle(  9, "#FF80AB", "Réunion I"),
            new Salle( 10, "#db1702", "Réunion J")

    );


}
