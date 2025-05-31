module hr.game.tinyepicdungeonsadventures {
    requires javafx.controls;
    requires javafx.fxml;

    opens hr.game.tinyepicdungeonsadventures to javafx.fxml;
    exports hr.game.tinyepicdungeonsadventures;
}