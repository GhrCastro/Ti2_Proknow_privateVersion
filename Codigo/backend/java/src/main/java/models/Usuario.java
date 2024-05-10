package models;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Usuario {

	public static int ultimo_id = 0;

	private int id;
	private String name;
	private String cpf;
	private String email;
	private double salary;
	private String cellNumber;
	private String password;
	private double expenses;
	private Date regDate;
	// private String faceId;

	public Usuario() {

		this.id = -1;
		this.name = "";
		this.cpf = "";
		this.email = "";
		this.salary = 0;
		this.cellNumber = "";
		this.password = "";
		this.expenses = 0;
	}

	public Usuario(int id, String name, String cpf, String email, double salary, String cellNumber, String password,
			double expenses, Date regDate) {

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

	public int getId() {
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

	public Date getRegDate() {
		return regDate;
	}
	// public double getFaceId() {
	// return faceId;

	public void setId(int id) {
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

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	// public void setFaceId(int faceId) {
	// this.faceId = faceId;
	// }
}