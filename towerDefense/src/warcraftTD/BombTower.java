package warcraftTD;

public class BombTower extends Tower{
	public BombTower(Position p) {
		super(p);
		this.type="bomb";
		this.range=0.2;
		this.north = new Paire(new Position(p), new Position(p.x, p.y+range));
		this.south = new Paire(new Position(p), new Position(p.x, p.y-range));
		this.east = new Paire(new Position(p), new Position(p.x+range, p.y));
		this.west = new Paire(new Position(p), new Position(p.x-range, p.y));
		this.vitesse=20;
	}


	public void draw() {
		if(!upgrade) {
			StdDraw.picture(p.x,p.y,"images/bomb_tower.png");
		}
		else {
			StdDraw.picture(p.x,p.y,"images/bomb_towerUP.png");
		}

	}

}


