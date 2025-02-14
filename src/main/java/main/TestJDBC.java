package main;

import entities.*;
import services.PostService;
import services.CommentService;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class TestJDBC {

    public static void main(String[] args) {
        // Create instances of services
        PostService postService = new PostService();
        CommentService commentService = new CommentService();



        // Create instances of entities
        Post post = new Post(1, "poddst", "This is the content of my first post", LocalDateTime.now());
        Comment comment = new Comment(2,2, "amddine", "comment", LocalDateTime.now());


        try {


            // Test PostService: Insert post and verify
            System.out.println("Testing postService:");
            commentService.delete(comment);
            System.out.println(commentService.readAll()); // Output all posts to verify insertion


        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
