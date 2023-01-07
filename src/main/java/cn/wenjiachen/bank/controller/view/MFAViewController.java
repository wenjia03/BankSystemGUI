package cn.wenjiachen.bank.controller.view;

/**
 * @author Wenjia Chen
 * @date 2023/1/515:28
 */
import cn.wenjiachen.bank.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MFAViewController {

    @FXML
    private Button confirmButton;

    @FXML
    private TextField password;

    @FXML
    protected void onConfirmButtonClick() throws Exception {
        if (password.getText().length() == 6) {
            Application.userInputMFA = password.getText();
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
