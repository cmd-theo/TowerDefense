package warcraftTD;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Credits {

	int width;
	int height;
	int nbSquareX;
	int nbSquareY;
	double squareWidth;
	double squareHeight;
	
	List<Position> back; //liste contenant les positions des cases de l'option "back"
	
	boolean end;
	boolean enableBack; //boolean passant à true si l'utilisateur clique sur "back"
	
	
	/**
	 * Initialisation de la page des crédits en fonction de la largeur, la hauteur et le nombre de cases donnÃ©es
	 * @param width
	 * @param height
	 * @param nbSquareX
	 * @param nbSquareY
	 * @param startSquareX
	 * @param startSquareY
	 */
	
	public Credits(int width, int height, int nbSquareX, int nbSquareY, int startSquareX, int startSquareY) {
		this.width = width;
		this.height = height;
		this.nbSquareX = nbSquareX;
		this.nbSquareY = nbSquareY;
		squareWidth = (double) 1 / nbSquareX;
		squareHeight = (double) 1 / nbSquareY;
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
	
	public void setOptions() {
		back.add(new Position(5 * squareWidth + squareWidth / 2, 1 * squareWidth + squareWidth / 2));
		back.add(new Position(6 * squareWidth + squareWidth / 2, 1 * squareWidth + squareWidth / 2));
		back.add(new Position(4 * squareWidth + squareWidth / 2, 1 * squareWidth + squareWidth / 2));
	}
	
	/*
	 * Permet à l'utilisateur de revenir au menu principal et d'afficher les crédits
	 */
	
	public void drawOptions() {
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(0.5, 0.5, "Ce Tower Defense a été réalisé par Theo Boulaire et Thomas Boué. Amusez-vous bien !");
		for(int i=0; i<back.size(); i++) {
			StdDraw.picture(back.get(i).x, back.get(i).y,"images/capture.jpg", squareWidth, squareHeight );
		}
		StdDraw.picture(back.get(0).x, back.get(0).y, "images/back.png");
	}

	public void mouseClick(double x, double y) {
		double normalizedX = (int)(x / squareWidth) * squareWidth + squareWidth / 2;
		double normalizedY = (int)(y / squareHeight) * squareHeight + squareHeight / 2;
		Position p = new Position(normalizedX, normalizedY);
		Position e;
		Iterator<Position> q = back.iterator();
		while(q.hasNext()) {
			e=q.next();
			if(p.x==e.x&&p.y==e.y) enableBack=true;
		}


	}
	
	public void run() {
		drawBackground();
		setOptions();
		drawOptions();
		StdDraw.picture(0.5, 0.90, "images/head.png");
		while(!end) {
			if (StdDraw.isMousePressed()) {
				mouseClick(StdDraw.mouseX(), StdDraw.mouseY());
				StdDraw.pause(100);
			}
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
