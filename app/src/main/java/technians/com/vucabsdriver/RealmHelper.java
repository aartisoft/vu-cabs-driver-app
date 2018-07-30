package technians.com.vucabsdriver;

import java.util.ArrayList;

import io.realm.Realm;
import technians.com.vucabsdriver.Model.Profile.Profile;

public class RealmHelper {
    Realm realm;

    public RealmHelper(Realm realm) {
        this.realm = realm;
    }

    public void saveProfile(final Profile profile){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(Profile.class).findAll().deleteAllFromRealm();
                realm.copyToRealmOrUpdate(profile);
            }
        });
    }
//
    public Profile retrieveProfile(){

        return realm.where(Profile.class).findFirst();
    }
//
//
//
//    //WRITE
//    public void save(final RealmLocation realmLocation)
//    {
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//
//                RealmLocation s=realm.copyToRealm(realmLocation);
//
//            }
//        });
//    }
//
//    //READ/RETRIEVE
//    public ArrayList<RealmLocation> retrieve()
//    {
//        RealmResults<RealmLocation> realmLocations=realm.where(RealmLocation.class).findAll();
//
//        return new ArrayList<>(realmLocations);
//    }
}
