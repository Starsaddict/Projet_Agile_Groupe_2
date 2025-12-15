package model;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Code")
public class Code {

    @Id
    @Column(name = "Code", nullable = false, length = 100)
    private String code; // clé primaire fournie manuellement par un service

    @Column(name = "Role", nullable = false, length = 100)
    private String role;

    public Code() {
    }

    public Code(String code, String role) {
        this.code = code;
        this.role = role;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Code)) return false;
        Code that = (Code) o;
        return code != null && code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }

    @Override
    public String toString() {
        return "Code [code=" + code + ", role=" + role + "]";
    }
}