package com.local.android.teleasistenciaticplus.act.debug;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.local.android.teleasistenciaticplus.R;



public class actMainDebug extends Activity {

    /**
     * Creación de la actividad de depuración
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /////////////////////////////////////////////////////////////////////
        // Creación del UI de ListView con los subapartados de Depuración
        ////////////////////////////////////////////////////////////////////

            /// Layout
            setContentView(R.layout.layout_debug_main);
            /// Listview
            final ListView listView = (ListView) findViewById(R.id.debug_main_listView);
            /// String para el ListView
            String[] values = new String[]{
                    "Configuración usuario",// 0,
                    "Envío SMS", //id 1
                    "Lectura .LOG", //id 2
                    "Cifrado/Descifrado", //id 3
                    "Comprobacion conexión a servidor", // id 4
                    "Acceso de datos e internet", //id 5
                    "Uso de la memoria", //id 6
                    "Modo ducha", //id 7
            };

            /// Creación del adaptador con su String
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, values);
            /// Vinculación del adaptador con la lista
            listView.setAdapter(adapter);

            /// Creación del OnClickListener para las pulsaciones
            ////////////////////////////////////////////////////////////////////

            // ListView Item Click Listener
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    Class actToLoad = null;

                    switch(position) {
                        case 7: //"Modo ducha"
                            actToLoad = actDebugChronometer.class;
                            break;
                        case 6: //"Uso de la memoria"
                            actToLoad = actDebugMemory.class;
                            break;
                        case 5: //"Acceso de datos e internet"
                            actToLoad = actDebugDataConnection.class;
                            break;
                        case 4: //"Comprobacion conexión a servidor"
                            actToLoad = actDebugCheckServer.class;
                            break;
                        case 3: //"Cifrado Descifrado"
                            actToLoad = actDebugCifrado.class;
                            break;
                        case 2: //"Lectura de log de depuración"
                            actToLoad = actDebugShowLog.class;
                            break;
                        case 1: //"Envio SMS"
                            actToLoad = actDebugSMS.class;
                            break;
                        case 0: //"Configuración de usuario"
                            actToLoad = actDebugUserConfig.class;
                            break;
                    }

                    Intent newIntent;
                    newIntent = new Intent().setClass(actMainDebug.this, actToLoad);
                    startActivity(newIntent);

                    /*
                    // ListView Clicked item index
                    int itemPosition = position;

                    // ListView Clicked item value
                    String itemValue = (String) listView.getItemAtPosition(position);

                    // Show Alert
                    Toast.makeText(getApplicationContext(),
                            "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                            .show();
                    */
                }

            });

        /// Fin creación listView
        ////////////////////////////////////////////////////////////////////

        /*
        //Texto de la dirección de servidor
        /*TextView serverAddress = (TextView) findViewById(R.id.edit_server_adress);
        serverAddress.setText(Constants.SERVER_URL);
        */
    }

    /**
     * Menus de la actividad
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    /**
     * Opciones del menú
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_act_main_debug_exit) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Fin de la activity
     * @param view vista
     */
    public void exit_button(View view) {
        finish();
    }

} // Fin actividadMainDebug