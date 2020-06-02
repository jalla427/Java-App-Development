/**
 * @author Mark Hall
 * 
 * @description SpamPointCalculatorInterface to guarantee students have correct 
 * 				class methods
 * 
 */

package computational_object;

public interface SpamPointCalculatorInterface
{
	//Accessors
	
	public abstract int size();
	
	//Altered to display spam list in a nicer format,
	//made the requested method "displaySpamList" unnecessary
	public abstract String getSpamList();
	
	//No longer in use, functionality covered by displayCalculatedSpamPoints
	public abstract int calculateSpamPoints( String email );
	
	public abstract String displayCalculatedSpamPoints( String email );
	
	public abstract String toString();
	
	//Mutators/Transformers
	
	public abstract void add( String phraseOrKeyword ) throws InvalidSpamException;
	
	public abstract void remove( String phraseOrKeyword ) throws InvalidSpamException;
	
	// Added by Jack Allaire for use in PA02_GUI_AllaireJack
	public abstract String[] spamListToArray();
	
	
}
