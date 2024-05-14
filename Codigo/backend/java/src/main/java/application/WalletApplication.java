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
        // Rota para obter a carteira de um usuário
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

        // Outras rotas relacionadas a wallets podem ser adicionadas aqui
    }
}
