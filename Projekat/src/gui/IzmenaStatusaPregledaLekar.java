package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import model.Pregled;
import model.StatusPregleda;
import net.miginfocom.swing.MigLayout;
import utils.Utils;

public class IzmenaStatusaPregledaLekar extends JFrame {
	
	private Pregled pregled;
	private JLabel lblStatusPregleda = new JLabel("Status pregleda: ");
	private JComboBox<StatusPregleda> cbStatusPregleda = new JComboBox<StatusPregleda>(StatusPregleda.values());
	private JButton btnIzmena = new JButton("Izmeni");
	
	public IzmenaStatusaPregledaLekar(Pregled pregled) {
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
		
		cbStatusPregleda.removeItem(StatusPregleda.ZATRAZEN);
		cbStatusPregleda.removeItem(StatusPregleda.ZAKAZAN);
		
		add(lblStatusPregleda);
		add(cbStatusPregleda);
		add(new JLabel());
		add(btnIzmena);
	}
	
	private void initActions() {
		btnIzmena.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				StatusPregleda statusPregleda = (StatusPregleda) cbStatusPregleda.getSelectedItem();
				pregled.setStatus(statusPregleda);
				Utils.snimiPregled();
				IzmenaStatusaPregledaLekar.this.dispose();
				IzmenaStatusaPregledaLekar.this.setVisible(false);
			}
		});
	}
}
