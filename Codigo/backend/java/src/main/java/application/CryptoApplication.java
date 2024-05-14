package application;

import static spark.Spark.*;

import java.util.List;
import com.google.gson.Gson;
import services.CryptoService;
import models.Crypto;

public class CryptoApplication {

    private final CryptoService cryptoService;
    private final Gson gson = new Gson();

    public CryptoApplication(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    public void initializeRoutes() {
        get("/cryptos", (req, res) -> {
            res.type("application/json");
            List<Crypto> cryptos = cryptoService.getAllCryptoFromUser();
            return gson.toJson(new StandardResponse(StatusResponse.SUCCESS, gson.toJsonTree(cryptos)));
        });

        post("/cryptos", (req, res) -> {
            res.type("application/json");
            Crypto crypto = gson.fromJson(req.body(), Crypto.class);
            cryptoService.addCrypto(crypto);
            return gson.toJson(new StandardResponse(StatusResponse.SUCCESS, "Crypto successfully added"));
        });
    }
}
