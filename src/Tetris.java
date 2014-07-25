import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Tetris extends JApplet implements KeyListener, ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private int speed;
	private boolean gameOver;
	private Screen screen;
	private Side side;
	private Tetromino current, next;
	private int currentRow, currentCol, orientation;
    private String song;
	private AudioClip clip;
    private Timer timer;
	
	public void init() {
		this.setFocusable(true);
		this.addKeyListener(this);
		this.setLayout(new BorderLayout());
		setSize(629,781);
		side = new Side();
		screen = new Screen();
		JPanel main = new JPanel();
		main.setBackground(Color.BLACK);
		main.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		getContentPane().setBackground(Color.BLACK);
		main.add(screen);
		screen.addMouseListener(this);
		side.a.addActionListener(this);
		side.b.addActionListener(this);
		side.c.addActionListener(this);
		side.newGame.addActionListener(this);
		side.pause.addActionListener(this);
		side.help.addActionListener(this);
		title();
		this.add(side, BorderLayout.EAST);
		this.add(main, BorderLayout.CENTER);
		playSong("tetrisb.mid");
		speed = 600;
		timer = new Timer(speed, new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        loop();
		    }
		});
		timer.setInitialDelay(600);
		startGame();
	}
	
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.rotate(-45);
		g2d.setFont(new Font("default", 1, 24));
		g2d.setColor(Color.white);
		g2d.drawString("FUN!", 0, 130);
		
	}
	
	private void startGame() {
		nextPiece();
		gameOver = false;
		timer.start();
	}
	
	private void newGame() {
		speed = 600;
		timer.setDelay(speed);
		screen.newGame();
		side.newGame();
		startGame();
	}
	
	private void loop() {
		if(!gameOver){
			updateScreen();
		} else {
			endGame();
		}
		repaint();
	}
	
	private void endGame() {
		screen.gameOver();
		timer.stop();
	}

	private void updateScreen() {
		
		 // check to see if piece can drop down by 1
		if(screen.validate(current, currentCol, currentRow + 1, orientation)) {
			screen.moveDown(current, currentCol, currentRow++, orientation);
		} else { // if not add it
			screen.addPiece(current, currentCol, currentRow, orientation);
			// scoring
			int numRows = screen.checkForRows();
			int points = 0;
			assert(numRows <= 4); // can't get more than 4 lines
				switch(numRows){
					case 1: points = 100; break;
					case 2: points = 200; break;
					case 3: points = 400; break;
					case 4: points = 800; break;
					default: 
				}
				int score = side.getScore();
				// update level -- After 9 levels cut speed in half until they lose!
				if (score >=0 && score < 500){
					side.updateText(points, numRows, 1);
				} else if (score >= 500 && score < 1000){
					side.updateText(points, numRows, 2);
					speed = 600;
				} else if (score >= 1000 && score < 1500){
					side.updateText(points, numRows, 3);
					speed = 500;
				} else if (score >= 1500 && score < 2000){
					side.updateText(points, numRows, 4);
					speed = 400;
				} else if (score >= 2000 && score < 2500){
					side.updateText(points, numRows, 5);
					speed = 300;
				} else if (score >= 2500 && score < 3000){
					side.updateText(points, numRows, 6);
					speed = 200;
				} else if (score >= 3000 && score < 3500){
					side.updateText(points, numRows, 7);
					speed = 150;
				} else if (score >= 3500 && score < 4000){
					side.updateText(points, numRows, 8);
					speed = 100;
				} else {
					side.updateText(points, numRows, 9);
					speed -= speed/2;
				}
				timer.setDelay(speed);
				// slight delay until next piece

			nextPiece();
		}
	}
	
	private void nextPiece() {
		if(current == null){
			this.current = Tetromino.generatePiece();
			this.next = Tetromino.generatePiece();

		} else {
			this.current = this.next;
			this.next = Tetromino.generatePiece();
		}
		side.updateNext(next);
		orientation = 0;
		currentCol = current.getTiles()[0].length == 16 ? 2 : 3;
		currentCol = current.getTiles()[0].length == 4 ? 4 : currentCol;
		currentRow = 0;
		boolean isOver = screen.addPiece(current, currentCol, currentRow, orientation);
		if(!isOver){
			gameOver = true;
		}
	}

	private void title() {
		try {
			JPanel panel = new JPanel();
			panel.setLayout(new FlowLayout());
			panel.setBackground(Color.BLACK);
			Image i = getImage(getCodeBase(), "tetris.png");
			JLabel l = new JLabel(new ImageIcon(i));
			panel.add(l);
			this.add(panel, BorderLayout.NORTH);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void keyReleased(KeyEvent event) {
		int key = event.getKeyCode();
		if(key == KeyEvent.VK_UP){
			if(screen.checkRotate(current, currentCol, currentRow, orientation)) {
				screen.removePiece(current, currentCol, currentRow, orientation);
				orientation = (orientation+1)%4;
				screen.addPiece(current, currentCol, currentRow, orientation);
			}
			screen.paintScreen();
		} else if(key == KeyEvent.VK_DOWN){
			if(screen.validate(current, currentCol, currentRow + 1, orientation)) {
				screen.moveDown(current, currentCol, currentRow++, orientation);
			}
			screen.repaint();
		} else if(key == KeyEvent.VK_LEFT) {
			if(screen.checkLeft(current, currentCol, currentRow, orientation)){
				screen.removePiece(current, currentCol, currentRow, orientation);
				screen.moveLeft(current, currentCol--, currentRow, orientation);
			}
			screen.repaint();
		} else if(key == KeyEvent.VK_RIGHT){
			if(screen.checkRight(current, currentCol, currentRow, orientation)) {
				screen.removePiece(current, currentCol, currentRow, orientation);
				screen.moveRight(current, currentCol++, currentRow, orientation);
			}
				screen.repaint();
		}
		repaint();
	}
	

	@Override
	public void keyPressed(KeyEvent arg0) {	}

	@Override
	public void keyTyped(KeyEvent arg0) {  }

	private void playSong(String song){
		// if no song has been started ie: beginning
		if(this.song == null){
			this.song = "tetrisb";
			clip = getAudioClip(getCodeBase(), this.song);
			clip.loop();
		}
		if(!this.song.equals(song)){
			clip.stop();
			clip = getAudioClip(getCodeBase(), song);				
			clip.loop();
			this.song = song;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(src == side.a){
			playSong("tetrisb.mid");
			this.requestFocus();
		} else if(src == side.b){
			playSong("tetrisc.mid");
			this.requestFocus();
		} else if(src == side.c){
			playSong("zelda.aiff");
			this.requestFocus();
		} else if(src == side.pause){
			screen.pause();
			timer.stop();
			this.requestFocus();
		} else if(src == side.newGame){
			newGame();
			this.requestFocus();
		} else if(src == side.help){
			screen.pause();
			timer.stop();
			side.popupHelp();
		}

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(screen.paused){
			screen.resume();
			timer.start();
		} else if(screen.gameOver){
			screen.gameOver = false;
			newGame();
		}
		this.requestFocus();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {	}

	@Override
	public void mouseExited(MouseEvent arg0) { }

	@Override
	public void mousePressed(MouseEvent arg0) { }

	@Override
	public void mouseReleased(MouseEvent arg0) { }
	
}
