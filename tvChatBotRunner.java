import java.util.Scanner;

/**
 * A simple class to run the Magpie class.
 * @author Laurie White
 * @version April 2012
 */
public class tvChatBotRunner
{

	/**
	 * Create a Magpie, give it user input, and print its replies.
	 */
	public static void main(String[] args)
	{
		tvChatBot chat = new tvChatBot();
		
		String name = chat.Greeting();
		Scanner in = new Scanner (System.in);
		String statement = in.nextLine();
		
		while (!statement.equals("Bye"))
		{
			System.out.println (chat.getResponse(statement, name));
			statement = in.nextLine();
		}
	}

}
