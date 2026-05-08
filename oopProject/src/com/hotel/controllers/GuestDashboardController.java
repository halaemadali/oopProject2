package com.hotel.controllers;

import com.hotel.enums.ReservationStatus;
import com.hotel.models.Guest;
import com.hotel.models.Reservation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class GuestDashboardController implements Initializable {

    // ── Header ──
    @FXML private Label welcomeLabel;
    @FXML private Label memberSinceLabel;
    @FXML private Label balanceLabel;

    // ── Profile Card ──
    @FXML private Label profileNameLabel;
    @FXML private Label profileGenderLabel;
    @FXML private Label profileDobLabel;
    @FXML private Label profileAddressLabel;
    @FXML private Label profileGenderDetailLabel;
    @FXML private Label profileBalanceLabel;

    // ── Stats ──
    @FXML private Label totalResLabel;
    @FXML private Label pendingResLabel;
    @FXML private Label confirmedResLabel;
    @FXML private Label cancelledResLabel;
    @FXML private Label resCountLabel;

    // ── Reservations Table ──
    @FXML private VBox  reservationRowsContainer;
    @FXML private Label noReservationsLabel;

    // ── Status bar ──
    @FXML private Label statusLabel;

    private Guest currentGuest;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Data injected via setGuest()
    }

    /** Called by LoginController after a successful guest login */
    public void setGuest(Guest guest) {
        this.currentGuest = guest;
        populateDashboard();
    }

    // ─────────────────────────────────────────
    //  Populate all sections
    // ─────────────────────────────────────────
    private void populateDashboard() {
        if (currentGuest == null) return;

        // Header
        welcomeLabel.setText("Welcome, " + currentGuest.getUsername());
        memberSinceLabel.setText("Guest Account  |  " + currentGuest.getGender());
        balanceLabel.setText(String.format("$%.2f", currentGuest.getBalance()));

        // Profile card
        profileNameLabel.setText(currentGuest.getUsername());
        profileGenderLabel.setText(currentGuest.getGender() != null
                ? currentGuest.getGender().toString() : "—");
        profileDobLabel.setText(currentGuest.getDateOfBirth() != null
                ? currentGuest.getDateOfBirth().toString() : "—");
        profileAddressLabel.setText(currentGuest.getAddress() != null
                ? currentGuest.getAddress() : "—");
        profileGenderDetailLabel.setText(currentGuest.getGender() != null
                ? currentGuest.getGender().toString() : "—");
        profileBalanceLabel.setText(String.format("$%.2f", currentGuest.getBalance()));

        // Stats counters
        int total = 0, pending = 0, confirmed = 0, cancelled = 0;
        for (Reservation r : currentGuest.getReservations()) {
            total++;
            if (r.getStatus() == ReservationStatus.PENDING)   pending++;
            if (r.getStatus() == ReservationStatus.CONFIRMED) confirmed++;
            if (r.getStatus() == ReservationStatus.CANCELLED) cancelled++;
        }
        totalResLabel.setText(String.valueOf(total));
        pendingResLabel.setText(String.valueOf(pending));
        confirmedResLabel.setText(String.valueOf(confirmed));
        cancelledResLabel.setText(String.valueOf(cancelled));
        resCountLabel.setText(total + " record" + (total != 1 ? "s" : ""));

        // Reservations table rows
        reservationRowsContainer.getChildren().clear();

        // Show only active (non-cancelled, non-completed)
        boolean hasActive = false;
        for (Reservation r : currentGuest.getReservations()) {
            if (r.getStatus() == ReservationStatus.CANCELLED ||
                    r.getStatus() == ReservationStatus.COMPLETED) continue;

            hasActive = true;
            reservationRowsContainer.getChildren().add(buildReservationRow(r));
        }

        noReservationsLabel.setVisible(!hasActive);
        noReservationsLabel.setManaged(!hasActive);
    }

    // ─────────────────────────────────────────
    //  Build one table row for a reservation
    // ─────────────────────────────────────────
    private HBox buildReservationRow(Reservation r) {
        HBox row = new HBox();
        row.setStyle("-fx-background-color: #fafafa; -fx-background-radius: 6; -fx-padding: 10 8 10 8;");
        row.setSpacing(0);

        // Status color
        String statusColor = switch (r.getStatus()) {
            case PENDING   -> "#f39c12";
            case CONFIRMED -> "#27ae60";
            case CHECKED_IN-> "#2980b9";
            default        -> "#95a5a6";
        };

        row.getChildren().addAll(
                cell(String.valueOf(r.getID()),                                       50,  "#2c3e50", false),
                cell(String.valueOf(r.getRoom() != null ? r.getRoom().getRoomNumber() : "—"), 70, "#2c3e50", false),
                cell(r.getRoom() != null ? r.getRoom().getType().getCategory() : "—", 80,  "#555555", false),
                cell(r.getCheckin()  != null ? r.getCheckin().toString()  : "—",     110, "#555555", false),
                cell(r.getCheckout() != null ? r.getCheckout().toString() : "—",     110, "#555555", false),
                statusBadge(r.getStatus().toString(), statusColor,                    100),
                cell(r.getInvoice() != null
                        ? String.format("$%.2f", r.getInvoice().calculateTotal())
                        : "—",                                                        -1,  "#e06d67", true)
        );

        // Alternate row background
        int index = reservationRowsContainer.getChildren().size();
        if (index % 2 == 0)
            row.setStyle("-fx-background-color: white; -fx-background-radius: 6; -fx-padding: 10 8 10 8;");

        return row;
    }

    /** Plain text cell */
    private Label cell(String text, double width, String color, boolean grow) {
        Label lbl = new Label(text);
        lbl.setStyle("-fx-text-fill: " + color + "; -fx-font-size: 12px;");
        lbl.setWrapText(false);
        if (width > 0) {
            lbl.setPrefWidth(width);
            lbl.setMinWidth(width);
        }
        if (grow) HBox.setHgrow(lbl, javafx.scene.layout.Priority.ALWAYS);
        return lbl;
    }

    /** Colored pill badge for status */
    private HBox statusBadge(String text, String color, double width) {
        Label lbl = new Label(text);
        lbl.setStyle("-fx-text-fill: white; -fx-font-size: 10px; -fx-font-weight: bold; " +
                "-fx-background-color: " + color + "; -fx-background-radius: 20; " +
                "-fx-padding: 3 10 3 10;");

        HBox box = new HBox(lbl);
        box.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        box.setPrefWidth(width);
        box.setMinWidth(width);
        return box;
    }

    // ─────────────────────────────────────────
    //  Button Handlers
    // ─────────────────────────────────────────
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
            statusLabel.setText("Navigation error: " + e.getMessage());
        }
    }

    @FXML
    private void handleLogout() {
        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("/Resources/fxml/LoginScreen.fxml")
            );
            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Hotel – Login");
        } catch (Exception e) {
            statusLabel.setText("Logout error: " + e.getMessage());
        }
    }
}
