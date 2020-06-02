package gui_components;

import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

public class SpamButton extends JButton 
{
	public SpamButton(String text, ActionListener listener) 
	{
		super(text);
		this.addActionListener(listener);
	}


}
