package com.local.android.teleasistenciaticplus.lib.networking;

import com.local.android.teleasistenciaticplus.lib.cifrado.Cifrado;
import com.local.android.teleasistenciaticplus.lib.helper.AppLog;
import com.local.android.teleasistenciaticplus.modelo.Constants;
import com.local.android.teleasistenciaticplus.modelo.GlobalData;

/**
 * Created by GAMO1J on 02/03/2015.
 * Clase genérica para realizar operaciones contra el servidor
 */
public class ServerOperations {

    /**
     * Método de comprobación del estado del servidor (online/offline | true/false)
     * @return boolean con la condición de online/offline
     */
    public static boolean serverIsOnline() {

        String url = Constants.SERVER_URL + Constants.CONTROLLER_CHECK_ONLINE_SERVER;
        String textRead = "";

        try {
            HttpUrlTextRead miUrl = new HttpUrlTextRead(url);
                textRead = miUrl.getText();
        } catch (Exception e) {
                AppLog.d("ServerOperations", "Error leyendo el archivo");
        }

        AppLog.i("ServerOperations", "textRead: " + textRead);

        if (textRead != null) {
            if (textRead.equals("true")) {
                AppLog.i("ServerOperations", textRead);
                return true;
            } else {
                AppLog.i("ServerOperations", "Error accediendo a la dirección:\"" + url + "\"");
                return false;
            }
        }
        return false;
    }

    /**
     * Comprobación de usuario registrado en el sistema
     * @return boolean con la condición de registrado o no
     */
    public static boolean isRegisteredOnServer() {

        //recuperamos el número del terminal
        String phoneNumber = GlobalData.getPhoneNumber();
        AppLog.i("ServerOperations -> ", "Número de teléfono: " + phoneNumber);

        //Encriptamos el número de teléfono
        if( phoneNumber.isEmpty() ) {
            AppLog.i("ServerOperations", "Número de teléfono: VACIO");
            return false;
        }

        //Llamamos a la encriptación
        String cifrado = "";
        try {
            cifrado = new Cifrado().cifrar(phoneNumber);
        } catch (Exception e) {
            AppLog.e("ServerOperations", "Problema de cifrado: " + phoneNumber, e);
        }

        //conectamos al servidor vía /phone/check/ y recuperamos la respuesta
        String ConstUrlPhoneCheck = Constants.SERVER_URL + Constants.CONTROLLER_CHECK_PHONE + cifrado;
        String textRead = "";
        try {
            HttpUrlTextRead miUrl = new HttpUrlTextRead(ConstUrlPhoneCheck);
            textRead = miUrl.getText();
        } catch (Exception e) {
            AppLog.d("ServerOperations", "Error de conexión??");
        }

        //Si el server nos devuelve el string "true" el usuario está registrado
        if (textRead != null) {
            if (textRead.equals("true")) {
                //AppLog.i("ServerOperations", textRead);
                return true;
            } else {
                AppLog.i("ServerOperations", "Error accediendo a la dirección:\"" + ConstUrlPhoneCheck + "\"");
                return false;
            }
        }
        return false;
    }

    /**
     * //Solicitar el nombre del usuario
     * @return el nombre del usuario o "unknown user"
     */
    public static String retrieveUserName () {

        //Recuperamos el número de teléfono
        String pn = GlobalData.getPhoneNumber();

        //Llamamos a la encriptación
        String cifrado = "";
        try {
            cifrado = new Cifrado().cifrar(pn);
        } catch (Exception e) {
            AppLog.e("ServerOperations -->", "Problema de cifrado: " + pn, e);
        }

        //conectamos al servidor vía /phoneuser/name/ y recuperamos la respuesta
        String ConstUrlPhoneUserName = Constants.SERVER_URL + Constants.CONTROLLER_USER_NAME + cifrado;
        String textRead = "";
        try {
            HttpUrlTextRead miUrl = new HttpUrlTextRead(ConstUrlPhoneUserName);
            textRead = miUrl.getText();
        } catch (Exception e) {
            AppLog.d("ServerOperations -> ", "Error de conexión??");
        }
        if (textRead != null) {
            if ( textRead.length() != 0 ) {
                AppLog.i("ServerOperations -> ", textRead);
                return textRead;
            } else {
                AppLog.i("ServerOperations -> ", "Error recuperando el nombre del usuario:\"" + ConstUrlPhoneUserName + "\"");
                return "unknown user";
            }
        }
        return "unknown user";
    }


    /**
     * Comprueba si hay algún aviso activo para este usuario.
     * @return true | false
     */

    public static boolean checkAviso() {
        //Recuperamos el número de teléfono
        String pn = GlobalData.getPhoneNumber();

        //Llamamos a la encriptación
        String cifrado = "";
        try {
            cifrado = new Cifrado().cifrar(pn);
        } catch (Exception e) {
            AppLog.e("ServerOperations -->", "Problema de cifrado: " + pn, e);
        }

        //conectamos al servidor vía /aviso/check/ y recuperamos la respuesta
        String ConstURLCheck = Constants.SERVER_URL + Constants.CONTROLLER_AVISO_CHECK + cifrado;
        String textRead = "";
        try {
            HttpUrlTextRead miUrl = new HttpUrlTextRead(ConstURLCheck);
            textRead = miUrl.getText();
        } catch (Exception e) {
            AppLog.d("ServerOperations -> ", "ERROR Checkeo de aviso");
        }
        if (textRead != null) {
            if (textRead.equals("true")) {
                AppLog.i("ServerOperations -> ", "Aviso ya estaba activo: ");
                return true;
            } else {
                AppLog.i("ServerOperations -> ", "No hay avisos activos: ");
                return false;
            }
        }
        AppLog.i("ServerOperations -> ", "ERROR texRead ");
        return false;
    }

    /**
     * Crea un nuevo aviso en el servidor
     * @return true | false
     */
    public static boolean crearAviso() {

        //Recuperamos el número de teléfono
        String pn = GlobalData.getPhoneNumber();

        //Llamamos a la encriptación
        String cifrado = "";
        try {
            cifrado = new Cifrado().cifrar(pn);
        } catch (Exception e) {
            AppLog.e("ServerOperations -->", "Problema de cifrado: " + pn, e);
        }
        String ConstURLCreate = Constants.SERVER_URL + Constants.CONTROLLER_AVISO_CREATE + cifrado;
        String textRead = "";

        //creamos nuevo aviso
        try {
            HttpUrlTextRead miUrl = new HttpUrlTextRead(ConstURLCreate);
            textRead = miUrl.getText();
        } catch (Exception e) {
            AppLog.d("ServerOperations -> ", "Error de conexión??");
        }
        if (textRead != null) {
            if (textRead.equals("true")) {
                AppLog.i("ServerOperations -> ", "Aviso CREADO: " + textRead);
                return true;
            } else {
                //Toast.makeText(getApplicationContext(), "No hay avisos activos", Toast.LENGTH_SHORT).show();
                AppLog.i("ServerOperations -> ", "Error creando aviso: " + textRead);
                return false;
            }
        }
        AppLog.i("ServerOperations -> ", "ERROR texRead = " + textRead);
        return false;
    }

    /**
     * Borrar aviso de usuario
     */

    public static boolean borrarAviso() {

        //Recuperamos el número de teléfono
        String pn = GlobalData.getPhoneNumber();

        //Llamamos a la encriptación
        String cifrado = "";
        try {
            cifrado = new Cifrado().cifrar(pn);
        } catch (Exception e) {
            AppLog.e("ServerOperations -->", "Problema de cifrado: " + pn, e);
        }

        String ConstURLCreate = Constants.SERVER_URL + Constants.CONTROLLER_AVISO_DELETE + cifrado;

        String textRead = "";

        //borramos el aviso

        try {
            HttpUrlTextRead miUrl = new HttpUrlTextRead(ConstURLCreate);
            textRead = miUrl.getText();
        } catch (Exception e) {
            AppLog.d("ServerOperations -> ", "Error de conexión??");
        }
        if (textRead != null) {
            if (textRead.equals("true")) {
                AppLog.i("ServerOperations -> ", "Aviso BORRADO: " + textRead);
                return true;
            } else {
                //Toast.makeText(getApplicationContext(), "No hay alarmas activas", Toast.LENGTH_SHORT).show();
                AppLog.i("ServerOperations -> ", "Error borrando aviso: " + textRead);
                return false;
            }
        }
        AppLog.i("ServerOperations -> ", "ERROR texRead = " + textRead);
        return false;
    }
}