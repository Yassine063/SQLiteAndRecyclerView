package com.example.labo1;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CustomDialog extends Dialog {

    public Context context;

    private EditText editTextNomProduit,editTextCodeCategorie,editTextprix,editTextQte;
    private Button buttonOK;
    private Button buttonCancel;

    private DialogListener listener;

    public CustomDialog(Context context, DialogListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_custom_dialog);

        editTextNomProduit= (EditText) findViewById(R.id.dialogueNomProduit);
        editTextCodeCategorie= (EditText) findViewById(R.id.dialogueCodeCatégorie);
        editTextprix= (EditText) findViewById(R.id.dialoguePrix);
        editTextNomProduit= (EditText) findViewById(R.id.dialogueNomProduit);

        this.buttonOK = (Button) findViewById(R.id.button_ok);
        this.buttonCancel  = (Button) findViewById(R.id.button_cancel);

        this.buttonOK .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonOKClick();
            }
        });
        this.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonCancelClick();
            }
        });
    }

    // User click "OK" button.
    private void buttonOKClick()  {

        String nomProduit = this.editTextNomProduit.getText().toString();
        String codeCtégorie = this.editTextNomProduit.getText().toString();
        String prix = this.editTextNomProduit.getText().toString();
        String qte = this.editTextNomProduit.getText().toString();

        if(nomProduit.isEmpty() || codeCtégorie.isEmpty() || prix.isEmpty() || qte.isEmpty())  {
            Toast.makeText(this.context, "Merçi de remplir tout les champs", Toast.LENGTH_LONG).show();
            return;
        }
        this.dismiss(); // Close Dialog

        if(this.listener!= null)  {
            this.listener.validateEditeText(nomProduit,codeCtégorie,prix,qte);
        }
    }

    // User click "Cancel" button.
    private void buttonCancelClick()  {
        this.dismiss();
    }
    interface DialogListener {
        public void validateEditeText(String nomProduit,String codeCtégorie,String prix,String qte);
    }
}