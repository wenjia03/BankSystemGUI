package cn.wenjiachen.bank.controller.view;

import cn.wenjiachen.bank.Application;
import cn.wenjiachen.bank.domain.User;
import cn.wenjiachen.bank.service.PermissionService;
import cn.wenjiachen.bank.service.user.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


/**
 * @author Wenjia Chen
 * @date 2023/1/420:40
 */


public class LoginViewController {

    @FXML
    private TextField LoginAccount;

    @FXML
    private Button LoginButton;

    @FXML
    private PasswordField password;

    @FXML
    protected void onLoginButtonClick() throws Exception {
        System.out.println("Login");
        User user = UserService.fetchUserByEmail(LoginAccount.getText());
        if (user != null && user.isPasswordValid(password.getText()) && !user.isLocked()) {
            System.out.println("Login Success");
            Application.LoginedUser = user;
            Application.LoginPermissions = PermissionService.getPermissionsList(user.getPermissionGroupID()).get(0);
            Application.showMainView();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("登录出错");
            alert.setHeaderText("登录失败");
            alert.setContentText("请检查您的账号密码，或确保当前账户未被锁定，拥有正常的权限。");
            alert.showAndWait();
        }
    }

}
