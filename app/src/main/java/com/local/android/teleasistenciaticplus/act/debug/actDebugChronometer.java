package com.local.android.teleasistenciaticplus.act.debug;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.local.android.teleasistenciaticplus.R;

/**
 * Created by GAMO1J on 09/04/2015.
 */
public class actDebugChronometer extends Activity {

    public CountDownTimer isTheFinalCountDown; //clase para la cuenta atrás

    //párametros para la clase
    public int futureTime;
    public int interval;

    //Token para evitar llamadas adicionales
    public boolean activedCountDown;
    //TODO: sustituir por deshabilitar el botón Start


    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_debug_ducha);

        //El intervalo de refresco se estable a segundos
        interval = 1000;

        activedCountDown = false;

    }

    public void startCountDown(View v) {

        if (!activedCountDown) {

            activedCountDown = true;

            //Capturamos el tiempo (en minutos) introducido por el usuario
            final TextView mTextField = (TextView) findViewById(R.id.mTextField);

            EditText text = (EditText) findViewById(R.id.etMinutos);

            String mins = text.getEditableText().toString();

            futureTime = Integer.parseInt(mins) * 60000;

            isTheFinalCountDown = new CountDownTimer(futureTime, interval) {

                @Override
                public void onTick(long millisUntilFinished) {

                    if (millisUntilFinished  < 60000) {
                        mTextField.setText("00:" + millisUntilFinished / 1000);
                    } else {
                        //TODO parse the textfield to show minutes and seconds
                        int minutos = (int) (millisUntilFinished / 60000);
                        int segundos = (int) ( ( (millisUntilFinished / 1000) - (minutos * 60)) );
                        if(millisUntilFinished > 600000) {
                            mTextField.setText("" + minutos + ":" + segundos);
                        }else {
                            mTextField.setText("0" + minutos + ":" + segundos);
                        }

                    }

                }

                @Override
                public void onFinish() {
                    //TODO: launch SMS
                    mTextField.setText("Send SMS now");
                    activedCountDown = false;
                }
            }.start();
        }
    }

    public void cancelCountDown(View v) {

        activedCountDown = false;
        isTheFinalCountDown.cancel();

        TextView mTextField = (TextView) findViewById(R.id.mTextField);
        mTextField.setText("00:00");
    }

    /**
     * Salida de la aplicación al pulsar el botón de salida del layout
     *
     * @param view vista
     */
    public void exit_button(View view) {
        finish();
    }
}



 /*
        final Chronometer cronoDucha = (Chronometer) findViewById(R.id.chronometer);

        cronoDucha.setBase(System.currentTimeMillis());

        cronoDucha.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {

            @Override
            public void onChronometerTick(Chronometer chronometer) {
                AppLog.i("actDebugChronometer","Base       : " + chronometer.getBase()  );
                AppLog.i("actDebugChronometer", "TimeMillis : " + System.currentTimeMillis());
                CharSequence text = chronometer.getText();
                if (text.length()  == 5) {
                    chronometer.setText("00:"+text);
                } else if (text.length() == 7) {
                    chronometer.setText("0"+text);
                }

            }
        });
        cronoDucha.setBase(SystemClock.elapsedRealtime());
        cronoDucha.start();
        */
