package models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
//import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
//import models.Badge;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class Usuario {

    private UUID id;
    private String name;
    private String cpf;
    private String email;
    private double salary;
    private String cellNumber;
    private String password;
    private double expenses;
    private Date regDate;
    private Wallet wallet;
    // private ArrayList<Badge> badges;

    public Usuario() {
        this.id = UUID.randomUUID();
        this.regDate = new Date();
    }

    public Usuario(String name, String cpf, String email, double salary, String cellNumber, String password,
            double expenses) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.salary = salary;
        this.cellNumber = cellNumber;
        this.password = password;
        this.expenses = expenses;
        this.regDate = new Date();
        this.wallet = new Wallet(this.id);
        // this.badges = new ArrayList<>();
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

    public Date getRegDate() {
        return regDate;
    }

    public Wallet getWallet() {
        return wallet;
    }

    // public ArrayList<Badge> getBadges() {
    // return badges;
    // }

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
        // this.password = hashPassword(password);
        this.password = password;
    }

    public void setExpenses(double expenses) {
        this.expenses = expenses;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    // public void addBadge(Badge badge) {
    // badges.add(badge);
    // }

    public String getFormattedDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(regDate);
    }

    public boolean isValid() {
        return name != null && !name.isEmpty() &&
                cpf != null && !cpf.isEmpty() &&
                email != null && !email.isEmpty() &&
                password != null && !password.isEmpty();
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao hashear a senha", e);
        }
    }

    // Methods to convert badges to and from JSON
    // public String badgesToJson() {
    // Gson gson = new Gson();
    // return gson.toJson(badges);
    // }

    // public void badgesFromJson(String json) {
    // Gson gson = new Gson();
    // Type badgeListType = new TypeToken<ArrayList<Badge>>() {}.getType();
    // this.badges = gson.fromJson(json, badgeListType);
    // }
}
