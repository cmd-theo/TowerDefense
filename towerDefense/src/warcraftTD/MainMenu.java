package warcraftTD;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainMenu {
	//Caract�ristiques sur la taille de la fen�tre du menu principal
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
	
	//Etat du menu : en cours d'utilisation (l'utilisateur va choisir son option) ou ferm� (correspond � un click sur la case "Quit")
	boolean end;
	//D�finit si l'utilisateur � cliqu� sur la case "Play"
	boolean enablePlay;
	//D�finit si l'utilisateur � cliqu� sur la case "Credits"
	boolean enableCredit;
	
	/**
	 * Initialisation du menu principal en fonction de la largeur, la hauteur et le nombre de cases données
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
	 * D�finit la position des cases offrant diff�rentes options � l'uitilisateur
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
	 * Dessine � l'aide de la biblioth�que StdDraw les options 
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
	 * V�rifies quand l'utilisateur clique dans la fen�tre:
	 *  - si il clique sur la case "Play" : l'emm�ne dans le menu du choix de la difficult� de jeu
	 *  - si il clique sur la case "Credits" : l'emm�ne sur la page des cr�dits
	 *  - si il clique sur la case "Quit" : quitte le menu principal, le jeu s'arr�te
	 * @param x la position sur l'axe des abscisses de la souris de l'utilisateur 
	 * @param y la position sur l'axe des ordonn�es de la souris de l'utilisateur
	 * (axes d�finis par la biblioth�que StdDraw)
	 */
	public void mouseClick(double x, double y) {
		double normalizedX = (int)(x / squareWidth) * squareWidth + squareWidth / 2;
		double normalizedY = (int)(y / squareHeight) * squareHeight + squareHeight / 2;
		Position p = new Position(normalizedX, normalizedY);
		Position e;
		Iterator<Position> i = play.iterator(); //it�ration afin de parcourir les positions correpondantes � la case "Play"
		while(i.hasNext()) {
			e=i.next();
			if(p.x==e.x&&p.y==e.y) enablePlay=true; //l'utilisateur � cliqu� sur la case "Play", le boolean associ� passe � true
		}
		Iterator<Position> n = credit.iterator(); //it�ration afin de parcourir les positions correpondantes � la case "Credits"
		while(n.hasNext()) {
			e=n.next();
			if(p.x==e.x&&p.y==e.y) enableCredit=true; //l'utilisateur � cliqu� sur la case "Credits", le boolean associ� passe � true
		}
		Iterator<Position> q = quit.iterator(); //it�ration afin de parcourir les positions correpondantes � la case "Quit"
		while(q.hasNext()) {
			e=q.next();
			if(p.x==e.x&&p.y==e.y) {
				System.out.println("Goodbye !");
				System.exit(0); //l'utilisatur souhiate quitter le jeu, alors le programme s'arr�te une fois qu'il a cliqu� sur "Quit" 
			}
		}
	}
	
	/**
	 * Boucle principale du menu. G�re la naviation parmis les diff�rentes options s'offrant � l'utilisateur.
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
			//Cr�ation d'un nouveau menu pour commencer � jouer
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
			//Cr�ation d'une nouvelle instance de Credits qui va afficher les cr�dits 
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

