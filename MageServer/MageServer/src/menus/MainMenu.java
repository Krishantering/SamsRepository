package menus;

import handle.MageQuest;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MainMenu extends JPanel implements ActionListener {
	
	public JButton Start;
	public JButton Options;
	public MageQuest mq;
	
	/**
	 * This is the main menu of the game thingy magiggy
	 */
	private static final long serialVersionUID = 1L;

	public MainMenu(MageQuest mq) {
		
		this.mq=mq;
		
		Start=new JButton("Start");
		Start.addActionListener(this);
		this.add(Start);
		Options=new JButton("Options");
		Options.addActionListener(this);
		this.add(Options);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton src = (JButton) e.getSource();
		
		if(src==Start){
			this.setVisible(false);
			mq.start();
		}
		
	}

}
