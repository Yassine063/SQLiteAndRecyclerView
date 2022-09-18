package com.example.labo1;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.database.sqlite.SQLiteDatabase;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView afficheInventaire;
    Spinner spinner;
    DataBaseHelper myDb;
    SQLiteDatabase db;
    ArrayList<Produit> produitList;
    ProduitAdapter myAdapter;
    ArrayAdapter<String> adapter;
     ArrayList<String> spinnerValues;
    View view;
    String categorie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chargerProduits();
        refreshSpinner();
        setSupportActionBar(findViewById(R.id.toolbar));

        afficheInventaire = findViewById(R.id.afficheInventaire);
        myDb = new DataBaseHelper(MainActivity.this);
        view = findViewById(R.id.afficheInventaire);


    }
   public void refreshSpinner(){
       spinner = findViewById(R.id.spinner);
       spinnerValues = myDb.getAllCategories();
       spinnerValues.add(0, "select categorie");

       adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,spinnerValues);
       adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       spinner.setAdapter(adapter);

       produitList = new ArrayList<>();
       produitList = myDb.readData();

       myAdapter = new ProduitAdapter(produitList, MainActivity.this);
       recyclerView = findViewById(R.id.recylerview);

       spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

           @Override
           public void onItemSelected(AdapterView<?> adapterView,View view,int position,long l){






               if(spinner.getSelectedItemId() != 0){

                   String categ = spinner.getSelectedItem().toString();
                   ArrayList<Produit> produitListFiltered = new ArrayList<>();


                   for(int i=0; i<produitList.size(); i++){

                       String catego = produitList.get(i).getCateg();
                       if(catego.equalsIgnoreCase(categ)){

                           produitListFiltered.add(produitList.get(i));
                       }
                   }

                   Intent intent = new Intent(MainActivity.this,ListeSelonCategorieActivity.class);
                   intent.putExtra("categorie", categ);
                   intent.putParcelableArrayListExtra("listCategorie", produitListFiltered);
                   startActivity(intent);
               }


           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }

       });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_accueil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.add:
                view.setVisibility(View.INVISIBLE);
                sendDataDialog();
                return true;
            case R.id.lister:
                view.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                listerProduits();

                return true;
            case R.id.categorie:
                view.setVisibility(View.INVISIBLE);

//                Intent intent = new Intent(MainActivity.this, ListeSelonCategorieActivity.class);
//                intent.putExtra("listeProduit", listeProduitSelonCategorie(categorie));
//                startActivity(intent);

                return true;
            case R.id.total:

                totalInventaire();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void chargerProduits() {

        myDb = new DataBaseHelper(this);
        myDb.obtenirListeProduits();
    }

    private void totalInventaire() {

        double prix = 0;
        int qte = 0;
        double total = 0;

        for (int i = 0; i < produitList.size(); i++) {

            prix = produitList.get(i).getPrix();
            qte = produitList.get(i).getQte();

            total += prix * qte;
        }

        view.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        afficheInventaire.setText(total + "$");
    }

    public ArrayList listeProduitSelonCategorie(String categorie) {


        TextView _id = findViewById(R.id.listViewId);
        TextView nom = findViewById(R.id.listeViewName);
        TextView prix = findViewById(R.id.listeViewPrix);
        TextView qte = findViewById(R.id.listeViewQte);
        TextView categ = findViewById(R.id.listeViewCateg);

        Cursor cursorProduit = db.rawQuery("SELECT _id,nom,prix,qte FROM Produit WHERE categ like ?",
                new String []{categorie}) ;
        ArrayList<Produit> produits = new ArrayList<>();

        if (cursorProduit.getCount() > 0) {
            for (int x = 0; x < cursorProduit.getCount(); x++) {

                cursorProduit.moveToNext();
                if (cursorProduit.getString(2) == categorie) {
                    produits.add(new Produit(
                            cursorProduit.getInt(0),
                            cursorProduit.getString(1),
                            cursorProduit.getString(2),
                            cursorProduit.getDouble(3),
                            cursorProduit.getInt(4)));
                }
            }
        }
        return produits;
    }

    private void listerProduits() {

        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void sendDataDialog() {

        Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.layout_custom_dialog);

        EditText nomProduit = dialog.findViewById(R.id.dialogueNomProduit);
        EditText codeCtégorie = dialog.findViewById(R.id.dialogueCodeCatégorie);
        EditText prix = dialog.findViewById(R.id.dialoguePrix);
        EditText qte = dialog.findViewById(R.id.dialogueQte);
        Button btnAdd = dialog.findViewById(R.id.button_ok);
        Button btnCancel = dialog.findViewById(R.id.button_cancel);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Produit produit;
                produit = new Produit(1, nomProduit.getText().toString(), codeCtégorie.getText().toString(),
                        Double.parseDouble(prix.getText().toString()), Integer.parseInt(qte.getText().toString()));
                long id = myDb.dbhEnregistrerProduit(produit);
                int idInt = (int) id;
                produit.setId(idInt);
                produitList.add(produit);

                myAdapter.notifyDataSetChanged();

                if (idInt != -1) {
                    Toast.makeText(getApplicationContext(), "Le produit" + idInt + " a ete bien ajouter", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Le produit n'a pas ete ajouter", Toast.LENGTH_SHORT).show();

                }
                dialog.dismiss();
            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("onDestroy", "OnDestroy called");
        //Close dn connections here todo
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
