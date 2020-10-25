package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import model.MedicinskaSestra;
import net.miginfocom.swing.MigLayout;
import utils.Utils;

public class MedicinskaSestraMedSes extends JFrame {
	
	private MedicinskaSestra medicinskaSestra;
	private static JTable tableMedicinskeSestre;
	private JButton btnIzmeni = new JButton("Izmeni");
	private JButton btnDodaj = new JButton("Dodaj");
	private JButton btnObrisi = new JButton("Obrisi");
	
	public MedicinskaSestraMedSes() {
		setTitle("Medicinske sestre");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(1000,500);
		setLocationRelativeTo(null);
		setResizable(false);
		Container c = this.getContentPane();
		c.setBackground(new Color(215, 145, 151));
		initGUI();
	}
	
	private void initGUI() {
		MigLayout layout = new MigLayout("wrap", "[]", "[]5[]");
		setLayout(layout);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("slike/Health.png");
		setIconImage(img);
		
		String[] header = new String[] { "Ime", "Prezime", "JMBG", "Pol", "Adresa", "Br. telefona", "Korisnicko ime",
				"Lozinka", "Uloga", "Plata", "Sluzba" };
		
		int velicina = Utils.getMedicinskeSestre().size();
		Object[][] content = new Object[velicina][header.length];
		for(int i = 0; i < velicina; i++) {
			MedicinskaSestra medicinskaSestra = Utils.getMedicinskeSestre().get(i);
			content[i][0] = medicinskaSestra.getIme();
			content[i][1] = medicinskaSestra.getPrezime();
			content[i][2] = medicinskaSestra.getJmbg();
			content[i][3] = medicinskaSestra.getPol();
			content[i][4] = medicinskaSestra.getAdresa();
			content[i][5] = medicinskaSestra.getBrojTelefona();
			content[i][6] = medicinskaSestra.getKorisnickoIme();
			content[i][7] = medicinskaSestra.getLozinka();
			content[i][8] = medicinskaSestra.getUloga();
			content[i][9] = medicinskaSestra.getPlata();
			content[i][10] = medicinskaSestra.getSluzba();
		}
		
		DefaultTableModel model = new DefaultTableModel(content, header);
		tableMedicinskeSestre = new JTable(model);
		tableMedicinskeSestre.setRowSelectionAllowed(true);
		tableMedicinskeSestre.setColumnSelectionAllowed(false);
		tableMedicinskeSestre.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableMedicinskeSestre.setDefaultEditor(Object.class, null);
		JScrollPane scrollPane = new JScrollPane(tableMedicinskeSestre);
		scrollPane.setPreferredSize(new Dimension(1000, 400));
		scrollPane.setMaximumSize(new Dimension(1000, 400));
		add(scrollPane, BorderLayout.CENTER);
		btnIzmeni = new JButton("Izmeni");
		btnDodaj = new JButton("Dodaj");
		btnObrisi = new JButton("Obrisi");
		add(btnIzmeni, "split 3, right");
		add(btnDodaj);
		add(btnObrisi);
		
		btnDodaj.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				IzmenaKreiranjeMS ikms = new IzmenaKreiranjeMS(medicinskaSestra);
				ikms.setVisible(true);
			}
		});
		
		btnIzmeni.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int red = tableMedicinskeSestre.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Nije selektovan nijedan red!", "Greska",
							JOptionPane.WARNING_MESSAGE);
				}else {
					DefaultTableModel model = (DefaultTableModel) tableMedicinskeSestre.getModel();
					String korisnickoIme = model.getValueAt(red, 6).toString();
					MedicinskaSestra medicinskaSestra = Utils.pronadjiMedicinskuSestru(korisnickoIme);
					if(medicinskaSestra != null) {
						IzmenaKreiranjeMS ikms = new IzmenaKreiranjeMS(medicinskaSestra);
						ikms.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null, "Medicinska sestra nije pronadjena, pokusajte ponovo!", "Greska",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		btnObrisi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int red = tableMedicinskeSestre.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Nije selektovan nijedan red!", "Greska",
							JOptionPane.WARNING_MESSAGE);
				}else {
					String korisnickoIme = tableMedicinskeSestre.getValueAt(red, 6).toString();
					MedicinskaSestra medicinskaSestra = Utils.pronadjiMedicinskuSestru(korisnickoIme);
					if(medicinskaSestra != null) {
						int izabrana = JOptionPane.showConfirmDialog(null, "Da li ste sigurni?", "Brisanje medicinske sestre", JOptionPane.YES_NO_OPTION);
						if(izabrana == JOptionPane.YES_OPTION) {
							Utils.getMedicinskeSestre().remove(medicinskaSestra);
							DefaultTableModel model = (DefaultTableModel) tableMedicinskeSestre.getModel();
							model.removeRow(red);
							Utils.snimiMedicinskeSestre();
						}
					}
					
				}
			}
		});
	}
	
	public static void DodajRedUTabelu(Object[] dataRow) {
		DefaultTableModel model = (DefaultTableModel)tableMedicinskeSestre.getModel();
		model.addRow(dataRow);
	}
}
