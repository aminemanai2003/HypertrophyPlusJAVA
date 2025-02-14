package services;

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
        String query = "INSERT INTO post (title, content, created_at) VALUES (?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, post.getTitle());
            ps.setString(2, post.getContent());
            ps.setTimestamp(3, Timestamp.valueOf(post.getCreated_at()));
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Post post) throws SQLException {
        String query = "UPDATE post SET title = ?, content = ?, created_at = ? WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, post.getTitle());
            ps.setString(2, post.getContent());
            ps.setTimestamp(3, Timestamp.valueOf(post.getCreated_at()));
            ps.setInt(4, post.getPost_id()); // Updated to match 'id'
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Post post) throws SQLException {
        String query = "DELETE FROM post WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, post.getPost_id()); // Updated to match 'id'
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
                Post post = new Post();
                post.setPost_id(rs.getInt("id")); // Updated to match 'id'
                post.setTitle(rs.getString("title"));
                post.setContent(rs.getString("content"));
                post.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
                posts.add(post);
            }
        }
        return posts;
    }
}
