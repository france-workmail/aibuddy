package com.snarfapps.aibuddy;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OpenAIClient extends AsyncTask<String, Void, JSONObject> {
    private static final String TAG = "OpenAIClient";
    private static final String API_KEY = Constants.API_KEY;
    private static final String ENDPOINT = "https://api.openai.com/v1/completions";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private OnTaskCompleted listener;

    public OpenAIClient(OnTaskCompleted listener) {
        this.listener = listener;
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        String prompt = params[0];
        String responseString = null;
        JSONObject responseJson = null;

        OkHttpClient client = new OkHttpClient();

        JSONObject data = new JSONObject();
        try {
            data.put("prompt", prompt);
            data.put("max_tokens", 1000);
            data.put("temperature", 0.7);
            data.put("model", "text-davinci-003");
        } catch (JSONException e) {
            Log.e(TAG, "JSON error: " + e.getMessage());
            return null;
        }

        RequestBody requestBody = RequestBody.create(data.toString(), JSON);
        Request request = new Request.Builder()
                .url(ENDPOINT)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + API_KEY)
                .post(requestBody)
                .build();

        try {
            Response response = client.newCall(request).execute();
            responseString = response.body().string();
        } catch (IOException e) {
            Log.e(TAG, "IO error: " + e.getMessage());
            return null;
        }

        try {
            responseJson = new JSONObject(responseString);
        } catch (JSONException e) {
            Log.e(TAG, "JSON error: " + e.getMessage());
            return null;
        }

        return responseJson;
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        try {
            JSONObject firstChoice = result.getJSONArray("choices").getJSONObject(0);
            listener.onTaskCompleted(firstChoice.getString("text"));
        } catch (JSONException e) {
            listener.onTaskError(e.getMessage());
        }catch (Exception e){
            listener.onTaskError(e.getMessage());
        }
    }

    public interface OnTaskCompleted {
        void onTaskCompleted(String result);
        void onTaskError(String er);
    }
}
