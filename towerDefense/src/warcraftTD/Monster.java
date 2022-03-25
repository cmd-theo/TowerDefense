package warcraftTD;

public abstract class Monster {
	// Position du monstre à l'instant t
	Position p;
	// Vitesse du monstre
	double speed;
	// Position du monstre à l'instant t+1
	Position nextP;
	// Boolean pour savoir si le monstre à atteint le chateau du joueur
	boolean reached;
	// Compteur de déplacement pour savoir si le monstre à atteint le chateau du joueur
	int checkpoint = 0;
	//Nombre de virages effectu�s par le monstre sur le chemin
	int virage;
	//Puissance d'attaque du monstre 
	int damage;
	//Points de vie du monstre
	int pv;
	//Identit� du monstre : base, air ou boss
	String id;
	//Passe a true si le monstre est touch� par un projectile
	boolean shot;
	//Difficult� choisie par l'utilisateur, influe sur l'apparence du monstre
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
	 * D�place le monstre en fonction de sa vitesse sur l'axe des x et des y et de sa prochaine position.
	 */
	public void move() {
		// Mesure sur quel axe le monstre se dirige.
		double dx = nextP.x - p.x;
		double dy = nextP.y - p.y;
		if (dy + dx != 0){
			// Mesure la distance à laquelle le monstre à pu se déplacer.
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
	 * Fonction abstraite qui sera instanciée dans les classes filles pour afficher le monstre sur le plateau de jeu.
	 */
	public abstract void draw();
}
