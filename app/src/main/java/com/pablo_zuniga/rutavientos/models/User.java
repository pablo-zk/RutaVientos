package com.pablo_zuniga.rutavientos.models;

import com.pablo_zuniga.rutavientos.app.MyApplication;

import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {
    @PrimaryKey
    private int id;

    private String username;
    private String password;
    private String nombre;
    private String apellido;
    private int telefono;
    private int fotoPerfil;
    private int puntuacion;
    private RealmList<Integer> routesId;

    public User(){}

    public User(String username, String password, String nombre, String apellido, int telefono, int fotoPerfil, int puntuacion, RealmList<Integer> routesId) {
        this.id = MyApplication.userId.incrementAndGet();
        this.username = username;
        this.password = password;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.fotoPerfil = fotoPerfil;
        this.puntuacion = puntuacion;
        this.routesId = routesId;
    }


    public int getId() {
        return id;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public int getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(int fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public RealmList<Integer> getRoutesId() {
        return routesId;
    }

    public void setRoutesId(RealmList<Integer> routesId) {
        this.routesId = routesId;
    }

}
