package LOO.Aula08;

import java.io.*;

public class Curso {
    public String codigo, nomeCurso, cargaHoraria, tipoCurso, modalidade;

    public String gravar(String caminho) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho + "/" + codigo + ".txt"))) {
            bw.write(codigo + "\n");
            bw.write(nomeCurso + "\n");
            bw.write(cargaHoraria + "\n");
            bw.write(tipoCurso + "\n");
            bw.write(modalidade + "\n");
            return "Curso gravado com sucesso!";
        } catch (IOException e) {
            return "Erro ao gravar o curso: " + e.getMessage();
        }
    }

    public Curso ler(String caminho) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminho + "/" + codigo + ".txt"))) {
            Curso curso = new Curso();
            curso.codigo = br.readLine();
            curso.nomeCurso = br.readLine();
            curso.cargaHoraria = br.readLine();
            curso.tipoCurso = br.readLine();
            curso.modalidade = br.readLine();
            return curso;
        } catch (IOException e) {
            return null;
        }
    }
}
