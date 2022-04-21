package model;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ShipPicker extends VBox {

    private ImageView circleImage;
    private ImageView shipImage;

    private String circleNotChoosen = "resources/shipChooser/grey_tickWhite.png";
    private String circleChoosen = "resources/shipChooser/red_tick.png";

    private Ship ship;

    private boolean isCircleChoosen;

    public ShipPicker(Ship ship) {
        circleImage = new ImageView(circleNotChoosen);
        shipImage = new ImageView(ship.getURL());
        shipImage.setFitHeight(120);
        shipImage.setFitWidth(110);
        this.ship = ship; 
        isCircleChoosen = false;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.getChildren().addAll(circleImage, shipImage);
    }

    public Ship getShip() {
        return this.ship;
    }

    public boolean getIsCircleChoosen() {
        return this.isCircleChoosen;
    }

    public void setIsCircleChoosen(boolean isCircleChoosen) {
        this.isCircleChoosen = isCircleChoosen;
        String imageToSet = this.isCircleChoosen ? circleChoosen : circleNotChoosen;
        circleImage.setImage(new Image(imageToSet));
    }
}
