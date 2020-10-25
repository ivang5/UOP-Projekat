package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import model.Pregled;
import model.StatusPregleda;
import net.miginfocom.swing.MigLayout;
import utils.Utils;

public class IzmenaPregledaPacijent extends JFrame {

	private Pregled pregled;
	private JLabel lblOpis = new JLabel("Opis: ");
	private JLabel lblStatus = new JLabel("Status: ");
	private JTextField txtOpis = new JTextField(40);
	private JComboBox<StatusPregleda> cbStatusPregleda = new JComboBox<StatusPregleda>(StatusPregleda.values());
	private JButton btnIzmena = new JButton("Izmeni");
	
	public IzmenaPregledaPacijent(Pregled pregled) {
			this.pregled = pregled;
			setTitle("Pregled: " + this.pregled.getId());
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
		MigLayout layout = new MigLayout("wrap 2, center, center");
		setLayout(layout);
		
		if(this.pregled != null) {
			popunjavanjePolja();
		}
		
		if(this.pregled.getStatus().equals(StatusPregleda.ZAKAZAN)) {
			txtOpis.setEditable(false);
			cbStatusPregleda.removeItem(StatusPregleda.ZAVRSEN);
			cbStatusPregleda.removeItem(StatusPregleda.ZATRAZEN);
		}
		
		if(this.pregled.getStatus().equals(StatusPregleda.ZATRAZEN)) {
			cbStatusPregleda.removeItem(StatusPregleda.ZAKAZAN);
			cbStatusPregleda.removeItem(StatusPregleda.ZAVRSEN);
		}
		
		add(lblOpis);
		add(lblStatus);
		add(txtOpis);
		add(cbStatusPregleda);
		add(new JLabel());
		add(btnIzmena);
	}
	
	private void popunjavanjePolja() {
		txtOpis.setText(this.pregled.getOpis());
		cbStatusPregleda.setSelectedItem(this.pregled.getStatus());
	}
	
	private void initActions() {
		btnIzmena.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String opisPregleda = txtOpis.getText();
				StatusPregleda statusPregleda = (StatusPregleda) cbStatusPregleda.getSelectedItem();
				pregled.setOpis(opisPregleda);
				pregled.setStatus(statusPregleda);
				Utils.snimiPregled();
				IzmenaPregledaPacijent.this.dispose();
				IzmenaPregledaPacijent.this.setVisible(false);
			}
		});
	}
}
