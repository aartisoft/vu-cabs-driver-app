package technians.com.vucabsdriver.View.MainView.Fragments.RatingFeedback;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import technians.com.vucabsdriver.R;
import technians.com.vucabsdriver.Model.RatingFeedback.Rating;


public class RatingFeedbackAdapter extends RecyclerView.Adapter<RatingFeedbackAdapter.MyViewHolder> {

    private Context context;
    private List<Rating> ratingList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_CustomerName,tv_RatingText,tv_Comment,tv_updated;
        RatingBar ratingBar;

        public MyViewHolder(View view) {

            super(view);
            tv_CustomerName = view.findViewById(R.id.rating_custname);
            tv_RatingText = view.findViewById(R.id.rating_ratingtext);
            tv_Comment = view.findViewById(R.id.rating_comment);
            tv_updated = view.findViewById(R.id.rating_updatedate);
            ratingBar= view.findViewById(R.id.rating_ratingbar);
        }
    }

    public RatingFeedbackAdapter(Context context, List<Rating> ratingList) {
        this.context = context;
        this.ratingList = ratingList;
    }

    @Override
    public RatingFeedbackAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ratingfeedback_listrow, parent, false);
        return new RatingFeedbackAdapter.MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(RatingFeedbackAdapter.MyViewHolder holder, int position) {
        Rating rating = ratingList.get(position);
        String CustomerName = rating.getCus_name().substring(0,1).toUpperCase() + rating.getCus_name().substring(1);
        String Comment = rating.getComment().substring(0,1).toUpperCase() + rating.getComment().substring(1);
        holder.tv_CustomerName.setText(CustomerName);
        holder.tv_RatingText.setText(String.format("%d/5", rating.getRating()));
        holder.tv_Comment.setText(Comment);
        holder.tv_updated.setText(parseDateToddMMyyyy(rating.getUpdated_at()));
        holder.ratingBar.setRating(rating.getRating());
    }

    @Override
    public int getItemCount() {
        return ratingList.size();
    }

    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "MMM dd,yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
}