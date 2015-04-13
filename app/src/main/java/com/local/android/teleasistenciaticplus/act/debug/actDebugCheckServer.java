package com.local.android.teleasistenciaticplus.act.debug;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.local.android.teleasistenciaticplus.R;
import com.local.android.teleasistenciaticplus.lib.helper.AlertDialogShow;
import com.local.android.teleasistenciaticplus.lib.helper.AppLog;
import com.local.android.teleasistenciaticplus.lib.networking.HttpUrlTextRead;
import com.local.android.teleasistenciaticplus.lib.networking.Networking;
import com.local.android.teleasistenciaticplus.modelo.Constants;

/**
 * Actividad que comprueba la conexión de nuestra aplicación al servidor maestro
 */
public class actDebugCheckServer extends Activity {

    /**
     * OnCreate principal
     * @param savedInstanceState estado de la aplicación
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_debug_check_server);

        //Asignar por defecto el SERVER_URL a la caja correspondiente
        TextView serverAddress = (TextView) findViewById(R.id.edit_server_adress);

        String url = serverAddress.getText().toString(); //primero usamos la introducida en la caja de texto

        if (url.length() == 0) {  //Si la cadena está vacia usamos la url por defecto
            serverAddress.setText(Constants.SERVER_URL);
        }

    }

    /**
     * Fin de la activity
     * @param view vista
     */
    public void exit_button(View view) {
        finish();
    }

    public void debug_check_server_online(View view) {

        ////////////////////////////////////////////////////
        // Comprobación de estado online servidor
        ////////////////////////////////////////////////////

        //Interfaz
        TextView serverAddress = (TextView) findViewById(R.id.edit_server_adress);

        String url = serverAddress.getText().toString(); //primero usamos la introducida en la caja de texto

        if (url.length() == 0) {  //Si la cadena está vacia usamos la url por defecto
            serverAddress.setText(Constants.SERVER_URL);
        }

        Boolean isNetworkAvailable = Networking.isConnectedToInternet();

        setTextBackground(serverAddress, isNetworkAvailable); //¿Tenemos conexión a internet?

        //Comprobación del servidor está disponible (se hace mediante la lectura de un fichero en el mismo)
        //y sólo se realiza si existe conexión de internet

        if (isNetworkAvailable) {

            String textRead = null;

            try {
                HttpUrlTextRead miUrl = new HttpUrlTextRead(url);
                textRead = miUrl.getText();
            } catch (Exception e) {
                AppLog.d("actMainDebugCheckServer", "Error leyendo el archivo");
            }

            String resultado;

            if (textRead == null) {
                resultado = getResources().getString(R.string.ERROR);
                AppLog.i("actMainDebugCheckServer", "Error accediendo a la dirección:\"" + url + "\"");
            } else {
                resultado = getResources().getString(R.string.CORRECTO);
                AppLog.i("actMainDebugCheckServer", textRead);
            }

            /////////
            //Generación de alerta en pantalla con el resultado de la conexión
            /////////
            AlertDialogShow popup_conn = new AlertDialogShow();
            popup_conn.setTitulo(getResources().getString(R.string.check_server_conn_title));

            if (resultado.equals(getResources().getString(R.string.ERROR))) {
                popup_conn.setMessage(getResources().getString(R.string.check_server_conn_error));
            } else {
                popup_conn.setMessage(getResources().getString(R.string.check_server_conn_ok));
            }
            popup_conn.setLabelNeutral(getResources().getString(R.string.close_window));
            popup_conn.show(getFragmentManager(), "dummyTAG");
            //Fin del mensaje de alerta
        }
    }

    /**
     * Helper que mostrará los valores true/false de forma gráfica
     * asignando el fondo de color verde o rojo.
     * @param serverAddress el Textview de la caja de texto a la que se le cambiará el color de fondo
     * @param valorPositivo si lo queremos en true o false
     */
    private void setTextBackground(TextView serverAddress, Boolean valorPositivo) {

        if ( valorPositivo ) {
            serverAddress.setBackgroundColor(getResources().getColor(R.color.green));
        } else {
            serverAddress.setBackgroundColor(getResources().getColor(R.color.red));
        }
    }

}