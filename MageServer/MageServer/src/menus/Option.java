package menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Option extends JPanel implements ActionListener{
	
	int State=0;
	boolean bState;
	String Subject="Uknown Subject";
	int type;
	JButton b1;
	JButton b2;
	JLabel label;
	
	/**
	 * This is an option of any type
	 */
	private static final long serialVersionUID = 1L;

	public Option(String Subject,int State) {
		this.type=1;
		this.Subject=Subject;
		this.State=State;
		
		
	}
	
	public Option(String Subject,boolean bState) {
		this.type=2;
		this.Subject=Subject;
		this.bState=bState;
		
			label=new JLabel(Subject+" : "+bState);
			b1=new JButton("Change");
			b1.addActionListener(this);
			
			this.add(label);
			this.add(b1);
			
		
	}
	
	public boolean getbState(){
		return bState;
	}
	
	public int getState(){
		return State;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		
		if(type==2){
			
			if(bState){
				bState=false;
			}else{
				bState=true;
			}
			label.setText(Subject+" : "+bState);
		}
		
	}

}
