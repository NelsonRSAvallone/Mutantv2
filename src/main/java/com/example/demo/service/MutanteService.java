package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;


import com.example.demo.dtos.MutanteDto;
import com.example.demo.entities.Mutant;
import com.example.demo.repository.MutanteRepository;

@Service
public class MutanteService {

	// inyeccion de dependencia del repository
	private MutanteRepository repository;

	// constructor
	public MutanteService(MutanteRepository repository) {
		this.repository = repository;
	}

	
	// Metidodo para find all va a devolver una lista de
	// MutanteDto-----------------------------
	
	public List<MutanteDto> findAll() throws Exception {

		List<Mutant> entities = repository.findAll();// pide las entidades del repositorio
		List<MutanteDto> dtos = new ArrayList<MutanteDto>();// Aca guardamos las entidades transformadas en dtos

		try {
			for (Mutant i : entities) {
				MutanteDto dto = new MutanteDto();// objeto de dto de donde vamos a sacar la informacion
				dto.setId(i.getId());
				dto.setNombre(i.getNombre());
				dto.setAdn(i.getAdn());
				dto.setEstado(i.getEstado());
				dtos.add(dto);


			}
			return dtos;

		} catch (Exception e) {
			throw new Exception();
		}
	}

	// ----------------------------------FIND by ID------------------------------------
	// el controller inyecta el int id para que el metodo trabaje
	public MutanteDto findById(int id) throws Exception {

		Optional<Mutant> entityOptional = repository.findById(id);// evita que un metodo devuelva null

		MutanteDto dto = new MutanteDto();// objeto para guardar las variables
		try {

			Mutant mutante = entityOptional.get();//
			dto.setId(mutante.getId());
			dto.setNombre(mutante.getNombre());
			dto.setAdn(mutante.getAdn());
			dto.setEstado(mutante.getEstado());

		} catch (Exception e) {

			throw new Exception();
		}
		return dto;
	}

	// ----------------------------------------GuardarHumano------------------------------
	// con este metodo vamos a persistir un humano en la BD y se setea el estado y
	// se devuelve como un dto
	public MutanteDto guardarMutante(MutanteDto dto) throws Exception {

		Mutant mutante = new Mutant();

		mutante.setNombre(dto.getNombre());
		mutante.setAdn(dto.getAdn());
		mutante.setEstado("mutante");

		try {
			mutante = repository.save(mutante);
			dto.setId(mutante.getId());
			dto.setEstado(mutante.getEstado());
			return dto;
		} catch (Exception e) {
			throw new Exception();
		}

	}

	// -----------------------------------------------------GuardarMutante

	public MutanteDto guardarHumano(MutanteDto dto) throws Exception {

		Mutant mutante = new Mutant();
		mutante.setNombre(dto.getNombre());
		mutante.setAdn(dto.getAdn());
		mutante.setEstado("humano");

		try {

			mutante = repository.save(mutante);// Llama al save del JPA
			dto.setId(mutante.getId());
			return dto;

		} catch (Exception e) {
			throw new Exception();
		}

	}
	// ---------------------------------------UPDATE----------------------------------------
	//respetando el id sirve para modificar adn y si resulta humano o mutante, cambia su estado
	public MutanteDto update(int id, MutanteDto dto) throws Exception {
		Optional<Mutant> mutante = repository.findById(id);

		if (isMutant(dto)) {// metodo que define si el nombre es mutante o humano
			// en caso de ser true dentro del metodo se setea el estado mutante
			try {
				Mutant entity = mutante.get();
				
				entity.setNombre(dto.getNombre());
				entity.setAdn(dto.getAdn());
				repository.save(entity);
				dto.setId(entity.getId());
				return dto;

			} catch (Exception e) {

				throw new Exception();
			}

		} else {
			// en caso de ser false dentro del metodo isMutant se setea al dto el estado
			// humano

			try {
				Mutant entity = mutante.get();

				entity.setNombre(dto.getNombre());
				entity.setAdn(dto.getAdn());
				repository.save(entity);
				dto.setId(entity.getId());
				return dto;

			} catch (Exception e) {
				throw new Exception();
			}

		}
	}

	// -------------------------------------Delete-----------------------------------------

	public boolean delete(int id) throws Exception {
		try {
			if (repository.existsById(id)) {
				repository.deleteById(id);
				return true;
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			throw new Exception();
		}

	}

	// ---------------------------------Cuadrar-----------------------------

	public boolean cuadrar(String[] secuencia) {

		boolean bandera = true;
		for (String s : secuencia) {

			if (secuencia.length != s.length()) {
				bandera = false;
			}
		}
		for (String s : secuencia) {
			for (int i = 0; i < s.length(); i++) {

				if (s.charAt(i) != 'A' && s.charAt(i) != 'T' && s.charAt(i) != 'C' && s.charAt(i) != 'G') {

					bandera = false;
				}
			}
		}
		return bandera;
	}

	// ------------------isMutant------------------------------------------------
	public boolean isMutant(MutanteDto dto) {
		
		int f = dto.getAdn().length;// cantidad de elementos,me va a representar la cantidad de filas
		int c = dto.getAdn()[0].length();// longitud del primer elemento, todos deberian ser igual.
		char[][] m = new char[f][c];
	
		int cont = 0;
		

		if (cuadrar(dto.getAdn())) {//verifica las letras permitidas y si es true hace los siguientes for
			for (int i = 0; i < f; i++) {//llena una matriz con los caracteres que estaban en la cadena original
				for (int j = 0; j < c; j++) {
					m[i][j] = dto.getAdn()[i].charAt(j);
				}
			}

		}

		// inicio de for, si i =0 va a dar tantas vueltas mientras sea igual a f
		for (int i = 0, x = f - 1; i < f; i++, x--) {// si X es igual a 0 no me tomaria el ultimo valor
			for (int j = 0, y = c - 1; j < c; j++, y--) {
				// checkear Horizontal
				if (j + 3 < c) {// con esto no se desborda.
					if (m[i][j] == m[i][j + 1] && m[i][j + 1] == m[i][j + 2] && m[i][j + 2] == m[i][j + 3]) {
						cont++;
						// System.out.println("Horizontal");

					}
				}
				// checkear vertical----------------------------------------------------------
				if (i + 3 < f) {
					if (m[i][j] == m[i + 1][j] && m[i + 1][j] == m[i + 2][j] && m[i + 2][j] == m[i + 3][j]) {
						cont++;
						// System.out.println("Vertical");
					}
				}
				// checkear diagonal-----------------------------------------------------------
				if (i + 3 < f && j + 3 < c) {//para que no se desborden las i y las j en diagonal
					if (m[i][j] == m[i + 1][j + 1] && m[i + 1][j + 1] == m[i + 2][j + 2]
							&& m[i + 2][j + 2] == m[i + 3][j + 3]) {
						cont++;
						// System.out.println("diagonal");
					}
				}
				// checkear diagonalInvertida-------------------------------------------------
				if (x - 3 >= 0 && y - 3 >= 0) {// desbordamiento, usando el los dos for del principio
					if (m[x][y] == m[x - 1][y - 1] && m[x - 1][y - 1] == m[x - 2][y - 2]
							&& m[x - 2][y - 2] == m[x - 3][y - 3]) {
						cont++;
						// System.out.println("diagonal invertida");
					}
				} //

				if (cont >2) {//si el contador es mayor a 2 entonces se hace un breack y sale del for
					break;
				}

			}
		} // fin for
		if (cont > 2) {// si el contador es mayor a 2 ya es mutante por lo tanto ya me tira un true
			dto.setEstado("mutante");// si es true le seteo muntante
			return true;

		} else {
			dto.setEstado("humano");// de lo contrario le seteo humano
			return false;
		}

	}// fin isMutant

}
