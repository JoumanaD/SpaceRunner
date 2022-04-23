package view;

import java.util.ArrayList;
import java.util.List;

import model.InfoLabel;
import model.Sauvegarde;
import model.Ship;
import model.ShipPicker;
import model.SpaceRunnerButton;
import model.SpaceRunnerSubScene;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
	
	// attribut qui va permettre de sauvegarder les scores
	private Sauvegarde save;
	
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
		save = new Sauvegarde();
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
		creditsSubscene = new SpaceRunnerSubScene();
		helpSubscene = new SpaceRunnerSubScene();
		mainPane.getChildren().addAll(scoreSubscene, creditsSubscene, helpSubscene);
		
		createScoreSubScene();
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
		InfoLabel creditsLabel = new InfoLabel("CREDITS");
		creditsLabel.setLayoutX(110);
		creditsLabel.setLayoutY(25);
		
		Text etudiantTitre = new Text (50, 120, "Etudiantes");
		etudiantTitre.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
		etudiantTitre.setFill(Color.GOLD);
		
		Text n1 = new Text (50, 170, "ELDAKAR  Joumana");
		Text n2 = new Text (50, 200, "KAYA        Delphine");
		n1.setFont(Font.font ("Verdana", FontWeight.BOLD, 20));
		n1.setFill(Color.WHITE);
		n2.setFont(Font.font ("Verdana", FontWeight.BOLD, 20));
		n2.setFill(Color.WHITE);
		
		Text encadrantTitre = new Text (350, 120, "Encadrant");
		encadrantTitre.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
		encadrantTitre.setFill(Color.GOLD);
		
		Text prof_cours = new Text (350, 170, "BUI-XUAN Binh-Minh");
		Text prof_tme = new Text (350, 200, "ESCRIOU   Arthur");
		prof_cours.setFont(Font.font ("Verdana", FontWeight.BOLD, 20));
		prof_cours.setFill(Color.WHITE);
		prof_tme.setFont(Font.font ("Verdana", FontWeight.BOLD, 20));
		prof_tme.setFill(Color.WHITE);
		
		ImageView logoSorbonne = new ImageView("resources/logo_Sorbonne.png");
		logoSorbonne.setFitWidth(130);
		logoSorbonne.setFitHeight(130);
		logoSorbonne.setLayoutX(390);
		logoSorbonne.setLayoutY(230);
		
		ImageView logoJeu = new ImageView("resources/logo_Jeu.png");
		logoJeu.setFitWidth(190);
		logoJeu.setFitHeight(150);
		logoJeu.setLayoutX(60);
		logoJeu.setLayoutY(220);
		
		Text info = new Text (110, 370, "logo du groupe");
		info.setFont(Font.font ("Verdana", FontWeight.BOLD, 10));
		
		
		
		creditsSubscene.getPane().getChildren().addAll(creditsLabel, etudiantTitre, n1,n2, 
				encadrantTitre, prof_cours, prof_tme, logoSorbonne, logoJeu, info);
		
	}
	
	// -------------------------------------------------------------------------
	// HELP SUBSCENE
	// -------------------------------------------------------------------------
	private void createHelpSubScene() {
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
				+ "- Si le joueur perd les 3 vies alors il a perdu!\n\n");
		jeuDetails.setFont(Font.font ("Verdana", FontWeight.BOLD, 15));
		jeuDetails.setFill(Color.WHITE);
		helpSubscene.getPane().getChildren().addAll(helpLabel, projet, jeu, jeuDetails);
	}

	// -------------------------------------------------------------------------
	// SCORE SUBSCENE
	// -------------------------------------------------------------------------
	private void createScoreSubScene() {
		scoreSubscene = new SpaceRunnerSubScene();
		mainPane.getChildren().add(scoreSubscene);
		fillScoreSubscene();
	}
	// remplit la section de score
	private void fillScoreSubscene() {
		InfoLabel scoreLabel = new InfoLabel("SCORES");
		scoreLabel.setLayoutX(110);
		scoreLabel.setLayoutY(25);
		
		Text titre = new Text (50, 120, "Derniers Scores");
		titre.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
		titre.setFill(Color.GOLD);
		scoreSubscene.getPane().getChildren().addAll(scoreLabel, titre);
		
		// on recupere les scores depuis la sauvegarde et on l'affiche sur notre section
		int j = 150;
		ArrayList<String> scores = save.getListScores();
		int tailleaffichage = 5;
		if(scores.size() < 5)  tailleaffichage = scores.size(); 
		for(int i = 0; i<tailleaffichage; i++) {
			String score = (i+1) + scores.get(i);
			Text t = new Text (50, j, score);
			j += 30;
			t.setFont(Font.font ("Verdana", FontWeight.BOLD, 20));
			t.setFill(Color.BEIGE);
			scoreSubscene.getPane().getChildren().add(t);
		}
		scoreSubscene.getPane().getChildren().add(createButtonToDeleteScores());
		scoreSubscene.getPane().getChildren().add(createButtonToRefreshScores());
	}
	
	// permet de faire un refresh des scores a l'affichage (utile apres avoir fini sa partie pour voir son score)
	private void refreshScoreSubscene() {
		scoreSubscene.getPane().getChildren().clear();
		fillScoreSubscene();
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

	// Boutons de la sections SCORES
	private SpaceRunnerButton createButtonToDeleteScores() {
		SpaceRunnerButton deleteButton = new SpaceRunnerButton("DELETE SCORES");
		deleteButton.setLayoutX(350);
		deleteButton.setLayoutY(300);
		
		deleteButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				save.clearSave();
				int nbNode = scoreSubscene.getPane().getChildren().size();
				for (int i = 2; i<nbNode-2 ; i++) {
					scoreSubscene.getPane().getChildren().remove(2);						
				}
			}
		});
		return deleteButton;
	}
	
	private SpaceRunnerButton createButtonToRefreshScores() {
		SpaceRunnerButton refreshButton = new SpaceRunnerButton("REFRESH");
		refreshButton.setLayoutX(150);
		refreshButton.setLayoutY(300);
		
		refreshButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				refreshScoreSubscene();				
			}
		});
		return refreshButton;
	}
	
	
    private void createBackground() {
        Image backgroudImage = new Image("resources/space.png", 256, 256, false, false);
        BackgroundImage background = new BackgroundImage(backgroudImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        mainPane.setBackground(new Background(background));
    }

	private void createLogo() {
		ImageView logo = new ImageView("resources/spacerunner.png");
		logo.setFitWidth(500);
		logo.setFitHeight(230);
		logo.setLayoutX(280);
		logo.setLayoutY(20);

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
