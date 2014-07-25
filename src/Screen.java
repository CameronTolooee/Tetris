import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Screen extends JPanel {

	private static final long serialVersionUID = 2195379760914381351L;	
	
	private Square[][] screen;
	public boolean paused;
	public boolean gameOver, drawOver = false;

	public void paintScreen(){
		repaint();
	}

	public Screen() {
		super();
		paused = false;
		screen = new Square[22][10];
		for (int row = 0; row < 20; ++row) {
			for (int col = 0; col < 10; ++col) {
				screen[row][col] = new Square(true, Color.BLUE);
			}
		}
		for(int i = 0; i < 10; ++i){
			screen[20][i] = new Square(false, Color.BLACK);
			screen[21][i] = new Square(false, Color.BLACK);
		}
		setBackground(Color.BLACK);
		setBorder(BorderFactory.createLineBorder(Color.RED));
		Dimension d = new Dimension(350, 694);
		setPreferredSize(d);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(paused) {
			g.setColor(Color.BLACK);
			g.fillRect(1, 1, getWidth()-1, getHeight()-6);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Monospaced", Font.BOLD, 15));
			g.drawString("Click to resume", 100, 200);
		} else if(gameOver) {
			// Do game over animation
			animateGameOver();
		} else if (drawOver){
			gameOver = true;
			drawScreen(g);
			drawOver = false;
			g.setColor(Color.BLACK);
			g.fillRect(45, 175, 250, 150);
			g.setColor(Color.WHITE);
			g.drawRect(45, 175, 250, 150);
			g.setFont(new Font("Monospaced", Font.BOLD, 15));
			g.drawString("Click to play again!", 75, 250);
		} else {
			drawScreen(g);
		}
		
	}
	
	@Override
	public void paint(Graphics g){
		paintComponent(g);
	}
	
	// randomly fill empty spaces
	private void animateGameOver() {
		gameOver = false;
		final Random r = new Random();
		Timer t = new Timer(7, new ActionListener() {
			int cntr = 0;
			@Override
			public void actionPerformed(ActionEvent e) {
				if(cntr == 199) {
					((Timer)e.getSource()).stop();
					drawOver = true;
					repaint();
				}
				int x = r.nextInt(10);
				int y = 19 - (cntr / 10);
					if(screen[y][x].isEmpty()) {
						screen[y][x] = new Square(false,  new Color(r.nextInt(225),r.nextInt(225),r.nextInt(225)));
						repaint();
					}
					++cntr;
				}
			});
		t.start();
	}

	public void newGame(){
		paused = false;
		gameOver = false;
		drawOver = false;
		screen = new Square[22][10];
		for (int row = 0; row < 20; ++row) {
			for (int col = 0; col < 10; ++col) {
				screen[row][col] = new Square(true, Color.BLUE);
			}
		}
		for(int i = 0; i < 10; ++i){
			screen[20][i] = new Square(false, Color.BLACK);
			screen[21][i] = new Square(false, Color.BLACK);
		}
		repaint();
	}

	public void drawScreen(Graphics g) {
		for (int row = 0; row < 20; ++row) {
			for (int col = 0; col < 10; ++col) {
				Square s = screen[row][col];
				if (!s.isEmpty()) {
					drawSquare(s.getColor(), col * 35, row * 35, g);
				}
			}
		}
	}

	
	public boolean addPiece(Tetromino piece, int x, int y, int orientation){
		boolean[][] tiles = piece.getTiles();
		int count = 0;
		int width = piece.getTiles()[0].length == 9 ? 3 : 4; // get width (3 or 4) of piece
		width = piece.getTiles()[0].length == 4 ? 2 : width;
		for(int row = 0; row < width; ++row) {
			for(int col = 0; col < width; ++col){
				if(tiles[orientation][count++]){
					if(screen[y+row][x+col].isEmpty()){
						screen[row+y][col+x] = new Square(false, piece.getColor());
					} else {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public void gameOver(){
		gameOver = true;
		repaint();
	}
	
	public void pause(){
		paused = true;
		repaint();
	}
	
	public void resume(){
		paused = false;
		repaint();
	}
	
	public void printScreen() {
		System.out.println("-------------------------------------------------------------");
		for (int row = 0; row < 22; ++row) {
			for (int col = 0; col < 10; ++col) {
				System.out.printf("%6s ",screen[row][col].isEmpty());
			}
			System.out.println();
		}
	}

	public static void drawSquare(Color c, int x, int y, Graphics g) {
		// main square
		g.setColor(c);
		g.fillRect(x, y, 35, 35);
		

		// darker LL
		g.setColor(c.darker());
		Polygon p = new Polygon();
		p.addPoint(x, y+35);
		p.addPoint(x+35, y+35);
		p.addPoint(x+30, y+30);
		p.addPoint(x+5, y+30);
		p.addPoint(x+5, y+5);
		p.addPoint(x, y);
		g.fillPolygon(p);
		
		// lighter UR
		g.setColor(c.brighter());
		p = new Polygon();
		p.addPoint(x, y);
		p.addPoint(x+35, y);
		p.addPoint(x+35, y+35);
		p.addPoint(x+30, y+30);
		p.addPoint(x+30, y+5);
		p.addPoint(x+5, y+5);
		g.fillPolygon(p);
		
		// black outline
		g.setColor(Color.BLACK);
		g.drawRect(x, y, 35, 35);
	}
	
	public synchronized boolean validate(Tetromino current, int x, int y, int orientation) {
		
		boolean[][] tiles = current.getTiles();
		int width = current.getTiles()[0].length == 9 ? 3 : 4; // get width (2, 3, or 4) of piece
		width = current.getTiles()[0].length == 4 ? 2 : width;	
		boolean result = true;
		int count = 0;
		// clear old piece
		--y;
				for(int row = 0; row < width; ++row) {
					for(int col = 0; col < width; ++col){ // compare piece with board
						if(tiles[orientation][count++] && !screen[row+y][col+x].isEmpty() && y < 19){
							screen[row+y][col+x] = new Square();
						}
					}
				}
		++y;
		count = 0;
		for(int row = 0; row < width; ++row) {
			for(int col = 0; col < width; ++col){
				if(tiles[orientation][count++] && !screen[row+y][col+x].isEmpty()){
					result = false;
				}
			}
		}
		return result;
	}

	public void removePiece(Tetromino current, int x, int y, int orientation){
		boolean[][] tiles = current.getTiles();
		int count = 0;
		int width = current.getTiles()[0].length == 9 ? 3 : 4; // get width (2, 3, or 4) of piece
		width = current.getTiles()[0].length == 4 ? 2 : width;	
		for(int row = 0; row < width; ++row) {
			for(int col = 0; col < width; ++col){ // compare piece with board
				if(tiles[orientation][count++] && !screen[row+y][col+x].isEmpty() && y < 18){
					screen[row+y][col+x] = new Square();
				}
			}
		}
	}
	
	public int checkForRows() {
		// scan for completed rows and remove them
		ArrayList<Integer> rowsToBeCleared = new ArrayList<Integer>();
		boolean test = true;
		for(int i = 0; i < 20; ++i){
			for(int j = 0; j < 10; ++j){
				if(screen[i][j].isEmpty())
					test = false;
			} 
			if(test){
				rowsToBeCleared.add(i);
			} else {
				test = true;
			}
		}
		// update score to reflect rows cleared
		removeRows(rowsToBeCleared);
		return rowsToBeCleared.size();
	}	
	
	private void removeRows(ArrayList<Integer> rowsToBeCleared){
		int cntr = 0;
		String rows ="";
		for(int row : rowsToBeCleared){
			rows += row+", ";
			for(int i = row; i > 0; --i){
				for(int j = 0; j < 10; ++j) {
					screen[i][j] = new Square(screen[i-1][j].isEmpty(), screen[i-1][j].getColor());
				}
			}
		}
		System.out.println(rows);
	}

	public void moveDown(Tetromino piece, int x, int y, int orientation) {
		boolean[][] tiles = piece.getTiles();
		int count = 0;
		// clear the current piece out
		int width = piece.getTiles()[0].length == 9 ? 3 : 4; // get width (3 or 4) of piece
		width = piece.getTiles()[0].length == 4 ? 2 : width;	

		for(int row = 0; row < width; ++row) {
			for(int col = 0; col < width; ++col){
				if(tiles[orientation][count++] && !screen[row+y][col+x].isEmpty()){
					screen[row+y][col+x] = new Square();
				}
			}
		}
		addPiece(piece, x, ++y, orientation);
	}
	

	public void moveLeft(Tetromino current, int x, int y, int orientation) {
		boolean[][] tiles = current.getTiles();
		int count = 0;
		// clear the current piece out
		int width = current.getTiles()[0].length == 9 ? 3 : 4; // get width (3 or 4) of piece
		width = current.getTiles()[0].length == 4 ? 2 : width;	
		++x;
		for(int row = 0; row < width; ++row) {
			for(int col = 0; col < width; ++col){
				if(col+x < 10 && tiles[orientation][count++] && !screen[row+y][col+x].isEmpty() ){
					screen[row+y][col+x] = new Square();
				}
			}
		}
		--x;
		boolean result = true;
		count = 0;
		for(int row = 0; row < width; ++row) {
			for(int col = 0; col < width; ++col){
				if(tiles[orientation][count++] && !screen[row+y][col+x].isEmpty()){
					result = false;
				}
			}
		}
		if(result)
			addPiece(current, --x, y, orientation);
	}

	public void moveRight(Tetromino current, int x, int y, int orientation) {
		boolean[][] tiles = current.getTiles();
		int count = 0;
		// clear the current piece out
		int width = current.getTiles()[0].length == 9 ? 3 : 4; // get width (3 or 4) of piece
		width = current.getTiles()[0].length == 4 ? 2 : width;	
		boolean result = true;
		count = 0;
		for(int row = 0; row < width; ++row) {
			for(int col = 0; col < width; ++col){
				if(tiles[orientation][count++] && !screen[row+y][col+x].isEmpty()){
					result = false;
				}
			}
		}
		if(result)
			addPiece(current, ++x, y, orientation);
		
	}

	public boolean checkRotate(Tetromino current, int x, int y, int orientation) {
		boolean result = false;
		if(checkLeft(current, x+1, y, (orientation+1)%4)){
			if(checkRight(current, x+1, y, (orientation+1)%4)){
				result = true;
			}
		}
		return result;
	}
	
	public boolean checkLeft(Tetromino current, int x, int y, int orientation) {
		boolean result = true;
		int col = x-1;
		if(col + current.bufferLeft(orientation) < 0){
			result = false;
		} else {
			//checkVersusBoard
		}
		return result;
	}
	
	public boolean checkRight(Tetromino current, int x, int y, int orientation) {
		boolean result = true;
		int col = x-1;
		int width = current.getTiles()[0].length == 9 ? 3 : 4; // get width (3 or 4) of piece
		width = current.getTiles()[0].length == 4 ? 2 : width;
		if(col - current.bufferRight(orientation) > (9-width-1)) {
			result = false;
		} else {
			//checkVersusBoard
		}
		return result;
	}
}







