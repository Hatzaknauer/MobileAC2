package com.example.projetoagenda.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projetoagenda.dao.PersonagemDAO;
import com.example.projetoagenda.R;
import com.example.projetoagenda.model.Personagem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.example.projetoagenda.ui.activities.ConstatesActivities.CHAVE_PERSONAGEM;


    public class ListaPersonagemActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Lista de Personagens";

    //Cria objeto PersonagemDAO
    private final PersonagemDAO dao = new PersonagemDAO();

    //Criação de um adaptador necessário para a lista utilizando Personagem
    private ArrayAdapter<Personagem> adapter;

    //Cria a tela referenciando o devido layout setando o título com base na variável utilizada na classe
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_personagem);
        setTitle(TITULO_APPBAR);
        //Chama método que cria botão
        configuraFabNovoPersonagem();
        //Chama método da lista
        configuraLista();
    }

    //Método que define uma função para o botão da tela
    private void configuraFabNovoPersonagem() {
        //Encontra referência do botão no xml
        FloatingActionButton botaoNovoPersonagem = findViewById(R.id.fab_add);
        //Cria listener dentro do botão pra "ouvir" o click do usuário
        botaoNovoPersonagem.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) { abreFormulario(); }
        });
    }

    //Método para abrir o formulário carregando a classe
    private void abreFormulario() {
        startActivity(new Intent(this, FormularioPersonagemActivity.class));
    }

    //
    @Override
    protected void onResume() {
        super.onResume();
        atualizaPersonagem();
    }

    //
    private void atualizaPersonagem() {
        adapter.clear();
        adapter.addAll(dao.todos());
    }

    //
    private void remove(Personagem personagem){
        dao.remove(personagem);
        adapter.remove(personagem);
    }

    //
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //menu.add("Remover");
        getMenuInflater().inflate(R.menu.activity_lista_personagem_menu, menu);
    }

    //
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.activity_lista_personagem_menu_remover){
            //Instancia alerta de segurança para não ocorrer remoções acidentais
            new AlertDialog.Builder(this)
                    //Conteúdo do alerta, título, mensagem, botão de sim e não
                    .setTitle("Removendo Personagem")
                    .setMessage("Tem certeza que quer remover?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        //Quando clicado no botão "sim", recebe qual o personagem a ser removido
                        public void onClick(DialogInterface dialogInterface, int i) {
                            AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                            Personagem personagemEscolhido = adapter.getItem(menuInfo.position);
                            remove(personagemEscolhido);
                        }
                    })
                    //Não remove caso clicado em não
                    .setNegativeButton("Não", null)
                    //Remove o popup volta pra tela anterior
                    .show();
        }

        //Retorna as informações para o método
        return super.onContextItemSelected(item);
    }

    //
    private void configuraLista(){
        ListView listaDePersonagens = findViewById(R.id.activity_main_lista_personagem);
        configuraAdapter(listaDePersonagens);
        configuraItemPorClique(listaDePersonagens);
        registerForContextMenu(listaDePersonagens);
    }

    //Método para que todos os itens da lista sejam clicáveis
    private void configuraItemPorClique(ListView listaDePersonagens) {
        //Adiciona o listener aos itens da lista
        listaDePersonagens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //Parâmetros que serão passados para o método, para que quando seja clicado o item da lista, seja aberto o item correspondente
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {
                //Recebe o personagem e o aloca na variável
                Personagem personagemEscolhido = (Personagem) adapterView.getItemAtPosition(posicao);
                //Exibe personagem informando para o método
                abreFormularioEditar(personagemEscolhido);
            }
        });
    }

    //Exibe o personagem escolhido na lista, utilizando o personagem passado anteriormente
    private void abreFormularioEditar(Personagem personagemEscolhido) {
        //Utiliza o método Intent e cria um objeto com as informações de qual lista e qual formulário será utilizado
        Intent vaiParaFormulario = new Intent(ListaPersonagemActivity.this, FormularioPersonagemActivity.class);
        //Recebe informações sobre o personagem escolhido que está na Interface, pasando qual o personagem escolhido.
        vaiParaFormulario.putExtra(CHAVE_PERSONAGEM, personagemEscolhido);
        startActivity(vaiParaFormulario);
    }

    //
    private void configuraAdapter(ListView listaDePersonagens){
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listaDePersonagens.setAdapter(adapter);
    }
}