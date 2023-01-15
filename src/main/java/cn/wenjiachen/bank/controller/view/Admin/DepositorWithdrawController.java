package cn.wenjiachen.bank.controller.view.Admin;

import cn.wenjiachen.bank.Application;
import cn.wenjiachen.bank.DAO.TransException;
import cn.wenjiachen.bank.domain.Profiles;
import cn.wenjiachen.bank.domain.Trans.enums.TransType;
import cn.wenjiachen.bank.service.Trans.TransService;
import cn.wenjiachen.bank.utils.Tools;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DepositorWithdrawController implements Initializable {

    @FXML
    private Button ChoiseUserButton;

    @FXML
    private Button ConfirmButton;

    @FXML
    private Label chineseNumberLabel;

    @FXML
    private TextField transNoteText;

    @FXML
    private TextField transValueText;

    @FXML
    private TextField userText;

    private Profiles nowProfile = null;

    private Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    private Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);

    @FXML
    protected void onSetChoiseUserButtonClicked() {
        System.out.println("onSetChoiseUserButtonClicked");
        try {
            nowProfile = Application.choiceUser();
            if (nowProfile != null) {
                userText.setText(nowProfile.getUserName() + " (" + nowProfile.getUserBankCardNumber() + ")");
            } else {
                userText.setText("请选择用户");
            }
        } catch (IOException e) {
            errorAlert.setTitle("错误");
            errorAlert.setHeaderText("软件内部关键文件丢失");
            errorAlert.setContentText("软件内部关键文件丢失，请联系IT支持");
            errorAlert.showAndWait();
        }
    }

    @FXML
    protected void onSetConfirmButtonClicked() throws IOException {
        if (nowProfile == null) {
            errorAlert.setTitle("错误");
            errorAlert.setHeaderText("未选择用户");
            errorAlert.setContentText("请先选择用户");
            errorAlert.showAndWait();
            return;
        }
        if (transValueText.getText().isEmpty()) {
            errorAlert.setTitle("错误");
            errorAlert.setHeaderText("未输入金额");
            errorAlert.setContentText("请先输入金额");
            errorAlert.showAndWait();
            return;
        }
        if (!Application.LoginPermissions.HasPermission(new String[]{"HIGH_ALL", "HIGH_TRANS"})) {
            if (nowProfile.checkPassword(Application.getUserInputPassword())) {
                errorAlert.setTitle("错误");
                errorAlert.setHeaderText("密码错误");
                errorAlert.setContentText("密码错误");
                errorAlert.showAndWait();
                return;
            }

        }
        if(!Application.MFAConfirm()){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("错误");
            errorAlert.setHeaderText("MFA验证有误");
            errorAlert.setContentText("MFA验证有误");
            errorAlert.showAndWait();
            return;
        }
        try {
            Double.parseDouble(transValueText.getText());
            TransService.createTrans(
                    nowProfile.getUserBankCardNumber(),
                    null, // 取款交易没有入账账户
                    transValueText.getText(),
                    TransType.WITHDRAW,
                    Application.LoginedUser,
                    "向" + nowProfile.getUserBankCardNumber() + "取款  " + transNoteText.getText()
            );
            infoAlert.setTitle("成功");
            infoAlert.setHeaderText("取款成功");
            infoAlert.setContentText("取款成功");
            infoAlert.showAndWait();
            transValueText.setText("");
            transNoteText.setText("");
            nowProfile = null;
            userText.setText("请选择用户");
        } catch (NumberFormatException e) {
            errorAlert.setTitle("错误");
            errorAlert.setHeaderText("金额格式错误");
            errorAlert.setContentText("请检查金额格式");
            errorAlert.showAndWait();
            return;
        } catch (SQLException e) {
            errorAlert.setTitle("错误");
            errorAlert.setHeaderText("服务器错误");
            errorAlert.setContentText("请检查网络连接，或联系IT支持或系统管理员。错误信息：" + e.getMessage());
            errorAlert.showAndWait();
            return;
        } catch (TransException e) {
            errorAlert.setTitle("错误");
            errorAlert.setHeaderText("交易错误");
            errorAlert.setContentText("交易错误:：" + e.getMessage());
            errorAlert.showAndWait();
            return;
        }
    }


    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("initialize");

        transValueText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                try {
                    chineseNumberLabel.setText(Tools.toChineseUpper(Double.parseDouble(t1)));
                } catch (Exception e) {
                    System.out.println("无法解析");
                }
            }
        });

    }
}
