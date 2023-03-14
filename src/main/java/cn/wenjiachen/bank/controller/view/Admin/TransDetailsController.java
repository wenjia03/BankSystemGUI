package cn.wenjiachen.bank.controller.view.Admin;

import cn.wenjiachen.bank.Application;
import cn.wenjiachen.bank.exception.TransException;
import cn.wenjiachen.bank.domain.SearchType;
import cn.wenjiachen.bank.domain.Trans.ProfileTrans;
import cn.wenjiachen.bank.domain.Trans.enums.TransStatus;
import cn.wenjiachen.bank.domain.Trans.enums.TransType;
import cn.wenjiachen.bank.service.Trans.TransService;
import cn.wenjiachen.bank.utils.Tools;
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

import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TransDetailsController implements Initializable {

    @FXML
    private RadioButton BankCard;

    @FXML
    private TableColumn<ProfileTrans, String> FromID;

    @FXML
    private ToggleGroup Groups;

    @FXML
    private RadioButton Info;

    @FXML
    private TableView<ProfileTrans> Table;

    @FXML
    private TableColumn<ProfileTrans, String> Teller;

    @FXML
    private TableColumn<ProfileTrans, String> ToID;

    @FXML
    private TableColumn<?, ?> TransDate;

    @FXML
    private RadioButton TransIDRadio;

    @FXML
    private TableColumn<?, ?> TransNote;
    @FXML
    private TableColumn<?, ?> TransID;

    @FXML
    private TableColumn<ProfileTrans, String> TransStatusColumn;

    @FXML
    private TableColumn<ProfileTrans, String> TransTypeColumn;

    @FXML
    private Label infoLabel;

    @FXML
    private AnchorPane loadingPane;

    @FXML
    private Button returnTrans;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchText;

    @FXML
    private Button stopTrans;

    @FXML
    private TableColumn<?, ?> transBalance;

    @FXML
    private ChoiceBox<String> TypeChoise;

    private ObservableList<ProfileTrans> selectedItems;

    private List<ProfileTrans> profileTransList;

    private Map<TransType, String> transTypeStringMap;

    /**
     * 初始化一个静态的对照Map
     * 此处的初始化方法是使用了Stream
     * <a href="https://juejin.cn/post/7034660287395397669">参考链接</a>
     */
    private final Map<TransStatus, String> TransStatusStringMap = Stream.of(new Object[][]{
            {TransStatus.SUCCESS, "交易成功"},
            {TransStatus.PROCESSING, "交易处理中"},
            {TransStatus.FAIL, "交易失败"},
            {null, "全部"}
    }).collect(Collectors.toMap((data -> (TransStatus) data[0]), data -> (String) data[1]));
    private final Map<TransType, String> TransTypeStringMap = Stream.of(new Object[][]{
            {TransType.DEPOSIT, "存款"},
            {TransType.WITHDRAW, "取款"},
            {TransType.TRANSFER, "转账"},
            {TransType.PAYMENT, "支付"},
            {TransType.STOP, "止付"},
            {TransType.RETURN, "倒回"},
            {null, "全部"}
    }).collect(Collectors.toMap((data -> (TransType) data[0]), data -> (String) data[1]));


    /**
     * 初始化 在此处为初始化表格的属性构造器工厂对象
     */
    @Override

    public void initialize(URL url, ResourceBundle resourceBundle) {
        // 设置当前表格的单行选择模式
        Table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        // 获取基本的数据元素
        // 需要注意的是，这里需要一定一定之一PropertyValueFactory的传入参数，该工厂底层使用反射，属性名错误会导致反射失败返回NullPtr
        TransDate.setCellValueFactory(new PropertyValueFactory<>("TransDate"));
        TransID.setCellValueFactory(new PropertyValueFactory<>("TransID"));
        transBalance.setCellValueFactory(new PropertyValueFactory<>("TransMoney"));
        TransNote.setCellValueFactory(new PropertyValueFactory<>("TransInfo"));

        /*
          此处使用了一个自定义的工厂方法，为TransType枚举提供注释显示。
          此处应修改SceneBuilder给定的默认Class示例，并显式的给出TableColumn的泛型参数
          参考链接：
          https://docs.oracle.com/javase/10/docs/api/javafx/util/class-use/Callback.html
          https://stackoverflow.com/questions/24934704/understanding-cellfactories-and-callback
          https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TableColumn.CellDataFeatures.html
         */
        TransTypeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ProfileTrans, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ProfileTrans, String> profileTransStringCellDataFeatures) {
                return new ReadOnlyStringWrapper(TransTypeStringMap.get(profileTransStringCellDataFeatures.getValue().getTransType()));
            }
        });
        Teller.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ProfileTrans, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ProfileTrans, String> profileTransStringCellDataFeatures) {
                return new ReadOnlyStringWrapper(profileTransStringCellDataFeatures.getValue().getTeller().getUserName());
            }
        });

        ToID.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ProfileTrans, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ProfileTrans, String> profileTransStringCellDataFeatures) {
                if (profileTransStringCellDataFeatures.getValue().getTransType() == TransType.WITHDRAW) {
                    return new ReadOnlyStringWrapper("现金");
                }
                return new ReadOnlyStringWrapper(profileTransStringCellDataFeatures.getValue().getToID() + "(" + profileTransStringCellDataFeatures.getValue().getToUserProfiles().getUserName() + ")");
            }
        });
        FromID.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ProfileTrans, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ProfileTrans, String> profileTransStringCellDataFeatures) {
                if (profileTransStringCellDataFeatures.getValue().getTransType() == TransType.DEPOSIT) {
                    return new ReadOnlyStringWrapper("现金");
                }
                return new ReadOnlyStringWrapper(profileTransStringCellDataFeatures.getValue().getFromID() + "(" + profileTransStringCellDataFeatures.getValue().getFromUserProfiles().getUserName() + ")");
            }
        });

        TransStatusColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ProfileTrans, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ProfileTrans, String> profileTransStringCellDataFeatures) {
                return new ReadOnlyStringWrapper(TransStatusStringMap.get(profileTransStringCellDataFeatures.getValue().getTransStatus()));
            }
        });

        reloading();

        selectedItems = Table.getSelectionModel().getSelectedItems();
        selectedItems.addListener(new ListChangeListener<ProfileTrans>() {
            @Override
            public void onChanged(Change<? extends ProfileTrans> change) {
                if (change.getList().size() == 0) {
                    stopTrans.setDisable(true);
                    returnTrans.setDisable(true);
                    return;
                }
                System.out.println("Selected: " + change.getList().get(0));
                if (change.getList().size() > 0) {
                    stopTrans.setDisable(false);
                    returnTrans.setDisable(false);
                }
                infoLabel.setText("当前选择项：" + change.getList().get(0).getTransID());
            }
        });

        for (String s : TransStatusStringMap.values()) {
            TypeChoise.getItems().add(s);
        }
        if (!Application.LoginPermissions.HasPermission(new String[]{"HIGH_ALL", "HIGH_DEPOSITOR"})) {
            returnTrans.setDisable(true);
            stopTrans.setDisable(true);
        }

    }


    private void reloading() {
        loadingPane.setVisible(true);
        Task<Void> task = new Task<>() {
            int length = 0;

            @Override
            protected Void call() throws Exception {
                try {
                    List<ProfileTrans> profileTrans = TransService.fetchAllTrans();
                    profileTransList = new ArrayList<>(profileTrans);
                    Table.getItems().clear();
                    Table.getItems().addAll(profileTransList);
                    length = profileTransList.size();
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
        List<ProfileTrans> res = new ArrayList<>();
        System.out.println(Groups.getSelectedToggle().getProperties().get("id"));
        SearchType searchType = SearchType.ALL;
        String nowMode = "综合选择";
        if (TransIDRadio.isSelected()) {
            searchType = SearchType.TRANS_ID;
            nowMode = "流水号";
        } else if (Info.isSelected()) {
            searchType = SearchType.TRANS_NOTE;
            nowMode = "交易备注";
        } else if (BankCard.isSelected()) {
            searchType = SearchType.ASSOCIATED_CARD_ID;
            nowMode = "交易相关卡号";
        }
        TransType transType = Tools.valueGetKey(TransTypeStringMap, TypeChoise.getValue(), TransType.class);
        for (ProfileTrans trans : profileTransList) {
            if (transType != null && trans.getTransType() != transType)
                continue;
            switch (searchType) {
                case ALL -> {
                    if (trans.FromID.contains(searchText.getText()) || trans.ToID.contains(searchText.getText())
                            || trans.getTransInfo().contains(searchText.getText())
                            || trans.getTransID().contains(searchText.getText())
                            || trans.getFromUserProfiles().getUserName().contains(searchText.getText())
                            || trans.getToUserProfiles().getUserName().contains(searchText.getText()))
                        res.add(trans);
                }
                case ASSOCIATED_CARD_ID -> {
                    if (Objects.equals(trans.FromID, searchText.getText()) || Objects.equals(trans.ToID, searchText.getText()))
                        res.add(trans);
                }
                case TRANS_NOTE -> {
                    if (trans.getTransInfo().contains(searchText.getText()))
                        res.add(trans);
                }
                case TRANS_ID -> {
                    if (Objects.equals(trans.getTransID(), searchText.getText()))
                        res.add(trans);
                }
            }
        }

        Table.getItems().clear();
        Table.getItems().addAll(res);
        infoLabel.setText("已筛选，共获取 " + res.size() + " 条记录，当前模式：" + nowMode);
    }

    @FXML
    protected void onStopTransButtonClicked() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        Alert warning = new Alert(Alert.AlertType.WARNING);
        warning.setHeaderText("警告");
        warning.setContentText("您确定要停止交易 " + selectedItems.get(0).getTransID() + " 吗？止付有几率止付失败，且不可撤销");
        warning.setTitle("警告");
        warning.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    TransService.stopTrans(selectedItems.get(0));
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setHeaderText("成功");
                    alert1.setContentText("交易已终止。");
                    alert1.setTitle("成功");
                } catch (TransException e) {
                    alert.setTitle("错误");
                    alert.setHeaderText("错误");
                    alert.setContentText(e.getMessage());
                } catch (SQLException e) {
                    alert.setTitle("错误");
                    alert.setHeaderText("错误");
                    alert.setContentText("数据库错误,请联系管理员  " + e.getMessage());
                }
            }
        });

        reloading();
    }

    @FXML
    protected void onReturnTransButtonClicked() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        Alert warning = new Alert(Alert.AlertType.WARNING);
        warning.setTitle("警告");
        warning.setHeaderText("警告");
        warning.setContentText("您确定要退回该交易吗？");
        warning.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    TransService.returnTrans(selectedItems.get(0));
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setHeaderText("成功");
                    alert1.setContentText("交易已退回。");
                    alert1.setTitle("成功");
                } catch (TransException e) {
                    alert.setTitle("错误");
                    alert.setHeaderText("错误");
                    alert.setContentText(e.getMessage());
                } catch (SQLException e) {
                    alert.setTitle("错误");
                    alert.setHeaderText("错误");
                    alert.setContentText("数据库错误,请联系管理员  " + e.getMessage());
                }
            }
        });
        reloading();
    }


    @FXML
    protected void copyText() {
        String elementTableSelected = Table.getSelectionModel().getSelectedItem().toString();
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(elementTableSelected);
        clipboard.setContent(content);
        infoLabel.setText("已复制到剪贴板");
    }

}
