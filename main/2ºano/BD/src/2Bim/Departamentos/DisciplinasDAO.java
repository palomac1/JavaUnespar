package Departamentos;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Filme.BD;

public class DisciplinasDAO {
    public Disciplina disciplina;
    public BD bd;
    private PreparedStatement statement;
    private ResultSet resultSet;
    private String men, sql;
    public static final byte INCLUSAO = 1;
    public static final byte ALTERACAO = 2;
    public static final byte EXCLUSAO = 3;

    public DisciplinasDAO() {
        bd = new BD();
        disciplina = new Disciplina();
    }

    public boolean localizar() {
        sql = "select * from disciplinas where ID_disciplina = ?";
        try {
            statement = bd.connection.prepareStatement(sql);
            statement.setString(1, disciplina.getID_disciplina());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                disciplina.setID_disciplina(resultSet.getString(1));
                disciplina.setID_curso(resultSet.getString(2));
                disciplina.setNome(resultSet.getString(3));
                disciplina.setCarga_horaria(resultSet.getInt(4));
                disciplina.setArea_materia(resultSet.getString(5));
                return true;
            }
            return false;
        } catch (SQLException erro) {
            men = "Falha na operação " + erro.toString();
            return false;
        }
    }

    public String atualizar(int operacao) {
        men = "Operação realizada com sucesso!";
        try {
            if (operacao == INCLUSAO) {
                sql = "insert into disciplinas values (?,?,?,?,?)";
                statement = bd.connection.prepareStatement(sql);
                statement.setString(1, disciplina.getID_disciplina());
                statement.setString(2, disciplina.getID_curso());
                statement.setString(3, disciplina.getNome());
                statement.setInt(4, disciplina.getCarga_horaria());
                statement.setString(5, disciplina.getArea_materia());

            } else if (operacao == ALTERACAO) {
                sql = "update disciplinas set ID_curso = ?, nome = ?, carga_horaria = ?, area_materia = ? where ID_disciplina = ?";
                statement = bd.connection.prepareStatement(sql);
                statement.setString(1, disciplina.getID_curso());
                statement.setString(2, disciplina.getNome());
                statement.setInt(3, disciplina.getCarga_horaria());
                statement.setString(4, disciplina.getArea_materia());
                statement.setString(5, disciplina.getID_disciplina());

            } else if (operacao == EXCLUSAO) {
                sql = "delete from disciplinas where ID_disciplina = ?";
                statement = bd.connection.prepareStatement(sql);
                statement.setString(1, disciplina.getID_disciplina());
            }
            if (statement.executeUpdate() == 0) {
                men = "Falha na operação!";
            }
        } catch (SQLException erro) {
            men = "Falha na operação " + erro.toString();
        }
        return men;
    }

    public List<String> getCursos() {
        List<String> cursos = new ArrayList<>();
        sql = "select ID_curso from cursos";
        try {
            statement = bd.connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                cursos.add(resultSet.getString("ID_curso"));
            }
        } catch (SQLException erro) {
            men = "Falha na operação " + erro.toString();
        }
        return cursos;
    }

    public List<Disciplina> getDisciplinas() {
        List<Disciplina> disciplinas = new ArrayList<>();
        sql = "select * from disciplinas";
        try {
            statement = bd.connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Disciplina d = new Disciplina();
                d.setID_disciplina(resultSet.getString(1));
                d.setID_curso(resultSet.getString(2));
                d.setNome(resultSet.getString(3));
                d.setCarga_horaria(resultSet.getInt(4));
                d.setArea_materia(resultSet.getString(5));
                disciplinas.add(d);
            }
        } catch (SQLException erro) {
            men = "Falha na operação " + erro.toString();
        }
        return disciplinas;
    }
}