package technians.com.vucabsdriver;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmController1{
    private Context context;

    public RealmController1(Context context) {
        this.context = context;
    }

    public RealmConfiguration initializeDB() {
        Realm.init(context);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("Vucabsdriver.realm")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfig);
        return realmConfig;
    }

}
