package utils;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import news_reader.news.asap.appforyou.asapnews.NewsList;

import static utils.AppController.TAG;

/**
 * Created by gamef on 12-03-2017.
 */

public class FetchNews {
    NewsList activity;
    ArrayList <Topic > topicArrayList;
    int currentFetchingItem=0;


    public FetchNews(NewsList activity, ArrayList<Topic> topicArrayList) {
        this.activity = activity;
        this.topicArrayList = topicArrayList;

    }

    public void startFetching() {


           if (topicArrayList.size() >currentFetchingItem) {
               fetchnewsFromSource(topicArrayList.get(currentFetchingItem).getTopicSource() ,
                       currentFetchingItem);
               currentFetchingItem++;

           }else{

           }


    }

    private void fetchnewsFromSource(String topicSource, final int topicPriority) {

        // Tag used to cancel the request
        String tag_json_obj = "json_obj_req";

        String url = "https://newsapi.org/v1/articles?source="+topicSource+"&sortBy=top&apiKey" +
                "=0e46f631264e40f28d93b7b7e22ea320";

        /*ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();*/

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());

                        // pDialog.hide();


                        activity.fetchNewsFromSourceListner(extractJSON(response , topicArrayList.get(topicPriority).getTopicName()) ,topicPriority);

                        startFetching();

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                // hide the progress dialog
                //pDialog.hide();
            }
        });

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);



    }

    public FetchNews(NewsList activity ) {
        this.activity = activity;
    }

    public FetchNews() {
    }

    public void fetchTOINews() {

        // Tag used to cancel the request
        String tag_json_obj = "json_obj_req";

        String url = "https://newsapi.org/v1/articles?source=the-times-of-india&sortBy=top&apiKey" +
                "=0e46f631264e40f28d93b7b7e22ea320";

        /*ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();*/

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());

                        // pDialog.hide();

                        activity.fetchToiNewsListner(extractJSON(response));
                        fetchEngadgetNews();


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                // hide the progress dialog
                //pDialog.hide();
            }
        });

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);


    }

    private ArrayList<NewsArticle> extractJSON(JSONObject response) {

        ArrayList<NewsArticle> newsArraylist = new ArrayList<>();

        try {
            JSONArray newsArticles = response.getJSONArray("articles");
            String source = response.getString("source");

            for ( int i = 0; i < newsArticles.length(); i++ ) {
                JSONObject news = newsArticles.getJSONObject(i);
                NewsArticle newsArticle = new NewsArticle();

                newsArticle.setNewsTitle(news.getString("title"));
                newsArticle.setNewsDescription(news.getString("description"));
                newsArticle.setNewsAuthor(news.getString("author"));
                newsArticle.setNewsImageURl(news.getString("urlToImage"));
                newsArticle.setNewsURL(news.getString("url"));
                newsArticle.setNewsPublishedAt(news.getString("publishedAt"));
                newsArticle.setNewsSource(source);

                newsArraylist.add(newsArticle);

            }

            Log.d(TAG, "extractJSON: " + newsArraylist.size());

            fetchImage(newsArraylist );

            return newsArraylist;

        } catch (JSONException e) {
            e.printStackTrace();
            return newsArraylist;

        }


    }

    private ArrayList<NewsArticle> extractJSON(JSONObject response ,String topic) {

        ArrayList<NewsArticle> newsArraylist = new ArrayList<>();

        try {
            JSONArray newsArticles = response.getJSONArray("articles");
            String source = response.getString("source");

            for ( int i = 0; i < newsArticles.length(); i++ ) {
                JSONObject news = newsArticles.getJSONObject(i);
                NewsArticle newsArticle = new NewsArticle();

                newsArticle.setNewsTitle(news.getString("title"));
                newsArticle.setNewsDescription(news.getString("description"));
                newsArticle.setNewsAuthor(news.getString("author"));
                newsArticle.setNewsImageURl(news.getString("urlToImage"));
                newsArticle.setNewsURL(news.getString("url"));
                newsArticle.setNewsPublishedAt(news.getString("publishedAt"));
                newsArticle.setNewsSource(source);
                newsArticle.setNewsTopic(topic);

                newsArraylist.add(newsArticle);

            }

            Log.d(TAG, "extractJSON: " + newsArraylist.size());

            fetchImage(newsArraylist );

            return newsArraylist;

        } catch (JSONException e) {
            e.printStackTrace();
            return newsArraylist;

        }


    }


    public void fetchImage(final ArrayList<NewsArticle> newsArticleArrayList) {


        for ( int i = 0; i < newsArticleArrayList.size(); i++ ) {


            ImageLoader imageLoader = AppController.getInstance().getImageLoader();

// If you are using normal ImageView
            imageLoader.get(newsArticleArrayList.get(i).getNewsImageURl(), new ImageLoader.ImageListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "Image Load Error: " + error.getMessage());
                }

                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                    if ( response.getBitmap() != null ) {
                        // load image into imageview
                        //imageView.setImageBitmap(response.getBitmap());

                        for(int j=0 ; j<newsArticleArrayList.size() ; j++){
                           if ( newsArticleArrayList.get(j).getNewsImageURl().equals(response
                                    .getRequestUrl())){
                               newsArticleArrayList.get(j).setNewsImage(response.getBitmap());
                               activity.onFetchImageCallBack(j);

                               j=newsArticleArrayList.size()+1;

                           }
                        }

                    }
                }
            });

        }

    }

    public void fetchEngadgetNews(){


        // Tag used to cancel the request
        String tag_json_obj = "json_obj_req";

        String url = "https://newsapi.org/v1/articles?source=engadget&sortBy=latest&apiKey" +
                "=0e46f631264e40f28d93b7b7e22ea320";

        /*ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();*/

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());

                        // pDialog.hide();


                        activity.fetchEngadgetListner(extractJSON(response));


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                // hide the progress dialog
                //pDialog.hide();
            }
        });

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);




    }

}
