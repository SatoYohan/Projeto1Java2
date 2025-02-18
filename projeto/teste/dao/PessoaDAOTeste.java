package dao;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.*;

import entities.Pessoa;

class PessoaDAOTeste {

    @Disabled
    void cadastrarPessoaTeste() throws SQLException, IOException {
        Pessoa pessoa = new Pessoa();
        pessoa.setCodigoPessoa(1);
        pessoa.setNomeCompleto("Andre");
        pessoa.setEmail("andre@gmail.com");
        pessoa.setSenha("123");
        pessoa.setIdFuncao(2);
        
        Connection conn = BancoDados.conectar();
        int resultado = new PessoaDAO(conn).cadastrar(pessoa);
        
        assertEquals(1, resultado);
    }

    @Disabled
    void buscarTodosPessoasTeste() throws SQLException, IOException {
        Connection conn = BancoDados.conectar();
        List<Pessoa> listaPessoas = new PessoaDAO(conn).buscarTodos();
        
        assertNotNull(listaPessoas);
    }

    @Disabled
    void buscarPorCodigoPessoaTeste() throws SQLException, IOException {
        int codigo = 1;
        
        Connection conn = BancoDados.conectar();
        Pessoa pessoa = new PessoaDAO(conn).buscarPorCodigo(codigo);
        
        assertNotNull(pessoa);
        assertEquals("Andre teste", pessoa.getNomeCompleto());
    }

    @Disabled
    void atualizarPessoaTeste() throws SQLException, IOException {
        Pessoa pessoa = new Pessoa();
        pessoa.setCodigoPessoa(1);
        pessoa.setNomeCompleto("Andre teste");
        pessoa.setEmail("teste@gmail.com");
        pessoa.setSenha("456");
        pessoa.setIdFuncao(1);
        
        Connection conn = BancoDados.conectar();
        int resultado = new PessoaDAO(conn).atualizar(pessoa);
        
        assertEquals(1, resultado);
    }

    @Disabled
    void excluirPessoaTeste() throws SQLException, IOException {
        int codigo = 1;
        
        Connection conn = BancoDados.conectar();
        int resultado = new PessoaDAO(conn).excluir(codigo);
        
        assertEquals(1, resultado);
    }

}
