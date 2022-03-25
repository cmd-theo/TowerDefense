package warcraftTD;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Menu {
	//Caractéristiques sur la taille de la fenêtre du menu
	int width;
	int height;
	int nbSquareX;
	int nbSquareY;
	double squareWidth;
	double squareHeight;
	
	//Listes des positions des cases du menu de jeu
	List<Position> easy;
	List<Position> normal;
	List<Position> hardcore;
	List<Position> back;
	
	//Etat du menu : en cours d'utilisation (l'utilisateur va choisir son option) ou fermé (correspond à un click sur la case "Back")
	boolean end;
	//Définit si l'utilisateur à cliqué sur la case "Easy"
	boolean enableW1;
	//Définit si l'utilisateur à cliqué sur la case "Normal"
	boolean enableW2;
	//Définit si l'utilisateur à cliqué sur la case "Hard"
	boolean enableW3;
	//Définit si l'utilisateur à cliqué sur la case "Back"
	boolean enableBack;
	
	/**
	 * Initialisation du menu secondaire en fonction de la largeur, la hauteur et le nombre de cases donnÃ©es
	 * @param width
	 * @param height
	 * @param nbSquareX
	 * @param nbSquareY
	 * @param startSquareX
	 * @param startSquareY
	 */
	
	public Menu(int width, int height, int nbSquareX, int nbSquareY, int startSquareX, int startSquareY) {
		this.width = width;
		this.height = height;
		this.nbSquareX = nbSquareX;
		this.nbSquareY = nbSquareY;
		squareWidth = (double) 1 / nbSquareX;
		squareHeight = (double) 1 / nbSquareY;
		easy = new ArrayList<Position>();
		normal = new ArrayList<Position>();
		hardcore = new ArrayList<Position>();
		back = new ArrayList<Position>();
		this.end = false;
		StdDraw.setCanvasSize(width, height);
		StdDraw.enableDoubleBuffering();
	}
	
	public void drawBackground() {	
		StdDraw.setPenColor(StdDraw.WHITE);
		for (int i = 0; i < nbSquareX; i++)
			for (int j = 0; j < nbSquareY; j++)
				StdDraw.picture(0.5, 0.5, "images/menu_background.png");
	}
	
	/**
	 * Définit la position des cases offrant différentes options à l'uitilisateur
	 */
	
	public void setOptions() {
		easy.add(new Position(5 * squareWidth + squareWidth / 2, 7 * squareWidth + squareWidth / 2));
		easy.add(new Position(6 * squareWidth + squareWidth / 2, 7 * squareWidth + squareWidth / 2));
		easy.add(new Position(4 * squareWidth + squareWidth / 2, 7 * squareWidth + squareWidth / 2));
		normal.add(new Position(5 * squareWidth + squareWidth / 2, 5 * squareWidth + squareWidth / 2));
		normal.add(new Position(6 * squareWidth + squareWidth / 2, 5 * squareWidth + squareWidth / 2));
		normal.add(new Position(4 * squareWidth + squareWidth / 2, 5 * squareWidth + squareWidth / 2));
		hardcore.add(new Position(5 * squareWidth + squareWidth / 2, 3 * squareWidth + squareWidth / 2));
		hardcore.add(new Position(6 * squareWidth + squareWidth / 2, 3 * squareWidth + squareWidth / 2));
		hardcore.add(new Position(4 * squareWidth + squareWidth / 2, 3 * squareWidth + squareWidth / 2));
		back.add(new Position(5 * squareWidth + squareWidth / 2, 1 * squareWidth + squareWidth / 2));
		back.add(new Position(6 * squareWidth + squareWidth / 2, 1 * squareWidth + squareWidth / 2));
		back.add(new Position(4 * squareWidth + squareWidth / 2, 1 * squareWidth + squareWidth / 2));
	}
	
	/**
	 * Dessine à l'aide de la bibliothèque StdDraw les options 
	 */
	
	public void drawOptions() {
		StdDraw.setPenColor(StdDraw.BLACK);
		for(int i=0; i<easy.size(); i++) {
			StdDraw.picture(easy.get(i).x, easy.get(i).y,"images/capture.jpg", squareWidth, squareHeight );
		}
		StdDraw.picture(easy.get(0).x, easy.get(0).y, "images/easy.png");
		for(int i=0; i<normal.size(); i++) {
			StdDraw.picture(normal.get(i).x, normal.get(i).y,"images/capture.jpg", squareWidth, squareHeight );
		}
		StdDraw.picture(normal.get(0).x, normal.get(0).y, "images/normal.png");
		for(int i=0; i<hardcore.size(); i++) {
			StdDraw.picture(hardcore.get(i).x, hardcore.get(i).y,"images/capture.jpg", squareWidth, squareHeight );
		}
		StdDraw.picture(hardcore.get(0).x, hardcore.get(0).y, "images/hard.png");
		for(int i=0; i<back.size(); i++) {
			StdDraw.picture(back.get(i).x, back.get(i).y,"images/capture.jpg", squareWidth, squareHeight );
		}
		StdDraw.picture(back.get(0).x, back.get(0).y, "images/back.png");
	}

	/**
	 * Vérifies quand l'utilisateur clique dans la fenêtre:
	 *  - si il clique sur la case "Easy" : l'emmène dans le jeu correpondant à la difficulté facile
	 *  - si il clique sur la case "Normal" : l'emmène dans le jeu correpondant à la difficulté normale
	 *  - si il clique sur la case "Hard" : l'emmène dans le jeu correpondant à la difficulté difficile
	 *  - si il clique sur la case "Back" : quitte le menu de jeu, reveint au menu principal
	 * @param x la position sur l'axe des abscisses de la souris de l'utilisateur 
	 * @param y la position sur l'axe des ordonnées de la souris de l'utilisateur
	 * (axes définis par la bibliothèque StdDraw)
	 */
	
	public void mouseClick(double x, double y) {
		double normalizedX = (int)(x / squareWidth) * squareWidth + squareWidth / 2;
		double normalizedY = (int)(y / squareHeight) * squareHeight + squareHeight / 2;
		Position p = new Position(normalizedX, normalizedY);
		Position e;
		Iterator<Position> i = easy.iterator(); //itération afin de parcourir les positions correpondantes à la case "Easy"
		while(i.hasNext()) {
			e=i.next();
			if(p.x==e.x&&p.y==e.y) enableW1=true; //l'utilisateur à cliqué sur la case "Easy", le boolean associé passe à true
		}
		Iterator<Position> n = normal.iterator(); //itération afin de parcourir les positions correpondantes à la case "Normal"
		while(n.hasNext()) {
			e=n.next();
			if(p.x==e.x&&p.y==e.y) enableW2=true; //l'utilisateur à cliqué sur la case "Normal", le boolean associé passe à true 
		}
		Iterator<Position> h = hardcore.iterator(); //itération afin de parcourir les positions correpondantes à la case "Hard"
		while(h.hasNext()) {
			e=h.next();
			if(p.x==e.x&&p.y==e.y) enableW3=true; //l'utilisateur à cliqué sur la case "Hard", le boolean associé passe à true
		}
		Iterator<Position> q = back.iterator(); //itération afin de parcourir les positions correpondantes à la case "Back"
		while(q.hasNext()) {
			e=q.next();
			if(p.x==e.x&&p.y==e.y) enableBack=true; //l'utilisateur à cliqué sur la case "Back", le boolean associé passe à true
		}


	}
	
	/**
	 * Boucle principale du menu de jeu. Gère la naviation parmis les différentes options s'offrant à l'utilisateur.
	 */
	
	public void run() {
		System.out.println("Choisir la difficulté ou revenir au menu principal.");
		drawBackground();
		setOptions();
		drawOptions();
		StdDraw.picture(0.5, 0.90, "images/head.png");
		while(!end) {
			if (StdDraw.isMousePressed()) {
				mouseClick(StdDraw.mouseX(), StdDraw.mouseY());
				StdDraw.pause(250);
			}
			//Création d'un plateau de jeu correspondant à la difficulté choisie, connue par la valeur des booleans associés à ces difficultés
			if(enableW1) {
				int width = 1000;
				int height = 600;
				int nbSquareX = 11;
				int nbSquareY = 11;
				int startX = 1;
				int startY = 10;		
				World w = new World(width, height, nbSquareX, nbSquareY, startX, startY);
				w.run();
				if(w.end) { // Si la boucle de jeu est terminée, crée un nouveau menu de jeu
					Menu m = new Menu(width, height, nbSquareX, nbSquareY, startX, startY);
					m.run();
				}
			}
			else if(enableW2) {
				int width = 1000;
				int height = 600;
				int nbSquareX = 11;
				int nbSquareY = 11;
				int startX = 1;
				int startY = 10;		
				World2 w2 = new World2(width, height, nbSquareX, nbSquareY, startX, startY);
				w2.run();
				if(w2.end) { // Si la boucle de jeu est terminée, crée un nouveau menu de jeu
					Menu m = new Menu(width, height, nbSquareX, nbSquareY, startX, startY);
					m.run();
				}
			}
			else if(enableW3) {
				int width = 1000;
				int height = 600;
				int nbSquareX = 11;
				int nbSquareY = 11;
				int startX = 1;
				int startY = 10;		
				World3 w3 = new World3(width, height, nbSquareX, nbSquareY, startX, startY);
				w3.run();
				if(w3.end) { // Si la boucle de jeu est terminée, crée un nouveau menu de jeu
					Menu m = new Menu(width, height, nbSquareX, nbSquareY, startX, startY);
					m.run();
				}
			}
			// Si l'utilisateur clique sur "Back", on revient sur un menu principal en en créant un nouveau
			if(enableBack) {
				int width = 1000;
				int height = 600;
				int nbSquareX = 11;
				int nbSquareY = 11;
				int startX = 1;
				int startY = 10;
				MainMenu main = new MainMenu(width, height, nbSquareX, nbSquareY, startX, startY);
				main.run();
			}
			StdDraw.show();
		}
	}
	
}
