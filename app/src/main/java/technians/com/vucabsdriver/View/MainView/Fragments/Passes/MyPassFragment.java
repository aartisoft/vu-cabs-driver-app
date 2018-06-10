package technians.com.vucabsdriver.View.MainView.Fragments.Passes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.vipulasri.ticketview.TicketView;

import io.realm.Realm;
import technians.com.vucabsdriver.PayUMoneyIntegrate.PayUMoneyActivity;
import technians.com.vucabsdriver.R;
import technians.com.vucabsdriver.Utilities.SessionManager;

import static technians.com.vucabsdriver.Utilities.Constants.PayUMoneyRequestCode;
import static technians.com.vucabsdriver.Utilities.Constants.showProgressDialog;


public class MyPassFragment extends Fragment implements MyPassesMVPView, View.OnClickListener {

    SessionManager sessionManager;
    ProgressDialog progressDialog;
    MyPassesPresenter presenter;
    private Realm realm;
    TextView tv_TotalRides, tv_Validity, tv_RidesAmount, tv_Ridesleft;
    Button btn_renewPass;
    int Amount = 0, Rides = 0;
    TicketView ticketView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_pass, container, false);
        Realm.init(getActivity());
        realm = Realm.getDefaultInstance();
        sessionManager = new SessionManager(getActivity());
        progressDialog = showProgressDialog(getActivity());
        presenter = new MyPassesPresenter();
        presenter.attachView(this);

        ticketView = view.findViewById(R.id.ticketView);
        tv_TotalRides = view.findViewById(R.id.fragment_mypass_ridescount);
        tv_Validity = view.findViewById(R.id.fragment_mypass_validity);
        tv_RidesAmount = view.findViewById(R.id.fragment_mypass_rideamount);
        tv_Ridesleft = view.findViewById(R.id.fragment_mypass_ridesleft);
        btn_renewPass = view.findViewById(R.id.fragment_btn_renewpass);
        btn_renewPass.setOnClickListener(this);
        presenter.getPassesDetails();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle(getString(R.string.mypasses));
    }

    @Override
    public void onDestroy() {
        realm.close();
        presenter.detachView();
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        realm.close();
        presenter.detachView();
        super.onDestroyView();
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
    public void setdata(String ridesLeft, String totalRides, String rideAmount) {
        Amount = Integer.valueOf(rideAmount);
        Rides = Integer.valueOf(totalRides);
        int RidesLeft = Integer.valueOf(ridesLeft);
        int TotalRides = Integer.valueOf(totalRides);
        tv_TotalRides.setText(String.format("%s Rides", totalRides));
        tv_RidesAmount.setText(String.format("₹ %s", rideAmount));
        if (RidesLeft < TotalRides && RidesLeft != 0 ||RidesLeft == TotalRides) {
            tv_Ridesleft.setText(String.format("%s/%s", ridesLeft, totalRides));
        } else if (RidesLeft > TotalRides) {
            tv_Ridesleft.setText(String.format("%s/%s", ridesLeft, ridesLeft));
        } else if (RidesLeft == 0) {
            SpannableStringBuilder builder = new SpannableStringBuilder();
            SpannableString redSpannable = new SpannableString(ridesLeft);
            redSpannable.setSpan(new ForegroundColorSpan(Color.RED), 0, ridesLeft.length(), 0);
            builder.append(redSpannable);
            builder.append(String.format("/%s", totalRides));
            tv_Ridesleft.setText(builder, TextView.BufferType.SPANNABLE);

        }
        if (RidesLeft == 0) {

            tv_Validity.setText(getString(R.string.expired));
            tv_Validity.setTextColor(Color.RED);
            ticketView.setBorderColor(Color.RED);
        } else if (RidesLeft > 0) {
            tv_Validity.setText(getString(R.string.unlimitedvalidity));
            ticketView.setBorderColor(getResources().getColor(R.color.colorAccent));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_btn_renewpass: {
                getActivity().startActivityForResult(new Intent(getActivity(), PayUMoneyActivity.class)
                        .putExtra("Amount", Amount)
                        .putExtra("Rides_qty", Rides), PayUMoneyRequestCode);
                break;
            }
        }
    }
}
