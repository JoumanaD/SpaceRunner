package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

// La classe Sauvegarde se charge de sauvegarder les scores des joueurs dans un fichier scores.txt
public class Sauvegarde {
	private final String nameFile;
	
	public Sauvegarde() {
		this.nameFile = "src/resources/scores.txt";
	}
	
	/**
	 * Permet de recuperer depuis la sauvegarde la liste entiere des scores du jeu
	 * @return
	 */
	@SuppressWarnings("resource")
	public ArrayList<String> getListScores(){
		try {
			ArrayList<String> listscores = new ArrayList<>();
			File file = new File(nameFile);
			Scanner sc;
			sc = new Scanner(file);
			while(sc.hasNextLine()) {
				String score = ". PLAYER\t" + sc.nextLine();
				listscores.add(score);
			}
			Collections.reverse(listscores);
			return listscores;
		} catch (FileNotFoundException e) {
			System.err.println("Exception: "+ e.getMessage());
		}
		return null;
	}

	/**
	 * permet de sauvegarder un nouveau score dans le fichier de sauvegarde
	 * @param score le score du joueur a sauvegarder
	 */
    public void saveScoreOnFile(int score) {
    	try {
    		FileWriter fw = new FileWriter(this.nameFile,true);
    		fw.write(score + "\n");
    		fw.close();
    	} catch(IOException ioe) {
    		System.err.println("IOException: "+ ioe.getMessage());
    	}
    }
    
    /**
     * permet de vider la sauvegarde, tous les scores sont effaces
     */
    public void clearSave() {
    	try {
    		FileWriter fw;
    		fw = new FileWriter(this.nameFile, false);
    		fw.close();

    	} catch (IOException ioe) {
    		System.err.println("IOException: "+ ioe.getMessage());
    	}
    }

}
