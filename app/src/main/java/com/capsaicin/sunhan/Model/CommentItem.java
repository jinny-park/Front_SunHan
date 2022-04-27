package com.capsaicin.sunhan.Model;

public class CommentItem {
    int userProfile;
    String userId;
    String content;
    String commentTime;

    public CommentItem(int userProfile, String userId, String content, String commentTime) {
        this.userProfile = userProfile;
        this.userId = userId;
        this.content = content;
        this.commentTime = commentTime;
    }

    public int getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(int userProfile) {
        this.userProfile = userProfile;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }
}