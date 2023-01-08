package cn.wenjiachen.bank.controller.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Wenjia Chen
 * @date 2023/1/5 16:56
 */


public class TransferViewController implements Initializable {

    @FXML
    private Label Tips;

    @FXML
    private TextField TransferBalance;

    @FXML
    private AnchorPane TransferPane;

    @FXML
    private Label TransferTips;

    @FXML
    private Label accountInfo;

    @FXML
    private TextField cardID;

    @FXML
    private Button confirmButton;

    @FXML
    private Label myInfo;

    @FXML
    private Button searchButton;


    /**
     * 初始化窗体
     *
     * @param url 资源定位
     * @param resourceBundle 资源绑定
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // 将提示窗口隐藏
        Tips.setVisible(false);
        TransferTips.setVisible(false);
        TransferPane.setVisible(false);

    }

    /**
     * 点击查询按钮
     */
    @FXML
    protected void onSearchButtonClick() {
        if(cardID.getText().isEmpty()) {
            Tips.setText("请输入卡号");
            Tips.setVisible(true);
        } else {
//            if()
        }
    }
}
