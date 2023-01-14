package cn.wenjiachen.bank.controller.view;

import cn.wenjiachen.bank.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static cn.wenjiachen.bank.Application.*;

public class AdminMainController implements Initializable {


    @FXML
    private MenuItem depositorCreate;

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onDepositorCreateClicked() throws IOException {
        showAdminDepositorCreateView();
        System.out.println("onDepositorCreateClicked");
    }

    public void onDepositorDetailsClicked() throws IOException {
        System.out.println("onDepositorDetailsClicked");
        showAdminDepositorDetailsView();
    }

    public void onDepositorDeleteClicked(ActionEvent actionEvent) throws IOException {
        System.out.println("onDepositorDeleteClicked");
        showAdminDepositorDeleteView();
    }

    public void onTransDetailsClicked(ActionEvent actionEvent) throws IOException {
        System.out.println("onTransDetailsClicked");
        Application.showAdminTransDetailsClickView();
    }

    public void onTransCreateClicked(ActionEvent actionEvent) throws IOException {
        showAdminCreateTransClickView();
    }
}
