package cn.wenjiachen.bank.controller.view.Admin;

import cn.wenjiachen.bank.Application;
import cn.wenjiachen.bank.domain.Permission.Permissions;
import cn.wenjiachen.bank.domain.User;
import cn.wenjiachen.bank.service.PermissionService;
import cn.wenjiachen.bank.service.user.UserService;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class UserDetailsController implements Initializable {

    @FXML
    private ToggleGroup Groups;

    @FXML
    private Button LockUserButton;

    @FXML
    private TableColumn<User, String> LoginNameColumn;

    @FXML
    private TableView<User> Table;

    @FXML
    private TableColumn<User, String> UUIDColumn;

    @FXML
    private Button changeInfoButton;

    @FXML
    private Button createUserButton;

    @FXML
    private Button deleteUserButton;

    @FXML
    private Label infoLabel;

    @FXML
    private TableColumn<User, String> isMFAColumn;

    @FXML
    private AnchorPane loadingPane;

    @FXML
    private TableColumn<User, String> permissionGroupColumn;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchText;

    @FXML
    private TableColumn<User, String> userNameColumn;
    @FXML
    private TableColumn<User, String> isLockedColumn;
    private ObservableList<User> selectedItems;

    private List<User> userList;

    /**
     * 初始化 在此处为初始化表格的属性构造器工厂对象
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // 设置当前表格的单行选择模式
        Table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        // 获取基本的数据元素
        UUIDColumn.setCellValueFactory(new PropertyValueFactory<>("UUID"));
        LoginNameColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("UserName"));
        isMFAColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<User, String> userStringCellDataFeatures) {
                if (userStringCellDataFeatures.getValue().isMFAEnabled() == null)
                    return new ReadOnlyStringWrapper("否");
                return new ReadOnlyStringWrapper(userStringCellDataFeatures.getValue().isMFAEnabled() ? "是" : "否");
            }
        });
        isLockedColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<User, String> userStringCellDataFeatures) {
                if (userStringCellDataFeatures.getValue().isLocked() == null)
                    return new ReadOnlyStringWrapper("否");
                return new ReadOnlyStringWrapper(userStringCellDataFeatures.getValue().isLocked() ? "是" : "否");
            }
        });
        permissionGroupColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call
                    (TableColumn.CellDataFeatures<User, String> userStringCellDataFeatures) {
                try {
                    List<Permissions> permissionsList = PermissionService.getPermissionsList(userStringCellDataFeatures.getValue().getPermissionGroupID());
                    if (permissionsList.size() == 0) {
                        return new ReadOnlyStringWrapper("无法解析此用户的权限组");
                    } else {
                        return new ReadOnlyStringWrapper(permissionsList.get(0).getPermissionGroupName());
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        reloading();

        selectedItems = Table.getSelectionModel().

                getSelectedItems();
        selectedItems.addListener(new ListChangeListener<User>() {
            @Override
            public void onChanged(Change<? extends User> change) {
                if (change.getList().size() == 0) {
                    changeInfoButton.setDisable(true);
                    deleteUserButton.setDisable(true);
                    LockUserButton.setDisable(true);
                    return;
                }
                System.out.println("Selected: " + change.getList().get(0));
                if (change.getList().size() > 0) {
                    changeInfoButton.setDisable(false);
                    deleteUserButton.setDisable(false);
                    LockUserButton.setDisable(false);
                }
                infoLabel.setText("当前选择项：" + change.getList().get(0).getUserName() + " [" + change.getList().get(0).getUUID() + "]");
                if (change.getList().get(0).isLocked()) {
                    LockUserButton.setText("解锁用户");
                } else {
                    LockUserButton.setText("锁定用户");
                }
                if (Objects.equals(change.getList().get(0).getUUID(), Application.LoginedUser.getUUID())) {
                    LockUserButton.setDisable(true);
                    deleteUserButton.setDisable(true);
                } else {
                    LockUserButton.setDisable(false);
                    deleteUserButton.setDisable(false);
                }
            }

        });

    }

    @FXML
    protected void onChangeInfoButtonClicked() throws IOException {
        System.out.println("Change Info Button Clicked");
        User profiles = selectedItems.get(0);
        System.out.println(profiles);
        Application.UpdateUser(profiles);
        reloading();
    }

    @FXML
    protected void onLockedUserClick() throws IOException {
        User profiles = selectedItems.get(0);
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("警告");
        alert.setContentText("您确定要锁定用户 " + profiles.getUserName() + " 吗？");
        alert.setTitle("警告");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    UserService.changeLock(profiles.getUUID(), !profiles.isLocked());
                    reloading();
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setHeaderText("成功");
                    alert1.setContentText("用户 " + profiles.getUserName() + " 已被锁定");
                    alert1.setTitle("成功");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void reloading() {
        loadingPane.setVisible(true);
        Task<Void> task = new Task<>() {
            int length = 0;

            @Override
            protected Void call() throws Exception {
                try {
                    List<User> fetchAllUserList = UserService.fetchAllUser();
                    userList = new ArrayList<>(fetchAllUserList);
                    Table.getItems().clear();
                    Table.getItems().addAll(fetchAllUserList);
                    length = fetchAllUserList.size();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                return null;
            }

            /**
             *
             */
            @Override
            protected void succeeded() {
                loadingPane.setVisible(false);
                infoLabel.setText("已与服务器同步，共获取 " + length + " 条记录");
                super.succeeded();
            }
        };
        new Thread(task).start();
    }

    @FXML
    protected void searchFilter() {
        List<User> res = new ArrayList<>();
        System.out.println(Groups.getSelectedToggle().getProperties().get("id"));

        for (User user : userList) {
            if (user.getUserName().contains(searchText.getText()) || user.getEmail().contains(searchText.getText()) || user.getUUID().contains(searchText.getText())) {
                res.add(user);
            }

        }
        Table.getItems().clear();
        Table.getItems().addAll(res);
        infoLabel.setText("已筛选，共获取 " + res.size() + " 条记录");
    }

    @FXML
    protected void onDeleteButtonClicked() {
        User profiles = selectedItems.get(0);
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("警告");
        alert.setContentText("您确定要删除用户 " + profiles.getUserName() + " 吗？");
        alert.setTitle("警告");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    UserService.deleteUser(profiles.getUUID());
                    reloading();
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setHeaderText("成功");
                    alert1.setContentText("用户 " + profiles.getUserName() + " 已被删除");
                    alert1.setTitle("成功");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        reloading();
    }

    @FXML
    protected void copyText() {
        String elementTableSelected = Table.getSelectionModel().getSelectedItem().getUserName();
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(elementTableSelected);
        clipboard.setContent(content);
        infoLabel.setText("已复制到剪贴板");
    }

}
