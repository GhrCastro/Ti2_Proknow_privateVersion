package application;

import static spark.Spark.*;

import java.util.List;
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
            return gson.toJson(new StandardResponse(StatusResponse.SUCCESS, gson.toJsonTree(badgeService.getAllBadges())));
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

        // MEXER AQUI
        // User Badge update
        // patch("/badges/update-badge/:id", (req, res) -> { // post
        //     res.type("application/json");

        //     UUID id = UUID.fromString(req.params(":id"));
        //     // String name = req.params(":badgeName");

        //     // Encontrar a badge que se deseja fazer update pelo seu ID
        //     Badge badgeToUpdate = badgeService.getBadgeById(id);            

        //     if (badgeToUpdate == null) {
        //         res.status(404);
        //         return gson.toJson(new StandardResponse(StatusResponse.ERROR, "Badge não encontrada."));            }
                
        //     try {
        //         // Atualizar o campo 'enable' da badge
        //         badgeToUpdate.setEnable();                
                                

        //         // badgeService.updateBadge(id, name);
        //         return gson.toJson(new StandardResponse(StatusResponse.SUCCESS, "Badge atualizado com Sucesso!"));
        //     } catch (Exception e) {
        //         // TODO: handle exception
        //         res.status(400);
        //         return gson.toJson(new StandardResponse(StatusResponse.ERROR, "Erro ao aatualizar Badge do usuário."));
        //     }
        // });
    }
}
