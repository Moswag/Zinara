package cytex.co.zw;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cytex.co.zw.interfac.VolleySingleton;
import cytex.co.zw.util.UrlConstants;
import cytex.co.zw.util.UserSessionManager;

public class AdvancePayment extends AppCompatActivity {

    UserSessionManager userSessionManager;
    EditText tollgateE;
    Button save;
    StringRequest request;
    String URL=UrlConstants.ADVANCEPAYMENT_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_payment);

        userSessionManager = new UserSessionManager(AdvancePayment.this);
        HashMap<String, String> username = userSessionManager.getUserDetails();
        final String numberplate = username.get(UserSessionManager.KEY_User);

        tollgateE = (EditText) findViewById(R.id.tollgateE);
        save = (Button) findViewById(R.id.pay);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String tollgate = tollgateE.getText().toString();
                if (tollgate.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please fill in the tollgate", Toast.LENGTH_LONG).show();
                } else {
                    request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String res = jsonObject.getString("response");
                                if (res.equals("success")) {
                                    Toast.makeText(getApplicationContext(), "Ticket successfully captured", Toast.LENGTH_LONG).show();
                                    tollgateE.setText("");

                                } else {
                                    Toast.makeText(getApplicationContext(), res, Toast.LENGTH_LONG).show();
                                    tollgateE.setText("");
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
                            params.put("status", "Unused");
                            return params;
                        }
                    };
                    VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
                }

            }
        });
    }


}
