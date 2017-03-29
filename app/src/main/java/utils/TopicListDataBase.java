package utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.sql.Array;
import java.util.ArrayList;

/**
 * Created by gamef on 15-03-2017.
 */

public class TopicListDataBase {

    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;
    private static final String PREF_NAME = "topicSeq";


    public TopicListDataBase(Context _context) {
        this._context = _context;
        //pref = _context.getSharedPreferences(PREF_NAME, 0);
        //editor = pref.edit();
    }


    public ArrayList<Topic> getTopicList(){

        ArrayList<Topic> topicArrayList =new ArrayList<>();

        Topic topic= new Topic();
        topic.setTopicName("India");
        topic.setTopicPriority(0);
        topic.setTopicSource("the-times-of-india");
        topic.setTopicStatus(true);

        topicArrayList.add(topic);

        Topic topic2= new Topic();
        topic2.setTopicName("International");
        topic2.setTopicPriority(1);
        topic2.setTopicSource("bloomberg");
        topic2.setTopicStatus(true);
        topicArrayList.add(topic2);

        Topic topic3= new Topic();
        topic3.setTopicName("Technology");
        topic3.setTopicPriority(2);
        topic3.setTopicSource("the-verge");
        topic3.setTopicStatus(true);
        topicArrayList.add(topic3);

        Topic topic4= new Topic();
        topic4.setTopicName("Science");
        topic4.setTopicPriority(3);
        topic4.setTopicSource("new-scientist");
        topic4.setTopicStatus(true);
        topicArrayList.add(topic4);

        Topic topic5= new Topic();
        topic5.setTopicName("Cricket");
        topic5.setTopicPriority(4);
        topic5.setTopicSource("espn-cric-info");
        topic5.setTopicStatus(true);
        topicArrayList.add(topic5);




        return topicArrayList;

    }

}
