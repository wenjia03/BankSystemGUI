package cn.wenjiachen.bank.controller.view;

import cn.wenjiachen.bank.Application;
import cn.wenjiachen.bank.controller.Showable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StagePool {
    private Map<String, Stage> stageMap = new HashMap<>();


    public void addStage(String name, Stage stage) {
        stageMap.put(name, stage);
    }

    public void delStage(String name) {
        stageMap.remove(name);
    }

    public Stage LoadStage(String name, String FXML) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource(FXML));
        Scene BalanceView = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Hello!");
        return getStage(name, fxmlLoader, BalanceView, stage);
    }

    public Stage LoadStage(String name, String FXML, int x, int y) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource(FXML));
        Scene BalanceView = new Scene(fxmlLoader.load(), x, y);
        Stage stage = new Stage();
        stage.setTitle("Hello!");
        return getStage(name, fxmlLoader, BalanceView, stage);
    }

    public Stage LoadStage(String name, String FXML, String WindowTitle) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource(FXML));
        Scene BalanceView = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle(WindowTitle);
        return getStage(name, fxmlLoader, BalanceView, stage);
    }

    private Stage getStage(String name, FXMLLoader fxmlLoader, Scene balanceView, Stage stage) {
        stage.setScene(balanceView);
        try {
            // 通过 Loader 获取 FXML 对应的 ViewCtr，并将本 StageController 注入到 ViewCtr 中
            Showable controlledStage = (Showable) fxmlLoader.getController();
            controlledStage.setStagePool(this, name);
        } catch (ClassCastException e) {
            System.out.println("FXML 对应的 Controller 没有实现 Showable 接口,未被注入 StagePool");
        }
        stageMap.put(name, stage);
        return stage;
    }

    public boolean isStageExist(String name) {
        return stageMap.containsKey(name);
    }

    public List<String> getAllStage() {
        return new ArrayList<>(stageMap.keySet());
    }

    public Stage getStage(String name) {
        return stageMap.get(name);
    }

    public void show(String name) {
        stageMap.get(name).show();
    }

    public void closeStage(String name) {
        stageMap.get(name).close();
        stageMap.remove(name);
        stageMap.remove(name);
    }

    public void closeAllStage() {
        for (String name : stageMap.keySet()) {
            stageMap.get(name).close();
            stageMap.remove(name);
            stageMap.remove(name);
        }
    }


    public void showAndWait(String name) {
        stageMap.get(name).showAndWait();
    }

    public void closeAllExcept(String name) {
        for (String key : stageMap.keySet()) {
            if (!key.equals(name)) {
                stageMap.get(key).close();
                stageMap.remove(key);
            }
        }
    }
}
