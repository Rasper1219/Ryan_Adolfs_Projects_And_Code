package chocAn;

/**
 * The OperatorInterface class. Provides an interface for the operator to interact with.
 * @author Kaleigh Andrzejewski
 *
 */
public class OperatorInterface {

	//String userID;
	//String password;
	/** 
	 * Function to update a member in the records
	 * @param member what member the action is being done to
	 * @param action what update the action is being done
	 * @param edit which part of the record is being edited
	 * @param memArr the array of MemberRecords for persistence
	 * @param memSize size of memArr
	 * @return
	 */

	public boolean updateMember(Records member, int action, int edit, MemberRecords[] memArr, int memSize) {
		int flag = 0;
		if (action == 1) { //add member
			MemberRecords newMem = new MemberRecords();
			newMem.memberRecord.setName(member.name);
			newMem.memberRecord.setAddress(member.address);
			newMem.memberRecord.setCity(member.city);
			newMem.memberRecord.setNumID(member.numID);
			newMem.memberRecord.setPhoneNum(member.phoneNum);
			newMem.memberRecord.setState(member.state);
			newMem.memberRecord.setZipCode(member.zipCode);
			//newMem.memberRecord.setServices(member.services[0]);
			newMem.memberRecord.setValidation("Valid");
			memArr[memSize] = newMem;
			memSize++;
			System.out.println("Member Added.");
			flag = 1;
			//add to an array
		} else if (action == 2) {
			 //delete member
			for (int i=0; i<memSize; i++) {
				if (memArr[i] != null && memArr[i].memberRecord.numID == member.numID) {
					memArr[i] = null;
					flag = 1;
					break;
				}
			}
			if (flag == 1) {
				System.out.println("Member Deleted.");
			} else {
				System.out.println("Member ID not found.");
			}
		} else if (action == 3) { //edit member
			//while (array member.name != member.name) then edit what needs to be edit
			for (int i=0; i<memSize; i++) {
				if (memArr[i] != null && memArr[i].memberRecord.numID == member.numID) {
					if (edit == 1) {
						memArr[i].memberRecord.setName(member.name);
					} else if (edit == 2) {
						memArr[i].memberRecord.setNumID(member.numID);
					} else if (edit == 3) { 
						memArr[i].memberRecord.setPhoneNum(member.phoneNum);
					} else if (edit == 4) {
						memArr[i].memberRecord.setAddress(member.address);
					} else if (edit == 5) {
						memArr[i].memberRecord.setCity(member.city);
					} else if (edit == 6) {
						memArr[i].memberRecord.setState(member.state);
					} else if (edit == 7) {
						memArr[i].memberRecord.setZipCode(member.zipCode);
					} else if (edit == 8){
						memArr[i].memberRecord.setServices(member.services[0]);
					} else if (edit == 9) {
						memArr[i].memberRecord.setValidation(member.validation);
					}
					flag = 1;
					break;
				}
			}
			if (flag == 1) {
				System.out.println("Member Edited.");
			} else {
				System.out.println("Member ID not found.");
			}
		} else {
			return false; //incorrect number inserted
		}
		
		if (flag == 1) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Function to update a provider in the records
	 * @param provider what provider the action is being done to
	 * @param action what update the action is being done
	 * @param edit which part of the record is being edited
	 * @param proArr the array of ProviderRecords for persistence
	 * @param proSize size of proArr
	 * @return
	 */
	public boolean updateProvider(Records provider, int action, int edit, ProviderRecords[] proArr, int proSize) {
		int flag = 0; //0 for not found, 1 for found
		if (action == 1) { //add member
			ProviderRecords newPro = new ProviderRecords();
			newPro.providerRecord.setName(provider.name);
			newPro.providerRecord.setAddress(provider.address);
			newPro.providerRecord.setCity(provider.city);
			newPro.providerRecord.setNumID(provider.numID);
			newPro.providerRecord.setPhoneNum(provider.phoneNum);
			newPro.providerRecord.setState(provider.state);
			newPro.providerRecord.setZipCode(provider.zipCode);
			//newPro.providerRecord.setServices(provider.services[0]);
			proArr[proSize] = newPro;
			proSize++;
			System.out.println("Provider Added.");
			flag = 1;
			//add to an array
		} else if (action == 2) { //delete provider
			for (int i=0; i<proSize; i++) {
				if (proArr[i] != null && proArr[i].providerRecord.numID == provider.numID) {
					proArr[i] = null;
					flag = 1;
					break;
				}
			}
			if (flag == 1) {
				System.out.println("Provider Deleted.");
			} else {
				System.out.println("Provider ID not found.");
			}
		} else if (action == 3) { //edit member
			//while (array member.name != member.name) then edit what needs to be edit
			for (int i=0; i<proSize; i++) {
				if (proArr[i] != null && proArr[i].providerRecord.numID == provider.numID) {
					if (edit == 1) {
						proArr[i].providerRecord.setName(provider.name);
					} else if (edit == 2) {
						proArr[i].providerRecord.setNumID(provider.numID);
					} else if (edit == 3) {
						proArr[i].providerRecord.setPhoneNum(provider.phoneNum);
					} else if (edit == 4) {
						proArr[i].providerRecord.setAddress(provider.address);
					} else if (edit == 5) {
						proArr[i].providerRecord.setCity(provider.city);
					} else if (edit == 6) {
						proArr[i].providerRecord.setState(provider.state);
					} else if (edit == 7) {
						proArr[i].providerRecord.setZipCode(provider.zipCode);
					} else if (edit == 8){
						proArr[i].providerRecord.setServices(provider.services[0]);
					}
					flag = 1;
				}
			}
			if (flag == 1) {
				System.out.println("Provider Edited.");
			} else {
				System.out.println("Provider ID not found.");
			}
			
		} else {
			return false; //incorrect number inserted
		}
		if (flag == 1) {
			return true;
		} else {
			return false;
		}
	}
}
