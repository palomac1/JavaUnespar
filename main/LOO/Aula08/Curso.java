package LOO.Aula08;

import java.io.*;

public class Curso {
    public String codigo, nomeCurso, cargaHoraria, tipoCurso, modalidade;

    public String gravar(String path) {
        try {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdir();
            }
            
        PrintWriter pw = new PrintWriter(path + "/" + codigo + ".txt");
            pw.write(codigo + "\n");
            pw.write(nomeCurso + "\n");
            pw.write(cargaHoraria + "\n");
            pw.write(tipoCurso + "\n");
            pw.write(modalidade + "\n");
            pw.close();
            return "Curso gravado com sucesso!";
        } catch (IOException erro) {
            return "Erro ao gravar o curso: " + erro.toString();
        }
    }

    public Curso ler(String path) {
        try (BufferedReader br = new BufferedReader(
            new FileReader(path + "/" + codigo + ".txt"))) {
            Curso curso = new Curso();
            curso.codigo = br.readLine();
            curso.nomeCurso = br.readLine();
            curso.cargaHoraria = br.readLine();
            curso.tipoCurso = br.readLine();
            curso.modalidade = br.readLine();
            br.close();
            return this;
        } catch (IOException e) {
            return null;
        }
    }
}
