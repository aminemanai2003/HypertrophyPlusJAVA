package services;

import entities.Comment;
import utils.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentService implements IService<Comment> {

    private Connection cnx;

    public CommentService() {
        cnx = MyConnection.getInstance().getConnection();
    }

    @Override
    public void create(Comment comment) throws SQLException {
        String query = "INSERT INTO Comment (post_id, author, content, created_at) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, comment.getPostId());  // Link comment to a post
            ps.setString(2, comment.getAuthor());
            ps.setString(3, comment.getContent());
            ps.setTimestamp(4, Timestamp.valueOf(comment.getCreatedAt()));
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Comment comment) throws SQLException {
        String query = "UPDATE Comment SET post_id = ?, author = ?, content = ?, created_at = ? WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, comment.getPostId());  // Ensure the post_id is updated correctly
            ps.setString(2, comment.getAuthor());
            ps.setString(3, comment.getContent());
            ps.setTimestamp(4, Timestamp.valueOf(comment.getCreatedAt()));
            ps.setInt(5, comment.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Comment comment) throws SQLException {
        String query = "DELETE FROM Comment WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, comment.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public List<Comment> readAll() throws SQLException {
        List<Comment> comments = new ArrayList<>();
        String query = "SELECT * FROM Comment";
        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                Comment comment = new Comment(
                        rs.getInt("id"),
                        rs.getInt("post_id"),
                        rs.getString("author"),
                        rs.getString("content"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                );
                comments.add(comment);
            }
        }
        return comments;
    }
}
