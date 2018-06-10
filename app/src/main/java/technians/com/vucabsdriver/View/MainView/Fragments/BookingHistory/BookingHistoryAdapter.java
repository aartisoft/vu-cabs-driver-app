package technians.com.vucabsdriver.View.MainView.Fragments.BookingHistory;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import technians.com.vucabsdriver.R;
import technians.com.vucabsdriver.model.PendingRequest.BookingData;

import static technians.com.vucabsdriver.Utilities.Constants.FormatAmount;
import static technians.com.vucabsdriver.Utilities.Constants.formateDateFromstring;

/**
 * Created by vineet on 11/18/2017.
 */

public class BookingHistoryAdapter extends RecyclerView.Adapter<BookingHistoryAdapter.MyViewHolder> {

    private List<BookingData> bookinglist;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView TextViewCabType, TextViewPaymentMode, TextViewCreatiomdate, TextViewRideAmount,TextViewGst,TextViewToll,TextViewTotalAmount,
                TextViewStatus, TextViewPickup, TextViewDestination;
        ImageView ImageViewStatus;
        public MyViewHolder(View view) {
            super(view);
            TextViewCabType = view.findViewById(R.id.fragment_bookinghistory_tv_CustomerName);
            TextViewPaymentMode = view.findViewById(R.id.fragment_bookinghistory_tv_paymentmode);
            TextViewRideAmount = view.findViewById(R.id.fragment_bookinghistory_tv_estimatedfare);
            TextViewGst = view.findViewById(R.id.fragment_bookinghistory_tv_taxes);
            TextViewToll = view.findViewById(R.id.fragment_bookinghistory_tv_toll);
            TextViewCreatiomdate = view.findViewById(R.id.fragment_bookinghistory_tv_date);
            TextViewTotalAmount = view.findViewById(R.id.fragment_bookinghistory_tv_totalbill);
            TextViewStatus = view.findViewById(R.id.fragment_bookinghistory_tv_status);
            TextViewPickup = view.findViewById(R.id.fragment_bookinghistory_tv_PickUp);
            TextViewDestination = view.findViewById(R.id.fragment_bookinghistory_tv_Drop);
            ImageViewStatus = view.findViewById(R.id.fragment_bookinghistory_iv_status);
        }
    }

    public BookingHistoryAdapter(List<BookingData> bookinglist, Context context) {
        this.bookinglist = bookinglist;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookinghistory_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final BookingData BookingList = bookinglist.get(position);
        holder.TextViewCabType.setText(BookingList.getCustomer_name());
        holder.TextViewPaymentMode.setText(BookingList.getPayment_type());
        holder.TextViewCreatiomdate.setText(formateDateFromstring("dd-MM-yyyy hh:mm:ss", "MMM dd yyyy, hh:mm a", BookingList.getDate()));
        holder.TextViewRideAmount.setText(FormatAmount(BookingList.getRide_amount()));
        holder.TextViewGst.setText(FormatAmount(BookingList.getGst_amount()));
        holder.TextViewToll.setText(FormatAmount(BookingList.getToll()));
        holder.TextViewTotalAmount.setText(FormatAmount(BookingList.getTotal_amount()));
        holder.TextViewPickup.setText(BookingList.getPickup_location());
        holder.TextViewDestination.setText(BookingList.getDrop_location());
        switch (BookingList.getStatus()){
            case 0:{
                holder.TextViewStatus.setText(context.getString(R.string.scheduled));
                holder.ImageViewStatus.setBackground(context.getResources().getDrawable(R.drawable.ic_schedule_black_24dp));
                break;
            }
            case 1:{
                holder.TextViewStatus.setText(context.getString(R.string.completed));
                holder.ImageViewStatus.setBackground(context.getResources().getDrawable(R.drawable.ic_check_circle_coloraccent_24dp));
                break;
            }
            case 2:{
                holder.TextViewStatus.setText(context.getString(R.string.cancelled));
                holder.ImageViewStatus.setBackground(context.getResources().getDrawable(R.drawable.ic_cancel_red_a700_24dp));
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return bookinglist.size();
    }
}