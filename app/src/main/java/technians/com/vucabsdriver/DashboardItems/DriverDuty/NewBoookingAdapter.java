//package technians.com.vucabsdriver.DashboardItems.DriverDuty;
//
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Build;
//import android.support.annotation.RequiresApi;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.TextView;
//
//import java.util.List;
//
//import technians.com.vucabsdriver.model.BookingHistory;
//import technians.com.vucabsdriver.model.DriverLocation;
//import technians.com.vucabsdriver.R;
//import technians.com.vucabsdriver.Utilities.Constants;
//import technians.com.vucabsdriver.Utilities.DatabaseHandler;
//
//import static technians.com.vucabsdriver.Utilities.Constants.FormatCarType;
//
///**
// * Created by WINDOW 64 BIT on 11/19/2017.
// */
//
//public class NewBoookingAdapter extends RecyclerView.Adapter<NewBoookingAdapter.MyViewHolder> {
//
//    private List<BookingHistory> pendingrequestList;
//    private Context context;
//    private DatabaseHandler databaseHandler ;
//
//    public class MyViewHolder extends RecyclerView.ViewHolder {
//        TextView TextViewCustomerName, TextViewPickup;
//        Button ButtonStartTrip,mButtonPickCustomer;
//
//        public MyViewHolder(View view) {
//            super(view);
//            TextViewCustomerName = view.findViewById(R.id.pendingRequest_customername);
//            TextViewPickup = view.findViewById(R.id.pendingRequest_pickuplocation);
//            mButtonPickCustomer = view.findViewById(R.id.pendingRequest_btn_pickcustomer);
//            ButtonStartTrip = view.findViewById(R.id.pendingRequest_btn_starttrip);
//        }
//    }
//
//    public NewBoookingAdapter(List<BookingHistory> pendingrequestList, Context context) {
//        this.pendingrequestList = pendingrequestList;
//        this.context = context;
//    }
//
//    @Override
//    public NewBoookingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pendingrequest_item, parent,
//                false);
//        return new NewBoookingAdapter.MyViewHolder(itemView);
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    @Override
//    public void onBindViewHolder(final NewBoookingAdapter.MyViewHolder holder, int position) {
//        databaseHandler = new DatabaseHandler(context);
//        final DriverLocation driverLocation = databaseHandler.getDriverLocation();
//        final ProgressDialog mProgressDialogObj = Constants.showProgressDialog(context);
//        final BookingHistory PendingList = pendingrequestList.get(position);
//        holder.TextViewCustomerName.setText(FormatCarType(PendingList.getCustomerName()));
//        holder.TextViewPickup.setText(PendingList.getPickLocation());
//        holder.ButtonStartTrip.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                context.startActivity(new Intent(context,BookingOtpActivity.class));
//            }
//        });
//        holder.mButtonPickCustomer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
//                        Uri.parse("http://maps.google.com/maps?saddr=" + driverLocation.getLatitude() + "," + driverLocation.getLongitude() +
//                                "&daddr=" + PendingList.getPickLat() + "," + PendingList.getPickLng() + ""));
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return pendingrequestList.size();
//    }
//}
