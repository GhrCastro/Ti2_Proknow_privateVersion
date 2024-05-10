package services.interfaces;
import java.util.List;

import models.Crypto;

public interface CryptoService {
	void createCryptoTable();
	void addCrypto(Crypto crypto);
	List<Crypto> getAllCryptoFromUser();
}
