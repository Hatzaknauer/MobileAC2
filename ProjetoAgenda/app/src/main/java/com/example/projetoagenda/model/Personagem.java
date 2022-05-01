package com.example.projetoagenda.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

//Criação da classe implementando itens da Interface
public class Personagem implements Serializable
{
    //Variáveis para construção do personagem
    private String nome;
    private String nascimento;
    private String altura;
    private int id = 0;

    //Objeto personagem com suas informações
    public Personagem(String nome, String nascimento, String altura) {
        this.nome = nome;
        this.nascimento = nascimento;
        this.altura = altura;
    }

    public Personagem() {

    }
    // Define variáveis de dados com os valores alocados
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }
    public void setAltura(String altura) {
        this.altura = altura;
    }

    //Recebe os valores da classe para retornar através do método
    public String getNome() {
        return nome;
    }
    public String getNascimento() {
        return nascimento;
    }
    public String getAltura() {
        return altura;
    }

    @NonNull
    @Override
    //Declaração dos valores da classe
    public String toString() {
        return nome;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    //Id só é válido se for maior que 0
    public boolean IdValido() {
        return id > 0;
    }
}

