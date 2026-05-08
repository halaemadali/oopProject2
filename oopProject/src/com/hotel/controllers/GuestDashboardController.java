package com.hotel.controllers;

import com.hotel.models.Guest;
import com.hotel.models.Reservation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

public class GuestDashboardController {

    // These IDs must match your FXML fx:id exactly
    @FXML private Label welcomeLabel;
    @FXML private Label balanceLabel;
    @FXML private Label memberSinceLabel;

    @FXML private Label profileNameLabel;
    @FXML private Label profileGenderLabel;
    @FXML private Label profileDobLabel;
    @FXML private Label profileAddressLabel;
    @FXML private Label profileGenderDetailLabel;
    @FXML private Label profileBalanceLabel;

    @FXML private Label totalResLabel;
    @FXML private Label pendingResLabel;
    @FXML private Label confirmedResLabel;
    @FXML private Label cancelledResLabel;
    @FXML private Label resCountLabel;
    @FXML private Label noReservationsLabel;
    @FXML private VBox reservationRowsContainer;

    private Guest currentGuest;

    /**
     * This is the method the LoginController calls to pass data.
     */
    public void setGuest(Guest guest) {
        this.currentGuest = guest;
        updateUI();
    }

    private void updateUI() {
        if (currentGuest == null) return;

        // Header Section
        welcomeLabel.setText("Welcome, " + currentGuest.getUsername());
        balanceLabel.setText(String.format("%.2f", currentGuest.getBalance()));
        memberSinceLabel.setText("HMS Member");

        // Profile Card Section
        profileNameLabel.setText(currentGuest.getUsername());
        profileGenderLabel.setText(currentGuest.getGender().toString());
        profileDobLabel.setText(currentGuest.getDateOfBirth().toString());
        profileAddressLabel.setText(currentGuest.getAddress());
        profileGenderDetailLabel.setText(currentGuest.getGender().toString());
        profileBalanceLabel.setText(String.format("%.2f", currentGuest.getBalance()));

        // Stats Section
        int total = currentGuest.getReservations().size();
        totalResLabel.setText(String.valueOf(total));
        resCountLabel.setText(total + " records");

        // Toggle visibility of empty state label
        noReservationsLabel.setVisible(total == 0);
        noReservationsLabel.setManaged(total == 0);
    }

    @FXML
    private void handleLogout() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Resources/fxml/LoginScreen.fxml"));
            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Hotel Login");
        } catch (Exception e) {
            System.out.println("Logout error: " + e.getMessage());
        }
    }

    @FXML
    private void handleMakeReservation() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/Resources/fxml/ManageReservationScreen.fxml")
            );
            Parent root = loader.load();
            GuestManageReservationController ctrl = loader.getController();
            ctrl.setGuest(currentGuest);

            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Hotel – Make a Reservation");
        } catch (Exception e) {
            System.out.println("Navigation error: " + e.getMessage());
        }
    }
}