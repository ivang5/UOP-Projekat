package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import model.Korisnik;
import model.Pacijent;
import model.Pregled;
import model.StatusPregleda;
import net.miginfocom.swing.MigLayout;
import utils.Utils;

public class PacijentWindow extends JFrame{
	
	private Korisnik pacijent;
	public static Pacijent korisnik;
	
	private static ArrayList<Pregled> listaPregleda = new ArrayList<Pregled>();
	private static JTable tablePacijentPregledi;
	private JButton btnZakazivanje = new JButton("Zakazi novi pregled");
	private JButton btnIzmeni = new JButton("Izmeni status i opis pregleda");
	private Pregled pregled;
	
	public PacijentWindow(Korisnik pacijent) {
		this.pacijent = pacijent;
		setTitle(this.pacijent.getKorisnickoIme() + " - pregledi");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(1000,500);
		setLocationRelativeTo(null);
		setResizable(false);
		Container c = this.getContentPane();
		c.setBackground(new Color(215, 145, 151));
		initGUI();
	}
	
	public void initGUI() {
		MigLayout layout = new MigLayout("wrap", "[]", "[]5[]");
		setLayout(layout);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("slike/Health.png");
		setIconImage(img);
		
		listaPregleda.clear();
		for(Pregled pregled:Utils.getPregledi()) {
			if(pregled.getPacijent().getKorisnickoIme().equals(pacijent.getKorisnickoIme())) {
				listaPregleda.add(pregled);
			}
		}
		
		String[] header = new String[] {"ID","Pacijent", "Lekar", "Termin", "Soba", "Opis", "Status"};
		
		int velicina = listaPregleda.size();
		Object [][]content = new Object[velicina][header.length];
		for(int i = 0; i < velicina; i++) {
			Pregled pregled = listaPregleda.get(i);
			content[i][0] = pregled.getId();
			content[i][1] = pregled.getPacijent().getKorisnickoIme();
			content[i][2] = pregled.getLekar().getKorisnickoIme();
			content[i][3] = pregled.getTermin();
			content[i][4] = pregled.getSoba();
			content[i][5] = pregled.getOpis();
			content[i][6] = pregled.getStatus();
		}
		
		DefaultTableModel model = new DefaultTableModel(content, header);
		tablePacijentPregledi = new JTable(model);
		tablePacijentPregledi.setRowSelectionAllowed(true);
		tablePacijentPregledi.setColumnSelectionAllowed(false);
		tablePacijentPregledi.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		tablePacijentPregledi.setDefaultEditor(Object.class, null);
		JScrollPane scrollPane = new JScrollPane(tablePacijentPregledi);
		scrollPane.setPreferredSize(new Dimension(1000, 400));
		scrollPane.setMaximumSize(new Dimension(1000, 400));
		add(scrollPane, BorderLayout.CENTER);
		add(btnZakazivanje, "split 2, right");
		add(btnIzmeni);
		
		btnIzmeni.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int red = tablePacijentPregledi.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Nije selektovan nijedan red!", "Greska",
							JOptionPane.WARNING_MESSAGE);
				}else {
					DefaultTableModel model = (DefaultTableModel) tablePacijentPregledi.getModel();
					int id = Integer.parseInt(model.getValueAt(red, 0).toString());
					Pregled pregled = Utils.pronadjiPregled(id);
					if(pregled != null && !pregled.getStatus().equals(StatusPregleda.OTKAZAN) && !pregled.getStatus().equals(StatusPregleda.ZAVRSEN)) {
						IzmenaPregledaPacijent ispp = new IzmenaPregledaPacijent(pregled);
						ispp.setVisible(true);
					}else {JOptionPane.showMessageDialog(null, "Ovaj pregled je otkazan ili zavrsen.", "Greska",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		btnZakazivanje.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// TODO Auto-generated method stub
				ZakazivanjePregleda zakazivanjePrelgeda = new ZakazivanjePregleda(pregled,korisnik);
				zakazivanjePrelgeda.setVisible(true);
			}
		});
	}
	
	public static void DodajRedUTabelu(Object[] dataRow) {
		DefaultTableModel model = (DefaultTableModel)tablePacijentPregledi.getModel();
		model.addRow(dataRow);
	}
}
