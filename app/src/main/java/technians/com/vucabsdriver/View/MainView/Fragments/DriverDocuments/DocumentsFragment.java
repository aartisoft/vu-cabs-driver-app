package technians.com.vucabsdriver.View.MainView.Fragments.DriverDocuments;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import technians.com.vucabsdriver.R;
import technians.com.vucabsdriver.Utilities.Constants;
import technians.com.vucabsdriver.Utilities.SessionManager;
import technians.com.vucabsdriver.Model.Documents.DocumentResponce;
import technians.com.vucabsdriver.Model.Documents.DocumentsList;
import technians.com.vucabsdriver.Model.RetrofitError.NetworkError;
import technians.com.vucabsdriver.rest.ApiClient;
import technians.com.vucabsdriver.rest.ApiInterface;


public class DocumentsFragment extends Fragment {
    private RecyclerView recyclerView;
    private DocumentsListAdapter mAdapter;
    private ProgressDialog mProgressDialogObj;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_documents, container, false);
        mProgressDialogObj = Constants.showProgressDialog(getActivity());
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_documents_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        getDocumentsList();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle(getString(R.string.documents));
    }
    private void getDocumentsList() {
        try {
            mProgressDialogObj.show();
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<DocumentResponce> call = apiService.getDocumentsList(new SessionManager(getActivity()).getToken());
            call.enqueue(new Callback<DocumentResponce>() {
                @Override
                public void onResponse(@NonNull Call<DocumentResponce> call, @NonNull retrofit2.Response<DocumentResponce> response) {
                    mProgressDialogObj.dismiss();
                    if (response.body() != null) {
                        if (response.body().getStatus() == 200) {
                            ArrayList<DocumentsList> arrayList = response.body().getDocumentsLists();
                            ShowDocuments(arrayList);
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<DocumentResponce> call, @NonNull Throwable t) {
                    mProgressDialogObj.dismiss();
                    String error = new NetworkError(t).getAppErrorMessage();
                    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(getActivity(), getString(R.string.label_something_went_wrong), Toast.LENGTH_SHORT).show();
        }
    }

    private void ShowDocuments(List<DocumentsList> DocumentList) {
        mAdapter = new DocumentsListAdapter(DocumentList,getActivity().getApplicationContext());
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
