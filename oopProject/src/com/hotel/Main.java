package com.hotel;

import com.hotel.database.HotelDatabase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        HotelDatabase.initializeData();

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/Resources/fxml/WelcomeScreen.fxml")
            );
            Parent root = loader.load();

            stage.setScene(new Scene(root));
            stage.setTitle("Hotel Management System");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/Resources/images/Hotel_Logo.png")));
            stage.setResizable(false);
            stage.show();

        } catch (Exception e) {
            System.err.println("Failed to load Welcome Screen:");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}