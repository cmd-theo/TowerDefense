package warcraftTD;

public class ArrowTower extends Tower {

	public ArrowTower(Position p) {
		super(p);
		this.type="arrow";
		this.range=0.3;
		this.north = new Paire(new Position(p), new Position(p.x, p.y+range));
		this.south = new Paire(new Position(p), new Position(p.x, p.y-range));
		this.east = new Paire(new Position(p), new Position(p.x+range, p.y));
		this.west = new Paire(new Position(p), new Position(p.x-range, p.y));
		this.vitesse=15;
	}


	public void draw() {
		if(!upgrade) {
			StdDraw.picture(p.x,p.y,"images/bow_tower.png");
		}
		else {
			StdDraw.picture(p.x,p.y,"images/arrow_towerUP.png");
		}

	}

}

