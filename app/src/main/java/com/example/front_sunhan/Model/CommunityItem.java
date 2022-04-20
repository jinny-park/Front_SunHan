package com.example.front_sunhan.Model;

public class CommunityItem {
    int userProfile;
    String userId;
    String uploadTime;
    String content;
    String commentNum;

    public CommunityItem(int userProfile, String userId, String uploadTime, String content, String commentNum) {
        this.userProfile = userProfile;
        this.userId = userId;
        this.uploadTime = uploadTime;
        this.content = content;
        this.commentNum = commentNum;
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

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(String commentNum) {
        this.commentNum = commentNum;
    }
}
