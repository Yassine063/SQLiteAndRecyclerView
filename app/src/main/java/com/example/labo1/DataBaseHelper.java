package com.example.labo1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    private Context context;

    private static final String DATABASE_NAME = "bdproduits";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "Produit";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nom TEXT, " +
                "categ TEXT, " +
                "prix INTEGER, " +
                "qte INTEGER)";

        db.execSQL(sql);

        ContentValues values = new ContentValues();

        values.put("nom","Chal");
        values.put("categ","Boissons");
        values.put("prix","90");
        values.put("qte","39");
        db.insert("Produit", null, values);

        values.put("nom","Chang");
        values.put("categ","Boissons");
        values.put("prix","95");
        values.put("qte","17");
        db.insert("Produit", null, values);

        values.put("nom","Aniseed Syrup");
        values.put("categ","Condiments");
        values.put("prix","50");
        values.put("qte","13");
        db.insert("Produit", null, values);

        values.put("nom","Chef Anton's Cajun Seasonin");
        values.put("categ","Condiments");
        values.put("prix","110");
        values.put("qte","53");
        db.insert("Produit", null, values);

        values.put("nom","Chef Anton's Gumbo Mix");
        values.put("categ","Condiments");
        values.put("prix","106,75");
        values.put("qte","0");
        db.insert("Produit", null, values);

        values.put("nom","Grandm's Boysenberry");
        values.put("categ","Condiments");
        values.put("prix","125");
        values.put("qte","120");
        db.insert("Produit", null, values);

        values.put("nom","Uncle Bob's Organic Dried Pears");
        values.put("categ","Produits sec");
        values.put("prix","150");
        values.put("qte","15");
        db.insert("Produit", null, values);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Produit");
        onCreate(db);
    }

    void obtenirListeProduits(){

        SQLiteDatabase db = this.getWritableDatabase();

    }

    public ArrayList<Produit> readData() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorProduit = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList<Produit> monProduit = new ArrayList<>();

        if (cursorProduit.moveToFirst()) {
            do {

                monProduit.add(new Produit(
                        cursorProduit.getInt(0),
                        cursorProduit.getString(1),
                        cursorProduit.getString(2),
                        cursorProduit.getDouble(3),
                        cursorProduit.getInt(4)));
            } while (cursorProduit.moveToNext());
        }

        cursorProduit.close();
        return monProduit;
    }

    public Double readTotal() {
        double totalChaqueProduit;
        double totalInventaire = 0.0;

        ArrayList<Double> tabPrix = new ArrayList<>();
        ArrayList<Integer> tabQte = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorTotal = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);


        if (cursorTotal.moveToFirst()) {
            int i = 0;
            do {
                tabPrix.add(cursorTotal.getDouble(3));
                tabQte.add(cursorTotal.getInt(4));

                totalChaqueProduit = tabPrix.indexOf(i) * Double.valueOf(tabQte.indexOf(i));
                totalInventaire += totalChaqueProduit;

                  i++;
            } while (cursorTotal.moveToNext());

        }
        cursorTotal.close();
        return totalInventaire;
    }


public long dbhEnregistrerProduit(Produit produit){

    SQLiteDatabase db= this.getWritableDatabase();
    ContentValues values = new ContentValues();

    values.put("nom",produit.getNom());
    values.put("categ",produit.getCateg());
    values.put("prix",produit.getPrix());
    values.put("qte",produit.getQte());

    long id = db.insert("Produit",null,values);
    db.close();

return id;
}

   public ArrayList<Produit> getProductsByCateg(String cetegoryToFilter){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorProduit = db.rawQuery("select * from Produit where categ = " + cetegoryToFilter + "" ,null);

        ArrayList<Produit> monProduit = new ArrayList<>();

        if (cursorProduit.moveToFirst()) {
            do {

                monProduit.add(new Produit(
                        cursorProduit.getInt(0),
                        cursorProduit.getString(1),
                        cursorProduit.getString(2),
                        cursorProduit.getDouble(3),
                        cursorProduit.getInt(4)));
            } while (cursorProduit.moveToNext());
        }

        cursorProduit.close();
        return monProduit;
    }

    public ArrayList<String> getAllCategories() {

        String categ;
        SQLiteDatabase db= this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT DISTINCT categ  FROM Produit",null);

        ArrayList<String> categories = new ArrayList<String>();

        if(cursor.moveToFirst()){
            do{
                categ =  cursor.getString(cursor.getColumnIndexOrThrow("categ"));
                categories.add(categ);

            }while (cursor.moveToNext());
        }
        return categories;
    }
}
