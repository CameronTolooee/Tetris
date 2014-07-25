import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.PaintContext;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class NextPanel extends JPanel {
	private Tetromino next;
	private int offset;
	private int orientation;
	
	public NextPanel() {
		JLabel next = new JLabel("NEXT");
		next.setForeground(Color.WHITE);
		setPreferredSize(new Dimension(180,180));
		setBackground(Color.BLACK);
		setBorder(BorderFactory.createLineBorder(Color.WHITE));
		add(next);
	}
	
	@Override
	public void paintComponent(Graphics g){
		boolean[][] tiles = next.getTiles();
		int count = 0;
		int width = next.getTiles()[0].length == 9 ? 3 : 4; // get width (3 or 4) of piece
		width = next.getTiles()[0].length == 4 ? 2 : width;
		for(int row = 0; row < width; ++row) {
			for(int col = 0; col < width; ++col){
				if(tiles[orientation][count++]){
					Screen.drawSquare(next.getColor(), offset+col*35, offset+row*35, g);
				}
			}
		}
	}
	
	public void setNext(Tetromino next){
		// determine best orientation;
		this.next = next;
		switch(next.toString()) {
		case "TypeJ":orientation = 3; offset = 30; break;
		case "TypeZ":
		case "TypeI": orientation = 2; offset = 20; break;
		case "TypeS": orientation = 2; offset = 30; break;
		case "TypeL": orientation = 1; offset = 30; break;
		case "TypeT": orientation = 0; offset = 30;break;
		default: orientation = 0; offset = 50;
		} 
	}
}
