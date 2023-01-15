package cn.wenjiachen.bank.controller.view.Admin;

import cn.wenjiachen.bank.Application;
import cn.wenjiachen.bank.domain.UserProfiles;
import cn.wenjiachen.bank.service.user.UserProfileService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DeleteDepositorController implements Initializable {
    @FXML
    private Button Cancel;

    @FXML
    private Button Confirm;

    @FXML
    private Button chooseUserButton;

    @FXML
    private TextField userShowText;

    @FXML
    private Label warningTitle;

    private UserProfiles toDeleteUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userShowText.setText("未选择");
        if (Application.LoginPermissions.HasPermission(new String[]{"HIGH_ALL", "HIGH_DEPOSITOR"})) {
            warningTitle.setVisible(false);
        }
    }

    @FXML
    protected void onChooseUserButtonClicked() {
        try {
            toDeleteUser = Application.choiceUser();
            if (toDeleteUser != null) {
                userShowText.setText(toDeleteUser.getUserName() + " (" + toDeleteUser.getUserIDCard() + ")");
            } else {
                toDeleteUser = null;
                userShowText.setText("未选择");
            }
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("软件内部错误");
            alert.setHeaderText("软件内部错误");
            alert.setContentText("系统缺少必要的文件，请联系IT支持。");
            alert.showAndWait();
        }
    }

    @FXML
    protected void onCancelButtonClicked() {
        Stage s = (Stage) Cancel.getScene().getWindow();
        s.close();
    }

    @FXML
    protected void onConfirmButtonClicked() throws Exception {
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        if (toDeleteUser == null) {
            errorAlert.setTitle("错误");
            errorAlert.setHeaderText("错误");
            errorAlert.setContentText("未选择用户，请先选择用户。");
            errorAlert.showAndWait();
            return;
        }
        if (!Application.LoginPermissions.HasPermission(new String[]{"HIGH_ALL", "HIGH_DEPOSITOR"})) {
            String userPassword = Application.getUserInputPassword();
            if (userPassword == null) {
                return;
            }
            if (!toDeleteUser.checkPassword(userPassword)) {
                errorAlert.setTitle("错误");
                errorAlert.setHeaderText("错误");
                errorAlert.setContentText("密码错误。无法确认用户身份，无法完成销户 " + toDeleteUser.UserBankCardPassword);
                errorAlert.showAndWait();
                return;
            }
        }
        if(!Application.MFAConfirm()){
            errorAlert.setTitle("错误");
            errorAlert.setHeaderText("MFA验证有误");
            errorAlert.setContentText("MFA验证有误");
            errorAlert.showAndWait();
            return;
        }
        if (UserProfileService.deleteProfile(toDeleteUser)) {
            infoAlert.setTitle("成功");
            infoAlert.setHeaderText("成功");
            infoAlert.setContentText("用户删除成功。");
            infoAlert.showAndWait();
            Stage s = (Stage) Cancel.getScene().getWindow();
            s.close();
        } else {
            errorAlert.setTitle("错误");
            errorAlert.setHeaderText("错误");
            errorAlert.setContentText("用户删除失败。");
            errorAlert.showAndWait();
        }
    }
}
