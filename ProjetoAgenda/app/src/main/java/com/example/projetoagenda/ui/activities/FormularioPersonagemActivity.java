package com.example.projetoagenda.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.projetoagenda.R;
import com.example.projetoagenda.dao.PersonagemDAO;
import com.example.projetoagenda.model.Personagem;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import static com.example.projetoagenda.ui.activities.ConstatesActivities.CHAVE_PERSONAGEM;

public class FormularioPersonagemActivity extends AppCompatActivity {
    //Variáveis que serão utilizadas para alterar os textos do layout diretamente pela classe
    private static final String TITULO_APPBAR_EDITA_PERSONAGEM = "Editar o Personagem";
    private static final String TITULO_APPBAR_NOVO_PERSONAGEM = "Novo Personagem";
    //Variáveis que receberão os valores dos campos presentes na tela
    private EditText campoNome;
    private EditText campoNascimento;
    private EditText campoAltura;

    private final PersonagemDAO dao = new PersonagemDAO();
    private Personagem personagem;

    //Informa o que será aberto ao criar, no caso, menu, sobrescrevendo o método
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //Exibe um novo menu flutuante na tela
        getMenuInflater().inflate(R.menu.activity_formulario_personagem_menu_salvar, menu);
        
        return super.onCreateOptionsMenu(menu);
    }

    //Informa o que acontecerá quando o botão for clicado
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int itemId = item.getItemId();
        if (itemId == R.id.activity_formulario_personagem_menu_salvar) {
            finalizarFormulario();
        }
        return super.onOptionsItemSelected(item);
    }

    //Recebe o layout que será utilizado na classe
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_personagem);
        inicializacaoCampos();
        //configuraBotaoSalvar();
        carregaPersonagem();
    }

    //Metodo cujo tem a intenção de coletar dados
    private void carregaPersonagem() {
        Intent dados = getIntent();
        //Caso contenha dados, os recebe
        if (dados.hasExtra(CHAVE_PERSONAGEM)) {
            setTitle(TITULO_APPBAR_EDITA_PERSONAGEM);
            personagem = (Personagem) dados.getSerializableExtra(CHAVE_PERSONAGEM);
            preencheCampos();
        }
        //Caso não tenha dados, traz método para inserir novo personagem
        else {
            setTitle(TITULO_APPBAR_NOVO_PERSONAGEM);
            personagem = new Personagem();
        }
    }

    //Transfere a informação dos campos preenchidos para exibir os campos no personagem
    private void preencheCampos() {
        campoNome.setText(personagem.getNome());
        campoAltura.setText(personagem.getAltura());
        campoNascimento.setText(personagem.getNascimento());
    }

    //Método utilizado ao confirmar a adição do personagem
    private void finalizarFormulario()
    {
        //Adiciona informações
        preencherPersonagem();
        //Verifica o id pelo método
        if (personagem.IdValido())
        {
            dao.edita(personagem);
            finish();
        } else
        {
            dao.salva(personagem);
        }
        //finaliza tela
        finish();
    }

    //Método para alocar os itens do XML nos itens da classe
    private void inicializacaoCampos()
    {
        //Encontra os XML para alocar nos campos
        campoNome = findViewById(R.id.editText_nome);
        campoNascimento = findViewById(R.id.editText_nascimento);
        campoAltura = findViewById(R.id.editText_altura);

        //Formatação utilizando script importado pelo github
        SimpleMaskFormatter smfAltura = new SimpleMaskFormatter("N,NN");
        MaskTextWatcher mtwAltura = new MaskTextWatcher(campoAltura, smfAltura);
        campoAltura.addTextChangedListener(mtwAltura);

        //Formatação utilizando script importado pelo github
        SimpleMaskFormatter smfNascimento = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher mtwNascimento = new MaskTextWatcher(campoNascimento, smfNascimento);
        campoNascimento.addTextChangedListener(mtwNascimento);
    }

    //Preenche as informações recebidas pelo formulário para a classe personagem
    private void preencherPersonagem() {
        String nome = campoNome.getText().toString();
        String nascimento = campoNascimento.getText().toString();
        String altura = campoAltura.getText().toString();

        personagem.setNome(nome);
        personagem.setAltura(altura);
        personagem.setNascimento(nascimento);

    }
}
