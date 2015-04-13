package com.local.android.teleasistenciaticplus.act.online;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.local.android.teleasistenciaticplus.R;
import com.local.android.teleasistenciaticplus.act.debug.actMainDebug;
import com.local.android.teleasistenciaticplus.lib.helper.AppLog;
import com.local.android.teleasistenciaticplus.lib.networking.ServerOperations;
import com.local.android.teleasistenciaticplus.lib.playsound.PlaySound;
import com.local.android.teleasistenciaticplus.modelo.Constants;
import com.local.android.teleasistenciaticplus.modelo.DebugLevel;

/**
 * Actividad principal
 * - Primera actividad tras la pantalla inicial de carga.
 * - Sólo se accede a ella si no hay problemas de conexión e identificación del usuario.
 * - Se inicializan los elementos del UI y se comprueba si hay avisos activos
 */
public class actMain extends Activity implements Constants {

    //TAG para AppLog
    final static String TAG = "TELEASISTENCIA-actMain --> ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        //Damos la bienvenida
        PlaySound.play(R.raw.bienvenido);

        //Personalizamos el ActionBar con icono y el nombre del usuario
        customizeActionBar();

        //Comprobamos si hay un aviso activo
        Boolean retrieveAviso = false;
        retrieveAviso = ServerOperations.checkAviso();

        if (retrieveAviso == true){

            //Hay un aviso activo
            AppLog.i(TAG, "sendAviso --> Aviso activo detectado");
            Toast.makeText(getApplicationContext(), R.string.dialer_checked, Toast.LENGTH_SHORT).show();

            setAvisoActived(true);
        }
        else {
            //No hay aviso activo (situación inicial por defecto cuando se instala la App)
            setAvisoActived(false);
        }
    }


    /**
     * Menu de la aplicación principal. En base a la constante de depuración
     * mostraremo o no el menu de depuración
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu;

        // Si estamos en modo de depuración
        if ( Constants.DEBUG_LEVEL == DebugLevel.DEBUG ) {
            getMenuInflater().inflate(R.menu.menu_act_main, menu);
        } else { //si estamos en modo de producción no mostramos el menu de depuración
            getMenuInflater().inflate(R.menu.menu_act_main_produccion, menu);
        }

        return true;
    }

    /**
     * Menu de la aplicación principal: Pantalla de debug, salida
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_actmain_exit_app) {
            finish();
        } else if ( id == R.id.menu_actmain_debug_screen ) {
            Intent intent = new Intent(this, actMainDebug.class);
            startActivity(intent);

            if( Constants.SHOW_ANIMATION ) {

                overridePendingTransition(R.animator.animation2, R.animator.animation1);

            }
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Modificación del ActionBar con el nombre del usuario
     *     *
     * @param
     * @return
     */
    public void customizeActionBar () {

        //TODO Incluir icono en el action bar

        //Obtenemos del servidor el nombre del usuario asociado a este número de teléfono
        String userName = ServerOperations.retrieveUserName();

            //SDK API > 16
            ActionBar actionBar2 = getActionBar();
            actionBar2.setTitle(userName);
            actionBar2.setIcon(R.drawable.ic_launcher);
            actionBar2.setLogo(R.drawable.ic_launcher);
    }

    /**
     * Implementación de la pulsación del botón rojo para generar un Aviso
     * Primero comprueba que no haya un aviso activo.
     * (esto puede ser redundante porque en el método onCreate se comprobará y de detectarse se
     * inhabilitará el botón).
     * Si no hay activo activo se creará un nuevo Aviso.
     * @param view
     */
    public void sendAviso(View view) {

        Boolean retrieveAviso = false;
        Boolean createAviso = false;

        //Comprobamos si hay un aviso activo
        retrieveAviso = ServerOperations.checkAviso();

        if (retrieveAviso == true){

            //Hay un aviso activo
            AppLog.i(TAG, "sendAviso --> Aviso activo detectado");
            Toast.makeText(getApplicationContext(), R.string.dialer_checked, Toast.LENGTH_SHORT).show();

            setAvisoActived(true);

        }else {
            //No hay aviso previo. Creamos el aviso
            createAviso = ServerOperations.crearAviso();

            if (createAviso == true){
                //Éxito
                AppLog.i(TAG, "sendAviso --> Aviso Creado");

                setAvisoActived(true);

                //Confirmamos al usuario el envío del aviso
                PlaySound.play(R.raw.aviso_enviado);

            }
            else {
                //Fracaso
                AppLog.i(TAG, "sendAviso --> ERROR Aviso NO creado");
                Toast.makeText(getApplicationContext(), R.string.dialer_error, Toast.LENGTH_SHORT).show();
                //Avisamos al usuario sobre el fallo del envío del aviso
                PlaySound.play(R.raw.error_aviso_no_enviado);
            }
        }
    }

    /**
     * Método para solicitar al servidor la eliminación de un aviso enviado previamente
     * @param mview
     */
    public void CancelarAviso (View mview){

        Boolean deleteAviso = false;
        AppLog.i(TAG, "Cancelar aviso");

        //llamamos al servidor
        deleteAviso = ServerOperations.borrarAviso();

        if (deleteAviso == true) {

            setAvisoActived(false);

            //Confirmamos al usuario la cancelación del aviso
            PlaySound.play(R.raw.aviso_cancelado);

        }

        else {
            AppLog.i(TAG, "ERROR: Cancelar aviso");
            Toast.makeText(getApplicationContext(), R.string.dialer_abort_error, Toast.LENGTH_SHORT).show();

            //Confirmamos al usuario el envío del aviso
            PlaySound.play(R.raw.error_aviso_no_cancelado);

        }
    }

    /**
     * Personaliza el estado de los botones de la activity
     *
     * @param status
     * true: Aviso Activo. Deshabilita el botón rojo y muestra el botón para cancelar aviso.
     * false: Aviso Inactivo. Habilita el botón rojo y oculta el botón para cancelar aviso.
     */
    public void setAvisoActived (Boolean status){


        if (status == true) {

            //Deshabilita el botón rojo
            ImageButton tfmRedButton;
            tfmRedButton = (ImageButton) findViewById(R.id.tfmButton);
            //tfmRedButton.setActivated(false);
            tfmRedButton.setEnabled(false);
            //tfmRedButton.setPressed(true);
            tfmRedButton.setBackgroundResource(R.drawable.grey_button200);

            //TextView tvBtnLabel;
            //tvBtnLabel = (TextView) findViewById(R.id.btnLabel);
            //tvBtnLabel.setText("");

            //Muestra botón de cancelar aviso
            Button btnCancelarAviso;
            btnCancelarAviso = (Button) findViewById(R.id.btnCancel);
            btnCancelarAviso.setVisibility(View.VISIBLE);
        }

        else {
            //Habilita el botón rojo
            ImageButton tfmRedButton;
            tfmRedButton = (ImageButton) findViewById(R.id.tfmButton);
            tfmRedButton.setEnabled(true);

            tfmRedButton.setBackgroundResource(R.drawable.red_button200);

            //TextView tvBtnLabel;
            //tvBtnLabel = (TextView) findViewById(R.id.btnLabel);
            //tvBtnLabel.setText(R.string.red_button_label_send);

            //Oculta botón de cancelar aviso
            Button btnCancelarAviso;
            btnCancelarAviso = (Button) findViewById(R.id.btnCancel);
            btnCancelarAviso.setVisibility(View.INVISIBLE);
        }
    }
}
