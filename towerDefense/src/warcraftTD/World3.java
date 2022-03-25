package warcraftTD;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.LinkedList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class World3 {
	// Liste de l'ensemble des monstres d'une vague
	List<Monster> monsters = new ArrayList<Monster>();
	// Map associant (une tour associée à un projectile) contenant toutes les tours posées par le joueur sur le plateau
	Map<Tower,Projectile> tower = new HashMap<Tower,Projectile>();
	// Liste des cases (positions) du chemin suivi par les monstres
	List<Position> path = new ArrayList<Position>();
	// Liste des cases où les tours ne peuvent pas être posées (contient les cases du chemin et les cases des informations du jeu)
	List<Position> out = new ArrayList<Position>();

	// Position par laquelle les monstres vont venir
	Position spawn;
	// Position du chateau que doivent atteindre les monstres
	Position castle;
	// Position des informations du jeu (vie et or)
	Position infos;

	// Information sur la taille du plateau de jeu
	int width;
	int height;
	int nbSquareX;
	int nbSquareY;
	double squareWidth;
	double squareHeight;

	// Nombre de points de vie du joueur
	int life = 20;
	// Nombre de pièces d'or du joueur
	int gold=100;
	// Numéro de la vague de monstres en cours 
	int wave = 0;
	// Compteurs de fréquence de tir des tours
	int updateA=0;
	int updateB=0;
	int updateAup=0;
	int updateBup=0;

	// Commande sur laquelle le joueur appuie (sur le clavier)
	char key;

	// Condition pour terminer la partie
	boolean end = false;
	// Condition pour commencer la partie
	boolean start = false;
	// Condition pour gagner de l'argent à la fin d'une vague
	boolean gain = false;

	/**
	 * Initialisation du monde en fonction de la largeur, la hauteur et le nombre de cases donnÃ©es
	 * @param width
	 * @param height
	 * @param nbSquareX
	 * @param nbSquareY
	 * @param startSquareX
	 * @param startSquareY
	 */

	public World3(int width, int height, int nbSquareX, int nbSquareY, int startSquareX, int startSquareY) {
		this.width = width;
		this.height = height;
		this.nbSquareX = nbSquareX;
		this.nbSquareY = nbSquareY;
		squareWidth = (double) 1 / nbSquareX;
		squareHeight = (double) 1 / nbSquareY;
		spawn = new Position(startSquareX * squareWidth + squareWidth / 2, startSquareY * squareHeight + squareHeight / 2);
		castle = new Position(10 * squareWidth + squareWidth / 2, 0 * squareHeight + squareHeight / 1);
		infos = new Position(0.01, 0.09);
		StdDraw.setCanvasSize(width, height);
		StdDraw.enableDoubleBuffering();
		out.add(spawn);
	}

	/**
	 * Définit le décors du plateau de jeu.
	 */
	public void drawBackground() {	
		StdDraw.setPenColor(StdDraw.LIGHT_GREEN);
		for (int i = 0; i < nbSquareX; i++)
			for (int j = 0; j < nbSquareY; j++)
				StdDraw.picture(i * squareWidth + squareWidth / 2, j * squareHeight + squareHeight / 2, "images/world_h.png", squareWidth, squareHeight);
	}

	/**
	 * Initialise le chemin sur la position du point de départ des monstres. Cette fonction permet d'afficher une route qui sera différente du décors.
	 */
	public void setPath() {
		int i=0;
		int j=0;
		for(j=9; j>=3; j--) {
			path.add(new Position(1 * squareWidth + squareWidth / 2, j * squareWidth + squareWidth / 2));
		}
		for(i=1; i<2; i++) {
			path.add(new Position(i * squareWidth + squareWidth / 2, j * squareWidth + squareWidth / 2));
		}
		for(j=2; j>=1; j--) {
			path.add(new Position(i * squareWidth + squareWidth / 2, j * squareWidth + squareWidth / 2));
		}
		for(i=2; i<=4; i++) {
			path.add(new Position(i * squareWidth + squareWidth / 2, j * squareWidth + squareWidth / 2));
		}
		for(j=0; j<=3; j++) {
			path.add(new Position(i * squareWidth + squareWidth / 2, j * squareWidth + squareWidth / 2));         
		}
		for(i=5; i>=4; i--) {
			path.add(new Position(i * squareWidth + squareWidth / 2, j * squareWidth + squareWidth / 2));         
		}
		for(j=4; j<=9; j++) {
			path.add(new Position(i * squareWidth + squareWidth / 2, j * squareWidth + squareWidth / 2));         
		}
		for(i=3; i<=5; i++) {
			path.add(new Position(i * squareWidth + squareWidth / 2, j * squareWidth + squareWidth / 2));         
		}
		for(j=10; j>=6; j--) {
			path.add(new Position(i * squareWidth + squareWidth / 2, j * squareWidth + squareWidth / 2));         
		}
		for(i=6; i<=7; i++) {
			path.add(new Position(i * squareWidth + squareWidth / 2, j * squareWidth + squareWidth / 2));         
		}
		for(j=5; j<=9; j++) {
			path.add(new Position(i * squareWidth + squareWidth / 2, j * squareWidth + squareWidth / 2));         
		}
		for(i=8; i<=9; i++) {
			path.add(new Position(i * squareWidth + squareWidth / 2, j * squareWidth + squareWidth / 2));         
		}
		for(j=10; j>=1; j--) {
			path.add(new Position(i * squareWidth + squareWidth / 2, j * squareWidth + squareWidth / 2));         
		}
		// Ajout de la case située aux coordonnées normalisées (i,j) dans la liste des cases du chemin

	}

	/**
	 * Affiche le chemin sur le plateau de jeu
	 */

	public void drawPath() {
		Position k = new Position(spawn);
		StdDraw.setPenColor(StdDraw.YELLOW);
		StdDraw.picture(k.x, k.y,"images/path_h.PNG", squareWidth, squareHeight );
		for(int i=0; i<path.size(); i++) {
			Position p = new Position(path.get(i));
			out.add(p);
			StdDraw.picture(p.x, p.y,"images/path_h.PNG", squareWidth, squareHeight );
		}
	}

	public void drawSpawn() {
		StdDraw.picture(spawn.x,spawn.y, "images/spawn.png");

	}
	/**
	 * Affiche certaines informations sur l'écran telles que les points de vie du joueur ou son or
	 */

	public void drawInfos() {
		StdDraw.setPenColor(StdDraw.BLACK);
		Position p = new Position(infos);
		String lifes = Integer.toString(life);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.picture(p.x*9, p.y,"images/coeur.png");
		StdDraw.text(p.x*9, p.y/1.9, lifes);
		String golds = Integer.toString(gold);
		StdDraw.picture(p.x*3, p.y,"images/Gold.png");
		StdDraw.text(p.x*3, p.y/1.9, golds);
	}
	public void drawCastle() {
		StdDraw.picture(castle.x,castle.y, "images/castle.png", squareWidth, squareHeight);
	}

	/**
	 * Fonction qui récupère le positionnement de la souris et permet d'afficher une image de tour en temps réél
	 *	lorsque le joueur appuie sur une des touches permettant la construction d'une tour.
	 */
	public void drawMouse() {
		double normalizedX = (int)(StdDraw.mouseX() / squareWidth) * squareWidth + squareWidth / 2;
		double normalizedY = (int)(StdDraw.mouseY() / squareHeight) * squareHeight + squareHeight / 2;

		String image = null;
		switch (key) {
		case 'a' : 
			StdDraw.picture(normalizedX, normalizedY, "images/bow_tower.png");
			// TODO Ajouter une image pour reprÃ©senter une tour d'archers

			break;
		case 'b' :
			StdDraw.picture(normalizedX, normalizedY, "images/bomb_tower.png");
			// TODO Ajouter une image pour reprÃ©senter une tour Ã  canon
			break;
		case 'e':
			StdDraw.picture(normalizedX,normalizedY, "images/up.png");
			break;
		}
		if (image != null)
			StdDraw.picture(normalizedX, normalizedY, image, squareWidth, squareHeight);
	}


	/**
	 * Ajoute un monstre de base à la liste des monstres pour une vague donnée
	 */

	public void addBaseMonster() {
		Monster m = new BaseMonster(new Position(spawn));
		m.difficulty = 3; 
		monsters.add(m);
	}

	/**
	 * Ajoute un monstre volant à la liste des monstres pour une vague donnée
	 */

	public void addAirMonster() {
		Monster m = new AirMonster(new Position(spawn));
		m.difficulty = 3;
		monsters.add(m);
	}

	/**
	 * Ajoute un boss à la liste des monstres pour une vague donnée
	 */

	public void addBossMonster() {
		Monster m = new BossMonster(new Position(spawn));
		m.difficulty = 3;
		m.pv = 200;
		monsters.add(m);
	}


	/**
	 * Définit les vagues de monstres que va devoir affronter le joueur
	 */

	public void waves() {
		if(monsters.size()==0&&!end) {
			wave++;
			switch (wave) {
			case 1 :
				for (int i=0; i<5; i++) {
					addBaseMonster();
				}
				break;
			case 2 :
				for (int i=0; i<10; i++) {
					addBaseMonster();
				}
				break;
			case 3 : 
				for (int i=0; i<10; i++) {
					addAirMonster();
				}
				break;
			case 4 : 
				for (int i=0; i<1; i++) {
					addBossMonster();
				}
				break;
			}
		}
	}




	/**
	 * Pour chaque monstre de la liste de monstres de la vague, utilise la fonction update() qui appelle les fonctions run() et draw() de Monster.
	 * Modifie la position du monstre au cours du temps à l'aide du paramètre nextP.
	 */

	public void updateMonsters() {
		Iterator<Monster> i = monsters.iterator();
		Monster m;
		do {
			if(!i.hasNext()) { // Si plus de monstres dans l'intérator on sort de la boucle while gérant les éléments de cet interator
				break;
			}
			m = i.next();
			// Définition du chemin suivi par le monstre en fonction des cases du chemin présentes dans la liste "path" 
			// Les virages sont utilisés pour éviter les conditions qui pourraient être vérifiées à plusieurs endroits en même temps
			if(m.virage<1) m.nextP = path.get(7);
			if(m.p.y<path.get(7).y) {
				m.nextP = path.get(8);
				m.virage=1;
			}
			if(m.p.x>path.get(8).x&&m.virage<5) {
				m.nextP=path.get(10);
				m.virage=2;
			}
			if(m.p.y<(path.get(10).y+0.02)&&m.virage>1&&m.virage<5) {
				m.virage=3;
				m.nextP=path.get(13);
			}
			if(m.p.x>path.get(13).x&&m.virage<5) {
				m.virage=4;
				m.nextP=path.get(17);
			}
			if(m.p.y>path.get(17).y-0.02&&m.virage>3&&m.virage<7) {
				m.virage=5;
				m.nextP=path.get(19);
			}
			if(m.p.x<path.get(19).x&&m.virage>4&&m.virage<7) {
				m.virage=6;
				m.nextP=path.get(25);
			}
			if(m.p.y>path.get(25).y-0.015&&m.virage>5&&m.virage<9) {
				m.virage=7;
				m.nextP=path.get(28);
			}
			if(m.p.x>path.get(28).x&&m.virage>6&&m.virage<9) {
				m.virage=8;
				m.nextP=path.get(33);
			}
			if(m.p.y<path.get(33).y&&m.virage>7&&m.virage<10) {
				m.virage=9;
				m.nextP=path.get(35);
			}
			if(m.p.x>path.get(35).x&&m.virage>8&&m.virage<11) {
				m.virage=10;
				m.nextP=path.get(40);
			}
			if(m.p.y>path.get(40).y-0.015&&m.virage>9&&m.virage<12) {
				m.virage=11;
				m.nextP=path.get(42);
			}
			if(m.p.x>path.get(42).x) {
				m.virage=12;
				m.nextP=path.get(51);
			}
			if((m.p.x<castle.x+0.05&&m.p.x>castle.x-0.05)&&(m.p.y<castle.y+0.05&&m.p.y>castle.y-0.05)) {
				m.nextP = new Position(castle.x, castle.y);
				m.reached = true;
				this.life-=m.damage;
				i.remove();
				monsters.remove(m);
				// Si le monstre arrive jusqu'au château le joueur perd 1 point de vie et le monstre est supprimé de la liste "monsters"
			}
			m.update();
			// Gestion des points de vie du monstre si il est touché
			if(m.pv<=0) {
				i.remove();
				monsters.remove(m);
				gold+=5;
			}
			if(monsters.size()==0) gain=true; // Si les vague est terminée et que le joueur a toujours de la vie alors il va pouvoir gagner de l'argent
		}
		while (i.hasNext()&&m.checkpoint>20); // Gestion de la fréquence d'apparition des monstres
	}

	/**
	 * 
	 */

	public void updateTower() {
		for (Map.Entry<Tower, Projectile> entry : tower.entrySet()) {
			Tower x = entry.getKey();
			Projectile y = entry.getValue();
			x.update();
			if(x != null && y != null ) {
				y.update();  

				Iterator<Monster> i =monsters.iterator();
				Monster m;
				while(i.hasNext()) {
					m=i.next();
					if(monsters.size()!=0) {

						Iterator<Monster> it = monsters.iterator();
						Monster u;

						while (it.hasNext()) {
							u=it.next();
							if((m.id.equals("base")||m.id.equals("boss"))&&(x.type.equals("arrow")||(x.type.equals("bomb")))||m.id.equals("air")&&(x.type.equals("arrow"))) {
								if((u.p.x<=x.north.getEnd().x+0.08&&u.p.x>=x.north.getStart().x-0.08)&&(u.p.y<=x.north.getEnd().y&&u.p.y>=x.north.getStart().y)) {
									if((updateA==x.vitesse||updateAup==x.vitesse||updateB==x.vitesse||updateBup==x.vitesse)&&y.reached==false) {
										y.reached = true;
										y.nextP=x.north.getEnd();

										break;
									}
								}
								if((u.p.x<=x.south.getEnd().x+0.08&&u.p.x>=x.south.getStart().x-0.08)&&(u.p.y>=x.south.getEnd().y&&u.p.y<=x.south.getStart().y)) {
									if((updateA==x.vitesse||updateAup==x.vitesse||updateB==x.vitesse||updateBup==x.vitesse)&&y.reached==false) {
										y.reached = true;
										y.nextP=x.south.getEnd();

										break;
									}
								}
								if((u.p.x>=x.west.getEnd().x&&u.p.x<=x.west.getStart().x)&&(u.p.y<=x.west.getEnd().y+0.08&&u.p.y>=x.west.getStart().y-0.08)) {
									if((updateA==x.vitesse||updateAup==x.vitesse||updateB==x.vitesse||updateBup==x.vitesse)&&y.reached==false) {
										y.reached = true;
										y.nextP= x.west.getEnd();

										break;
									}
								}

								if((u.p.x<=x.east.getEnd().x&&u.p.x>=x.east.getStart().x)&&(u.p.y<=x.east.getEnd().y+0.08&&u.p.y>=x.west.getStart().y-0.08)) {
									if((updateA==x.vitesse||updateAup==x.vitesse||updateB==x.vitesse||updateBup==x.vitesse)&y.reached==false) {
										y.reached = true;
										y.nextP=x.east.getEnd();

										break;

									}
								}
							}				
						}
						if(x.upgrade&& x.type=="arrow") {
							if(y.checkpoint==18) {
								y.reached = true;
								Position h = new Position(x.p);
								Projectile xt = new ArrowProjectile(h);
								tower.replace(x, xt);

							}
						}
						if(!x.upgrade && x.type=="arrow") {
							if(y.checkpoint==9) {
								y.reached = true;
								Position h = new Position(x.p);
								Projectile xt = new ArrowProjectile(h);
								tower.replace(x, xt);
							}

						}
						if(x.upgrade&& x.type=="bomb") {
							if(y.checkpoint==20) {
								y.reached = true;
								Position h = new Position(x.p);
								Projectile xt = new BombProjectile(h);
								tower.replace(x, xt);

							}
						}
						if(!x.upgrade && x.type=="bomb") {
							if(y.checkpoint==9) {
								y.reached = true;
								Position h = new Position(x.p);
								Projectile xt = new BombProjectile(h);
								tower.replace(x, xt);
							}

						}
					}

					if(y.p.x<m.p.x+0.03&&y.p.x>m.p.x-0.04&&y.p.y<m.p.y+0.04&&y.p.y>m.p.y-0.04) {
						Position h = new Position(x.p);
						if(x.type=="arrow") {
							Projectile xt = new ArrowProjectile(h);
							tower.replace(x, xt);
						}
						if(x.type=="bomb") {
							Projectile xt = new BombProjectile(h);
							tower.replace(x, xt);
						}
						y.reached=true;
						m.shot=true;
						if(m.shot) {
							m.pv -= y.damage;
							m.shot=false;

						}
					}
					break;
				}  			
			}


		}
		if(monsters.size()==0) {
			for (Map.Entry<Tower, Projectile> entry : tower.entrySet()) {
				Tower x = entry.getKey();
				Projectile y = entry.getValue();
				x.update();

				Position h = new Position(x.p);
				if(x.type=="arrow") {
					Projectile xt = new ArrowProjectile(h);
					tower.replace(x, xt);
				}
				if(x.type=="bomb") {
					Projectile xt = new BombProjectile(h);
					tower.replace(x, xt);
				}

				y.update(); 
			}
		}

	}





	/**
	 * Met à jour toutes les informations du plateau de jeu ainsi que les déplacements des monstres et les attaques des tours.
	 * @return les points de vie restants du joueur
	 */
	public int update() {
		drawBackground();
		drawPath();
		drawCastle();
		drawSpawn();
		updateMonsters();

		updateTower();
		updateA++;
		updateAup++;
		updateB++;
		updateBup++;
		if(updateA>15) {
			updateA=0;
		}
		if(updateB>20) {
			updateB=0;
		}
		if(updateAup>10) {
			updateAup=0;
		}
		if(updateBup>15) {
			updateBup=0;
		}
		drawMouse();
		drawInfos();


		return life;
	}

	/**
	 * Récupère la touche appuyée par l'utilisateur et affiche les informations pour la touche sélectionnée
	 * @param key la touche utilisése par le joueur
	 */
	public void keyPress(char key) {
		key = Character.toLowerCase(key);
		this.key = key;
		switch (key) {
		case 'a':

			System.out.println("Arrow Tower selected (50g).");
			break;
		case 'b':
			System.out.println("Bomb Tower selected (60g).");
			break;
		case 'e':
			System.out.println("Evolution selected (40g).");
			break;
		case 's':

			System.out.println("Starting game!");
			start = true;
			break;
		case 'q':
			System.out.println("Exiting.");
			end=true;
			break;
		}
	}

	/**
	 * Vérifie lorsque l'utilisateur clique sur sa souris qu'il peut: 
	 * 		- Ajouter une tour Ã  la position indiquée par la souris.
	 * 		- Améliorer une tour existante.
	 * Puis l'ajouter à la liste des tours
	 * @param x
	 * @param y
	 */
	public void mouseClick(double x, double y) {
		double normalizedX = (int)(x / squareWidth) * squareWidth + squareWidth / 2;
		double normalizedY = (int)(y / squareHeight) * squareHeight + squareHeight / 2;
		Position p = new Position(normalizedX, normalizedY);
		Position xt = new Position(normalizedX, normalizedY);
		switch (key) {
		case 'a':
			Tower a = new ArrowTower(p);
			Projectile c = new ArrowProjectile(xt);
			if (gold >= 50) {
				Iterator<Position> i = out.iterator();
				Position b;
				boolean t = false;
				while (i.hasNext()) {
					b = i.next();
					if ((a.p.x == b.x && a.p.y == b.y)) {
						t = true;
					}
				}
				if (t == false) {
					gold -= 50;
					tower.put(a,c);
					out.add(a.p);
				}

			}
			break;
		case 'b':
			Tower k = new BombTower(p);
			Projectile d = new BombProjectile(xt);
			if (gold >= 60) {
				Iterator<Position> i = out.iterator();
				Position u;
				boolean t = false;
				while (i.hasNext()) {
					u = i.next();
					if ((k.p.x == u.x && k.p.y == u.y)) {
						t = true;
					}
				}
				if (t == false) {
					gold -= 60;
					tower.put(k,d);
					out.add(k.p);
				}

			}
			break;
		case 'e':
			for(Map.Entry<Tower, Projectile> entry : tower.entrySet()) {
				Tower t = entry.getKey();
				Projectile z = entry.getValue();
				if(gold>=40) {
					if(t.p.x==p.x &&t.p.y==p.y &&!t.upgrade) {
						t.upgrade=true;
						gold-=40;
						System.out.println("Upgrade !");
						if(t.type=="arrow") {
							t.vitesse=10;
							t.range=0.6;
							z.damage=4;
							t.north = new Paire(new Position(p), new Position(p.x, p.y+t.range));
							t.south = new Paire(new Position(p), new Position(p.x, p.y-t.range));
							t.east = new Paire(new Position(p), new Position(p.x+t.range, p.y));
							t.west = new Paire(new Position(p), new Position(p.x-t.range, p.y));


						}
						if(t.type=="bomb") {
							t.vitesse=15;
							t.range=0.4;
							z.damage=12;
							t.north = new Paire(new Position(p), new Position(p.x, p.y+t.range));
							t.south = new Paire(new Position(p), new Position(p.x, p.y-t.range));
							t.east = new Paire(new Position(p), new Position(p.x+t.range, p.y));
							t.west = new Paire(new Position(p), new Position(p.x-t.range, p.y));

						}
					}
				}
				else {
					System.out.println("Pas assez de gold !");
				}
			}
			break;
		}
	}

	/**
	 * Comme son nom l'indique, cette fonction permet d'afficher dans le terminal les différentes possibilités 
	 * offertes au joueur pour intéragir avec le clavier
	 */
	public void printCommands() {
		System.out.println("Press A to select Arrow Tower (cost 50g).");
		System.out.println("Press B to select Cannon Tower (cost 60g).");
		System.out.println("Press E to update a tower (cost 40g).");
		System.out.println("Click on the grass to build it.");
		System.out.println("Press S to start.");
		System.out.println("Press Q to quit.");
	}



	/**
	 * Récupère la touche entrée au clavier ainsi que la position de la souris et met à jour le plateau en fonction de ces interractions
	 */
	public void run() {
		double startTime = System.currentTimeMillis();
		double endTime = startTime + 25000; // Environ 20 secondes de delai
		setPath();
		printCommands();
		System.out.println("Placez vos premières tours avant l'arrivée des monstres !");
		while(!end) {
			if(start&&System.currentTimeMillis()>=endTime) { // Condition permettant de laisser quelques secondes de délai entre chaque vague
				waves();
				startTime = System.currentTimeMillis();
				endTime = startTime + 25000;
				System.out.println("La vague " + wave + " est en approche !");
			}
			// Condition en lien avec le boolean "gain" pour gagner de l'argent après une vague
			if(wave!=0&&gain) {
				gold+=100;
				System.out.println("Vous avez surmonté la vague " + wave + " ! Preparez vos défenses pour la prochaine avec ces 100 pièces supplémentaires !");
				gain=false;
			}
			StdDraw.clear();
			if (StdDraw.hasNextKeyTyped()) {
				keyPress(StdDraw.nextKeyTyped());
			}

			if (StdDraw.isMousePressed()) {
				mouseClick(StdDraw.mouseX(), StdDraw.mouseY());
				StdDraw.pause(50);
			}

			update();
			// Conditions finales de victoire ou de défaite
			if(life<=0) {
				System.out.println("Vous avez perdu !");
				end=true;
			}
			if(wave==4&&monsters.size()==0&&life>0) {
				System.out.println("C'est gagné !");
				end=true;
			}
			StdDraw.show();
			StdDraw.pause(20);
		}
	}
}
