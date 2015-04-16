package com.local.android.teleasistenciaticplus.lib.sms;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.local.android.teleasistenciaticplus.act.debug.actDebugSMS;
import com.local.android.teleasistenciaticplus.modelo.GlobalData;

/**
 * Created by FESEJU on 19/03/2015.
 * La clase envia un SMS y guarda flags de envio y recepción.
 */

public class SmsDispatcher {

    private String phoneNumber; //Destinatario
    private String message; //cuerpo del mensaje

    BroadcastReceiver sendBroadcastReceiver = new sentReceiver();

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
     */
    public void send() {

        String SENT = "SMS_SENT";

        final Context miContexto = GlobalData.getAppContext();

        final PendingIntent sentPI = PendingIntent.getBroadcast(miContexto, 0, new Intent(SENT), 0);

        miContexto.registerReceiver(sendBroadcastReceiver, new IntentFilter(SENT));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, null);

        /*
        ///////////////////////////////////////////////////////////////////
        // Envio del SMS
        SmsManager sms = SmsManager.getDefault();
        try {
            sms.sendTextMessage(phoneNumber, null, message, sentPI, null);
        } catch (Exception e) {
            //Excepcion conocida actual: SMS send error Invalid message body (mensaje vacio)
            //Excepcion conocida actual: Invalid destinationAddress (cadena vacia)
            AppLog.e("SmsDispatcher", "SMS send error", e);
        }
        ///////////////////////////////////////////////////////////////////
        */
    }

    /**
     *
     */
    class sentReceiver extends BroadcastReceiver {
        final Context miContexto = GlobalData.getAppContext();

        @Override
        public void onReceive(Context context, Intent arg1) {
            switch (getResultCode()) {
                case Activity.RESULT_OK:
                    actDebugSMS.sms_post_send(true, "Activity.RESULT_OK");
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
                    Toast.makeText(miContexto, "Null PDU", Toast.LENGTH_SHORT)
                            .show();
                    break;
                case SmsManager.RESULT_ERROR_RADIO_OFF:
                    Toast.makeText(miContexto, "Radio off",
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}

