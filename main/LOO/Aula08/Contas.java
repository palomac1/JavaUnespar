package LOO.Aula08;

import java.io.*;

public class Contas {
    public String NumeroConta, Titular, Saldo, TipoConta;

    public String gravar(String path) {
        try {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdir();
            }

            PrintWriter pw = new PrintWriter(path + "/" + NumeroConta + ".txt");//cria
            pw.println(NumeroConta);
            pw.println(Titular);
            pw.println(Saldo);
            pw.println(TipoConta);
            pw.flush();
            pw.close();
            return "Arquivo gravado com sucesso!";
        } catch (IOException erro) {
            return "Falha ao gravar o arquivo: " + erro.toString();
        }
    }

    public Contas ler(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path + "/" + NumeroConta + ".txt"))) {
            Contas conta = new Contas();
            conta.NumeroConta = br.readLine();
            conta.Titular = br.readLine();
            conta.Saldo = br.readLine();
            conta.TipoConta = br.readLine();
            return conta;
        } catch (IOException e) {
            return null;
        }
    }
}
