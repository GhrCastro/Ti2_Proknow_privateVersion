package services.implementations;

import java.util.List;

import dao.CryptoDao;
import dao.DAO;
import models.Crypto;
import services.interfaces.CryptoService;

public class CryptoServiceImpl implements CryptoService {

	private final CryptoDao crypto;

	public CryptoServiceImpl(DAO dao) {
		this.crypto = dao.getJdbiContext().onDemand(CryptoDao.class);
	}

	@Override
	public void createCryptoTable() {
		try {
			crypto.createTable();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void addCrypto(Crypto newCrypto) {
		try {
			crypto.insert(newCrypto.getId(), newCrypto.getName(), newCrypto.getSymbol(), newCrypto.getPriceUsd());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Crypto> getAllCryptoFromUser() {
		return crypto.listCryptos();
	}

}
