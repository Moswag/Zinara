package cytex.co.zw;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.vision.barcode.Barcode;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cytex.co.zw.interfac.VolleySingleton;
import cytex.co.zw.util.UrlConstants;
import cytex.co.zw.util.UserSessionManager;
import info.androidhive.barcode.BarcodeReader;

public class MainActivity extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private BarcodeReader barcodeReader;
    UserSessionManager sessionManager;
    String mynumberplate;
    StringRequest request;
    String URL=UrlConstants.ADVANCEPAYMENT_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // getting barcode instance
        barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_fragment);

        sessionManager=new UserSessionManager(getApplicationContext());
        HashMap<String,String> session=sessionManager.getUserDetails();
        mynumberplate=session.get(UserSessionManager.KEY_User);
        /***
         * Providing beep sound. The sound file has to be placed in
         * `assets` folder
         */
        // barcodeReader.setBeepSoundFile("shutter.mp3");

        /**
         * Pausing / resuming barcode reader. This will be useful when you want to
         * do some foreground user interaction while leaving the barcode
         * reader in background
         * */
        // barcodeReader.pauseScanning();
        // barcodeReader.resumeScanning();
    }

    @Override
    public void onScanned(final Barcode barcode) {
        Log.e(TAG, "onScanned: " + barcode.displayValue);
        barcodeReader.playBeep();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
               // Toast.makeText(getApplicationContext(), "Barcode: " + barcode.displayValue, Toast.LENGTH_SHORT).show();
                String json = barcode.displayValue;
                String[] groups = json.split(",");


              final  String tollgate = groups[0];
                final String numberplate = groups[1];
                if (numberplate.equals(mynumberplate)) {
                    Toast.makeText(getApplicationContext(), "Numberplate plate is " + numberplate + " and paying for tollgete " + tollgate, Toast.LENGTH_LONG).show();
                    request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String res = jsonObject.getString("response");
                                if (res.equals("success")) {
                                    Toast.makeText(getApplicationContext(), "Ticket successfully captured", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(MainActivity.this,MyTickets.class));


                                } else {
                                    Toast.makeText(getApplicationContext(), res, Toast.LENGTH_LONG).show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Failed to connect to database", Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> params = new HashMap<>();
                            params.put("tollgate", tollgate);
                            params.put("numberplate", numberplate);
                            params.put("status", "Used");
                            return params;
                        }
                    };
                    VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);


                } else {
                    Toast.makeText(getApplicationContext(), "License do not match", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onScannedMultiple(List<Barcode> barcodes) {
        Log.e(TAG, "onScannedMultiple: " + barcodes.size());

        String codes = "";
        for (Barcode barcode : barcodes) {
            codes += barcode.displayValue + ", ";
        }

        final String finalCodes = codes;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Barcodes: " + finalCodes, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String errorMessage) {

    }

    @Override
    public void onCameraPermissionDenied() {
        Toast.makeText(getApplicationContext(), "Camera permission denied!", Toast.LENGTH_LONG).show();
        finish();
    }
}