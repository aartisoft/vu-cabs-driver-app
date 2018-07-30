package technians.com.vucabsdriver.View.MainView.Fragments.RatingFeedback;

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
import android.widget.Toast;

import java.util.ArrayList;

import io.realm.Realm;
import technians.com.vucabsdriver.Model.RatingFeedback.Rating;
import technians.com.vucabsdriver.R;
import technians.com.vucabsdriver.RealmController1;
import technians.com.vucabsdriver.Utilities.Constants;
import technians.com.vucabsdriver.Utilities.SessionManager;


public class RatingFeedbackFragment extends Fragment implements RatingFeedbackMVPView{
    private RecyclerView recyclerView;
    private SessionManager sessionManager;
    RatingFeedbackPresenter presenter;
    ProgressDialog progressDialog;
    Realm realm;
    TextView textViewNoratings;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rating_feedback, container, false);
        RealmController1 realmController1 = new RealmController1(getContext());
        realm= Realm.getInstance(realmController1.initializeDB());
        progressDialog = Constants.showProgressDialog(getActivity());
        recyclerView = view.findViewById(R.id.rating_recyclerview);
        textViewNoratings = view.findViewById(R.id.fragment_rating_tv_norating);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        sessionManager = new SessionManager(getActivity());
        presenter = new RatingFeedbackPresenter();
        presenter.attachView(this);
        presenter.getRatingList();
        return view;
    }

    @Override
    public void onDestroyView() {
        realm.close();
        presenter.detachView();
        super.onDestroyView();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle(getString(R.string.ratingfeedback));
    }

    @Override
    public SessionManager getSession() {
        return sessionManager;
    }

    @Override
    public void showProgress() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @Override
    public void hideProgress() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showApiError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Realm getRealm() {
        return realm;
    }

    @Override
    public void setAdapter(ArrayList<Rating> arrayList) {
        if (arrayList.size()!=0) {
            recyclerView.setVisibility(View.VISIBLE);
            textViewNoratings.setVisibility(View.GONE);
            RatingFeedbackAdapter adapter = new RatingFeedbackAdapter(getActivity(), arrayList);
            recyclerView.setAdapter(adapter);
        }else {
            recyclerView.setVisibility(View.GONE);
            textViewNoratings.setVisibility(View.VISIBLE);
        }
    }
}
