module hr.game.tinyepicdungeonsadventures {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;
    requires java.naming;
    requires static lombok;
    requires org.slf4j;
    requires org.apache.logging.log4j.core;
    requires org.apache.logging.log4j.slf4j2.impl;
    requires java.desktop;

    opens hr.game.tinyepicdungeonsadventures.ui to javafx.fxml;
    opens hr.game.tinyepicdungeonsadventures.chat to java.rmi;

    exports hr.game.tinyepicdungeonsadventures;
}