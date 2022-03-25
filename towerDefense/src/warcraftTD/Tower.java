package warcraftTD;

public abstract class Tower {
	// Position de la tour
	Position p;
	//Passe a true si la tour a été améliorée
	boolean upgrade;
	double range;
	Paire north;
	Paire south;
	Paire east;
	Paire west;
	String type;
	int vitesse;
	public Tower(Position p) {
		this.p=p;
		this.type="";
		this.upgrade=false;
		this.range=0;
		this.north = new Paire(new Position(p), new Position(p.x, p.y+range));
		this.south = new Paire(new Position(p), new Position(p.x, p.y-range));
		this.east = new Paire(new Position(p), new Position(p.x+range, p.y));
		this.west = new Paire(new Position(p), new Position(p.x-range, p.y));
		this.vitesse=0;
	}
	
	public void update() {
		draw();
	}
	public abstract void draw();



}
