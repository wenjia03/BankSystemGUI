package cn.wenjiachen.bank.controller.view.Admin;

import cn.wenjiachen.bank.Application;
import cn.wenjiachen.bank.Dao.TransException;
import cn.wenjiachen.bank.controller.Showable;
import cn.wenjiachen.bank.controller.view.StagePool;
import cn.wenjiachen.bank.domain.Trans.enums.TransType;
import cn.wenjiachen.bank.domain.UserProfile;
import cn.wenjiachen.bank.service.Trans.TransService;
import cn.wenjiachen.bank.utils.Tools;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateTransViewController implements Initializable, Showable {
    @FXML
    private TextField BalanceText;

    @FXML
    private Button Confirm;

    @FXML
    private TextField FromUserText;

    @FXML
    private TextField TransNoteText;


    @FXML
    private Label balanceLabel;

    @FXML
    private RadioButton paymentRadio;

    @FXML
    private Button selectFromUserButton;

    @FXML
    private Button selectToUserButton;

    @FXML
    private Label tipsLabel;

    @FXML
    private TextField toUserText;

    @FXML
    private ToggleGroup transTypeGroup;

    @FXML
    private RadioButton transferRadio;

    private UserProfile fromUser;

    private UserProfile toUser;
    private StagePool stagePool;
    private String stageName;


    /**
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        balanceLabel.setText("");
        BalanceText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                try {
                    balanceLabel.setText(Tools.toChineseUpper(Double.parseDouble(t1)));
                } catch (Exception e) {
                    System.out.println("Error");
                }
            }
        });
    }

    public void onConfirmClicked() throws Exception {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("转账失败");
        alert.setHeaderText("交易出错");
        if (fromUser == null || toUser == null) {
            alert.setContentText("请选择转账用户");
            alert.showAndWait();
            return;
        }
        if (balanceLabel.getText().equals("")) {
            alert.setContentText("请输入转账金额");
            alert.showAndWait();
            return;
        }
        if (!Application.LoginPermissions.HasPermission(new String[]{"HIGH_ALL", "HIGH_TRANS"})) {
            if (!fromUser.checkPassword(Application.getUserInputPassword())) {
                alert.setContentText("密码错误，请重试");
                alert.showAndWait();
            }
        }

        try {
            TransType transType = null;
            if (transferRadio.isSelected()) {
                transType = TransType.TRANSFER;
            } else {
                transType = TransType.PAYMENT;
            }
            TransService.createTrans(fromUser.getUserBankCardNumber(), toUser.getUserBankCardNumber(),
                    BalanceText.getText(), transType, Application.LoginedUser, TransNoteText.getText());
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("转账成功");
            successAlert.setHeaderText("转账成功");
            successAlert.setContentText("转账成功");
            successAlert.showAndWait();
            stagePool.closeStage(stageName);
        } catch (TransException transException) {
            alert.setContentText("当前交易失败：" + transException.getMessage());
            alert.showAndWait();
        }
    }

    public void onSelectFromUserClicked() {
        System.out.println("onSelectFromUserClicked");
        try {
            this.fromUser = Application.choiceUser();
            if (fromUser != null) {
                FromUserText.setText(fromUser.getUserName() + "(" + fromUser.getUserBankCardNumber() + ")");
            } else {
                FromUserText.setText("未选择");
            }
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("程序关键性资源损坏");
            alert.setContentText("程序关键性资源损坏，请联系管理员");
            alert.showAndWait();
        }
    }

    public void onSelectToUserClicked() {
        System.out.println("onSelectToUserClicked");
        try {
            this.toUser = Application.choiceUser();
            if (toUser != null) {
                toUserText.setText(toUser.getUserName() + "(" + toUser.getUserBankCardNumber() + ")");
            } else {
                toUserText.setText("未选择");
            }
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("程序关键性资源损坏");
            alert.setContentText("程序关键性资源损坏，请联系管理员");
            alert.showAndWait();
        }
    }

    /**
     */
    @Override
    public void setStagePool(StagePool stagePool, String stageName) {
        this.stagePool = stagePool;
        this.stageName = stageName;

    }
}
