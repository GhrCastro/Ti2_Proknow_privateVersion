package services;

import java.util.UUID;

public class TaskService {
	private final RewardService rewardService;
	
	public TaskService(RewardService rewardService) {
		this.rewardService = rewardService;
	}
	
	public void completeTask(UUID userId, String taskName) {
		rewardService.rewardUser(userId, "COMPLETE_TASK");
	}
}
