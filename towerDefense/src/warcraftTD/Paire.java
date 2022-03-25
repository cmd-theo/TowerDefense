package warcraftTD;

public class Paire {
	
	private Position start;
	private Position end;
	public  Paire(Position start, Position end) {
		this.start = start;
		this.end = end;
		
	}
	public Position getStart(){
        return this.start;
    }

    public Position getEnd(){

        return this.end;

    }
    public void setStart(Position p) {
    	this.start = p;
    }
    public void setEnd(Position p) {
    	this.end = p;
    }
	
}
