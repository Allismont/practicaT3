package modelo;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.mysql.jdbc.ResultSetMetaData;

/**
 * Clase que permite exportar los datos de la tabla a  un archivo de Excel , XML o SQL .
 */
public class Exportar {

	/**
	 * Exporta los datos de una tabla de datos a un archivo de Excel (.xls).
	 * 
	 * @param tablaDatos la tabla de datos a exportar.
	 */
	public void exportarExcel(JTable tablaDatos) {
		JFileChooser chooser = new JFileChooser();
		// filtro para que solo se puedan seleccionar archivos con extensión .xls
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de excel", "xls");
		chooser.setFileFilter(filter);
		chooser.setDialogTitle("Guardar archivo"); // Se establece el título del diálogo
		chooser.setAcceptAllFileFilterUsed(false);
		if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) { // Si se ha seleccionado una opción para
																			// guardar el archivo
			String ruta = chooser.getSelectedFile().toString().concat(".xls");// Se obtiene la ruta y se le añade la
																				// extensión .xls
			try {

				File archivoXLS = new File(ruta);
				if (archivoXLS.exists()) {
					archivoXLS.delete();// Si ya existe un archivo con el mismo nombre, se elimina
				}

				archivoXLS.createNewFile();// Se crea el archivo nuevo
				Workbook libro = new HSSFWorkbook();
				FileOutputStream archivo = new FileOutputStream(archivoXLS);
				Sheet hoja = libro.createSheet("HOJA 1");
				// Se recorren las filas de la tabla para crear las filas en la hoja
				for (int f = 0; f < tablaDatos.getRowCount(); f++) {
					Row fila = hoja.createRow(f);
					// se crea la cabecera de las columnas
					for (int c = 0; c < tablaDatos.getColumnCount(); c++) {
						Cell celda = fila.createCell(c);
						if (f == 0) {
							celda.setCellValue(tablaDatos.getColumnName(c));
						}
					}
				}
				int filaInicio = 1;
				// Se recorren las filas de la tabla para crear las filas en la hoja
				for (int f = 0; f < tablaDatos.getRowCount(); f++) {
					Row fila = hoja.createRow(filaInicio);
					filaInicio++;
					for (int c = 0; c < tablaDatos.getColumnCount(); c++) {
						Cell celda = fila.createCell(c);
						// Si el valor de la celda es de tipo double, se guarda como tal
						if (tablaDatos.getValueAt(f, c) instanceof Double) {
							celda.setCellValue(Double.parseDouble(tablaDatos.getValueAt(f, c).toString()));
							// Si el valor de la celda es de tipo float, se guarda como tal
						} else if (tablaDatos.getValueAt(f, c) instanceof Float) {
							celda.setCellValue(Float.parseFloat((String) tablaDatos.getValueAt(f, c)));
						} else {
							// Si el valor de la celda no es numérico, se guarda como texto
							celda.setCellValue(String.valueOf(tablaDatos.getValueAt(f, c)));
						}
					}
				}

				libro.write(archivo);
				archivo.close();
				Desktop.getDesktop().open(archivoXLS);
				JOptionPane.showMessageDialog(null, "Archivo XSL exportado con éxito.");
			} catch (IOException | NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Error al exportar el archivo XML.");

			}
		}

	}

	/**
	 * Exporta los datos de una tabla de datos a un archivo de XML (.xml).
	 * 
	 * @param tablaDatos la tabla de datos a exportar.
	 */

	public void exportarXML(JTable tablaDatos) {
		JFileChooser chooser = new JFileChooser();
		// Se crea un filtro para que solo se muestren archivos con extensión .xml
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos XML", "xml");
		// Se establece el título del cuadro de diálogo y se desactiva la opción de
		// aceptar cualquier tipo de archivo
		chooser.setFileFilter(filter);
		chooser.setDialogTitle("Guardar archivo");
		chooser.setAcceptAllFileFilterUsed(false);
		// Se muestra el cuadro de diálogo para que el usuario seleccione la ubicación y
		// el nombre del archivo
		if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			// Se obtiene la ruta del archivo seleccionado
			String ruta = chooser.getSelectedFile().toString();
			// Si la ruta no termina en .xml, se agrega la extensión al final
			if (!ruta.endsWith(".xml")) {
				ruta += ".xml"; // agregar la extensión .xml si no se ingresa
			}

			try {
				
				// Se crea el documento XML
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				Document doc = docBuilder.newDocument();

				// Se crea el elemento raíz
				Element rootElement = doc.createElement("tablaDatos");
				doc.appendChild(rootElement);

				// Se agrega una fila con el encabezado de la tabla
				String[] encabezado = new String[tablaDatos.getColumnCount()];
				for (int j = 0; j < tablaDatos.getColumnCount(); j++) {
					encabezado[j] = tablaDatos.getColumnName(j);
				}
				

				// Se agregan las filas con los datos de la tabla
				for (int i = 0; i < tablaDatos.getRowCount(); i++) {
					String[] datos = new String[tablaDatos.getColumnCount()];
					for (int j = 0; j < tablaDatos.getColumnCount(); j++) {
						datos[j] = String.valueOf(tablaDatos.getValueAt(i, j));
					}
					Element filaDatos = crearElementoFila(doc, datos);
					rootElement.appendChild(filaDatos);
				}
				// Se transforma el documento en un archivo XML
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File(ruta));
				transformer.transform(source, result);

				JOptionPane.showMessageDialog(null, "Archivo XML exportado con éxito.");
			} catch (ParserConfigurationException | TransformerException pce) {
				JOptionPane.showMessageDialog(null, "Error al exportar el archivo XML.");
			}
		}
	}

	// Método auxiliar que crea un elemento XML para una fila de la tabla
	private Element crearElementoFila(Document doc, String[] datos) {
		Element fila = doc.createElement("Datos");
		for (int j = 0; j < datos.length; j++) {
			
			Element columna = doc.createElement("columna");
			
			columna.appendChild(doc.createTextNode(datos[j]));
			fila.appendChild(columna);
		}
		return fila;
	}

	

	/**
	 * Exporta los datos de una JTable a un archivo SQL.
	 * @param tablaDatos la tabla que contiene los datos a exportar.
	 */
	
	public void exportarSQL(JTable tablaDatos) {

		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos SQL", "sql");
		chooser.setFileFilter(filter);
		chooser.setDialogTitle("Guardar archivo");
		chooser.setAcceptAllFileFilterUsed(false);
		if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			String ruta = chooser.getSelectedFile().toString().concat(".sql");
			try {
				BufferedWriter out = new BufferedWriter(new FileWriter(ruta));

				// Se recorren las filas de la tabla para obtener los datos
				for (int f = 0; f < tablaDatos.getRowCount(); f++) {

					String ciudad = (String) tablaDatos.getValueAt(f, 0);
					String pais = (String) tablaDatos.getValueAt(f, 1);
					String distrito = (String) tablaDatos.getValueAt(f, 2);
					String continente = (String) tablaDatos.getValueAt(f, 3);
					String idioma = (String) tablaDatos.getValueAt(f, 4);
					int poblacion = (int) tablaDatos.getValueAt(f, 5);

					String insert = "INSERT INTO nuevaTabla (ciudad, pais, distrito, continente, idioma, poblacion) "
							+ "VALUES ('" + ciudad + "', '" + pais + "', '" + distrito + "', '" + continente + "', '"
							+ idioma + "', " + poblacion + ");";

					out.write(insert + "\n");
				}
				out.close();
				Desktop.getDesktop().open(new File(ruta));
				JOptionPane.showMessageDialog(null, "Archivo SQL exportado con éxito.");
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Error al exportar el archivo SQL.");
			}
		}

	}

}
	

