package models;

import models.Badge;
import java.util.LinkedList;
import java.util.UUID;
import java.time.LocalDate;
import java.text.SimpleDateFormat;

public class Usuario {

	private UUID id;
	private String name;
	private String cpf;
	private String email;
	private double salary;
	private String cellNumber;
	private String password;
	private double expenses;
	private LocalDate regDate;
	private LinkedList<Badge> Badges;

	public Usuario(UUID id, String name, String cpf, String email, double salary, String cellNumber, String password,
			double expenses, LocalDate regDate){

		this.id = id;
		this.name = name;
		this.cpf = cpf;
		this.email = email;
		this.salary = salary;
		this.cellNumber = cellNumber;
		this.password = password;
		this.expenses = expenses;
		this.regDate = regDate;
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCpf() {
		return cpf;
	}

	public String getEmail() {
		return email;
	}

	public double getSalary() {
		return salary;
	}

	public String getCellNumber() {
		return cellNumber;
	}

	public String getPassword() {
		return password;

	}

	public double getExpenses() {
		return expenses;
	}

	public LocalDate getRegDate() {
		return regDate;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public void setCellNumber(String cellNumber) {
		this.cellNumber = cellNumber;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setExpenses(double expenses) {
		this.expenses = expenses;
	}

	public void setRegDate(LocalDate regDate) {
		this.regDate = regDate;
	}
}
