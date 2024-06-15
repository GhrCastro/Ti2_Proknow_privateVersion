package services;

import java.math.BigDecimal;
import java.util.UUID;

import dao.*;
import models.PKWCoin;
import models.Wallet;

public class RewardService {
	private final WalletDao walletDao;
	
	public RewardService(DAO dao) {
		this.walletDao = dao.getJdbiContext().onDemand(WalletDao.class);
	}
	
	public void rewardUser(UUID userId, String action) {
		var walletId = walletDao.findWalletByUserId(userId);
		if(walletId != null) {
			Wallet wallet = new Wallet();
			 BigDecimal rewardAmount;
	            switch (action) {
	                case "REGISTER":
	                    rewardAmount = BigDecimal.valueOf(150);
	                    break;
	                case "COMPLETE_TASK":
	                    rewardAmount = BigDecimal.valueOf(50);
	                    break;
	                default:
	                    rewardAmount = BigDecimal.ZERO;
	                    break;
	            }
	            if (rewardAmount.compareTo(BigDecimal.ZERO) > 0) {
	                wallet.deposit(new PKWCoin(), rewardAmount);
	                walletDao.updateWalletBalance(wallet.getOwnerId(), "PKW", wallet.getBalance("PKW"));
	            }
		}
	}
}
