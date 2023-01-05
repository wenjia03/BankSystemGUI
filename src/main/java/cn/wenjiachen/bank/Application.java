package cn.wenjiachen.bank;

import cn.wenjiachen.bank.controller.view.MainViewController;
import cn.wenjiachen.bank.domain.Profiles;
import cn.wenjiachen.bank.domain.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {

    public static User LoginedUser = null;
    public static Profiles StoreProfiles = null;

    private static Scene LoginView;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("LoginView.fxml"));
        LoginView = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(LoginView);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void showMainView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("MainView.fxml"));
        Scene MainView = new Scene(fxmlLoader.load(), 640, 480);
        Stage stage = (Stage) LoginView.getWindow();
        stage.setTitle("Hello!");
        stage.setScene(MainView);
        stage.show();
    }

    public static void showBalanceView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("BalanceView.fxml"));
        Scene BalanceView = new Scene(fxmlLoader.load(), 640, 480);
        Stage stage = new Stage();
        stage.setTitle("Hello!");
        stage.setScene(BalanceView);
        stage.show();
    }
}