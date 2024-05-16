package application;

import static spark.Spark.*;

import java.util.UUID;
import com.google.gson.Gson;
import services.WalletService;
import models.Wallet;

public class WalletApplication {

    private final WalletService walletService;
    private final Gson gson = new Gson();

    public WalletApplication(WalletService walletService) {
        this.walletService = walletService;
    }

    public void initializeRoutes() {
        post("/wallets", (req, res) -> {
            res.type("application/json");
            UUID userId = UUID.fromString(req.queryParams("userId"));
            walletService.createWallet(userId);
            return gson.toJson(new StandardResponse(StatusResponse.SUCCESS, "Carteira criada com sucesso."));
        });

        get("/wallets/:userId", (req, res) -> {
            res.type("application/json");
            UUID userId = UUID.fromString(req.params(":userId"));
            Wallet wallet = walletService.getWalletByUserId(userId);
            if (wallet != null) {
                return gson.toJson(new StandardResponse(StatusResponse.SUCCESS, gson.toJsonTree(wallet)));
            } else {
                res.status(404);
                return gson.toJson(new StandardResponse(StatusResponse.ERROR, "Carteira não encontrada."));
            }
        });

        post("/wallets/:userId/deposit", (req, res) -> {
            res.type("application/json");
            UUID userId = UUID.fromString(req.params(":userId"));
            String currency = req.queryParams("currency");
            double amount = Double.parseDouble(req.queryParams("amount"));
            try {
                walletService.deposit(userId, currency, amount);
                return gson.toJson(new StandardResponse(StatusResponse.SUCCESS, "Depósito realizado com sucesso."));
            } catch (Exception e) {
                res.status(400);
                return gson.toJson(new StandardResponse(StatusResponse.ERROR, "Erro ao realizar depósito: " + e.getMessage()));
            }
        });

        post("/wallets/:userId/withdraw", (req, res) -> {
            res.type("application/json");
            UUID userId = UUID.fromString(req.params(":userId"));
            String currency = req.queryParams("currency");
            double amount = Double.parseDouble(req.queryParams("amount"));
            try {
                walletService.withdraw(userId, currency, amount);
                return gson.toJson(new StandardResponse(StatusResponse.SUCCESS, "Saque realizado com sucesso."));
            } catch (Exception e) {
                res.status(400);
                return gson.toJson(new StandardResponse(StatusResponse.ERROR, "Erro ao realizar saque: " + e.getMessage()));
            }
        });
    }
}
