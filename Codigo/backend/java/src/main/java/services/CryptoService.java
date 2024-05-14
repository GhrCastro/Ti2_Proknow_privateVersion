package services;

import java.util.List;

import dao.CryptoDao;
import dao.DAO;
import models.Crypto;
import services.interfaces.CryptoService;

public class CryptoService {

	private final CryptoDao crypto;

	public CryptoService(DAO dao) {
		this.crypto = dao.getJdbiContext().onDemand(CryptoDao.class);
	}

	public void createCryptoTable() {
		try {
			crypto.createTable();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void addCrypto(Crypto newCrypto) {
		try {
			crypto.insert(newCrypto.getId(), newCrypto.getName(), newCrypto.getSymbol(), newCrypto.getPriceUsd());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<Crypto> getAllCryptoFromUser() {
		return crypto.listCryptos();
	}

}
