module cn.wenjiachen.bank {


    requires org.kordamp.bootstrapfx.core;
    requires googleauth;
    requires jbcrypt;
    requires java.sql;
    requires druid;
    requires jjwt;
    requires javafx.fxml;
    requires javafx.controls;
    requires java.naming;

    requires core;
    requires java.desktop;



    opens cn.wenjiachen.bank to javafx.fxml;
    exports cn.wenjiachen.bank;
    exports cn.wenjiachen.bank.domain;
    exports cn.wenjiachen.bank.domain.Permission;
    opens cn.wenjiachen.bank.domain to javafx.fxml;
    opens cn.wenjiachen.bank.domain.Trans to javafx.fxml, javafx.base;
    opens cn.wenjiachen.bank.domain.Permission to javafx.fxml, javafx.base;
    opens cn.wenjiachen.bank.domain.Trans.enums to javafx.fxml;
    opens cn.wenjiachen.bank.domain.Permission.impl to javafx.fxml, javafx.base;
    exports cn.wenjiachen.bank.Dao;
    opens cn.wenjiachen.bank.Dao to javafx.fxml;
    exports cn.wenjiachen.bank.controller.view;
    opens cn.wenjiachen.bank.controller.view to javafx.fxml;
    exports cn.wenjiachen.bank.controller.view.Admin;
    opens cn.wenjiachen.bank.controller.view.Admin to javafx.fxml;
    opens cn.wenjiachen.bank.exception to javafx.fxml;
    exports cn.wenjiachen.bank.exception;

}