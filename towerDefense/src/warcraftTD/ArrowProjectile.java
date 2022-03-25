package warcraftTD;

public class ArrowProjectile extends Projectile{


	public ArrowProjectile(Position p) {
		super(p);
		damage = 2;
		speed = 0.040;
	}
	
	// Récupération des attributs de base du projectile et initalisation des caractéristiques propres au projectile tiré par la tour d'archers

	/**
	 * Affiche une flèche tirée par la tour d'archers
	 */
	public void draw() {
		StdDraw.picture(p.x,p.y,"images/arrow.png");
	}





}
