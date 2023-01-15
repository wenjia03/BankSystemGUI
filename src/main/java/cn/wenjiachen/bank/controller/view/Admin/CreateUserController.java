package cn.wenjiachen.bank.controller.view.Admin;

import cn.wenjiachen.bank.Application;
import cn.wenjiachen.bank.controller.Showable;
import cn.wenjiachen.bank.controller.view.StagePool;
import cn.wenjiachen.bank.domain.Permission.Permissions;
import cn.wenjiachen.bank.domain.User;
import cn.wenjiachen.bank.domain.UserException;
import cn.wenjiachen.bank.service.PermissionService;
import cn.wenjiachen.bank.service.user.UserService;
import cn.wenjiachen.bank.utils.Securities;
import cn.wenjiachen.bank.utils.Tools;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreateUserController implements Initializable, Showable {

    @FXML
    private Button ChangeMFAButton;

    @FXML
    private Button Confirm;


    @FXML
    private Label MFATips;

    @FXML
    private Button CreateQRCodeButton;

    @FXML
    private ImageView MFAQRCode;

    @FXML
    private Label MFASettingLabel;

    @FXML
    private PasswordField PasswordText;

    @FXML
    private TextField PermissionText;

    @FXML
    private Button SelectPermissionButton;

    @FXML
    private TextField UserLoginNameText;

    @FXML
    private TextField UserNameText;

    private String MFAKey = Securities.generate2FA();

    private Permissions selectPermissions = null;

    private boolean isMFA = false;
    private StagePool stagePool;
    private String stageName;

    private User toUpdateUser;

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (Application.toUpdateUser != null) {
            this.toUpdateUser = Application.toUpdateUser;
            UserNameText.setText(toUpdateUser.getEmail());
            UserLoginNameText.setText(toUpdateUser.getUserName());
            PasswordText.setPromptText("（未修改）");
            try {
                PermissionText.setText(PermissionService.getPermissionsList(toUpdateUser.getPermissionGroupID()).get(0).getPermissionGroupName());
                MFAKey = toUpdateUser.getMFA();
                MFATips.setText("未修改");
                isMFA = toUpdateUser.isMFAEnabled();
                MFAQRCode.setVisible(false);
                CreateQRCodeButton.setVisible(false);
                Confirm.setText("修改");
                selectPermissions = PermissionService.getPermissionsList(toUpdateUser.getPermissionGroupID()).get(0);

            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("错误");
                alert.setHeaderText("数据库错误");
                alert.setContentText("数据库错误");
                alert.showAndWait();
            }
        }

    }

    @FXML
    protected void onChangeMFAButtonClicked() {
        isMFA = !isMFA;
        if (isMFA) {
            ChangeMFAButton.setText("关闭MFA");
            MFASettingLabel.setText("已设置");
            MFAQRCode.setVisible(true);
            CreateQRCodeButton.setVisible(true);
            MFAKey = Securities.generate2FA();
            if (MFAQRCode.getImage() != null) {
                MFAQRCode.setImage(null);
            }
            MFATips.setVisible(true);
        } else {
            ChangeMFAButton.setText("开启MFA");
            MFASettingLabel.setText("未设置");
            MFAQRCode.setVisible(false);
            CreateQRCodeButton.setVisible(false);
            MFATips.setVisible(true);
        }
    }

    @FXML
    protected void onSelectPermissionButtonClicked() {
        try {
            selectPermissions = Application.choicePermission();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText("软件关键文件丢失");
            alert.setContentText("软件关键文件丢失，请联系系统管理员");
            alert.showAndWait();
        }
        if (selectPermissions != null) {
            PermissionText.setText(selectPermissions.getPermissionGroupName() + " - " + selectPermissions.getPermissionGroupID());
        } else {
            PermissionText.setText("未选择");
        }
    }

    @FXML
    protected void onConfirmButtonClicked() throws IOException {
        Alert error = new Alert(Alert.AlertType.ERROR);
        Alert success = new Alert(Alert.AlertType.INFORMATION);
        if (UserLoginNameText.getText().equals("")) {
            error.setTitle("错误");
            error.setHeaderText("用户名不能为空");
            error.setContentText("用户名不能为空");
            error.showAndWait();
            return;
        }
        if (UserNameText.getText().equals("")) {
            error.setTitle("错误");
            error.setHeaderText("用户姓名不能为空");
            error.setContentText("用户姓名不能为空");
            error.showAndWait();
            return;
        }

        if (selectPermissions == null) {
            error.setTitle("错误");
            error.setHeaderText("用户权限不能为空");
            error.setContentText("用户权限不能为空");
            error.showAndWait();
            return;
        }
        if (toUpdateUser == null) {
            if (PasswordText.getText().equals("")) {
                error.setTitle("错误");
                error.setHeaderText("用户密码不能为空");
                error.setContentText("用户密码不能为空");
                error.showAndWait();
                return;
            }
            if(!Application.MFAConfirm()){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("错误");
                errorAlert.setHeaderText("MFA验证有误");
                errorAlert.setContentText("MFA验证有误");
                errorAlert.showAndWait();
                return;
            }
            try {
                if (isMFA) {
                    UserService.createUser(
                            UserLoginNameText.getText(),
                            PasswordText.getText(),
                            UserNameText.getText(),
                            selectPermissions.getPermissionGroupID(),
                            MFAKey
                    );
                } else {
                    UserService.createUser(
                            UserLoginNameText.getText(),
                            PasswordText.getText(),
                            UserNameText.getText(),
                            selectPermissions.getPermissionGroupID()
                    );
                }
                success.setTitle("成功");
                success.setHeaderText("用户创建成功");
                success.setContentText("用户创建成功");
                success.showAndWait();
                stagePool.closeStage(stageName);
            } catch (SQLException e) {
                error.setTitle("错误");
                error.setHeaderText("数据库错误");
                error.setContentText("数据库错误，请联系系统管理员");
                error.showAndWait();
            } catch (UserException e) {
                error.setTitle("错误");
                error.setHeaderText("用户创建失败");
                error.setContentText(e.getMessage());
                error.showAndWait();
            }
        } else {
            toUpdateUser.setUserName(UserNameText.getText());
            toUpdateUser.setPermissionGroupID(selectPermissions.getPermissionGroupID());
            if (isMFA) {
                toUpdateUser.setMFA(MFAKey);
            } else {
                toUpdateUser.setMFA("");
            }
            if (!PasswordText.getText().isEmpty()) {
                toUpdateUser.setPassword(PasswordText.getText());
            }
            try {
                UserService.updateUser(toUpdateUser);
                success.setTitle("成功");
                success.setHeaderText("用户修改成功");
                success.setContentText("用户修改成功");
                success.showAndWait();
                stagePool.closeStage(stageName);
            } catch (SQLException e) {
                error.setTitle("错误");
                error.setHeaderText("数据库错误");
                error.setContentText("数据库错误，请联系系统管理员");
                error.showAndWait();
            }
        }

    }

    @FXML
    protected void onCreateQRCodeButtonClicked() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("警告");
        if (UserLoginNameText.getText().isEmpty()) {
            alert.setHeaderText("用户名不能为空");
            alert.setContentText("请先输入用户名");
            alert.showAndWait();
            return;
        }
        if (UserNameText.getText().isEmpty()) {
            alert.setHeaderText("姓名不能为空");
            alert.setContentText("请先输入姓名");
            alert.showAndWait();
            return;
        }
        MFAQRCode.setImage(Tools.getTOTPQRCodeImage(MFAKey, "银行管理系统 - " + UserLoginNameText.getText(), UserNameText.getText()));
    }

    /**
     * @param stagePool
     * @param stageName
     */
    @Override
    public void setStagePool(StagePool stagePool, String stageName) {
        this.stagePool = stagePool;
        this.stageName = stageName;
    }
}
