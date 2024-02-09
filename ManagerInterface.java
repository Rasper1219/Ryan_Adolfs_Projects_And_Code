package chocAn;

/** Responsible for interfacing between a manager and report generator
 *
 * @author Max Hawkins
 *
 */
public class ManagerInterface {


	/**
	 * @param userID: Holds the user's ID
	 * @param password: Holds the user's passsword
	 * @return boolean: true or false
	 */
	public boolean validateManager(String userID, String password) {


		return true;
	}



	/**
	 * Request a report of reportType to be generated and written to disk. Return success
	 * or failure to write.
	 * @param reportType
	 * @param member
	 * @param provider
	 * @param memSize
	 * @param proSize
	 * @param service
	 * @param servSize
	 * @param id
	 * @return
	 *
	 * @param reportType: Holds the type of report
	 * @return boolean: True or false
	 */
	public boolean requestReport(String reportType,
								MemberRecords[] member, ProviderRecords[] provider,
								int memSize, int proSize,
								ServiceRecord[] service, int servSize, int id) {
		// ReportGenerator class instance to generate reports
		ReportGenerator generator = new ReportGenerator();

		/*System.out.println("Sizes:");
		System.out.println(memSize);
		System.out.println(proSize);
		System.out.println(servSize);*/

		// Generate given report from user indicated selection
		switch (reportType) {
            case "1":
				System.out.println("\nGenerating Provider Report...");
				generator.generateProviderReport(member, provider, memSize, proSize, service, servSize, id);
				break;
			case "2":
				System.out.println("\nGenerating Member Report...");
				generator.generateMemberReport(member, provider, memSize, proSize, service, servSize, id);
				break;
			case "3":
				System.out.println("\nGenerating Summary Report...");
				generator.generateSummaryReport(member, provider, memSize, proSize, service, servSize);
				break;
			case "4":
				System.out.println("\nGenerating ETF Report...");
				generator.generateEFTReport(member, provider, memSize, proSize, service, servSize);
				break;
			default:
				// Invalid report selection
				System.out.println("Invalid report selection input. Try again.");
				return false;
		}
		return true;
	}
}
