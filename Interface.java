package chocAn;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.jgoodies.forms.factories.DefaultComponentFactory;

public class Interface {
	JLabel loginFailMessage;
	private JFrame frame;
	private JTextField userIdInput;
	private JTextField passwordInput;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interface window = new Interface();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Interface() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton loginButton = new JButton("Log In");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Scanner input = new Scanner(System.in);  // Create a Scanner object for user input
				MainTerminal term = new MainTerminal();
				//initializing things in array
				MemberRecords newRec = new MemberRecords();
				ServiceRecord newServ = new ServiceRecord();
				newServ.setServiceCode(000001);
				newServ.setServiceFee(125);
				newServ.setMemberNum(1320);
				newServ.setProviderNum(4931);
				newServ.setComments("N/A");
				newServ.setCurrentDateTime("11-30-22 07:30:12");
				newServ.setServiceDateTime("11-30-22");
				newRec.memberRecord.setNumID(1320);
				newRec.memberRecord.setName("Bryce Young");
				newRec.memberRecord.setPhoneNum("7168984231");
				newRec.memberRecord.setAddress("1831 University Station");
				newRec.memberRecord.setCity("Tuscaloosa");
				newRec.memberRecord.setState("AL");
				newRec.memberRecord.setZipCode(35487);
				newRec.memberRecord.setServices(newServ);
				newRec.memberRecord.setValidation("Valid");
				term.memRecordArr[0] = newRec;
				MemberRecords newRec1 = new MemberRecords();
				newRec1.memberRecord.setName("Josh Allen");
				newRec1.memberRecord.setNumID(5892);
				newRec1.memberRecord.setPhoneNum("7168984231");
				newRec1.memberRecord.setAddress("1831 University Station");
				newRec1.memberRecord.setCity("Tuscaloosa");
				newRec1.memberRecord.setState("AL");
				newRec1.memberRecord.setZipCode(35487);
				newRec1.memberRecord.setValidation("Valid");
				term.memRecordArr[1] = newRec1;
				MemberRecords newRec2 = new MemberRecords();
				newRec2.memberRecord.setName("Patrick Mahomes");
				newRec2.memberRecord.setNumID(4931);
				newRec2.memberRecord.setPhoneNum("7168984231");
				newRec2.memberRecord.setAddress("1831 University Station");
				newRec2.memberRecord.setCity("Tuscaloosa");
				newRec2.memberRecord.setState("AL");
				newRec2.memberRecord.setZipCode(35487);
				newRec2.memberRecord.setValidation("Valid");
				term.memRecordArr[2] = newRec2;
				term.memRecSize = 3;
				ProviderRecords newRec5 = new ProviderRecords();
				newRec5.providerRecord.setNumID(4931);
				newRec5.providerRecord.setName("Nick Saban");
				newRec5.providerRecord.setPhoneNum("9092367885");
				newRec5.providerRecord.setAddress("401 Middle Creek Rd");
				newRec5.providerRecord.setCity("Tuscaloosa");
				newRec5.providerRecord.setState("AL");
				newRec5.providerRecord.setZipCode(35487);
				newRec5.providerRecord.setServices(newServ);
				term.proRecordArr[0] = newRec5;
				newServ.setServiceFee(98);
				ProviderRecords newRec3 = new ProviderRecords();
				newRec3.providerRecord.setName("Gumby Gumberton");
				newRec3.providerRecord.setNumID(1320);
				newRec3.providerRecord.setPhoneNum("9092367885");
				newRec3.providerRecord.setAddress("401 Middle Creek Rd");
				newRec3.providerRecord.setCity("Tuscaloosa");
				newRec3.providerRecord.setState("AL");
				newRec3.providerRecord.setZipCode(35487);
				newRec3.providerRecord.setServices(newServ);
				term.proRecordArr[1] = newRec3;
				newServ.setServiceFee(103);
				ProviderRecords newRec4 = new ProviderRecords();
				newRec4.providerRecord.setName("Tim Cook");
				newRec4.providerRecord.setNumID(5892);
				newRec4.providerRecord.setPhoneNum("9092367885");
				newRec4.providerRecord.setAddress("401 Middle Creek Rd");
				newRec4.providerRecord.setCity("Tuscaloosa");
				newRec4.providerRecord.setState("AL");
				newRec4.providerRecord.setZipCode(35487);
				newRec4.providerRecord.setServices(newServ);
				term.proRecordArr[2] = newRec4;
				term.proRecSize = 3;
				

				term.idToPassword.put("mscott","1234");
				term.idToUserType.put("mscott",UserType.MANAGER);
				term.idToPassword.put("trina","1234");
				term.idToUserType.put("trina",UserType.PROVIDER);
				term.idToPassword.put("rsmith","1234");
				term.idToUserType.put("rsmith",UserType.OPERATOR);
				term.idToPassword.put("timer","1234");
				term.idToUserType.put("timer",UserType.WEEKLYTIMER);
				UserType validation_response = UserType.INVALID;
				int error = 0;
				int flag = 0;

				while(validation_response == UserType.INVALID){
					//System.out.println("\n--- User Login ---");
					//System.out.println("\nEnter userID:");
					String userID = userIdInput.getText();  // Read user input
					//System.out.println("Enter password:");
					String password = passwordInput.getText();  // Read user input

					validation_response = term.validateNonMemberUser(userID, password);

					// If invalid response, query for desire to continue/quit
					if(validation_response == UserType.INVALID){
						//JOptionPane.showMessageDialog(null, "User validation failed");
						if (error >= 3) {
							frame.dispose();
							ExitWindow exit = new ExitWindow();
							exit.setVisible(true);
							input.close(); // Free the scanner
						}
						if (flag == 0) {
							loginFailMessage.setText("User validation failed, try again.");
							error = error + 1;
							flag = 1;
						}
					}
				}

				switch (validation_response) {
	            case PROVIDER:
	            		frame.dispose();
	            		ProviderInterfaceWindow providerWindow = new ProviderInterfaceWindow(term.memRecordArr, term.memRecSize, term.serviceRecordArr, term.servRecSize);
	            		providerWindow.setVisible(true);
						System.out.println("\n--- Provider Menu ---");
	                    break;
				case OPERATOR:
						frame.dispose();
						OperatorInterfaceWindow operatorWindow = new OperatorInterfaceWindow(term.memRecordArr, term.proRecordArr, term.memRecSize, term.proRecSize, 0);
						operatorWindow.setVisible(true);
						System.out.println("\n--- Operator Menu ---");
	                    break;
				case MANAGER:
						frame.dispose();
						ManagerInterfaceWindow managerWindow = new ManagerInterfaceWindow(term.memRecordArr, term.proRecordArr, term.memRecSize, term.proRecSize, term.serviceRecordArr, term.servRecSize);
						managerWindow.setVisible(true);
						System.out.println("\n--- Manager Menu ---");
						break;
				case WEEKLYTIMER:
						frame.dispose();
						WeeklyTimerWindow weeklyTimerWindow = new WeeklyTimerWindow(term.memRecordArr, term.proRecordArr, term.memRecSize, term.proRecSize, term.serviceRecordArr, term.servRecSize);
						weeklyTimerWindow.setVisible(true);
						System.out.println("\n--- Weekly Timer Menu ---");
						break;
				// Invalid/unreachable path - something bad happened
	            default:
					System.out.println("Invalid response state reached. Exiting system...");
					input.close(); // Free the scanner
					return;
				}

			}
		});
		loginButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		loginButton.setBackground(new Color(204, 0, 51));
		loginButton.setBounds(167, 132, 89, 23);
		frame.getContentPane().add(loginButton);

		userIdInput = new JTextField();
		userIdInput.setBounds(167, 70, 96, 20);
		frame.getContentPane().add(userIdInput);
		userIdInput.setColumns(10);

		passwordInput = new JTextField();
		passwordInput.setBounds(167, 101, 96, 20);
		frame.getContentPane().add(passwordInput);
		passwordInput.setColumns(10);

		JLabel welcomeTitle = DefaultComponentFactory.getInstance().createTitle("Welcome to ChocAn");
		welcomeTitle.setForeground(new Color(255, 0, 51));
		welcomeTitle.setFont(new Font("Rockwell", Font.BOLD, 20));
		welcomeTitle.setBounds(108, 11, 210, 35);
		frame.getContentPane().add(welcomeTitle);

		JLabel userIDLabel = DefaultComponentFactory.getInstance().createLabel("User ID:");
		userIDLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		userIDLabel.setBounds(84, 71, 72, 14);
		frame.getContentPane().add(userIDLabel);

		JLabel passwordIDLabel = DefaultComponentFactory.getInstance().createLabel("Password:");
		passwordIDLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		passwordIDLabel.setBounds(67, 102, 90, 14);
		frame.getContentPane().add(passwordIDLabel);

		loginFailMessage = new JLabel("");
		loginFailMessage.setFont(new Font("Tahoma", Font.PLAIN, 15));
		loginFailMessage.setBounds(149, 166, 168, 14);
		frame.getContentPane().add(loginFailMessage);
	}
}
