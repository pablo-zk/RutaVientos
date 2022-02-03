package com.pablo_zuniga.rutavientos.models;

import com.pablo_zuniga.rutavientos.app.MyApplication;

import java.util.Calendar;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Route extends RealmObject{
    @PrimaryKey
    private int id;

    private String destino;
    private String origen;
    private int plazasLibres;
    private Date horaSalida;
    private String conductor;

    public Route(){}

    public Route(String origen, String destino, int plazasLibres, Date horaSalida, String conductor) {
        this.id = MyApplication.routeId.incrementAndGet();
        this.destino = destino;
        this.origen = origen;
        this.plazasLibres = plazasLibres;
        this.horaSalida = horaSalida;
        this.conductor = conductor;
    }

    public int getId() {
        return id;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public int getPlazasLibres() {
        return plazasLibres;
    }

    public void setPlazasLibres(int plazasLibres) {
        this.plazasLibres = plazasLibres;
    }

    public Date getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(Date horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getConductor() {
        return conductor;
    }

    public void setConductor(String conductor) {
        this.conductor = conductor;
    }
}
