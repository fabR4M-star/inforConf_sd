package py.una.bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import py.una.entidad.Deuda;

public class DeudaDAO {

	public List<Deuda> seleccionar() {
		String sql = "SELECT id, persona_id, entidad, monto, dias FROM deuda";
		List<Deuda> lista = new ArrayList<Deuda>();

		try (Connection conn = Bd.connect();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				lista.add(mapearDeuda(rs));
			}
		} catch (SQLException ex) {
			System.out.println("Error en la seleccion de deudas: " + ex.getMessage());
		}
		return lista;
	}

	public List<Deuda> seleccionarPorId(Integer id) {
		String sql = "SELECT id, persona_id, entidad, monto, dias FROM deuda WHERE id = ?";
		List<Deuda> lista = new ArrayList<Deuda>();

		try (Connection conn = Bd.connect();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					lista.add(mapearDeuda(rs));
				}
			}
		} catch (SQLException ex) {
			System.out.println("Error en la seleccion de deuda: " + ex.getMessage());
		}
		return lista;
	}

	public List<Deuda> seleccionarPorPersonaId(Integer personaId) {
		String sql = "SELECT id, persona_id, entidad, monto, dias FROM deuda WHERE persona_id = ?";
		List<Deuda> lista = new ArrayList<Deuda>();

		try (Connection conn = Bd.connect();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, personaId);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					lista.add(mapearDeuda(rs));
				}
			}
		} catch (SQLException ex) {
			System.out.println("Error en la seleccion de deudas por persona: " + ex.getMessage());
		}
		return lista;
	}

	public long insertar(Deuda deuda) throws SQLException {
		String sql = "INSERT INTO deuda (persona_id, entidad, monto, dias) VALUES (?, ?, ?, ?)";

		try (Connection conn = Bd.connect();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			cargarParametros(pstmt, deuda);
			return pstmt.executeUpdate();
		}
	}

	public long actualizar(Deuda deuda) throws SQLException {
		String sql = "UPDATE deuda SET persona_id = ?, entidad = ?, monto = ?, dias = ? WHERE id = ?";

		try (Connection conn = Bd.connect();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			cargarParametros(pstmt, deuda);
			pstmt.setInt(5, deuda.getId());
			return pstmt.executeUpdate();
		}
	}

	public long borrar(Integer id) throws SQLException {
		String sql = "DELETE FROM deuda WHERE id = ?";

		try (Connection conn = Bd.connect();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			return pstmt.executeUpdate();
		}
	}

	private Deuda mapearDeuda(ResultSet rs) throws SQLException {
		Deuda deuda = new Deuda();
		deuda.setId(rs.getInt("id"));
		deuda.setPersonaId(rs.getInt("persona_id"));
		deuda.setEntidad(rs.getString("entidad"));
		deuda.setMonto(rs.getBigDecimal("monto"));
		deuda.setDias(rs.getInt("dias"));
		return deuda;
	}

	private void cargarParametros(PreparedStatement pstmt, Deuda deuda)
			throws SQLException {
		pstmt.setInt(1, deuda.getPersonaId());
		pstmt.setString(2, deuda.getEntidad());
		pstmt.setBigDecimal(3, deuda.getMonto());
		pstmt.setInt(4, deuda.getDias());
	}
}