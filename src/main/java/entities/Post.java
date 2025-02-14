package entities;

import java.time.LocalDateTime;

public class Post {
    private int post_id;
    private String title;
    private String content;
    private LocalDateTime created_at;

    public Post() {}

    public Post(String title, String content, LocalDateTime created_at) {
        this.title = title;
        this.content = content;
        this.created_at = created_at;
    }

    public Post(int post_id, String title, String content, LocalDateTime created_at) {
        this.post_id = post_id;
        this.title = title;
        this.content = content;
        this.created_at = created_at;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
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

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "Post{" +
                "post_id=" + post_id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", created_at=" + created_at +
                '}';
    }
}
