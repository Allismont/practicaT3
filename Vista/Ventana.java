package Vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controlador.Ejecutador;
import modelo.BBDD;
import modelo.Sistema;

import java.awt.CardLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class Ventana extends JFrame {

	private JPanel contentPane;
	DefaultTableModel modeloTabla = new DefaultTableModel();

	/**
	 * Launch the application.
	 */
	public static void cargaVentana(Ventana frame) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Ventana() {
		setTitle("DATOS DE PAISES");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Ventana.class.getResource("/img/tierra.png")));
		//Creamos el objeto de tipo BBDD
		BBDD bd = new BBDD();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1034, 694);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnOpciones = new JMenu("Opciones");
		menuBar.add(mnOpciones);
		
		JMenuItem mntmConsulta = new JMenuItem("Consultar");
		mntmConsulta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Consultar c = new Consultar();
				nuevoPanel(c);
			}
		});
		mnOpciones.add(mntmConsulta);
		
		JSeparator separator = new JSeparator();
		mnOpciones.add(separator);
		
		JMenuItem mntmModifica = new JMenuItem("Modificar");
		mntmModifica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Consultar c = new Consultar();
				nuevoPanel(c);
			}
		});
		mnOpciones.add(mntmModifica);
		
		JSeparator separator_1 = new JSeparator();
		mnOpciones.add(separator_1);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Borrar");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Consultar c = new Consultar();
				nuevoPanel(c);
			}
		});
		mnOpciones.add(mntmNewMenuItem_1);
		
		JSeparator separator_2 = new JSeparator();
		mnOpciones.add(separator_2);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Insertar");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Insertar in = new Insertar();
				nuevoPanel(in);
			}
		});
		mnOpciones.add(mntmNewMenuItem_2);
		
		JMenu mnNewMenu_1 = new JMenu("|");
		menuBar.add(mnNewMenu_1);
		
		JMenu mnAcerca = new JMenu("Acerca de");
		mnAcerca.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Consultar c = new Consultar();
				nuevoPanel(c);
				JOptionPane.showMessageDialog(null,"Nombre: Allison \nApellido: Montenegro \n"
					+ "Instituto: CFP Juan XXIII \nCiclo formativo: Grado Superior Desarrollo De "
						+ "Aplicaciones WEB \n profesor: Gonzalo de Tomás \n Curso: 2022 - 2023 ");
			}
		});
		
		menuBar.add(mnAcerca);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("BIENVENIDO USUARIO");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setIcon(new ImageIcon(Ventana.class.getResource("/img/SALUDO.png")));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 35));
		contentPane.add(lblNewLabel, "name_2200063923682100");
		
		
	}
	
	public void nuevoPanel(JPanel panelActual) {
		contentPane.removeAll();
		contentPane.add(panelActual);
		contentPane.repaint();
		contentPane.revalidate();
	}
}
