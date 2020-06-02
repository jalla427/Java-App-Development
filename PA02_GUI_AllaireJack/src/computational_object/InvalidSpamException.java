/**********************************************
* 
* @name InvalidSpamException.java
* @author Jack Allaire
* @purpose: Extension of the extension class 
* 		   intended for use with errors pertaining 
* 		   to the modification.
* @sources https://www.webucator.com/how-to/how-create-an-exception-class-java.cfm
* 
**********************************************/

package computational_object;

public class InvalidSpamException extends Exception
{
	public InvalidSpamException(String message)
	{
		super(message);
	}
}
