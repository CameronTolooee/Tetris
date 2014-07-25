import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;


public class Side extends JPanel {

	private static final long serialVersionUID = 2195379760914381351L;
	
	private int score;
	private int lines;
	private int level;
	private JLabel text;
	private String song;
	private NextPanel n;
	public JRadioButton a;
	public JRadioButton b;
	public JRadioButton c;
	public JButton pause;
	public JButton help;
	public JButton newGame;
	
	
	public Side() {
		this.setLayout(new GridLayout(3,1));
		this.setBackground(Color.BLACK);
		score = 0;
		lines = 0;
		level = 1;
		next();
		text();
		buttons();
	}
	
	public int getScore(){
		return score;
	}
	
	
	private void next() {
		JPanel p = new JPanel();
		p.setBackground(Color.BLACK);
		p.setLayout(new FlowLayout());
		n = new NextPanel();
		p.add(n);
		this.add(p);
	}
	
	public void updateNext(Tetromino tet){
		n.setNext(tet);
		n.repaint();
	}
	
	@Override
	public void paintComponent(Graphics g){
	}
	
	private void text(){
		JPanel p = new JPanel();
		p.setBackground(Color.BLACK);
		p.setFont(new Font("Monospaced", Font.BOLD, 14));
		text = new JLabel("<HTML>Score: "+score
				+ "<br>Level: "+level
				+"<br>Lines: "+lines+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</HTML>");
		text.setForeground(Color.WHITE);
		text.setFont(new Font("Monospaced", Font.BOLD, 20));
		p.add(text);
		this.add(p);
	}
	
	private void buttons(){
		ButtonGroup group = new ButtonGroup();
		
		newGame = new JButton("New Game");
		help = new JButton("Help");
		pause = new JButton("Pause");
		
		newGame.setBackground(Color.BLACK);
		help.setBackground(Color.BLACK);
		pause.setBackground(Color.BLACK);
		
		newGame.setForeground(Color.WHITE);
		help.setForeground(Color.WHITE);
		pause.setForeground(Color.WHITE);
		
		pause.setToolTipText("Click to pause the game!");
		help.setToolTipText("Click for instructions on how to play the game.");
		newGame.setToolTipText("Click to end this game and start a new one.");
	
		newGame.setFont(new Font("Monospaced", Font.BOLD, 15));
		help.setFont(new Font("Monospaced", Font.BOLD, 15));
		pause.setFont(new Font("Monospaced", Font.BOLD, 15));
		
		help.setBorderPainted(false);
		help.setFocusPainted(false);
		
		pause.setBorderPainted(false);
		pause.setFocusPainted(false);
		
		newGame.setBorderPainted(false);
		newGame.setFocusPainted(false);
		
		
		
		a = new JRadioButton("A");
		b = new JRadioButton("B");
		c = new JRadioButton("C");
		a.setBackground(Color.BLACK);
		b.setBackground(Color.BLACK);
		c.setBackground(Color.BLACK);
		a.setForeground(Color.WHITE);
		b.setForeground(Color.WHITE);
		c.setForeground(Color.WHITE);

		a.setFont(new Font("Monospaced", Font.BOLD, 15));
		b.setFont(new Font("Monospaced", Font.BOLD, 15));
		c.setFont(new Font("Monospaced", Font.BOLD, 15));
		
		group.add(a);
		group.add(b);
		group.add(c);
		a.setSelected(true);
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.setBackground(Color.BLACK);
		
		JPanel top = new JPanel();
		top.setLayout(new FlowLayout());
		top.setBackground(Color.BLACK);
		top.add(pause);
		top.add(newGame);
		top.add(help);
		
		JPanel bottom = new JPanel();
		JPanel south = new JPanel();
		south.setLayout(new BorderLayout());
		
		
		JLabel music = new JLabel("Music:");
		music.setForeground(Color.WHITE);
		music.setFont(new Font("Monospaced", Font.BOLD, 15));
		music.setHorizontalAlignment(JLabel.CENTER);
		
		
		bottom.setLayout(new FlowLayout());
		bottom.setBackground(Color.BLACK);
		bottom.add(a);
		bottom.add(b);
		bottom.add(c);
		
		south.setBackground(Color.BLACK);
		south.add(music, BorderLayout.NORTH);
		south.add(bottom, BorderLayout.CENTER);
		
		p.add(top, BorderLayout.NORTH);
		p.add(south, BorderLayout.SOUTH);
		this.add(p);
	}
	
	public void updateText(int score, int lines, int level){
		this.score += score;
		this.lines += lines;
		this.level = level;
		text.setText("<HTML>Score: "+this.score
				+ "<br>Level: "+this.level
				+"<br>Lines: "+this.lines+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</HTML>");
	}

	public void popupHelp(){
		JFrame popup = new JFrame();
		JTextArea text = new JTextArea();
		JPanel p = new JPanel();
		JLabel howTo =  new JLabel("HOW TO PLAY");
		p.add(howTo);
		text.setBackground(Color.BLACK);
		text.setForeground(Color.WHITE);
		text.setFont(new Font("Monospaced", Font.BOLD, 15));
		text.setText("Controls:\nRotate Piece:\tUP\nMove Left:\tLEFT\nMove Right:\tRIGHT\nMove Down:\tDOWN");
		popup.add(p, BorderLayout.NORTH);
		popup.add(text, BorderLayout.CENTER);
		popup.pack();
		popup.setSize(250, 180);
		popup.setVisible(true);
		popup.setLocation(300, 500);
	}
	
	public void newGame(){
		updateText(-score, -lines, 1);
	}
	
}
