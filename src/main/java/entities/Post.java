package entities;

import java.time.LocalDateTime;

public class Post {
    private int postId; // Changed to camelCase
    private String title;
    private String content;
    private String author; // Added author field
    private int likes; // Added likes field
    private int dislikes; // Added dislikes field
    private LocalDateTime createdAt; // Changed to camelCase

    // Default constructor
    public Post() {
    }

    // Constructor without postId (for creating new posts)
    public Post(String title, String content, String author, int likes, int dislikes, LocalDateTime createdAt) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.likes = likes;
        this.dislikes = dislikes;
        this.createdAt = createdAt;
    }

    // Constructor with postId (for retrieving existing posts)
    public Post(int postId, String title, String content, String author, int likes, int dislikes, LocalDateTime createdAt) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.author = author;
        this.likes = likes;
        this.dislikes = dislikes;
        this.createdAt = createdAt;
    }

    // Constructor with only postId (for referencing posts)
    //public Post(int postId) {
    //    this.postId = postId;
    //}



    // Getters and Setters
    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // toString method
    @Override
    public String toString() {
        return String.format(
                "Post{postId=%d, title='%s', content='%s', author='%s', likes=%d, dislikes=%d, createdAt=%s}",
                postId, title, content, author, likes, dislikes, createdAt
        );
    }

    public int getId() {
        return postId;

    }
}