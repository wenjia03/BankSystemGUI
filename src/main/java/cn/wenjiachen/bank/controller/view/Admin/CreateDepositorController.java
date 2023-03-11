package cn.wenjiachen.bank.controller.view.Admin;

import cn.wenjiachen.bank.Application;
import cn.wenjiachen.bank.controller.Showable;
import cn.wenjiachen.bank.controller.view.StagePool;
import cn.wenjiachen.bank.domain.UserProfile;
import cn.wenjiachen.bank.service.user.UserProfileService;
import cn.wenjiachen.bank.utils.Securities;
import cn.wenjiachen.bank.utils.Tools;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

public class CreateDepositorController implements Initializable, Showable {
    @FXML
    private ToggleGroup AccountType;

    @FXML
    private DatePicker Birthday;

    @FXML
    private Label IDCardLabel;

    @FXML
    private TextField address;

    @FXML
    private Label birthLabel;

    @FXML
    private Button cancel;

    @FXML
    private Button generateID;

    @FXML
    private Label passwordStatus;

    @FXML
    private RadioButton personAccount;

    @FXML
    private Button setPassword;

    @FXML
    private Button submit;

    @FXML
    private TextField userCardID;

    @FXML
    private TextField userIDCardText;

    @FXML
    private TextField userName;

    @FXML
    private TextField userPhoneNum;
    @FXML
    private RadioButton publicAccount;


    private String password;

    private Boolean isUpdate = false;
    // 是否为修改模式

    private UserProfile toUpdateUserProfiles;
    private StagePool stagePool;
    private String stageName;
    // 待修改

    @FXML
    protected void onGenerateIDClicked() {
        userCardID.setText(Tools.generateCardID());
    }

    @FXML
    protected void onSetPasswordClicked() throws IOException {
        setPassword.setVisible(false);
        password = Application.getUserInputPassword();
        if (password == null || password.isEmpty())
            passwordStatus.setText("未设置");
        else
            passwordStatus.setText("已设置");
        setPassword.setVisible(true);

    }

    @FXML
    protected void onCancelClicked() {
        stagePool.closeStage(stageName);
    }

    @FXML
    protected void onUserIDCardTextChanged() {
        // 验证该身份证是否合法
        if (personAccount.isSelected() && Tools.isIDCard(userIDCardText.getText())) {
            if (Tools.isIDCardDate(userIDCardText.getText())) {
                Birthday.setValue(Objects.requireNonNull(Tools.getIDCardBirthday(userIDCardText.getText())).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

            }
        }

    }

    protected void createModeSubmit() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("提示");
        try {
            UserProfile userProfiles = new UserProfile(
                    userName.getText(),
                    personAccount.isSelected() ?
                            Date.from(Birthday.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
                            : null,
                    userCardID.getText(),
                    "",
                    0.00,
                    Securities.getUUID(),
                    userPhoneNum.getText(),
                    address.getText(),
                    userIDCardText.getText()
            );
            userProfiles.setPassword(password);
            UserProfileService.createProfile(userProfiles);
            alert.setHeaderText("创建成功");
            alert.setContentText("创建成功");
            alert.showAndWait();
        } catch (Exception e) {
            alert.setHeaderText("内部信息错误");
            alert.setContentText("请联系管理员: " + e.getMessage());
            alert.show();
            return;
        }
        onCancelClicked();
    }

    protected void editModeSubmit() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("提示");

        try {
            toUpdateUserProfiles.setUserName(userName.getText());
            if (personAccount.isSelected())
                toUpdateUserProfiles.setUserBirthDate(Date.from(Birthday.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
            toUpdateUserProfiles.setUserIDCard(userCardID.getText());
            toUpdateUserProfiles.setPhoneNumber(userPhoneNum.getText());
            toUpdateUserProfiles.setAddress(address.getText());
            toUpdateUserProfiles.setUserIDCard(userIDCardText.getText());
            if (password != null && !password.isEmpty())
                toUpdateUserProfiles.setPassword(password);
            UserProfileService.updateProfile(toUpdateUserProfiles);
            alert.setHeaderText("修改成功");
            alert.setContentText("修改成功");
            alert.showAndWait();
        } catch (Exception e) {
            alert.setHeaderText("内部信息错误");
            alert.setContentText("请联系管理员: " + e.getMessage());
            alert.show();
            return;
        }
        onCancelClicked();
    }

    @FXML
    protected void onSubmitClicked() throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("提示");
        if ((password == null || password.isEmpty()) && !isUpdate) {
            alert.setHeaderText("信息有误");
            alert.setContentText("请先设置密码");
            alert.show();
            return;
        }
        if (personAccount.isSelected() && !Tools.isIDCard(userIDCardText.getText())) {
            alert.setHeaderText("信息有误");
            alert.setContentText("身份证号码不合法");
            alert.show();
            return;
        }

        if (userName.getText().isEmpty() || userPhoneNum.getText().isEmpty() ||
                (personAccount.isSelected() && Birthday.getValue() == null) || address.getText().isEmpty() || userCardID.getText().isEmpty()) {
            alert.setHeaderText("信息有误");
            alert.setContentText("请填写完整信息");
            alert.show();
            return;
        }
        if (isUpdate) {
            editModeSubmit();
        } else {
            createModeSubmit();
        }
    }

    /**
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // 为CardID 域添加监听器，用于填充生日 优化用户体验

        personAccount.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (t1) {
                    Birthday.setVisible(true);
                    birthLabel.setVisible(true);
                    IDCardLabel.setText("储户身份证号：");
                } else {
                    Birthday.setVisible(false);
                    Birthday.setValue(null);
                    birthLabel.setVisible(false);
                    IDCardLabel.setText("社会信用代码：");
                }
            }
        });


        userIDCardText.textProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                onUserIDCardTextChanged();
                System.out.println(t1);

            }
        });
        String thisTitle = "新储户 - 创建用户";
        isUpdate = (Application.toUpdate != null);
        // 修改模式启动 从数据缓存中读取数据
        if (isUpdate) {

            toUpdateUserProfiles = Application.toUpdate;
            Application.toUpdate = null;
            userName.setText(toUpdateUserProfiles.getUserName());
            if (toUpdateUserProfiles.userBirthDate != null)
                Birthday.setValue(Tools.dateTime(toUpdateUserProfiles.getUserBirthDate()));
            userCardID.setText(toUpdateUserProfiles.getUserBankCardNumber());
            userPhoneNum.setText(toUpdateUserProfiles.getPhoneNumber());
            address.setText(toUpdateUserProfiles.getAddress());
            userIDCardText.setText(toUpdateUserProfiles.getUserIDCard());
            passwordStatus.setText("未修改");
            generateID.setVisible(false);
            userCardID.setDisable(true);
            if (toUpdateUserProfiles.userBirthDate == null) {
                publicAccount.setSelected(true);
                personAccount.setSelected(false);
                Birthday.setVisible(false);
                Birthday.setValue(null);
                birthLabel.setVisible(false);
                IDCardLabel.setText("社会信用代码：");
            } else {
                personAccount.setSelected(true);
                publicAccount.setSelected(false);
                IDCardLabel.setText("储户身份证号：");

            }
            personAccount.setDisable(true);
            publicAccount.setDisable(true);
            thisTitle = userName + " - 信息修改";

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
