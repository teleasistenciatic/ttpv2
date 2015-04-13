package com.local.android.teleasistenciaticplus.act.user;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.local.android.teleasistenciaticplus.R;
import com.local.android.teleasistenciaticplus.lib.helper.AlertDialogShow;
import com.local.android.teleasistenciaticplus.lib.helper.AppSharedPreferences;
import com.local.android.teleasistenciaticplus.modelo.GlobalData;

public class actUserOptionsDatosPersonales extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_user_options_datos_personales);

        //Leemos los valores por defecto que hay guardados en el SharedPreferences
        String[] datosPersonales = new AppSharedPreferences().getUserData();

        TextView textEditNombre = (TextView) findViewById(R.id.user_options_datos_personales_nombre_text);
        TextView textEditApellidos = (TextView) findViewById(R.id.user_options_datos_personales_apellidos_text);

        textEditNombre.setText(datosPersonales[0]);
        textEditApellidos.setText(datosPersonales[1]);
    }

    /**
     * Se guardan los datos del usuario en las SharedPreferences
     * @param view vista
     */
    public void user_options_datos_personales_edit(View view) {

        TextView textEditNombre = (TextView) findViewById(R.id.user_options_datos_personales_nombre_text);
        TextView textEditApellidos = (TextView) findViewById(R.id.user_options_datos_personales_apellidos_text);

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

    }

    public void user_options_datos_personales_exit(View view) {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_act_user_options_datos_personales, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_user_options_datos_personales_exit_app) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
