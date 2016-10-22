package nyc.c4q.huilin.multithreading;

import org.json.JSONObject;

/**
 * Created by huilin on 10/22/16.
 */

public class ChuckNorrisJoke {

    private String joke;

    public ChuckNorrisJoke(JSONObject jsonObject) {
        this.joke = jsonObject.optString("joke");
//        log.d()
    }
}
