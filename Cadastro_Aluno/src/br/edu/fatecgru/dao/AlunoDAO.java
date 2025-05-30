package br.edu.fatecgru.dao;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import javax.swing.*;
import java.io.FileOutputStream;

import com.mysql.cj.xdevapi.Statement;

import br.edu.fatecgru.model.Aluno;
import br.edu.fatecgru.util.ConnectionFactory;


public class AlunoDAO {
	// Metodos de Java para o Banco
	private Connection conn;	// Conecta ao BD
	private PreparedStatement ps;	// Permite SQL
	private ResultSet rs;	// Uma Table
	private Aluno aluno;
	
	public List<String> carregarCursos() throws SQLException {
        List<String> cursos = new ArrayList<>();
        String sql = "SELECT nome_curso FROM tbcurso";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                cursos.add(rs.getString("nome_curso"));
            }
        }
        return cursos;
    }

	public List<String> carregarDisciplinasPorCurso(int idCurso) throws SQLException {
	    List<String> disciplinas = new ArrayList<>();
	    String sql = "SELECT nome_disciplina FROM tbdisciplinas WHERE idCurso = ?";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, idCurso);
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                String nomeDisciplina = rs.getString("nome_disciplina");
	                //System.out.println("Disciplina encontrada para o ID " + idCurso + ": " + nomeDisciplina); // LOG
	                disciplinas.add(nomeDisciplina);
	            }
	        }
	    }
	    //System.out.println("Disciplinas encontradas para o ID " + idCurso + ": " + disciplinas); // LOG
	    return disciplinas;
	}
	
	
	public AlunoDAO() throws Exception{
		/*
		 * Chama a classe ConnectionFactory e estabelece uma conexão
		 */
		
		try {
			this.conn = ConnectionFactory.getConnection();
		} catch (Exception e) {
			throw new Exception("erro: \n" + e.getMessage());
		}
	}
	
	/* ========================================================
	 *  Metodo salvar Aluno tela Curso
	 * ========================================================
	 */
	
	public void salvar(Aluno aluno) throws Exception {
	    // Verificação inicial de dados essenciais
	    if (aluno.getNome() == null || aluno.getNome().trim().isEmpty()) {
	        throw new Exception("O nome não pode ser nulo ou vazio");
	    }
	    if (aluno.getRGM() == null || aluno.getRGM().trim().isEmpty()) {
	        throw new Exception("O RGM não pode ser nulo ou vazio");
	    }
	    if (aluno.getCPF() == null || aluno.getCPF().trim().isEmpty()) {
	        throw new Exception("O CPF não pode ser nulo ou vazio");
	    }

	    // SQL para inserção do aluno
	    String SQLAluno = "INSERT INTO tbaluno (Nome, RGM, DataNasc, CPF, Email, Endereco, Municipio, UF, Telefone, Campus, Turno) "
	                     + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	    // SQL para inserção do aluno no curso
	    String SQLCursoAluno = "INSERT INTO tbcurso (aluno_rgm, nome_curso, semestre) VALUES (?, ?, ?)";

	    // SQL para inserir dados na tabela de notas e faltas
	    String SQLNotaFalta = "INSERT INTO tbnotas_faltas (aluno_rgm, nota, falta, idDisciplina) VALUES (?, ?, ?, ?)";

	    try {
	        conn.setAutoCommit(false); // Desativa autocommit para controle manual

	        // Inserção do aluno na tabela `tbaluno`
	        try (PreparedStatement ps = conn.prepareStatement(SQLAluno)) {
	            ps.setString(1, aluno.getNome());
	            ps.setString(2, aluno.getRGM());
	            ps.setString(3, aluno.getDataNasc());
	            ps.setString(4, aluno.getCPF());
	            ps.setString(5, aluno.getEmail());
	            ps.setString(6, aluno.getEndereco());
	            ps.setString(7, aluno.getMunicipio());
	            ps.setString(8, aluno.getUF());
	            ps.setString(9, aluno.getTelefone());
	            ps.setString(10, aluno.getCampus()); // Campus
	            ps.setString(11, aluno.getPeriodo()); // Período
	            ps.executeUpdate();
	        }

	        // Inserção na tabela `tbcurso` (associar aluno ao curso)
	        try (PreparedStatement psCurso = conn.prepareStatement(SQLCursoAluno)) {
	            psCurso.setString(1, aluno.getRGM());
	            psCurso.setString(2, aluno.getCurso());
	            psCurso.setString(3, aluno.getSemestre());
	            psCurso.executeUpdate();
	        }

	        // Recuperar o ID do curso associado ao nome do curso
	        String SQLCursoID = "SELECT idCurso FROM tbcurso WHERE nome_curso = ?";
	        int idCurso = 0;
	        try (PreparedStatement psCursoID = conn.prepareStatement(SQLCursoID)) {
	            psCursoID.setString(1, aluno.getCurso());
	            ResultSet rsCurso = psCursoID.executeQuery();
	            if (rsCurso.next()) {
	                idCurso = rsCurso.getInt("idCurso");
	            } else {
	                throw new Exception("Curso não encontrado: " + aluno.getCurso());
	            }
	        }

	        // Recuperar as disciplinas associadas ao curso do aluno
	        String SQLDisciplinas = "SELECT idDisciplina FROM tbdisciplinas WHERE idCurso = ?";
	        try (PreparedStatement psDisciplinas = conn.prepareStatement(SQLDisciplinas)) {
	            psDisciplinas.setInt(1, idCurso); // Usa o idCurso para encontrar as disciplinas do curso
	            ResultSet rs = psDisciplinas.executeQuery();

	            // Inserir as disciplinas na tabela `tbnotas_faltas`
	            try (PreparedStatement psNotas = conn.prepareStatement(SQLNotaFalta)) {
	                while (rs.next()) {
	                    int idDisciplina = rs.getInt("idDisciplina");
	                    psNotas.setString(1, aluno.getRGM()); // RGM do aluno
	                    psNotas.setDouble(2, 0.0); // Inicializa a nota como 0
	                    psNotas.setInt(3, 0); // Inicializa a falta como 0
	                    psNotas.setInt(4, idDisciplina); // idDisciplina
	                    psNotas.addBatch(); // Adiciona a operação ao batch
	                }
	                psNotas.executeBatch(); // Executa todas as inserções de uma vez
	            }
	        }

	        conn.commit(); // Tudo certo, confirma

	    } catch (SQLException sqle) {
	        if (conn != null) {
	            try {
	                conn.rollback(); // Reverte qualquer alteração em caso de erro
	            } catch (SQLException rollbackEx) {
	                throw new Exception("Erro ao fazer rollback: " + rollbackEx.getMessage(), rollbackEx);
	            }
	        }
	        throw new Exception("Erro ao inserir dados: " + sqle.getMessage(), sqle);
	    } finally {
	        try {
	            if (conn != null) {
	                conn.setAutoCommit(true); // Restaura o autocommit
	                ConnectionFactory.closeConnection(conn);
	            }
	        } catch (SQLException e) {
	            throw new Exception("Erro ao fechar a conexão: " + e.getMessage(), e);
	        }
	    }

	    //System.out.println("Nome do aluno: " + aluno.getNome());
	}

	/* ========================================================
	 *  Metodo pesquisar Aluno tela Curso
	 * ========================================================
	 */
	public Aluno pesquisar(String rgm) throws Exception {
	    String SQLAluno = "SELECT nome FROM tbaluno WHERE RGM = ?";
	    String SQLNotaFalta = "SELECT nota, falta FROM tbnotas_faltas WHERE aluno_rgm = ?";
	    // Modifique esta consulta para buscar o idCurso pelo nome_curso
	    String SQLCursoInfo = """
	                          SELECT tc.nome_curso
	                          FROM tbcurso tc
	                          WHERE tc.aluno_rgm = ?
	                          """;
	    String SQLCursoId = "SELECT idCurso FROM tbcurso WHERE nome_curso = ?";

	    Aluno aluno = new Aluno();
	    aluno.setRGM(rgm);

	    try(PreparedStatement psAluno = conn.prepareStatement(SQLAluno)){
	        psAluno.setString(1, aluno.getRGM());
	        ResultSet rs = psAluno.executeQuery();
	        if (rs.next()) {
	            aluno.setNome(rs.getString("Nome"));
	        }
	    }

	    String nomeCursoAluno = null;
	    try (PreparedStatement psCursoInfo = conn.prepareStatement(SQLCursoInfo)) {
	        psCursoInfo.setString(1, aluno.getRGM());
	        ResultSet rsCursoInfo = psCursoInfo.executeQuery();
	        if (rsCursoInfo.next()) {
	            nomeCursoAluno = rsCursoInfo.getString("nome_curso");
	            aluno.setCurso(nomeCursoAluno);
	        } else {
	            //System.out.println("Nenhum curso encontrado para o RGM: " + rgm);
	            aluno.setIdCurso(0); // Ou algum valor padrão
	            return aluno; // Se não encontrar o curso, não precisa continuar
	        }
	    }

	    if (nomeCursoAluno != null) {
	        try (PreparedStatement psCursoId = conn.prepareStatement(SQLCursoId)) {
	            psCursoId.setString(1, nomeCursoAluno);
	            ResultSet rsCursoId = psCursoId.executeQuery();
	            if (rsCursoId.next()) {
	                aluno.setIdCurso(rsCursoId.getInt("idCurso"));
	                //System.out.println("ID do Curso (pelo nome) lido do banco: " + aluno.getIdCurso());
	            } else {
	                //System.out.println("ID do Curso não encontrado para o nome: " + nomeCursoAluno);
	                aluno.setIdCurso(0); // Ou algum valor padrão
	            }
	        }
	    }


	    try (PreparedStatement psNotas = conn.prepareStatement(SQLNotaFalta)) {
	        psNotas.setString(1, aluno.getRGM());
	        ResultSet rs = psNotas.executeQuery();

	        if (rs.next()) {
	            aluno.setNota(rs.getFloat("nota"));
	            aluno.setFalta(rs.getInt("falta"));
	        } else {
	            aluno.setNota(0);
	            aluno.setFalta(0);
	        }

	    } catch (SQLException sqle) {
	        throw new Exception("Erro ao buscar aluno: " + sqle.getMessage(), sqle);
	    }
	    //System.out.println("Nome do aluno: " + aluno.getNome());
	    return aluno;
	}
	private int obterDisciplinaId(String nomeDisciplina) throws SQLException {
	    // Consulta para obter o id da disciplina pelo nome
	    String sql = "SELECT idDisciplina FROM tbdisciplinas WHERE nome_disciplina = ?";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setString(1, nomeDisciplina);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt("idDisciplina");
	            } else {
	                throw new SQLException("Disciplina não encontrada: " + nomeDisciplina);
	            }
	        }
	    }
	}
	
	/* ========================================================
	 *  Metodo Salvar Notas
	 * ========================================================
	 */

	public void salvarNotas(Aluno aluno) throws Exception {
	    //System.out.println("RGM: " + aluno.getRGM());

	    // Verificação inicial de dados essenciais
	    if (aluno.getRGM() == null || aluno.getRGM().trim().isEmpty()) {
	        throw new Exception("O RGM não pode ser nulo ou vazio");
	    }
	    if (aluno.getDisciplina() == null || aluno.getDisciplina().trim().isEmpty()) {
	        throw new Exception("A disciplina não pode ser nula ou vazia");
	    }
	    if (aluno.getSemestre() == null || aluno.getSemestre().trim().isEmpty()) {
	        throw new Exception("O semestre não pode ser nulo ou vazio");
	    }

	    // SQL Statements
	    String SQLCursoAluno = "UPDATE tbcurso SET semestre = ? WHERE aluno_rgm = ?";
	    String SQLNotasFaltas = "UPDATE tbnotas_faltas SET nota = ?, falta = ? WHERE aluno_rgm = ? AND idDisciplina = ?";

	    // Desabilita autocommit para gerenciar transações manualmente
	    try {
	        conn.setAutoCommit(false); // Desabilita o auto commit

	        //System.out.println("Atualizando notas e faltas...");
	        //System.out.println("RGM: " + aluno.getRGM());
	        //System.out.println("Nota: " + aluno.getNota());
	        //System.out.println("Falta: " + aluno.getFalta());
	        //System.out.println("Disciplina: " + aluno.getDisciplina());

	        // Obtendo o ID da disciplina
	        int disciplinaId = obterDisciplinaId(aluno.getDisciplina());  // Captura o ID da disciplina
	        //System.out.println("ID da Disciplina: " + disciplinaId);

	        // Verificando a existência de um registro de notas para o aluno e disciplina
	        String verificaExistenciaSQL = "SELECT 1 FROM tbnotas_faltas WHERE aluno_rgm = ? AND idDisciplina = ?";
	        try (PreparedStatement psVerifica = conn.prepareStatement(verificaExistenciaSQL)) {
	            psVerifica.setString(1, aluno.getRGM());
	            psVerifica.setInt(2, disciplinaId);
	            ResultSet rsVerifica = psVerifica.executeQuery();

	            // Se o registro não existir, insira-o
	            if (!rsVerifica.next()) {
	                //System.out.println("Não existe registro para o aluno RGM " + aluno.getRGM() + " e disciplina " + disciplinaId);
	                String SQLInserirNotas = "INSERT INTO tbnotas_faltas (aluno_rgm, idDisciplina, nota, falta) VALUES (?, ?, ?, ?)";
	                try (PreparedStatement psInserirNotas = conn.prepareStatement(SQLInserirNotas)) {
	                    psInserirNotas.setString(1, aluno.getRGM());
	                    psInserirNotas.setInt(2, disciplinaId);
	                    psInserirNotas.setDouble(3, aluno.getNota()); // nota
	                    psInserirNotas.setInt(4, aluno.getFalta());
	                    psInserirNotas.executeUpdate();
	                    //System.out.println("Novo registro inserido com sucesso.");
	                }
	            } else {
	                // Caso o registro exista, atualize as notas e faltas
	                try (PreparedStatement psNotasFaltas = conn.prepareStatement(SQLNotasFaltas)) {
	                    psNotasFaltas.setDouble(1, aluno.getNota()); // nota
	                    psNotasFaltas.setInt(2, aluno.getFalta());   // falta
	                    psNotasFaltas.setString(3, aluno.getRGM());   // aluno_rgm
	                    psNotasFaltas.setInt(4, disciplinaId);       // idDisciplina
	                    // Verificando o número de linhas afetadas
	                    int linhasAfetadas = psNotasFaltas.executeUpdate();
	                    //System.out.println("Linhas afetadas pelo UPDATE: " + linhasAfetadas);
	                }
	            }
	        }

	        // Atualização na tabela de curso
	        try (PreparedStatement psCurso = conn.prepareStatement(SQLCursoAluno)) {
	            psCurso.setString(1, aluno.getSemestre()); // semestre
	            psCurso.setString(2, aluno.getRGM());      // aluno_rgm
	            psCurso.executeUpdate();
	        }

	        conn.commit(); // Confirma todas as operações realizadas

	    } catch (SQLException sqle) {
	        // Em caso de erro, faz o rollback da transação
	        if (conn != null) {
	            try {
	                conn.rollback(); // Reverte qualquer alteração em caso de erro
	            } catch (SQLException rollbackEx) {
	                throw new Exception("Erro ao fazer rollback: " + rollbackEx.getMessage(), rollbackEx);
	            }
	        }
	        throw new Exception("Erro ao inserir dados: " + sqle.getMessage(), sqle);
	    } finally {
	        // Restaura o autocommit e fecha a conexão
	        try {
	            if (conn != null) {
	                conn.setAutoCommit(true); // Restaura o autocommit
	                ConnectionFactory.closeConnection(conn); // Fecha a conexão
	            }
	        } catch (SQLException e) {
	            throw new Exception("Erro ao fechar a conexão: " + e.getMessage(), e);
	        }
	    }
	}
	
	/* ========================================================
	 *  Metodo excluir Aluno
	 * ========================================================
	 */
	
	public void excluirAluno(String rgm) throws Exception {
	    if (rgm == null || rgm.trim().isEmpty()) {
	        throw new Exception("O RGM não pode ser nulo ou vazio");
	    }

	    String sqlExcluir = "DELETE FROM tbaluno WHERE RGM = ?";

	    try {
	        conn.setAutoCommit(false); // Início da transação

	        try (PreparedStatement psExcluir = conn.prepareStatement(sqlExcluir)) {
	            psExcluir.setString(1, rgm);
	            int linhasAfetadas = psExcluir.executeUpdate();

	            if (linhasAfetadas == 0) {
	                throw new Exception("Nenhum aluno encontrado com o RGM informado.");
	            }
	        }

	        conn.commit(); // Confirma exclusão
	        //System.out.println("Aluno com RGM " + rgm + " excluído com sucesso.");

	    } catch (SQLException sqle) {
	        if (conn != null) {
	            try {
	                conn.rollback(); // Reverte caso algo dê errado
	            } catch (SQLException rollbackEx) {
	                throw new Exception("Erro ao fazer rollback: " + rollbackEx.getMessage(), rollbackEx);
	            }
	        }
	        throw new Exception("Erro ao excluir aluno: " + sqle.getMessage(), sqle);

	    } finally {
	        try {
	            if (conn != null) {
	                conn.setAutoCommit(true); // Restaura autocommit
	                ConnectionFactory.closeConnection(conn);
	            }
	        } catch (SQLException e) {
	            throw new Exception("Erro ao fechar a conexão: " + e.getMessage(), e);
	        } 
	    }
	}
	
	/* ========================================================
	 *  Metodo Excluir Disciplina
	 * ========================================================
	 */	
	public boolean excluirDisciplina(String rgm, String nomeDisciplina) {
	    String buscarIdSql = "SELECT idDisciplina FROM tbdisciplinas WHERE nome_disciplina = ?";
	    String deletarSql = "DELETE FROM tbnotas_faltas WHERE aluno_rgm = ? AND idDisciplina = ?";

	    try (PreparedStatement stmtBuscar = conn.prepareStatement(buscarIdSql)) {

	        stmtBuscar.setString(1, nomeDisciplina);
	        ResultSet rs = stmtBuscar.executeQuery();

	        if (rs.next()) {
	            int idDisciplina = rs.getInt("idDisciplina");

	            try (PreparedStatement stmtDeletar = conn.prepareStatement(deletarSql)) {
	                stmtDeletar.setString(1, rgm);
	                stmtDeletar.setInt(2, idDisciplina);

	                int linhasAfetadas = stmtDeletar.executeUpdate();
	                return linhasAfetadas > 0;
	            }

	        } else {
	            //System.out.println("Disciplina não encontrada.");
	            return false;
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	/* ========================================================
	 *  Metodo Buscar Aluno
	 * ========================================================
	 */
	
	public Aluno buscarDadosAluno(String rgm) throws Exception {
	    String sql = """
	        SELECT a.Nome, c.nome_curso, c.semestre
	        FROM tbaluno a
	        JOIN tbcurso c ON c.aluno_rgm = a.RGM
	        WHERE a.RGM = ?
	    """;

	    try (Connection conn = ConnectionFactory.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setString(1, rgm);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            Aluno aluno = new Aluno();
	            aluno.setRGM(rgm);
	            aluno.setNome(rs.getString("Nome"));
	            aluno.setCurso(rs.getString("nome_curso"));
	            aluno.setSemestre(rs.getString("semestre"));
	            return aluno;
	        } else {
	            return null;
	        }
	    }
	}
	
	/* ========================================================
	 *  Metodo pesquisar Aluno Curso
	 * ========================================================
	 */	
	public Aluno buscarDadosAlunoDados(String rgm) throws Exception {
	    String sqlDados = "SELECT Nome, Endereco, UF, Telefone, Municipio, DataNasc, CPF, Email, Campus, Turno FROM tbaluno WHERE RGM = ?";
	    String sqlCurso = "SELECT nome_curso, semestre FROM tbcurso WHERE aluno_rgm = ?";

	    try (Connection conn = ConnectionFactory.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sqlDados)) {

	        stmt.setString(1, rgm);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            Aluno aluno = new Aluno();
	            aluno.setRGM(rgm);
	            aluno.setNome(rs.getString("Nome"));
	            aluno.setEndereco(rs.getString("Endereco"));
	            aluno.setUF(rs.getString("UF"));
	            aluno.setTelefone(rs.getString("Telefone"));
	            aluno.setMunicipio(rs.getString("Municipio"));
	            aluno.setDataNasc(rs.getString("DataNasc"));
	            aluno.setCPF(rs.getString("CPF"));
	            aluno.setEmail(rs.getString("Email"));
	            aluno.setPeriodo(rs.getString("Turno"));
	            aluno.setCampus(rs.getString("Campus"));

	            // Agora buscar os dados do curso
	            try (PreparedStatement psCurso = conn.prepareStatement(sqlCurso)) {
	                psCurso.setString(1, rgm);
	                ResultSet rs1 = psCurso.executeQuery();

	                if (rs1.next()) {
	                    aluno.setCurso(rs1.getString("nome_curso"));
	                    aluno.setSemestre(rs1.getString("semestre"));
	                }
	            }

	            return aluno;

	        } else {
	            return null;
	        }
	    }
	}
	
	/* ========================================================
	 *  Metodo pesquisar aluno no Boletim
	 * ========================================================
	 */
	public void pesquisarBoletimDoAluno(String rgm, JTable tableBoletim) throws Exception {
	    // Definindo a consulta SQL para pegar a disciplina, nota e falta
	    String sql = """
	        SELECT d.nome_disciplina, nf.nota, nf.falta
	        FROM tbnotas_faltas nf
	        JOIN tbdisciplinas d ON nf.idDisciplina = d.idDisciplina
	        WHERE nf.aluno_rgm = ?
	    """;

	    try (Connection conn = ConnectionFactory.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setString(1, rgm);
	        ResultSet rs = stmt.executeQuery();

	        // Cria ou obtém o modelo da tabela
	        DefaultTableModel model = (DefaultTableModel) tableBoletim.getModel();

	        // Define as colunas apenas uma vez, caso ainda não tenha sido feito
	        if (model.getColumnCount() == 0) {
	            model.addColumn("Disciplina"); // Corrigi para "Disciplina"
	            model.addColumn("Nota"); // Corrigi para "Nota"
	            model.addColumn("Falta"); // Corrigi para "Falta"
	        }

	        // Limpa as linhas da tabela antes de adicionar novos dados
	        model.setRowCount(0);

	        // Adiciona os dados à tabela
	        boolean dadosEncontrados = false;  // Variável de controle para verificar se há dados

	        while (rs.next()) {
	            dadosEncontrados = true;
	            model.addRow(new Object[]{
	                rs.getString("nome_disciplina"),
	                rs.getDouble("nota"),
	                rs.getInt("falta")
	            });
	        }

	        // Se não houver dados, mostramos uma mensagem
	        if (!dadosEncontrados) {
	            JOptionPane.showMessageDialog(null, "Nenhuma disciplina encontrada para este RGM.");
	        }

	    } catch (SQLException e) {
	        // Exceção em caso de erro no banco de dados
	        throw new Exception("Erro ao buscar boletim do aluno: " + e.getMessage(), e);
	    }
	}

}



