package models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Usuario {

    private UUID id;
    private String name;
    private String cpf;
    private String email;
    private String password;
    private Date regDate;

    public Usuario() {
        this.id = UUID.randomUUID();
        this.regDate = new Date();
    }

    public Usuario(String name, String cpf, String email, String password) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.password = hashPassword(password); 
        this.regDate = new Date();
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

    public String getPassword() {
        return password;
    }

    public Date getRegDate() {
        return regDate;
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

    public void setPassword(String password) {
        this.password = hashPassword(password);
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

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


}
