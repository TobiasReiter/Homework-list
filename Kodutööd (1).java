package com.example.projekti_graafika;

public class Kodutööd {
    private String töö;
    private String kuupäev;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Kodutööd(int id, String töökuupäev) {
        String[] osad = töökuupäev.split(";");
        this.töö = osad[0];
        this.kuupäev = osad[1];
        this.id = id;
    }


    @Override
    public String toString() {
        return id + " " + töö + " " + kuupäev;
    }

    public String getTöö() {
        return töö;
    }

    public void setTöö(String töö) {
        this.töö = töö;
    }

    public String getKuupäev() {
        return kuupäev;
    }

    public void setKuupäev(String kuupäev) {
        this.kuupäev = kuupäev;
    }
}

