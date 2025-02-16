package controllers;

import entities.Comment;
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
import services.CommentService;

import java.sql.SQLException;

public class CommentCrud {

    @FXML
    private TableColumn<Comment, String> authorCOL;

    @FXML
    private TextField authorTF;

    @FXML
    private TableColumn<Comment, String> cdateCOL;

    @FXML
    private DatePicker cdateDP;

    @FXML
    private TableColumn<Comment, String> commentCOL;

    @FXML
    private TextField commentTF;

    @FXML
    private TableView<Comment> commentTV;

    @FXML
    private Button deleteCommentBtn;

    @FXML
    private Button updateCommentBtn;

    private int postId;

    @FXML
    void addComment(ActionEvent event) {
    try {
        CommentService commentService = new CommentService();
        Comment newComment = new Comment(
            postId,
            authorTF.getText(),
            commentTF.getText(),
            cdateDP.getValue().atStartOfDay()
        );

        commentService.create(newComment);

        ObservableList<Comment> obs = FXCollections.observableArrayList(commentService.readByPostId(postId));
        commentTV.setItems(obs);

        authorTF.clear();
        commentTF.clear();
        cdateDP.getEditor().clear();
    } catch (Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Add comment failed");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    }

    @FXML
    void deleteComment(ActionEvent event) {
    Comment selectedComment = commentTV.getSelectionModel().getSelectedItem();
    if(selectedComment != null) {
        try {
            new CommentService().delete(selectedComment);
            ObservableList<Comment> obs = FXCollections.observableArrayList(new CommentService().readByPostId(postId));
            commentTV.setItems(obs);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("delete comment failed");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    }

    @FXML
    void updateComment(ActionEvent event) {
    Comment selectedComment = commentTV.getSelectionModel().getSelectedItem();
    if(selectedComment != null) {
        try {
            selectedComment.setAuthor(authorTF.getText());
            selectedComment.setContent(commentTF.getText());
            selectedComment.setCreatedAt(cdateDP.getValue().atStartOfDay());
            new CommentService().update(selectedComment);
            ObservableList<Comment> obs = FXCollections.observableArrayList(new CommentService().readByPostId(this.postId));
            commentTV.setItems(obs);
            authorTF.clear();
            commentTF.clear();
            cdateDP.getEditor().clear();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("update comment failed");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    }

    @FXML
    void PostInterface(ActionEvent event) {
try {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/PostCrud.fxml"));
    AnchorPane root = loader.load();
    Scene scene = new Scene(root);
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.hide();
    stage.setScene(scene);
    stage.show();
} catch (Exception e) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText("Failed to load post interface");
    alert.setContentText(e.getMessage());
    alert.showAndWait();
}

    }

    @FXML
    void initialize() {
try {
    ObservableList<Comment> obs = FXCollections.observableArrayList(new CommentService().readByPostId(this.postId));
    commentTV.setItems(obs);

    authorCOL.setCellValueFactory(new PropertyValueFactory<>("author"));
    commentCOL.setCellValueFactory(new PropertyValueFactory<>("content"));
    cdateCOL.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
} catch (Exception e) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText("Failed to load comments");
    alert.setContentText(e.getMessage());
    alert.showAndWait();
}
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public void setComments(ObservableList<Comment> obs) {
        commentTV.setItems(obs);
    }
}