module hr.game.tinyepicdungeonsadventures {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;
    requires java.naming;
    requires static lombok;
    requires org.apache.logging.log4j.core;

    opens hr.game.tinyepicdungeonsadventures.ui to javafx.fxml;
    opens hr.game.tinyepicdungeonsadventures.core to javafx.fxml;
    opens hr.game.tinyepicdungeonsadventures.model to javafx.fxml;

    exports hr.game.tinyepicdungeonsadventures;
}