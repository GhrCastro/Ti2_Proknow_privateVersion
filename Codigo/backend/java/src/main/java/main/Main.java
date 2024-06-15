package main;

import static spark.Spark.*;

import com.google.gson.JsonParser;

import application.BadgeApplication;
import application.CryptoApplication;
import application.UserApplication;
import application.WalletApplication;

import org.jdbi.v3.core.Jdbi;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dao.DAO;
import models.Usuario;
import services.*;

public class Main {

	public static void main(String[] args) {
		// CryptoAPIImpl api = new CryptoAPIImpl();
		// String data = api.getMarketData();
		// JsonObject obj = JsonParser.parseString(data).getAsJsonObject();
		// System.out.println(obj);
		// System.out.println("ok");

		port(4567);

		DAO dao = new DAO(); // Conexão com Banco de Dados

		UsuarioService usuarioService = new UsuarioService(dao);
		CryptoService cryptoService = new CryptoService(dao);
		BadgeService badgeService = new BadgeService(dao);
		WalletService walletService = new WalletService(dao);
		RewardService rewardService = new RewardService(dao);

		new UserApplication(usuarioService, walletService, rewardService).initializeRoutes();
		new CryptoApplication(cryptoService).initializeRoutes();
		new WalletApplication(walletService).initializeRoutes();
		new BadgeApplication(badgeService).initializeRoutes();
	}
}
