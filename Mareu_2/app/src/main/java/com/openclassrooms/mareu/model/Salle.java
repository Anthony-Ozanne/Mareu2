package com.openclassrooms.mareu.model;

import java.util.Objects;

public class Salle {

    /** Lieu de la réunion */
    private int numero; // Salle salle;

    /** couleur de la salle de la réunion */
    private String couleur;

    /** nom de la salle de la réunion */
    private String nom;

    public int getNumero() {
        return numero;
    }

    public Salle(int numero, String couleur, String nom) {
        this.numero = numero;
        this.couleur = couleur;
        this.nom = nom;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Salle{" +
                "numero=" + numero +
                ", couleur='" + couleur + '\'' +
                ", nom='" + nom + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Salle salle = (Salle) o;
        return numero == salle.numero && Objects.equals(couleur, salle.couleur) && Objects.equals(nom, salle.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero, couleur, nom);
    }
}
