package com.example.labo1;

import android.os.Parcel;
import android.os.Parcelable;

public class Produit implements Parcelable {

    private int id;
    private String nom;
    private String categ;
    private double prix;
    private int qte;
   public static int nbProduits;

    public Produit(int id, String nom, String categ, double prix, int qte) {
        this.id=id;
        this.nom = nom;
        this.categ = categ;
        this.prix = prix;
        this.qte = qte;
    }

    protected Produit(Parcel in) {
        id = in.readInt();
        nom = in.readString();
        categ = in.readString();
        prix = in.readDouble();
        qte = in.readInt();
    }

    public static final Creator<Produit> CREATOR = new Creator<Produit>() {
        @Override
        public Produit createFromParcel(Parcel in) {
            return new Produit(in);
        }

        @Override
        public Produit[] newArray(int size) {
            return new Produit[size];
        }
    };

    public int getId() { return id;   }

    public void setId(int id) {  this.id = id;   }

    public String getNom() { return nom; }

    public void setNom(String nom) { this.nom = nom; }

    public String getCateg() {  return categ; }

    public void setCateg(String categ) { this.categ = categ; }

    public double getPrix() { return prix; }

    public void setPrix(double prix) {  this.prix = prix;  }

    public int getQte() {  return qte; }

    public void setQte(int qte) { this.qte = qte; }

    public static int getNbProduits() { return nbProduits;  }

    public static void setNbProduits(int nbProduits) {    Produit.nbProduits = nbProduits; }

    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", categ='" + categ + '\'' +
                ", prix=" + prix +
                ", qte=" + qte +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nom);
        dest.writeString(categ);
        dest.writeDouble(prix);
        dest.writeInt(qte);
    }
}
