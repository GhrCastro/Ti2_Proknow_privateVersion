package models;

import java.math.BigDecimal;

public class Crypto extends Moeda {
	private BigDecimal priceUsd;
	private BigDecimal volume24h;
	private BigDecimal priceChange24h;
	private BigDecimal marketCap;

	private String chain;

	public Crypto(int id, String name, BigDecimal amount, String symbol, BigDecimal priceUsd, BigDecimal volume24h,
			BigDecimal priceChange24h, BigDecimal marketCap, String chain) {
		super(id, name, amount, symbol);
		this.priceUsd = priceUsd;
		this.volume24h = volume24h;
		this.priceChange24h = priceChange24h;
		this.marketCap = marketCap;
		this.setChain(chain);
	}
	
	 // Getters e Setters
    public BigDecimal getPriceUsd() {
        return priceUsd;
    }

    public void setPriceUsd(BigDecimal priceUsd) {
        this.priceUsd = priceUsd;
    }

    public BigDecimal getVolume24h() {
        return volume24h;
    }

    public void setVolume24h(BigDecimal volume24h) {
        this.volume24h = volume24h;
    }

    public BigDecimal getPriceChange24h() {
        return priceChange24h;
    }

    public void setPriceChange24h(BigDecimal priceChange24h) {
        this.priceChange24h = priceChange24h;
    }

    public BigDecimal getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(BigDecimal marketCap) {
        this.marketCap = marketCap;
    }

	public String getChain() {
		return chain;
	}

	public void setChain(String chain) {
		this.chain = chain;
	}

}
