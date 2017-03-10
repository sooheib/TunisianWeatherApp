package teamtreehouse.com.stormy.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import teamtreehouse.com.stormy.R;
import teamtreehouse.com.stormy.ui.MainActivity;
import teamtreehouse.com.stormy.ui.SingleItemView;
import teamtreehouse.com.stormy.ui.MainList;

public class ListViewAdapter extends BaseAdapter {

    // Declare Variables
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;

    public ListViewAdapter(Context context,
                           ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        // Declare Variables
        TextView city;
        TextView latitude;
        TextView longitude;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.listview_item, parent, false);
        // Get the position from the results
        HashMap<String, String> resultp = new HashMap<String, String>();
        resultp = data.get(position);

        // Locate the TextViews in listview_item.xml
        city = (TextView) itemView.findViewById(R.id.city);
       // latitude = (TextView) itemView.findViewById(R.id.latitude);
       // longitude = (TextView) itemView.findViewById(R.id.longitude);
        // Locate the ImageView in listview_item.xml

        // Capture position and set results to the TextViews
        city.setText(resultp.get(MainList.CITY));
        System.out.println(resultp.get(MainList.CITY));
        System.out.println(resultp.get(MainList.LONGITUDE));
        System.out.println(resultp.get(MainList.LATITUDE));


        //latitude.setText(resultp.get(MainList.LATITUDE));
        //longitude.setText(resultp.get(MainList.LONGITUDE));
        // Capture position and set results to the ImageView
        // Passes flag images URL into ImageLoader.class to download and cache
        // images
        // Capture button clicks on ListView items
        itemView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Get the position from the results
                HashMap<String, String> resultp = new HashMap<String, String>();
                resultp = data.get(position);
                // Send single item click data to SingleItemView Class
                Intent intent = new Intent(context, MainActivity.class);
                // Pass all data rank
                intent.putExtra("city", resultp.get(MainList.CITY));
                System.out.println(resultp.get(MainList.CITY));
                // Pass all data country
                intent.putExtra("latitude", resultp.get(MainList.LATITUDE));
                //System.out.println(resultp.get(MainList.LATITUDE);

                // Pass all data population
                intent.putExtra("longitude",
                        resultp.get(MainList.LONGITUDE));
               // System.out.println(resultp.get(MainList.LONGITUDE));


                // Start SingleItemView Class
                context.startActivity(intent);
            }
        });

        return itemView;
    }
}
