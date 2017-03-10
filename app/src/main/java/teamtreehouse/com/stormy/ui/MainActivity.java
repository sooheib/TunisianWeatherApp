package teamtreehouse.com.stormy.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import teamtreehouse.com.stormy.R;
import teamtreehouse.com.stormy.adapters.ListViewAdapter;
import teamtreehouse.com.stormy.weather.City;
import teamtreehouse.com.stormy.weather.Current;
import teamtreehouse.com.stormy.weather.Day;
import teamtreehouse.com.stormy.weather.Forecast;
import teamtreehouse.com.stormy.weather.Hour;


public class MainActivity extends ActionBarActivity {



    JSONObject jsonobject;
    JSONArray jsonarray;
    ArrayList<String> citylist;
    ArrayList<City> cityy;

    //json string
    private String jsonString = "{\"employee\":" +
            "[" +
            "{\"city\":\"Ariana\",\"latitude\":\"36.866537\",\"longitude\":\"10.164723\"}," +
            "{\"city\":\"Beja\",\"latitude\":\"36.7333193\",\"longitude\":\"9.1843676\"}," +
            "{\"city\":\"BenArous\",\"latitude\":\"36.7435\",\"longitude\":\"10.231976\"},"+
            "{\"city\":\"Bizerte\",\"latitude\":\"37.266667\",\"longitude\":\"9.866667000000007\"}," +
            "{\"city\":\"Gabes\",\"latitude\":\"33.883333\",\"longitude\":\"10.116667000000007\"}," +
            "{\"city\":\"Gafsa\",\"latitude\":\"34.416667\",\"longitude\":\"8.78333299999997\"},"+
            "{\"city\":\"Jendouba\",\"latitude\":\"36.5072263\",\"longitude\":\"8.7756556\"}," +
            "{\"city\":\"Kairouan\",\"latitude\":\"35.695603\",\"longitude\":\"9.723267\"}," +
            "{\"city\":\"Kasserine\",\"latitude\":\"35.2102\",\"longitude\":\"8.8585\"},"+
            "{\"city\":\"Kebili\",\"latitude\":\"33.7058\",\"longitude\":\"8.9706\"}," +
            "{\"city\":\"Kef\",\"latitude\":\"36.1675\",\"longitude\":\"8.7043\"}," +
            "{\"city\":\"Mahdia\",\"latitude\":\"35.5039\",\"longitude\":\"11.0560\"},"+
            "{\"city\":\"Manouba\",\"latitude\":\"36.7624\",\"longitude\":\"9.8336\"}," +
            "{\"city\":\"Medenine\",\"latitude\":\"33.3396\",\"longitude\":\"10.4912\"}," +
            "{\"city\":\"Monastir\",\"latitude\":\"35.7710\",\"longitude\":\"10.8272\"}," +
            "{\"city\":\"Nabeul\",\"latitude\":\"36.4538\",\"longitude\":\"10.7372\"}," +
            "{\"city\":\"Sfax\",\"latitude\":\"34.7397\",\"longitude\":\"10.7599\"}," +
            "{\"city\":\"SidiBouzid\",\"latitude\":\"34.8876\",\"longitude\":\"9.5223\"}," +
            "{\"city\":\"Siliana\",\"latitude\":\"36.0850\",\"longitude\":\"9.3695\"}," +
            "{\"city\":\"Sousse\",\"latitude\":\"35.8288\",\"longitude\":\"10.6405\"}," +
            "{\"city\":\"Tatouine\",\"latitude\":\"32.9292\",\"longitude\":\"10.4512\"}," +
            "{\"city\":\"Tozeur\",\"latitude\":\"33.9204\",\"longitude\":\"8.1338\"}," +
            "{\"city\":\"Tunis\",\"latitude\":\"36.8149\",\"longitude\":\"10.1591\"}," +
            "{\"city\":\"Zaghouan\",\"latitude\":\"36.3973\",\"longitude\":\"10.1462\"}"+

            "]}";


    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String DAILY_FORECAST = "DAILY_FORECAST";
    public static final String HOURLY_FORECAST = "HOURLY_FORECAST";
//    private Spinner spinner;
//    ListViewAdapter adapter;

    // Declare Variables
//    String city = "";
    String lati = "";
    String longi = "";
    ProgressDialog mProgressDialog;
    private Forecast mForecast;

    public static String CITY = "city";
    public static String LATITUDE = "latitude";
    public static String LONGITUDE = "longitude";

    @InjectView(R.id.timeLabel)
    TextView mTimeLabel;
    @InjectView(R.id.temperatureLabel)
    TextView mTemperatureLabel;
    @InjectView(R.id.humidityValue)
    TextView mHumidityValue;
    @InjectView(R.id.precipValue)
    TextView mPrecipValue;
    @InjectView(R.id.summaryLabel)
    TextView mSummaryLabel;
    @InjectView(R.id.iconImageView)
    ImageView mIconImageView;
    @InjectView(R.id.refreshImageView)
    ImageView mRefreshImageView;
    @InjectView(R.id.progressBar)
    ProgressBar mProgressBar;
    //@InjectView(R.id.locationLabel)
    //TextView mlocationLabel;

    //
//    double latitude = 37.8267;
//    double longitude = -122.423;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    private GoogleApiClient client2;


    String city="Ariana";
    double latitude = 36.866537;
    double longitude = 10.164723;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        mProgressBar.setVisibility(View.INVISIBLE);


        if (isNetworkAvailable()) {
            //mlocationLabel.setText(city);
            getForecast(latitude, longitude);

            new DownloadJSON().execute();


            TextView txtlatitude = (TextView) findViewById(R.id.latitude);
            TextView txtlongitude = (TextView) findViewById(R.id.longitude);

            mRefreshImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // getForecast(latitude, longitude);
                    new DownloadJSON().execute();

                }
            });

            //getForecast(latitude, longitude);
            new DownloadJSON().execute();

            Log.d(TAG, "Main UI code is running!");

            // ATTENTION: This was auto-generated to implement the App Indexing API.
            // See https://g.co/AppIndexing/AndroidStudio for more information.
            client2 = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        }
        else {

            city="---";
            //mlocationLabel.setText(city);

            AlertDialogFragmentCnx dialog = new AlertDialogFragmentCnx();
            dialog.show(getFragmentManager(), "error_dialog");
        }






    }

    // Download JSON file AsyncTask
    private class DownloadJSON extends AsyncTask<Void, Void, Void> {

        double latit=1.0;
        double longit=2.0;
        String latie="";
        String longie="";


        @Override
        protected Void doInBackground(Void... params) {
            // Locate the WorldPopulation Class
            cityy = new ArrayList<City>();
            // Create an array to populate the spinner
            citylist = new ArrayList<String>();
            // JSON file URL address
//            jsonobject = JSONfunctions
//                    .getJSONfromURL("http://www.androidbegin.com/tutorial/jsonparsetutorial.txt");

            try {
                jsonobject = new JSONObject(jsonString);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                // Locate the NodeList name
                jsonarray = jsonobject.getJSONArray("employee");
                for (int i = 0; i < jsonarray.length(); i++) {
                    jsonobject = jsonarray.getJSONObject(i);

                    City citylatlon = new City();

                    citylatlon.setCity(jsonobject.optString("city"));
                    citylatlon.setLatitude(jsonobject.optString("latitude"));
                    citylatlon.setLongitude(jsonobject.optString("longitude"));
                    cityy.add(citylatlon);

                    // Populate spinner with country names
                    citylist.add(jsonobject.optString("city"));

                    //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item,);

                    //ArrayAdapter adapter = ArrayAdapter.createFromResource(this,ci, R.layout.spinner_item);

                }
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            // Locate the spinner in activity_main.xml
            Spinner mySpinner = (Spinner) findViewById(R.id.spinnera);


            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                    MainActivity.this,R.layout.spinner_item, citylist);

            //R.layout.spinner_item
            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);

            // Spinner adapter
            mySpinner
                    .setAdapter(spinnerArrayAdapter);

            // Spinner on item click listener
            mySpinner
                    .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> arg0,
                                                   View arg1, int position, long arg3) {
                            // TODO Auto-generated method stub
                            // Locate the textviews in activity_main.xml

                           // TextView txtcity = (TextView) findViewById(R.id.locationLabel);

//                            TextView txtrank = (TextView) findViewById(R.id.rank);
//                            TextView txtcountry = (TextView) findViewById(R.id.country);
//                            TextView txtpopulation = (TextView) findViewById(R.id.population);

//                            txtcity.setText(cityy.get(position).getCity()+" "+cityy.get(position).getLatitude()
//                                    +" "+cityy.get(position).getLongitude()+ " ");

                           // txtcity.setText(cityy.get(position).getCity());

                            // Set the text followed by the position
//                            txtrank.setText("Rank : "
//                                    + world.get(position).getRank());
//                            txtcountry.setText("Country : "
//                                    + world.get(position).getCountry());
//                            txtpopulation.setText("Population : "
//                                    + world.get(position).getPopulation());

                            latie=cityy.get(position).getLatitude();
                            longie=cityy.get(position).getLongitude();

                            latit = Double.parseDouble(latie);
                            longit = Double.parseDouble(longie);

                            getForecast(latit, longit);

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                            // TODO Auto-generated method stub
                        }
                    });
        }
    }

    private void getForecast(double latitude, double longitude) {

        String apiKey = "27974c4bc33201748eaf542a6769c3b7";

        String forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                "/" + latitude + "," + longitude + "?lang=fr";


        if (isNetworkAvailable()) {
            toggleRefresh();

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(forecastUrl)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    alertUserAboutError();
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });

                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
                            mForecast = parseForecastDetails(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                }
                            });
                        } else {
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    } catch (JSONException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }
                }
            });
        } else {
//            Toast.makeText(this, getString(R.string.network_unavailable_message),
//                    Toast.LENGTH_LONG).show();

            AlertDialogFragmentCnx dialog = new AlertDialogFragmentCnx();
            dialog.show(getFragmentManager(), "error_dialog");
        }
    }

    private void toggleRefresh() {
        if (mProgressBar.getVisibility() == View.INVISIBLE) {
            mProgressBar.setVisibility(View.VISIBLE);
            mRefreshImageView.setVisibility(View.INVISIBLE);
        } else {
            mProgressBar.setVisibility(View.INVISIBLE);
            mRefreshImageView.setVisibility(View.VISIBLE);
        }
    }

    private void updateDisplay() {

        Current current = mForecast.getCurrent();
        mTemperatureLabel.setText(current.getTemperature() + "");
        mTimeLabel.setText("à " + current.getFormattedTime() + " ");
        mHumidityValue.setText(current.getHumidity() + " ");
        mPrecipValue.setText(current.getPrecipChance() + "%");
        mSummaryLabel.setText(current.getSummary());
        Drawable drawable = getResources().getDrawable(current.getIconId());
        mIconImageView.setImageDrawable(drawable);
    }

    private Forecast parseForecastDetails(String jsonData) throws JSONException {
        Forecast forecast = new Forecast();

        forecast.setCurrent(getCurrentDetails(jsonData));
        forecast.setHourlyForecast(getHourlyForecast(jsonData));
        forecast.setDailyForecast(getDailyForecast(jsonData));

        return forecast;
    }

    private Day[] getDailyForecast(String jsonData) throws JSONException {

        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        JSONObject daily = forecast.getJSONObject("daily");
        JSONArray data = daily.getJSONArray("data");

        Day[] days = new Day[data.length()];

        for (int i = 0; i < data.length(); i++) {
            JSONObject jsonDay = data.getJSONObject(i);
            Day day = new Day();

            day.setSummary(jsonDay.getString("summary"));
            day.setIcon(jsonDay.getString("icon"));
            day.setTemperatureMax(jsonDay.getDouble("temperatureMax"));
            day.setTime(jsonDay.getLong("time"));
            day.setTimezone(timezone);

            days[i] = day;
        }

        return days;
    }

    private Hour[] getHourlyForecast(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        JSONObject hourly = forecast.getJSONObject("hourly");
        JSONArray data = hourly.getJSONArray("data");

        Hour[] hours = new Hour[data.length()];

        for (int i = 0; i < data.length(); i++) {
            JSONObject jsonHour = data.getJSONObject(i);
            Hour hour = new Hour();

            hour.setSummary(jsonHour.getString("summary"));
            hour.setIcon(jsonHour.getString("icon"));
            hour.setTemperature(jsonHour.getDouble("temperature"));
            hour.setTime(jsonHour.getLong("time"));
            hour.setTimezone(timezone);

            hours[i] = hour;
        }

        return hours;
    }

    private Current getCurrentDetails(String jsonData) throws JSONException {

        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        Log.i(TAG, "From JSON: " + timezone);

        JSONObject currently = forecast.getJSONObject("currently");

        Current current = new Current();
        current.setHumidity(currently.getDouble("humidity"));
        current.setTime(currently.getLong("time"));
        current.setIcon(currently.getString("icon"));
        current.setPrecipChance(currently.getDouble("precipProbability"));
        current.setSummary(currently.getString("summary"));
        current.setTemperature(currently.getDouble("temperature"));
        current.setTimeZone(timezone);
        Log.d(TAG, current.getFormattedTime());

        return current;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }

        return isAvailable;
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }

    @OnClick(R.id.dailyButton)
    public void startDailyActivity(View view) {
        Intent intent = new Intent(this, DailyForecastActivity.class);
        intent.putExtra(DAILY_FORECAST, mForecast.getDailyForecast());
        startActivity(intent);
    }

    @OnClick(R.id.hourlyButton)
    public void startHourlyActivity(View view) {
        Intent intent = new Intent(this, HourlyForecastActivity.class);
        intent.putExtra(HOURLY_FORECAST, mForecast.getHourlyForecast());
        startActivity(intent);
    }




}