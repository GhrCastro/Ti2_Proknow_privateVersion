package app;
import com.google.gson.JsonParser;

import services.implementations.CryptoAPIImpl;

import com.google.gson.JsonObject;
public class Main {

	public static void main(String[] args) {
		CryptoAPIImpl api = new CryptoAPIImpl();
		String data = api.getMarketData();
		
		JsonObject obj = JsonParser.parseString(data).getAsJsonObject();
		System.out.println(obj);
	}

}
