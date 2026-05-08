package com.hotel.controllers;

import com.hotel.database.HotelDatabase;
import com.hotel.enums.PaymentMethod;
import com.hotel.models.Guest;
import com.hotel.models.Receptionist;
import com.hotel.models.Reservation;
import com.hotel.models.Room;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ReceptionistDashboardController implements Initializable {

    // ── Header ──────────────────────────────────────────────────────────────
    @FXML private Label lblReceptionist;

    // ── Input fields ────────────────────────────────────────────────────────
    @FXML private TextField         reservationIdField;
    @FXML private TextField         guestUsernameField;
    @FXML private TextField         roomNumberField;
    @FXML private ComboBox<String>  paymentCombo;

    // ── Status label ────────────────────────────────────────────────────────
    @FXML private Label statusLabel;

    // ── Reservations Table ──────────────────────────────────────────────────
    @FXML private TableView<Reservation>              reservationsTable;
    @FXML private TableColumn<Reservation, Integer>   colID;
    @FXML private TableColumn<Reservation, String>    colGuest;
    @FXML private TableColumn<Reservation, Integer>   colRoom;
    @FXML private TableColumn<Reservation, String>    colCheckin;
    @FXML private TableColumn<Reservation, String>    colCheckout;
    @FXML private TableColumn<Reservation, String>    colStatus;
    @FXML private TableColumn<Reservation, Double>    colTotal;

    private Receptionist currentReceptionist;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupReservationsTable();
        paymentCombo.getItems().addAll("CASH", "CREDIT_CARD", "ONLINE");
        loadAllReservations();
    }

    private void setupReservationsTable() {
        colID.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getID()).asObject());
        colGuest.setCellValueFactory(c -> {
            Guest g = c.getValue().getGuest();
            return new SimpleStringProperty(g != null ? g.getUsername() : "—");
        });
        colRoom.setCellValueFactory(c -> {
            Room r = c.getValue().getRoom();
            return new SimpleIntegerProperty(r != null ? r.getRoomNumber() : 0).asObject();
        });
        colCheckin.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getCheckin() != null ? c.getValue().getCheckin().toString() : "—"));
        colCheckout.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getCheckout() != null ? c.getValue().getCheckout().toString() : "—"));
        colStatus.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getStatus().name()));
        colTotal.setCellValueFactory(c ->
                new SimpleDoubleProperty(c.getValue().getInvoice() != null ? c.getValue().getInvoice().calculateTotal() : 0).asObject());
    }

    public void setReceptionist(Receptionist r) {
        this.currentReceptionist = r;
        lblReceptionist.setText(r.getUsername());
        loadAllReservations();
    }

    // ════════════════════════════════════════════════ SIDEBAR ACTIONS ═══════

    @FXML private void handleViewAll() {
        clearStatus();
        loadAllReservations();
        showInfo("Showing all reservations (" + HotelDatabase.reservations.size() + " total).");
    }

    @FXML private void handleViewPending() {
        clearStatus();
        ObservableList<Reservation> pending = FXCollections.observableArrayList();
        for (Reservation r : HotelDatabase.reservations) {
            if ("PENDING".equals(r.getStatus().name())) {
                pending.add(r);
            }
        }
        reservationsTable.setItems(pending);
        showInfo("Showing " + pending.size() + " PENDING reservation(s).");
    }

    @FXML private void handleConfirm() {
        clearStatus();
        int id = parseReservationId();
        if (id < 0) return;
        currentReceptionist.confirmReservation(id);
        loadAllReservations();
        showSuccess("Reservation #" + id + " confirmed.");
    }

    @FXML private void handleCancel() {
        clearStatus();
        int id = parseReservationId();
        if (id < 0) return;
        currentReceptionist.cancelReservation(id);
        loadAllReservations();
        showSuccess("Reservation #" + id + " cancelled.");
    }

    @FXML private void handleCheckIn() {
        clearStatus();
        String username = guestUsernameField.getText().trim();
        int id = parseReservationId();
        if (id < 0) return;
        if (username.isEmpty()) {
            showError("Please enter the guest username.");
            return;
        }
        currentReceptionist.checkIn(username, id);
        loadAllReservations();
        showSuccess("Check-in processed for guest: " + username);
    }

    @FXML private void handleCheckout() {
        clearStatus();
        String username = guestUsernameField.getText().trim();
        int roomNum = parseRoomNumber();
        if (roomNum < 0) return;
        if (username.isEmpty()) {
            showError("Please enter the guest username.");
            return;
        }
        if (paymentCombo.getValue() == null) {
            showError("Please select a payment method.");
            return;
        }
        PaymentMethod method = PaymentMethod.valueOf(paymentCombo.getValue());
        currentReceptionist.processGuestCheckout(username, roomNum, method);
        loadAllReservations();
        showSuccess("Checkout processed for room " + roomNum + ".");
    }

    @FXML private void handleViewGuests() {
        clearStatus();
        showGuestsInNewWindow();
        showInfo("Opened All Guests window (" + HotelDatabase.guests.size() + " guests)");
    }

    @FXML private void handleViewRooms() {
        clearStatus();
        showRoomsInNewWindow();
        showInfo("Opened All Rooms window (" + HotelDatabase.rooms.size() + " rooms)");
    }

    @FXML private void handleLogout() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Resources/fxml/LoginScreen.fxml"));
            Stage stage = (Stage) statusLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Hotel Login");
        } catch (Exception e) {
            showError("Logout error: " + e.getMessage());
        }
    }

    // ════════════════════════════════════════════════════ HELPERS ═══════════

    private void loadAllReservations() {
        ObservableList<Reservation> all = FXCollections.observableArrayList(HotelDatabase.reservations);
        reservationsTable.setItems(all);
    }

    private int parseReservationId() {
        try {
            int id = Integer.parseInt(reservationIdField.getText().trim());
            if (id <= 0) throw new NumberFormatException();
            return id;
        } catch (Exception e) {
            showError("Please enter a valid Reservation ID.");
            return -1;
        }
    }

    private int parseRoomNumber() {
        try {
            int num = Integer.parseInt(roomNumberField.getText().trim());
            if (num <= 0) throw new NumberFormatException();
            return num;
        } catch (Exception e) {
            showError("Please enter a valid Room Number.");
            return -1;
        }
    }

    // ── Popup: All Rooms ────────────────────────────────────────────────────
    private void showRoomsInNewWindow() {
        TableView<Room> table = new TableView<>();
        table.setPrefSize(800, 550);

        TableColumn<Room, Integer> colNum = new TableColumn<>("Room No");
        colNum.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));

        TableColumn<Room, String> colType = new TableColumn<>("Type");
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));

        // Status column - robust version
        TableColumn<Room, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(c -> {
            Room room = c.getValue();
            if (room == null) return new SimpleStringProperty("—");

            try {
                // Try common status getter names
                Object statusObj = null;
                if (hasMethod(room, "getStatus")) {
                    statusObj = room.getClass().getMethod("getStatus").invoke(room);
                } else if (hasMethod(room, "getRoomStatus")) {
                    statusObj = room.getClass().getMethod("getRoomStatus").invoke(room);
                } else if (hasMethod(room, "getState")) {
                    statusObj = room.getClass().getMethod("getState").invoke(room);
                }

                String status = (statusObj != null) ? statusObj.toString() : "UNKNOWN";
                return new SimpleStringProperty(status);
            } catch (Exception e) {
                return new SimpleStringProperty("ERROR");
            }
        });

        TableColumn<Room, Double> colPrice = new TableColumn<>("Price");
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        table.getColumns().addAll(colNum, colType, colStatus, colPrice);
        table.setItems(FXCollections.observableArrayList(HotelDatabase.rooms));

        VBox vbox = new VBox(10, new Label("All Rooms - Total: " + HotelDatabase.rooms.size()), table);
        vbox.setStyle("-fx-padding: 15;");

        Stage stage = new Stage();
        stage.setTitle("All Rooms");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(vbox));
        stage.show();
    }

    // ── Popup: All Guests ───────────────────────────────────────────────────
    private void showGuestsInNewWindow() {
        TableView<Guest> table = new TableView<>();
        table.setPrefSize(800, 500);

        TableColumn<Guest, String> colUser = new TableColumn<>("Username");
        colUser.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<Guest, String> colName = new TableColumn<>("Full Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("fullName"));

        TableColumn<Guest, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Guest, String> colPhone = new TableColumn<>("Phone");
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        table.getColumns().addAll(colUser, colName, colEmail, colPhone);
        table.setItems(FXCollections.observableArrayList(HotelDatabase.guests));

        VBox vbox = new VBox(10, new Label("All Guests - Total: " + HotelDatabase.guests.size()), table);
        vbox.setStyle("-fx-padding: 15;");

        Stage stage = new Stage();
        stage.setTitle("All Guests");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(vbox));
        stage.show();
    }

    // Helper method to check if a method exists
    private boolean hasMethod(Object obj, String methodName) {
        try {
            obj.getClass().getMethod(methodName);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    private void clearStatus() {
        statusLabel.setText("");
    }

    private void showError(String msg) {
        statusLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 13px;");
        statusLabel.setText("❌  " + msg);
    }

    private void showSuccess(String msg) {
        statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-size: 13px;");
        statusLabel.setText("✅  " + msg);
    }

    private void showInfo(String msg) {
        statusLabel.setStyle("-fx-text-fill: #2980b9; -fx-font-size: 13px;");
        statusLabel.setText("ℹ️  " + msg);
    }
}