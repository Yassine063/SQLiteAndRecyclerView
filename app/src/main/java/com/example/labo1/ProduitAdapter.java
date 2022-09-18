package com.example.labo1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProduitAdapter extends RecyclerView.Adapter<ProduitAdapter.MyViewHolder>{

    private ArrayList<Produit> produitArrayList;
    Context context;
//    ItemClickListener listener;

    public ProduitAdapter(ArrayList<Produit> produits, MainActivity mainActivity) {

        this.produitArrayList = produits;
        this.context = context;
//        this.listener = listener;
    }
    public ProduitAdapter(ArrayList<Produit> produits, ListeSelonCategorieActivity mainActivity) {

        this.produitArrayList = produits;
        this.context = context;
//        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row, parent, false);
        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//         on below line we are setting data
        // to our views of recycler view item.
        Produit produitObj = produitArrayList.get(position);

        holder._id.setText(String.valueOf(produitObj.getId()));
        holder.nom.setText(produitObj.getNom());
        holder.categ.setText(produitObj.getCateg());
        holder.prix.setText(String.valueOf(produitObj.getPrix()));
        holder.qte.setText(String.valueOf(produitObj.getQte()));
    }

    @Override
    public int getItemCount() {

        return produitArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView _id,nom,categ,prix,qte;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            _id = itemView.findViewById(R.id.textId);
            nom = itemView.findViewById(R.id.nomProduit);
            categ = itemView.findViewById(R.id.codeCatégorie);
            prix = itemView.findViewById(R.id.prixUnitaire);
            qte = itemView.findViewById(R.id.unitéStock);
        }
    }

    public interface ItemClickListener{
        void onItemClick(Produit produit);
    }
}


