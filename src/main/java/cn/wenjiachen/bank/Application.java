package cn.wenjiachen.bank;

import cn.wenjiachen.bank.config.SQLConfig;
import cn.wenjiachen.bank.controller.view.StagePool;
import cn.wenjiachen.bank.domain.Permission.Permissions;
import cn.wenjiachen.bank.domain.User;
import cn.wenjiachen.bank.domain.UserProfile;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.sql.Connection;

public class Application extends javafx.application.Application {

    public static User LoginedUser = null;

    public static UserProfile ChoiceUser = null;

    public static String userInputMFA = "";

    public static String userInputPassword = "";

    private static Scene LoginView;

    public static UserProfile toUpdate;

    public static Permissions selectPermission;

    public static StagePool stagePool = new StagePool();

    public static Permissions LoginPermissions;

    public static User toUpdateUser = null;
    public static Permissions toUpdatePermissions = null;

    @Override
    public void start(Stage stage) throws Exception {
        stagePool.LoadStage("Loading", "Loading.fxml", "欢迎");
        stagePool.show("Loading");
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                try {
                    Connection connection = SQLConfig.getDataSource().getConnection();
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("错误");
                    alert.setHeaderText("数据库连接失败");
                    alert.setContentText("请检查数据库配置是否正确");
                    alert.showAndWait();
                    System.exit(0);
                }
                return null;
            }

            /**
             *
             */
            @Override
            protected void succeeded() {
                try {
                    stagePool.LoadStage("Login", "LoginView.fxml", "请登录");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stagePool.show("Login");
                stagePool.closeStage("Loading");
                super.succeeded();
            }
        };
        new Thread(task).start();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void showMainView() throws IOException {
        Stage stage = stagePool.LoadStage("Main", "AdminMain.fxml", 640, 480, "主界面 - " + LoginedUser.getUserName());
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("确认");
                alert.setHeaderText("确认退出");
                alert.setContentText("确认退出登录并关闭系统吗？");
                alert.showAndWait().ifPresent(response -> {
                    if (response.getButtonData().isCancelButton()) {
                        windowEvent.consume();
                    } else {
                        stagePool.closeAllStage();
                        System.exit(0);
                    }
                });
            }
        });
        stagePool.show("Main");
        stagePool.closeStage("Login");
    }

    public static void showAdminDepositorCreateView(boolean isWait) throws IOException {
        toUpdate = null;
        stagePool.LoadStage("CreateDepositor", "CreateDepositor.fxml", "储户开户");
        if (!isWait)
            stagePool.show("CreateDepositor");
        else
            stagePool.showAndWait("CreateDepositor");

    }

    public static void showAdminDepositorCreateView() throws IOException {
        showAdminDepositorCreateView(false);
    }

    public static void showAdminDepositorDeleteView(boolean isWait) throws IOException {
        toUpdate = null;
        stagePool.LoadStage("DeleteDepositor", "DeleteDepositorView.fxml", "储户销户");
        if (!isWait)
            stagePool.show("DeleteDepositor");
        else
            stagePool.showAndWait("DeleteDepositor");
    }

    public static void showAdminDepositorDeleteView() throws IOException {
        showAdminDepositorDeleteView(false);
    }

    public static void changeUserProfiles(UserProfile userProfiles) throws IOException {
        toUpdate = userProfiles;
        stagePool.LoadStage("ChangeUserInfo", "CreateDepositor.fxml", "修改信息");
        stagePool.showAndWait("ChangeUserInfo");
        toUpdate = userProfiles;
    }

    public static void showAdminDepositorDetailsView(boolean isWait) throws IOException {
        stagePool.LoadStage("DepositorDetails", "DepositorDetails.fxml", "储户详情");
        if (!isWait)
            stagePool.show("DepositorDetails");
        else
            stagePool.showAndWait("DepositorDetails");
    }

    public static void showAdminDepositorDetailsView() throws IOException {
        showAdminDepositorDetailsView(false);
    }


    public static String getUserInputMFA() throws IOException {
        stagePool.LoadStage("2FA", "2FAView.fxml", "验证密码器");
        stagePool.showAndWait("2FA");
        String t = userInputMFA;
        userInputMFA = "";
        return t;
    }

    public static String getUserInputPassword() throws IOException {
        stagePool.LoadStage("PasswordConfirm", "PasswordConfirmView.fxml");
        stagePool.showAndWait("PasswordConfirm");
        String t = userInputPassword;
        userInputPassword = "";
        return t;
    }

    public static UserProfile choiceUser() throws IOException {
        ChoiceUser = null;
        stagePool.LoadStage("DepositorChoice", "DepositorChoice.fxml", "选择储户");
        stagePool.showAndWait("DepositorChoice");
        return ChoiceUser;
    }

    public static Permissions choicePermission() throws IOException {
        selectPermission = null;
        stagePool.LoadStage("PermissionSelect", "PermissionSelect.fxml", "选择权限");
        stagePool.showAndWait("PermissionSelect");
        return selectPermission;
    }

    public static void showAdminTransDetailsClickView() throws IOException {
        stagePool.LoadStage("TransDetail", "TransDetail.fxml", "交易详情");
        stagePool.show("TransDetail");
    }

    public static void showAdminCreateTransClickView(boolean isWait) throws IOException {
        stagePool.LoadStage("CreateTrans", "CreateTransView.fxml", "创建交易");
        if (!isWait)
            stagePool.show("CreateTrans");
        else
            stagePool.showAndWait("CreateTrans");
    }

    public static void showAdminCreateTransClickView() throws IOException {
        showAdminCreateTransClickView(false);
    }

    public static void showAdminDepositorDepositView(boolean isWait) throws IOException {
        stagePool.LoadStage("DepositorDeposit", "DepositorDepositView.fxml", "存款");
        if (!isWait)
            stagePool.show("DepositorDeposit");
        else
            stagePool.showAndWait("DepositorDeposit");
    }

    public static void showAdminDepositorDepositView() throws IOException {
        showAdminDepositorDepositView(false);
    }

    public static void showAdminDepositorWithdrawView(boolean isWait) throws IOException {
        stagePool.LoadStage("DepositorWithdraw", "DepositorWithdrawView.fxml", "取款");
        if (!isWait)
            stagePool.show("DepositorWithdraw");
        else
            stagePool.showAndWait("DepositorWithdraw");
    }

    public static void showAdminDepositorWithdrawView() throws IOException {
        showAdminDepositorWithdrawView(false);
    }

    public static void showAdminPermissionDetailView(boolean isWait) throws IOException {
        stagePool.LoadStage("PermissionDetails", "PermissionDetails.fxml", "权限详情");
        if (!isWait)
            stagePool.show("PermissionDetails");
        else
            stagePool.showAndWait("PermissionDetails");
    }

    public static void showAdminPermissionDetailView() throws IOException {
        showAdminPermissionDetailView(false);
    }

    public static void showAdminCreatePermissionGroupView(boolean isWait) throws IOException {
        stagePool.LoadStage("CreatePermissionGroup", "CreatePermissionGroup.fxml", "创建权限组");
        if (!isWait)
            stagePool.show("CreatePermissionGroup");
        else
            stagePool.showAndWait("CreatePermissionGroup");
    }

    public static void showAdminCreatePermissionGroupView() throws IOException {
        showAdminCreatePermissionGroupView(false);
    }

    public static void showAdminCreateUserView(boolean isWait) throws IOException {
        stagePool.LoadStage("CreateUser", "CreateUser.fxml", "创建用户");
        if (!isWait)
            stagePool.show("CreateUser");
        else
            stagePool.showAndWait("CreateUser");
    }

    public static void showAdminCreateUserView() throws IOException {
        showAdminCreateUserView(false);
    }

    public static void showAdminUserDetailView(boolean isWait) throws IOException {
        stagePool.LoadStage("UserDetails", "UserDetails.fxml", "用户详情");
        if (!isWait)
            stagePool.show("UserDetails");
        else
            stagePool.showAndWait("UserDetails");
    }

    public static void showAdminUserDetailView() throws IOException {
        showAdminUserDetailView(false);
    }

    public static void UpdateUser(User user) throws IOException {
        toUpdateUser = user;
        stagePool.LoadStage("UpdateUser", "CreateUser.fxml", "修改用户");
        stagePool.showAndWait("UpdateUser");
        toUpdateUser = null;
    }

    public static void UpdatePermission(Permissions permissions) throws IOException {
        toUpdatePermissions = permissions;
        stagePool.LoadStage("UpdatePermission", "CreatePermissionGroup.fxml", "修改权限组");
        stagePool.showAndWait("UpdatePermission");
        toUpdatePermissions = null;
    }

    public static void Logout() throws IOException {
        LoginedUser = null;
        stagePool.LoadStage("Login", "LoginView.fxml");
        stagePool.show("Login");
        stagePool.closeAllExcept("Login");
    }

    public static boolean MFAConfirm() throws IOException {
        if (LoginedUser.isMFAEnabled() == null || !LoginedUser.isMFAEnabled())
            return true;
        String MFA = getUserInputMFA();
        if (MFA == null || MFA.equals(""))
            return false;
        return LoginedUser.MFAValidate(Integer.parseInt(MFA));
    }
}