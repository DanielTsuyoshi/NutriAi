package br.com.fiap.nutriai.models;

public record Token(
    String token,
    String type,
    String prefix
) {}