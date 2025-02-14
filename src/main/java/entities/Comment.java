package entities;

import java.time.LocalDateTime;

public class Comment {
    private int id;
    private int postId;
    private String author;
    private String content;
    private LocalDateTime createdAt;

    public Comment() {}

    public Comment(int postId, String author, String content, LocalDateTime createdAt) {
        this.postId = postId;
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
    }

    public Comment(int id, int postId, String author, String content, LocalDateTime createdAt) {
        this.id = id;
        this.postId = postId;
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", postId=" + postId +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
