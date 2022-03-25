package warcraftTD;

public abstract class Monster {
	// Position du monstre Ã  l'instant t
	Position p;
	// Vitesse du monstre
	double speed;
	// Position du monstre Ã  l'instant t+1
	Position nextP;
	// Boolean pour savoir si le monstre Ã  atteint le chateau du joueur
	boolean reached;
	// Compteur de dÃ©placement pour savoir si le monstre Ã  atteint le chateau du joueur
	int checkpoint = 0;
	//Nombre de virages effectués par le monstre sur le chemin
	int virage;
	//Puissance d'attaque du monstre 
	int damage;
	//Points de vie du monstre
	int pv;
	//Identité du monstre : base, air ou boss
	String id;
	//Passe a true si le monstre est touché par un projectile
	boolean shot;
	//Difficulté choisie par l'utilisateur, influe sur l'apparence du monstre
	int difficulty;
	
	public Monster(Position p) {
		this.p = p;
		this.nextP = new Position(p);
		this.damage = 0;
		this.speed = 0;
		this.virage = 0;
		this.pv = 0;
		this.shot = false;
		this.id = "";
		this.difficulty = 0;
	}
	
	/**
	 * Déplace le monstre en fonction de sa vitesse sur l'axe des x et des y et de sa prochaine position.
	 */
	public void move() {
		// Mesure sur quel axe le monstre se dirige.
		double dx = nextP.x - p.x;
		double dy = nextP.y - p.y;
		if (dy + dx != 0){
			// Mesure la distance Ã  laquelle le monstre Ã  pu se dÃ©placer.
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
	 * Fonction abstraite qui sera instanciÃ©e dans les classes filles pour afficher le monstre sur le plateau de jeu.
	 */
	public abstract void draw();
}
