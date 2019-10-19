package cytex.co.zw;

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

import java.util.ArrayList;
import java.util.List;

import cytex.co.zw.adapter.RoadRulesAdapter;
import cytex.co.zw.model.Rule;

public class RoadRules extends AppCompatActivity {
    SearchView searchView;
    RecyclerView listshowrcy;
    List<Rule> productlists=new ArrayList<>();

    RoadRulesAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_road_rules);

        productlists.add(new Rule("Chapter 1","Please avoid driving when drunk"));
        productlists.add(new Rule("Chapter 2","Fasten your sit belts when driving"));
        productlists.add(new Rule("Chapter 3","The use of hooter everywhere is prohibited"));
        productlists.add(new Rule("Chapter 4","Dont overtake when you can not see whats ahead of you 10m away"));
        productlists.add(new Rule("Chapter 5","Overspeeding is a punishable offense"));
        productlists.add(new Rule("Chapter 6","License is a requirement in the road unless you are a validate student on a road with an instructor deside you"));
        productlists.add(new Rule("Chapter 7","Overspeeding is a punishable offense"));
        productlists.add(new Rule("Chapter 8","Read and obey all road signs"));
        productlists.add(new Rule("Chapter 9","Please check your vehicle from time to time for fitness"));
        productlists.add(new Rule("Chapter 10","Use of p[hones while driving is prohibited"));
        productlists.add(new Rule("Chapter 11","Drinking and driving is prohibited"));
        productlists.add(new Rule("Chapter 12","Keep on the speed limit"));
        productlists.add(new Rule("Chapter 13","Exercise caution as there is no second chance when death approaches"));
        productlists.add(new Rule("Chapter 14","Loud noises endanger you as you wont hear the hooter or signs of warning coming your way"));
        productlists.add(new Rule("Chapter 15","When felling asleep please park and have your time of rest"));
        productlists.add(new Rule("Chapter 16","A reserse is neccessary if you travelling long journeys"));
        productlists.add(new Rule("Chapter 17","Allow the VID to do its duty"));
        productlists.add(new Rule("Chapter 18","Do not wave throw litter in the road as it endangers some driver's life"));
        productlists.add(new Rule("Chapter 19","Exercise extreme cautin when driving"));
        productlists.add(new Rule("Chapter 20","Use of earphones or headphones when driving is prohibited, it is for the pilots"));



        listshowrcy=(RecyclerView)findViewById(R.id.listshow);
        listshowrcy.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        listshowrcy.setLayoutManager(layoutManager);
        adapter=new RoadRulesAdapter(productlists,RoadRules.this);
        listshowrcy.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchfile,menu);
        final MenuItem myActionMenuItem=menu.findItem(R.id.search);

        searchView=(SearchView) myActionMenuItem.getActionView();
        changeSearchViewTextColor(searchView);
        List<Rule> fil=new ArrayList<>();

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
                final  List<Rule> filtermodelist=filter(productlists,newText);
                adapter.setfilter(filtermodelist);
                return true;
            }
        });

        return true;
    }

    private List<Rule> filter(List<Rule> pl,String query){
        query=query.toLowerCase();
        final List<Rule> filteredHolderList=new ArrayList<>();
        for(Rule model:pl){
            final  String text=model.getChapter().toLowerCase();
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
}
