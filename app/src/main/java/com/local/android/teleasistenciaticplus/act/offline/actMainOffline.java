package com.local.android.teleasistenciaticplus.act.offline;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.local.android.teleasistenciaticplus.R;
import com.local.android.teleasistenciaticplus.act.debug.actMainDebug;
import com.local.android.teleasistenciaticplus.act.user.actUserOptions;
import com.local.android.teleasistenciaticplus.act.user.actUserOptionsPersonaContacto;
import com.local.android.teleasistenciaticplus.fragment.fragUserRegister;
import com.local.android.teleasistenciaticplus.lib.helper.AlertDialogShow;
import com.local.android.teleasistenciaticplus.lib.helper.AppLog;
import com.local.android.teleasistenciaticplus.lib.helper.AppSharedPreferences;
import com.local.android.teleasistenciaticplus.lib.playsound.PlaySound;
import com.local.android.teleasistenciaticplus.modelo.Constants;
import com.local.android.teleasistenciaticplus.modelo.DebugLevel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Actividad princical del modo OFFLINE
 *
 * @param
 */

public class actMainOffline extends Activity implements fragUserRegister.OnFragmentInteractionListener {


    ImageButton SMSAlertButton;
    ImageButton SMSOKButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_offline);

        SMSAlertButton = (ImageButton) findViewById(R.id.tfmButton);
        SMSOKButton = (ImageButton) findViewById(R.id.btnIamOK);

        //Damos la bienvenida
        if(Constants.PLAY_SOUNDS){
            PlaySound.play(R.raw.bienvenido);
        }


        /////////////////////////////////////////////////////////////
        // Si no tiene datos personales (nombre + apellidos) en la aplicación se muestra el fragmento
        /////////////////////////////////////////////////////////////

        boolean hasUserData = new AppSharedPreferences().hasUserData();
        if (!hasUserData) {
            Fragment miFragRegistroUsuario = new fragUserRegister();

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.overlay_user_register_loading_screen, miFragRegistroUsuario)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Si estamos en modo de depuración
        if ( Constants.DEBUG_LEVEL == DebugLevel.DEBUG ) {
            getMenuInflater().inflate(R.menu.menu_act_mainoffline, menu);
        } else { //si estamos en modo de producción no mostramos el menu de depuración
            getMenuInflater().inflate(R.menu.menu_act_mainoffline_produccion, menu);
        }

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_actmainoffline_exit_app) {
            finish();
        } else if ( id == R.id.menu_actmainoffline_debug_screen ) {
            Intent intent = new Intent(this, actMainDebug.class);
            startActivity(intent);
        } else if ( id == R.id.menu_act_user_options ) {
            Intent intent = new Intent(this, actUserOptions.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Se encarga de gestionar la UI del fragmento de registro de nuevo usuario
     * @param uri
     */
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void user_register_datos_personales_aceptar(View view) {

        TextView textEditNombre = (TextView) findViewById(R.id.user_register_datos_personales_nombre_text);
        TextView textEditApellidos = (TextView) findViewById(R.id.user_register_datos_personales_apellidos_text);

        AppSharedPreferences userSharedPreferences = new AppSharedPreferences();
        userSharedPreferences.setUserData( textEditNombre.getText().toString() , textEditApellidos.getText().toString() );

        /////////
        //Feedback al usuario tras la actualización
        /////////
            AlertDialogShow popup_conn = new AlertDialogShow();
            popup_conn.setTitulo(getResources().getString(R.string.user_options_datos_personales_edit));

            popup_conn.setMessage(getResources().getString(R.string.user_options_datos_personales_correct_edit));

            popup_conn.setLabelNeutral(getResources().getString(R.string.close_window));
            popup_conn.show(getFragmentManager(), "dummyTAG");
        //Fin del mensaje de información


        fragmentUserRegisterHide();

    }

    public void fragmentUserRegisterHide() {

        //Fragment miFragRegistroUsuario = new fragUserRegister();

        FragmentManager fragmentManager = getFragmentManager();
        Fragment userFragment = getFragmentManager().findFragmentById(R.id.overlay_user_register_loading_screen);
        fragmentManager.beginTransaction()
                .hide(userFragment)
                .commit();
    }


    /////////////////////////////////////////////////////////////
    // Métodos asociados a los botones de la UI
    /////////////////////////////////////////////////////////////

    /**
     * Botón de acceso a la Configuración
     * Da acceso a la configuración de parámetros personales
     * @param
     *
     */
    public void configuration_action_button(View view) {

        Intent intent = new Intent(this, actUserOptions.class);

        startActivity(intent);

        if( Constants.SHOW_ANIMATION ) {

            overridePendingTransition(R.animator.animation2, R.animator.animation1);

        }
    }

    /**
     * Botón para volver a casa
     * Activa la opción de volver a casa
     * @param view
     *
     */
    public void backtohome_action_button(View view) {

        Toast.makeText(getBaseContext(), "Volver a Casa" , Toast.LENGTH_LONG).show();
        //TODO implementar este método y la clase (actBackToHome)
        /*
        Intent intent = new Intent(this, actBackToHome.class);


        startActivity(intent);

        if( Constants.SHOW_ANIMATION ) {

            overridePendingTransition(R.animator.animation2, R.animator.animation1);

        }
        */
    }


    /**
     * Botón para activar el modo ducha
     * Activa el modo ducha
     * @param view
     *
     */
    public void showermode_action_button(View view) {

        Toast.makeText(getBaseContext(), "Modo Ducha" , Toast.LENGTH_LONG).show();
        PlaySound.play(R.raw.modo_ducha_activado);

        //TODO implementar este método y la clase (actShowerMode)
        /*
        Intent intent = new Intent(this, actShowerMode.class);


        startActivity(intent);

        if( Constants.SHOW_ANIMATION ) {

            overridePendingTransition(R.animator.animation2, R.animator.animation1);

        }
        */
    }


    /**
     * Botón para acceder a los contactos familiares
     * Atajo a los contactos familiares
     * @param view
     *
     */
    public void familiar_action_button(View view) {

        Intent intent = new Intent(this, actUserOptionsPersonaContacto.class);

        startActivity(intent);

        if( Constants.SHOW_ANIMATION ) {

            overridePendingTransition(R.animator.animation2, R.animator.animation1);

        }
    }



    /**
     * Envio de los SMS a los familiares
     * @param view
     */
    public void sendAvisoOffline(View view) {

        //1. Leemos la lista de personas de contacto
        //2. Comprobamos el tiempo transcurrido desde el último SMS enviado
        //2. Se les envía SMS
        //3. Se muestra un mensaje de indicación


        Boolean hayPersonasContactoConTelefono = new AppSharedPreferences().hasPersonasContacto();

        if ( !hayPersonasContactoConTelefono ) {

            /*
            /////////
            //Genera una alerta en caso de que no tengamos asignados los contactos
            /////////
            AlertDialogShow popup_conn = new AlertDialogShow();

            popup_conn.setTitulo(getResources().getString(R.string.user_register_no_phone_contacs_title));
            popup_conn.setMessage(getResources().getString(R.string.user_register_no_phone_contacs));
            popup_conn.setLabelNeutral(getResources().getString(R.string.close_window));
            popup_conn.show(getFragmentManager(), "dummyTAG");

            //Se abre el menú de personas de contacto*/
            //TODO solucionar la llamada del dialog para que solo responda al botón cerrar

            Toast.makeText(getBaseContext(), getResources().getString(R.string.user_register_no_phone_contacs) , Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, actUserOptionsPersonaContacto.class);

            startActivity(intent);

            if( Constants.SHOW_ANIMATION ) {

                overridePendingTransition(R.animator.animation2, R.animator.animation1);

            }
        }

        //Operación de envío de SMS
        String[] personasContacto = new AppSharedPreferences().getPersonasContacto();

        if ( personasContacto[1].length() > 0) {

            //new AppSMS().generateSmsAviso( personasContacto[1] );
        }

        if ( personasContacto[3].length() > 0) {

            //new AppSMS().generateSmsAviso( personasContacto[3] );
        }

        if ( personasContacto[5].length() > 0) {

            //new AppSMS().generateSmsAviso( personasContacto[5] );
        }

        //TODO: mejorar con el control de errores de SMS

        //Si se ha mandado algún SMS...

        if ( (personasContacto[1].length() + personasContacto[3].length() + personasContacto[5].length() ) > 0) {

            //Actualizamos el tiempo del envío del mensaje
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd-MM-yyyy 'a las' HH:mm:ss");

            String currentDateandTime = sdf.format(new Date());

            //Mostramos el texto en la pantalla
            TextView tvUltimoSMSEnviado = (TextView) findViewById(R.id.tvUltimoSMSEnviado);

            tvUltimoSMSEnviado.setText("Último SMS enviado el " + currentDateandTime);

            AppLog.i("sendAvisoOffline", "SMS enviado: " + tvUltimoSMSEnviado.getText());

            //TODO: almacenar en sharedpreferences la fecha del último SMS que se envió


            /////////
            //Toast de confirmación de envío
            /////////
            //Toast.makeText(getBaseContext(), getResources().getString(R.string.user_sms_sent) ,
            //        Toast.LENGTH_LONG).show();
            //Fin del mensaje de alerta


            //Avisamos al usuario de que ha enviado el SMS con un sonido
            if(Constants.PLAY_SOUNDS) {

                PlaySound.play(R.raw.mensaje_enviado);

            }

            //Deshabilitamos el botón y cambiamos su aspecto
            view.setEnabled(false);

            view.setBackgroundResource(R.drawable.grey_button200);

            //Habilitamos el botón transcurridos unos segundos
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    SMSAlertButton.setEnabled(true);
                    SMSAlertButton.setBackgroundResource(R.drawable.red_button200);
                }
            },Constants.SMS_SENDING_DELAY);

        }
    }

    /**
     * botón para enviar SMS de tranquilidad (I'm OK)
     * @param view
     */
    public void sendAvisoIamOK(View view) {

        //1. Leemos la lista de personas de contacto
        //2. Se les envía SMS
        //3. Se muestra un mensaje de indicación

        Boolean hayPersonasContactoConTelefono = new AppSharedPreferences().hasPersonasContacto();

        if ( !hayPersonasContactoConTelefono ) {

            Toast.makeText(getBaseContext(), getResources().getString(R.string.user_register_no_phone_contacs) , Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, actUserOptionsPersonaContacto.class);

            startActivity(intent);

            if( Constants.SHOW_ANIMATION ) {

                overridePendingTransition(R.animator.animation2, R.animator.animation1);

            }
        }

        //Operación de envío de SMS
        String[] personasContacto = new AppSharedPreferences().getPersonasContacto();

        if ( personasContacto[1].length() > 0) {

            //new AppSMS().generateSmsIamOK( personasContacto[1] );

        }

        if ( personasContacto[3].length() > 0) {

            //new AppSMS().generateSmsIamOK( personasContacto[3] );

        }

        if ( personasContacto[5].length() > 0) {

            //new AppSMS().generateSmsIamOK( personasContacto[5] );

        }



        //Si se ha mandado algún SMS...

        if ( (personasContacto[1].length() + personasContacto[3].length() + personasContacto[5].length() ) > 0) {

            //Actualizamos el tiempo del envío del mensaje
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd-MM-yyyy 'a las' HH:mm:ss");

            String currentDateandTime = sdf.format(new Date());

            //Mostramos el texto en la pantalla
            TextView tvUltimoSMSEnviado = (TextView) findViewById(R.id.tvUltimoSMSEnviado);

            tvUltimoSMSEnviado.setText("Último SMS enviado el " + currentDateandTime);

            AppLog.i("sendAvisoOffline", "SMS enviado: " + tvUltimoSMSEnviado.getText());

            //TODO: almacenar en sharedpreferences la fecha del último SMS que se envió


            /////////
            //Toast de confirmación de envío
            /////////
            //Toast.makeText(getBaseContext(), getResources().getString(R.string.user_sms_sent) ,
            //        Toast.LENGTH_LONG).show();
            //Fin del mensaje de alerta


            //Avisamos al usuario de que ha enviado el SMS con un sonido
            if(Constants.PLAY_SOUNDS) {

                PlaySound.play(R.raw.mensaje_enviado);

            }

            //Deshabilitamos el botón y cambiamos su aspecto
            view.setEnabled(false);

            view.setBackgroundResource(R.drawable.iam_ok_pressed);

            //Habilitamos el botón transcurridos unos segundos
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    SMSOKButton.setEnabled(true);
                    SMSOKButton.setBackgroundResource(R.drawable.iam_ok);
                }
            },Constants.SMS_SENDING_DELAY);

        }










    }
}