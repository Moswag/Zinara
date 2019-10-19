package cytex.co.zw.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cytex.co.zw.AboutApp;
import cytex.co.zw.AdvancePayment;
import cytex.co.zw.MainActivity;
import cytex.co.zw.MyTickets;
import cytex.co.zw.MyAccount;
import cytex.co.zw.R;
import cytex.co.zw.RoadRules;
import cytex.co.zw.model.Book;

public class HomeFragmentRecyclerViewAdapter extends RecyclerView.Adapter<HomeFragmentRecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Book> mData;

    public HomeFragmentRecyclerViewAdapter(Context mContext, List<Book> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater mInflator=LayoutInflater.from(mContext);
        view=mInflator.inflate(R.layout.cardview_category_view,viewGroup,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int position) {

        myViewHolder.tv_book_title.setText(mData.get(position).getTitle());
        myViewHolder.img_book_thumbnail.setImageResource(mData.get(position).getThumbnail());

        //set click listener
        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pressed=mData.get(position).getCategory();
                if(pressed.equals("qr")){
                    Intent intent=new Intent(mContext,MainActivity.class);
                    mContext.startActivity(intent);
                }
                else if(pressed.equals("advance_payment")){
                    Intent intent=new Intent(mContext,AdvancePayment.class);
                    mContext.startActivity(intent);
                }
                else if(pressed.equals("my_tickets")){
                    Intent intent=new Intent(mContext,MyTickets.class);
                    mContext.startActivity(intent);

                }
                else if(pressed.equals("road_rules")){
                    Intent intent=new Intent(mContext,RoadRules.class);
                    mContext.startActivity(intent);

                }
                else if(pressed.equals("myaccount")){
                    Intent intent=new Intent(mContext,MyAccount.class);
                    mContext.startActivity(intent);
                }
                else{
                    Intent intent=new Intent(mContext,AboutApp.class);
                    mContext.startActivity(intent);
                }

            }
        });



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_book_title;
        ImageView img_book_thumbnail;
        CardView cardView;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_book_title=(TextView) itemView.findViewById(R.id.book_title_id);
            img_book_thumbnail=(ImageView) itemView.findViewById(R.id.book_img_id);

            cardView=(CardView) itemView.findViewById(R.id.cardview_id);
        }
    }

}
