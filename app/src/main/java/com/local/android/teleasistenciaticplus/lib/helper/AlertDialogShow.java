package com.local.android.teleasistenciaticplus.lib.helper;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.local.android.teleasistenciaticplus.R;
import com.local.android.teleasistenciaticplus.modelo.GlobalData;

/**
 * Created by GAMO1J on 25/02/2015.
 *
 * Clase genérica para mostrar mensajes en en el IU vía AlertDialog.
 * Sólo mostrará un AlertDialog con un botón.
 */

/* TODO ver si se puede abstraer y simplificar */

public class AlertDialogShow extends DialogFragment {

    //Datos de Entrada para la inicialización del dialog
    String titulo;
    String Message;
    String labelNeutral;

    @Override
    public AlertDialog onCreateDialog(Bundle savedInstanceState) {
        // Usa la clase constructora para la creación del dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //Aplicamos un estilo al Dialog
        //LayoutInflater inflater = getActivity().getLayoutInflater();
        //builder.setView(inflater.inflate(R.layout.popup_dialog, null));

        //Definimos el icono, título y mensaje
        builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle(this.titulo);
        builder.setMessage(this.Message);

        //Declaramos al acción del botón. Por defecto, al ser una ventana informativa no se
        //ejecuta ninguna acción concreta
        builder.setNeutralButton(this.labelNeutral, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // No requiere de implementación
            }
        });
        return show(builder);
    }

    //Devolver el diálogo al host activity
    public AlertDialog show(AlertDialog.Builder builder) {
        return builder.create();
    }

    //Setters
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setMessage(String message) {
        this.Message = message;
    }

    public void setLabelNeutral(String labelNeutral) {
        this.labelNeutral = labelNeutral;
    }

}


