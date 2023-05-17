package modelo;

/**
 * La clase Sistema representa una ciudad con sus  atributos y métodos getter y setter
 */
public class Sistema {
	
	private String continente; 
	private String pais;
	private String idioma;
	private String distrito;
	private String nombre;
	private int poblacion;
	

	/**
	 * Constructor por defecto de la clase Sistema.
	 */
	public Sistema() {
		this.continente = null;
		this.pais = null;
		this.idioma = null;
		this.distrito = null;
		this.nombre = null;
		this.poblacion = 0;
	}
	
	/**
	 * Constructor por parametros de la clase Sistema.
	 * @param continente El continente donde se encuentra la ciudad.
	 * @param pais El pais donde se encuentra la ciudad.
	 * @param idioma El idioma hablado en la ciudad.
	 * @param distrito El distrito donde se encuentra la ciudad.
	 * @param nombre El nombre de la ciudad.
	 * @param poblacion La poblacion de la ciudad.
	 */
	public Sistema(String continente, String pais, String idioma, String distrito, String nombre, int poblacion) {
		this.continente = continente;
		this.pais = pais;
		this.idioma = idioma;
		this.distrito = distrito;
		this.nombre = nombre;
		this.poblacion = poblacion;
	}

	/**
	 * Método getter para el atributo continente.
	 * @return El continente donde se encuentra la ciudad.
	 */
	
	public String getContinente() {
		return continente;
	}

	/**
	 * Método setter para el atributo continente.
	 * @param continente El nuevo continente donde se encuentra la ciudad.
	 */
	
	public void setContinente(String continente) {
		this.continente = continente;
	}

	/**
	 * Método getter para el atributo pais.
	 * @return El pais donde se encuentra la ciudad.
	 */
	
	public String getPais() {
		return pais;
	}
	
	/**
	 * Método setter para el atributo pais.
	 * @param pais El nuevo pais donde se encuentra la ciudad.
	 */
	
	public void setPais(String pais) {
		this.pais = pais;
	}

	/**
	 * Método getter para el atributo idioma.
	 * @return El idioma hablado en la ciudad.
	 */
	public String getIdioma() {
		return idioma;
	}

	/**
	 * Método setter para el atributo idioma.
	 * @param idioma El nuevo idioma hablado en la ciudad.
	 */
	
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	/**
	 * Método getter para el atributo distrito.
	 * @return El distrito donde se encuentra la ciudad.
	 */
	
	public String getDistrito() {
		return distrito;
	}

	/**
	 * Método setter para el atributo distrito.
	 * @param distrito El nuevo distrito donde se encuentra la ciudad.
	 */
	
	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}
	
	/**
	 * Método getter para el atributo nombre.
	 * @return El nombre de la ciudad.
	 */

	public String getNombre() {
		return nombre;
	}

	/**
	 * Método setter para el atributo nombre.
	 * @param  nombre de  la ciudad.
	 */
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Método getter para el atributo poblacion.
	 * @return la poblacion de la ciudad.
	 */
	
	public int getPoblacion() {
		return poblacion;
	}

	/**
	 * Método setter para el atributo poblacion.
	 * @param  poblacion de  la ciudad.
	 */
	
	public void setPoblacion(int poblacion) {
		this.poblacion = poblacion;
	}

	
	
	
	
}	
