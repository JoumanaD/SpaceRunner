package view;

import model.Ship;
import model.SmallInfoLabel;

import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GameViewManager {
    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;

    // la taille de la fenetre du jeu
    private static final int GAME_WIDTH = 600;
    private static final int GAME_HEIGHT = 800;

    private Stage menuStage;
    private ImageView ship;

    private boolean isLeftKeyPressed;
    private boolean isRightKeyPressed;
    private int angle;
    private AnimationTimer gameTimer;

    // background image pour le jeu 
    private GridPane gridPane1;
    private GridPane gridPane2; 
    private final static String BACKGROUND_IMAGE = "resources/space.png";
    private final static String DEMOGORGON_IMAGE = "resources/enemies/demogorgon.png";
    private final static String GOLD_STAR_IMAGE = "resources/shipChooser/star_gold.png";
    
    // les objets qui tombent
    private ImageView[] demogorgonList; 
    Random randomPositionGenerator; 
    
    // l'objet pour gagner un point
    private ImageView star; 
    
    // Partie Score
    private SmallInfoLabel pointsLabel; 
    private ImageView[] playerLifes; 
    private int playerLife; 
    private int points; 
    
    // attributs pour l'algorithme de collission 
    // considerant les images comme des cerles
    private final static int STAR_RAYON = 17;
    private final static int SHIP_RAYON = 50;
    private final static int DEMOGORGON_RAYON = 30;
    
    public GameViewManager() {
        initializeStage();
        createKeyListeners();
        randomPositionGenerator = new Random();
    }

	// -------------------------------------------------------------------------
    // Clavier config
    // -------------------------------------------------------------------------
    //
    private void createKeyListeners() {
        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.LEFT) {
                    isLeftKeyPressed = true;
                } else if (event.getCode() == KeyCode.RIGHT) {
                    isRightKeyPressed  = true;
                } 
            }
            
        });

        gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.LEFT) {
                    isLeftKeyPressed = false;
                } else if (event.getCode() == KeyCode.RIGHT) {
                    isRightKeyPressed = false;
                }
            }
            
        });
    }


    private void initializeStage(){
        gamePane = new AnchorPane();
        gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
        gameStage = new Stage();
        gameStage.setScene(gameScene);

    }

    //  -------------------------------------------------------------------------
    // CREATION JEU 
	//  -------------------------------------------------------------------------

   /**
    * 
    * @param menuStage
    * @param choosenShip
    */
    public void creatNewGame(Stage menuStage, Ship choosenShip) {
        this.menuStage = menuStage;
        this.menuStage.hide();
        createBackground();
        createShip(choosenShip);
        createGameElements(choosenShip);
        createGameElements();
        createGameLoop();
        gameStage.show();
    }
    

    // timer game
    private void createGameLoop() {
        gameTimer = new AnimationTimer() {

            @Override
            public void handle(long arg0) {
            	moveBackground();
            	moveGameElements();
            	checkPositionElement();
            	collision();
                moveShip();
                
            }
            
            
        };

        gameTimer.start();
    }
    
    private void createBackground() {
        gridPane1 = new GridPane(); 
        gridPane2 = new GridPane(); 

        for(int i = 0; i < 12; i++) {
            ImageView backgroundImage1 = new ImageView(BACKGROUND_IMAGE);
            ImageView backgroundImage2 = new ImageView(BACKGROUND_IMAGE);
            GridPane.setConstraints(backgroundImage1, i%3, i/3);
            GridPane.setConstraints(backgroundImage2, i%3, i/3);
            gridPane1.getChildren().add(backgroundImage1);
            gridPane2.getChildren().add(backgroundImage2);
        }

        gridPane2.setLayoutY(-1024);

        gamePane.getChildren().addAll(gridPane1, gridPane2);
    }
    
    private void moveBackground() {
    	gridPane1.setLayoutX(gridPane1.getLayoutX() + 1.5);
    	gridPane2.setLayoutY(gridPane2.getLayoutY() + 1.5);
    	
    	if(gridPane1.getLayoutY() >= 2) {
    		gridPane1.setLayoutY(-1024);
    	}
    	if(gridPane2.getLayoutY() >= 2) {
    		gridPane2.setLayoutY(-1024);
    	}
    }

	//  -------------------------------------------------------------------------
    // ELEMENTS DU JEU
	//  -------------------------------------------------------------------------
    /**
     * 
     * @param choosenShip
     */
    private void createGameElements(Ship choosenShip) {
    	playerLife = 2; 
    	star = new ImageView(GOLD_STAR_IMAGE);
    	setNewElementPosition(star);
    	gamePane.getChildren().add(star);
    	pointsLabel = new SmallInfoLabel("POINTS : 00");
    	pointsLabel.setLayoutX(460);
    	pointsLabel.setLayoutY(20);
    	gamePane.getChildren().add(pointsLabel);
    	playerLifes = new ImageView[3];
    	
    	for (int i = 0; i < playerLifes.length; i++) {
    		playerLifes[i] = new ImageView(choosenShip.getUrlLife());
    		playerLifes[i].setFitWidth(40);
    		playerLifes[i].setFitHeight(40);
    		playerLifes[i].setLayoutX(455 + (i * 50));
    		playerLifes[i].setLayoutY(80);
    		gamePane.getChildren().add(playerLifes[i]);
    	}
	}


	private void createGameElements() {
    	demogorgonList = new ImageView[10];
    	for(int i = 0; i < demogorgonList.length; i++) {
    		demogorgonList[i] = new ImageView(DEMOGORGON_IMAGE);
    		demogorgonList[i].setFitHeight(80);
            demogorgonList[i].setFitWidth(80);
    		setNewElementPosition(demogorgonList[i]);
    		gamePane.getChildren().add(demogorgonList[i]);
    	}
    	
    }
    
	// mouvement des objets dans le jeu 
    private void moveGameElements() {
    	// mouvement de l'étoile
    	star.setLayoutY(star.getLayoutY() + 5);
    	
    	// le mouvement de demogorgon 
    	for(int i = 0; i < demogorgonList.length; i++) {
    		demogorgonList[i].setLayoutY(demogorgonList[i].getLayoutY()+4);
    		demogorgonList[i].setRotate(demogorgonList[i].getRotate()+2);
    	}
    }
    
    // changer la position des enemies 
    private void checkPositionElement() {
    	if(star.getLayoutY() > 1200) {
    		setNewElementPosition(star);
    	}
    	for(int i = 0; i < demogorgonList.length; i++) {
    		if(demogorgonList[i].getLayoutY() > 900) {
    			setNewElementPosition(demogorgonList[i]);
    		}
    	}
    }
    private void setNewElementPosition(ImageView image) {
    	image.setLayoutX(randomPositionGenerator.nextInt(370));
		image.setLayoutY(-randomPositionGenerator.nextInt(3200)+100);
    }
    
    //  -------------------------------------------------------------------------
    // CREATION VAISSEAU
	//  -------------------------------------------------------------------------
    //
    private void createShip(Ship choosenShip) {
        ship = new ImageView(choosenShip.getURL());
        ship.setFitHeight(120);
        ship.setFitWidth(110);
        ship.setLayoutX((GAME_WIDTH/2) - 40);
        ship.setLayoutY(GAME_HEIGHT - 120); 
        gamePane.getChildren().add(ship);
    }

    // rotation de vaisseau
    private void moveShip() {
        if (isLeftKeyPressed && ! isRightKeyPressed) {

            if(angle > -30) {
				angle -= 5;
			}
			ship.setRotate(angle);
			if(ship.getLayoutX() > -20) {
				ship.setLayoutX(ship.getLayoutX() - 5);
			}

        } if(!isLeftKeyPressed && isRightKeyPressed) {

            if(angle < 30) {
				angle += 5;
			}
			ship.setRotate(angle);
			if(ship.getLayoutX() < 522) {
				ship.setLayoutX(ship.getLayoutX() + 5);
			}

        } if(!isLeftKeyPressed && !isRightKeyPressed) {

            if(angle < 0) {
				angle += 5;
			} else if (angle > 0) {
				angle -= 5;
			}
			ship.setRotate(angle);

        } if(isLeftKeyPressed && isRightKeyPressed) {

            if(angle < 0) {
				angle += 5;
			} else if (angle > 0) {
				angle -= 5;
			}
			ship.setRotate(angle);

        }
    }
    
    
	//  -------------------------------------------------------------------------
    // COLLISION
	//  -------------------------------------------------------------------------
    private void collision() {
    	
    	if ( SHIP_RAYON + STAR_RAYON > calculateDistance(ship.getLayoutX() + 49, star.getLayoutX() + 15, 
    			ship.getLayoutY() + 37, star.getLayoutY() + 15)) {
    		setNewElementPosition(star);
    		
    		points++;
    		String textToSet = "POINTS : ";
    		if(points < 10) {
    			textToSet = textToSet + "0";
    		}
    		pointsLabel.setText(textToSet + points);
    	}
    	
    	for (int i = 0; i < demogorgonList.length; i++) {
    		if(DEMOGORGON_RAYON + SHIP_RAYON > calculateDistance(ship.getLayoutX() + 49, demogorgonList[i].getLayoutX() + 20, 
    				ship.getLayoutY() + 37, demogorgonList[i].getLayoutY() + 20)) {
    			removeLife();
    			setNewElementPosition(demogorgonList[i]);
    		}
    	}
    }
    
    private void removeLife() {
    	gamePane.getChildren().remove(playerLifes[playerLife]);
    	playerLife--; 
    	if(playerLife < 0) {
    		gameStage.close();
    		gameTimer.stop();
    		menuStage.show();
    	}
    }
    // méthode pour calcule la distance entre deux points
    private double calculateDistance(double x1, double x2, double y1, double y2) {
    	return Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
    }
}
