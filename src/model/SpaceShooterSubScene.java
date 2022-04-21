package model;

import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.util.Duration;

public class SpaceShooterSubScene extends SubScene{
	
	//private final static String FONT_PATH = "file:src/main/resources/com/example//kenvector_future.ttf";
	private final static String BACKGROUND_IMAGE = "resources/subscenebg.png";
	
	private  boolean isHidden;
	
	
	public SpaceShooterSubScene() {
		super(new AnchorPane(), 600, 400);
		prefWidth(600);
		prefHeight(400);

        Image image = new Image(BACKGROUND_IMAGE, 600, 400, false, true);
		BackgroundImage bgimage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
                                BackgroundPosition.DEFAULT, null);
		AnchorPane root2 = (AnchorPane) this.getRoot();

		root2.setBackground(new Background(bgimage));
		
		isHidden = true ;
		
		setLayoutX(1024);
		setLayoutY(250);
		
	}
	
	public void moveSubScene() {
		TranslateTransition transition = new TranslateTransition();
		transition.setDuration(Duration.seconds(0.3));
		transition.setNode(this);
		
		if (isHidden) {
			
			transition.setToX(-676);
			isHidden = false;
			
		} else {
			
			transition.setToX(0);
			isHidden = true ;
		}
		
		
		transition.play();
	}
	
	// une methode pour retourner le pain de sub scene 
	public AnchorPane getPane() {
		return (AnchorPane) this.getRoot();
	}

}

