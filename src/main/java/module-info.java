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


    opens cn.wenjiachen.bank to javafx.fxml;
    exports cn.wenjiachen.bank;
    exports cn.wenjiachen.bank.domain;
    opens cn.wenjiachen.bank.domain to javafx.fxml;
    exports cn.wenjiachen.bank.DAO;
    opens cn.wenjiachen.bank.DAO to javafx.fxml;
    exports cn.wenjiachen.bank.controller.view;
    opens cn.wenjiachen.bank.controller.view to javafx.fxml;
}