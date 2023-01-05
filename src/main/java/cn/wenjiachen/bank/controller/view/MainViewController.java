package cn.wenjiachen.bank.controller.view;


import cn.wenjiachen.bank.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Wenjia Chen
 * @date 2023/1/421:51
 */



public class MainViewController implements Initializable {

    @FXML
    private Button AccountButton;

    @FXML
    private Button BalanceButton;

    @FXML
    private Button HistoryButton;

    @FXML
    private Button PwdChangedButton;

    @FXML
    private Button TransButton;

    @FXML
    private Button UserBaseInfoButton;

    @FXML
    private Label UserTitle;

    @FXML
    public void init1(){
        UserTitle.setText("欢迎您！" + Application.StoreProfiles.getUserName());
    }

    @FXML
    protected void onBalanceButtonClicked() throws IOException {
        Application.showBalanceView();
    }

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init1();
    }
}
