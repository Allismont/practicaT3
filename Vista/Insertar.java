package Vista;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import controlador.Ejecutador;
import modelo.BBDD;
import modelo.Sistema;

import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JSlider;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Insertar extends JPanel {
	private JLabel lblNombre;
	private JLabel lblPais;
	private JLabel lblDistrito;
	private JLabel lblPoblacion;
	private JComboBox cmbPaises;
	private JComboBox cmbDistrito;
	private JSlider sliderPoblacion;
	private JButton btnInsertar;
	DefaultTableModel modeloTabla = new DefaultTableModel();


	/**
	 * Create the panel.
	 */
	BBDD bd = new BBDD();
	public Insertar() {
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("INSERTAR");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(391, 78, 277, 52);
		add(lblNewLabel);
		
		JTextField textCiudad = new JTextField();
		textCiudad.setBounds(391, 211, 201, 20);
		add(textCiudad);
		textCiudad.setColumns(10);
		
		lblNombre = new JLabel("NOMBRE");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNombre.setBounds(248, 209, 91, 20);
		add(lblNombre);
		
		cmbPaises = new JComboBox();
		cmbPaises.setBounds(391, 270, 201, 22);
		add(cmbPaises);
		// crear un ArrayList para almacenar los países
		ArrayList<String> arrLPaises = new ArrayList<>();
		// obtener la lista de países de la base de datos
		arrLPaises= bd.listadoPaises();
		// rellena los paises al JComboBox
		for (int i = 0; i < arrLPaises.size(); i++) {
			cmbPaises.addItem(arrLPaises.get(i));
		}
		// Creamos una clase BanderaRenderer que extiende de JLabel e implementa la interfaz ListCellRenderer
		class BanderaRenderer extends JLabel implements ListCellRenderer<Object> {
			// Constructor de la clase
		    public BanderaRenderer() {
		        setOpaque(true);
		    }

		    @Override
		 // Método de la interfaz ListCellRenderer que es llamado para obtener el componente que se usará para mostrar cada elemento en la lista desplegable
		    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		    	  // Convertimos el objeto value a un String que representa el nombre del país seleccionado
		        String pais = (String) value;
		        
		        // Creamos un ImageIcon a partir de la imagen de la bandera del país seleccionado
		        ImageIcon bandera = new ImageIcon("src/img/" + pais + ".png");
		      
		        // Establecemos el texto del componente en el nombre del país
		        setText(pais); 
		     // Establecemos el icono del componente en la imagen de la bandera
		        setIcon(bandera); 
		        setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
		        setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
		        setFont(list.getFont());
		     // Devolvemos el componente creado
		        return this;
		    }
		}
		
		// Asignamos un objeto de la clase BanderaRenderer al componente JComboBox cmbPaises
		cmbPaises.setRenderer(new BanderaRenderer());
		//evento para detectar cuando se selecciona un país
		cmbPaises.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				 // borrar la lista de distritos actual
				cmbDistrito.removeAllItems();
				 // se crea un ArrayList para almacenar los distritos disponibles para el país seleccionado
				ArrayList<String> arrLDistri = new ArrayList<>();
				String aux;

		        // obtener el país seleccionado del JComboBox
				aux= cmbPaises.getSelectedItem().toString();
				// para obtener la lista de distritos de la base de datos para el país seleccionado
				arrLDistri= bd.cargaDistritos(aux);
				// agregar cada distrito al JComboBox
				for (int i = 0; i < arrLDistri.size(); i++) {
					cmbDistrito.addItem(arrLDistri.get(i));
				}
				
			}
		});
			
		cmbDistrito = new JComboBox();
		cmbDistrito.setBounds(391, 333, 201, 22);
		add(cmbDistrito);
		
				ArrayList<String> arrLPobla =new ArrayList<>();
				String aux;
				aux=cmbPaises.getSelectedItem().toString();
				arrLPobla=bd.cargaPobla(aux);
				
				for(int i=0; i<arrLPobla.size();i++) {
					//Añadimos los elementos al combo box
					cmbPaises.addItem(arrLPobla.get(i));
				}


		sliderPoblacion = new JSlider(JSlider.HORIZONTAL, 0, 2000000, 0);
		sliderPoblacion.setMinorTickSpacing(50000);
		sliderPoblacion.setMajorTickSpacing(500000);
		sliderPoblacion.setPaintTicks(true);
		sliderPoblacion.setPaintLabels(true);
		sliderPoblacion.setBounds(368, 398, 310, 45);
		add(sliderPoblacion);
		

		lblPais = new JLabel("PAIS");
		lblPais.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPais.setBounds(255, 268, 76, 22);
		add(lblPais);
		
		lblDistrito = new JLabel("DISTRITO");
		lblDistrito.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDistrito.setBounds(248, 333, 91, 18);
		add(lblDistrito);
		
		lblPoblacion = new JLabel("POBLACIÓN");
		lblPoblacion.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPoblacion.setBounds(248, 398, 103, 20);
		add(lblPoblacion);
		
		btnInsertar = new JButton("Insertar");
		//
		btnInsertar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// para Obtener los valores de entrada de ciudad,pais,distrito y poblacion
				String nuevaCiudad = textCiudad.getText();//Obtener el valor del JTextField cuando el usuario haga clic en un botón para agregar la ciudad.
				String nuevoPais = cmbPaises.getSelectedItem().toString();
				String nuevoDistrito = cmbDistrito.getSelectedItem().toString();
				int nuevaPoblacion = sliderPoblacion.getValue();
				// Mostrar un cuadro de diálogo de confirmación
				int opcion = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea insertar la ciudad " + nuevaCiudad + "?", "Confirmar insertado", JOptionPane.YES_NO_OPTION);
				// Si se hace clic en si se insertar la ciudad en la base de datos y limpiar los campos de entrada
				if(opcion==JOptionPane.YES_OPTION) {
					bd.insertar(nuevaCiudad, nuevoDistrito, nuevoPais, nuevaPoblacion, modeloTabla);

					// Limpiar los campos de entrada
					textCiudad.setText("");
					cmbPaises.setSelectedIndex(0);
					cmbDistrito.setSelectedIndex(0);
					sliderPoblacion.setValue(0);
				}
				
			}
		});
		
		btnInsertar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnInsertar.setBounds(625, 208, 89, 23);
		add(btnInsertar);

	}
}
