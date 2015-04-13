package com.local.android.teleasistenciaticplus.modelo;

import android.app.Application;
import android.content.Context;

/**
 * Created by FESEJU on 11/02/2015.
 * - Se ejecutara en primer lugar, incluso antes que la actividad principal
 * - Es un Singleton que guardará el contexto de la aplicación allá cuando sea necesario
 * - Al guardar el Contexto (BIG) de la aplicación mediante getApplicationContext()
 * no tendremos el problema de memoryLeak
 *
 */

public class GlobalData extends Application {

    private static Context context; //Contexto de la aplicación
    private static String phoneNumber; //Número de teléfono del terminal

    private static OperationMode operationMode; //Modo de operación de funcionamiento

    /**
     * Se guarda el contexto de la aplicación
     * @param context contexto BIG
     */
    public static void setContext(Context context) {
        GlobalData.context = context;
    }

    /**
     * Se guarda el numero de teléfono
     * @param phoneNumber número de teléfono
     */
    public static void setPhoneNumber( String phoneNumber ) {
        GlobalData.phoneNumber = phoneNumber;
    }

    /**
     * Getter del contexto de la aplicación
     * @return contexto BIG
     */
    public static Context getAppContext() {
        return GlobalData.context;
    }

    /**
     * Getter del numero de teléfono de la aplicación
     * @return numero de teléfono
     */
    public static String getPhoneNumber() { return GlobalData.phoneNumber;}

    /**
     * Devuelve el modo de operación
     * @return enumeración del modo de operación
     */
    public static OperationMode getOperationMode() {
        return operationMode;
    }

    /**
     * Establece el modo de operación
     * @param operationMode
     */
    public static void setOperationMode(OperationMode operationMode) {
        GlobalData.operationMode = operationMode;
    }
}