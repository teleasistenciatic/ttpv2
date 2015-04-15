package com.local.android.teleasistenciaticplus.lib.sms;

import com.local.android.teleasistenciaticplus.lib.helper.AppLog;

/**
 * Created by FESEJU on 15/04/2015.
 */
public class SmsCheck {

    public static int i = 0;
    public static boolean smsHayDatosEnvioSms = false;

    public static boolean smsEnviado = false; //Flag de envio
    public static String codigoEnviado = null; //Cadena con el codigo de exito/error al enviar el SMS

    public static boolean smsConfirmado = false; //Flag de recepción
    public static String codigoConfirmado = null; //Cadena con el codigo de exito/error al confirmar un SMS

    public static void clearData() {
        smsHayDatosEnvioSms = false;
        smsEnviado = false;
        codigoEnviado = null;
        smsConfirmado = false;
        codigoConfirmado = null;
        AppLog.i("actDebugSMS", "Datos de la clase estática limpiados");
    }

}
