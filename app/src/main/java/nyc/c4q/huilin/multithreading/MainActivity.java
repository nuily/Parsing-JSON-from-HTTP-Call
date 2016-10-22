package nyc.c4q.huilin.multithreading;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private TextView helloTV;
    String chuckNorrisAPI = "http://api.icndb.com/jokes/random/";
    InputStream inputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        movieTV = (TextView) findViewById(R.id.hello_tv);
//        movieTV.setText(readInputStream(inputStream));

//        Android blocked this method below because it was on the main thread
//        String output = performNetworkOperation(chuckNorrisAPI); b
        helloTV = (TextView) findViewById(R.id.hello_tv);
        new AsyncHelper().execute();
    }

    public String readInputStream(InputStream inputStream) {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder stringBuilder = new StringBuilder();

        try {
            while (bufferedReader.readLine() != null) {
                String line = bufferedReader.readLine();
                stringBuilder.append(line + "\n");
            }
            //close IO Objects so that they don't stay alive after method finishes
            inputStreamReader.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String jsonText = stringBuilder.toString();
        return jsonText;
    }

    public String performNetworkOperation(String urlEndpoint) {
        try {
            URL url = new URL(urlEndpoint);
            InputStream stream = url.openStream();
            String out = readInputStream(stream);
            return out;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class AsyncHelper extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
//            ... indicates that all the types that will be passed through in comma form (of array)
//            return performNetworkOperation(chuckNorrisAPI);
//            return readInputStream(getResources().openRawResource(R.raw.jsondemo));
            try {
                return grabJoke();
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            create a new Async class to run this method in another thread
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            helloTV.setText(s);
        }

    }

    public String grabJoke() throws JSONException {
        JSONObject obj = new JSONObject(networkRequest());
        JSONObject value = obj.getJSONObject("value");
        return value.optString("joke");
    }

//    public JSONObject downloadJoke(String url) throws JSONException {
//
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder().url(url).build();
//        try {
//            Response response = client.newCall(request).execute();
//            String body = response.body().string();
//            JSONObject json = new JSONObject(body); // option #1 to get my joke
//            String joke = json.optString("joke");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }


//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//
//        int movie;
//        try {
//            movie = inputStream.read();
//            while (movie != -1) {
//                byteArrayOutputStream.write(movie);
//                movie = inputStream.read();
//            }
//            inputStream.close();
//    } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Log.v("Text Data", byteArrayOutputStream.toString());
//        try {
//            JSONObject jObject = new JSONObject(byteArrayOutputStream.toString());
//            JSONObject jObjectResult = jObject.getJSONArray("Movies");
//            for (int i = 0; i )

public String networkRequest() {
        OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(chuckNorrisAPI)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
        }
    }
}
