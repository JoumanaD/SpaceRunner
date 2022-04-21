package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;

public class SmallInfoLabel extends Label {

	private final static String FONT_PATH = "src/resources/kenvector_future.ttf";
	
	public SmallInfoLabel(String text) {
		
		setPrefWidth(130);
		setPrefHeight(50);
		BackgroundImage backgroundImage = new BackgroundImage(new Image("resources/shipChooser/grey_button15.png", 130, 50, false, false),
				BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		setBackground(new Background(backgroundImage));
		setAlignment(Pos.CENTER_LEFT);
		setPadding(new Insets(10, 10, 10, 10));
		setLabelFont();
		setText(text);
		
		
	}
	
	private void setLabelFont() {
		try {
			setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)), 15));
		} catch (FileNotFoundException e) {
			setFont(Font.font("Verdana",15));
		}
	}

}
