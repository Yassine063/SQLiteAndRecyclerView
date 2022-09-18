package com.example.labo1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class ListeSelonCategorieActivity extends AppCompatActivity {

  RecyclerView recyclerView2;
    ArrayList<Produit> produitList;
    ProduitAdapter myAdapter;
    DataBaseHelper Db;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_selon_categorie);

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        String categ = getIntent().getStringExtra("categorie");

        if(getIntent() != null){
            produitList = getIntent().getParcelableArrayListExtra("listCategorie");
        }else{
            produitList = new ArrayList<>();
        }


        Db = new DataBaseHelper(ListeSelonCategorieActivity.this);

        recyclerView2 = findViewById(R.id.recycler2);


        myAdapter = new ProduitAdapter(produitList, ListeSelonCategorieActivity.this);
        recyclerView2.setAdapter(myAdapter);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
    }
}