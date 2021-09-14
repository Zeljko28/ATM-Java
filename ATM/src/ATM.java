
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import java.awt.event.ActionListener;
import java.io.*;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.Panel;
import java.awt.BorderLayout;
import javax.swing.JScrollBar;


public class ATM {

	private JFrame frame;
	
	private JLayeredPane layeredPane = new JLayeredPane();
	private JPasswordField passwordField;
	private JTextField txtUplata;
	private int isplata = 0;
	private JTextArea txtPolje;
	private String pin; 
	private int stanje;
	private String pathPin = "C:\\Users\\zeljk\\Desktop\\Fajlovi\\PIN.txt";  //Promeniti putanju!
	private String pathT1 = "C:\\Users\\zeljk\\Desktop\\Fajlovi\\T1.txt";
	private String pathT2 = "C:\\Users\\zeljk\\Desktop\\Fajlovi\\T2.txt";
	private String pathT3 = "C:\\Users\\zeljk\\Desktop\\Fajlovi\\T3.txt";
	private String pathS1 = "C:\\Users\\zeljk\\Desktop\\Fajlovi\\S1.txt";
	private String pathS2 = "C:\\Users\\zeljk\\Desktop\\Fajlovi\\S2.txt";
	private String pathS3 = "C:\\Users\\zeljk\\Desktop\\Fajlovi\\S3.txt";
	private int uplata = 0;
	private boolean flagPin = false;
	private int brojac = 0;
	private int counter = 0;
	private JTextField txtDrugiIznos;
	private boolean panelFlag; // promenljiva dodata zbog problema sa panelima
	
	
	


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ATM window = new ATM();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void switchPanel(JPanel panel) {
		
		layeredPane.removeAll();
		layeredPane.add(panel);
		layeredPane.repaint();
		layeredPane.revalidate();
		
	}
	
	public void WriteToFile(String path) {
		
		File file = new File(path);
		if(path == pathT1 || path == pathT2|| path == pathT3) {
			if(isplata != 0) {
				try {
					FileWriter Writer = new FileWriter(file,true);
					Writer.write("\n-----------------------\n" + "-" + isplata + " din.");
					Writer.close();
				}
				catch(IOException e) {
					JOptionPane.showMessageDialog(null, "Greska");
				}
			}
			else {
				try {
					FileWriter Writer = new FileWriter(file,true);
					Writer.write("\n-----------------------\n" + "+" + uplata + " din.");
					Writer.close();
				}
				catch(IOException e) {
					JOptionPane.showMessageDialog(null, "Greska");
				}
			}
		}
		if(path == pathS1 || path == pathS2 || path == pathS3) {
			if(isplata != 0) {
				try {
					FileWriter Writer = new FileWriter(file);
					stanje = stanje - isplata;
					//JOptionPane.showMessageDialog(null, stanje);
				
					Writer.write("" + stanje);
				
					Writer.close();
				}
				catch(IOException e) {
					JOptionPane.showMessageDialog(null, "Greska");
				}
			}
			else {
				try {
					FileWriter Writer = new FileWriter(file);
					stanje = stanje + uplata;
					//JOptionPane.showMessageDialog(null, stanje);
				
					Writer.write("" + stanje);
				
					Writer.close();
				}
				catch(IOException e) {
					JOptionPane.showMessageDialog(null, "Greska");
				}
			}
		}
	}
	
	public void write(String path) {
		
		File file = new File(path);
		if(path == pathT1 || path == pathT2|| path == pathT3) {
		try {
			Scanner z = new Scanner(file);
			z.useDelimiter("[\n]");
			
			while(z.hasNext()) {
				String tmp = z.next().trim();
				txtPolje.setText(txtPolje.getText() + tmp + "\n");
			}
			
			z.close();
		}
		catch(FileNotFoundException ex) {
			JOptionPane.showMessageDialog(null, "Fajl nije pronadjen!");
		}
		}
		
		if(path == pathS1 || path == pathS2|| path == pathS3) {
	
			txtPolje.setText(txtPolje.getText() + "\n\n" + "Trenutno stanje: " + stanje + " din.");
			
				
			}

		
		
	}
	
	public void read(String path) {
		
		File file = new File(path);
		if(path == pathPin) {
			try {
				Scanner x = new Scanner(file);
				x.useDelimiter("[,\n]");
				
				while(x.hasNext() && !flagPin) {
					String tempPin = x.next();
					brojac++;
					if(tempPin.trim().equals(passwordField.getText().trim())) {
						flagPin = true;
						pin = passwordField.getText();
						x.close();
						break;
					}
				}
				if(flagPin == false) {
					JOptionPane.showMessageDialog(null, "Netacan Pin!");
					passwordField.setText("");
					}
				}
				catch(FileNotFoundException ex) {
					JOptionPane.showMessageDialog(null, "Fajl nije pronadjen!");
				}
			
		}
	}
	public void readStanje(String path) {
		File file = new File(path);
				try {
					Scanner y = new Scanner(file);
					y.useDelimiter("[\n]");
					
					stanje = Integer.parseInt(y.next().trim());
					y.close();
					
						}
					
				 catch (FileNotFoundException e) {
					
					JOptionPane.showMessageDialog(null, "Fajl nije pronadjen!");
				}
		}
	
	/*public void removeLine(String path) {
		
		File file = new File(path);
		try{
			Scanner x = new Scanner(file);
			while(x.hasNext()) {
				
			}
		}
		
		
		
		
	}*/
	
	
	/**
	 * Create the application.
	 */
	public ATM() {
		initialize();
	}
	
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("ATM Machine");
		frame.getContentPane().setFont(new Font("Arial", Font.BOLD, 17));
		frame.setBounds(100, 100, 1060, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		layeredPane = new JLayeredPane();
		layeredPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.DARK_GRAY, Color.DARK_GRAY));
		layeredPane.setBounds(119, 11, 470, 201);
		frame.getContentPane().add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
		
		JPanel panel0 = new JPanel();
		layeredPane.add(panel0, "name_271255318955600");
		panel0.setLayout(null);
		
		JLabel Potvrdi = new JLabel("Potvrdi");
		Potvrdi.setFont(new Font("Arial", Font.BOLD, 17));
		Potvrdi.setBounds(399, 152, 57, 34);
		panel0.add(Potvrdi);
		
		JLabel btn_otkazi1 = new JLabel("Otkazi");
		btn_otkazi1.setLabelFor(frame);
		btn_otkazi1.setFont(new Font("Arial", Font.BOLD, 17));
		btn_otkazi1.setBounds(10, 152, 57, 34);
		panel0.add(btn_otkazi1);
		
		passwordField = new JPasswordField();
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		passwordField.setFont(new Font("Arial", Font.BOLD, 20));
		passwordField.setBounds(143, 92, 178, 34);
		panel0.add(passwordField);
		

		
		JPanel panel2 = new JPanel();
		layeredPane.setLayer(panel2, 0);
		layeredPane.add(panel2, "name_256297602097900");
		panel2.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("Unesite drugi iznos");
		lblNewLabel_3.setFont(new Font("Arial", Font.BOLD, 17));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_3.setBounds(10, 153, 152, 33);
		
		panel2.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("2.000 din.");
		lblNewLabel_4.setFont(new Font("Arial", Font.BOLD, 17));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_4.setBounds(10, 88, 80, 33);
		panel2.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("1.000 din.");
		lblNewLabel_5.setFont(new Font("Arial", Font.BOLD, 17));
		lblNewLabel_5.setBounds(10, 28, 80, 25);
		panel2.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("5.000 din.");
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_6.setFont(new Font("Arial", Font.BOLD, 17));
		lblNewLabel_6.setBounds(376, 28, 80, 25);
		panel2.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("Uplata novca");
		lblNewLabel_7.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_7.setFont(new Font("Arial", Font.BOLD, 17));
		lblNewLabel_7.setBounds(342, 92, 114, 29);
		panel2.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("Provera stanja");
		lblNewLabel_8.setFont(new Font("Arial", Font.BOLD, 17));
		lblNewLabel_8.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_8.setBounds(331, 157, 125, 25);
		
		panel2.add(lblNewLabel_8);
		
		JPanel panel3 = new JPanel();
		layeredPane.setLayer(panel3, 0);
		layeredPane.add(panel3, "name_256301396495300");
		panel3.setLayout(null);
		
		JLabel lblNewLabel_9 = new JLabel("Bez priznanice");
		lblNewLabel_9.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_9.setFont(new Font("Arial", Font.BOLD, 17));
		lblNewLabel_9.setBounds(10, 153, 116, 33);
		panel3.add(lblNewLabel_9);
		
		JLabel lblNewLabel_10 = new JLabel("Papirna priznanica");
		lblNewLabel_10.setFont(new Font("Arial", Font.BOLD, 17));
		lblNewLabel_10.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_10.setBounds(310, 153, 146, 33);
		panel3.add(lblNewLabel_10);
		
		JLabel lblIsplata = new JLabel("");
		lblIsplata.setFont(new Font("Arial", Font.BOLD, 15));
		lblIsplata.setBounds(120, 80, 263, 24);
		panel3.add(lblIsplata);
		
		JPanel panel4 = new JPanel();
		layeredPane.add(panel4, "name_256305215158000");
		panel4.setLayout(null);
		
		txtUplata = new JTextField();
		txtUplata.setHorizontalAlignment(SwingConstants.CENTER);
		txtUplata.setFont(new Font("Arial", Font.BOLD, 17));
		txtUplata.setText("");
		txtUplata.setBounds(137, 98, 176, 33);
		panel4.add(txtUplata);
		txtUplata.setColumns(10);
		
		JLabel lblUplata = new JLabel("Unesite iznos novca za uplatu:");
		lblUplata.setFont(new Font("Arial", Font.BOLD, 15));
		lblUplata.setBounds(117, 60, 223, 27);
		panel4.add(lblUplata);
		
		JLabel lblNewLabel = new JLabel("Potvrdi");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 17));
		lblNewLabel.setBounds(394, 159, 62, 27);
		panel4.add(lblNewLabel);
		
		JLabel lblNevalidno2 = new JLabel("");
		lblNevalidno2.setForeground(Color.RED);
		lblNevalidno2.setBounds(98, 142, 272, 27);
		panel4.add(lblNevalidno2);
		
		JPanel panel5 = new JPanel();
		layeredPane.add(panel5, "name_256309535782600");
		panel5.setLayout(null);
		
		JLabel lblDrugiIznos = new JLabel("Unesite drugi iznos:");
		lblDrugiIznos.setFont(new Font("Arial", Font.BOLD, 15));
		lblDrugiIznos.setBounds(163, 65, 142, 21);
		panel5.add(lblDrugiIznos);
		
		txtDrugiIznos = new JTextField();
		txtDrugiIznos.setHorizontalAlignment(SwingConstants.CENTER);
		txtDrugiIznos.setFont(new Font("Arial", Font.BOLD, 17));
		txtDrugiIznos.setBounds(163, 97, 142, 28);
		panel5.add(txtDrugiIznos);
		txtDrugiIznos.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Potvrdi");
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 17));
		lblNewLabel_1.setBounds(399, 158, 57, 28);
		panel5.add(lblNewLabel_1);
		
		JLabel lblNevalidno1 = new JLabel("");
		lblNevalidno1.setForeground(Color.RED);
		lblNevalidno1.setBounds(94, 136, 281, 28);
		panel5.add(lblNevalidno1);
		
		
		JLabel lbl_pssw = new JLabel("Unesite svoj PIN kod:");
		lbl_pssw.setFont(new Font("Arial", Font.BOLD, 17));
		lbl_pssw.setBounds(143, 51, 178, 41);
		panel0.add(lbl_pssw);
		
		JLabel lblEr = new JLabel("");
		lblEr.setHorizontalAlignment(SwingConstants.CENTER);
		lblEr.setForeground(Color.RED);
		lblEr.setBounds(99, 137, 263, 19);
		panel0.add(lblEr);
		
		JPanel panel6 = new JPanel();
		layeredPane.add(panel6, "name_394947944352100");
		panel6.setLayout(null);
		
		JLabel lbl6 = new JLabel("Transakcija uspesno izvrsena!");
		lbl6.setFont(new Font("Arial", Font.BOLD, 15));
		lbl6.setBounds(127, 89, 219, 26);
		panel6.add(lbl6);
		
		JButton btnNewButton = new JButton("1");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(panel0.isShowing()) {
					passwordField.setText(passwordField.getText() + "1");
				}
				if(panel5.isShowing()) {txtDrugiIznos.setText(txtDrugiIznos.getText() + "1");}
				if(panel4.isShowing()) {txtUplata.setText(txtUplata.getText() + "1");}
			}
		});
		btnNewButton.setFont(new Font("Arial", Font.BOLD, 20));
		btnNewButton.setBounds(119, 223, 150, 55);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("2");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(panel0.isShowing()) {
					passwordField.setText(passwordField.getText() + "2");
				}
				if(panel5.isShowing()) {txtDrugiIznos.setText(txtDrugiIznos.getText() + "2");}
				if(panel4.isShowing()) {txtUplata.setText(txtUplata.getText() + "2");}
			}
		});
		btnNewButton_1.setFont(new Font("Arial", Font.BOLD, 20));
		btnNewButton_1.setBounds(279, 223, 150, 55);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("3");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(panel0.isShowing()) {
					passwordField.setText(passwordField.getText() + "3");
				}
				if(panel5.isShowing()) {txtDrugiIznos.setText(txtDrugiIznos.getText() + "3");}
				if(panel4.isShowing()) {txtUplata.setText(txtUplata.getText() + "3");}
			}
		});
		btnNewButton_2.setFont(new Font("Arial", Font.BOLD, 20));
		btnNewButton_2.setBounds(439, 223, 150, 55);
		frame.getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("4");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(panel0.isShowing()) {
					passwordField.setText(passwordField.getText() + "4");
				}
				if(panel5.isShowing()) {txtDrugiIznos.setText(txtDrugiIznos.getText() + "4");}
				if(panel4.isShowing()) {txtUplata.setText(txtUplata.getText() + "4");}
			}
		});
		btnNewButton_3.setFont(new Font("Arial", Font.BOLD, 20));
		btnNewButton_3.setBounds(119, 289, 150, 55);
		frame.getContentPane().add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("5");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(panel0.isShowing()) {
					passwordField.setText(passwordField.getText() + "5");
				}
				if(panel5.isShowing()) {txtDrugiIznos.setText(txtDrugiIznos.getText() + "5");}
				if(panel4.isShowing()) {txtUplata.setText(txtUplata.getText() + "5");}
			}
		});
		btnNewButton_4.setFont(new Font("Arial", Font.BOLD, 20));
		btnNewButton_4.setBounds(279, 289, 150, 55);
		frame.getContentPane().add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("6");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(panel0.isShowing()) {
					passwordField.setText(passwordField.getText() + "6");
				}
				if(panel5.isShowing()) {txtDrugiIznos.setText(txtDrugiIznos.getText() + "6");}
				if(panel4.isShowing()) {txtUplata.setText(txtUplata.getText() + "6");}
			}
		});
		btnNewButton_5.setFont(new Font("Arial", Font.BOLD, 20));
		btnNewButton_5.setBounds(439, 289, 150, 55);
		frame.getContentPane().add(btnNewButton_5);
		
		JButton btnNewButton_6 = new JButton("7");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(panel0.isShowing()) {
					passwordField.setText(passwordField.getText() + "7");
				}
				if(panel5.isShowing()) {txtDrugiIznos.setText(txtDrugiIznos.getText() + "7");}
				if(panel4.isShowing()) {txtUplata.setText(txtUplata.getText() + "7");}
			}
		});
		btnNewButton_6.setFont(new Font("Arial", Font.BOLD, 20));
		btnNewButton_6.setBounds(119, 355, 150, 55);
		frame.getContentPane().add(btnNewButton_6);
		
		JButton btnNewButton_7 = new JButton("8");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(panel0.isShowing()) {
					passwordField.setText(passwordField.getText() + "8");
				}
				if(panel5.isShowing()) {txtDrugiIznos.setText(txtDrugiIznos.getText() + "8");}
				if(panel4.isShowing()) {txtUplata.setText(txtUplata.getText() + "8");}
			}
		});
		btnNewButton_7.setFont(new Font("Arial", Font.BOLD, 20));
		btnNewButton_7.setBounds(279, 355, 150, 55);
		frame.getContentPane().add(btnNewButton_7);
		
		JButton btnNewButton_8 = new JButton("9");
		btnNewButton_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(panel0.isShowing()) {
					passwordField.setText(passwordField.getText() + "9");
				}
				if(panel5.isShowing()) {txtDrugiIznos.setText(txtDrugiIznos.getText() + "9");}
				if(panel4.isShowing()) {txtUplata.setText(txtUplata.getText() + "9");}
			}
		});
		btnNewButton_8.setFont(new Font("Arial", Font.BOLD, 20));
		btnNewButton_8.setBounds(439, 355, 150, 55);
		frame.getContentPane().add(btnNewButton_8);
		
		JButton btnNewButton_9 = new JButton("0");
		btnNewButton_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(panel0.isShowing()) {
					passwordField.setText(passwordField.getText() + "0");
				}
				if(panel5.isShowing()) {txtDrugiIznos.setText(txtDrugiIznos.getText() + "0");}
				if(panel4.isShowing()) {txtUplata.setText(txtUplata.getText() + "0");}
			}
		});
		btnNewButton_9.setFont(new Font("Arial", Font.BOLD, 20));
		btnNewButton_9.setBounds(279, 421, 150, 55);
		frame.getContentPane().add(btnNewButton_9);
		
		JButton btn_desno_dole = new JButton("<<");
		btn_desno_dole.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(panel2.isShowing()) {panelFlag = true;}
				
				if(panel0.isShowing()) {
				try {
					Integer.parseInt(passwordField.getText());
					read(pathPin);
					if(flagPin == true) {
						switchPanel(panel2);
					}
						
						
						
					}
					catch(NumberFormatException ex) {
						
						lblEr.setText("Netacan unos! PIN kod mora biti broj!");
					}
				}
					
			
				if(panel3.isShowing()) {
					
					
					if(brojac == 2) {readStanje(pathS1);}
					if(brojac == 4) {readStanje(pathS2);}
					if(brojac == 6) {readStanje(pathS3);}
					if(isplata != 0) {
						if(isplata > stanje) {
							lblIsplata.setText("Nemate dovoljno sredstava na racunu.");
							}
					
						else {
							lblIsplata.setText("Transakcija uspesno izvrsena!");
							//JOptionPane.showMessageDialog(null, stanje);
							if(brojac == 2 && counter == 0) {
							
								WriteToFile(pathS1);
								WriteToFile(pathT1);
								write(pathT1);
								write(pathS1);
								counter = 1;
							}
							if(brojac == 4 && counter == 0) {
							
								WriteToFile(pathS2);
								WriteToFile(pathT2);
								write(pathT2);
								write(pathS2);
								counter = 1;
							}
							if(brojac == 6 && counter == 0){
								WriteToFile(pathS3);
								WriteToFile(pathT3);
								write(pathT3);
								write(pathS3);
								counter = 1;
							}
						}
						
					}
					else {
						lblIsplata.setText("Transakcija uspesno izvrsena!");
						if(brojac == 2 && counter == 0) {
							
							WriteToFile(pathS1);
							WriteToFile(pathT1);
							write(pathT1);
							write(pathS1);
							counter = 1;
						}
						if(brojac == 4 && counter == 0) {
						
							WriteToFile(pathS2);
							WriteToFile(pathT2);
							write(pathT2);
							write(pathS2);
							counter = 1;
						}
						if(brojac == 6 && counter == 0){
							WriteToFile(pathS3);
							WriteToFile(pathT3);
							write(pathT3);
							write(pathS3);
							counter = 1;
						}
						
					}
				}
				if(panel5.isShowing()) {
					
					try {
					isplata = Integer.parseInt(txtDrugiIznos.getText());
					

					
						if(isplata % 1000 != 0) {
							isplata = 0;
							lblNevalidno1.setText("Najmanja novcanica koju bankomat moze da isplati je 1000 dinara.\n Molimo unesite drugi iznos.");
						
						}
						else {
							switchPanel(panel3);
						}
					}
					
					catch(NumberFormatException ex) {
						lblNevalidno1.setText("Nepravilan unos! Pokusajte ponovo!");
					}
				}
				
				if(panel4.isShowing()) {
					
					
					try {
						uplata = Integer.parseInt(txtUplata.getText());
						if(uplata % 1000 != 0) {
							uplata = 0;
							lblNevalidno2.setText("Najmanja novcanica koju mozete uplatiti je 1000 dinara.\n Molimo unesite drugi iznos.");
						}
					
						else {
							switchPanel(panel3);
						}
					}
				
				catch(NumberFormatException ex) {
					lblNevalidno2.setText("Nepravilan unos! Pokusajte ponovo!");
				}
				}
				if(panel2.isShowing() && panelFlag) {
					
					if(brojac == 2 && counter == 0) {
						
						readStanje(pathS1);
						write(pathT1);
						write(pathS1);
						counter = 1;
					}
					if(brojac == 4 && counter == 0) {
						readStanje(pathS2);
						write(pathT2);
						write(pathS2);
						counter = 1;
					}
					if(brojac == 6 && counter == 0){
						readStanje(pathS3);
						write(pathT3);
						write(pathS3);
						counter = 1;
					}
					switchPanel(panel6);
					
					
				}
				
			}
		});
		btn_desno_dole.setFont(new Font("Arial", Font.BOLD, 25));
		btn_desno_dole.setBounds(599, 157, 99, 55);
		frame.getContentPane().add(btn_desno_dole);
		
		JButton btn_lev_dole = new JButton(">>");
		btn_lev_dole.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(panel3.isShowing()) {
					if(isplata > stanje) {
						lblIsplata.setText("Nemate dovoljno sredstava na racunu.");
						
					}
					else {
						lblIsplata.setText("Transakcija uspesno izvrsena!");
						if(brojac == 2 && counter == 0) {
							
							WriteToFile(pathS1);
							WriteToFile(pathT1);
							counter = 1;
						}
						if(brojac == 4 && counter == 0) {
							
							WriteToFile(pathS2);
							WriteToFile(pathT2);
							counter = 1;
						}
						if(brojac == 6 && counter == 0){
							WriteToFile(pathS3);
							WriteToFile(pathT3);
							counter = 1;
						}
					}
				}
				
				
				if(panel2.isShowing()) {
					
					switchPanel(panel5);
				}
				
				if(panel0.isShowing()) {uplata = 0;}
				
			}
		});
		btn_lev_dole.setFont(new Font("Arial", Font.BOLD, 25));
		btn_lev_dole.setBounds(10, 157, 99, 55);
		frame.getContentPane().add(btn_lev_dole);
		
		JButton btn_levo_sredina = new JButton(">>");
		btn_levo_sredina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(panel2.isShowing()) {
					isplata = 2000;
					
					switchPanel(panel3);
				}
			}
		});
		btn_levo_sredina.setFont(new Font("Arial", Font.BOLD, 25));
		btn_levo_sredina.setBounds(10, 91, 99, 55);
		frame.getContentPane().add(btn_levo_sredina);
		
		JButton btn_levo_gore = new JButton(">>");
		btn_levo_gore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(panel2.isShowing()) {
					isplata = 1000;
					switchPanel(panel3);
				}
			}
		});
		btn_levo_gore.setFont(new Font("Arial", Font.BOLD, 25));
		btn_levo_gore.setBounds(10, 25, 99, 55);
		frame.getContentPane().add(btn_levo_gore);
		
		JButton btn_desno_sredina = new JButton("<<");
		btn_desno_sredina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(panel2.isShowing()) {
					switchPanel(panel4);
				}
			}
		});
		btn_desno_sredina.setFont(new Font("Arial", Font.BOLD, 25));
		btn_desno_sredina.setBounds(599, 91, 99, 55);
		frame.getContentPane().add(btn_desno_sredina);
		
		JButton btn_desno_gore = new JButton("<<");
		btn_desno_gore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(panel2.isShowing()) {
					isplata = 5000;
					switchPanel(panel3);
				}

			}
		});
		btn_desno_gore.setFont(new Font("Arial", Font.BOLD, 25));
		btn_desno_gore.setBounds(599, 25, 99, 55);
		frame.getContentPane().add(btn_desno_gore);
		
		JButton btn_glavno_otkazi = new JButton("Otka\u017Ei");
		btn_glavno_otkazi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				switchPanel(panel0);
				switchPanel(panel0);
				passwordField.setText("");
				lblEr.setText("");
				flagPin = false;
				stanje = 0;
				isplata = 0;
				pin = "";
				lblIsplata.setText("");
				txtPolje.setText("");
				counter = 0;
				brojac = 0;
				txtDrugiIznos.setText("");
				panelFlag = false;
				lblNevalidno1.setText("");
				lblNevalidno2.setText("");
				txtUplata.setText("");
				
			}
		});
		btn_glavno_otkazi.setFont(new Font("Arial", Font.BOLD, 20));
		btn_glavno_otkazi.setBounds(119, 421, 150, 55);
		frame.getContentPane().add(btn_glavno_otkazi);
		
		JButton btn_glavno_obrisi = new JButton("Obri\u0161i");
		btn_glavno_obrisi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(panel0.isShowing()) {
					if(passwordField.getText().length() > 0) {
						
						String temp = passwordField.getText();
						temp = temp.substring(0, temp.length() - 1);
						passwordField.setText(temp);
					}
					
				}
				if(panel4.isShowing()) {
					if(txtUplata.getText().length() > 0) {
						
						String temp = txtUplata.getText();
						temp = temp.substring(0, temp.length() - 1);
						txtUplata.setText(temp);
					}
					
				}
				
				if(panel5.isShowing()) {
					if(txtUplata.getText().length() > 0) {
						
						String temp = txtDrugiIznos.getText();
						temp = temp.substring(0, temp.length() - 1);
						txtDrugiIznos.setText(temp);
					}
					
				}
				
			}
		});
		btn_glavno_obrisi.setFont(new Font("Arial", Font.BOLD, 20));
		btn_glavno_obrisi.setBounds(439, 421, 150, 55);
		frame.getContentPane().add(btn_glavno_obrisi);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(718, 25, 294, 360);
		frame.getContentPane().add(scrollPane);
		
		txtPolje = new JTextArea();
		txtPolje.setFont(new Font("Monospaced", Font.BOLD, 15));
		txtPolje.setEditable(false);
		scrollPane.setViewportView(txtPolje);
		
		//String [] niz = new String[] {"1" ,"2"};
	}
}	
