package lexicalAnalyzerPackage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class LexicalAnalyzer 
{
	public static void main(String[] args) 
	{
		//Ask for file path if lexInput.txt can't be reached for some reason
		/*System.out.println("What is the filepath to be scanned?");
		Scanner scan = new Scanner(System.in);
		String file = scan.nextLine();
		System.out.println();*/
		String file = "../LexicalAnalyzer/src/lexicalAnalyzerPackage/lexInput.txt"; // above requires this line be commented out
		//char goosecheck = 'A'; int d = (int)goosecheck; System.out.println(d); //This is here if you want to find a character that breaks it
		
		//Start analyzer
		System.out.println("Ian Neer, CSCI4200-DA, Fall 2018, Lexical Analyzer");
		print80Asterisks();
		
		//Read file line-by-line, pass to buildLexemes function
		try(BufferedReader lineByLineScan = new BufferedReader(new FileReader(file)))
		{ 
			String line;
			while((line = lineByLineScan.readLine()) != null)
			{
				buildLexemes(line); //On line 39. Runs for every line in input file.
			}
		} 
		catch (FileNotFoundException e) {e.printStackTrace();} 
		catch (IOException e) {e.printStackTrace();}
		//End of file
		System.out.printf("%-26s %5s %n", "Next token is: " + "END_OF_FILE", " Next Lexeme is: " + "EOF");
		System.out.println("Lexical analysis of the program is complete!"); //Program is finished running. 
	}
	
	public static void buildLexemes(String Line)
	{
		System.out.println("Input is: " + Line);
		
		//variables that point at the current character, next character, and last character
		char character;
		char nextCharacter;
		char lastCharacter = Line.charAt(Line.length()-1);
		//accompanying tokens for the above
		String token;
		String nextToken;
		String lastToken = getToken(lastCharacter);
		String lexeme = "";
		
		for(int i=0; i<Line.length()-1; i++)
		{
			//Shifts variables to next character as i increments
			character = Line.charAt(i);
			token = getToken(character);
			nextCharacter = Line.charAt(i+1);
			nextToken = getToken(nextCharacter);
			
			//Deals with first N-1 characters, where N is the length of Line in characters
			if((nextToken == token)&&(token != "invalid"))
			{
				lexeme += character;
			}
			else if ((nextToken != token)&&(token != "invalid"))
			{
				lexeme += character;
				System.out.printf("%-26s %5s %n", "Next token is: " + token, " Next Lexeme is: " + lexeme);
				lexeme = "";
			}
			
			//deals with last character in Line (Nth)
			if((i==Line.length()-2)&&(nextToken != "invalid"))
			{
				lexeme += nextCharacter;
				System.out.printf("%-26s %5s %n", "Next token is: " + nextToken, " Next Lexeme is: " + lexeme);
				lexeme = "";
			}
			
		}
		print80Asterisks(); //Line is processed. buildLexemes() repeats with new file lines until Line 27 of this code shuts off.
	}
	
	public static String getToken(char character)
	{//Gets unicode of character, if statements return token name
		int uni_code = (int)character;
		String token;

			if (((uni_code >= 62) && (uni_code <= 92)) || ((uni_code >= 97) && (uni_code <= 122)))
				{token = "IDENT";}
			else if ((uni_code>=48) && (uni_code <= 57))
				{token = "INT_LITERAL";} 
			else if (uni_code == 61)
				{token = "ASSIGN_OP";} 
			else if (uni_code == 43)
				{token = "ADD_OP";} 
			else if (uni_code == 45)
				{token = "SUB_OP";} 
			else if (uni_code == 42)
				{token = "MULT_OP";} 
			else if (uni_code == 47)
				{token = "DIV_OP";} 
			else if (uni_code == 40)
				{token = "LEFT_PAREN";} 
			else if (uni_code == 41)
				{token = "RIGHT_PAREN";} 
			else if (uni_code == 0) //very peculiar if this would be returned.
				{token = "END_OF_FILE";}
			else {token = "invalid";} 
			return token;
	}
	public static void print80Asterisks()
	{
		System.out.println("********************************************************************************");
	}
	// 26-33 referenced from http://stackoverflow.com/questions/5868369/how-to-read-a-large-text-file-line-by-line-using-java
	// 71 and 79 use string formatting learned from https://examples.javacodegeeks.com/core-java/string/java-string-format-example
}
