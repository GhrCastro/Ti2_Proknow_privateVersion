package app;
import com.google.gson.JsonParser;

import services.implementations.CryptoAPIImpl;

import org.jdbi.v3.core.Jdbi;

import com.google.gson.JsonObject;
import dao.DAO;

public class Main {

	public static void main(String[] args) {
		CryptoAPIImpl api = new CryptoAPIImpl();
		String data = api.getMarketData();
		
		JsonObject obj = JsonParser.parseString(data).getAsJsonObject();
		System.out.println(obj);
		
		
        System.out.println("ok");

	}

}
