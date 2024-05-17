package models;

import java.math.BigDecimal;
import java.util.Random;

public class Moeda {
	private int id;
	private String name;
	private BigDecimal amount;
	private String symbol;

	
	public Moeda() {
	}

	public Moeda(String name, BigDecimal amount, String symbol) {
		this.id = generateRandomId();
		this.name = name;
		this.amount = amount;
		this.symbol = symbol;
	}

	private int generateRandomId() {
		// Supondo que queremos IDs entre 1000 e 9999
		
		Random random = new Random();
		
		int min = 1;
		int max = 9999;
		return random.nextInt((max - min) + 1) + min;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

}
