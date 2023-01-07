package cn.wenjiachen.bank.controller.view;
import cn.wenjiachen.bank.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * @author Wenjia Chen
 * @date 2023/1/516:12
 */
public class PasswordConfirmController {


    @FXML
    private Button confirmButton;

    @FXML
    private PasswordField password;

    @FXML
    void onConfirmButtonClick(MouseEvent event) {
        if (password.getText().length() == 6) {
            Application.userInputPassword = password.getText();
            Stage s = (Stage) confirmButton.getScene().getWindow();
            s.close();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("凭据格式错误");
            alert.setHeaderText("凭据格式错误");
            alert.setContentText("请检查您的凭据格式");
            password.setText("");
            alert.showAndWait();
        }
    }

}
