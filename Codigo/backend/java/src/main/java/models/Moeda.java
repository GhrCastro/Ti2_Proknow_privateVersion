package models;

import java.math.BigDecimal;

public class Moeda {
	private int id;
	private String name;
	private BigDecimal amount;
	private String symbol;
	
	public Moeda() { }
	public Moeda(int id, String name, BigDecimal amount, String symbol) { 
		this.id = id;
		this.name = name;
		this.amount = amount;
		this.symbol = symbol;
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
