package gui_components;

import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.text.Document;

public class SpamTextField extends JTextField 
{

	public SpamTextField(int sizex, int sizey, boolean rightAlligned) 
	{
		super();
		setMaximumSize(new Dimension(sizex, sizey));
		if(!rightAlligned)
		{
			setHorizontalAlignment(JTextField.LEFT);
		}
		else
		{
			setHorizontalAlignment(JTextField.RIGHT);
		}
	}
}
