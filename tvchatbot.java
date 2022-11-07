import java.util.Scanner;
/**
 * A program to carry on conversations with a human user.
 * This version:
 *<ul><li>
 * 		Uses advanced search for keywords 
 *</li><li>
 * 		Will transform statements as well as react to keywords
 *</li></ul>
 * @author Laurie White
 * @version April 2012
 *
 */
public class tvChatBot
{
	/**
	 * Get a default greeting 	
	 * @return a greeting
	 */	

	 private String name = "";
	
	public String Greeting()
	{
		System.out.println("Hello my name is Joe, let's talk. What's your name? (To exit respond with Bye)");
		Scanner in = new Scanner (System.in);
		String name = in.nextLine();
		System.out.println("Hello " + name + "! How are you doing?");
		String respond = in.nextLine();
		if (findKeyword(respond, "good") >= 0 || (findKeyword(respond, "great") >= 0))
			System.out.println("Great! I myself am pretty good!");
		else if (findKeyword(respond, "bad") >= 0 || findKeyword(respond, "terrible") >= 0) {
			System.out.println("Sorry to hear that. I myself am pretty okay.");
		}
		else {
			System.out.println("Okay, I myself am pretty okay.");
		}
		System.out.println("What would you like to talk about? I personally love TV shows!");
		return name;
	}
	
	/**
	 * Gives a response to a user statement
	 * 
	 * @param statement
	 *            the user statement
	 * @return a response based on the rules given
	 */
	public String getResponse(String statement, String name)
	{
		String response = "";
		if (statement.length() == 0)
		{
			response = name + " say something, please.";
		}

		else if (findKeyword(statement, "no") >= 0 || findKeyword(statement, "meh") >= 0)
		{
			response = "Why are you so negative " + name + "?";
		}
		else if (findKeyword(statement, "yes") >= 0)
		{
			response = "Sounds good " + name + "!";
		}

		else if (findKeyword(statement, "mother") >= 0
				|| findKeyword(statement, "father") >= 0
				|| findKeyword(statement, "sister") >= 0
				|| findKeyword(statement, "brother") >= 0)
		{
			response =  "Very cool! Tell me more about your family.";
		}

		// Responses which require transformations
		else if (findKeyword(statement, "I want to", 0) >= 0)
		{
			response = transformIWantToStatement(statement);
		} 
		else if (findKeyword(statement, "hi") >= 0 || findKeyword(statement, "hello") >= 0) {
			response = "Hello again " + name;
		}
		else if (findKeyword(statement, "television") >= 0 || findKeyword(statement, "TV") >= 0) 
		{
			response = TVShow(name);
		}
		else
		{
			// Look for a two word (you <something> me)
			// pattern
			int psn = findKeyword(statement, "you", 0);

			if (psn >= 0
					&& findKeyword(statement, "me", psn) >= 0)
			{
				response = transformYouMeStatement(statement);
			}
			else
			{
				response = getRandomResponse();
			}
		}
		return response;
	}
	
	/**
	 * Take a statement with "I want to <something>." and transform it into 
	 * "What would it mean to <something>?"
	 * @param statement the user statement, assumed to contain "I want to"
	 * @return the transformed statement
	 */
	private String transformIWantToStatement(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		int psn = findKeyword (statement, "I want to", 0);
		String restOfStatement = statement.substring(psn + 9).trim();
		Scanner in = new Scanner (System.in);
		System.out.println("What would it mean to " + restOfStatement + "?");
		String ask = in.nextLine();
		return "Oh okay that's cool!";
	}

	
	
	/**
	 * Take a statement with "you <something> me" and transform it into 
	 * "What makes you think that I <something> you?"
	 * @param statement the user statement, assumed to contain "you" followed by "me"
	 * @return the transformed statement
	 */
	private String transformYouMeStatement(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		
		int psnOfYou = findKeyword (statement, "you", 0);
		int psnOfMe = findKeyword (statement, "me", psnOfYou + 3);
		
		String restOfStatement = statement.substring(psnOfYou + 3, psnOfMe).trim();
		System.out.println("What makes you think that I " + restOfStatement + " you?");
		Scanner in = new Scanner (System.in);
		String ask = in.nextLine();
		return "Oh that makes sense";
	}
	
	

	
	
	/**
	 * Search for one word in phrase.  The search is not case sensitive.
	 * This method will check that the given goal is not a substring of a longer string
	 * (so, for example, "I know" does not contain "no").  
	 * @param statement the string to search
	 * @param goal the string to search for
	 * @param startPos the character of the string to begin the search at
	 * @return the index of the first occurrence of goal in statement or -1 if it's not found
	 */
	private int findKeyword(String statement, String goal, int startPos)
	{
		String phrase = statement.trim();
		//  The only change to incorporate the startPos is in the line below
		int psn = phrase.toLowerCase().indexOf(goal.toLowerCase(), startPos);
		
		//  Refinement--make sure the goal isn't part of a word 
		while (psn >= 0) 
		{
			//  Find the string of length 1 before and after the word
			String before = " ", after = " "; 
			if (psn > 0)
			{
				before = phrase.substring (psn - 1, psn).toLowerCase();
			}
			if (psn + goal.length() < phrase.length())
			{
				after = phrase.substring(psn + goal.length(), psn + goal.length() + 1).toLowerCase();
			}
			
			//  If before and after aren't letters, we've found the word
			if (((before.compareTo ("a") < 0 ) || (before.compareTo("z") > 0))  //  before is not a letter
					&& ((after.compareTo ("a") < 0 ) || (after.compareTo("z") > 0)))
			{
				return psn;
			}
			
			//  The last position didn't work, so let's find the next, if there is one.
			psn = phrase.indexOf(goal.toLowerCase(), psn + 1);
			
		}
		
		return -1;
	}
	
	/**
	 * Search for one word in phrase.  The search is not case sensitive.
	 * This method will check that the given goal is not a substring of a longer string
	 * (so, for example, "I know" does not contain "no").  The search begins at the beginning of the string.  
	 * @param statement the string to search
	 * @param goal the string to search for
	 * @return the index of the first occurrence of goal in statement or -1 if it's not found
	 */
	private int findKeyword(String statement, String goal)
	{
		return findKeyword (statement, goal, 0);
	}
	


	/**
	 * Pick a default response to use if nothing else fits.
	 * @return a non-committal string
	 */
	private String getRandomResponse()
	{
		final int NUMBER_OF_RESPONSES = 4;
		double r = Math.random();
		int whichResponse = (int)(r * NUMBER_OF_RESPONSES);
		String response = "";
		
		if (whichResponse == 0)
		{
			response = "Interesting, tell me more.";
		}
		else if (whichResponse == 1)
		{
			response = "Mhm";
		}
		else if (whichResponse == 2)
		{
			response = "Do you really think so?";
		}
		else if (whichResponse == 3)
		{
			response = "Huh? How about we talk about TV?";
		}
		else if (whichResponse == 4)
		{
			response = "What do you mean? How about we talk about TV?";
		}

		return response;
	}

	private String TVShow(String name) {
		Scanner in = new Scanner (System.in);
		System.out.println("Great! Let's talk about TV, what's your favorite TV show " + name + "?");
		String show = in.nextLine();
		if (findKeyword(show, "show") >= 0) 
		{
			int psnOfShow = findKeyword (show, "show", 0);
			show = show.substring(psnOfShow + 4).trim();
		}
		System.out.println(show + " sounds cool! I've never heard of it. Would you recommend I watch it?");
		String answer = in.nextLine();
		if (findKeyword(answer, "no") >= 0)
		{
			System.out.print("Good to know, I probably won't watch it.");
		}
		else if (findKeyword(answer, "yes") >= 0) {
			System.out.print("Great! I start binge watching it tonight!");
		}
		System.out.println(" My favorite TV Show is Business Proposal, it's a really funny Korean Drama. Have you heard of it?");
		answer = in.nextLine();
		if (findKeyword(answer, "no") >= 0)
		{
			System.out.print("Well you should check it out!");
		}
		else if (findKeyword(answer, "yes") >= 0) {
			System.out.print("Amazing! We should watch it together sometime!");
		}
		return " Nice talking about TV shows " + name + "!";
	}

}
