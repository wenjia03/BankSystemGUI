package cn.wenjiachen.bank.controller.view.Admin;

import cn.wenjiachen.bank.Application;
import cn.wenjiachen.bank.domain.Profiles;
import cn.wenjiachen.bank.domain.SearchType;
import cn.wenjiachen.bank.domain.UserProfiles;
import cn.wenjiachen.bank.service.user.UserProfileService;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DepositorChoiceController implements Initializable {

    @FXML
    private ToggleGroup Groups;

    @FXML
    private RadioButton IDCard;

    @FXML
    private RadioButton Name;

    @FXML
    private RadioButton Phone;

    @FXML
    private RadioButton BankCard;

    @FXML
    private TableView<UserProfiles> Table;

    @FXML
    private Button changeInfoButton;

    @FXML
    private Button deleteUserButton;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchText;

    @FXML
    private Label infoLabel;

    @FXML
    private TableColumn<?, ?> userAddress;

    @FXML
    private TableColumn<?, ?> userBalance;

    @FXML
    private TableColumn<?, ?> userBirthday;

    @FXML
    private TableColumn<?, ?> userCardID;

    @FXML
    private Button newProfiles;

    @FXML
    private AnchorPane loadingPane;

    @FXML
    private TableColumn<?, ?> userIDCard;

    @FXML
    private TableColumn<?, ?> userName;

    @FXML
    private TableColumn<?, ?> userPhone;
    private ObservableList<UserProfiles> selectedItems;

    private List<UserProfiles> userProfilesList;

    /**
     * 初始化 在此处为初始化表格的属性构造器工厂对象
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // 设置当前表格的单行选择模式
        Table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        // 获取基本的数据元素
        userName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        userCardID.setCellValueFactory(new PropertyValueFactory<>("userBankCardNumber"));
        userAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        userBirthday.setCellValueFactory(new PropertyValueFactory<>("userBirthDate"));
        userPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        userIDCard.setCellValueFactory(new PropertyValueFactory<>("UserIDCard"));
        userBalance.setCellValueFactory(new PropertyValueFactory<>("UserBankCardBalance"));
        reloading();

        selectedItems = Table.getSelectionModel().getSelectedItems();
        selectedItems.addListener(new ListChangeListener<Profiles>() {
            @Override
            public void onChanged(Change<? extends Profiles> change) {
                System.out.println("Selected: " + change.getList().get(0));
                infoLabel.setText("选择：" + change.getList().get(0).getUserName() + " [" + change.getList().get(0).getUserUUID() + "]");
            }
        });

    }


    @FXML
    protected void onNewProfilesButtonClicked() throws IOException {
        if (selectedItems.size() > 0) {
            Application.ChoiceUser = selectedItems.get(0);
            Stage stage = (Stage) infoLabel.getScene().getWindow();
            stage.close();
        } else {
            infoLabel.setText("错误 ： 未选择用户");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText("错误");
            alert.setContentText("未选择用户");
            alert.showAndWait();
        }
    }

    private void reloading() {
        loadingPane.setVisible(true);
        Task<Void> task = new Task<>() {
            int length = 0;

            @Override
            protected Void call() throws Exception {
                try {
                    List<UserProfiles> profilesList = UserProfileService.fetchAllProfiles();
                    userProfilesList = new ArrayList<>(profilesList);
                    Table.getItems().clear();
                    Table.getItems().addAll(profilesList);
                    length = profilesList.size();
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
        List<UserProfiles> res = new ArrayList<>();
        System.out.println(Groups.getSelectedToggle().getProperties().get("id"));
        SearchType searchType = SearchType.ALL;
        String nowMode = "综合选择";
        if (IDCard.isSelected()) {
            searchType = SearchType.ID_CARD;
            nowMode = "身份证号";
        } else if (Name.isSelected()) {
            searchType = SearchType.NAME;
            nowMode = "姓名";
        } else if (Phone.isSelected()) {
            searchType = SearchType.PHONE;
            nowMode = "手机号";
        } else if (BankCard.isSelected()) {
            searchType = SearchType.BANK_CARD;
            nowMode = "银行卡号";
        }
        for (UserProfiles profiles : userProfilesList) {
            switch (searchType) {
                case ID_CARD -> {
                    if (profiles.getUserIDCard().contains(searchText.getText()))
                        res.add(profiles);
                }
                case NAME -> {
                    if (profiles.getUserName().contains(searchText.getText()))
                        res.add(profiles);
                }
                case PHONE -> {
                    if (profiles.getPhoneNumber().contains(searchText.getText()))
                        res.add(profiles);
                }
                case BANK_CARD -> {
                    if (profiles.getUserBankCardNumber().contains(searchText.getText()))
                        res.add(profiles);
                }
                case ALL -> {
                    if (profiles.getUserIDCard().contains(searchText.getText()) ||
                            profiles.getUserName().contains(searchText.getText()) ||
                            profiles.getPhoneNumber().contains(searchText.getText()) ||
                            profiles.getUserBankCardNumber().contains(searchText.getText()))
                        res.add(profiles);
                }
            }
        }
        Table.getItems().clear();
        Table.getItems().addAll(res);
        infoLabel.setText("已筛选，共获取 " + res.size() + " 条记录，当前模式：" + nowMode);
    }


}
