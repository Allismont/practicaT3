package modelo;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;



/**
 * La clase BBDD contiene la base de datos world donde esta la  información
 * sobre los paises y ciudades del mundo
 */

public class BBDD {

	/**
	 * Busca las ciudades que tenga contenga la letra que se introduzca
	 * 
	 * @param seleccion la cadena de texto al buscar en los nombres de las ciudades
	 * @return un ArrayList que contiene los objetos Sistema que representan las
	 *         ciudades encontradas.
	 */

	String url = "jdbc:mysql://localhost:3306/world";
	String usuario = "daw";
	String contrasena = "paises2023";

	public ArrayList<Sistema> buscaCiudadesNombre(String seleccion) {

		ArrayList<Sistema> arrLCiudades = new ArrayList<>();
		// conexión a base de datos
		BBDD bd = new BBDD();
		Connection conexion = null;
		Statement consulta = null;
		ResultSet registro = null;

		try {
			conexion = DriverManager.getConnection(url, "daw", "paises2023");
//			conexion=DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "root", "");
			consulta = conexion.createStatement();

			registro = consulta
					.executeQuery(" SELECT c.name, co.name , c.District ,cl.language, co.Continent , c.population"
							+ " FROM city c, country co, countrylanguage  cl" + " WHERE c.name LIKE '%" + seleccion
							+ "%' " + " AND c.Countrycode = co.code" + " AND  co.code = cl.countrycode ");
			if (registro == null) {
				System.err.println("La base de datos está vacia.");
			}

			// Recorre los resultados de la consulta y los agrega a un ArrayList de objetos
			// Sistema
			while (registro.next()) {
				Sistema siudad = new Sistema();
				siudad.setContinente(registro.getString("co.continent"));
				siudad.setPais(registro.getString("co.name"));
				siudad.setIdioma(registro.getString("cl.language"));
				siudad.setDistrito(registro.getString("c.district"));
				siudad.setNombre(registro.getString("c.name"));
				siudad.setPoblacion(registro.getInt("c.population"));

				arrLCiudades.add(siudad);

			}

		} catch (SQLException error) {
			System.err.println("No se ha podido conectar con la base de datos.");
			JOptionPane.showMessageDialog(null, "No se ha podido conectar con la base de datos. ");
			error.printStackTrace();
		} finally {
			try {
				conexion.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "No se ha podido cerrar la base de datos ");
				System.err.println("No se ha podido cerrar la base de datos");
			} catch (NullPointerException e) {

			}

		}

		return arrLCiudades;
	}

	/**
	 * Obtiene el listado de los nombres de los países que estan en la base de datos
	 * 
	 * @return un ArrayList que contiene los nombres de los países
	 */

	public ArrayList<String> listadoPaises() {
		ArrayList<String> arrLPaises = new ArrayList<>();

		String aux;
		Connection conexion = null;
		Statement consulta = null;
		ResultSet registro = null;

		try {
			conexion = DriverManager.getConnection(url,  "daw", "paises2023");
//			conexion=DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "root", "");
			consulta = conexion.createStatement();
			registro = consulta.executeQuery("SELECT name FROM  country  ORDER BY name");

			if (registro == null) {
				System.out.println("La base de datos está vacia.");
			}
			while (registro.next()) {
				aux = registro.getString("name");

				arrLPaises.add(aux);

			}

		} catch (SQLException error) {
			System.err.println("No se ha podido conectar con la base de datos.");
			JOptionPane.showMessageDialog(null, "No se ha podido conectar con la base de datos.");
			error.printStackTrace();
		} finally {
			try {
				conexion.close();
			} catch (SQLException e) {
				System.err.println("No se ha podido cerrar la base de datos");
				JOptionPane.showMessageDialog(null, "No se ha podido cerrar la base de datos");
			} catch (NullPointerException e) {

			}
		}
		return arrLPaises;

	}

	/**
	 * 
	 * arraylist que realiza una consulta a la base de datos para obtener
	 * información sobre un país en particular.
	 * 
	 * @param seleccion una cadena que representa el nombre del país a consultar.
	 * @return un ArrayList de objetos Sistema con la información obtenida de la
	 *         consulta.
	 */

	public ArrayList<Sistema> consultarPaises(String seleccion) {
		ArrayList<Sistema> arrLConsultaPais = new ArrayList<>();

		BBDD bd = new BBDD();
		String aux;
		Connection conexion = null;
		Statement consulta = null;
		ResultSet registro = null;

		try {
			conexion = DriverManager.getConnection(url,  "daw", "paises2023");
//			conexion=DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "root", "");
			consulta = conexion.createStatement();
			registro = consulta.executeQuery(
					" select c.name, co.name , c.District , co.Continent, cl.language ,c.population, co.code"
							+ " from city c, country co, countrylanguage cl" + " where co.name = '" + seleccion + "'"
							+ " AND c.Countrycode = co.code" + " AND  co.code = cl.countrycode ");

			if (registro == null) {
				System.out.println("La base de datos está vacia.");
			}
			while (registro.next()) {
				Sistema siudad = new Sistema();
				siudad.setContinente(registro.getString("co.continent"));
				siudad.setPais(registro.getString("co.name"));
				siudad.setIdioma(registro.getString("cl.language"));
				siudad.setDistrito(registro.getString("c.district"));
				siudad.setNombre(registro.getString("c.name"));
				siudad.setPoblacion(registro.getInt("c.population"));

				arrLConsultaPais.add(siudad);
			}

		} catch (SQLException error) {
			System.err.println("No se ha podido conectar con la base de datos.");
			JOptionPane.showMessageDialog(null, "No se ha podido conectar con la base de datos. ");
			error.printStackTrace();
		} finally {
			try {
				conexion.close();
			} catch (SQLException e) {
				System.err.println("No se ha podido cerrar la base de datos");
				JOptionPane.showMessageDialog(null, "No se ha podido cerrar la base de datos ");
			} catch (NullPointerException e) {

			}
		}
		return arrLConsultaPais;

	}

	/**
	 * Obtiene un listado de los nombres de loslos distritos almacenados en la base
	 * de datos
	 * 
	 * @return un ArrayList que contiene los nombres de los distritos de las
	 *         ciudades
	 */

	public ArrayList<String> listaDistritos() {
		ArrayList<String> arrLDistritos = new ArrayList<>();

		// conexión a base de datos
		String aux;
		Connection conexion = null;
		Statement consulta = null;
		ResultSet registro = null;

		try {
			conexion = DriverManager.getConnection(url,  "daw", "paises2023");
//			conexion=DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "root", "");
			consulta = conexion.createStatement();
			registro = consulta.executeQuery("select distinct district from city order by district");

			if (registro == null) {
				System.out.println("La base de datos está vacia.");
			}

			while (registro.next()) {
				aux = registro.getString("district");
				arrLDistritos.add(aux);

			}
		} catch (SQLException error) {
			System.err.println("No se ha podido conectar con la base de datos.");
			JOptionPane.showMessageDialog(null, "No se ha podido conectar con la base de datos.");
			error.printStackTrace();
		} finally {
			try {
				conexion.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "No se ha podido cerrar la base de datos ");
				System.err.println("No se ha podido cerrar la base de datos");
			} catch (NullPointerException e) {

			}
		}

		return arrLDistritos;
	}

	/**
	 * 
	 * Consulta los datos de todas las ciudades que pertenecen a un distrito
	 * determinado y los almacena en un ArrayList de objetos Sistema.
	 * 
	 * @param seleccion El nombre del distrito a consultar.
	 * 
	 * @return ArrayList de objetos Sistema con los datos de todas las ciudades del
	 *         distrito consultado.
	 */

	public ArrayList<Sistema> consultarDistritos(String seleccion) {
		ArrayList<Sistema> arrLConsultarDistri = new ArrayList<>();

		BBDD bd = new BBDD();
		String aux;
		Connection conexion = null;
		Statement consulta = null;
		ResultSet registro = null;

		try {
			conexion = DriverManager.getConnection(url,  "daw", "paises2023");
//			conexion=DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "root", "");
			consulta = conexion.createStatement();
			registro = consulta
					.executeQuery(" select c.name, co.name , c.District , co.Continent, cl.language ,c.population"
							+ " from city c, country co, countrylanguage cl" + " where c.district = '" + seleccion + "'"
							+ " AND c.Countrycode = co.code" + " AND  co.code = cl.countrycode ");

			if (registro == null) {
				System.out.println("La base de datos está vacia.");
			}
			while (registro.next()) {
				Sistema siudad = new Sistema();
				siudad.setContinente(registro.getString("co.continent"));
				siudad.setPais(registro.getString("co.name"));
				siudad.setIdioma(registro.getString("cl.language"));
				siudad.setDistrito(registro.getString("c.district"));
				siudad.setNombre(registro.getString("c.name"));
				siudad.setPoblacion(registro.getInt("c.population"));

				arrLConsultarDistri.add(siudad);
			}

		} catch (SQLException error) {
			System.err.println("No se ha podido conectar con la base de datos.");
			JOptionPane.showMessageDialog(null, "No se ha podido conectar con la base de datos. ");
			error.printStackTrace();
		} finally {
			try {
				conexion.close();
			} catch (SQLException e) {
				System.err.println("No se ha podido cerrar la base de datos");
			} catch (NullPointerException e) {

			}
		}
		return arrLConsultarDistri;
	}

	/**
	 * Carga una tabla que contiene información sobre las ciudades cuya población es
	 * igual o mayor que un valor especificado.
	 * 
	 * @param seleccion el valor mínimo de población para las ciudades a incluir en
	 *                  la tabla
	 * @return un ArrayList que contiene los objetos Sistema que representan las
	 *         ciudades incluidas en la tabla
	 */
	public ArrayList<Sistema> cargarTablaPoblacion(int seleccion) {
		ArrayList<Sistema> arrLTabla = new ArrayList<>();

		BBDD bd = new BBDD();

		Connection conexion = null;
		Statement consulta = null;
		ResultSet registro = null;

		try {

			conexion = DriverManager.getConnection(url,  "daw", "paises2023");
//			conexion=DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "root", "");
			consulta = conexion.createStatement();

			registro = consulta
					.executeQuery(" SELECT c.name, co.name , c.District ,cl.language, co.Continent , c.population "
							+ " FROM city c, country co, countrylanguage  cl" + " WHERE c.population >= ' " + seleccion
							+ " ' " + " AND c.Countrycode = co.code" + " AND  co.code = cl.countrycode ");

			if (registro == null) {
				System.out.println("La base de datos está vacia.");
			}

			while (registro.next()) {
				Sistema siudad = new Sistema();
				siudad.setContinente(registro.getString("co.continent"));
				siudad.setPais(registro.getString("co.name"));
				siudad.setIdioma(registro.getString("cl.language"));
				siudad.setDistrito(registro.getString("c.district"));
				siudad.setNombre(registro.getString("c.name"));
				siudad.setPoblacion(registro.getInt("c.population"));

				arrLTabla.add(siudad);

			}

		} catch (SQLException error) {
			System.err.println("No se ha podido conectar con la base de datos.");
			JOptionPane.showMessageDialog(null, "No se ha podido conectar con la base de datos. ");
			error.printStackTrace();

		} finally {
			try {
				conexion.close();
			} catch (SQLException e) {
				System.err.println("No se ha podido cerrar la base de datos");
				JOptionPane.showMessageDialog(null, "No se ha podido cerrar la base de datos ");
			} catch (NullPointerException e) {

			}
		}

		return arrLTabla;
	}

	/**
	 * 
	 * carga los distritos de un país específico a partir de la base de datos
	 * 
	 * @param pais el nombre del país del cual se desea cargar los distritos.
	 * 
	 * @return un ArrayList de tipo String con los nombres de los distritos
	 *         encontrados.
	 */

	public ArrayList<String> cargaDistritos(String pais) {
		ArrayList<String> arrLDistri = new ArrayList<>();

		// conexión a base de datos
		Connection conexion = null;
		Statement consulta = null;
		ResultSet registro = null;

		try {
			conexion = DriverManager.getConnection(url,  "daw", "paises2023");
//			conexion=DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "root", "");
			consulta = conexion.createStatement();
			registro = consulta
					.executeQuery("select distinct city.district " + "from country, city " + "where country.Name='"
							+ pais + "' and country.Code=city.CountryCode " + "order by city.district");

			if (registro == null) {
				System.out.println("La base de datos está vacia.");
			}

			while (registro.next()) {
				String aux;
				aux = registro.getString("district");

				arrLDistri.add(aux);
				// Añadimos al combo box los nombres de la base de datos de world

			}

		} catch (SQLException error) {
			System.err.println("No se ha podido conectar con la base de datos.");
			error.printStackTrace();
		} finally {
			try {
				conexion.close();
			} catch (SQLException e) {
				System.err.println("No se ha podido cerrar la base de datos");
			} catch (NullPointerException e) {

			}
		}
		return arrLDistri;
	}

	/**
	 * 
	 * arraylist que devuelve una lista de los distritos de un país por parámetro
	 * ordenados alfabéticamente. La información se obtiene de la base de datos
	 * World.
	 * 
	 * @param pais nombre del país del que se desea obtener los distritos
	 * @return una lista de los distritos del país dados por parámetro
	 */

	public ArrayList<String> cargaPobla(String pais) {
		ArrayList<String> arrLPobla = new ArrayList<>();

		// conexión a base de datos
		Connection conexion = null;
		Statement consulta = null;
		ResultSet registro = null;

		try {
			conexion = DriverManager.getConnection(url,  "daw", "paises2023");
//			conexion=DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "root", "");
			consulta = conexion.createStatement();
			registro = consulta
					.executeQuery("select distinct city.district " + "from country, city " + "where country.Name='"
							+ pais + "' and country.Code=city.CountryCode " + "order by city.district");

			if (registro == null) {
				System.out.println("La base de datos está vacia.");
			}

			while (registro.next()) {
				String aux;
				aux = registro.getString("district");

				arrLPobla.add(aux);
				// Añadimos al combo box los nombres de la base de datos de world

			}

		} catch (SQLException error) {
			System.err.println("No se ha podido conectar con la base de datos.");
			error.printStackTrace();
		} finally {
			try {
				conexion.close();
			} catch (SQLException e) {
				System.err.println("No se ha podido cerrar la base de datos");
			} catch (NullPointerException e) {

			}
		}
		return arrLPobla;
	}

	/**
	 * 
	 * Inserta una nueva ciudad en la base de datos con los parámetros dados y
	 * actualiza una tabla con los datos de la base de datos.
	 * 
	 * @param nuevaCiudad    El nombre de la nueva ciudad a insertar.
	 * @param nuevoDistrito  El distrito de la nueva ciudad a insertar.
	 * @param nuevoPais      El país de la nueva ciudad a insertar.
	 * @param nuevaPoblacion La población de la nueva ciudad a insertar.
	 * @param modeloTabla    El modelo de la tabla a actualizar con los datos de la
	 *                       base de datos.
	 */
	public void insertar(String nuevaCiudad, String nuevoDistrito, String nuevoPais, int nuevaPoblacion,
			DefaultTableModel modeloTabla) {

		// conexión a base de datos
		Connection conexion = null;
		Statement consulta = null;
		ResultSet registro = null;

		try {
			conexion = DriverManager.getConnection(url,  "daw", "paises2023");
//			conexion=DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "root", "");
			consulta = conexion.createStatement();
			consulta.executeUpdate("INSERT INTO city (name, countrycode, district, population) VALUES " + "('"
					+ nuevaCiudad + "', (SELECT co.code FROM country co WHERE name = '" + nuevoPais + "'), '"
					+ nuevoDistrito + "', " + nuevaPoblacion + ")");
			JOptionPane.showMessageDialog(null, "Articulo guardado correctamente");

			// Actualizar la JTable con los datos de la base de datos
			registro = consulta.executeQuery("SELECT * FROM city");
			modeloTabla.setRowCount(0); // Limpiar la tabla

			while (registro.next()) {
				Object[] fila = { registro.getString("name"), registro.getString("countrycode"),
						registro.getString("district"), registro.getInt("population") };
				modeloTabla.addRow(fila); // Agregar la fila a la tabla
			}

		} catch (SQLException error) {
			System.err.println("No se ha podido conectar con la base de datos.");
			error.printStackTrace();
		} finally {
			try {
				conexion.close();
			} catch (SQLException e) {
				System.err.println("No se ha podido cerrar la base de datos");
			} catch (NullPointerException e) {

			}
		}

	}

	/**
	 * 
	 * Método que borra una ciudad de la base de datos y actualiza la JTable con los
	 * datos actualizados.
	 * 
	 * @param borrarCiudad nombre de la ciudad que se va a borrar
	 * @param modeloTabla  modelo de la JTable que se va a actualizar
	 */


	public void borrar(String borrarCiudad, DefaultTableModel modeloTabla) {

		// conexión a base de datos
		Connection conexion = null;
		PreparedStatement consulta = null;
		ResultSet registro = null;

		try {
			conexion = DriverManager.getConnection(url,  "daw", "paises2023");
//			conexion=DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "root", "");

			// Iniciar transacción
			conexion.setAutoCommit(false);

			// Borrar registro
			consulta = conexion.prepareStatement("DELETE FROM city WHERE name like ?");
			consulta.setString(1, "%" + borrarCiudad + "%");
			consulta.executeUpdate();
			JOptionPane.showMessageDialog(null, "Ciudad borrada correctamente");

			// Actualizar la JTable con los datos de la base de datos
			consulta = conexion.prepareStatement("SELECT * FROM city");
			registro = consulta.executeQuery();
			modeloTabla.setRowCount(0); // Limpiar la tabla

			while (registro.next()) {
				Object[] fila = { registro.getString("name"), registro.getString("countrycode"),
						registro.getString("district"), registro.getInt("population") };
				modeloTabla.addRow(fila); // Agregar la fila a la tabla
			}

			// Confirmar transacción
			conexion.commit();

		} catch (SQLException error) {
			System.err.println("No se ha podido conectar con la base de datos.");
			error.printStackTrace();
			try {
				conexion.rollback(); // Deshacer cambios en caso de error
			} catch (SQLException e) {
				System.err.println("No se ha podido deshacer la transacción");
			}
		} finally {
			try {
				if (consulta != null) {
					consulta.close();
				}
				if (registro != null) {
					registro.close();
				}
				if (conexion != null) {
					conexion.setAutoCommit(true); // Restablecer el modo de autocommit
					conexion.close();
				}
			} catch (SQLException e) {
				System.err.println("No se ha podido cerrar la base de datos");
			}
		}

	}


	/**
	 * 
	 * Modifica el nombre de una ciudad en la base de datos y actualiza la tabla de
	 * la interfaz gráfica con los nuevos datos.
	 * 
	 * @param ciudadAnterior el nombre de la ciudad a modificar
	 * @param ciudadNueva    el nuevo nombre para la ciudad
	 * @param modeloTabla    el modelo de la tabla de la interfaz gráfica
	 */
	public void modificar(String ciudadAnterior, String ciudadNueva, DefaultTableModel modeloTabla) {

		// conexión a base de datos
		Connection conexion = null;
		Statement consulta = null;
		ResultSet registro = null;

		try {
			conexion = DriverManager.getConnection(url,  "daw", "paises2023");
//			conexion=DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "root", "");
			consulta = conexion.createStatement();
			consulta.executeUpdate(
					"UPDATE city SET name = '" + ciudadNueva + "' WHERE name = '" + ciudadAnterior + "'");
			JOptionPane.showMessageDialog(null, "Ciudad modificada correctamente");

			// Actualizar la JTable con los datos de la base de datos
			registro = consulta.executeQuery("SELECT * FROM city");
			modeloTabla.setRowCount(0); // Limpiar la tabla

			while (registro.next()) {
				Object[] fila = { registro.getString("name"), registro.getString("countrycode"),
						registro.getString("district"), registro.getInt("population") };
				modeloTabla.addRow(fila); // Agregar la fila a la tabla
			}

		} catch (SQLException error) {
			System.err.println("No se ha podido conectar con la base de datos.");
			error.printStackTrace();
		} finally {
			try {
				conexion.close();
			} catch (SQLException e) {
				System.err.println("No se ha podido cerrar la base de datos");
			} catch (NullPointerException e) {

			}
		}

	}
	

}
