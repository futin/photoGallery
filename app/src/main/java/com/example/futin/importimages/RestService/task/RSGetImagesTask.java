package com.example.futin.importimages.RestService.task;

import android.os.AsyncTask;
import android.util.Log;

import com.example.futin.importimages.RestService.data.RSDataSingleton;
import com.example.futin.importimages.RestService.listeners.AsyncTaskListener;
import com.example.futin.importimages.RestService.models.Image;
import com.example.futin.importimages.RestService.response.RSGetImagesResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

/**
 * Created by Futin on 12/22/2015.
 */
public class RSGetImagesTask extends AsyncTask<Void, Void, RSGetImagesResponse> {
    final String TAG="getImages";
    RestTemplate restTemplate;
    AsyncTaskListener returnData;

    public RSGetImagesTask( AsyncTaskListener returnData) {
        this.returnData = returnData;
        restTemplate=new RestTemplate();
    }

    @Override
    protected RSGetImagesResponse doInBackground(Void... params) {
        try {
            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            header.set("Connection", "Close");
            String jsonText = "";
            HttpEntity<String> entity = new HttpEntity<>(jsonText, header);
            String address = RSDataSingleton.getInstance().getServerUrl().getImagesUrl();

            Log.i(TAG, "Address: " + address);
            ResponseEntity response = restTemplate.exchange(address, HttpMethod.GET, entity, String.class);
            Log.i(TAG, "After response ");
            if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                return new RSGetImagesResponse(HttpStatus.NOT_FOUND,
                        HttpStatus.NOT_FOUND.name());
            } else if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
                return new RSGetImagesResponse(HttpStatus.BAD_REQUEST,
                        HttpStatus.BAD_REQUEST.name());
            }else if (response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                return new RSGetImagesResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                        HttpStatus.INTERNAL_SERVER_ERROR.name());
            } else {
                Log.i(TAG, "Response ok ");
                String jsonBody=response.getBody().toString();
                JSONArray array=new JSONArray(jsonBody);
                ArrayList<Image> listOfImages=new ArrayList<>();
                for (int i=0; i<array.length();i++){
                    JSONObject objCity=array.getJSONObject(i);

                    String name=objCity.getString("name");
                    String url=objCity.getString("url");

                    Image image=new Image(name, url);
                    listOfImages.add(image);
                }
                Log.i(TAG, "Images: "+listOfImages.toString());

                return new RSGetImagesResponse(HttpStatus.OK,
                        HttpStatus.OK.name(),listOfImages);
            }


        } catch (HttpClientErrorException e) {
            Log.e(TAG, "Http Status: " + e.getStatusCode());
            Log.e(TAG, "Http Error: " + e.getResponseBodyAsString());
            return new RSGetImagesResponse(e.getStatusCode(),
                    e.getStatusText());
        } catch (HttpServerErrorException e) {
            Log.e(TAG, "Http Status: " + e.getStatusCode());
            Log.e(TAG, "Http Error: " + e.getResponseBodyAsString());
            return new RSGetImagesResponse(e.getStatusCode(),
                    e.getStatusText());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return new RSGetImagesResponse(null, null);
        }
    }

    @Override
    protected void onPostExecute(RSGetImagesResponse rsGetCitiesResponse) {
        super.onPostExecute(rsGetCitiesResponse);
        Log.i(TAG, "On post ok");
        returnData.returnDataOnPostExecute(rsGetCitiesResponse);
    }
}
