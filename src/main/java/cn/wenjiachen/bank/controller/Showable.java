package cn.wenjiachen.bank.controller;

import cn.wenjiachen.bank.controller.view.StagePool;

/**
 * 可展示接口
 * <br>
 * 该接口用于初始化窗口池和窗口管理必备的资源。
 * <br>
 * 所有主动申请窗口池管理资源的、或主动申请被窗口池管理的窗口，其Controller均需要实现该接口，以表明需要被窗口池管理。
 * <br>
 * 以下为接受窗口池管理的示例代码。
 * <code>
 * <br>
 * private StagePool stagePool;
 * <br>
 * private String StageName;
 * <br>
 * \@Override
 * <br>
 * public void setStagePool(StagePool stagePool,String stageName){
 * <br>
 * this.stagePool = stagePool;
 * <br>
 * this.stageName = stageName;
 * <br>
 * }
 * </code>
 */
public interface Showable {
    /**
     * 初始化窗口管理
     *
     * @param stagePool 当前应用程序的窗口池实例
     * @param stageName 当前窗口名称
     */
    void setStagePool(StagePool stagePool, String stageName);
}
