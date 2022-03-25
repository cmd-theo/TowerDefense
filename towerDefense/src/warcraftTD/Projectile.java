package warcraftTD;

public abstract class Projectile {
	// Position du projectile à l'instant t
	Position p;
	// Vitesse du projectile
	double speed;
	// Position du projectile à l'instant t+1
	Position nextP;
	// Boolean pour savoir si le monstre à atteint le chateau du joueur
	boolean reached;
	// Compteur de d�placement pour savoir si le projectile à atteint un monstre
	int checkpoint = 0;
	//D�g�ts caus�s par le projectile
	int damage;

	public Projectile(Position p) {
		this.p = p;
		this.nextP = new Position(p);
		this.damage = 0;
		this.speed = 0;
	}

	/**
	 * Déplace le projectile en fonction de sa vitesse sur l'axe des x et des y et de sa prochaine position.
	 */
	public void move() {
		// Mesure sur quel axe le projectile se dirige.
		double dx = nextP.x - p.x;
		double dy = nextP.y - p.y;
		if (dy + dx != 0){
			// Mesure la distance à laquelle le projectile à pu se déplacer.
			double ratioX = dx/(Math.abs(dx) + Math.abs(dy));
			double ratioY = dy/(Math.abs(dx) + Math.abs(dy));
			p.x += ratioX * speed;
			p.y += ratioY * speed;
		}
	}

	public void update() {
		move();
		draw();
		checkpoint++;
	}

	/**
	 * Fonction abstraite qui sera instanciée dans les classes filles pour afficher le projectiel sur le plateau de jeu.
	 */
	public abstract void draw();
}
