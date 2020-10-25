package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.Pregled;
import model.StatusPregleda;
import model.Uloga;
import net.miginfocom.swing.MigLayout;
import utils.Utils;

public class ZakazivanjePregledaMS extends JFrame {

	private Pregled pregled;
	private static SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd.MM.yyyy. HH:mm");
	private JLabel lblPacijent = new JLabel("Pacijent:");
	private JLabel lblLekar = new JLabel("Lekar:");
	private JLabel lblTermin = new JLabel("Termin pregleda");
	private JLabel lblSoba = new JLabel("Soba:");
	private JLabel lblOpis = new JLabel("Kratak opis:");
	private JLabel lblStatus = new JLabel("Status:");
	private JComboBox cbPacijenti;
	private JComboBox cbLekari;
	private static JTextField txtTermin = new JTextField(15);
	private JTextField txtSoba = new JTextField(10);
	private JTextField txtOpis = new JTextField(30);
	private JComboBox<StatusPregleda> cmbStatus = new JComboBox<StatusPregleda>(StatusPregleda.values());
	private JButton btnPotvrdi1 = new JButton("Potvrdi");
	private JButton btnPotvrdi2 = new JButton("Potvrdi");
	
	public ZakazivanjePregledaMS(Pregled pregled) {
		this.pregled = pregled;
		if(this.pregled == null) {
			setTitle("Zakazivanje pregleda");
		}else {
			setTitle("Potvrda pregleda");
			txtOpis.setEditable(false);
		}
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(300,400);
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
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("slike/Health.png");
		setIconImage(img);
		
		if(this.pregled != null) {
			popunjavanjePolja();
		}
		
		ArrayList<String> lekari = new ArrayList<String>();
		try {
			lekari = Utils.preuzmiKorImeLekara();
		} catch (FileNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		this.cbLekari = new JComboBox(lekari.toArray());
		cbLekari.setSelectedIndex(0);
		
		ArrayList<String> pacijenti = new ArrayList<String>();
		try {
			pacijenti = Utils.preuzmiKorImePacijenta();
		}catch (FileNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		this.cbPacijenti = new JComboBox(pacijenti.toArray());
		cbPacijenti.setSelectedIndex(0);
		
		cmbStatus.removeItem(StatusPregleda.OTKAZAN);
		cmbStatus.removeItem(StatusPregleda.ZATRAZEN);
		cmbStatus.removeItem(StatusPregleda.ZAVRSEN);
		
		if(this.pregled == null) {
			setTitle("Zakazivanje pregleda");
			add(lblPacijent);
			add(cbPacijenti);
			add(lblLekar);
			add(cbLekari);
			add(lblTermin);
			add(txtTermin);
			add(lblSoba);
			add(txtSoba);
			add(lblOpis);
			add(txtOpis);
			add(lblStatus);
			add(cmbStatus);
			add(new JLabel());
			add(btnPotvrdi1);
		}else {
			add(lblTermin);
			add(txtTermin);
			add(lblSoba);
			add(txtSoba);
			add(lblOpis);
			add(txtOpis);
			add(new JLabel());
			add(btnPotvrdi2);
		}
		
	}
	
	private void popunjavanjePolja() {
		txtTermin.setText("");
		txtSoba.setText("");
		txtOpis.setText(this.pregled.getOpis());
	}
	
	private void initActions() {
		btnPotvrdi1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Pregled noviPregled = new Pregled();
				noviPregled.setId(Utils.getPregledi().size() + 1);
				
				if(validacija() == true) {
					noviPregled.setPacijent(Utils.pronadjiPacijenta(cbPacijenti.getSelectedItem().toString()));
					noviPregled.setLekar(Utils.pronadjiLekara(cbLekari.getSelectedItem().toString()));
					java.util.Date dateTime = null;
					while(dateTime == null) {
						String termin = txtTermin.getText();
						try {
							dateTime = DATE_TIME_FORMAT.parse(termin);
							boolean proba123 = false;
							for(Pregled pregled:Utils.getPregledi()) {
								long razlika = pregled.getTermin().getTime() - dateTime.getTime();
								if(pregled.getLekar().equals(noviPregled.getLekar()) && razlika > 0 && razlika < 900000) {
									JOptionPane.showMessageDialog(null, "Lekar vec ima zakazan pregled u periodu od 15 minuta nakon odabranog vremena!", "Greska", JOptionPane.WARNING_MESSAGE);
									return;
								}else {
									proba123 = true;
								}
							}
							if(proba123 == true) {
								noviPregled.setTermin(dateTime);
							}
						} catch (Exception e2) {
							// TODO: handle exception
							JOptionPane.showMessageDialog(null, "Datum mora da bude u\ndd.MM.yyyy. HH:mm\nformatu.", "Greska", JOptionPane.WARNING_MESSAGE);
							txtTermin.setText("");
							return;
						}
					}
					noviPregled.setSoba(txtSoba.getText());
					noviPregled.setOpis(txtOpis.getText());
					noviPregled.setStatus(StatusPregleda.ZAKAZAN);
					Utils.snimiPregledUListu(noviPregled);
					MedicinskaSestraPregledi.DodajRedUTabelu(new Object[] {
							noviPregled.getId(),
							noviPregled.getPacijent().getKorisnickoIme(),
							noviPregled.getLekar().getKorisnickoIme(),
							noviPregled.getTermin(),
							txtSoba.getText(),
							txtOpis.getText(),
							StatusPregleda.ZAKAZAN
						});
					dispose();
				}
			}
		});
		
		btnPotvrdi2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(validacija() == true) {
					java.util.Date dateTime = null;
					while(dateTime == null) {
						String tekst = txtTermin.getText();
						try {
							dateTime = DATE_TIME_FORMAT.parse(tekst);
							boolean proba123 = false;
							for(Pregled pregled:Utils.getPregledi()) {
								long razlika = pregled.getTermin().getTime() - dateTime.getTime();
								if(pregled.getLekar().equals(pregled.getLekar()) && razlika > 0 && razlika < 900000) {
									JOptionPane.showMessageDialog(null, "Lekar vec ima zakazan pregled u periodu od 15 minuta nakon odabranog vremena!", "Greska", JOptionPane.WARNING_MESSAGE);
									return;
								}else {
									proba123 = true;
								}
							}
							if(proba123 == true) {
								pregled.setTermin(dateTime);
							}
						}catch (Exception e1) {
							// TODO: handle exception
							JOptionPane.showMessageDialog(null, "Datum treba biti u\ndd.MM.yyyy. HH:mm\nformatu.", "Greska", JOptionPane.WARNING_MESSAGE);
							txtTermin.setText("");
							return;
						}
					}
					String soba = txtSoba.getText().trim();
					String opis = txtOpis.getText().trim();
					StatusPregleda status = StatusPregleda.ZAKAZAN;
					pregled.setSoba(soba);
					pregled.setOpis(opis);
					pregled.setStatus(status);
					Utils.snimiPregled();
					ZakazivanjePregledaMS.this.dispose();
					ZakazivanjePregledaMS.this.setVisible(false);
				}
			}
		});
	}
	
	private boolean validacija() {
		boolean ok = true;
		String poruka = "Morate popuniti ";
		
		if(txtTermin.getText().trim().equals("")) {
			poruka += "Termin;\n";
			ok = false;
		}
		if (txtSoba.getText().trim().equals("")) {
			poruka += "Soba;\n";
			ok = false;
		}
		if (txtOpis.getText().trim().equals("")) {
			poruka += "Opis;\n";
			ok = false;
		}
		if (ok == false) {
			JOptionPane.showMessageDialog(null, poruka, "Greska", JOptionPane.WARNING_MESSAGE);
		}
		return ok;
	}
}
