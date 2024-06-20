package application;

import static spark.Spark.*;

import java.util.UUID;
import com.google.gson.Gson;

import models.Badge;
import models.UserBadge;
import models.UserCredentials;
import models.Usuario;
import services.*;

public class UserApplication {

    private final UsuarioService usuarioService;
    private final WalletService walletService;
    private final RewardService rewardService;
    private final Gson gson = new Gson();

    public UserApplication(UsuarioService usuarioService, WalletService walletService, RewardService rewardService) {
        this.usuarioService = usuarioService;
        this.walletService = walletService;
        this.rewardService = rewardService;
    }

    public void initializeRoutes() {

        // Código para desbloqueio do CORS
        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.header("Access-Control-Allow-Headers",
                    "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");
            response.header("Access-Control-Allow-Credentials", "true");
        });

        // Opções para CORS com preflight
        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });
        // --------------------------------------------------------//

        post("/usuarios", (req, res) -> {
            res.type("application/json");
            Usuario usuario = gson.fromJson(req.body(), Usuario.class);
            // System.out.println("###" + usuario);
            try {
                usuarioService.addUsuario(usuario);

                // Recebe badge cadastro
                usuarioService.addUserBadge(usuario.getId(), UUID.fromString("c081aab6-f162-49b4-b5b5-f5ba9b8e9214"));

                // rewardService.rewardUser(usuario.getId(), "REGISTER");

                return gson.toJson(new StandardResponse(StatusResponse.SUCCESS, gson.toJsonTree(usuario)));
            } catch (IllegalArgumentException e) {
                res.status(400);
                return gson.toJson(new StandardResponse(StatusResponse.ERROR, "Dados do usuário inválidos."));
            }
        });

        get("/usuarios/:id", (req, res) -> {
            res.type("application/json");
            UUID id = UUID.fromString(req.params(":id"));
            Usuario usuario = usuarioService.getUsuarioById(id);
            if (usuario != null) {
                return gson.toJson(new StandardResponse(StatusResponse.SUCCESS, gson.toJsonTree(usuario)));
            } else {
                res.status(404);
                return gson.toJson(new StandardResponse(StatusResponse.ERROR, "Usuário não encontrado."));
            }
        });

        post("/login", (req, res) -> {
            res.type("application/json");
            UserCredentials userCredentials = gson.fromJson(req.body(), UserCredentials.class);

            // System.out.println("###" + userCredentials.getEmail());
            // System.out.println("###" + userCredentials.getPassword());

            Usuario usuario = usuarioService.getUsuarioByEmail(userCredentials.getEmail());
            System.out.println("###" + usuario.getWallet_id());

            if (usuario != null) {
                // System.out.println("###" + usuario.getPassword());
                if (usuario.getPassword().equals(userCredentials.getPassword())) {
                    return gson.toJson(new StandardResponse(StatusResponse.SUCCESS, gson.toJsonTree(usuario)));
                } else {
                    return gson.toJson(new StandardResponse(StatusResponse.ERROR, "Senha incorreta"));
                }
            } else {
                res.status(404);
                return gson.toJson(new StandardResponse(StatusResponse.ERROR, "Usuário não encontrado."));
            }
        });

        get("/usuarios", (req, res) -> {
            res.type("application/json");
            return gson.toJson(
                    new StandardResponse(StatusResponse.SUCCESS, gson.toJsonTree(usuarioService.getAllUsuarios())));
        });

        put("/usuarios/:id", (req, res) -> {
            res.type("application/json");
            UUID id = UUID.fromString(req.params(":id"));
            Usuario usuario = gson.fromJson(req.body(), Usuario.class);
            try {
                usuarioService.updateUsuario(id, usuario);
                return gson.toJson(new StandardResponse(StatusResponse.SUCCESS, "Usuário atualizado com sucesso."));
            } catch (IllegalArgumentException e) {
                res.status(400);
                return gson.toJson(new StandardResponse(StatusResponse.ERROR, "Dados do usuário inválidos."));
            }
        });

        delete("/usuarios/:id", (req, res) -> {
            res.type("application/json");
            UUID id = UUID.fromString(req.params(":id"));
            Usuario usuario = usuarioService.getUsuarioById(id);
            if (usuario != null) {
                usuarioService.deleteUsuario(id);
                return gson.toJson(new StandardResponse(StatusResponse.SUCCESS, "Usuário deletado com sucesso."));
            } else {
                res.status(404);
                return gson.toJson(new StandardResponse(StatusResponse.ERROR, "Usuário não encontrado."));
            }
        });

        // users-badges
        post("/usuarios-badges", (req, res) -> {
            res.type("application/json");
            UserBadge userBadge = gson.fromJson(req.body(), UserBadge.class);

            try {
                usuarioService.addUserBadge(userBadge.getUser_id(), userBadge.getBadge_id());
                return gson.toJson(new StandardResponse(StatusResponse.SUCCESS, "Badge vinculada ao usuario!"));
            } catch (Exception e) {
                // TODO: handle exception
                res.status(400);
                return gson.toJson(new StandardResponse(StatusResponse.ERROR, "Erro ao vincular Badge ao usuário."));
            }
        });

        get("/usuarios-badges/:id", (req, res) -> {
            res.type("application/json");
            UUID user_id = UUID.fromString(req.params(":id"));

            try {
                return gson.toJson(new StandardResponse(StatusResponse.SUCCESS,
                        gson.toJsonTree(usuarioService.getAllUserBadges(user_id))));
            } catch (Throwable e) {
                // TODO: handle exception
                e.printStackTrace();
                res.status(400);
                return gson
                        .toJson(new StandardResponse(StatusResponse.ERROR, "Erro ao vincular Badge ao usuário." + e));
            }
        });

        // moeda-wallet
        
        get("/wallets/:id", (req, res) -> { // id:usuario
            res.type("application/json");
            UUID user_id = UUID.fromString(req.params(":id"));

            try {
               return gson.toJson(new StandardResponse(StatusResponse.SUCCESS, gson.toJsonTree(usuarioService.getAllUserMoedas(user_id))));
            } catch (Throwable e) {
                // TODO: handle exception
                e.printStackTrace();
                res.status(400);
                return gson
                        .toJson(new StandardResponse(StatusResponse.ERROR, "Erro ao receber moeda" + e));
            }
        });

    }
}
