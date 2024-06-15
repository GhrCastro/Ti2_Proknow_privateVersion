package application;

import static spark.Spark.*;

import java.util.UUID;

import com.google.gson.Gson;

import services.TaskService;

public class TaskApplication {
	private final TaskService taskService;
	private final Gson gson = new Gson();

	public TaskApplication(TaskService taskService) {
		this.taskService = taskService;
	}

	public void initializeRoutes() {
		// Rota para completar uma tarefa
		post("/tasks/complete", (req, res) -> {
			res.type("application/json");
			UUID userId = UUID.fromString(req.queryParams("userId"));
			String taskName = req.queryParams("taskName");
			taskService.completeTask(userId, taskName);
			return gson.toJson(new StandardResponse(StatusResponse.SUCCESS, "Tarefa completada com sucesso."));
		});
	}
}
