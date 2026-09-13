package py.una.bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import py.una.entidad.Persona;

public class PersonaDAO {

    public List<Persona> seleccionar() {
    String sql = "SELECT id, numero_documento, nombre_completo, tipo_persona, "
        + "estado, score, categoria_riesgo FROM persona";
        List<Persona> lista = new ArrayList<Persona>();

        try (Connection conn = Bd.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearPersona(rs));
            }
        } catch (SQLException ex) {
            System.out.println("Error en la seleccion: " + ex.getMessage());
        }
        return lista;
    }

    public List<Persona> seleccionarPorId(Integer id) {
        String sql = "SELECT id, numero_documento, nombre_completo, tipo_persona, "
                + "estado, score, categoria_riesgo FROM persona WHERE id = ?";
        List<Persona> lista = new ArrayList<Persona>();

        try (Connection conn = Bd.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearPersona(rs));
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error en la seleccion: " + ex.getMessage());
        }
        return lista;
    }

    public List<Persona> seleccionarPorNumeroDocumento(String numeroDocumento) {
        String sql = "SELECT id, numero_documento, nombre_completo, tipo_persona, "
                + "estado, score, categoria_riesgo FROM persona WHERE numero_documento = ?";
        List<Persona> lista = new ArrayList<Persona>();

        try (Connection conn = Bd.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, numeroDocumento);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearPersona(rs));
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error en la seleccion por numero de documento: " + ex.getMessage());
        }
        return lista;
    }

    public long insertar(Persona persona) throws SQLException {
        String sql = "INSERT INTO persona "
                + "(numero_documento, nombre_completo, tipo_persona, estado, score, categoria_riesgo) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Bd.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            cargarParametros(pstmt, persona);
            return pstmt.executeUpdate();
        }
    }

    public long actualizar(Persona persona) throws SQLException {
        String sql = "UPDATE persona SET numero_documento = ?, nombre_completo = ?, "
                + "tipo_persona = ?, estado = ?, score = ?, categoria_riesgo = ? WHERE id = ?";

        try (Connection conn = Bd.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            cargarParametros(pstmt, persona);
            pstmt.setInt(7, persona.getId());
            return pstmt.executeUpdate();
        }
    }

    public long borrar(Integer id) throws SQLException {
        String sql = "DELETE FROM persona WHERE id = ?";

        try (Connection conn = Bd.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        }
    }

    private Persona mapearPersona(ResultSet rs) throws SQLException {
        Persona persona = new Persona();
        persona.setId(rs.getInt("id"));
        persona.setNumeroDocumento(rs.getString("numero_documento"));
        persona.setNombreCompleto(rs.getString("nombre_completo"));
        persona.setTipoPersona(rs.getString("tipo_persona"));
        persona.setEstado(rs.getString("estado"));
        persona.setScore(rs.getInt("score"));
        persona.setCategoriaRiesgo(rs.getString("categoria_riesgo"));
        return persona;
    }

    private void cargarParametros(PreparedStatement pstmt, Persona persona)
            throws SQLException {
        pstmt.setString(1, persona.getNumeroDocumento());
        pstmt.setString(2, persona.getNombreCompleto());
        pstmt.setString(3, persona.getTipoPersona());
        pstmt.setString(4, persona.getEstado());
        pstmt.setInt(5, persona.getScore());
        pstmt.setString(6, persona.getCategoriaRiesgo());
    }
}