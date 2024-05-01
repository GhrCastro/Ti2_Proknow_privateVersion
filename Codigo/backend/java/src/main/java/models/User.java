package models;

import java.util.UUID;

public class User {
	private UUID id;
	private String name;
	private String email;
	private Wallet wallet; 

	public User(String name, String email) {
	    this.id = UUID.randomUUID();
	    this.name = name;
	    this.email = email;
	    this.wallet = new Wallet(id);  
	  }
}