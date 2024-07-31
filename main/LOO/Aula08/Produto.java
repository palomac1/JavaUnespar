package LOO.Aula08;

import java.io.*;
public class Produto {
    public String codigo, descricao, preco;
    public Produto ler(String path) {
        try {
            BufferedReader br = new BufferedReader(
                    new FileReader(path + "/" + codigo + ".txt"));
            codigo = br.readLine();
            descricao = br.readLine();
            preco = br.readLine();
            br.close();
            return this;
        } catch (IOException erro) {
            return null;
        }
    }

    public String gravar(String path) {
        try {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdir();
            }

            PrintWriter pw = new PrintWriter(path + "/" + codigo + ".txt");//cria
            pw.println(codigo);
            pw.println(descricao);
            pw.println(preco);
            pw.flush();
            pw.close();
            return "Arquivo gravado com sucesso!";
        } catch (IOException erro) {
            return "Falha ao gravar o arquivo: " + erro.toString();
        }
    }
}