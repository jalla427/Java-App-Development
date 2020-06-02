/**********************************************
* 
* @name SpamPointCalculator.java
* @author Jack Allaire
* @purpose computational object designed to read an email and scan
* 		   it for common phrases found in spam emails.
* @sources https://javarevisited.blogspot.com/2016/07/10-examples-to-read-text-file-in-java.html
*          https://www.javatpoint.com/java-console-readline-method
* 
**********************************************/

package computational_object;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SpamPointCalculator implements SpamPointCalculatorInterface 
{	
	//Returns spam words/phrases list as a String
	public String getSpamList()
	{
		int size = size();
		String spamWordsString = "";
		String[] spamWords = new String[size()];
		
		//Use method to place spam words list into an array
		spamWords = spamListToArray();
		//For loop to convert list to a string
		spamWordsString = "Total Spam Phrases: " + size + 
							"\n-----------------------------------\n";
		for(int counter = 0; counter < size(); counter++)
		{
				spamWordsString += (counter + 1) + ". " + spamWords[counter] + "\n";
		}
		
		return spamWordsString;
	}
	
	//Returns spam words/phrases as a String array
	//Intended to simplify other methods in SpamPointCalculator.java
	public String[] spamListToArray()
	{	
		int size = size();
		int counter = 0;
		
		//String array that will hold the retrieved strings 
		String[] spamWords = new String[size];
		
		//Scanner to read the list of spam phrases
		try 
		{
			Scanner spamListScanner = new Scanner(new File("emailText1.txt"));
			
			while(spamListScanner.hasNextLine())
			{
					spamWords[counter] = spamListScanner.nextLine();
					counter++;
			}
			spamListScanner.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}

		//returns the string
		return spamWords;
		
	}
	
	public int size()
	{
		int size = 0;
		
		//Scanner to count the list of spam phrases
		try 
		{
			File file = new File("emailText1.txt");
			Scanner spamListScanner = new Scanner(file);
			
			//Iterates through the spam list
			while(spamListScanner.hasNextLine())
			{
					size++;
					spamListScanner.nextLine();
			}
			spamListScanner.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		return size;
		
	}
	
	public void add(String phraseOrKeyword)throws InvalidSpamException
	{
		try 
		{
			//File writer instantiation
			//Will be used to append the spam list accordingly
			FileWriter fileWriter = new FileWriter("emailText1.txt", true);
			
			//Append new phrase to file
			fileWriter.write(phraseOrKeyword + "\n");
			fileWriter.close();
		} 
		catch(IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void remove(String phraseOrKeyword) throws InvalidSpamException
	{
		try 
		{	
			//Retrieve size
			int size = size();
			int newCounter = 0;
					
			//Array to hold spam words
			String[] spamWordsOld = new String[size];
			String[] spamWordsNew = new String[size-1];

			//Call methods to retrieve list of spam phrases
			spamWordsOld = spamListToArray();
			
			//Loop to move the list of spam phrases into a new, smaller array
			//and to omit the desired phrase
			for(int counter = 0; counter < size; counter++)
			{
				if(!spamWordsOld[counter].equals(phraseOrKeyword))
				{
					spamWordsNew[newCounter] = spamWordsOld[counter];
					newCounter++;
				}
			}
			
			//File writer instantiation
			//Will be used to alter the spam list accordingly
			FileWriter fileWriter = new FileWriter("emailText1.txt", false);
			
			//Write new array to file
			for(int counter = 0; counter < newCounter; counter++)
			{
				fileWriter.write(spamWordsNew[counter] + "\n");
			}
			fileWriter.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	//Method to calculate the spam score of a given String
	//**** Out of use ****
	public int calculateSpamPoints(String email) 
	{
		int size = size();
		int spamScore = 0;
		
		//String array to hold spam list values
		String[] spamWords = new String[size];
		
		//Get spam list
		spamWords = spamListToArray();
		
		//Loop through list and for each word check to see if it exists in the String
		for(int counter = 0; counter < size; counter++)
		{
			if(email.contains(spamWords[counter]))
			{
				spamScore++;
			}
		}
		return spamScore;
	}

	public String displayCalculatedSpamPoints(String email) 
	{	
		int size = size();
		int spamScore = 0;
		
		//Variables for dealing with the occurrences of a single phrase
		int wordLength = 1;
		int occurrences = 0;
		int entryNum = 0;
		
		//String to hold formatted results
		String spamScoreOutput = "";
		
		//String array to hold spam list values
		String[] spamWords = new String[size];
		
		//Get spam list
		spamWords = spamListToArray();
		
		//Loop through list and for each word check to see if it exists in the String
		for(int counter = 0; counter < size; counter++)
		{
			//Finds occurrences of the given word
			wordLength = spamWords[counter].length();
			occurrences = (email.length() - email.replaceAll(spamWords[counter],"").length())/wordLength;
			
			//Adds to output String if occurrences is greater than zero
			if(occurrences > 0)
			{
				entryNum++;
				spamScore += occurrences;
				spamScoreOutput += entryNum + ". <" + occurrences + "> " + spamWords[counter] + "\n";
			}
			occurrences = 0;
		}
		
		//Finalizes output
		spamScoreOutput += "\nSpam Score: " + Integer.toString(spamScore);
		
		return spamScoreOutput;
	}
}
