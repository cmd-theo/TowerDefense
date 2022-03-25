package warcraftTD;

public class AirMonster extends Monster {
	
	public AirMonster(Position p) {
		super(p); 
		damage = 1;
		speed = 0.020;
		pv = 3;
		id = "air";
		difficulty = 0;
		
		// R�cup�ration des attributs de base du monstre et initalisation des caract�ristiques propres au monstre volant
	}
	
	/**
	 * Affiche un monstre volant qui va se d�placer sur la carte
	 */
	
	public void draw() {
		switch (difficulty) {
		case 1 : 
			StdDraw.picture(p.x,p.y,"images/monster2.gif"); //image du monstre volant pour le niveau "easy"
			break;
		case 2 : 
			StdDraw.picture(p.x,p.y,"images/flying_n.gif"); //image du monstre volant pour le niveau "normal"
			break;
		case 3 :
			StdDraw.picture(p.x,p.y,"images/flying_h.gif"); //image du monstre volant pour le niveau "hard"
			break;
		}
		
	}
}
