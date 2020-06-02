package gui_components;

import java.awt.Color;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class SpamPanel extends JPanel 
{
	public SpamPanel(Color background, Color border) 
	{
		super();
		setBackground(background);
		setBorder(BorderFactory.createLineBorder(border));
	}
}
