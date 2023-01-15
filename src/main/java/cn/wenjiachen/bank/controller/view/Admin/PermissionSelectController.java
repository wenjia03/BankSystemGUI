package cn.wenjiachen.bank.controller.view.Admin;

import cn.wenjiachen.bank.Application;
import cn.wenjiachen.bank.controller.Showable;
import cn.wenjiachen.bank.controller.view.StagePool;
import cn.wenjiachen.bank.domain.Permission.Permissions;
import cn.wenjiachen.bank.domain.SearchType;
import cn.wenjiachen.bank.service.PermissionService;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PermissionSelectController implements Initializable, Showable {

    @FXML
    private ToggleGroup Groups;

    @FXML
    private TableColumn<Permissions, String> PermissionsColumn;

    @FXML
    private TableView<Permissions> Table;

    @FXML
    private Button select;

    @FXML
    private Label infoLabel;

    @FXML
    private AnchorPane loadingPane;

    @FXML
    private TableColumn<Permissions, String> permissionGroupIDColumn;

    @FXML
    private TableColumn<Permissions, String> permissionGroupNameColumn;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchText;

    private ObservableList<Permissions> selectedItems;

    private List<Permissions> gettedPermissionsList;
    private StagePool stagePool;
    private String stageName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // 设置当前表格的单行选择模式
        Table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        // 获取基本的数据元素
        permissionGroupIDColumn.setCellValueFactory(new PropertyValueFactory<>("PermissionGroupID"));
        permissionGroupNameColumn.setCellValueFactory(new PropertyValueFactory<>("PermissionGroupName"));
        PermissionsColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Permissions, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Permissions, String> permissionsStringCellDataFeatures) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (String s : permissionsStringCellDataFeatures.getValue().getPermissions()) {
                            if (Permissions.validPermissionString.contains(s))
                                stringBuilder.append(Permissions.PermissionMaps.get(s)).append(";");
                            else
                                stringBuilder.append("未知权限(").append(s).append(")").append(";");
                        }
                        return new ReadOnlyStringWrapper(stringBuilder.toString());
                    }
                }
        );

        reloading();

        selectedItems = Table.getSelectionModel().getSelectedItems();
        selectedItems.addListener(new ListChangeListener<Permissions>() {
            @Override
            public void onChanged(Change<? extends Permissions> change) {
                if (change.getList().size() == 0) {
                    select.setDisable(true);
                    return;
                }
                System.out.println("Selected: " + change.getList().get(0));
                if (change.getList().size() > 0) {
                    select.setDisable(false);
                }
                infoLabel.setText("当前选择项：" + change.getList().get(0).getPermissionGroupName());
            }
        });

    }

    @FXML
    protected void onSelectButtonClicked() throws IOException {
        Application.selectPermission = selectedItems.get(0);
        stagePool.getStage(stageName).close();
    }

    @FXML
    protected void onNewProfilesButtonClicked() throws IOException {
        System.out.println("New Profiles Button Clicked");
        Application.showAdminDepositorCreateView(true);
        reloading();
    }

    private void reloading() {
        loadingPane.setVisible(true);
        Task<Void> task = new Task<>() {
            int length = 0;

            @Override
            protected Void call() throws Exception {
                try {
                    List<Permissions> permissionsList = PermissionService.getPermissionsList();
                    gettedPermissionsList = new ArrayList<>(permissionsList);
                    Table.getItems().clear();
                    Table.getItems().addAll(permissionsList);
                    length = gettedPermissionsList.size();
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
        List<Permissions> res = new ArrayList<>();
        System.out.println(Groups.getSelectedToggle().getProperties().get("id"));
        SearchType searchType = SearchType.ALL;
        String nowMode = "综合选择";

        for (Permissions permissions : gettedPermissionsList) {
            if (permissions.getPermissionGroupName().contains(searchText.getText()) || permissions.getPermissionGroupID().contains(searchText.getText())) {
                res.add(permissions);
            }
        }
        Table.getItems().clear();
        Table.getItems().addAll(res);
        infoLabel.setText("已筛选，共获取 " + res.size() + " 条记录，当前模式：" + nowMode);
    }

    @FXML
    protected void onDeleteButtonClicked() {

        reloading();
    }

    @FXML
    protected void copyText() {
        String elementTableSelected = Table.getSelectionModel().getSelectedItem().getPermissionGroupName();
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(elementTableSelected);
        clipboard.setContent(content);
        infoLabel.setText("已复制到剪贴板");
    }

    public void onCreateGroupClicked(ActionEvent actionEvent) {
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

