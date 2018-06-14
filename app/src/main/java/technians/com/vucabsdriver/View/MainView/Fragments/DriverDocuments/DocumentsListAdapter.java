package technians.com.vucabsdriver.View.MainView.Fragments.DriverDocuments;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import technians.com.vucabsdriver.R;
import technians.com.vucabsdriver.Model.Documents.DocumentsList;

/**
 * Created by vineet on 10/27/2017.
 */

public class DocumentsListAdapter extends RecyclerView.Adapter<DocumentsListAdapter.MyViewHolder> {

    private List<DocumentsList> docList = new ArrayList<>();
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView TextViewDocTitle,TextViewDocStatus;
        ImageView ImageViewDocument;

        public MyViewHolder(View view) {
            super(view);
            TextViewDocTitle = (TextView) view.findViewById(R.id.doctitle);
            TextViewDocStatus = (TextView) view.findViewById(R.id.docStatus);
            ImageViewDocument = (ImageView) view.findViewById(R.id.ViewDoc);
        }
    }

    public DocumentsListAdapter(List<DocumentsList> docList, Context context) {
        this.docList = docList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.documents_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final DocumentsList DocList = docList.get(position);
        holder.TextViewDocTitle.setText(DocList.getDoc_name());
        if (Objects.equals(DocList.getDoc_name(), "Deriving Licence (D.L)"))
        {
            holder.TextViewDocTitle.setText("Driving Licence");
        }
        if (Objects.equals(DocList.getImage_upload(), "true"))
        {
            holder.TextViewDocStatus.setText("Verified");
            holder.TextViewDocStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check_circle_coloraccent_18dp, 0, 0, 0);
            holder.TextViewDocStatus.setCompoundDrawablePadding(5);
        }
       else if (Objects.equals(DocList.getImage_upload(), "false"))
        {
            holder.TextViewDocStatus.setText("Not verified");
            holder.TextViewDocStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_cancel_red_900_18dp, 0, 0, 0);
            holder.TextViewDocStatus.setCompoundDrawablePadding(5);

        }
    }

    @Override
    public int getItemCount() {
        return docList.size();
    }
}