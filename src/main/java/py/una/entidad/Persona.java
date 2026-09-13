package py.una.entidad;

public class Persona{

	Integer id;
	String numeroDocumento;
	String nombreCompleto;
	String tipoPersona;
	String estado;
	Integer score;
	String categoriaRiesgo;
	
	public Persona(){
	}

	public Persona(Integer pid, String pnumeroDocumento, String pnombreCompleto,
			String ptipoPersona, String pestado, Integer pscore, String pcategoriaRiesgo){
		this.id = pid;
		this.numeroDocumento = pnumeroDocumento;
		this.nombreCompleto = pnombreCompleto;
		this.tipoPersona = ptipoPersona;
		this.estado = pestado;
		this.score = pscore;
		this.categoriaRiesgo = pcategoriaRiesgo;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getCategoriaRiesgo() {
		return categoriaRiesgo;
	}

	public void setCategoriaRiesgo(String categoriaRiesgo) {
		this.categoriaRiesgo = categoriaRiesgo;
	}
}
