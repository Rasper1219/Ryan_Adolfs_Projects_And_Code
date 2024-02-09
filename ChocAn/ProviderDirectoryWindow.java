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

public class ProviderDirectoryWindow extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProviderDirectoryWindow frame = new ProviderDirectoryWindow();
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
	public ProviderDirectoryWindow(MemberRecords[] member, int memSize, ServiceRecord[] service, int servSize) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel proDirTitle = DefaultComponentFactory.getInstance().createTitle("Provider Directory");
		proDirTitle.setForeground(new Color(204, 0, 51));
		proDirTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		proDirTitle.setBounds(121, 11, 192, 25);
		contentPane.add(proDirTitle);
		
		JLabel proDirLbl = DefaultComponentFactory.getInstance().createLabel("(also sent as email attachment)");
		proDirLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		proDirLbl.setBounds(110, 47, 247, 14);
		contentPane.add(proDirLbl);
		
		JLabel aLbl = DefaultComponentFactory.getInstance().createLabel("ServiceA: 000001, $125");
		aLbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		aLbl.setBounds(60, 72, 205, 14);
		contentPane.add(aLbl);
		
		JLabel bLbl = DefaultComponentFactory.getInstance().createLabel("ServiceB: 000010, $98");
		bLbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		bLbl.setBounds(60, 97, 192, 14);
		contentPane.add(bLbl);
		
		JLabel cLbl = DefaultComponentFactory.getInstance().createLabel("ServiceC: 000011, $183");
		cLbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		cLbl.setBounds(60, 126, 178, 14);
		contentPane.add(cLbl);
		
		JLabel dLbl = DefaultComponentFactory.getInstance().createLabel("ServiceD: 000100, $57");
		dLbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		dLbl.setBounds(60, 151, 192, 14);
		contentPane.add(dLbl);
		
		JLabel eLbl = DefaultComponentFactory.getInstance().createLabel("ServiceE: 001001, $203");
		eLbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		eLbl.setBounds(60, 176, 166, 14);
		contentPane.add(eLbl);
		
		JLabel fLbl = DefaultComponentFactory.getInstance().createLabel("ServiceF: 000110, $86");
		fLbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		fLbl.setBounds(60, 201, 192, 14);
		contentPane.add(fLbl);
		
		JButton provDirBttn = new JButton("Return to Menu");
		provDirBttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
				dispose();
				ProviderInterfaceWindow provider = new ProviderInterfaceWindow(member, memSize, service, servSize);
				provider.setVisible(true);
			}
		});
		provDirBttn.setBounds(10, 231, 148, 23);
		contentPane.add(provDirBttn);
	}
	
	public ProviderDirectoryWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel proDirTitle = DefaultComponentFactory.getInstance().createTitle("Provider Directory");
		proDirTitle.setForeground(new Color(204, 0, 51));
		proDirTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		proDirTitle.setBounds(121, 11, 192, 25);
		contentPane.add(proDirTitle);
		
		JLabel proDirLbl = DefaultComponentFactory.getInstance().createLabel("(also sent as email attachment)");
		proDirLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		proDirLbl.setBounds(110, 47, 247, 14);
		contentPane.add(proDirLbl);
		
		JLabel aLbl = DefaultComponentFactory.getInstance().createLabel("ServiceA: 000001, $125");
		aLbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		aLbl.setBounds(60, 72, 205, 14);
		contentPane.add(aLbl);
		
		JLabel bLbl = DefaultComponentFactory.getInstance().createLabel("ServiceB: 000010, $98");
		bLbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		bLbl.setBounds(60, 97, 192, 14);
		contentPane.add(bLbl);
		
		JLabel cLbl = DefaultComponentFactory.getInstance().createLabel("ServiceC: 000011, $183");
		cLbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		cLbl.setBounds(60, 126, 178, 14);
		contentPane.add(cLbl);
		
		JLabel dLbl = DefaultComponentFactory.getInstance().createLabel("ServiceD: 000100, $57");
		dLbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		dLbl.setBounds(60, 151, 192, 14);
		contentPane.add(dLbl);
		
		JLabel eLbl = DefaultComponentFactory.getInstance().createLabel("ServiceE: 0001001, $203");
		eLbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		eLbl.setBounds(60, 176, 166, 14);
		contentPane.add(eLbl);
		
		JLabel fLbl = DefaultComponentFactory.getInstance().createLabel("ServiceF: 000110, $86");
		fLbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		fLbl.setBounds(60, 201, 192, 14);
		contentPane.add(fLbl);
		
		JButton provDirBttn = new JButton("Return to Menu");
		provDirBttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
				dispose();
				ProviderInterfaceWindow provider = new ProviderInterfaceWindow();
				provider.setVisible(true);
			}
		});
		provDirBttn.setBounds(10, 231, 148, 23);
		contentPane.add(provDirBttn);
	}
}
