package warcraftTD;

public class BaseMonster extends Monster {
	
	public BaseMonster(Position p) {
		super(p);
		damage = 1;
		speed = 0.010;
		pv = 5;
		id = "base";
		difficulty = 0;
	}
	
	// Récupération des attributs de base du monstre et initalisation des caractéristiques propres au monstre de base
	
	/**
	 * Affiche un monstre de base qui va se déplacer sur la carte
	 */
	public void draw() {
		switch (difficulty) {
		case 1 : 
			StdDraw.picture(p.x,p.y,"images/monster1.gif"); //image du monstre de base pour le niveau "easy"
			break;
		case 2 : 
			StdDraw.picture(p.x,p.y,"images/basic_n.gif"); //image du monstre de base pour le niveau "normal"
			break;
		case 3 :
			StdDraw.picture(p.x,p.y,"images/basic_h.gif"); //image du monstre de base pour le niveau "hard"
			break;
		}
		
	}
}
