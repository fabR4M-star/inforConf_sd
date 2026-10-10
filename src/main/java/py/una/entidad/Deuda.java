package py.una.entidad;

import java.math.BigDecimal;

public class Deuda {

	Integer id;
	Integer personaId;
	String entidad;
	BigDecimal monto;
	Integer dias;

	public Deuda() {
	}

	public Deuda(Integer id, Integer personaId, String entidad, BigDecimal monto, Integer dias) {
		this.id = id;
		this.personaId = personaId;
		this.entidad = entidad;
		this.monto = monto;
		this.dias = dias;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public Integer getDias() {
		return dias;
	}

	public void setDias(Integer dias) {
		this.dias = dias;
	}
}