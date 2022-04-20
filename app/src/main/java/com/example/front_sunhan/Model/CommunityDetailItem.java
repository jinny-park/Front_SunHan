package com.example.front_sunhan.Model;

public class CommunityDetailItem {
    int userProfile;
    String userId;
    String content;
    String commentDate;
    String commentTime;

    public CommunityDetailItem(int userProfile, String userId, String content, String commentDate, String commentTime) {
        this.userProfile = userProfile;
        this.userId = userId;
        this.content = content;
        this.commentDate = commentDate;
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

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }
}