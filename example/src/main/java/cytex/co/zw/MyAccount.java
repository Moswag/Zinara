package cytex.co.zw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cytex.co.zw.interfac.VolleySingleton;
import cytex.co.zw.util.UrlConstants;
import cytex.co.zw.util.UserSessionManager;

public class MyAccount extends AppCompatActivity {

    TextView account;
    TextView balance;

    String URL=UrlConstants.ACCOUNT_URL;
    StringRequest request;
    UserSessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        sessionManager= new UserSessionManager(MyAccount.this);

        HashMap<String,String> session=sessionManager.getUserDetails();
       final String myaccount=session.get(UserSessionManager.KEY_User);

        account=(TextView) findViewById(R.id.account);
        balance=(TextView) findViewById(R.id.balance);


        request=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray=new JSONArray(response);

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=(JSONObject)jsonArray.get(i);
                        String account1=jsonObject.getString("numberplate");
                        String balance1=jsonObject.getString("amount");
                        account.setText("Account: "+account1);
                        balance.setText("Balance: "+balance1);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"No connection to the database",Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params=new HashMap<>();
                params.put("numberplate",myaccount);
                return  params;
            }
        };

        VolleySingleton.getInstance(MyAccount.this).addToRequestQueue(request);

    }
}
