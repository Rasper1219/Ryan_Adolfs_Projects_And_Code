package chocAn;
/**
 * Records Class. Responsible for creating the records.
 * @author Kaleigh Andrzejewski
 *
 */
public class Records {
	String name;
	int numID;
	String phoneNum;
	String address;
	String city;
	String state;
	int zipCode;
	ServiceRecord[] services = new ServiceRecord[100];
	int serviceNum = 0;
	String validation;

	/**
	 * Retrieve name in records.
	 * @return
	 */
	public String getName() {

		return name;
	}

	/**
	 * Retrieve numID in records.
	 * @return
	 */

	public int getNumID() {

		return numID;
	}

	/**
	 * Retrieve address in records.
	 * @return
	 */
	public String getAddress() {

		return address;
	}

	/**
	 * Retrieve city in records.
	 * @return
	 */
	public String getCity() {

		return city;
	}

	/**
	 * Retrieve state in records.
	 * @return
	 */
	public String getState() {

		return state;
	}

	/**
	 * Retrieve zip code in records.
	 * @return
	 */
	public int getZipCode() {

		return zipCode;
	}

	/**
	 * Retrieve phone number in records.
	 * @return
	 */
	public String getPhoneNum() {

		return phoneNum;
	}

	/**
	 * Retrieve services in records.
	 * @return
	 */

	public ServiceRecord[] getServices() {
		//possibly return serviceNum as well?
		serviceNum++;
		return services;
	}

	/**
	 * Retrieve validation in records.
	 * @return
	 */
	public String getValidation() {

		return validation;
	}

	/**
	 * Set name in records.
	 * @param name the name for the record
	 * @return
	 */
	public void setName(String n) {
		/*System.out.println("Setting name of record");
		System.out.println(n);*/

		name = n;
	}

	/**
	 * Set id in records.
	 * @param iD the member/provider's id#
	 */
	public void setNumID(int iD) {
		numID = iD;
	}

	/**
	 * Set phone number in records.
	 * @param num phone number for the record
	 */
	public void setPhoneNum(String num) {
		phoneNum = num;
	}

	/**
	 * Set address in records.
	 * @param userAddress address for the record
	 */
	public void setAddress(String userAddress) {
		address = userAddress;
	}

	/**
	 * Set city in records.
	 * @param userCity city for the record
	 */
	public void setCity(String userCity) {
		city = userCity;
	}

	/**
	 * Set state in records.
	 * @param userState state for the record
	 */
	public void setState(String userState) {
		state = userState;
	}

	/**
	 * Set zip code in records.
	 * @param userZip zip code for the record
	 */
	public void setZipCode(int userZip) {
		zipCode = userZip;
	}

	/**
	 * Set services in records.
	 * @param serviceName service record for the record
	 */
	public void setServices(ServiceRecord serviceName) {
		services[serviceNum] = serviceName;
		serviceNum++;
	}

	/**
	 * Set validation in records.
	 * @param memVal validation status for records
	 */
	public void setValidation(String memVal) {
		validation = memVal;
	}
}
