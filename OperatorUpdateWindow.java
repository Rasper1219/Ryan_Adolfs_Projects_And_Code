package chocAn;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OperatorUpdateWindow extends JFrame {

	private JPanel contentPane;
	private JTextField nameTxt;
	private JTextField addressTxt;
	private JTextField cityTxt;
	private JTextField stateTxt;
	private JTextField zipTxt;
	private JTextField idTxt;
	private JTextField phoneTxt;
	private JTextField serviceTxt;
	private JTextField statusTxt;
	private JTextField editTxt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OperatorUpdateWindow frame = new OperatorUpdateWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public OperatorUpdateWindow(MemberRecords[] member, ProviderRecords[] provider, int memSize, int proSize, int action, int type) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		nameTxt = new JTextField();
		nameTxt.setBounds(96, 35, 96, 20);
		contentPane.add(nameTxt);
		nameTxt.setColumns(10);
		
		addressTxt = new JTextField();
		addressTxt.setBounds(96, 96, 96, 20);
		contentPane.add(addressTxt);
		addressTxt.setColumns(10);
		
		cityTxt = new JTextField();
		cityTxt.setBounds(96, 127, 96, 20);
		contentPane.add(cityTxt);
		cityTxt.setColumns(10);
		
		stateTxt = new JTextField();
		stateTxt.setBounds(297, 35, 96, 20);
		contentPane.add(stateTxt);
		stateTxt.setColumns(10);
		
		zipTxt = new JTextField();
		zipTxt.setBounds(297, 66, 96, 20);
		contentPane.add(zipTxt);
		zipTxt.setColumns(10);
		
		idTxt = new JTextField();
		idTxt.setBounds(96, 66, 96, 20);
		contentPane.add(idTxt);
		idTxt.setColumns(10);
		
		phoneTxt = new JTextField();
		phoneTxt.setBounds(297, 96, 96, 20);
		contentPane.add(phoneTxt);
		phoneTxt.setColumns(10);
		
		serviceTxt = new JTextField();
		serviceTxt.setBounds(297, 127, 96, 20);
		contentPane.add(serviceTxt);
		serviceTxt.setColumns(10);
		
		statusTxt = new JTextField();
		statusTxt.setBounds(297, 158, 96, 20);
		contentPane.add(statusTxt);
		statusTxt.setColumns(10);
		
		editTxt = new JTextField();
		editTxt.setBounds(297, 189, 96, 20);
		contentPane.add(editTxt);
		editTxt.setColumns(10);
		
		JButton updateBttn = new JButton("Update");
		updateBttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//type == 1, member, type == 2, provider
				OperatorInterface opInterface = new OperatorInterface();
				int edit = 0;
				int mSize = memSize; 
				int pSize = proSize;
				Records newRec = new Records();
				boolean complete = true;
				if (action == 1) {
					String name = nameTxt.getText();
					int id = Integer.parseInt(idTxt.getText());
					String address = addressTxt.getText();
					String city = cityTxt.getText();
					String state = stateTxt.getText();
					int zip = Integer.parseInt(zipTxt.getText());
					String phone = phoneTxt.getText();
					newRec.setName(name);
					newRec.setAddress(address);
					newRec.setCity(city);
					newRec.setNumID(id);
					newRec.setPhoneNum(phone);
					newRec.setState(state);
					newRec.setZipCode(zip);
					//newMem.memberRecord.setServices(services[0]);
					//able to add service record?
					if (type == 1) {
						String status = statusTxt.getText();
						newRec.setValidation(status);
						complete = opInterface.updateMember(newRec, action, edit, member, memSize);
						mSize++;
					} else if (type == 2) {
						complete = opInterface.updateProvider(newRec, action, edit, provider, proSize);
						pSize++;
					}
				} else if (action == 2) {
					int id = Integer.parseInt(idTxt.getText());
					newRec.setNumID(id);
					if (type == 1) {
						complete = opInterface.updateMember(newRec, action, edit, member, memSize);
					} else if (type == 2) {
						complete = opInterface.updateProvider(newRec, action, edit, provider, proSize);
					}
					
				} else if (action == 3) {
					edit = Integer.parseInt(editTxt.getText());
					int numID = Integer.parseInt(idTxt.getText());
					newRec.setNumID(numID);
					if (edit == 1) {
						String name = nameTxt.getText();
						newRec.setName(name);
					} else if (edit == 2) {
						int id = Integer.parseInt(idTxt.getText());
						newRec.setNumID(id);
					} else if (edit == 3) {
						String address = addressTxt.getText();
						newRec.setAddress(address);
					} else if (edit == 4) {
						String city = cityTxt.getText();
						newRec.setCity(city);
					} else if (edit == 5) {
						String state = stateTxt.getText();
						newRec.setState(state);
					} else if (edit == 6) {
						int zip = Integer.parseInt(zipTxt.getText());
						newRec.setZipCode(zip);
					} else if (edit == 7) {
						String phone = phoneTxt.getText();
						newRec.setPhoneNum(phone);
					} else if (edit == 8) {
						//int service = Integer.parseInt(serviceTxt.getText());
					} else if (edit == 9) {
						String status = statusTxt.getText();
						newRec.setValidation(status);
						//opInterface.updateMember(newRec, action, edit, member, memSize);
					}
					if (type == 1) {
						String status = statusTxt.getText();
						newRec.setValidation(status);
						complete = opInterface.updateMember(newRec, action, edit, member, memSize);
					} else if (type == 2) {
						complete = opInterface.updateProvider(newRec, action, edit, provider, proSize);
					}
				}
				contentPane.setVisible(false);
				dispose();
				if (complete == true) {
					OperatorInterfaceWindow operator = new OperatorInterfaceWindow(member, provider, mSize, pSize, 1);
					operator.setVisible(true);
				} else {
					OperatorInterfaceWindow operator = new OperatorInterfaceWindow(member, provider, mSize, pSize, 2);
					operator.setVisible(true);
				}
			}
		});
		updateBttn.setFont(new Font("Tahoma", Font.PLAIN, 13));
		updateBttn.setBounds(191, 225, 89, 23);
		contentPane.add(updateBttn);
		
		JLabel nameLbl = new JLabel("1) Name:");
		nameLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		nameLbl.setBounds(10, 36, 76, 14);
		contentPane.add(nameLbl);
		
		JLabel zipLbl = new JLabel("6) Zip Code:");
		zipLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		zipLbl.setBounds(202, 67, 85, 14);
		contentPane.add(zipLbl);
		
		JLabel idLbl = new JLabel("2) ID #:");
		idLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		idLbl.setBounds(10, 67, 76, 14);
		contentPane.add(idLbl);
		
		JLabel phoneLbl = new JLabel("7) Phone #:");
		phoneLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		phoneLbl.setBounds(202, 99, 85, 14);
		contentPane.add(phoneLbl);
		
		JLabel serviceLbl = new JLabel("8) Service #:");
		serviceLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		serviceLbl.setBounds(202, 130, 89, 14);
		contentPane.add(serviceLbl);
		
		JLabel addressLbl = new JLabel("3) Address:");
		addressLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		addressLbl.setBounds(10, 97, 76, 14);
		contentPane.add(addressLbl);
		
		JLabel cityLbl = new JLabel("4) City:");
		cityLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		cityLbl.setBounds(10, 127, 73, 17);
		contentPane.add(cityLbl);
		
		JLabel stateLbl = new JLabel("5) State:");
		stateLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		stateLbl.setBounds(202, 36, 75, 14);
		contentPane.add(stateLbl);
		
		JLabel verifLbl = new JLabel("9) Validation Status (member only):");
		verifLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		verifLbl.setBounds(49, 156, 242, 20);
		contentPane.add(verifLbl);
		
		JLabel editLbl = new JLabel("# Edit (if editing):");
		editLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		editLbl.setBounds(169, 188, 126, 19);
		contentPane.add(editLbl);
	}

	/**
	 * Create the frame.
	 */
	public OperatorUpdateWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		nameTxt = new JTextField();
		nameTxt.setBounds(96, 35, 96, 20);
		contentPane.add(nameTxt);
		nameTxt.setColumns(10);
		
		addressTxt = new JTextField();
		addressTxt.setBounds(96, 96, 96, 20);
		contentPane.add(addressTxt);
		addressTxt.setColumns(10);
		
		cityTxt = new JTextField();
		cityTxt.setBounds(96, 127, 96, 20);
		contentPane.add(cityTxt);
		cityTxt.setColumns(10);
		
		stateTxt = new JTextField();
		stateTxt.setBounds(297, 35, 96, 20);
		contentPane.add(stateTxt);
		stateTxt.setColumns(10);
		
		zipTxt = new JTextField();
		zipTxt.setBounds(297, 66, 96, 20);
		contentPane.add(zipTxt);
		zipTxt.setColumns(10);
		
		idTxt = new JTextField();
		idTxt.setBounds(96, 66, 96, 20);
		contentPane.add(idTxt);
		idTxt.setColumns(10);
		
		phoneTxt = new JTextField();
		phoneTxt.setBounds(297, 96, 96, 20);
		contentPane.add(phoneTxt);
		phoneTxt.setColumns(10);
		
		serviceTxt = new JTextField();
		serviceTxt.setBounds(297, 127, 96, 20);
		contentPane.add(serviceTxt);
		serviceTxt.setColumns(10);
		
		statusTxt = new JTextField();
		statusTxt.setBounds(297, 158, 96, 20);
		contentPane.add(statusTxt);
		statusTxt.setColumns(10);
		
		editTxt = new JTextField();
		editTxt.setBounds(297, 189, 96, 20);
		contentPane.add(editTxt);
		editTxt.setColumns(10);
		
		JButton updateBttn = new JButton("Update");
		updateBttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = nameTxt.getText();
				String id = idTxt.getText();
			}
		});
		updateBttn.setFont(new Font("Tahoma", Font.PLAIN, 13));
		updateBttn.setBounds(191, 225, 89, 23);
		contentPane.add(updateBttn);
		
		JLabel nameLbl = new JLabel("1) Name:");
		nameLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		nameLbl.setBounds(10, 36, 76, 14);
		contentPane.add(nameLbl);
		
		JLabel zipLbl = new JLabel("6) Zip Code:");
		zipLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		zipLbl.setBounds(202, 67, 85, 14);
		contentPane.add(zipLbl);
		
		JLabel idLbl = new JLabel("2) ID #:");
		idLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		idLbl.setBounds(10, 67, 76, 14);
		contentPane.add(idLbl);
		
		JLabel phoneLbl = new JLabel("7) Phone #:");
		phoneLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		phoneLbl.setBounds(202, 99, 85, 14);
		contentPane.add(phoneLbl);
		
		JLabel serviceLbl = new JLabel("8) Service #:");
		serviceLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		serviceLbl.setBounds(202, 130, 89, 14);
		contentPane.add(serviceLbl);
		
		JLabel addressLbl = new JLabel("3) Address:");
		addressLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		addressLbl.setBounds(10, 97, 76, 14);
		contentPane.add(addressLbl);
		
		JLabel cityLbl = new JLabel("4) City:");
		cityLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		cityLbl.setBounds(10, 127, 73, 17);
		contentPane.add(cityLbl);
		
		JLabel stateLbl = new JLabel("5) State:");
		stateLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		stateLbl.setBounds(202, 36, 75, 14);
		contentPane.add(stateLbl);
		
		JLabel verifLbl = new JLabel("9) Validation Status (member only):");
		verifLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		verifLbl.setBounds(49, 156, 242, 20);
		contentPane.add(verifLbl);
		
		JLabel editLbl = new JLabel("# Edit (if editing):");
		editLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		editLbl.setBounds(169, 188, 126, 19);
		contentPane.add(editLbl);
	}
}
