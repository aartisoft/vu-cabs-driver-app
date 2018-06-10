package technians.com.vucabsdriver.CustomInfoWindow;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import technians.com.vucabsdriver.R;

public class CustomInfoWindowGoogleMap implements GoogleMap.InfoWindowAdapter {

    private Context context;

    public CustomInfoWindowGoogleMap(Context ctx){
        context = ctx;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity)context).getLayoutInflater()
                .inflate(R.layout.custominfowindow, null);

        TextView mylocation = view.findViewById(R.id.tv_subtitle);
        TextView tv_lastupdated = view.findViewById(R.id.tv_lastupdated);
        TextView tv_status = view.findViewById(R.id.tv_status);

        mylocation.setText(marker.getSnippet());

        InfoWindowData infoWindowData = (InfoWindowData) marker.getTag();

        tv_lastupdated.setText(infoWindowData.getLast_updated());
        tv_status.setText(infoWindowData.getStatus());

        return view;
    }
}

