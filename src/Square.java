import java.awt.Color;


public class Square {
	private boolean empty;
	private Color color;
	
	public Square(){
		empty = true;
		color = null;
	}
	
	public Square(boolean empty, Color color){
		this.color = color;
		this.empty = empty;
	}
	
	public Color getColor(){
		return color;
	}
	
	public boolean isEmpty(){
		return empty;
	}
}
