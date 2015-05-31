package menus;

import handle.MageQuest;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainMenu extends JPanel implements ActionListener {
	
	public JButton singleplayer;
	public JButton multiplayer;
	public MageQuest mq;
	
	/**
	 * This is the main menu of the game thingy magiggy
	 */
	private static final long serialVersionUID = 1L;

	public MainMenu(MageQuest mq) {
		
		this.mq=mq;
		
		singleplayer=new JButton("SinglePlayer");
		singleplayer.addActionListener(this);
		this.add(singleplayer);
		multiplayer=new JButton("MultiPlayer");
		multiplayer.addActionListener(this);
		this.add(multiplayer);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton src = (JButton) e.getSource();
		
		if(src==singleplayer){
			this.setVisible(false);
			mq.start();
		}
		
		if(src==multiplayer){
			JOptionPane.showConfirmDialog(null, "Comming Soon", "MultiPlayer", JOptionPane.PLAIN_MESSAGE, JOptionPane.INFORMATION_MESSAGE);
		}
		
	}

}
