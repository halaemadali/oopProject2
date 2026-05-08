package com.hotel.controllers;

import com.hotel.models.Guest;
import com.hotel.models.Reservation;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GuestManageReservationController implements Initializable {

    @FXML private Label guestTagLabel;
    @FXML private TableView<Reservation> ReservationsTable;
    @FXML private TableColumn<Reservation, String> colId;
    @FXML private TableColumn<Reservation, String> colRoom;
    @FXML private TableColumn<Reservation, String> colType;
    @FXML private TableColumn<Reservation, String> colCheckin;
    @FXML private TableColumn<Reservation, String> colCheckout;
    @FXML private TableColumn<Reservation, String> colStatus;

    private Guest currentGuest;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getID())));
        colRoom.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getRoom().getRoomNumber())));
        colType.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getRoom().getType().getCategory()));
        colCheckin.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getCheckin())));
        colCheckout.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getCheckout())));
        colStatus.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getStatus())));
    }

    public void setGuest(Guest g) {
        this.currentGuest = g;
        if (guestTagLabel != null) {
            guestTagLabel.setText(g.getUsername());
        }
        refreshTable();
    }

    private void refreshTable() {
        if (currentGuest != null && currentGuest.getReservations() != null) {
            ReservationsTable.getItems().setAll(currentGuest.getReservations());
        }
    }

    @FXML
    private void handleGoToCancel() throws Exception {
        navigateTo("/Resources/fxml/CancelReservationScreen.fxml", "Cancel Reservation");
    }

    @FXML
    private void handleMakeReservation() throws Exception {
        navigateTo("/Resources/fxml/ReservationScreen.fxml", "Make Reservation");
    }

    @FXML
    private void handleBack() throws IOException {
        navigateTo("/Resources/fxml/GuestDashboard.fxml", "Guest Dashboard");
    }

    @FXML
    private void handleCheckout() throws IOException {
        navigateTo("/Resources/fxml/CheckoutScreen.fxml", "Checkout");
    }

    private void navigateTo(String fxmlPath, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();

        Object controller = loader.getController();
        try {
            controller.getClass().getMethod("setGuest", Guest.class).invoke(controller, currentGuest);
        } catch (Exception e) {

        }

        Stage stage = (Stage) ReservationsTable.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle(title);
    }
}