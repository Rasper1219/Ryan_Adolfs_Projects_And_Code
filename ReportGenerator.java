package chocAn;

import java.io.File;  // Import the File class
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors

// Timestamp utilities
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Responsible for generating reports.
 *
 * @author Max Hawkins and Kaleigh Andrzejewski
 *
 */
public class ReportGenerator {

	// File timestamp format
	private static final SimpleDateFormat date_format = new SimpleDateFormat("yyyy_MM_dd");
	// File folder for all reports
	private String reports_folder = "reports/";

	// The Provider Directory must also be created as a file. None of the files should actually
	// be sent as e-mail attachments.

	/**
	 * Returns a formatted string timestamp of the current day.
	 *
	 * @return String
	 */
	public String genTimestamp(){
		// Get current timestamp
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return date_format.format(timestamp); // Return formatted string of timestamp
	}

	/**
	 * Generate a Member report to disk.
	 * @param memArr
	 * @param proArr
	 * @param memSize
	 * @param proSize
	 * @param service
	 * @param servSize
	 * @param memID
	 */
	public void generateMemberReport(MemberRecords[] memArr, ProviderRecords[] proArr,
									int memSize, int proSize,
									ServiceRecord[] service, int servSize, int memID) {


		// Each member who has consulted a ChocAn provider during that week receives a list of
		// services provided to that member, sorted in order of service date. The report, which is also
		// sent as an e-mail attachment, includes:
		// Member name (25 characters).
		// Member number (9 digits).
		// Member street address (25 characters).
		// Member city (14 characters).
		// Member state (2 letters).
		// Member ZIP code (5 digits).
		// For each service provided, the following details are required:
		// Date of service (MM–DD–YYYY).
		// Provider name (25 characters).
		// Service name (20 characters).

		// Each member report must be written to its own file; the name of the fi le should begin with the member
		// name, followed by the date of the report. The provider reports should be handled the same
		// way.

			int flag = 0;
			for (int i=0; i<memSize; i++) {
				if (memArr[i] != null && memArr[i].memberRecord.numID == memID) {
					String file_path = reports_folder + memArr[i].memberRecord.getName() + "_" + genTimestamp() + ".txt";

					// Create new file if not already existing and catch IO errors
					try {
						File myObj = new File(file_path);
						if (myObj.createNewFile()) {
							//System.out.println("Member Report Generated: " + myObj.getName());
							System.out.println("Member Report Generated: ");
						} else {
							System.out.println("File already exists at " + file_path);
							return;
						}
						FileWriter myWriter = new FileWriter(file_path);
						myWriter.write("ChocAn Member Report");

						myWriter.write("\n");
						myWriter.write(memArr[i].memberRecord.getName());
						myWriter.write("\n");
						myWriter.write(memArr[i].memberRecord.getPhoneNum());
						myWriter.write("\n");
						myWriter.write(memArr[i].memberRecord.getAddress());
						myWriter.write("\n");
						myWriter.write(memArr[i].memberRecord.getCity());
						myWriter.write("\n");
						myWriter.write(memArr[i].memberRecord.getState());
						myWriter.write("\n");
						myWriter.write(Integer.toString(memArr[i].memberRecord.getZipCode()));
						myWriter.write("\n");
						myWriter.write(memArr[i].memberRecord.getPhoneNum());

						int num_services = memArr[i].memberRecord.serviceNum;
						ServiceRecord[] memServs = memArr[i].memberRecord.getServices();
						myWriter.write("\n\nServices: \n");
						for(int j=0; j<num_services; j++){
							myWriter.write("\n");
							myWriter.write(memServs[j].getServiceDateTime()); // TODO format properly - year should be 4 digits
							//find name
							int proID = memServs[i].getProviderNum();
							for (int k=0; i<proSize; i++) {
								if (proArr[k] != null && proArr[k].providerRecord.numID == proID) {
									myWriter.write("\n");
									myWriter.write(proArr[k].providerRecord.getName());
								}
							}
							myWriter.write("\n");
							int code = memServs[j].getServiceCode();
							String[] serviceNameArray = { "ServiceA", "ServiceB", "ServiceC", "ServiceD", "ServiceE", "ServiceF" };
							int[] serviceCodeArray = { 0001, 0010, 0011, 0100, 01001, 0110 };
							for (int l = 0; l<6; l++) {
								if (code == serviceCodeArray[l]) {
									myWriter.write(serviceNameArray[l]);
								}
							}
						}
						flag = 1;
						myWriter.close();
						break;

					} catch (IOException e) {
						System.out.println("An error occurred while generating Member report.");
						e.printStackTrace();
					}

				}
			}

			if (flag == 1) {
				System.out.println("Member Report Generated.");
			} else {
				System.out.println("Member ID not found.");
			}


	}

	/**
	 * Generate a Provider report to disk.
	 * @param memArr
	 * @param proArr
	 * @param proSize
	 * @param memSize
	 * @param service
	 * @param servSize
	 * @param proID
	 */
	public void generateProviderReport(MemberRecords[] memArr, ProviderRecords[] proArr,
									int proSize, int memSize,
									ServiceRecord[] service, int servSize, int proID) {

		int flag = 0;
		for (int i=0; i<proSize; i++) {
			if (proArr[i] != null && proArr[i].providerRecord.numID == proID) {

				String file_path = reports_folder + proArr[i].providerRecord.getName() + "_" + genTimestamp() + ".txt";
				// Create new file if not already existing and catch IO errors
				//MemberRecords[] member, ProviderRecords[] provider,int memSize, int proSize, ServiceRecord[] service, int servSize
				try {
					File myObj = new File(file_path);
					if (myObj.createNewFile()) {
						//System.out.println("Provider Report Generated: " + myObj.getName());
						System.out.println("Provider Report Generated: ");
					} else {
						System.out.println("File already exists at " + file_path);
						return;
					}
					FileWriter myWriter = new FileWriter(file_path);
					myWriter.write("ChocAn Provider Report");

			// Each provider who has billed ChocAn during that week receives a report, sent as an
			// e-mail attachment, containing the list of services he or she provided to ChocAn members.
			// To simplify the task of verifi cation, the report contains the same information as that entered
			// on the provider’s form, in the order that the data were received by the computer. At the end
			// of the report is a summary including the number of consultations with members and the
			// total fee for that week. That is, the fi elds of the report include:

			// Appendix A Term Project: Chocoholics Anonymous 629
			// Provider name (25 characters).
			// Provider number (9 digits).
			// Provider street address (25 characters).
			// Provider city (14 characters).
			// Provider state (2 letters).
			// Provider ZIP code (5 digits).
			// For each service provided, the following details are required:
			// Date of service (MM–DD–YYYY).
			// Date and time data were received by the computer (MM–DD–YYYY HH:MM:SS).
			// Member name (25 characters).
			// Member number (9 digits).
			// Service code (6 digits).
			// Fee to be paid (up to $999.99).
			// Total number of consultations with members (3 digits).
			// Total fee for week (up to $99,999.99).

			// Each member report must be written to its own file; the name of the fi le should begin with the member
			// name, followed by the date of the report. The provider reports should be handled the same
			// way.


					myWriter.write("\n");
					myWriter.write(proArr[i].providerRecord.getName());
					myWriter.write("\n");
					myWriter.write(Integer.toString(proArr[i].providerRecord.getNumID()));
					myWriter.write("\n");
					myWriter.write(proArr[i].providerRecord.getAddress());
					myWriter.write("\n");
					myWriter.write(proArr[i].providerRecord.getCity());
					myWriter.write("\n");
					myWriter.write(proArr[i].providerRecord.getState());
					myWriter.write("\n");
					myWriter.write(Integer.toString(proArr[i].providerRecord.getZipCode()));
					myWriter.write("\n");
					myWriter.write(proArr[i].providerRecord.getPhoneNum());

					myWriter.write("\n\nServices Rendered: \n");

					int num_services = proArr[i].providerRecord.serviceNum;
					int provider_fees = 0;
					ServiceRecord[] proServs = proArr[i].providerRecord.getServices();
					for(int j=0; j<num_services; j++){
						// For each service provided, the following details are required:
						// Date of service (MM–DD–YYYY).
						// Date and time data were received by the computer (MM–DD–YYYY HH:MM:SS).
						// Member name (25 characters).
						// Member number (9 digits).
						// Service code (6 digits).
						// Fee to be paid (up to $999.99).
						// Total number of consultations with members (3 digits).
						// Total fee for week (up to $99,999.99).
						myWriter.write("\n");
						myWriter.write(proServs[j].getServiceDateTime()); // TODO: MM-DD-YYYY
						myWriter.write("\n");
						myWriter.write(proServs[j].getCurDateTime()); // TODO: Should be date and time received by computer
						//find name
						int memID = proServs[j].getMemberNum();
						for (int k=0; i<memSize; i++) {
							if (memArr[k] != null && memArr[k].memberRecord.numID == memID) {
								myWriter.write("\n");
								myWriter.write(memArr[k].memberRecord.getName());
							}
						}
						myWriter.write("\n");
						myWriter.write(Integer.toString(memID));
						//myWriter.write(proServs[i].getMemberNum());
						myWriter.write("\n");
						myWriter.write(Integer.toString(proServs[j].getServiceCode()));
						myWriter.write("\n$");
						myWriter.write(Integer.toString(proServs[j].getServiceFee()));
						provider_fees = provider_fees + proServs[j].getServiceFee();
					}
					myWriter.write("\n\nTotal Services Provided: ");
					myWriter.write(Integer.toString(num_services));
					myWriter.write("\nTotal Fees Collected: $");
					myWriter.write(Integer.toString(provider_fees));

					flag = 1;
					myWriter.close();
					break;
			} catch (IOException e) {
				System.out.println("An error occurred while generating provider report.");
				e.printStackTrace();
			}
		}
	}

		if (flag == 1) {
			System.out.println("Provider Report Generated.");
		} else {
			System.out.println("Provider ID not found.");
		}

	}

	/**
	 * Generate a Summary report to disk.
	 *
	 * A summary report is given to the manager for accounts payable. The report lists every
	 * provider to be paid that week, the number of consultations each had, and his or her total
	 * fee for that week. Finally, the total number of providers who provided services, the total
	 * number of consultations, and the overall fee total are printed.
	 * @param member
	 * @param provider
	 * @param memSize
	 * @param proSize
	 * @param service
	 * @param servSize
	 */
	public void generateSummaryReport(MemberRecords[] member, ProviderRecords[] provider,
									int memSize, int proSize,
									ServiceRecord[] service, int servSize) {
		String file_path = reports_folder + "summaryReport_" + genTimestamp() + ".txt";

		// Create new file if not already existing and catch IO errors
		try {
			File myObj = new File(file_path);
			if (myObj.createNewFile()) {
				System.out.println("Summary Report Generated: " + myObj.getName());
			} else {
				System.out.println("File already exists at " + file_path);
				return;
			}
			FileWriter myWriter = new FileWriter(file_path);
			myWriter.write("ChocAn Summary Report \n(Provider Name - Number of Services Provided - Total Fees)\n");

			// Sum of consultations and overall fees
			int total_consults = 0;
			int total_fees = 0;
			// Iterate through providers
			for(int i=0; i<proSize; i++){
				System.out.print("Provider:");
				System.out.println(i);
				ProviderRecords cur_provider = provider[i];
				System.out.println(cur_provider.providerRecord.getName());
				System.out.println(cur_provider.providerRecord.getNumID());


				// Iterate through services of the provider
				int num_services = cur_provider.providerRecord.serviceNum;
				int provider_fees = 0;
				ServiceRecord[] proServArr = cur_provider.providerRecord.getServices();
				for(int j=0; j<num_services; j++){
					// Update the total fees
					//provider_fees = provider_fees + cur_provider.providerRecord.getServices()[j].getServiceFee();
					if (proServArr[j] != null) {
						provider_fees = provider_fees + proServArr[j].getServiceFee();
					}
				}

				// Write provider summary to file
				myWriter.write("\n");
				// myWriter.write(cur_provider.providerRecord.getName()); // TODO: Get this not to be null
				myWriter.write(" - ");
				myWriter.write(Integer.toString(num_services));
				myWriter.write(" - $");
				myWriter.write(Integer.toString(provider_fees));

				// Update number of total consults and total fees
				total_consults = total_consults + num_services;
				total_fees = total_fees + provider_fees;
			}

			// Write summary total of services provided and total fees
			myWriter.write("\n\nTotal Services Provided: ");
			myWriter.write(Integer.toString(total_consults));
			myWriter.write("\nTotal Fees Collected: $");
			myWriter.write(Integer.toString(total_fees));

			myWriter.close();
		} catch (IOException e) {
			System.out.println("An error occurred while generating summary report.");
			e.printStackTrace();
		}

	}

	/**
	 * Generate an Electronic Fund Transfer report to disk.
	 * @param member
	 * @param provider
	 * @param memSize
	 * @param proSize
	 * @param service
	 * @param servSize
	 */
	public void generateEFTReport(MemberRecords[] member, ProviderRecords[] provider,
								int memSize, int proSize,
								ServiceRecord[] service, int servSize) {
		String file_path = reports_folder + "ChocAnEFTReport_" + genTimestamp() + ".txt";

		// Create new file if not already existing and catch IO errors
		try {
			File myObj = new File(file_path);
			if (myObj.createNewFile()) {
				System.out.println("EFT Report Generated: " + myObj.getName());
			} else {
				System.out.println("File already exists at " + file_path);
				return;
			}
			FileWriter myWriter = new FileWriter(file_path);
			myWriter.write("ChocAn EFT Report\n(Provider Name - Number of Services Provided - Total Fees)\n");

			// A record consisting of electronic funds transfer (EFT) data is then written to a disk;
			// banking computers will later ensure that each provider’s bank account is credited with the
			// appropriate amount.

			// As for the EFT data, all that is required is that a file be set up
			// containing the provider name, provider number, and the amount to be transferred.

			for(int i=0; i<proSize; i++){
				System.out.print("Provider:");
				System.out.println(i);
				ProviderRecords cur_provider = provider[i];
				System.out.println(cur_provider.providerRecord.name);

				// Iterate through services of the provider
				int num_services = cur_provider.providerRecord.serviceNum;
				int provider_fees = 0;
				for(int j=0; j<num_services; j++){
					// Update the total fees
					provider_fees = provider_fees + cur_provider.providerRecord.getServices()[j].getServiceFee();
				}

				// Write provider summary to file
				myWriter.write("\n");
				// myWriter.write(cur_provider.providerRecord.getName()); // TODO: Get this not to be null
				myWriter.write(" - ");
				myWriter.write(Integer.toString(num_services));
				myWriter.write(" - $");
				myWriter.write(Integer.toString(provider_fees));

			}

			myWriter.close();
		} catch (IOException e) {
			System.out.println("An error occurred while generating EFT report.");
			e.printStackTrace();
		}

	}
}
