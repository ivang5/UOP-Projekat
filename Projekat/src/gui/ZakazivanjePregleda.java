package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.Pacijent;
import model.Pregled;
import model.StatusPregleda;
import model.Uloga;
import net.miginfocom.swing.MigLayout;
import utils.Utils;

public class ZakazivanjePregleda extends JFrame {

	private Pacijent pacijent;
	private JLabel lblOpis = new JLabel("Kratak opis: ");
	private JTextField txtOpis = new JTextField(40);
	private JButton btnZakazivanje = new JButton("Zakazi");
	private static SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd.MM.yyyy. HH:mm");
	
	public ZakazivanjePregleda(Pregled pregled, Pacijent pacijent) {
		this.pacijent = pacijent;
		setTitle("Zakazivanje pregleda");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(300,300);
		setLocationRelativeTo(null);
		setResizable(false);
		Container c = this.getContentPane();
		c.setBackground(new Color(215, 145, 151));
		initGUI();
		initActions();
	}
	
	private void initGUI() {
		MigLayout layout = new MigLayout("wrap 2");
		setLayout(layout);
		
		add(lblOpis);
		add(txtOpis);
		add(new JLabel());
		add(btnZakazivanje);
	}
	
	private void initActions() {
		btnZakazivanje.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Pregled noviPregled = new Pregled();
				noviPregled.setId(Utils.getPregledi().size() + 1);
				
				if(txtOpis.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "Morate uneti opis pregleda!", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					noviPregled.setPacijent(pacijent);
					noviPregled.setLekar(pacijent.getIzabraniLekar());
					java.util.Date termin;
					try {
						termin = DATE_TIME_FORMAT.parse("00.00.0000. 00:00");
						noviPregled.setTermin(termin);
					} catch (Exception e1) {
						// TODO: handle exception
						e1.printStackTrace();
					}
					noviPregled.setSoba("");
					noviPregled.setOpis(txtOpis.getText());
					noviPregled.setStatus(StatusPregleda.ZATRAZEN);
					Utils.snimiPregledUListu(noviPregled);
					PacijentWindow.DodajRedUTabelu(new Object[] {
							noviPregled.getId(),
							noviPregled.getPacijent().getKorisnickoIme(),
							noviPregled.getLekar().getKorisnickoIme(),
							noviPregled.getTermin(),
							"",
							noviPregled.getOpis(),
							StatusPregleda.ZATRAZEN
						});
					dispose();
				}
			}
		});
	}
}
