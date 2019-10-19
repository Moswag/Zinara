package cytex.co.zw.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cytex.co.zw.R;
import cytex.co.zw.adapter.HomeFragmentRecyclerViewAdapter;
import cytex.co.zw.model.Book;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    List<Book> lstBook;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        lstBook=new ArrayList<>();

        lstBook.add(new Book("Pay With QR","qr","Description Book",R.drawable.qr));
        lstBook.add(new Book("Pay In Advance","advance_payment","Description Book",R.drawable.advance_payment));
        lstBook.add(new Book("My Tickets","my_tickets","Description Book",R.drawable.ticket));
        lstBook.add(new Book("Road Rules","road_rules","Description Book",R.drawable.roadrules));
        lstBook.add(new Book("My Account","myaccount","Description Book",R.drawable.myaccount));
        lstBook.add(new Book("About App","about_app","Description Book",R.drawable.abtapp));


        RecyclerView myrv=(RecyclerView) view.findViewById(R.id.recyclerview_id);
        HomeFragmentRecyclerViewAdapter myAdapter=new HomeFragmentRecyclerViewAdapter(getActivity(),lstBook);
        myrv.setLayoutManager(new GridLayoutManager(getActivity(),3));
        myrv.setAdapter(myAdapter);
        return view;
    }





}
