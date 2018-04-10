package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.corsi.model.Corso;
import it.polito.tdp.corsi.model.Stats;

public class CorsoDAO {

	private final String jdbcURL = "jdbc:mysql://localhost/iscritticorsi?user=root";

	/**
	 * Ritorna tutti gli elementi della tabella CORSO
	 * 
	 * @return
	 */
	public List<Corso> listAll() {

		String sql = "SELECT codins, crediti, nome, pd " + "FROM corso";

		List<Corso> result = new ArrayList<>();

		try {

			Connection conn = ConnectDB.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet res = st.executeQuery();

			while (res.next()) {
				Corso c = new Corso(res.getString("codins"), res.getInt("crediti"), res.getString("nome"),
						res.getInt("pd"));

				result.add(c);

			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return result;
	}

	/**
	 * Ritorna i corsi che hanno questo periodo didattico {@code pd}
	 * 
	 * @param pd
	 * @return
	 */
	public List<Corso> listByPd(int pd) {

		String sql = "SELECT codins, crediti, nome, pd " + "FROM corso WHERE pd = ?";

		List<Corso> result = new ArrayList<>();

		try {

			Connection conn = ConnectDB.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, pd);

			ResultSet res = st.executeQuery();

			while (res.next()) {
				Corso c = new Corso(res.getString("codins"), res.getInt("crediti"), res.getString("nome"),
						res.getInt("pd"));

				result.add(c);

			}
			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return result;
	}

	// Calcola alcune statistiche sugli iscritti per il corso codins
	public Stats getStatsByCodins(String codins) {

		String sql = "SELECT s.cds, COUNT(DISTINCT(s.matricola)) AS tot FROM studente AS s, iscrizione AS i "
				+ "WHERE s.matricola = i.matricola AND i.codins = ? AND s.cds <> \"\" GROUP BY s.cds";  // <> \"\" vuol dire diverso da stringa vuota
		
		Stats stats = new Stats();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, codins);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				stats.getCdsMap().put(res.getString("cds"), res.getInt("tot"));
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return stats;
	}

}
