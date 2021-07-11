import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class GameFrame extends JFrame implements ActionListener{

	private static final long serialVersionUID = 3868855034012153296L;

	GamePanel game;
	//JButton resetButton;
	
	GameFrame() {
		
		game = new GamePanel();
		this.add(game);
		this.setTitle("Snake Game");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		
		/*
		 * resetButton = new JButton(); resetButton.setText("Reset");
		 * resetButton.setSize(75,40); resetButton.setLocation(0,200);
		 * resetButton.addActionListener(this); this.add(resetButton);
		 */		
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		/*
		 * if(e.getSource()==resetButton) { this.remove(game); game = new GamePanel();
		 * this.add(game); SwingUtilities.updateComponentTreeUI(this); }
		 */
	}
}
