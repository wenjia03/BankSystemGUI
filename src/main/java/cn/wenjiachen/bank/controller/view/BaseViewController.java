package cn.wenjiachen.bank.controller.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BaseViewController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}