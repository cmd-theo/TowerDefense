package warcraftTD;

public class ArrowProjectile extends Projectile{


	public ArrowProjectile(Position p) {
		super(p);
		damage = 2;
		speed = 0.040;
	}
	
	// R�cup�ration des attributs de base du projectile et initalisation des caract�ristiques propres au projectile tir� par la tour d'archers

	/**
	 * Affiche une fl�che tir�e par la tour d'archers
	 */
	public void draw() {
		StdDraw.picture(p.x,p.y,"images/arrow.png");
	}





}
