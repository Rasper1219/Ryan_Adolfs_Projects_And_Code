package chocAn;
/**
 * Class to create Service Record
 * @author Kaleigh Andrzejewski
 */
// Timestamp utilities
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class ServiceRecord {
	String serviceDateTime;
	int serviceCode;
	int providerNum;
	int memberNum;
	int serviceFee;
	String currentDateTime;
	String providerComments; // provider is given the option to add comments


	// File timestamp format
	private static final SimpleDateFormat date_format = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");

	public ServiceRecord(){
		super();
		//this.serviceDateTime = getCurDateTime();
	}

	//Setters ---------

	public void setServiceCode(int code) {
		this.serviceCode = code;
	}

	public void setProviderNum(int num) {
		this.providerNum = num;
	}

	public void setMemberNum(int num) {
		this.memberNum = num;
	}

	public void setServiceFee(int fee) {
		this.serviceFee = fee;
	}

	public void setComments(String comments) {
		this.providerComments = comments;
	}
	
	public void setCurrentDateTime(String date) {
		this.currentDateTime = date;
	}
	
	public void setServiceDateTime(String date) {
		this.serviceDateTime = date;
	}

	// Getters -------

	public String getCurDateTime() {
		// Get current timestamp
		//date_format. timestamp = new Timestamp(System.currentTimeMillis());
		//return date_format.format(timestamp); // Return formatted string of timestamp
		return serviceDateTime;
	}

	public int getServiceCode() {

		return serviceCode;
	}

	public int getProviderNum() {

		return providerNum;
	}

	public int getMemberNum() {

		return memberNum;
	}

	public String getServiceDateTime() {

		return serviceDateTime;
	}

	public int getServiceFee() {

		return serviceFee;
	}

	public String getComments() {

		return providerComments;
	}
}
