package utils;

/**
 * Created by gamef on 15-03-2017.
 */

public class Topic {
    private String topicName ="";
    private String topicSource ="";
    private int topicPriority =10 ,topicStartIndex =0;
    private boolean topicStatus;

    public boolean isTopicStatus() {
        return topicStatus;
    }

    public void setTopicStatus(boolean topicStatus) {
        this.topicStatus = topicStatus;
    }

    public Topic() {
    }

    public Topic(String topicName) {

        this.topicName = topicName;
    }

    public String getTopicSource() {
        return topicSource;
    }

    public void setTopicSource(String topicSource) {
        this.topicSource = topicSource;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public int getTopicPriority() {
        return topicPriority;
    }

    public void setTopicPriority(int topicPriority) {
        this.topicPriority = topicPriority;
    }

    public int getTopicStartIndex() {
        return topicStartIndex;
    }

    public void setTopicStartIndex(int topicStartIndex) {
        this.topicStartIndex = topicStartIndex;
    }


}
