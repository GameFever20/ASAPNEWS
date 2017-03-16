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
        for ( int i=0 ;i<10 ;i++) {
            topicArrayList.add(new Topic());
        }

        int topicLimit = 3;

        /*pref.getInt("topiclimit",5);*/
        int emptyTopiccell =topicLimit;

        int priority = 0;
        /*
                pref.getInt("India",0);
        */
        if ( priority <=topicLimit ) {
            Topic topic= topicArrayList.get(priority);
            topic.setTopicName("India");
            topic.setTopicPriority(priority);
            topic.setTopicSource("the-times-of-india");
            topic.setTopicStatus(true);
        }else{
            Topic topic= topicArrayList.get(emptyTopiccell);
            topic.setTopicName("India");
            topic.setTopicPriority(priority);
            topic.setTopicSource("the-times-of-india");
            topic.setTopicStatus(false);
            emptyTopiccell++;

        }

        priority =1;
        /*
        pref.getInt("Technology",1);
        */
        if ( priority <=topicLimit ) {
            Topic topic= topicArrayList.get(priority);
            topic.setTopicName("Technology");
            topic.setTopicPriority(priority);
            topic.setTopicSource("the-verge");
            topic.setTopicStatus(true);
        }else{
            Topic topic= topicArrayList.get(emptyTopiccell);
            topic.setTopicName("Technology");
            topic.setTopicPriority(priority);
            topic.setTopicSource("the-verge");
            topic.setTopicStatus(false);
            emptyTopiccell++;

        }

        priority =2;
        /*
        pref.getInt("Science",2);
        */
        if ( priority <=topicLimit ) {
            Topic topic= topicArrayList.get(priority);
            topic.setTopicName("Science");
            topic.setTopicPriority(priority);
            topic.setTopicSource("new-scientist");
            topic.setTopicStatus(true);
        }else{
            Topic topic= topicArrayList.get(emptyTopiccell);
            topic.setTopicName("Science");
            topic.setTopicPriority(priority);
            topic.setTopicSource("new-Scientist");
            topic.setTopicStatus(false);
            emptyTopiccell++;

        }


        return topicArrayList;

    }

}
