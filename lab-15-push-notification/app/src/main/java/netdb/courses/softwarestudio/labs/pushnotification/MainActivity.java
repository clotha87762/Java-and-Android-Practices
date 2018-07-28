package netdb.courses.softwarestudio.labs.pushnotification;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import android.telephony.TelephonyManager;
import java.io.IOException;


public class MainActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private final static String TAG = "MainActivity";

    private GoogleCloudMessaging gcm;
    private String regId = null;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        if (checkPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(this);
            registerInBackground();
            // TODO: Register your device

        } else {
            Log.i(TAG, "No valid Google Play Services APK found.");
        }

    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {

            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();

            } else {
                Log.i(TAG, "This device is not supported.");
                // Disable some functions of your app
            }

            return false;
        }
        return true;
    }

    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg;
                try {
                    // TODO: obtain your device id and assign it to regId
                    //TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    //String deviceId = tm.getDeviceId();
                    regId = gcm.register("324255575487");

                    msg = "Device registered, registration ID=" + regId;

                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                Log.i(TAG, msg);
            }
        }.execute(null, null, null);
    }


    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
