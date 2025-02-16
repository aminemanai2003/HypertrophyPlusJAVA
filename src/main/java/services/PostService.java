package services;

import entities.Comment;
import entities.Post;
import utils.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostService implements IService<Post> {
    private Connection cnx;

    public PostService() {
        cnx = MyConnection.getInstance().getConnection();
    }

    @Override
    public void create(Post post) throws SQLException {
        String query = "INSERT INTO post (title, content, author, likes, dislikes, created_at) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, post.getTitle());
            ps.setString(2, post.getContent());
            ps.setString(3, post.getAuthor());
            ps.setInt(4, post.getLikes());
            ps.setInt(5, post.getDislikes());
            ps.setTimestamp(6, Timestamp.valueOf(post.getCreatedAt()));

            ps.executeUpdate();

            // Get the generated ID and set it in the post object
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    post.setPostId(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public void update(Post post) throws SQLException {
        String query = "UPDATE post SET title = ?, content = ?, author = ?, likes = ?, dislikes = ?, created_at = ? WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, post.getTitle());
            ps.setString(2, post.getContent());
            ps.setString(3, post.getAuthor());
            ps.setInt(4, post.getLikes());
            ps.setInt(5, post.getDislikes());
            ps.setTimestamp(6, Timestamp.valueOf(post.getCreatedAt()));
            ps.setInt(7, post.getPostId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Post post) throws SQLException {
        String query = "DELETE FROM post WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, post.getPostId());
            ps.executeUpdate();
        }
    }

    @Override
    public List<Post> readAll() throws SQLException {
        List<Post> posts = new ArrayList<>();
        String query = "SELECT * FROM post";
        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                Post post = new Post(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("author"),
                        rs.getInt("likes"),
                        rs.getInt("dislikes"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                );
                posts.add(post);
            }
        }
        return posts;
    }

    // Additional useful methods
    public Post readById(int postId) throws SQLException {
        String query = "SELECT * FROM post WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, postId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Post(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("content"),
                            rs.getString("author"),
                            rs.getInt("likes"),
                            rs.getInt("dislikes"),
                            rs.getTimestamp("created_at").toLocalDateTime()
                    );
                }
            }
        }
        return null;
    }

    public void incrementLikes(int postId) throws SQLException {
        String query = "UPDATE post SET likes = likes + 1 WHERE post_id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, postId);
            ps.executeUpdate();
        }
    }

    public void incrementDislikes(int postId) throws SQLException {
        String query = "UPDATE post SET dislikes = dislikes + 1 WHERE post_id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, postId);
            ps.executeUpdate();
        }
    }

    public List<Comment> readCommentsByPostId(int postId) throws SQLException {
        CommentService commentService = new CommentService();
        return commentService.readByPostId(postId);
    }
}