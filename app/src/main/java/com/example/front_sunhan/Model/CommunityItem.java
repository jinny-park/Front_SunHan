package com.example.front_sunhan.Model;

public class CommunityItem {
    int userProfile;
    String userId;
    String uploadTime;
    String content;
    int commentNum;

    public CommunityItem(int userProfile, String userId, String uploadTime, String content, int commentNum) {
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

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }
}
