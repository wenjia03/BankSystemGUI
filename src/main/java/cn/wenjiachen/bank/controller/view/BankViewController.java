package cn.wenjiachen.bank.controller.view;

import cn.wenjiachen.bank.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * @author Wenjia Chen
 * @date 2023/1/4 23:37
 */

public class BankViewController implements Initializable {

    @FXML
    private Label Balance;

    @FXML
    private Label BankName;

    @FXML
    private Label CardID;

    @FXML
    private Button ConfirmButton;



    @FXML
    protected void onConfirmButtonClicked() throws IOException {
        Stage s = (Stage) ConfirmButton.getScene().getWindow();
        s.close();
    }

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Balance.setText("" + Application.StoreProfiles.getUserBankCardBalance());
        BankName.setText("" + Application.StoreProfiles.getUserBank().getBankName());

        // 将银行卡号每四位中增加一个空格
        String cardID = Application.StoreProfiles.getUserBankCardNumber();
        StringBuilder newCardID = new StringBuilder();
        for (int i = 0; i < cardID.length(); i++) {
            if (i % 4 == 0 && i != 0) {
                newCardID.append(" ");
            }
            newCardID.append(cardID.charAt(i));
        }
        CardID.setText(newCardID.toString());
    }
}
