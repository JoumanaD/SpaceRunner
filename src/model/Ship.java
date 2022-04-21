package model;

public enum Ship {
    WHITE("resources/shipChooser/spaceship-white.png", "resources/shipChooser/spaceship-white.png"),
    BLACK("resources/shipChooser/spaceship-black.png", "resources/shipChooser/spaceship-black.png"),
    BANDW("resources/shipChooser/spaceship-bw.png", "resources/shipChooser/spaceship-bw.png"),
    SORBONNE("resources/logo_Sorbonne.png", "resources/logo_Sorbonne.png");

    private String urlShip; 
    private String urlLife;
    
    private Ship(String urlShip, String urlLife) {
        this.urlShip = urlShip;
        this.urlLife = urlLife;
    }

    public String getURL() {
        return this.urlShip;
    }
    
    public String getUrlLife() {
    	return urlLife;
    }
} 
