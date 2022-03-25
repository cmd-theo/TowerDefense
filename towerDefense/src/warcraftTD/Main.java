package warcraftTD;

public class Main {

	public static void main(String[] args) {
		int width = 1000;
		int height = 600;
		int nbSquareX = 11;
		int nbSquareY = 11;
		int startX = 1;
		int startY = 10;	
		
		/**Initialisation du menu principal du jeu 
		*Trois possibilités : jouer, accéder aux crédits, quitter le jeu
		*/
		
		MainMenu main = new MainMenu(width, height, nbSquareX, nbSquareY, startX, startY);
		main.run();
		
		
		
	}

	
	
}

