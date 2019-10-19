package cytex.co.zw;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class Register extends AppCompatActivity {
     EditText name,phonenumber,numberplate,email,password,password2;
     Button register;
     TextView back_login;
     StringRequest request;
     String URL=UrlConstants.REGISTER_URL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name=(EditText) findViewById(R.id.name);
        phonenumber=(EditText) findViewById(R.id.phonenumber);
        numberplate=(EditText) findViewById(R.id.numberplate);
        email=(EditText) findViewById(R.id.email);
        password=(EditText) findViewById(R.id.password);
        password2=(EditText) findViewById(R.id.password2);
        register=(Button) findViewById(R.id.register);
        back_login=(TextView) findViewById(R.id.back_login);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final  String name1=name.getText().toString();
                final  String phonenumber1=phonenumber.getText().toString();
                final  String numberplate1=numberplate.getText().toString();
                final  String email1=email.getText().toString();
                final String password11=password.getText().toString();
                final  String password22=password2.getText().toString();

                if(name1.equals("")| phonenumber1.equals("")|numberplate1.equals("")|email1.equals("")|password11.equals("")|password22.equals("")){
                    Toast.makeText(getApplicationContext(),"please fill in all fields",Toast.LENGTH_LONG).show();
                }
                else{

                    if(!password11.equals(password22)){
                        Toast.makeText(getApplicationContext(),"passwords do not match, they should match",Toast.LENGTH_LONG).show();
                    }
                    else{
                        request=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject=new JSONObject(response);
                                    String res=jsonObject.getString("response");
                                    if(res.equals("success")){
                                        Toast.makeText(getApplicationContext(),"You have successfully registered",Toast.LENGTH_LONG).show();
                                        clearText();
                                        startActivity(new Intent(Register.this,LoginActivity.class));
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),res,Toast.LENGTH_LONG).show();
                                        clearText();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(),"Failed to connect to database",Toast.LENGTH_LONG).show();
                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> params=new HashMap<>();
                                params.put("name",name1);
                                params.put("phonenumber",phonenumber1);
                                params.put("numberplate",numberplate1);
                                params.put("email",email1);
                                params.put("password",password11);
                                return params;
                            }
                        };
                        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
                    }

                }


            }
        });

        back_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this,LoginActivity.class));
            }
        });
    }
    public void clearText(){
        name.setText("");
        phonenumber.setText("");
        numberplate.setText("");
        email.setText("");
        password.setText("");
        password2.setText("");
    }
}
