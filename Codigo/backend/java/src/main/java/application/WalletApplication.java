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
        // before((request, response) -> {
        //     response.header("Access-Control-Allow-Origin", "*");
        //     response.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        //     response.header("Access-Control-Allow-Headers", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");
        //     response.header("Access-Control-Allow-Credentials", "true");
        // });

        // options("/*", (request, response) -> {
        //     String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
        //     if (accessControlRequestHeaders != null) {
        //         response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
        //     }

        //     String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
        //     if (accessControlRequestMethod != null) {
        //         response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
        //     }

        //     return "OK";
        // });

//        post("/wallets", (req, res) -> {
//            res.type("application/json");
//            UUID userId = UUID.randomUUID();
//            UUID walletId = UUID.randomUUID();
//            walletService.createWallet(userId);
//            return gson.toJson(new StandardResponse(StatusResponse.SUCCESS, "Carteira criada com sucesso."));
//        });


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

        post("/wallets/transfer/fromUser/:fromUser/toUser/:toUser", (req, res) -> {
            res.type("application/json");
            try {
                UUID fromUser = UUID.fromString(req.params(":fromUser").trim());
                System.out.println(fromUser + "\n");
                UUID toUser = UUID.fromString(req.params(":toUser").trim());
                System.out.println(toUser + "\n");

                String name = req.queryParams("name");
                if (name == null || name.isEmpty()) {
                    throw new IllegalArgumentException("Currency name is missing");
                }

                double amount;
                try {
                    amount = Double.parseDouble(req.queryParams("amount"));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid amount format");
                }

                walletService.transfer(fromUser, toUser, name, amount);
                return gson.toJson(new StandardResponse(StatusResponse.SUCCESS, "Depósito realizado com sucesso."));
            } catch (Exception e) {
                res.status(400);
                return gson.toJson(new StandardResponse(StatusResponse.ERROR, "Erro ao realiclzar saque: " + e.getMessage()));
            }
        });

        post("/wallets/withdraw/:userId", (req, res) -> {
            res.type("application/json");
            try {
                UUID userId = UUID.fromString(req.params(":userId").trim());
                String name = req.queryParams("name");
                double amount;
                try {
                    amount = Double.parseDouble(req.queryParams("amount"));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid amount format");
                }
                walletService.withdraw(userId, name, amount);
                return gson.toJson(new StandardResponse(StatusResponse.SUCCESS, "Saque realizado com sucesso."));
            } catch (Exception e) {
                res.status(400);
                return gson.toJson(new StandardResponse(StatusResponse.ERROR, "Erro ao realiclzar saque: " + e.getMessage()));
            }
        });

        post("/wallets/deposit/:userId", (req, res) -> {
            res.type("application/json");
            UUID userId = UUID.fromString(req.params(":userId"));
            String currency = req.queryParams("currency");
            double amount = Double.parseDouble(req.queryParams("amount"));
            try {
                walletService.deposit(userId, currency, amount);
                return gson.toJson(new StandardResponse(StatusResponse.SUCCESS, "Saque realizado com sucesso."));
            } catch (Exception e) {
                res.status(400);
                return gson.toJson(new StandardResponse(StatusResponse.ERROR, "Erro ao realizar saque: " + e.getMessage()));
            }
        });


    }
}


