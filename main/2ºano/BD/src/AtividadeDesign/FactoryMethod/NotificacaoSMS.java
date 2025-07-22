package AtividadeDesign.FactoryMethod;

public class NotificacaoSMS implements Notificacao {
    @Override
    public void notificarUsuario() {
        System.out.println("Enviando uma notificação por SMS.");
    }
}

