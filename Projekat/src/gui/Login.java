package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.Korisnik;
import model.Lekar;
import model.MedicinskaSestra;
import model.Pacijent;
import net.miginfocom.swing.MigLayout;
import utils.Utils;

public class Login extends JFrame {

	private JLabel lblKorisnickoIme;
	private JLabel lblLozinka;
	private JTextField txtKorisnikoIme;
	private JPasswordField passLozinka;
	private JButton btnLogin;
	private JButton btnOdustani;
	
	
	public Login() {
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Dom zdravlja");
		setResizable(false);
		setSize(500,500);
		Container c = this.getContentPane();
		c.setBackground(new Color(215, 145, 151));
		initGUI();
		initActions();
	}
	
	
	public void initGUI() {
		MigLayout layout = new MigLayout("wrap", "[]25[]", "[]35[][]35[]");
		setLayout(layout);
		this.lblKorisnickoIme = new JLabel("Korisnicko ime");
		this.lblLozinka = new JLabel("Lozinka");
		this.txtKorisnikoIme = new JTextField(23);
		this.passLozinka = new JPasswordField(23);
		this.btnLogin = new JButton("Login");
		this.btnOdustani = new JButton("Odustani");
		this.getRootPane().setDefaultButton(btnLogin);
		lblKorisnickoIme.setForeground(Color.black);
		lblLozinka.setForeground(Color.black);
		btnLogin.setForeground(Color.black);
		btnOdustani.setForeground(Color.black);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("slike/Health.png");
		setIconImage(img);
		
		ImageIcon icon = new ImageIcon("slike/Health.png");
		JLabel lblImage = new JLabel();
		lblImage.setIcon(icon);
		add(new JLabel());
		add(lblImage);
		add(lblKorisnickoIme, "gapleft 20");
		add(txtKorisnikoIme, "wrap");
		add(lblLozinka, "gapleft 20");
		add(passLozinka, "wrap");
		add(new JLabel());
		add(btnLogin, "split 2");
		add(btnOdustani);
	}
	
	
	private void initActions() {
		btnOdustani.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				txtKorisnikoIme.setText("");
				passLozinka.setText("");
				dispose();
			}
		});
		
		btnLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String korisnickoIme = txtKorisnikoIme.getText();
				String lozinka = new String(passLozinka.getPassword());
				Utils.ucitavanjeKorisnika();
				Utils.ucitavanjePregleda();
				
				boolean loggedIn = false;
				for(Korisnik korisnik : Utils.getKorisnici()) {
					if(korisnik.getKorisnickoIme().equals(korisnickoIme) &&
							korisnik.getLozinka().equals(lozinka)) {
						Login.this.setVisible(false);
						Login.this.dispose();
						if(korisnik instanceof Lekar) {
							LekarWindow lekarWindow = new LekarWindow(korisnik);
							lekarWindow.setVisible(true);
						}else if(korisnik instanceof MedicinskaSestra) {
							MedicinskaSestraWindow medicinskaSestraWindow = new MedicinskaSestraWindow(korisnik);
							medicinskaSestraWindow.setVisible(true);
						}else if(korisnik instanceof Pacijent) {
							PacijentWindow pacijentWindow = new PacijentWindow(korisnik);
							pacijentWindow.setVisible(true);
							PacijentWindow.korisnik = (Pacijent) korisnik;
						}else {
							JOptionPane.showMessageDialog(null, "Doslo je do greske, pokusajte ponovo!", "Greska", JOptionPane.WARNING_MESSAGE);
						}
						loggedIn = true;
						break;
					}
				}
				if(loggedIn != true) {
					JOptionPane.showMessageDialog(null, "Uneti podaci nisu validni!", "Greska", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}
	
}
