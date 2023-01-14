package cn.wenjiachen.bank;

import cn.wenjiachen.bank.config.SQLConfig;
import cn.wenjiachen.bank.domain.User;
import cn.wenjiachen.bank.domain.UserProfiles;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class Application extends javafx.application.Application {

    public static User LoginedUser = null;

    public static UserProfiles ChoiceUser = null;

    public static String userInputMFA = "";

    public static String userInputPassword = "";

    private static Scene LoginView;

    public static UserProfiles toUpdate;


    @Override
    public void start(Stage stage) throws Exception {
        Connection connection = SQLConfig.getDataSource().getConnection();
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
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("AdminMain.fxml"));
        Scene MainView = new Scene(fxmlLoader.load(), 640, 480);
        Stage stage = (Stage) LoginView.getWindow();
        stage.setTitle("Hello!");
        stage.setScene(MainView);
        stage.show();
    }

    public static void showBalanceView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("BalanceView.fxml"));
        Scene BalanceView = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Hello!");
        stage.setScene(BalanceView);
        stage.show();
    }

    public static void showAdminDepositorCreateView(boolean isWait) throws IOException {
        toUpdate = null;
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("CreateDepositor.fxml"));
        Scene BalanceView = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Hello!");
        stage.setScene(BalanceView);
        if (!isWait)
            stage.show();
        else
            stage.showAndWait();

    }

    public static void showAdminDepositorCreateView() throws IOException {
        showAdminDepositorCreateView(false);
    }

    public static void showAdminDepositorDeleteView(boolean isWait) throws IOException {
        toUpdate = null;
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("DeleteDepositorView.fxml"));
        Scene BalanceView = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Hello!");
        stage.setScene(BalanceView);
        if (!isWait)
            stage.show();
        else
            stage.showAndWait();

    }

    public static void showAdminDepositorDeleteView() throws IOException {
        showAdminDepositorDeleteView(false);
    }

    public static void changeUserProfiles(UserProfiles userProfiles) throws IOException {
        toUpdate = userProfiles;
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("CreateDepositor.fxml"));
        Scene BalanceView = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Hello!");
        stage.setScene(BalanceView);
        stage.showAndWait();
        toUpdate = userProfiles;
    }

    public static void showAdminDepositorDetailsView(boolean isWait) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("DepositorDetails.fxml"));
        Scene BalanceView = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Hello!");
        stage.setScene(BalanceView);
        if (!isWait)
            stage.show();
        else
            stage.showAndWait();
    }

    public static void showAdminDepositorDetailsView() throws IOException {
        showAdminDepositorDetailsView(false);
    }


    public static String getUserInputMFA() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("2FAView.fxml"));
        Scene MFAView = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("验证密码器");
        stage.setScene(MFAView);
        stage.showAndWait();
        String t = userInputMFA;
        userInputMFA = "";
        return t;
    }

    public static String getUserInputPassword() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("PasswordConfirmView.fxml"));
        Scene PasswordConfirmView = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("验证密码");
        stage.setScene(PasswordConfirmView);
        stage.showAndWait();
        String t = userInputPassword;
        userInputPassword = "";
        return t;
    }

    public static UserProfiles choiceUser() throws IOException {
        ChoiceUser = null;
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("DepositorChoice.fxml"));
        Scene BalanceView = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Hello!");
        stage.setScene(BalanceView);
        stage.showAndWait();
        return ChoiceUser;
    }

    public static void showAdminTransDetailsClickView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("TransDetail.fxml"));
        Scene BalanceView = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Hello!");
        stage.setScene(BalanceView);
        stage.show();

    }

    public static void showAdminCreateTransClickView(boolean isWait) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("CreateTransView.fxml"));
        Scene BalanceView = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Hello!");
        stage.setScene(BalanceView);
        if (!isWait)
            stage.show();
        else
            stage.showAndWait();
    }

    public static void showAdminCreateTransClickView() throws IOException {
        showAdminCreateTransClickView(false);
    }

}