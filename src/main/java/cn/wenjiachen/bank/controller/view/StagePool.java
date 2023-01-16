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

/**
 * 窗口池，用于管理所有窗口。
 * <br>
 * 窗口池类似于一个单例模式，但是不是单例模式。该类在Application启动时初始化，因此，该类的实例是唯一的。
 * <br>
 * 对于主动接受窗口池管理的窗口，会为其注入一个唯一的类实例用于接受管理和控制所有窗口，
 * <br>
 * 对于被动接受管理的窗口，其内部无控制窗口方法，所以本类是否单例与其无关。
 * <br>
 * 主动接受窗口池管理的窗口，需要实现Showable接口，该接口提供了一个初始化方法，用于注入窗口池类实例和该窗口名。
 */
public class StagePool {
    private Map<String, Stage> stageMap = new HashMap<>();

    /**
     * 将一个已经创建好的Stage实例添加到窗口池中。
     *
     * @param name  窗口名
     * @param stage 窗口实例
     */
    public void addStage(String name, Stage stage) {
        stageMap.put(name, stage);
    }

    /**
     * 删除Stage
     *
     * @param name 窗口名
     */
    public void delStage(String name) {
        stageMap.remove(name);
    }

    /**
     * 加载窗口
     *
     * @param name 窗口名称
     * @param FXML FXML文件名
     * @return Stage实例
     * @throws IOException 文件读写错误
     */
    public Stage LoadStage(String name, String FXML) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource(FXML));
        Scene BalanceView = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Hello!");
        return getStage(name, fxmlLoader, BalanceView, stage);
    }

    /**
     * 加载窗口
     *
     * @param name 窗口名称
     * @param FXML FXML文件
     * @param x    长度
     * @param y    宽度
     * @return Stage
     * @throws IOException 文件读写异常
     */
    public Stage LoadStage(String name, String FXML, int x, int y) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource(FXML));
        Scene BalanceView = new Scene(fxmlLoader.load(), x, y);
        Stage stage = new Stage();
        stage.setTitle("Hello!");
        return getStage(name, fxmlLoader, BalanceView, stage);
    }

    /**
     * 加载窗口
     *
     * @param name        窗口名称
     * @param FXML        FXML文件
     * @param WindowTitle 窗口标题
     * @return Stage
     * @throws IOException 文件读写异常
     */
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
            if (fxmlLoader.getController().getClass().isAssignableFrom(Showable.class)) {
                Showable controlledStage = (Showable) fxmlLoader.getController();
                controlledStage.setStagePool(this, name);
            } else {
                System.out.println("FXML 对应的 Controller 没有实现 Showable 接口,未被注入 StagePool");
            }
        } catch (ClassCastException e) {
            System.out.println("该窗口接受窗口池管理，但无法初始化。 " + e.getMessage());
        }
        stageMap.put(name, stage);
        return stage;
    }

    /**
     * 检查窗口是否存在
     *
     * @param name 窗口名
     * @return 是否存在
     */
    public boolean isStageExist(String name) {
        return stageMap.containsKey(name);
    }

    /**
     * 获取当前实例内存活的窗口列表
     *
     * @return 窗口名列表
     */
    public List<String> getAllStage() {
        return new ArrayList<>(stageMap.keySet());
    }

    /**
     * 获取窗口实例
     *
     * @param name 窗口名
     * @return Stage
     */
    public Stage getStage(String name) {
        return stageMap.get(name);
    }

    /**
     * 显示窗口
     *
     * @param name 窗口名
     */
    public void show(String name) {
        stageMap.get(name).show();
    }

    /**
     * 关闭窗口
     *
     * @param name 窗口名
     */
    public void closeStage(String name) {
        stageMap.get(name).close();
        stageMap.remove(name);
    }

    /**
     * 关闭所有窗口
     */
    public void closeAllStage() {
        for (String name : stageMap.keySet()) {
            stageMap.get(name).close();
            stageMap.remove(name);
        }
    }


    /**
     * 展示并等待，阻塞线程
     *
     * @param name 窗口名称
     */
    public void showAndWait(String name) {
        stageMap.get(name).showAndWait();
        stageMap.remove(name);
    }

    /**
     * 除指定窗口外关闭所有窗口
     *
     * @param name 窗口名称
     */
    public void closeAllExcept(String name) {
        for (String key : stageMap.keySet()) {
            if (!key.equals(name)) {
                stageMap.get(key).close();
                stageMap.remove(key);
            }
        }
    }
}
