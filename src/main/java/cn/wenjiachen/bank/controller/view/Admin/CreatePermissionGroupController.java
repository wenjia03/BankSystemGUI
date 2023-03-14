package cn.wenjiachen.bank.controller.view.Admin;

import cn.wenjiachen.bank.Application;
import cn.wenjiachen.bank.controller.Showable;
import cn.wenjiachen.bank.controller.view.StagePool;
import cn.wenjiachen.bank.domain.Permission.Permissions;
import cn.wenjiachen.bank.domain.Permission.impl.PermissionsImpl;
import cn.wenjiachen.bank.service.PermissionService;
import cn.wenjiachen.bank.utils.Tools;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CreatePermissionGroupController implements Initializable, Showable {

    @FXML
    private Button Confirm;

    @FXML
    private Button addPermission;

    @FXML
    private Button delPermission;

    @FXML
    private TextField permissionGroupNameText;

    @FXML
    private ListView<String> permissionList;

    @FXML
    private ChoiceBox<String> permissionSelect;

    private String selectListValue = null;

    private List<String> permissionValue = new ArrayList<>();

    private Permissions toUpdatePermission = null;
    private StagePool stagePool;
    private String stageName;

    /**
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        toUpdatePermission = Application.toUpdatePermissions;
        if (toUpdatePermission != null) {
            permissionGroupNameText.setText(toUpdatePermission.getPermissionGroupName());
            permissionValue = toUpdatePermission.getPermissions();
            for (String permission : permissionValue) {
                permissionList.getItems().add(Permissions.PermissionMaps.get(permission));
            }
            Confirm.setText("更新");
        }
        permissionList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                selectListValue = t1;
                delPermission.setDisable(selectListValue == null || selectListValue.isEmpty());
            }
        });
        for (String s : Permissions.validPermissionString) {
            permissionSelect.getItems().add(Permissions.PermissionMaps.get(s));
        }
    }

    @FXML
    private void onAddPermissionClicked() {
        permissionList.getItems().add(permissionSelect.getValue());
        permissionValue.add(Tools.valueGetKey(Permissions.PermissionMaps, permissionSelect.getValue(), String.class));
    }

    @FXML
    private void onDelPermissionClicked() {
        permissionValue.remove(Tools.valueGetKey(Permissions.PermissionMaps, selectListValue, String.class));
        permissionList.getItems().remove(selectListValue);
    }

    @FXML
    private void onConfirmClicked() throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("提示");
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle("错误");
        if (permissionGroupNameText.getText().isEmpty()) {
            error.setHeaderText("权限组名不能为空");
            error.setContentText("权限组名不能为空");
            error.show();
            return;
        }
        if (!Application.MFAConfirm()) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("错误");
            errorAlert.setHeaderText("MFA验证有误");
            errorAlert.setContentText("MFA验证有误");
            errorAlert.showAndWait();
            return;
        }
        try {
            if (toUpdatePermission == null) {
                PermissionService.createPermissionGroup(
                        new PermissionsImpl(permissionGroupNameText.getText(), permissionValue)
                );
                alert.setHeaderText("创建成功");
                alert.setContentText("添加成功");
                alert.show();
                stagePool.closeStage(stageName);
            } else {
                toUpdatePermission.setPermissionGroupName(permissionGroupNameText.getText());
                toUpdatePermission.setPermissions(permissionValue);
                PermissionService.updatePermissionGroup(toUpdatePermission);
                alert.setHeaderText("修改成功");
                alert.setContentText("修改成功");
                alert.show();
                stagePool.closeStage(stageName);
            }
        } catch (SQLException e) {
            error.setHeaderText("创建失败");
            error.setContentText("服务器内部错误，请联系IT支持或系统管理员。 " + e.getMessage());
            error.show();
        }
    }

    /**
     *
     */
    @Override
    public void setStagePool(StagePool stagePool, String stageName) {
        this.stagePool = stagePool;
        this.stageName = stageName;

    }
}