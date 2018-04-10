package it.polito.tdp.corsi.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.db.CorsoDAO;
import it.polito.tdp.corsi.db.StudenteDAO;

public class Model {

	private List<Corso> corsi;
	private CorsoDAO corsoDAO;
	private StudenteDAO studenteDAO;

	public Model() {
		corsoDAO = new CorsoDAO();
		studenteDAO = new StudenteDAO();
	}

	public List<Corso> listaCorsi() {
		// Carico una sola volta tutti i corsi
		// sfruttando un meccanismo di cache
		if (this.corsi == null)
			this.corsi = corsoDAO.listAll();
		return this.corsi;
	}

	public List<Corso> listaCorsiSemestre(int pd) { // metodo che associo al cerca dell'interfaccia
		// il modello dovrà chiedere al database

		// opzione 1: leggo tutto e filtro io

		// this.corsi = corsoDAO.listAll(); // la salvo nel caso mi serva nuovamente
		//
		// List<Corso> risultato = new ArrayList<>();
		// for (Corso c : this.corsi) {
		// if (c.getPd() == pd) {
		// risultato.add(c);
		// }
		// }
		// return risultato;

		// opzione 2: faccio lavorare il database
		List<Corso> risultato2 = corsoDAO.listByPd(pd);
		return risultato2;

	} // quale opzione usare dipende da ciò che mi serve: è sempre meglio scrivere
		// meno codice (opzione2) ma a volte può essere necessario

	public String getNomeCognomeByMatricola(int matricola) {

		Studente studente = studenteDAO.getStudenteByMatricola(matricola);
		if (studente == null) {
			return "Non ho trovato nessuno studente associato a quella matricola";
		}

		return studente.getNome() + " " + studente.getCognome();

	}

	public List<Studente> getStudentiFromCodins(String codins) {
		List<Studente> studenti = studenteDAO.getStudentiByCodins(codins);
		return studenti;
	}

	public Map<Corso, Stats> getStats() {
		Map<Corso, Stats> corsoMap = new HashMap<Corso, Stats>();
		for (Corso c : listaCorsi()) {
			Stats stats = corsoDAO.getStatsByCodins(c.getCodIns());
			corsoMap.put(c, stats);
		}
		return corsoMap;
		
		/*OPPURE SENZA MAPPE
		 * {StringBuilder sb = new StringBuilder();
		 * for (Corso c : this.listaCorsi()) {
			Stats stats = corsoDAO.getStatsByCodins(c.getCodIns());
			sb.append(stat.toString());
			for (String cds : stat.getMappaCDS().keySet()){
				sb.append(" - " + cds + " " + stat.getMappaCDS().get(cds) + "\n");
				}
			
			return sb.toString()}
		*/
	}
}
