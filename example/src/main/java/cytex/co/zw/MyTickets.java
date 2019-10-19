package cytex.co.zw;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cytex.co.zw.adapter.MainActivityAdapter;
import cytex.co.zw.interfac.VolleySingleton;
import cytex.co.zw.model.Item;
import cytex.co.zw.model.Ticket;
import cytex.co.zw.util.UrlConstants;
import cytex.co.zw.util.UserSessionManager;

public class MyTickets extends AppCompatActivity {

    SearchView searchView;
    RecyclerView listshowrcy;
    List<Ticket> ticketlists=new ArrayList<>();
    String URL=UrlConstants.MYTICKETS_URL;
    StringRequest request;
    UserSessionManager userSessionManager;

    MainActivityAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tickets);

        userSessionManager=new UserSessionManager(MyTickets.this);

        HashMap<String,String> session=userSessionManager.getUserDetails();
        String numberplate=session.get(UserSessionManager.KEY_User);



        getTickets(numberplate);
        listshowrcy=(RecyclerView)findViewById(R.id.listshow);
        listshowrcy.setHasFixedSize(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchfile,menu);
        final MenuItem myActionMenuItem=menu.findItem(R.id.search);

        searchView=(SearchView) myActionMenuItem.getActionView();
        changeSearchViewTextColor(searchView);
        List<Ticket> fil=new ArrayList<>();

        ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setHintTextColor(getResources().getColor(R.color.white));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!searchView.isIconified()){
                    searchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                final  List<Ticket> filtermodelist=filter(ticketlists,newText);
                adapter.setfilter(filtermodelist);
                return true;
            }
        });

        return true;
    }

    private List<Ticket> filter(List<Ticket> pl,String query){
        query=query.toLowerCase();
        final List<Ticket> filteredHolderList=new ArrayList<>();
        for(Ticket model:pl){
            final  String text=model.getReference().toLowerCase();
            if(text.startsWith(query)){
                filteredHolderList.add(model);
            }
        }
        return filteredHolderList;
    }

    //change the text color of searchview

    private void changeSearchViewTextColor(View view){
        if(view!=null){
            if(view instanceof TextView){
                ((TextView) view).setTextColor(Color.WHITE);
                return;
            }
            else if(view instanceof ViewGroup){
                ViewGroup viewGroup=(ViewGroup) view;
                for(int i=0; i<viewGroup.getChildCount(); i++){
                    changeSearchViewTextColor(viewGroup.getChildAt(i));
                }


            }
        }
    }

    private void getTickets(final String numberplate){


        request=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray=new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        Ticket ticket=new Ticket();
                        ticket.setAmount(jsonObject.getString("amount"));
                        ticket.setDate(jsonObject.getString("date"));
                        ticket.setNumberPlate(jsonObject.getString("numberPlate"));
                        ticket.setReference(jsonObject.getString("reference"));
                        ticket.setStatus(jsonObject.getString("status"));
                        ticket.setTollgateName(jsonObject.getString("tollgateName"));
                        ticket.setTollgateNumber(jsonObject.getString("tollgateNumber"));

                        ticketlists.add(ticket);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                setuprecyclerview(ticketlists);

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
                params.put("numberplate",numberplate);
                return params;
            }
        };
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);






    }
    private void setuprecyclerview(List<Ticket> lstPosts) {
        //
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        listshowrcy.setLayoutManager(layoutManager);
        adapter=new MainActivityAdapter(lstPosts,MyTickets.this);
        listshowrcy.setAdapter(adapter);


    }
}
