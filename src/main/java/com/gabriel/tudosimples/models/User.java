package com.gabriel.tudosimples.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = User.TABLE_NAME)
public class User {
    public interface CreateUser{}
    public interface UpdateUser{}

    public static final String TABLE_NAME= "users";

    @Id   // primary key, com isso nós declaramos que o id vai sera a chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY)// com isso nós dizemos a estrategia de como a geração de ids ira acontecer
    @Column(name = "id",unique = true)  // com isso, mesmo sendo redundante, dizemos o nome da coluna no Db e com outros parametros fazemos a configuração
    private Long id;

    @Column(name = "username", length = 100, nullable = false, unique = true)
    @jakarta.validation.constraints.NotNull(groups = CreateUser.class)
    @NotEmpty(groups = CreateUser.class)
    @Size(min =2,max = 100, groups = CreateUser.class)
    private String username;

    @Column(name = "password", length = 60, nullable = false)
    @NotEmpty(groups = {CreateUser.class,UpdateUser.class})
    @jakarta.validation.constraints.NotNull(groups = {CreateUser.class,UpdateUser.class})
    @Size(min = 8, max = 60, groups = {CreateUser.class,UpdateUser.class})
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Task> Tasks = new ArrayList<Task>();

    public User(){

    }

    public User(Long id, String username, String password){
        this.id = id;
        this.password = password;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result +((this.id == null) ? 0 : this.id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object o){
        if (o == this)
            return true;
        if(! (o instanceof  User)){
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.getId()) && Objects.equals(username , user.getUsername()) && Objects.equals(password, user.getPassword());
    }

}
