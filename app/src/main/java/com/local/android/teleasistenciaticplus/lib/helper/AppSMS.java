package com.local.android.teleasistenciaticplus.lib.helper;

import com.local.android.teleasistenciaticplus.lib.networking.SmsDispatcher;
import com.local.android.teleasistenciaticplus.lib.phone.PhoneData;

/**
 * Created by FESEJU on 25/03/2015.
 * Clase helper encargada de generar el texto de los mensajes SMS que luego enviará SmsDispatcher
 */
public class AppSMS {

    String nombre;
    String apellidos;
    String currentDateandTime = new AppTime().getTimeDate();

    public AppSMS() {

        String[] nombreApellidos = new AppSharedPreferences().getUserData();
        this.nombre = nombreApellidos[0];
        this.apellidos = nombreApellidos[1];
    }

    public void generateSmsIamOK(String phoneNumberDestination) {
        // Andres García comunica que se encuentra bien a las 12:00 del día 12/03/2015

        String smsBodyText = "TELEASISTENCI@TIC+: " + nombre + " " + apellidos + " comunica que se encuentra bien a las " + currentDateandTime;

        new SmsDispatcher(phoneNumberDestination, smsBodyText).send();
    }

    public void generateSmsAviso(String phoneNumberDestination) {
        // Andres García comunica que se encuentra bien a las 12:00 del día 12/03/2015

        String smsBodyText = "TELEASISTENCI@TIC+: " + nombre + " " + apellidos + " ha generado un aviso " + currentDateandTime;

        new SmsDispatcher(phoneNumberDestination, smsBodyText).send();
    }

}
