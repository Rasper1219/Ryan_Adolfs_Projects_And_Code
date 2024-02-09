package chocAn;

import java.util.HashMap; // import the HashMap class
import java.util.Scanner;  // Import the Scanner class

// Types of system users
enum UserType {
	INVALID, // For invalid credentials
	MEMBER,
	PROVIDER,
	MANAGER,
	OPERATOR,
	WEEKLYTIMER,
  }
/**
 * Class that holds the main terminal
 * @author Max Hawkins
 *
 */

public class MainTerminal {

	// TODO: Put these in some class to make them more "secure" to mimic database query
	// Structure to contain mapping from userIDs to user type
	HashMap<String, UserType> idToUserType = new HashMap<String, UserType>();
	// Structure to contain mapping from userIDs to passwords
	// TODO: Combine these two into one to make things simpler
	HashMap<String, String> idToPassword = new HashMap<String, String>();
	public MemberRecords[] memRecordArr = new MemberRecords[100]; //arrays to store member and provider records
	public ProviderRecords[] proRecordArr = new ProviderRecords[100];
	public ServiceRecord[] serviceRecordArr = new ServiceRecord[100]; //stores service records
	public int memRecSize = 0;
	public int proRecSize = 0;
	public int servRecSize = 0;

	/**
	 * Validate all non-member users and return corresponding user type.
	 *
	 * @param userID: Holds the user's ID
	 * @param password: Holds the user's password
	 * @return UserType: Holds the user's type
	 */
	public UserType validateNonMemberUser(String userID, String password){
		// Check that the userID exists in database
		if(idToPassword.containsKey(userID) == false){
			System.out.println("Error: userID not found in user database.");
			return UserType.INVALID;
		}
		// If valid password
		if(idToPassword.get(userID).equals(password)){
			return idToUserType.get(userID);
		} // Invalid password
		else{
			System.out.println("Invalid userID and password combination.");
			return UserType.INVALID;
		}
	}


	/**
	 * @param code:
	 * @return String: 
	 */

	/**
	 *
	 * @author Ryan Adolfs
	 *
	 */
	public boolean validateMember(int memID, MemberRecords[] memArr, int memSize) {
		String reason = null;

		for (int i = 0; i < memSize; i++) {
			if (memID == memArr[i].memberRecord.getNumID()) {
				if (memArr[i].memberRecord.getValidation() == "Valid") {

					System.out.println("Member is Valid");
					return true;
//					System.out.println(memArr[i].memberRecord.getName());
//					System.out.println(memArr[i].memberRecord.getNumID());
//					System.out.println(memArr[i].memberRecord.getAddress());
//					System.out.println(memArr[i].memberRecord.getCity());
//					System.out.println(memArr[i].memberRecord.getState());
//					System.out.println(memArr[i].memberRecord.getServices());
				}
				else {
					System.out.println("Invalid Member: Member is invalid.");
					return false;
				}
			}
			else {
				System.out.println("Invalid Member: numID not found.");
				return false;
			}
		}
		return false;
	}


	/**
	 * Main entry point to the ChocAn system.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		// Startup message
		System.out.println("ChocAn System Startup...");
		Scanner input = new Scanner(System.in);  // Create a Scanner object for user input

		// Initialize user database with dummy data for testing/demoing
		MainTerminal term = new MainTerminal();
		// // mscott - 1234 - Manager
		term.idToPassword.put("mscott","1234");
		term.idToUserType.put("mscott",UserType.MANAGER);

		// Endless while loop for continual operation
		while(true){
		// User validation
		UserType validation_response = UserType.INVALID;
		while(validation_response == UserType.INVALID){
			System.out.println("\n--- User Login ---");
			System.out.println("\nEnter userID:");
			String userID = input.nextLine();  // Read user input
			System.out.println("Enter password:");
			String password = input.nextLine();  // Read user input

			validation_response = term.validateNonMemberUser(userID, password);

			// If invalid response, query for desire to continue/quit
			if(validation_response == UserType.INVALID){
				String continue_input = "0"; // Default to invalid input
				System.out.println("User validation failed.");

				// Query for valid continue response
				while(continue_input == "0"){
					System.out.println("1 - Try again");
					System.out.println("2 - Exit system");
					continue_input = input.nextLine();  // Read user input
					System.out.println("You entered: ");
					System.out.println(continue_input);
					// If exit
					if(continue_input.equals("2")){
						System.out.println("\nExiting ChocAn system...");
						input.close();
						return;
					}// If invalid input, keep querying for valid input
					else if(!continue_input.equals("1")){
						System.out.println("\nInvalid response. Please only enter 1 or 2.");
						continue_input = "0";
					}
				}
			}
		}

		// Enter appropiate menu for valid userType
		switch (validation_response) {
            case PROVIDER:
					System.out.println("\n--- Provider Menu ---");
                    break;
			case OPERATOR:
					System.out.println("\n--- Operator Menu ---");
                    break;
			case MANAGER:
					ManagerInterface man_interface = new ManagerInterface();
					String report_choice = "0";
					int id = 1320;
					while(!report_choice.equals("5")){
						System.out.println("\n--- Manager Menu ---");
						System.out.println("1 - Provider Report");
						System.out.println("2 - Member Report");
						System.out.println("3 - Summary Report");
						System.out.println("4 - ETF Report");
						System.out.println("5 - Exit Menu and Sign Out");
						System.out.println("\nChoose which report to generate:");

						report_choice = input.nextLine();  // Read user input
						// If not request exit, request report
						if(!report_choice.equals("5")){
							man_interface.requestReport(report_choice, term.memRecordArr, term.proRecordArr, term.memRecSize, term.proRecSize, term.serviceRecordArr, term.servRecSize, id);
						}
					}
                    break;
			// Invalid/unreachable path - something bad happened
            default:
				System.out.println("Invalid response state reached. Exiting system...");
				input.close(); // Free the scanner
				return;
        }

	} // End endless while loop

	} // End main function
} // End MainTerminal class
