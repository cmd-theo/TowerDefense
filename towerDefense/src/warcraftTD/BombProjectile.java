package warcraftTD;

public class BombProjectile extends Projectile{

	public BombProjectile(Position p) {
		super(p);
		damage = 8;
		speed = 0.020;
	}
	// Récupération des attributs de base du projectile et initalisation des caractéristiques propres au projectile tiré par la tour de bombes

	/**
	 * Affiche une fleche qui change de couleur au cours du temps
	 * Le monstre est reprÃ©sentÃ© par un cercle de couleur bleue ou grise
	 */
	public void draw() {
		StdDraw.picture(p.x,p.y,"images/bomb.png");
	}
}
