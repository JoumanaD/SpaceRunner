package view;

import java.util.ArrayList;
import java.util.List;

import model.InfoLabel;
import model.Ship;
import model.ShipPicker;
import model.SpaceRunnerButton;
import model.SpaceRunnerSubScene;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ViewManager {

	// -------------------------------------------------------------------------
	// Les Attributs 
	//  -------------------------------------------------------------------------
	//
	// taille de l'écran du menu 
    private static final int HEIGHT = 768;
    private static final int WIDTH = 1024;

    // AnchorPane organiser la conception de notre view interface 
    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;

    // Button pour lancer le jeu
    private final static int MENU_BUTTONS_START_X = 100;
    private final static int MENU_BUTTONS_START_Y = 250;

	// les fenetres des buttons 
	private SpaceRunnerSubScene shipChooserSubScene;
	private SpaceRunnerSubScene scoreSubscene;
	private SpaceRunnerSubScene helpSubscene;
	private SpaceRunnerSubScene creditsSubscene;
	
	private SpaceRunnerSubScene sceneToHide; 

    // liste pour stocker les buttons pour le menu 
    List<SpaceRunnerButton> menuButtons;

	// liste des vaisseaux 
	List<ShipPicker> shipsList;
	// Le vaisseau choisie 
	private Ship choosenShip;

    public ViewManager() {
        menuButtons = new ArrayList<>();
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
		createSubScenes();
        createButtons();
        createBackground();
		createLogo();
    }
    
	public Stage getMainStage() {
        return mainStage;
    }

	private void showSubScene(SpaceRunnerSubScene subScene) {
		if(sceneToHide != null) {
			sceneToHide.moveSubScene();
		}

		subScene.moveSubScene();
		sceneToHide = subScene;

	}
	private void createSubScenes() {
		
		scoreSubscene = new SpaceRunnerSubScene();
		mainPane.getChildren().addAll(scoreSubscene);

		createShipChooserSubScene();
		createHelpSubScene();
		createCreditsSubScene();
	}

	// -------------------------------------------------------------------------
	// PLAY SUBSCENE
	// -------------------------------------------------------------------------
	private void createShipChooserSubScene() {
		shipChooserSubScene = new SpaceRunnerSubScene();
		mainPane.getChildren().add(shipChooserSubScene);

		InfoLabel chooseShipLabel = new InfoLabel("CHOOSE YOUR SHIP");
		chooseShipLabel.setLayoutX(110);
		chooseShipLabel.setLayoutY(25);
		shipChooserSubScene.getPane().getChildren().add(chooseShipLabel);
		shipChooserSubScene.getPane().getChildren().add(createShipsToChoose());
		shipChooserSubScene.getPane().getChildren().add(createButtonToStart());
	}

	private HBox createShipsToChoose() {
		HBox box = new HBox();
		box.setSpacing(20);
		shipsList = new ArrayList<>();
		for (Ship ship : Ship.values()) {
			ShipPicker shipToPick = new ShipPicker(ship);
			shipsList.add(shipToPick);
			box.getChildren().addAll(shipToPick);
			shipToPick.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					for (ShipPicker ship : shipsList) {
						ship.setIsCircleChoosen(false);
					}
					shipToPick.setIsCircleChoosen(true);
					choosenShip = shipToPick.getShip();
					
				}
			});
		}
		
		box.setLayoutX(300 - (118*2));
		box.setLayoutY(100);
		return box;
	}

	// -------------------------------------------------------------------------
	// CREDITS SUBSCENE
	// -------------------------------------------------------------------------
	private void createCreditsSubScene() {
		creditsSubscene = new SpaceRunnerSubScene();
		mainPane.getChildren().add(creditsSubscene);

		InfoLabel creditsLabel = new InfoLabel("CREDITS");
		creditsLabel.setLayoutX(110);
		creditsLabel.setLayoutY(25);
		
		Text etudiantTitre = new Text (50, 120, "Etudiantes");
		etudiantTitre.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
		etudiantTitre.setFill(Color.GOLD);
		
		Text n1 = new Text (50, 170, "ELDAKAR Joumana");
		Text n2 = new Text (50, 200, "KAYA    Delphine");
		n1.setFont(Font.font ("Verdana", FontWeight.BOLD, 20));
		n1.setFill(Color.WHITE);
		n2.setFont(Font.font ("Verdana", FontWeight.BOLD, 20));
		n2.setFill(Color.WHITE);
		
		Text encadrantTitre = new Text (350, 120, "Encadrant");
		encadrantTitre.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
		encadrantTitre.setFill(Color.GOLD);
		
		Text prof = new Text (350, 185, " Binh-Minh Bui-Xuan");
		prof.setFont(Font.font ("Verdana", FontWeight.BOLD, 20));
		prof.setFill(Color.WHITE);
		
		ImageView logoSorbonne = new ImageView("resources/logo_Sorbonne.png");
		logoSorbonne.setFitWidth(150);
		logoSorbonne.setFitHeight(150);
		logoSorbonne.setLayoutX(390);
		logoSorbonne.setLayoutY(220);
		
		ImageView logoJeu = new ImageView("resources/logo_Jeu.png");
		logoJeu.setFitWidth(180);
		logoJeu.setFitHeight(150);
		logoJeu.setLayoutX(60);
		logoJeu.setLayoutY(220);
		
		
		creditsSubscene.getPane().getChildren().addAll(creditsLabel, etudiantTitre, n1,n2, 
				encadrantTitre, prof, logoSorbonne, logoJeu);
		
	}
	
	// -------------------------------------------------------------------------
	// HELP SUBSCENE
	// -------------------------------------------------------------------------
	private void createHelpSubScene() {
		helpSubscene = new SpaceRunnerSubScene();
		mainPane.getChildren().add(helpSubscene);
		
		InfoLabel helpLabel = new InfoLabel("HELP");
		helpLabel.setLayoutX(110);
		helpLabel.setLayoutY(25);
		
		Text projet = new Text (50, 120, "Projet CPA :\n ");
		projet.setFont(Font.font ("Verdana", FontWeight.BOLD, 30));
		projet.setFill(Color.GOLD);
		Text jeu = new Text (50, 150, "Jeu SpaceShooter");
		jeu.setFont(Font.font ("Verdana", FontWeight.BOLD, 20));
		jeu.setFill(Color.BEIGE);
		Text jeuDetails = new Text (60, 180, "- éviter les objets qui tombe\n\n"
				+ "- sauf les étoiles jaunes qui augmente le score\n\n"
				+ "- Le joueur posséde 3 vies\n\n"
				+ "- Une vie perdue lorsque un objet tombe sur le vaisseau\n\n"
				+ "- Si le joueur perd les 3 vies alors il est perdu!\n\n");
		jeuDetails.setFont(Font.font ("Verdana", FontWeight.BOLD, 15));
		jeuDetails.setFill(Color.WHITE);
		helpSubscene.getPane().getChildren().addAll(helpLabel, projet, jeu, jeuDetails);
	}
	
	
	
	// -------------------------------------------------------------------------------------
	// LES BUTTONS DE MENU INTITAL 
	// -------------------------------------------------------------------------------------
	//
	private SpaceRunnerButton createButtonToStart() {
		SpaceRunnerButton startButton = new SpaceRunnerButton("START");
		startButton.setLayoutX(350);
		startButton.setLayoutY(300);
		
		
		startButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (choosenShip != null) {
					GameViewManager gameManager = new GameViewManager();
					gameManager.creatNewGame(mainStage, choosenShip);
				}
				
			}
		});
		
		return startButton;
	}

    private void addMenuButton(SpaceRunnerButton button) {
        button.setLayoutX(MENU_BUTTONS_START_X);
        button.setLayoutY(MENU_BUTTONS_START_Y + menuButtons.size() * 80);
        menuButtons.add(button);
        mainPane.getChildren().addAll(button);
    }

    private void createButtons() {
        createStartButton();
        createScoresButton();
        createHelpButton();
        createCreditsButton();
        createExitButton();
    }

    private void createStartButton() {
		SpaceRunnerButton startButton = new SpaceRunnerButton("PLAY");
		addMenuButton(startButton);
		
		startButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				showSubScene(shipChooserSubScene);
				
			}
		});
	}
	
	private void createScoresButton() {
		SpaceRunnerButton scoresButton = new SpaceRunnerButton("SCORES");
		addMenuButton(scoresButton);
		
		scoresButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				showSubScene(scoreSubscene);
				
			}
		});
	}
	
	private void createHelpButton() {
		SpaceRunnerButton helpButton = new SpaceRunnerButton("HELP");
		addMenuButton(helpButton);
		
		helpButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				showSubScene(helpSubscene);
				
			}
		});
	}
	
	private void createCreditsButton() {
		
		SpaceRunnerButton creditsButton = new SpaceRunnerButton("CREDITS");
		addMenuButton(creditsButton);
		
		
		creditsButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				showSubScene(creditsSubscene);
			}
		});
		
	}
	
	private void createExitButton() {
		SpaceRunnerButton exitButton = new SpaceRunnerButton("EXIT");
		addMenuButton(exitButton);
		
		exitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				mainStage.close();
				
			}
		});
		
	}

    private void createBackground() {
        Image backgroudImage = new Image("resources/space.png", 256, 256, false, false);
        BackgroundImage background = new BackgroundImage(backgroudImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        mainPane.setBackground(new Background(background));
    }

	private void createLogo() {
		ImageView logo = new ImageView("resources/logo.png");
		logo.setFitWidth(400);
		logo.setFitHeight(250);
		logo.setLayoutX(320);
		logo.setLayoutY(50);

		logo.setOnMouseDragEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				logo.setEffect(new DropShadow());
			}
			
		});

		logo.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				logo.setEffect(null);
			}
			
		});

		mainPane.getChildren().add(logo);
	}


}
