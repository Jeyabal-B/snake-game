import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 4048961758527664547L;

	// window size settings
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	// character or item size
	static final int UNIT_SIZE = 25;
	static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
	static final int DELAY = 75;
	// snake size
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	int bodyParts = 6;
	int applesEaten = 0;
	// location of the apple
	int appleX;
	int appleY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	
	GamePanel() {
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.BLACK);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}

	public void startGame() {
		newApple();
		running = true;
		timer = new Timer(DELAY, this);
		timer.start();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	public void draw(Graphics g) {

		if (running) {
			// show the grid on screen, to understand
			/*
			 * for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) { // vertical lines
			 * g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT); // horizontal
			 * lines g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE); }
			 */
			g.setColor(Color.RED);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

			// drawing snake
			for (int i = 0; i < bodyParts; i++) {
				if (i == 0) {
					// head of the snake
					g.setColor(Color.GREEN);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				} else {
					// body of snake
					g.setColor(new Color(45, 180, 0));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}

			}
			
			//to display the score while playing the game
			g.setColor(Color.ORANGE);
			g.setFont(new Font("Ink Free", Font.BOLD, 40));
			FontMetrics fMetrics = getFontMetrics(g.getFont());
			g.drawString("Score "+applesEaten, (SCREEN_WIDTH - fMetrics.stringWidth("Score "+applesEaten))/2, g.getFont().getSize());
		} else {
			gameOver(g);
		}
	}

	// method to create a new apple whenever its called
	public void newApple() {
		appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
		appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
	}

	public void move() {
		for (int i = bodyParts; i > 0; i--) {
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		}
		switch (direction) {
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
		}
	}

	public void checkApple() {
		// eat apple, increases the body by 1
		if ((x[0] == appleX) && (y[0] == appleY)) {
			bodyParts++;
			applesEaten++;
			newApple();
		}
	}

	public void checkCollisions() {
		// to find when the snake hits itself
		for (int i = bodyParts; i > 0; i--) {
			if ((x[0] == x[i]) && (y[0] == y[i])) {
				running = false;
			}
		}
		// to find if snake touches the left border
		if (x[0] < 0) {
			running = false;
		}
		// right border
		if (x[0] > SCREEN_WIDTH) {
			running = false;
		}
		// top border
		if (y[0] < 0) {
			running = false;
		}
		// bottom border
		if (y[0] > SCREEN_HEIGHT) {
			running = false;
		}
		// stop timer
		if (!running) {
			timer.stop();
		}
	}

	public void gameOver(Graphics g) {
		// setting up text color and font
		g.setColor(Color.RED);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics fMetrics = getFontMetrics(g.getFont());
		// code to display actual text in screen finally
		g.drawString("Game Over!", (SCREEN_WIDTH - fMetrics.stringWidth("Game Over!")) / 2, SCREEN_HEIGHT / 2);
		
		g.setColor(Color.ORANGE);
		g.setFont(new Font("Ink Free", Font.BOLD, 40));
		FontMetrics sMetrics = getFontMetrics(g.getFont());
		g.drawString("Score "+applesEaten, (SCREEN_WIDTH - sMetrics.stringWidth("Score "+applesEaten))/2, g.getFont().getSize());
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
	}

	public class MyKeyAdapter extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent key) {
			switch (key.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if (direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if (direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if (direction != 'U') {
					direction = 'D';
				}
				break;
			}

		}
	}
}
