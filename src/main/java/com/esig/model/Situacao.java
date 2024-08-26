package com.esig.model;

public enum Situacao {
    EM_ANDAMENTO(0, "Em andamento"),
    CONCLUIDA(1, "Concluída");

    private final int nivel;
    private final String descricao;
    
    Situacao(int nivel, String descricao) {
        this.nivel = nivel;
        this.descricao = descricao;
    }

    public int getNivel() {
        return nivel;
    }
    
    public String getDescricao() {
        return descricao;
    }

}