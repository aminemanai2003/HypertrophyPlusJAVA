package controllers;

import entities.Comment;
import entities.Post;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import services.PostService;

import java.sql.SQLException;

public class PostCrud {
    PostService postService;
    public PostCrud() {
        this.postService = new PostService();
    }
    @FXML
    private TableColumn<?, ?> authorCOL;

    @FXML
    private TextField authorTF;

    @FXML
    private TableColumn<Post, String> cdateCOL;

    @FXML
    private DatePicker cdateDP;

    @FXML
    private TableColumn<Post, String> contentCOL;

    @FXML
    private TextField contentTF;

    @FXML
    private TableColumn<Post, Integer> dislikesCOL;

    @FXML
    private TextField dislikesTF;

    @FXML
    private TableColumn<Post, Integer> likesCOL;

    @FXML
    private TextField likesTF;

    @FXML
    private TableView<Post> postTV;

    @FXML
    private TableColumn<Post, String> titleCOL;

    @FXML
    private TextField titleTF;

    @FXML
    void addPost(ActionEvent event) {
        try {
            this.postService.create(new Post(
                    titleTF.getText(),
                    contentTF.getText(),
                    authorTF.getText(),
                    Integer.parseInt(likesTF.getText()),
                    Integer.parseInt(dislikesTF.getText()),
                    cdateDP.getValue().atStartOfDay()
            ));
            ObservableList<Post> obs = FXCollections.observableArrayList(this.postService.readAll());
            postTV.setItems(obs);
            this.dislikesTF.clear();
            this.likesTF.clear();
            this.contentTF.clear();
            this.authorTF.clear();
            this.titleTF.clear();
            this.cdateDP.getEditor().clear();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("create post failed");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }


    }

    @FXML
    void deletePost(ActionEvent event) {
        Post selectedPost = postTV.getSelectionModel().getSelectedItem();
        if(selectedPost != null) {
            try {
                this.postService.delete(selectedPost);
                ObservableList<Post> obs = FXCollections.observableArrayList(this.postService.readAll());
                postTV.setItems(obs);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("delete post failed");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }

    }

    @FXML
    void showComment(ActionEvent event) {
        Post selectedPost = postTV.getSelectionModel().getSelectedItem();
        if (selectedPost != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/CommentCrud.fxml"));
                AnchorPane root = loader.load();

                CommentCrud controller = loader.getController();
                controller.setPostId(selectedPost.getId());
                ObservableList<Comment> obs = FXCollections.observableArrayList(this.postService.readCommentsByPostId(selectedPost.getId()));
                controller.setComments(obs);

                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failed to load comments interface");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

    @FXML
    void updatePost(ActionEvent event) {
        Post selectedPost = postTV.getSelectionModel().getSelectedItem();
        if(selectedPost != null) {
            try {
                selectedPost.setTitle(titleTF.getText());
                selectedPost.setContent(contentTF.getText());
                selectedPost.setAuthor(authorTF.getText());
                selectedPost.setLikes(Integer.parseInt(likesTF.getText()));
                selectedPost.setDislikes(Integer.parseInt(dislikesTF.getText()));
                selectedPost.setCreatedAt(cdateDP.getValue().atStartOfDay());
                this.postService.update(selectedPost);
                ObservableList<Post> obs = FXCollections.observableArrayList(this.postService.readAll());
                postTV.setItems(obs);
                titleTF.clear();
                contentTF.clear();
                authorTF.clear();
                likesTF.clear();
                dislikesTF.clear();
                cdateDP.getEditor().clear();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("update post failed");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }

    }

    @FXML
    void initialize() {
try {
    ObservableList<Post> obs = FXCollections.observableArrayList(this.postService.readAll());
    postTV.setItems(obs);

    authorCOL.setCellValueFactory(new PropertyValueFactory<>("author"));
    titleCOL.setCellValueFactory(new PropertyValueFactory<>("title"));
    contentCOL.setCellValueFactory(new PropertyValueFactory<>("content"));
    cdateCOL.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
    likesCOL.setCellValueFactory(new PropertyValueFactory<>("likes"));
    dislikesCOL.setCellValueFactory(new PropertyValueFactory<>("dislikes"));
} catch (Exception e) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText("Failed to load posts");
    alert.setContentText(e.getMessage());
    alert.showAndWait();
}


    }
}