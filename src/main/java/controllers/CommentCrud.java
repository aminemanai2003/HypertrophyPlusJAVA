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
import java.time.LocalDate;
import java.time.LocalDateTime;

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
            if (validateForm()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Add comment confirmation");
                alert.setContentText("Are you sure you want to add this comment?");
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                        CommentService commentService = new CommentService();
                        Comment newComment = new Comment(
                                postId,
                                authorTF.getText(),
                                commentTF.getText(),
                                cdateDP.getValue().atStartOfDay()
                        );

                        try {
                            commentService.create(newComment);
                        if (!validateForm()) {
                            String msg = "";
                            if (authorTF.getText().isEmpty()) msg += "Author is required\n";
                            if (commentTF.getText().isEmpty()) msg += "Comment is required\n";
                            if (cdateDP.getValue() == null) msg += "Date is required\n";
                            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                            errorAlert.setTitle("Error");
                            errorAlert.setHeaderText("Form validation failed");
                            errorAlert.setContentText(msg);
                            errorAlert.showAndWait();
                            return;
                        }
                        } catch (Exception e) {
                            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                            errorAlert.setTitle("Error");
                            errorAlert.setHeaderText("Add comment failed");
                            errorAlert.setContentText(e.getMessage());
                            errorAlert.showAndWait();
                        }

                        ObservableList<Comment> obs = FXCollections.observableArrayList(commentService.readByPostId(postId));
                        commentTV.setItems(obs);

                        authorTF.clear();
                        commentTF.clear();
                        cdateDP.getEditor().clear();

                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Success");
                        successAlert.setHeaderText("Comment added successfully");
                        successAlert.setContentText("The comment has been added successfully");
                        successAlert.showAndWait();
                    }
                });
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Form validation failed");
                errorAlert.setContentText("Please fill out all fields correctly");
                errorAlert.showAndWait();
            }

        } catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Add comment failed");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
        }

    }

    @FXML
    void deleteComment(ActionEvent event) {
        Comment selectedComment = commentTV.getSelectionModel().getSelectedItem();
        if (selectedComment != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Delete comment confirmation");
            alert.setContentText("Are you sure you want to delete this comment?");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    try {
                        new CommentService().delete(selectedComment);
                        ObservableList<Comment> obs = FXCollections.observableArrayList(new CommentService().readByPostId(postId));
                        commentTV.setItems(obs);

                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Success");
                        successAlert.setHeaderText("Comment deleted successfully");
                        successAlert.setContentText("The comment has been deleted successfully");
                        successAlert.showAndWait();
                    } catch (Exception e) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Error");
                        errorAlert.setHeaderText("delete comment failed");
                        errorAlert.setContentText(e.getMessage());
                        errorAlert.showAndWait();
                    }
                }
            });
        }
    }

    @FXML
    void updateComment(ActionEvent event) {
        Comment selectedComment = commentTV.getSelectionModel().getSelectedItem();
        if (selectedComment != null) {
            if (validateForm()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Update comment confirmation");
                alert.setContentText("Are you sure you want to update this comment?");
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
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

                            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                            successAlert.setTitle("Success");
                            successAlert.setHeaderText("Comment updated successfully");
                            successAlert.setContentText("The comment has been updated successfully");
                            successAlert.showAndWait();
                        } catch (Exception e) {
                            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                            errorAlert.setTitle("Error");
                            errorAlert.setHeaderText("update comment failed");
                            errorAlert.setContentText(e.getMessage());
                            errorAlert.showAndWait();
                        }
                    }
                });
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Form validation failed");
                errorAlert.setContentText("Please fill out all fields correctly");
                errorAlert.showAndWait();
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
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success");
            successAlert.setHeaderText("Post interface loaded successfully");
            successAlert.setContentText("The post interface has been loaded successfully");
            successAlert.showAndWait();
        } catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Failed to load post interface");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
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

            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Failed to load comments");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
        }
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public void setComments(ObservableList<Comment> obs) {
        commentTV.setItems(obs);
    }

    private boolean validateForm() {
        if (authorTF.getText().isEmpty() || commentTF.getText().isEmpty() || cdateDP.getValue() == null) {
            return false;
        }

        if (cdateDP.getValue().isAfter(LocalDate.now())) {
            return false;
        }

        return true;
    }
}