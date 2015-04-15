package com.local.android.teleasistenciaticplus.lib.sms;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.local.android.teleasistenciaticplus.lib.helper.AppLog;
import com.local.android.teleasistenciaticplus.modelo.GlobalData;

/**
 * Created by FESEJU on 19/03/2015.
 * La clase envia un SMS y guarda flags de envio y recepción.
 */

public class SmsDispatcher {

    private String phoneNumber; //Destinatario
    private String message; //cuerpo del mensaje

    /**
     * Constructor
     *
     * @param phone   telefono
     * @param message mensaje
     */
    public SmsDispatcher(String phone, String message) {
        this.phoneNumber = phone;
        this.message = message;
    }

    /**
     * Envio de SMS con acuse de recibo
     *
     */
    public void send() {

        String SENT = "SMS_SENT";
        //String DELIVERED = "SMS_DELIVERED";

        final Context miContexto = GlobalData.getAppContext();

        PendingIntent sentPI = PendingIntent.getBroadcast(miContexto, 0, new Intent(SENT), 0);
        //PendingIntent deliveredPI = PendingIntent.getBroadcast(miContexto, 0, new Intent(DELIVERED), 0);

        //---when the SMS has been sent---
        miContexto.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        SmsCheck.clearData();
                        Toast.makeText(miContexto, "SMS enviado" + SmsCheck.i++ ,
                            Toast.LENGTH_SHORT).show();
                            SmsCheck.smsHayDatosEnvioSms = true;
                            SmsCheck.smsEnviado = true;
                            SmsCheck.codigoEnviado = "Activity.RESULT_OK";
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:

                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:

                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:

                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF: //Se puede forzar con modo avión

                        break;
                }
            }
        }, new IntentFilter(SENT));

        //---when the SMS has been delivered---
        // Esto hay que probarlo a fondo. En principio no hemos sido capaces de generarlo, ni siquiera que entre
        // en este bucle. Puede ser que sólo se de en un terminal real.
        /*
        miContexto.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        break;
                    case Activity.RESULT_CANCELED:
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));*/

        ///////////////////////////////////////////////////////////////////
        // Envio del SMS
        SmsManager sms =  SmsManager.getDefault();
        try {
            sms.sendTextMessage(phoneNumber,null,message,sentPI,null);
        } catch (Exception e) {
            //Excepcion conocida actual: SMS send error Invalid message body (mensaje vacio)
            //Excepcion conocida actual: Invalid destinationAddress (cadena vacia)
            AppLog.e("SmsDispatcher", "SMS send error", e);
        }
        ///////////////////////////////////////////////////////////////////

        /*
        while ( isInformacionSmsEnviado() == false ){
            AppLog.i("SMS","Enviado sms...");
        }*/
    }

}


/*

public void send() {
    // android.telephony.SmsManager (la versión que hay que usar)
    // import android.telephony.gsm.SmsManager (la versión antigua)
    SmsManager sms = SmsManager.getDefault();
    try {
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    } catch (Exception e) {
        //Excepcion conocida actual: SMS send error Invalid message body (mensaje vacio)
        //Excepcion conocida actual: Invalid destinationAddress (cadena vacia)
        AppLog.e("SmsDispatcher", "SMS send error", e);
    }
    AppLog.i("SMSSend", phoneNumber + " " + message);
}
 */

/*
public void send() {

        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        final Context miContexto = GlobalData.getAppContext();

        PendingIntent sentPI = PendingIntent.getBroadcast(miContexto, 0, new Intent(SENT), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(miContexto, 0, new Intent(DELIVERED), 0);

        //---when the SMS has been sent---
        miContexto.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:

                        Toast.makeText( miContexto, "SMS sent" + String.valueOf(getResultCode()),
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(miContexto, "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(miContexto, "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(miContexto, "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(miContexto, "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        // Resultado del SMS entregado
        //---when the SMS has been delivered---
        miContexto.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(miContexto, "SMS delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(miContexto, "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        // Envio del SMS
        SmsManager sms =  SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber,null,message,sentPI,deliveredPI);

    }
 */