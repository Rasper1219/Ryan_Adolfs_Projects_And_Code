package chocAn;

/**
 * The ProviderInterface class. Provides an interface for the provider to interact with.
 * @author Derek Mohr
 *
 */

public class ProviderInterface {
	String userID;
	String password;


	/*
	* Function for calling the validate member function in the main terminal.
	* @param code - code for the member being validated
	* @param memArr - array containing all members and their corresponding information
	* @param memSize - size of the member array
	* @return
	*/
	public String callValidateMember(int code, MemberRecords[] memArr, int memSize) {
		String status = " ";
		int flag = 0;
		for (int i=0; i<memSize; i++) {
			if (memArr[i] != null && memArr[i].memberRecord.numID == code) {
				status = memArr[i].memberRecord.getValidation();
				flag = 1;
				break;
			}
		}
		if (flag == 1) {
			return status;
		} else {
			return "Unfound";
		}
	}


	/*
	* Function for retrieving provider directory class.
	* @return
	*/
	public ProviderDirectory requestProviderDirectory() {
		ProviderDirectory directory = new ProviderDirectory();

		return directory;
	}

	/*
	* Function for billing ChocAn; builds service record containing information about the provided service
	* @param rec - the record that contains the information about the provided service
	* @return
	*/
	public void billChocAn(ServiceRecord rec) {
		if(rec.serviceCode == 0001 || rec.serviceCode == 0010 || rec.serviceCode == 0011 || rec.serviceCode == 0100 || rec.serviceCode == 01001 || rec.serviceCode == 0110) { 
			MainTerminal MT = new MainTerminal(); 
			ServiceRecord[] recArray = MT.serviceRecordArr;
			int size = MT.servRecSize;
			recArray[size] = rec; // adds record to end of service record array
			size++;
			MT.servRecSize = size;
			MT.serviceRecordArr = recArray;

			// write record to disc (show onscreen)
			System.out.println(MT.serviceRecordArr[size-1].currentDateTime);
			System.out.println(MT.serviceRecordArr[size-1].serviceDateTime);
			System.out.println(MT.serviceRecordArr[size-1].providerNum);
			System.out.println(MT.serviceRecordArr[size-1].memberNum);
			System.out.println(MT.serviceRecordArr[size-1].serviceCode);
			System.out.println("Provider comments: " + MT.serviceRecordArr[size-1].providerComments);
			System.out.println("Fee to be Paid: $" + rec.serviceFee); // display fee to be paid onscreen
		}
		
		// if given an invalid code, it won't be added to the array
		
	}
}
