package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.Lekar;
import model.Pol;
import model.Sluzba;
import model.Uloga;
import net.miginfocom.swing.MigLayout;
import utils.Utils;

public class IzmenaKreiranjeLekara extends JFrame {

	private Lekar lekar;
	private JLabel lblIme = new JLabel("Ime:");
	private JLabel lblPrezime = new JLabel("Prezime:");
	private JLabel lblJmbg = new JLabel("JMBG:");
	private JLabel lblPol = new JLabel("Pol:");
	private JLabel lblAdresa = new JLabel("Adresa:");
	private JLabel lblTelefon = new JLabel("Br. telefona:");
	private JLabel lblKorisnickoIme = new JLabel("Korisnicko ime:");
	private JLabel lblLozinka = new JLabel("Lozinka:");
	private JLabel lblPlata = new JLabel("Plata:");
	private JLabel lblSpecijalizacija = new JLabel("Specijalizacija:");
	private JLabel lblSluzba = new JLabel("Sluzba:");
	private JTextField txtIme = new JTextField(10);
	private JTextField txtPrezime = new JTextField(10);
	private JTextField txtJmbg = new JTextField(10);
	private JComboBox<Pol> cbPol = new JComboBox<Pol>(Pol.values());
	private JTextField txtAdresa = new JTextField(10);
	private JTextField txtTelefon = new JTextField(10);
	private JTextField txtKorisnickoIme = new JTextField(10);
	private JTextField txtLozinka = new JTextField(10);
	private JTextField txtPlata = new JTextField(10);
	private JTextField txtSpecijalizacija = new JTextField(10);
	private JComboBox<Sluzba> cbSluzba = new JComboBox<Sluzba>(Sluzba.values());
	private JButton btnPotvrdi = new JButton("Potvrdi");
	
	public IzmenaKreiranjeLekara(Lekar lekar) {
		this.lekar = lekar;
		if(this.lekar == null) {
			setTitle("Kreiranje lekara");
		}else {
			setTitle(this.lekar.getKorisnickoIme() + " - izmena");
		}
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(300, 600);
		setLocationRelativeTo(null);
		setResizable(false);
		Container c = this.getContentPane();
		c.setBackground(new Color(215, 145, 151));
		initGUI();
		initActions();
	}
	
	private void initGUI() {
		MigLayout layout = new MigLayout("wrap");
		setLayout(layout);
		
		if(this.lekar != null) {
			popunjavanjePolja();
		}
		
		cbSluzba.removeItem(Sluzba.PRAVNI_I_EKONOMSKI_POSLOVI);
		cbSluzba.removeItem(Sluzba.TEHNICKI_POSLOVI);
		
		add(lblIme);
		add(txtIme);
		add(lblPrezime);
		add(txtPrezime);
		add(lblJmbg);
		add(txtJmbg);
		add(lblPol);
		add(cbPol);
		add(lblAdresa);
		add(txtAdresa);
		add(lblTelefon);
		add(txtTelefon);
		add(lblKorisnickoIme);
		add(txtKorisnickoIme);
		add(lblLozinka);
		add(txtLozinka);
		add(lblPlata);
		add(txtPlata);
		add(lblSpecijalizacija);
		add(txtSpecijalizacija);
		add(lblSluzba);
		add(cbSluzba);
		add(new JLabel());
		add(btnPotvrdi);
	}
	
	private void popunjavanjePolja() {
		txtIme.setText(this.lekar.getIme());
		txtPrezime.setText(this.lekar.getPrezime());
		txtJmbg.setText(this.lekar.getJmbg());
		cbPol.setSelectedItem(this.lekar.getPol());
		txtAdresa.setText(this.lekar.getAdresa());
		txtTelefon.setText(this.lekar.getBrojTelefona());
		txtKorisnickoIme.setText(this.lekar.getKorisnickoIme());
		txtLozinka.setText(this.lekar.getLozinka());
		txtPlata.setText(String.valueOf(this.lekar.getPlata()));
		txtSpecijalizacija.setText(this.lekar.getSpecijalizacija());
		cbSluzba.setSelectedItem(this.lekar.getSluzba());
	}
	
	private void initActions() {
		btnPotvrdi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(validacija() == true) {
					String ime = txtIme.getText().trim();
					String prezime = txtPrezime.getText().trim();
					String jmbg = txtJmbg.getText().trim();
					Pol pol = (Pol) cbPol.getSelectedItem();
					String adresa = txtAdresa.getText().trim();
					String brojTelefona = txtTelefon.getText().trim();
					String korisnickoIme = txtKorisnickoIme.getText().trim();
					String lozinka = txtLozinka.getText().trim();
					Uloga uloga = Uloga.LEKAR;
					double plata = Double.parseDouble(txtPlata.getText().trim());
					String specijalizacija = txtSpecijalizacija.getText().trim();
					Sluzba sluzba = (Sluzba) cbSluzba.getSelectedItem();
					if(lekar == null) {
						lekar = new Lekar(ime, prezime, jmbg, pol, adresa, brojTelefona, korisnickoIme, lozinka, uloga, plata, specijalizacija, sluzba);
						Utils.getLekari().add(lekar);
						MedicinskaSestraLekari.DodajRedUTabelu(new Object[] {
								txtIme.getText(),
								txtPrezime.getText(),
								txtJmbg.getText(),
								cbPol.getSelectedItem(),
								txtAdresa.getText(),
								txtTelefon.getText(),
								txtKorisnickoIme.getText(),
								txtLozinka.getText(),
								Uloga.LEKAR,
								txtPlata.getText(),
								txtSpecijalizacija.getText(),
								cbSluzba.getSelectedItem()
							});
					}else {
						lekar.setIme(ime);
						lekar.setPrezime(prezime);
						lekar.setJmbg(jmbg);
						lekar.setPol(pol);
						lekar.setAdresa(adresa);
						lekar.setBrojTelefona(brojTelefona);
						lekar.setKorisnickoIme(korisnickoIme);
						lekar.setLozinka(lozinka);
						lekar.setUloga(uloga);
						lekar.setPlata(plata);
						lekar.setSpecijalizacija(specijalizacija);
						lekar.setSluzba(sluzba);
						
					}				
					Utils.snimiLekare();
					IzmenaKreiranjeLekara.this.dispose();
					IzmenaKreiranjeLekara.this.setVisible(false);
				}
			}
		});
	}
	
	private boolean validacija() {
		boolean ok = true;
		String poruka = "Morate popuniti ";
		if (txtIme.getText().trim().equals("")) {
			poruka += "Ime;\n";
			ok = false;
		}
		if (txtPrezime.getText().trim().equals("")) {
			poruka += "Prezime;\n";
			ok = false;
		}
		if (txtKorisnickoIme.getText().trim().equals("")) {
			poruka += "Korisnicko ime;\n";
			ok = false;
		}
		
		if (txtLozinka.getText().trim().equals("")) {
			poruka += "Lozinka;\n";
			ok = false;
		}
		if (txtPlata.getText().trim().equals("")) {
			poruka += "Plata;\n";
			ok = false;
		}

		if (txtJmbg.getText().trim().equals("")) {
			poruka += "JMBG;\n";
			ok = false;
		}
		if (txtAdresa.getText().trim().equals("")) {
			poruka += "Adresa;\n";
			ok = false;
		}
		if (txtTelefon.getText().trim().equals("")) {
			poruka += "Broj telefona;\n";
			ok = false;
		}
		if (txtSpecijalizacija.getText().trim().equals("")) {
			poruka += "Specijalizacija\n";
			ok = false;
		}

		if (ok == false) {
			JOptionPane.showMessageDialog(null, poruka, "Greska", JOptionPane.WARNING_MESSAGE);
		}
		return ok;
	}
}
