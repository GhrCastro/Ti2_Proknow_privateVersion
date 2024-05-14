package application;

import static spark.Spark.*;

import java.util.UUID;
import com.google.gson.Gson;

import models.Usuario;
import services.*;

public class UserApplication {

    private final UsuarioService usuarioService;
    private final Gson gson = new Gson();

    public UserApplication(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public void initializeRoutes() {
        post("/usuarios", (req, res) -> {
            res.type("application/json");
            Usuario usuario = gson.fromJson(req.body(), Usuario.class);
            try {
                usuarioService.addUsuario(usuario);
                return gson.toJson(new StandardResponse(StatusResponse.SUCCESS, "Usuário criado com sucesso."));
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

        get("/usuarios", (req, res) -> {
            res.type("application/json");
            return gson.toJson(new StandardResponse(StatusResponse.SUCCESS, gson.toJsonTree(usuarioService.getAllUsuarios())));
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
    }
}
