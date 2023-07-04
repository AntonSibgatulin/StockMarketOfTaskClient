package ru.antonsibgatulin.stockmarketoftaskclient.include.notification;

public class NotificationModel {

    private String mainText;
    private String text;
    private long time;

    public NotificationModel(String mainText, String text, long time) {
        this.mainText = mainText;
        this.text = text;
        this.time = time;
    }

    public String getMainText() {
        return mainText;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
