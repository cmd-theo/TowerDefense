package warcraftTD;

public class BossMonster extends Monster {

	public BossMonster(Position p) {
		super(p);
		damage = 10;
		speed = 0.005;
		pv = 100;
		id = "boss";
		difficulty = 0;
	}
	// R�cup�ration des attributs de base du monstre et initalisation des caract�ristiques propres au monstre volant
	
	/**
	 * Affiche le boss qui va se d�placer sur la carte
	 */
	
	public void draw() {
		switch (difficulty) {
		case 1 : 
			StdDraw.picture(p.x,p.y,"images/monster3.gif"); //image du boss pour le niveau "easy"
			break;
		case 2 : 
			StdDraw.picture(p.x,p.y,"images/boss_normal.gif"); //image du boss pour le niveau "easy"
			break;
		case 3 :
			StdDraw.picture(p.x,p.y,"images/boss_h.gif"); //image du boss pour le niveau "easy"
			break;
		}
		
	}
}
