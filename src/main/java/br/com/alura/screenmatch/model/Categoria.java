package br.com.alura.screenmatch.model;

import java.util.function.Predicate;

public enum Categoria{
    ACAO("Action","Ação"),
    COMEDIA("Comedy","Comédia"),
    DRAMA("Drama","Drama"),
    AVENTURA("Adventure","Aventura"),
    ROMANCE("Romance","Romance"),
    CRIME("Crime","Crime"),
    SUSPENSE("Thriller","Suspense");

    private String categoriaOmdb;
    private String CategoriaOmdbPortugues;

     Categoria(String categoriaOmdb, String categoriaOmdbPortugues){
        this.categoriaOmdb = categoriaOmdb;
        this.CategoriaOmdbPortugues = categoriaOmdbPortugues;
    }
    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }

    public static Categoria fromStringPortugues(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.CategoriaOmdbPortugues.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }
}
