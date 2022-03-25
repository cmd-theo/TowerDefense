package warcraftTD;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainMenu {
	//Caractéristiques sur la taille de la fenêtre du menu principal
	int width;
	int height;
	int nbSquareX;
	int nbSquareY;
	double squareWidth;
	double squareHeight;
	
	//Listes des positions des cases du menu principal
	List<Position> play;
	List<Position> quit;
	List<Position> credit;
	
	//Etat du menu : en cours d'utilisation (l'utilisateur va choisir son option) ou fermé (correspond à un click sur la case "Quit")
	boolean end;
	//Définit si l'utilisateur à cliqué sur la case "Play"
	boolean enablePlay;
	//Définit si l'utilisateur à cliqué sur la case "Credits"
	boolean enableCredit;
	
	/**
	 * Initialisation du menu principal en fonction de la largeur, la hauteur et le nombre de cases donnÃ©es
	 * @param width
	 * @param height
	 * @param nbSquareX
	 * @param nbSquareY
	 * @param startSquareX
	 * @param startSquareY
	 */
	
	public MainMenu(int width, int height, int nbSquareX, int nbSquareY, int startSquareX, int startSquareY) {
		this.width = width;
		this.height = height;
		this.nbSquareX = nbSquareX;
		this.nbSquareY = nbSquareY;
		squareWidth = (double) 1 / nbSquareX;
		squareHeight = (double) 1 / nbSquareY;
		play = new ArrayList<Position>();
		quit = new ArrayList<Position>();
		credit = new ArrayList<Position>();
		this.end = false;
		StdDraw.setCanvasSize(width, height);
		StdDraw.enableDoubleBuffering();
	}
	
	public void drawBackground() {	
		StdDraw.picture(0.5, 0.5, "images/menu_background.png");
	}
	
	/**
	 * Définit la position des cases offrant différentes options à l'uitilisateur
	 */
	
	public void setOptions() {
		play.add(new Position(5 * squareWidth + squareWidth / 2, 8 * squareWidth + squareWidth / 2));
		play.add(new Position(6 * squareWidth + squareWidth / 2, 8 * squareWidth + squareWidth / 2));
		play.add(new Position(4 * squareWidth + squareWidth / 2, 8 * squareWidth + squareWidth / 2));
		credit.add(new Position(5 * squareWidth + squareWidth / 2, 5 * squareWidth + squareWidth / 2));
		credit.add(new Position(6 * squareWidth + squareWidth / 2, 5 * squareWidth + squareWidth / 2));
		credit.add(new Position(4 * squareWidth + squareWidth / 2, 5 * squareWidth + squareWidth / 2));
		quit.add(new Position(5 * squareWidth + squareWidth / 2, 2 * squareWidth + squareWidth / 2));
		quit.add(new Position(6 * squareWidth + squareWidth / 2, 2 * squareWidth + squareWidth / 2));
		quit.add(new Position(4 * squareWidth + squareWidth / 2, 2 * squareWidth + squareWidth / 2));
	}
	
	/**
	 * Dessine à l'aide de la bibliothèque StdDraw les options 
	 */
	
	public void drawOptions() {
		StdDraw.setPenColor(StdDraw.BLACK);
		for(int i=0; i<play.size(); i++) {
			StdDraw.picture(play.get(i).x, play.get(i).y,"images/capture.jpg", squareWidth, squareHeight );
		}
		StdDraw.picture(play.get(0).x, play.get(0).y, "images/play.png");
		for(int i=0; i<credit.size(); i++) {
			StdDraw.picture(credit.get(i).x, credit.get(i).y,"images/capture.jpg", squareWidth, squareHeight );
		}
		StdDraw.picture(credit.get(0).x, credit.get(0).y, "images/credits.png");
		for(int i=0; i<quit.size(); i++) {
			StdDraw.picture(quit.get(i).x, quit.get(i).y,"images/capture.jpg", squareWidth, squareHeight );
		}
		StdDraw.picture(quit.get(0).x, quit.get(0).y, "images/quitter.png");
	}

	/**
	 * Vérifies quand l'utilisateur clique dans la fenêtre:
	 *  - si il clique sur la case "Play" : l'emmène dans le menu du choix de la difficulté de jeu
	 *  - si il clique sur la case "Credits" : l'emmène sur la page des crédits
	 *  - si il clique sur la case "Quit" : quitte le menu principal, le jeu s'arrête
	 * @param x la position sur l'axe des abscisses de la souris de l'utilisateur 
	 * @param y la position sur l'axe des ordonnées de la souris de l'utilisateur
	 * (axes définis par la bibliothèque StdDraw)
	 */
	public void mouseClick(double x, double y) {
		double normalizedX = (int)(x / squareWidth) * squareWidth + squareWidth / 2;
		double normalizedY = (int)(y / squareHeight) * squareHeight + squareHeight / 2;
		Position p = new Position(normalizedX, normalizedY);
		Position e;
		Iterator<Position> i = play.iterator(); //itération afin de parcourir les positions correpondantes à la case "Play"
		while(i.hasNext()) {
			e=i.next();
			if(p.x==e.x&&p.y==e.y) enablePlay=true; //l'utilisateur à cliqué sur la case "Play", le boolean associé passe à true
		}
		Iterator<Position> n = credit.iterator(); //itération afin de parcourir les positions correpondantes à la case "Credits"
		while(n.hasNext()) {
			e=n.next();
			if(p.x==e.x&&p.y==e.y) enableCredit=true; //l'utilisateur à cliqué sur la case "Credits", le boolean associé passe à true
		}
		Iterator<Position> q = quit.iterator(); //itération afin de parcourir les positions correpondantes à la case "Quit"
		while(q.hasNext()) {
			e=q.next();
			if(p.x==e.x&&p.y==e.y) {
				System.out.println("Goodbye !");
				System.exit(0); //l'utilisatur souhiate quitter le jeu, alors le programme s'arrête une fois qu'il a cliqué sur "Quit" 
			}
		}
	}
	
	/**
	 * Boucle principale du menu. Gère la naviation parmis les différentes options s'offrant à l'utilisateur.
	 */
	
	public void run() {
		drawBackground();
		setOptions();
		drawOptions();
		StdDraw.picture(0.5, 0.90, "images/head.png");
		while(!end) {
			if (StdDraw.isMousePressed()) {
				mouseClick(StdDraw.mouseX(), StdDraw.mouseY());
				StdDraw.pause(250);
			}
			//Création d'un nouveau menu pour commencer à jouer
			if(enablePlay) { 
				int width = 1000;
				int height = 600;
				int nbSquareX = 11;
				int nbSquareY = 11;
				int startX = 1;
				int startY = 10;		
				Menu m = new Menu(width, height, nbSquareX, nbSquareY, startX, startY);
				m.run();
			}
			//Création d'une nouvelle instance de Credits qui va afficher les crédits 
			if(enableCredit) {
				int width = 1000;
				int height = 600;
				int nbSquareX = 11;
				int nbSquareY = 11;
				int startX = 1;
				int startY = 10;
				Credits c = new Credits(width, height, nbSquareX, nbSquareY, startX, startY);
				c.run();
			}
			StdDraw.show();
		}
	}
	
}

