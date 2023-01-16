package cn.wenjiachen.bank.controller.view;

import cn.wenjiachen.bank.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static cn.wenjiachen.bank.Application.*;

public class AdminMainController implements Initializable {


    @FXML
    private Menu Admin;

    @FXML
    private Menu Bussiness;

    @FXML
    private MenuItem Deposite;

    @FXML
    private MenuItem DepositiorDelete;

    @FXML
    private Menu Permission;

    @FXML
    private Menu Trans;

    @FXML
    private MenuItem TransCreate;

    @FXML
    private MenuItem TransFetch;

    @FXML
    private MenuItem Withdraw;

    @FXML
    private MenuItem depositorCreate;

    @FXML
    private MenuItem details;

    /**
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // 权限部分的核心代码
        if (LoginPermissions.HasPermission("HIGH_ALL")) // 高级权限
            return;
        if (!LoginPermissions.HasPermission("HIGH_DEPOSITOR")) {
            // 高级储户权限
            if (!LoginPermissions.HasPermission("NORMAL_DEPOSITOR_CREATE")) // 存款人创建
                depositorCreate.setVisible(false);
            if (!LoginPermissions.HasPermission("NORMAL_DEPOSITOR_DELETE")) // 存款人删除
                DepositiorDelete.setVisible(false);
            if (!LoginPermissions.HasPermission("NORMAL_DEPOSITOR_FETCH")) // 存款
                details.setVisible(false);
        }
        if (!LoginPermissions.HasPermission("HIGH_TRANS")) {
            if (!LoginPermissions.HasPermission("NORMAL_TRANS_CREATE")) // 交易创建
                TransCreate.setVisible(false);
            if (!LoginPermissions.HasPermission("NORMAL_TRANS_FETCH"))
                TransFetch.setVisible(false);
        }
        if (!LoginPermissions.HasPermission("HIGH_BUSINESS")) {
            if (!LoginPermissions.HasPermission("NORMAL_BUSINESS_DEPOSIT"))
                Deposite.setVisible(false);
            if (!LoginPermissions.HasPermission("NORMAL_BUSINESS_WITHDRAW"))
                Withdraw.setVisible(false);
        }
        if (!LoginPermissions.HasPermission("HIGH_ADMIN")) {
            Permission.setVisible(false);
            Admin.setVisible(false);
        }

    }

    public void onDepositorCreateClicked() throws IOException {
        showAdminDepositorCreateView();
        System.out.println("onDepositorCreateClicked");
    }

    public void onDepositorDetailsClicked() throws IOException {
        System.out.println("onDepositorDetailsClicked");
        showAdminDepositorDetailsView();
    }

    public void onDepositorDeleteClicked(ActionEvent actionEvent) throws IOException {
        System.out.println("onDepositorDeleteClicked");
        showAdminDepositorDeleteView();
    }

    public void onTransDetailsClicked(ActionEvent actionEvent) throws IOException {
        System.out.println("onTransDetailsClicked");
        Application.showAdminTransDetailsClickView();
    }

    public void onTransCreateClicked(ActionEvent actionEvent) throws IOException {
        showAdminCreateTransClickView();
    }

    public void onDepositorDepositClicked(ActionEvent actionEvent) throws IOException {
        showAdminDepositorDepositView();
    }

    public void onDepositorWithdrawClicked(ActionEvent actionEvent) throws IOException {
        showAdminDepositorWithdrawView();
    }

    public void onPermissionDetailClicked(ActionEvent actionEvent) throws IOException {
        showAdminPermissionDetailView();
    }

    public void onCreatePermissionGroupClicked(ActionEvent actionEvent) throws IOException {
        showAdminCreatePermissionGroupView();
    }

    public void onCreateUserClicked(ActionEvent actionEvent) throws IOException {
        showAdminCreateUserView();
    }

    public void onUserDetailClicked(ActionEvent actionEvent) throws IOException {
        showAdminUserDetailView();
    }

    public void onLogoutClicked(ActionEvent actionEvent) throws IOException {
        Logout();
    }
}
