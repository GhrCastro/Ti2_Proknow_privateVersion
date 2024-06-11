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

        

        post("/badges", (req, res) -> {
            res.type("application/json");
            Badge badge = gson.fromJson(req.body(), Badge.class);
            System.out.println("###" + badge.getId());
            try {
                badgeService.addBadge(badge);
                return gson.toJson(new StandardResponse(StatusResponse.SUCCESS, "Badge criada com sucesso."));
            } catch (IllegalArgumentException e) {
                res.status(400);
                return gson.toJson(new StandardResponse(StatusResponse.ERROR, "Dados da Badge inválidos."));
            }
        });

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
            return gson
                    .toJson(new StandardResponse(StatusResponse.SUCCESS, gson.toJsonTree(badgeService.getAllBadges())));
        });


        /*
         * put("/badges/:id", (req, res) -> {
         * res.type("application/json");
         * UUID id = UUID.fromString(req.params(":id"));
         * Badge badge = gson.fromJson(req.body(), Badge.class);
         * try {
         * badgeService.updateBadge(id, badge);
         * return gson.toJson(new StandardResponse(StatusResponse.SUCCESS,
         * "Badge atualizada com sucesso."));
         * } catch (IllegalArgumentException e) {
         * res.status(400);
         * return gson.toJson(new StandardResponse(StatusResponse.ERROR,
         * "Dados da badge inválidos."));
         * }
         * });
         */

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

        // User Badge update
        post("/badges/update-badge", (req,res) -> {
            res.type("application/json");
            UUID id = UUID.fromString(req.params(":id"));
            Badge badge = badgeService.getBadgeById(id); // Vai ter que ser uma lista ou vetor de badges
            BadgeService badgeservice = gson.fromJson(req.body(), BadgeService.class);
            
            try {
                badgeservice.updateBadge(id, badge.getName());
                return gson.toJson(new StandardResponse(StatusResponse.SUCCESS, "Badge atualizado com Sucesso!"));
            } catch (Exception e) {
                // TODO: handle exception
                res.status(400);
                return gson.toJson(new StandardResponse(StatusResponse.ERROR, "Erro ao aatualizar Badge do usuário."));
            }
        });
    }
}
