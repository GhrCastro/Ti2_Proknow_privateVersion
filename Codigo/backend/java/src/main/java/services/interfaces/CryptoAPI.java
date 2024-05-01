package services.interfaces;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public interface CryptoAPI {
	  public String getMarketData(); 
}
