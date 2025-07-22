package AtividadeDesign.FactoryMethod;

public class NotificacaoEmail implements Notificacao {
    @Override
    public void notificarUsuario() {
        System.out.println("Enviando uma notificação por Email.");
    }
}
