package AtividadeDesign.FactoryMethod;

public class FabricaNotificacaoSMS extends FabricaNotificacao {
    @Override
    public Notificacao criarNotificacao() {
        return new NotificacaoSMS();
    }
}

