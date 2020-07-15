package com.otc.democajatrujillooct;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Scroller;
import android.widget.TextView;

import com.otc.sdk.pax.a920.OtcApplication;
import com.otc.sdk.pos.flows.ConfSdk;
import com.otc.sdk.pos.flows.sources.config.CustomError;
import com.otc.sdk.pos.flows.sources.config.InitializeResponseHandler;
import com.otc.sdk.pos.flows.sources.server.models.response.initialize.InitializeResponse;
import com.otc.sdk.pos.flows.sources.server.rest.ProcessInitializeCallback;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConfSdk.endpoint = "https://culqimpos.quiputech.com/";
        ConfSdk.tenant = "culqi";

        ConfSdk.username = "integracion@otcperu.com";
        ConfSdk.password = "Peru2019$$";

        //llave master
        ConfSdk.keyTmk = 1; // default
        //llaves
        ConfSdk.keyData = 10;
        ConfSdk.keyPin = 10;
        ConfSdk.keyMac = 10;

        //ejemplo de firma 1
        OtcApplication.getCrypted().getMacSignature("texto a firmar");

        //ejemplo de firma 2
        int keyMac = 10;
        OtcApplication.getCrypted().getMacSignature("texto a firmar", keyMac);

    }

    public void initialization(View view) {

        Log.i(TAG, "config endpoint => " + ConfSdk.endpoint);
        Log.i(TAG, "config tenant   => " + ConfSdk.tenant);
        Log.i(TAG, "config keyTmk   => " + ConfSdk.keyTmk);
        Log.i(TAG, "config keyData  => " + ConfSdk.keyData);
        Log.i(TAG, "config keyPin   => " + ConfSdk.keyPin);
        Log.i(TAG, "config keyMac   => " + ConfSdk.keyMac);

        // INITIALIZE -----------------------------------------------------------------------------
        ProcessInitializeCallback callback = new ProcessInitializeCallback();
        callback.initialization(this, new InitializeResponseHandler() {
            @Override
            public void onSuccess(InitializeResponse response) {
                Log.i(TAG, "onSuccess: " + response);
                ShowMessage(response.toString());
            }

            @Override
            public void onError(CustomError error) {
                Log.i(TAG, "onError: " + error.getMessage());
                Log.i(TAG, "onError: " + error.getStatusCode());
            }
        });

    }

    private void ShowMessage(String msg) {

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("OTC - DEMO")
                .setMessage(msg)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
        TextView textView = (TextView) dialog.findViewById(android.R.id.message);
        textView.setScroller(new Scroller(this));
        textView.setVerticalScrollBarEnabled(true);
        textView.setMovementMethod(new ScrollingMovementMethod());

    }

}