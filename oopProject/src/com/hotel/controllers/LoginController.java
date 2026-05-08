package com.hotel.controllers;

import com.hotel.models.Guest;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML private TextField     loginUsernameField;
    @FXML private PasswordField loginPasswordField;
    @FXML private TextField     loginPasswordVisible;
    @FXML private CheckBox      showPasswordCheck;
    @FXML private Label         loginStatusLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // nothing to initialize on the login screen
    }

    @FXML
    private void handleLogin() {
        String username = loginUsernameField.getText().trim();

        String password = loginPasswordField.isVisible()
                ? loginPasswordField.getText().trim()
                : loginPasswordVisible.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Please enter username and password.");
            return;
        }

        Guest guest = Guest.login(username, password);
        if (guest != null) {
            goToGuestDashboard(guest);
            return;
        }

        showError("Invalid username or password.");
    }

    @FXML
    private void handleShowPassword() {
        if (showPasswordCheck.isSelected()) {
            loginPasswordVisible.setText(loginPasswordField.getText());
            loginPasswordVisible.setVisible(true);
            loginPasswordField.setVisible(false);
        } else {
            loginPasswordField.setText(loginPasswordVisible.getText());
            loginPasswordField.setVisible(true);
            loginPasswordVisible.setVisible(false);
        }
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) loginUsernameField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleGoToRegister() {
        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("/Resources/fxml/RegisterScreen.fxml")
            );
            Stage stage = (Stage) loginUsernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Hotel – Register");
        } catch (Exception e) {
            showError("Navigation error: " + e.getMessage());
        }
    }

    private void goToGuestDashboard(Guest guest) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/Resources/fxml/GuestDashboard.fxml")
            );
            Parent root = loader.load();
            GuestDashboardController ctrl = loader.getController();
            ctrl.setGuest(guest);

            Stage stage = (Stage) loginUsernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Guest Dashboard");
        } catch (Exception e) {
            showError("Navigation error: " + e.getMessage());
        }
    }

    private void showError(String message) {
        loginStatusLabel.setStyle("-fx-text-fill: #e76c3c; -fx-font-size: 13px;");
        loginStatusLabel.setText(message);
    }

    private void showSuccess(String message) {
        loginStatusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-size: 13px;");
        loginStatusLabel.setText(message);
    }
    @FXML
    private void handleBack() {
        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("/Resources/fxml/WelcomeScreen.fxml")
            );
            Stage stage = (Stage) loginUsernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Hotel – Welcome");
        } catch (Exception e) {
            showError("Navigation error: " + e.getMessage());
        }
    }
}