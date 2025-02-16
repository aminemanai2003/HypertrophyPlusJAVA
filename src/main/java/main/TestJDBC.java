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
        Comment comment = new Comment(3,3,"au55th5or","co555ntent",LocalDateTime.now());



        try {


            // Test PostService: Insert post and verify
            System.out.println("Testing postService:");
            commentService.create(comment);
            System.out.println(commentService.readAll()); // Output all posts to verify insertion


        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
