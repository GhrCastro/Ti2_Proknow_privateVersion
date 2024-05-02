package services.implementations;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Version;

import services.interfaces.CryptoAPI;

public class CryptoAPIImpl implements CryptoAPI {
	private static final String API_KEY = "22109CC0-5827-463E-91C2-88046A3C909A";

	private HttpClient client = HttpClient.newBuilder()
			.version(Version.HTTP_2)
			.build();

	@Override
	public String getMarketData() {
		{
			String url = "https://rest.coinapi.io/v1/exchangerate/ETH/USD";
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(url))
					.header("X-CoinAPI-Key", API_KEY)
					.build();

			try {
				HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
				return response.body();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}
}
