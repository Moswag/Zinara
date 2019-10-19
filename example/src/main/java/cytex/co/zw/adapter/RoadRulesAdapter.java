package cytex.co.zw.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cytex.co.zw.R;
import cytex.co.zw.model.Rule;

public class RoadRulesAdapter extends RecyclerView.Adapter<RoadRulesAdapter.Holderview> {
 private List<Rule> productlist;
 private Context context;

    public RoadRulesAdapter(List<Rule> productlist, Context context) {
        this.productlist = productlist;
        this.context = context;
    }

    @NonNull
    @Override
    public Holderview onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View layout= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.road_rulescustomitem,viewGroup,false);

        return new Holderview(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull Holderview holderview, final int position) {

        holderview.chapter.setText(productlist.get(position).getChapter());
        holderview.description.setText(productlist.get(position).getDescription());

        holderview.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"click on"+ productlist.get(position).getChapter(),Toast.LENGTH_LONG).show();
            }
        });



    }

    @Override
    public int getItemCount() {
        return productlist.size();
    }

    public void setfilter(List<Rule> listitem){
        productlist=new ArrayList<>();
        productlist.addAll(listitem);
        notifyDataSetChanged();
    }

    class Holderview extends RecyclerView.ViewHolder{
        TextView chapter;
        TextView description;

        Holderview(View itemview){
          super(itemview);
          description=(TextView) itemview.findViewById(R.id.description);
            chapter=(TextView) itemview.findViewById(R.id.chapter);
        }

    }
}
