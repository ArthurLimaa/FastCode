package br.com.fatcode.model;

import java.time.LocalDate;
import java.util.Objects;

public class User {

    private String username;
    private String password;
    private String email;
    private LocalDate birthdate;
    private String cpf;
    private String address;
    private String cep;
    private String role;

    // Construtor completo para incluir role
    public User(String username, String password, String email, LocalDate birthdate, String cpf, String address, String cep, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.birthdate = birthdate;
        this.cpf = cpf;
        this.address = address;
        this.cep = cep;
        setRole(role); // Define o papel com validação
    }

    // Construtor básico sem o role
    public User(String username, String password) {
        this(username, password, null, null, null, null, null, "CLIENT"); // Papel padrão
    }

    // Construtor padrão
    public User() {
    }

    // Getters e Setters
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        if (!role.equals("ADMIN") && !role.equals("CLIENT")) {
            throw new IllegalArgumentException("Invalid role: " + role);
        }
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", birthdate=" + birthdate +
                ", cpf='" + cpf + '\'' +
                ", address='" + address + '\'' +
                ", cep='" + cep + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) &&
                Objects.equals(email, user.email) &&
                Objects.equals(birthdate, user.birthdate) &&
                Objects.equals(cpf, user.cpf) &&
                Objects.equals(address, user.address) &&
                Objects.equals(cep, user.cep) &&
                Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email, birthdate, cpf, address, cep, role);
    }
}
