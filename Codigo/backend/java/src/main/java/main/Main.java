package main;
import static spark.Spark.*;

import com.google.gson.JsonParser;
import application.UserApplication;
import org.jdbi.v3.core.Jdbi;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dao.DAO;
import models.Usuario;
import services.UsuarioService;

public class Main {

	public static void main(String[] args) {
		//CryptoAPIImpl api = new CryptoAPIImpl();
		//String data = api.getMarketData();
		//JsonObject obj = JsonParser.parseString(data).getAsJsonObject();
		//System.out.println(obj);			
        //System.out.println("ok");

		port(4567);
		
		DAO dao = new DAO();
		UsuarioService usuarioService = new UsuarioService(dao);
		UserApplication userApplication =  new UserApplication(usuarioService);
		userApplication.initializeRoutes();
		
	}
}
