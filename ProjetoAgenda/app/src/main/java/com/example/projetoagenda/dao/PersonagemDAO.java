package com.example.projetoagenda.dao;

import com.example.projetoagenda.model.Personagem;

import java.util.List;
import java.util.ArrayList;


public class PersonagemDAO {

    //Lista para adicionar personagens
    private final static List<Personagem> personagens = new ArrayList<>();
    private static int contadorDeIds = 1;

    //Método para salvar um novo personagem
    public void salva(Personagem personagemSalvo) {
        personagemSalvo.setId(contadorDeIds);
        //Adiciona um personagem à lista
        personagens.add(personagemSalvo);
        //Chama método para alterar o ID e não ter 2 ids repetidos
        atualizaId();
    }
    //Método para somar +1 no ID
    private void atualizaId()
    {
        contadorDeIds++; //Soma +1
    }

    //Método para editar personagem
    public void edita(Personagem personagem)
    {
        //Define o personagem encontrado através do método buscaPersonagemId
        Personagem personagemEncontrado = buscaPersonagemId(personagem);
        //Se o personagem encontrado for diferente de nulo
        if (personagemEncontrado != null)
        {
            //Encontra qual a posição do personagem na lista
            int posicaoDoPersonagem = personagens.indexOf(personagemEncontrado);
            //Re-insere o personagem na lista, atualizado
            personagens.set(posicaoDoPersonagem, personagem);
        }
    }

    //Método para buscar um personagem e retorná-lo com o Id
    private Personagem buscaPersonagemId(Personagem personagem)
    {
        //Para cada item dentro da lista personagens
        for (Personagem p : personagens)
        {
            //Recebe o ID para retornar
            if (p.getId() == personagem.getId()) {
                return p;
            }
        }
        //Quando acabar (ou for 0) os personagens retorna vazio
        return null;
    }

    //Lista com todos os personagens
    public List<Personagem> todos()
    {
        //Retorna uma nova lista de personagem contendo todos
        return new ArrayList<>(personagens);
    }

    //Remove personagem
    public void remove(Personagem personagem)
    {
        //Recebe o personagem através do método busca
        Personagem personagemDevolvido = buscaPersonagemId(personagem);
        //Se existir um valor no personagemDevolvido
        if (personagemDevolvido != null)
        {
            //chama método e passa qual o personagem a ser removido
            personagens.remove(personagemDevolvido);
        }
    }
}
