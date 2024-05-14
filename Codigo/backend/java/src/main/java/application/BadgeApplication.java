package application;

import static spark.Spark.*;

import java.util.UUID;
import com.google.gson.Gson;

import models.Badge;
import services.BadgeService;

public class BadgeApplication {

    private final BadgeService badgeService;
    private final Gson gson = new Gson();

    public BadgeApplication(BadgeService badgeService) {
        this.badgeService = badgeService;
    }

    public void initializeRoutes() {
        /*post("/badges", (req, res) -> { 
            res.type("application/json");
            Badge badge = gson.fromJson(req.body(), Badge.class);
            //System.out.println("###" + usuario);
            try {
                badgeService.addBadge(badge);
                return gson.toJson(new StandardResponse(StatusResponse.SUCCESS, "Usuário criado com sucesso."));
            } catch (IllegalArgumentException e) {
                res.status(400);
                return gson.toJson(new StandardResponse(StatusResponse.ERROR, "Dados do usuário inválidos."));
            }
        });*/

        get("/badges/:id", (req, res) -> {
            res.type("application/json");
            UUID id = UUID.fromString(req.params(":id"));
            Badge badge = badgeService.getBadgeById(id);
            if (badge != null) {
                return gson.toJson(new StandardResponse(StatusResponse.SUCCESS, gson.toJsonTree(badge)));
            } else {
                res.status(404);
                return gson.toJson(new StandardResponse(StatusResponse.ERROR, "Badge não encontrada."));
            }
        });

        get("/badges", (req, res) -> { 
            res.type("application/json");
            return gson.toJson(new StandardResponse(StatusResponse.SUCCESS, gson.toJsonTree(badgeService.getAllBadges())));
        });

        /*put("/badges/:id", (req, res) -> { 
            res.type("application/json");
            UUID id = UUID.fromString(req.params(":id"));
            Badge badge = gson.fromJson(req.body(), Badge.class);
            try {
                badgeService.updateBadge(id, badge);
                return gson.toJson(new StandardResponse(StatusResponse.SUCCESS, "Badge atualizada com sucesso."));
            } catch (IllegalArgumentException e) {
                res.status(400);
                return gson.toJson(new StandardResponse(StatusResponse.ERROR, "Dados da badge inválidos."));
            }
        });*/

        delete("/badges/:id", (req, res) -> { 
            res.type("application/json");
            UUID id = UUID.fromString(req.params(":id"));
            Badge badge = badgeService.getBadgeById(id);
            if (badge != null) {
                badgeService.deleteBadge(id);
                return gson.toJson(new StandardResponse(StatusResponse.SUCCESS, "Badge deletada com sucesso."));
            } else {
                res.status(404);
                return gson.toJson(new StandardResponse(StatusResponse.ERROR, "Badge não encontrada."));
            }
        });
    }
}
