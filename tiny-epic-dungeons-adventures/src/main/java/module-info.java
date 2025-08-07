module hr.game.tinyepicdungeonsadventures {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires org.apache.logging.log4j.core;

    opens hr.game.tinyepicdungeonsadventures to javafx.fxml;
    exports hr.game.tinyepicdungeonsadventures;
}