package technians.com.vucabsdriver;


import android.content.Intent;
import android.os.Bundle;

import technians.com.vucabsdriver.View.Login.LoginWithPhone.LoginWithPhoneActivity;
import wail.splacher.com.splasher.lib.SplasherActivity;
import wail.splacher.com.splasher.models.SplasherConfig;
import wail.splacher.com.splasher.utils.Const;

public class SplashScreenActivity extends SplasherActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initSplasher(SplasherConfig splasherConfig) {
        splasherConfig.setCustomView(R.layout.activity_splash_screen)
                .setReveal_start(Const.START_CENTER)
                .setAnimationDuration(1000);
    }

    @Override
    public void onSplasherFinished() {
        Intent intent = new Intent(SplashScreenActivity.this, LoginWithPhoneActivity.class);
        startActivity(intent);
        finish();
    }
}
