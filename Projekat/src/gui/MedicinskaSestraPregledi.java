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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import model.KategorijaOsiguranja;
import model.Pregled;
import model.StatusPregleda;
import model.ZdravstvenaKnjizica;
import net.miginfocom.swing.MigLayout;
import utils.Utils;

public class MedicinskaSestraPregledi extends JFrame {
	
	private Pregled pregled;
	private static JTable tablePregledi;
	private JLabel lblRacun = new JLabel("Iznos racuna: ");
	private JTextField txtRacun = new JTextField(10);
	private JButton btnObracunaj = new JButton("Obracunaj");
	private JButton btnZakazivanje = new JButton("Zakazi pregled");
	private JButton btnPotvrdaPregleda = new JButton("Odobri zatrazen pregled");
	private JButton btnObrisi = new JButton("Obrisi pregled");
	
	public MedicinskaSestraPregledi() {
		setTitle("Pregledi");
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
		
		String[] header = new String[] {"ID","Pacijent", "Lekar", "Termin", "Soba", "Opis", "Status"};
		
		int velicina = Utils.getPregledi().size();
		Object[][] content = new Object[velicina][header.length];
		for(int i = 0; i < velicina; i++) {
			Pregled pregled = Utils.getPregledi().get(i);
			content[i][0] = pregled.getId();
			content[i][1] = pregled.getPacijent().getKorisnickoIme();
			content[i][2] = pregled.getLekar().getKorisnickoIme();
			content[i][3] = pregled.getTermin();
			content[i][4] = pregled.getSoba();
			content[i][5] = pregled.getOpis();
			content[i][6] = pregled.getStatus();
		}
		
		DefaultTableModel model = new DefaultTableModel(content, header);
		tablePregledi = new JTable(model);
		tablePregledi.setRowSelectionAllowed(true);
		tablePregledi.setColumnSelectionAllowed(false);
		tablePregledi.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		tablePregledi.setDefaultEditor(Object.class, null);
		JScrollPane scrollPane = new JScrollPane(tablePregledi);
		scrollPane.setPreferredSize(new Dimension(1000, 400));
		scrollPane.setMaximumSize(new Dimension(1000, 400));
		add(scrollPane, BorderLayout.CENTER);
		add(btnObracunaj, "left");
		add(lblRacun, "split 2, left");
		add(txtRacun);
		add(btnZakazivanje, "split 3,right");
		add(btnPotvrdaPregleda);
		add(btnObrisi);
//		add(new JLabel());
		
		btnObracunaj.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int red = tablePregledi.getSelectedRow();
				double prva = 300.00;
				double druga = 50.00;
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Nije selektovan nijedan red!", "Greska",
							JOptionPane.WARNING_MESSAGE);
				}else {
					DefaultTableModel model = (DefaultTableModel) tablePregledi.getModel();
					int id = Integer.parseInt(model.getValueAt(red, 0).toString());
					Pregled pregled = Utils.pronadjiPregled(id);
					ZdravstvenaKnjizica knjizica = Utils.pronadjiZdravstvenuKnjizicu(pregled.getPacijent().getZdravstvenaKnjizica().getBroj());
					if(pregled != null && pregled.getStatus().equals(StatusPregleda.ZAVRSEN)) {
						knjizica.getKategorijaOsiguranja();
						if(knjizica.getKategorijaOsiguranja().equals(KategorijaOsiguranja.PRVA)) {
							txtRacun.setText(String.valueOf(prva) + " RSD");
						}else if(knjizica.getKategorijaOsiguranja().equals(KategorijaOsiguranja.DRUGA)) {
							txtRacun.setText(String.valueOf(druga) + " RSD");
						}else if(knjizica.getKategorijaOsiguranja().equals(KategorijaOsiguranja.TRECA)) {
							txtRacun.setText("Besplatno");
						}
					}else {
						JOptionPane.showMessageDialog(null, "Selektovani pregled nije zavrsen!", "Greska",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		btnZakazivanje.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ZakazivanjePregledaMS zpms = new ZakazivanjePregledaMS(pregled);
				zpms.setVisible(true);
			}
		});
		
		btnPotvrdaPregleda.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int red = tablePregledi.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Nije selektovan nijedan red!", "Greska",
							JOptionPane.WARNING_MESSAGE);
				}else {
					DefaultTableModel model = (DefaultTableModel) tablePregledi.getModel();
					int id = Integer.parseInt(model.getValueAt(red, 0).toString());
					Pregled pregled = Utils.pronadjiPregled(id);
					if(pregled != null && pregled.getStatus().equals(StatusPregleda.ZATRAZEN)) {
						ZakazivanjePregledaMS pf = new ZakazivanjePregledaMS(pregled);
						pf.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null, "Selektovani pregled nije Zatrazen!", "Greska",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		btnObrisi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int red = tablePregledi.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Nije selektovan nijedan red!", "Greska",
							JOptionPane.WARNING_MESSAGE);
				}else {
					int id = Integer.parseInt(tablePregledi.getValueAt(red, 0).toString());
					Pregled pregled = Utils.pronadjiPregled(id);
					if(pregled!=null) {
						int izabran = JOptionPane.showConfirmDialog(null, "Da li ste sigurni?", "Brisanje pregleda", JOptionPane.YES_NO_OPTION);
						if(izabran == JOptionPane.YES_OPTION) {
							Utils.getPregledi().remove(pregled);
							DefaultTableModel model = (DefaultTableModel) tablePregledi.getModel();
							model.removeRow(red);
							Utils.snimiPregled();
						}
					}
				}
			}
		});
		
	}
	
	public static void DodajRedUTabelu(Object[] dataRow) {
		DefaultTableModel model = (DefaultTableModel)tablePregledi.getModel();
		model.addRow(dataRow);
	}
}
