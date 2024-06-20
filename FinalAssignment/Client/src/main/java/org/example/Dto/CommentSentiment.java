package org.example.Dto;

public class CommentSentiment {
    private String comment;
    private int sentiment;

    public CommentSentiment(String comment, int sentiment) {
        this.comment = comment;
        this.sentiment = sentiment;
    }

    public String getComment() {
        return comment;
    }

    public int getSentiment() {
        return sentiment;
    }
}
