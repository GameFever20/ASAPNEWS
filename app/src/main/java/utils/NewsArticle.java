package utils;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gamef on 12-03-2017.
 */

public class NewsArticle implements Parcelable {
    /*this clss consist of detaials regarding a  news article */

    private String newsAuthor;
    private String newsTitle;
private     String newsDescription;
    private String newsURL;
    private String newsImageURl;
    private String newsPublishedAt;
    private Bitmap newsImage;
    private String newsSource;
    private String newsTopic;

    public String getNewsSource() {
        return newsSource;
    }

    public void setNewsSource(String newsSource) {
        this.newsSource = newsSource;
    }

    public String getNewsTopic() {
        return newsTopic;
    }

    public void setNewsTopic(String newsTopic) {
        this.newsTopic = newsTopic;
    }

    public String getNewsPublishedAt() {
        return newsPublishedAt;
    }

    public void setNewsPublishedAt(String newsPublishedAt) {
        this.newsPublishedAt = newsPublishedAt;
    }



    public NewsArticle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public NewsArticle() {

    }

    public String getNewsAuthor() {

        return newsAuthor;
    }

    public void setNewsAuthor(String newsAuthor) {
        this.newsAuthor = newsAuthor;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsDescription() {
        return newsDescription;
    }

    public void setNewsDescription(String newsDescription) {
        this.newsDescription = newsDescription;
    }

    public String getNewsURL() {
        return newsURL;
    }

    public void setNewsURL(String newsURL) {
        this.newsURL = newsURL;
    }

    public String getNewsImageURl() {
        return newsImageURl;
    }

    public void setNewsImageURl(String newsImageURl) {
        this.newsImageURl = newsImageURl;
    }

    public Bitmap getNewsImage() {
        return newsImage;
    }

    public void setNewsImage(Bitmap newsImage) {
        this.newsImage = newsImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(newsAuthor);
        dest.writeString(newsDescription);
        dest.writeString(newsURL);
        dest.writeString(newsImageURl);
        dest.writeString(newsTitle);
        dest.writeString(newsPublishedAt);
        dest.writeParcelable(newsImage ,1);
        dest.writeString(newsSource);
        dest.writeString(newsTopic);


    }

    private NewsArticle(Parcel in){
        this.newsAuthor = in.readString();
        this.newsDescription = in.readString();
        this.newsTitle = in.readString();
        this.newsURL = in.readString();
        this.newsImageURl = in.readString();
        this.newsPublishedAt = in.readString();
        this.newsSource=in.readString();
        this.newsTopic=in.readString();



    }

    public static final Parcelable.Creator<NewsArticle> CREATOR = new Parcelable.Creator<NewsArticle>() {

        @Override
        public NewsArticle createFromParcel(Parcel source) {
            return new NewsArticle(source);
        }

        @Override
        public NewsArticle[] newArray(int size) {
            return new NewsArticle[size];
        }
    };



}
