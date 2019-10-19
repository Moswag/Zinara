package cytex.co.zw;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddIPAddress extends AppCompatActivity {
    EditText editIp;
    Button addIp;
    UserIPManager ipManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ipaddress);

        ipManager=new UserIPManager(getApplicationContext());


        editIp=(EditText) findViewById(R.id.ipaddress);
        addIp=(Button) findViewById(R.id.addip);


        addIp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editIp.getText().toString().equals("")){
                    editIp.setFocusable(true);
                    editIp.setError("Please fill in the IP");
                }
                else{

                    ipManager.createIPSession(editIp.getText().toString());
                    Toast.makeText(getApplicationContext(),"IP Address successfully set",Toast.LENGTH_LONG).show();

                    startActivity(new Intent(AddIPAddress.this, LoginActivity.class));
                }
            }
        });
    }
}
