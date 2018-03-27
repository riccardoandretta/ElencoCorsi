package it.polito.tdp.corsi.model;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.corsi.db.CorsoDAO;

public class Model {

	private List<Corso> corsi;

	public List<Corso> listaCorsiSemestre(int pd) { // metodo che associo al cerca dell'interfaccia
		// il modello dovrà chiedere al database

		CorsoDAO dao = new CorsoDAO();

		// opzione 1: leggo tutto e filtro io

		this.corsi = dao.listAll(); // la salvo nel caso mi serva nuovamente

		List<Corso> risultato = new ArrayList<>();
		for (Corso c : this.corsi) {
			if (c.getPd() == pd) {
				risultato.add(c);
			}
		}
		return risultato;

		// opzione 2: faccio lavorare il database
//		List<Corso> risultato2 = dao.listByPd(pd);
//		return risultato2;

	} // quale opzione usare dipende da ciò che mi serve: è sempre meglio scrivere
		// meno codice (opzione2) ma a volte può essere necessario
}
