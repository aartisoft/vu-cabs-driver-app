package technians.com.vucabsdriver.View.MainView.Fragments.BookingHistory;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import technians.com.vucabsdriver.R;
import technians.com.vucabsdriver.Utilities.Constants;
import technians.com.vucabsdriver.model.BookingHistory;
import technians.com.vucabsdriver.model.PendingRequest.BookingData;


public class BookingHistoryFragment extends Fragment {

    private ProgressDialog mProgressDialogObj;
    private Realm realm;

    public BookingHistoryFragment() {
        // Required empty public constructor
    }
    List<BookingHistory> BookingList = new ArrayList<>();
    private RecyclerView recyclerView;
    TextView mTextViewNoBooking;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booking_history, container, false);
        Realm.init(getActivity());
        realm = Realm.getDefaultInstance();
        RealmResults<BookingData> bookingData  = realm.where(BookingData.class).findAll();
        ArrayList<BookingData> list = new ArrayList<>();
        list.addAll(bookingData);
        Collections.reverse(list);

        recyclerView = view.findViewById(R.id.fragment_bookinghistory_recycler_view);
        mProgressDialogObj = Constants.showProgressDialog(getActivity());
        mTextViewNoBooking = view.findViewById(R.id.fragment_mypasses_tv_nopasses);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        prepareBookingData(list);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle(getString(R.string.ridehistory));
    }
    private void prepareBookingData(List<BookingData> bookingList) {
        if (bookingList.size()!=0) {
            mTextViewNoBooking.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            BookingHistoryAdapter mAdapter = new BookingHistoryAdapter(bookingList, getActivity().getApplicationContext());
            recyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
        else {
            mTextViewNoBooking.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

}
