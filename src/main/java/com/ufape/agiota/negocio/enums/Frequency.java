package com.ufape.agiota.negocio.enums;

public enum Frequency {
    SEMANAL(7), QUINZENAL(15), MENSAL(30);

    public int valor;
    Frequency(int valor){
        this.valor = valor;
    }
}
