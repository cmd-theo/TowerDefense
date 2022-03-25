package warcraftTD;

public class BombProjectile extends Projectile{

	public BombProjectile(Position p) {
		super(p);
		damage = 8;
		speed = 0.020;
	}
	// R�cup�ration des attributs de base du projectile et initalisation des caract�ristiques propres au projectile tir� par la tour de bombes

	/**
	 * Affiche une fleche qui change de couleur au cours du temps
	 * Le monstre est représenté par un cercle de couleur bleue ou grise
	 */
	public void draw() {
		StdDraw.picture(p.x,p.y,"images/bomb.png");
	}
}
