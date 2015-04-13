package com.local.android.teleasistenciaticplus.act.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.local.android.teleasistenciaticplus.R;
import com.local.android.teleasistenciaticplus.lib.helper.AppLog;
import com.local.android.teleasistenciaticplus.lib.helper.AppSharedPreferences;
import com.local.android.teleasistenciaticplus.lib.phone.PhoneContacts;

import java.util.Map;

public class actUserOptionsPersonaContacto extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_user_options_persona_contacto);

        ////////////////////////////////////////////////////////////////////
        /// Leer los valores de persona s de contacto
        ///////////////////////////////////////////////////////////////////

        String personasContacto[] = new AppSharedPreferences().getPersonasContacto();

        ////////////////////////////////////////////////////////////////////
        /// Inflar el layout con los valores de personas de contacto
        ///////////////////////////////////////////////////////////////////

        TextView textedit = (TextView) findViewById(R.id.user_options_persona_contacto_text);
        TextView texteditName = (TextView) findViewById(R.id.user_options_persona_contacto_name_text);
        TextView textedit1 = (TextView) findViewById(R.id.user_options_persona_contacto_text_1);
        TextView texteditName1 = (TextView) findViewById(R.id.user_options_persona_contacto_name_text_1);
        TextView textedit2 = (TextView) findViewById(R.id.user_options_persona_contacto_text_2);
        TextView texteditName2 = (TextView) findViewById(R.id.user_options_persona_contacto_name_text_2);

        textedit.setText( personasContacto[0] );
        texteditName.setText( personasContacto[1] );

        textedit1.setText( personasContacto[2] );
        texteditName1.setText( personasContacto[3] );

        textedit2.setText( personasContacto[4] );
        texteditName2.setText( personasContacto[5] );

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_act_user_options_persona_contacto, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_user_options_persona_contacto_exit_app) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Selección del contacto principal
     *
     * @param view
     */
    public void user_options_persona_contacto_text_click_1(View view) {

        //Abrir la lista de contactos
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, 0);

    }

    /**
     * Selección del contacto 2
     *
     * @param view
     */
    public void user_options_persona_contacto_text_click_2(View view) {
        //Abrir la lista de contactos
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    /**
     * Selección del contacto 3
     *
     * @param view
     */
    public void user_options_persona_contacto_text_click_3(View view) {
        //Abrir la lista de contactos
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, 2);
    }

    /**
     * Lanza la actividad para almacenar el contacto seleccionado
     * @param view
     */


    /**
     * Función que recoge los datos del contacto seleccionado
     *
     * @param reqCode    regcode
     * @param resultCode resultCode
     * @param data       El data del intent
     */
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        Map contactDataMap = null;

        TextView textedit = (TextView) findViewById(R.id.user_options_persona_contacto_text);
        TextView texteditName = (TextView) findViewById(R.id.user_options_persona_contacto_name_text);

        TextView textedit1 = (TextView) findViewById(R.id.user_options_persona_contacto_text_1);
        TextView texteditName1 = (TextView) findViewById(R.id.user_options_persona_contacto_name_text_1);

        TextView textedit2 = (TextView) findViewById(R.id.user_options_persona_contacto_text_2);
        TextView texteditName2 = (TextView) findViewById(R.id.user_options_persona_contacto_name_text_2);

        switch (reqCode) {

            case (0):
                if (resultCode == Activity.RESULT_OK) {

                    contactDataMap = new PhoneContacts(data).getPhoneContact();

                    textedit.setText(contactDataMap.get("displayName").toString());
                    texteditName.setText(contactDataMap.get("phoneNumber").toString());

                    AppLog.i("Contactos", contactDataMap.toString());
                }
                break;

            case (1):
                if (resultCode == Activity.RESULT_OK) {

                    contactDataMap = new PhoneContacts(data).getPhoneContact();

                    textedit1.setText(contactDataMap.get("displayName").toString());
                    texteditName1.setText(contactDataMap.get("phoneNumber").toString());

                    AppLog.i("Contactos", contactDataMap.toString());

                }
                break;

            case (2):
                if (resultCode == Activity.RESULT_OK) {

                    contactDataMap = new PhoneContacts(data).getPhoneContact();

                    textedit2.setText(contactDataMap.get("displayName").toString());
                    texteditName2.setText(contactDataMap.get("phoneNumber").toString());

                    AppLog.i("Contactos", contactDataMap.toString());

                }
                break;
        }

        /*
        -Valores del Array asociativo-
        contactMap.put("displayName", displayName);
        contactMap.put("hasPhoneNumber", hasPhoneNumber);
        contactMap.put("phoneNumber", phoneNumber);
        contactMap.put("contactId", contactId);*/

        //////////////////////////////////////////////
        // Guardar las personas de contacto en el SharedPreferences
        //////////////////////////////////////////////

        AppSharedPreferences userSharedPreferences = new AppSharedPreferences();
        userSharedPreferences.setPersonasContacto(  textedit.getText().toString(), texteditName.getText().toString(),
                                                    textedit1.getText().toString(), texteditName1.getText().toString(),
                                                    textedit2.getText().toString(), texteditName2.getText().toString()
                                                 );

    }

}
