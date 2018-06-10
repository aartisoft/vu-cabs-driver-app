package technians.com.vucabsdriver.View.MainView.Fragments.RatingFeedback;

import java.util.ArrayList;

import io.realm.Realm;
import technians.com.vucabsdriver.View.MVPView;
import technians.com.vucabsdriver.model.RatingFeedback.Rating;

public interface RatingFeedbackMVPView extends MVPView{
    Realm getRealm();

    void setAdapter(ArrayList<Rating> arrayList);
}
