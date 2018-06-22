package example.com.secretsafe.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import example.com.secretsafe.R;
import example.com.secretsafe.activity.CategoryActivity;

/**
 * Created by EypCnn on 1.01.2018.
 */

public class Parmakizi extends FingerprintManager.AuthenticationCallback {


    private Context context;


    // Constructor
    public Parmakizi(Context mContext) {
        context = mContext;
    }


    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {
        CancellationSignal cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }


    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        this.update("Parmak izi kimlik doğrulama hatası\n" + errString, false);
    }


    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        this.update("Parmak izi kimlik doğrulaması yardım\n" + helpString, false);
    }


    @Override
    public void onAuthenticationFailed() {
        this.update("Parmak izi kimlik doğrulaması başarısız oldu.", false);

    }


    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        this.update("Parmak izi kimlik doğrulaması başarılı oldu.", true);
        context.startActivity(new Intent(context,CategoryActivity.class));
    }


    public void update(String e, Boolean success){
        TextView textView =  ((Activity)context).findViewById(R.id.errorText);
        textView.setText(e);
        if(success){
            textView.setTextColor(ContextCompat.getColor(context,R.color.colorPrimaryDark));
        }
    }
}
