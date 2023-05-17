package Vista;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;

import controlador.Ejecutador;

import modelo.BBDD;
import modelo.Exportar;
import modelo.Sistema;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.JToolBar;
import javax.swing.ListCellRenderer;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.io.File;
import java.io.IOException;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Consultar extends JPanel {
	private JComboBox cmbPais;
	private JComboBox cmbDistritos;
	private JTextField textCiudad;
	private JTable tablaDatos;
	private JSlider sliderPoblacion;
	DefaultTableModel modeloTabla = new DefaultTableModel();

	
	BBDD bd = new BBDD();
	Exportar exp = new Exportar();
	private JScrollPane scrollPane;
	private JRadioButton rdbtnNombre;
	private JRadioButton rdbtnPais;
	private JRadioButton rdbtnDistrito;
	private JRadioButton rdbtnPoblacion;
	private JLabel lblEXCEL;
	private JLabel lblXML;
	private JLabel lblSQL;

	public Consultar() {

		setLayout(null);
		textCiudad = new JTextField();
		textCiudad.setBounds(422, 186, 235, 20);
		add(textCiudad);
		textCiudad.setColumns(10);

		ArrayList<String> arrLCiudades = new ArrayList<>();
		String aux;
		aux = textCiudad.getText().toString();

		// Creamos el renderer para la columna de la bandera
		class ImagenRenderer extends DefaultTableCellRenderer {
			JLabel lbl = new JLabel();

			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				lbl.setIcon((ImageIcon) value);
				return lbl;
			}
		}

		// Añadimos la acción al jtextfield
		textCiudad.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				modeloTabla.setColumnIdentifiers(
						new Object[] { "Ciudad", "Pais", "Distrito", "Continente", "Idioma", "Poblacion", "Bandera" });
				modeloTabla.setRowCount(0);

				// Creamos el renderer para la columna de la bandera
				ImagenRenderer renderer = new ImagenRenderer();
				TableColumn colum = tablaDatos.getColumnModel().getColumn(6);
				colum.setCellRenderer(renderer);

				for (Sistema siudad : bd.buscaCiudadesNombre(textCiudad.getText().toString())) {
					String isoCode = siudad.getPais();
					String flagFileName = "src/img/" + isoCode.toLowerCase() + ".png";
					ImageIcon bandera = new ImageIcon(flagFileName);
					modeloTabla.addRow(new Object[] { siudad.getNombre(), siudad.getPais(), siudad.getDistrito(),
							siudad.getContinente(), siudad.getIdioma(), siudad.getPoblacion(), bandera });
				}
			}
		});

		cmbPais = new JComboBox();
		cmbPais.setBounds(422, 217, 235, 22);
		add(cmbPais);

		ArrayList<String> arrLPaises = new ArrayList<>();
		arrLPaises = bd.listadoPaises();
		for (int i = 0; i < arrLPaises.size(); i++) { // para rellenar los paises
			cmbPais.addItem(arrLPaises.get(i)); // Añadimos los elementos al combo box
		}

		// Creamos el renderer personalizado
		class BanderaRenderer extends JLabel implements ListCellRenderer<Object> {
			public BanderaRenderer() {
				setOpaque(true);
			}

			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				String pais = (String) value;
				ImageIcon bandera = new ImageIcon("src/img/" + pais + ".png");
				// Cargamos la imagen de la bandera
				setText(pais); // Seteamos el texto con el nombre del país
				setIcon(bandera); // Seteamos la imagen de la bandera
				setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
				setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
				setFont(list.getFont());
				return this;
			}
		}

		// Añadimos el renderer personalizado al combo box
		cmbPais.setRenderer(new BanderaRenderer());

		// Creamos el renderer para la columna de la bandera
		class ImageRenderer extends DefaultTableCellRenderer {
			JLabel lbl = new JLabel();

			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				lbl.setIcon((ImageIcon) value);
				return lbl;
			}
		}

		// Añadimos la acción al combo box
		cmbPais.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				modeloTabla.setColumnIdentifiers(
						new Object[] { "Ciudad", "Pais", "Distrito", "Continente", "Idioma", "Poblacion", "Bandera" });
				modeloTabla.setRowCount(0);

				// Creamos el renderer para la columna de la bandera
				ImageRenderer renderer = new ImageRenderer();
				TableColumn colum = tablaDatos.getColumnModel().getColumn(6);
				colum.setCellRenderer(renderer);

				for (Sistema siudad : bd.consultarPaises(cmbPais.getSelectedItem().toString())) {
					String isoCode = siudad.getPais();
					String flagFileName = "src/img/" + isoCode.toLowerCase() + ".png";
					ImageIcon bandera = new ImageIcon(flagFileName);
					modeloTabla.addRow(new Object[] { siudad.getNombre(), siudad.getPais(), siudad.getDistrito(),
							siudad.getContinente(), siudad.getIdioma(), siudad.getPoblacion(), bandera });
				}
			}
		});

		cmbDistritos = new JComboBox();
		cmbDistritos.setBounds(422, 254, 235, 22);
		add(cmbDistritos);

		ArrayList<String> arrLDistritos = new ArrayList<>();
		arrLDistritos = bd.listaDistritos();

		for (int i = 0; i < arrLDistritos.size(); i++) {
			cmbDistritos.addItem(arrLDistritos.get(i));
		}

		// Creamos el renderer para la columna de la bandera
		class imagenBanderaRenderer extends DefaultTableCellRenderer {
			JLabel lbl = new JLabel();

			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				lbl.setIcon((ImageIcon) value);
				return lbl;
			}
		}

		cmbDistritos.addItemListener(new ItemListener() { // Añadimos una acción
			@Override
			public void itemStateChanged(ItemEvent e) { // Cuando cambie un elemento del combo box
				modeloTabla.setColumnIdentifiers( // identificamos como se llamaran las columnas de la tabla
						new Object[] { "Ciudad", "Pais", "Distrito", "Continente", "Idioma", "Poblacion", "Bandera" });
				modeloTabla.setRowCount(0);

				// Creamos el renderer para la columna de la bandera
				imagenBanderaRenderer renderer = new imagenBanderaRenderer();
				TableColumn colum = tablaDatos.getColumnModel().getColumn(6);
				colum.setCellRenderer(renderer);

				for (Sistema siudad : bd.consultarDistritos(cmbDistritos.getSelectedItem().toString())) {
					String isoCode = siudad.getPais();
					String flagFileName = "src/img/" + isoCode.toLowerCase() + ".png";
					ImageIcon bandera = new ImageIcon(flagFileName);
					modeloTabla.addRow(new Object[] { siudad.getNombre(), siudad.getPais(), siudad.getDistrito(),
							siudad.getContinente(), siudad.getIdioma(), siudad.getPoblacion(), bandera,

					});
				}

			}

		});

		sliderPoblacion = new JSlider(JSlider.HORIZONTAL, 0, 2000000, 0);
		sliderPoblacion.setMinorTickSpacing(50000);
		sliderPoblacion.setMajorTickSpacing(500000);
		sliderPoblacion.setPaintTicks(true);
		sliderPoblacion.setPaintLabels(true);
		sliderPoblacion.setBounds(404, 287, 345, 41);
		add(sliderPoblacion);
		
		// Creamos el renderer para la columna de la bandera
		class LaimagenRenderer extends DefaultTableCellRenderer {
			JLabel lbl = new JLabel();

			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				lbl.setIcon((ImageIcon) value);
				return lbl;
			}
		}

		
		sliderPoblacion.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {

				try {

					Ejecutador eje = new Ejecutador();
					modeloTabla.setRowCount(0);

					modeloTabla.setColumnIdentifiers(new Object[] { "Ciudad", "Pais", "Distrito", "Continente",
							"Idioma", "Poblacion", "Bandera" });

					tablaDatos.setModel(modeloTabla);
					for (Sistema s : bd.cargarTablaPoblacion(sliderPoblacion.getValue())) {

						String isoCode = s.getPais();
						String flagFileName = "src/img/" + isoCode.toLowerCase() + ".png";
						ImageIcon bandera = new ImageIcon(flagFileName);

						modeloTabla.addRow(new Object[] { s.getNombre(), s.getPais(), s.getDistrito(),
								s.getContinente(), s.getIdioma(), s.getPoblacion(), bandera });
					}
				} catch (NullPointerException eje2) {
					// TODO: handle exception
				}

				// Creamos el renderer para la columna de la bandera
				ImagenRenderer renderer = new ImagenRenderer();
				TableColumn colum = tablaDatos.getColumnModel().getColumn(6);
				colum.setCellRenderer(renderer);

			}
		});

	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

	
		JLabel lblConsulta = new JLabel("CONSULTAR, MODIFICAR Y BORRAR");
		lblConsulta.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblConsulta.setHorizontalAlignment(SwingConstants.CENTER);
		lblConsulta.setBounds(264, 75, 494, 41);
		add(lblConsulta);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(61, 416, 900, 153);
		add(scrollPane);

		tablaDatos = new JTable();
		scrollPane.setViewportView(tablaDatos);
		tablaDatos.setModel(modeloTabla);

		rdbtnNombre = new JRadioButton("Nombre");
		rdbtnNombre.setFont(new Font("Tahoma", Font.PLAIN, 15));
		rdbtnNombre.setBounds(279, 183, 109, 23);
		add(rdbtnNombre);

		rdbtnNombre.addActionListener(new ActionListener() { // esta accion lo que hace es que al seleccionar un
																// radioButton deshabilita los demas

			@Override
			public void actionPerformed(ActionEvent e) {
				textCiudad.setEnabled(true);// aqui permite que el jtexfield sea el unico hablitado con respecto a los
											// demas
				cmbPais.setEnabled(false);
				cmbDistritos.setEnabled(false);
				sliderPoblacion.setEnabled(false);

			}
		});

		rdbtnPais = new JRadioButton("Pais");
		rdbtnPais.setFont(new Font("Tahoma", Font.PLAIN, 15));
		rdbtnPais.setBounds(279, 216, 109, 23);
		add(rdbtnPais);

		rdbtnPais.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cmbPais.setEnabled(true);
				textCiudad.setEnabled(false);
				cmbDistritos.setEnabled(false);
				sliderPoblacion.setEnabled(false);

			}
		});

		rdbtnDistrito = new JRadioButton("Distrito");
		rdbtnDistrito.setFont(new Font("Tahoma", Font.PLAIN, 15));
		rdbtnDistrito.setBounds(279, 252, 109, 23);
		add(rdbtnDistrito);

		rdbtnDistrito.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cmbDistritos.setEnabled(true);
				cmbPais.setEnabled(false);
				textCiudad.setEnabled(false);
				sliderPoblacion.setEnabled(false);

			}
		});

		rdbtnPoblacion = new JRadioButton("Poblacion");
		rdbtnPoblacion.setFont(new Font("Tahoma", Font.PLAIN, 15));
		rdbtnPoblacion.setBounds(279, 291, 109, 23);
		add(rdbtnPoblacion);

		rdbtnPoblacion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sliderPoblacion.setEnabled(true);
				cmbDistritos.setEnabled(false);
				cmbPais.setEnabled(false);
				textCiudad.setEnabled(false);

			}
		});

		ButtonGroup grupo = new ButtonGroup(); // agrupamos los radio button
		grupo.add(rdbtnNombre);
		grupo.add(rdbtnDistrito);
		grupo.add(rdbtnPais);
		grupo.add(rdbtnPoblacion);

		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String borrarCiudad = textCiudad.getText();// Obtener el valor del JTextField cuando el usuario haga
															// clic en un botón para agregar la ciudad.
				int opcion = JOptionPane.showConfirmDialog(null,
						"¿Está seguro de que desea borrar la ciudad " + borrarCiudad + "?", "Confirmar borrado",
						JOptionPane.YES_NO_OPTION);
				if (opcion == JOptionPane.YES_OPTION) {
					bd.borrar(borrarCiudad, modeloTabla);

					// Limpiar los campos de entrada
					textCiudad.setText("");
					cmbPais.setSelectedIndex(0);
					cmbDistritos.setSelectedIndex(0);
					sliderPoblacion.setValue(0);
				}

			}
		});

		btnBorrar.setBounds(503, 369, 89, 23);
		add(btnBorrar);

		JButton btnModificar = new JButton("Modificar");
		btnModificar.setBounds(366, 369, 89, 23);
		add(btnModificar);
		btnModificar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Obtener la fila seleccionada en la tabla
				int filaSeleccionada = tablaDatos.getSelectedRow();
				if (filaSeleccionada == -1) {
					JOptionPane.showMessageDialog(null, "Seleccione una ciudad para modificar");
					return;
				}
				String ciudadAnterior = modeloTabla.getValueAt(filaSeleccionada, 0).toString();

				// Mostrar cuadro de diálogo de confirmación
				int opcion = JOptionPane.showConfirmDialog(null,
						"¿Está seguro de que desea modificar la ciudad " + ciudadAnterior + "?",
						"Confirmar modificación", JOptionPane.YES_NO_OPTION);
				if (opcion == JOptionPane.YES_OPTION) {
					// Solicitar nuevo nombre de la ciudad
					String nuevoNombreCiudad = JOptionPane.showInputDialog(null,
							"Introduzca el nuevo nombre de la ciudad:");
					if (nuevoNombreCiudad == null || nuevoNombreCiudad.trim().equals("")) {
						JOptionPane.showMessageDialog(null, "Debe introducir un nombre válido para la ciudad");
						return;
					}

					// Modificar la ciudad en la base de datos
					bd.modificar(ciudadAnterior, nuevoNombreCiudad, modeloTabla);

					// Limpiar los campos de entrada
					textCiudad.setText("");
					cmbPais.setSelectedIndex(0);
					cmbDistritos.setSelectedIndex(0);
					sliderPoblacion.setValue(0);
				}
			}
		});

		JToolBar toolBarSQL = new JToolBar();
		toolBarSQL.setBounds(10, 0, 56, 31);
		add(toolBarSQL);

		lblSQL = new JLabel("");
		lblSQL.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				exp.exportarSQL(tablaDatos);

			}
		});
		toolBarSQL.add(lblSQL);
		lblSQL.setIcon(new ImageIcon(Consultar.class.getResource("/img/servidor-sql (1).png")));

		JToolBar toolBarXML = new JToolBar();
		toolBarXML.setBounds(81, 0, 56, 31);
		add(toolBarXML);

		lblXML = new JLabel("");
		lblXML.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				exp.exportarXML(tablaDatos);
			}

		});
		toolBarXML.add(lblXML);
		lblXML.setIcon(new ImageIcon(Consultar.class.getResource("/img/archivo-xml.png")));

		JToolBar toolBarEXCEL = new JToolBar();
		toolBarEXCEL.setBounds(147, 0, 56, 31);
		add(toolBarEXCEL);

		lblEXCEL = new JLabel("");
		lblEXCEL.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				exp.exportarExcel(tablaDatos);

			}
		});

		toolBarEXCEL.add(lblEXCEL);
		lblEXCEL.setIcon(new ImageIcon(Consultar.class.getResource("/img/archivo-excel.png")));

	}
}
