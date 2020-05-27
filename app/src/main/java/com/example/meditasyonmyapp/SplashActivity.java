package com.example.meditasyonmyapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    public boolean internetVarMi(final Context context)
    {
        final ConnectivityManager connectivityManager= ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if(internetVarMi(this))
        {
            Thread splashThread;
            splashThread=new Thread()
            {
                @Override public void run()
                {
                    try {
                        synchronized (this)
                        {
                            wait(20000);
                        }
                    }
                    catch (InterruptedException ex)
                    {

                    }
                    finally {
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                    }
                }
            };
            splashThread.start();
        }
        else
        {
            AlertDialog alert= new AlertDialog.Builder(this).create();
            alert.setTitle("Bağlantı Hatası");
            alert.setMessage("Lütfen İnternet Bağlantınızı Kontrol Edin.");
            alert.setButton(DialogInterface.BUTTON_NEUTRAL, "TAMAM", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int pid = android.os.Process.myPid();
                    android.os.Process.killProcess(pid);
                    dialog.dismiss();
                }
            });
            alert.show();
        }
    }
}
