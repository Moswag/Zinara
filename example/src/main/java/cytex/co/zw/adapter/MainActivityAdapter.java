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
import cytex.co.zw.model.Item;
import cytex.co.zw.model.Ticket;

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.Holderview> {
 private List<Ticket> productlist;
 private Context context;

    public MainActivityAdapter(List<Ticket> productlist, Context context) {
        this.productlist = productlist;
        this.context = context;
    }

    @NonNull
    @Override
    public Holderview onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View layout= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.customitem,viewGroup,false);

        return new Holderview(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull Holderview holderview, final int position) {

        holderview.amount.setText("Amount: "+productlist.get(position).getAmount());
        holderview.date.setText("Date: "+productlist.get(position).getDate());
        holderview.numberplate.setText("Number Plate: "+productlist.get(position).getNumberPlate());
        holderview.reference.setText("Reference: "+productlist.get(position).getReference());
        holderview.status.setText("Status: "+productlist.get(position).getStatus());
        holderview.tollgateName.setText("Tollgate name: "+productlist.get(position).getTollgateName());


//        holderview.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context,"click on"+ productlist.get(position).getName(),Toast.LENGTH_LONG).show();
//            }
//        });



    }

    @Override
    public int getItemCount() {
        return productlist.size();
    }

    public void setfilter(List<Ticket> listitem){
        productlist=new ArrayList<>();
        productlist.addAll(listitem);
        notifyDataSetChanged();
    }

    class Holderview extends RecyclerView.ViewHolder{

        TextView tollgateName,date,amount,status,reference,numberplate;

        Holderview(View itemview){
          super(itemview);
            tollgateName=(TextView) itemview.findViewById(R.id.tollgateName);
            date=(TextView) itemview.findViewById(R.id.date);
            amount=(TextView) itemview.findViewById(R.id.amount);
            status=(TextView) itemview.findViewById(R.id.status);
            reference=(TextView) itemview.findViewById(R.id.reference);
            numberplate=(TextView) itemview.findViewById(R.id.numberplate);
        }

    }
}
