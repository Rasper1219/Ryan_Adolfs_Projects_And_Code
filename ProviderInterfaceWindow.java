package chocAn;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class ProviderInterfaceWindow extends JFrame {

	private JPanel contentPane;
	private JTextField memberLbl;
	private JTextField dateInput;
	private JTextField serviceInput;
	private JTextField commentInput;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProviderInterfaceWindow frame = new ProviderInterfaceWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ProviderInterfaceWindow(MemberRecords[] member, int memSize, ServiceRecord[] service, int servSize) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel providerTitle = DefaultComponentFactory.getInstance().createTitle("Provider Menu");
		providerTitle.setForeground(new Color(204, 0, 51));
		providerTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		providerTitle.setBounds(152, 11, 160, 25);
		contentPane.add(providerTitle);
		
		memberLbl = new JTextField();
		memberLbl.setBounds(332, 67, 96, 20);
		contentPane.add(memberLbl);
		memberLbl.setColumns(10);
		
		JLabel memNumLbl = new JLabel("Member #:");
		memNumLbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		memNumLbl.setBounds(338, 42, 76, 14);
		contentPane.add(memNumLbl);
		
		JLabel statusLbl = new JLabel("");
		statusLbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		statusLbl.setBounds(278, 87, 148, 25);
		contentPane.add(statusLbl);
		
		dateInput = new JTextField();
		dateInput.setBounds(332, 113, 96, 20);
		contentPane.add(dateInput);
		dateInput.setColumns(10);
		
		serviceInput = new JTextField();
		serviceInput.setBounds(332, 144, 96, 20);
		contentPane.add(serviceInput);
		serviceInput.setColumns(10);
		
		JLabel dateLbl = new JLabel("MM-DD-YYYY:");
		dateLbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		dateLbl.setBounds(247, 115, 85, 14);
		contentPane.add(dateLbl);
		
		JLabel serviceDisplay = new JLabel("");
		serviceDisplay.setFont(new Font("Tahoma", Font.PLAIN, 13));
		serviceDisplay.setBounds(249, 163, 179, 14);
		contentPane.add(serviceDisplay);
		
		JLabel serviceLbl = new JLabel("Service #:");
		serviceLbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		serviceLbl.setBounds(262, 149, 69, 14);
		contentPane.add(serviceLbl);
		
		JLabel feeDisplay = new JLabel("");
		feeDisplay.setFont(new Font("Tahoma", Font.PLAIN, 13));
		feeDisplay.setBounds(288, 217, 140, 14);
		contentPane.add(feeDisplay);
		
		JLabel feeLbl = new JLabel("Fee: ");
		feeLbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		feeLbl.setBounds(247, 217, 48, 14);
		contentPane.add(feeLbl);
		
		commentInput = new JTextField();
		commentInput.setBounds(244, 186, 184, 20);
		contentPane.add(commentInput);
		commentInput.setColumns(10);
		
		JLabel commentLbl = new JLabel("Comments:");
		commentLbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		commentLbl.setBounds(163, 188, 76, 14);
		contentPane.add(commentLbl);
		
		JButton validateMemBttn = new JButton("Validate Member");
		validateMemBttn.addActionListener(new ActionListener() {
			ProviderInterface proInterface = new ProviderInterface();
			public void actionPerformed(ActionEvent e) {
				int id = Integer.parseInt(memberLbl.getText());
				String validMess = proInterface.callValidateMember(id, member, memSize);
				statusLbl.setText(validMess);
			}
		});
		validateMemBttn.setFont(new Font("Tahoma", Font.PLAIN, 15));
		validateMemBttn.setBounds(10, 64, 229, 23);
		contentPane.add(validateMemBttn);
		
		JButton requestBttn = new JButton("Request Provider Directory");
		requestBttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
				dispose();
				ProviderDirectoryWindow directory = new ProviderDirectoryWindow(member, memSize, service, servSize);
				directory.setVisible(true);
			}
		});
		requestBttn.setFont(new Font("Tahoma", Font.PLAIN, 15));
		requestBttn.setBounds(10, 154, 229, 23);
		contentPane.add(requestBttn);
		
		JButton billBttn = new JButton("Bill ChocAn");
		billBttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProviderInterface proInterface = new ProviderInterface();
				ServiceRecord newServ = new ServiceRecord();
				int id = Integer.parseInt(memberLbl.getText());
				int code = Integer.parseInt(serviceInput.getText());
				String date = dateInput.getText();
				String comment = commentInput.getText();
				int flag = 0;
				if (code == 000001) {
					feeDisplay.setText("$125 - ServiceA");
					newServ.setServiceFee(125);
					System.out.println("Billing Successful.");
				} else if (code == 10) {
					feeDisplay.setText("$98 - ServiceB");
					newServ.setServiceFee(98);
					System.out.println("Billing Successful.");
				} else if (code == 11) {
					feeDisplay.setText("$183 - ServiceC");
					newServ.setServiceFee(183);
					System.out.println("Billing Successful.");
				} else if (code == 100) {
					feeDisplay.setText("$57 - ServiceD");
					newServ.setServiceFee(57);
					System.out.println("Billing Successful.");
				} else if (code == 101) {
					feeDisplay.setText("$203 - ServiceE");
					newServ.setServiceFee(203);
					System.out.println("Billing Successful.");
				} else if (code == 110) {
					feeDisplay.setText("$86 - ServiceF");
					newServ.setServiceFee(86);
					System.out.println("Billing Successful.");
				} else {
					serviceDisplay.setText("Incorrect service code.");
					System.out.println("Billing Unsuccessful.");
					System.out.println(code);
					flag = 1;
				}
				if (flag == 0) {
					newServ.setProviderNum(4931);
					newServ.setMemberNum(id);
					newServ.setServiceCode(code);
					newServ.setServiceDateTime(date);
					newServ.setCurrentDateTime(date);
					newServ.setComments(comment);
					proInterface.billChocAn(newServ);
				}
			}
		});
		billBttn.setFont(new Font("Tahoma", Font.PLAIN, 15));
		billBttn.setBounds(10, 110, 229, 23);
		contentPane.add(billBttn);
		
		JButton provExitBttn = new JButton("Exit");
		provExitBttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
				dispose();
				ExitWindow exit = new ExitWindow();
				exit.setVisible(true);
			}
		});
		provExitBttn.setFont(new Font("Tahoma", Font.PLAIN, 15));
		provExitBttn.setBounds(10, 231, 89, 23);
		contentPane.add(provExitBttn);
	}
	
	public ProviderInterfaceWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel providerTitle = DefaultComponentFactory.getInstance().createTitle("Provider Menu");
		providerTitle.setForeground(new Color(204, 0, 51));
		providerTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		providerTitle.setBounds(152, 11, 160, 25);
		contentPane.add(providerTitle);
		
		JButton validateMemBttn = new JButton("Validate Member");
		validateMemBttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		validateMemBttn.setFont(new Font("Tahoma", Font.PLAIN, 15));
		validateMemBttn.setBounds(10, 64, 229, 23);
		contentPane.add(validateMemBttn);
		
		JButton requestBttn = new JButton("Request Provider Directory");
		requestBttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
				dispose();
				ProviderDirectoryWindow directory = new ProviderDirectoryWindow();
				directory.setVisible(true);
			}
		});
		requestBttn.setFont(new Font("Tahoma", Font.PLAIN, 15));
		requestBttn.setBounds(10, 154, 229, 23);
		contentPane.add(requestBttn);
		
		JButton billBttn = new JButton("Bill ChocAn");
		billBttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		billBttn.setFont(new Font("Tahoma", Font.PLAIN, 15));
		billBttn.setBounds(10, 110, 229, 23);
		contentPane.add(billBttn);
		
		JButton provExitBttn = new JButton("Exit");
		provExitBttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
				dispose();
				ExitWindow exit = new ExitWindow();
				exit.setVisible(true);
			}
		});
		provExitBttn.setFont(new Font("Tahoma", Font.PLAIN, 15));
		provExitBttn.setBounds(10, 231, 89, 23);
		contentPane.add(provExitBttn);
		
		memberLbl = new JTextField();
		memberLbl.setBounds(332, 67, 96, 20);
		contentPane.add(memberLbl);
		memberLbl.setColumns(10);
		
		JLabel memNumLbl = new JLabel("Member #:");
		memNumLbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		memNumLbl.setBounds(338, 42, 76, 14);
		contentPane.add(memNumLbl);
		
		JLabel statusLbl = new JLabel("");
		statusLbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		statusLbl.setBounds(278, 87, 148, 25);
		contentPane.add(statusLbl);
		
		dateInput = new JTextField();
		dateInput.setBounds(332, 113, 96, 20);
		contentPane.add(dateInput);
		dateInput.setColumns(10);
		
		serviceInput = new JTextField();
		serviceInput.setBounds(332, 144, 96, 20);
		contentPane.add(serviceInput);
		serviceInput.setColumns(10);
		
		JLabel dateLbl = new JLabel("MM-DD-YYYY:");
		dateLbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		dateLbl.setBounds(247, 115, 85, 14);
		contentPane.add(dateLbl);
		
		JLabel serviceDisplay = new JLabel("");
		serviceDisplay.setFont(new Font("Tahoma", Font.PLAIN, 13));
		serviceDisplay.setBounds(249, 163, 179, 14);
		contentPane.add(serviceDisplay);
		
		JLabel serviceLbl = new JLabel("Service #:");
		serviceLbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		serviceLbl.setBounds(262, 149, 69, 14);
		contentPane.add(serviceLbl);
		
		JLabel feeDisplay = new JLabel("");
		feeDisplay.setFont(new Font("Tahoma", Font.PLAIN, 13));
		feeDisplay.setBounds(288, 217, 140, 14);
		contentPane.add(feeDisplay);
		
		JLabel feeLbl = new JLabel("Fee: ");
		feeLbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		feeLbl.setBounds(247, 217, 48, 14);
		contentPane.add(feeLbl);
		
		commentInput = new JTextField();
		commentInput.setBounds(244, 186, 184, 20);
		contentPane.add(commentInput);
		commentInput.setColumns(10);
		
		JLabel commentLbl = new JLabel("Comments:");
		commentLbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		commentLbl.setBounds(163, 188, 76, 14);
		contentPane.add(commentLbl);
	}
}
