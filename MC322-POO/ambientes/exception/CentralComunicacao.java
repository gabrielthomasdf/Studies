package ambientes.exception;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CentralComunicacao {
    private ArrayList<String> mensagens;
    private static CentralComunicacao instancia;
    private final DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    // Construtor privado para Singleton
    private CentralComunicacao() {
        this.mensagens = new ArrayList<>();
    }

    // Método para obter a instância única
    public static CentralComunicacao getInstancia() {
        if (instancia == null) {
            instancia = new CentralComunicacao();
        }
        return instancia;
    }

    /**
     * Registra uma nova mensagem na central
     * @param remetente ID do robô que enviou a mensagem
     * @param msg Conteúdo da mensagem
     */
    public void registrarMensagem(String remetente, String msg) {
        LocalDateTime agora = LocalDateTime.now();
        String mensagemFormatada = String.format("[%s] %s: %s", 
            agora.format(formatoData),
            remetente,
            msg
        );
        mensagens.add(mensagemFormatada);
    }

    /**
     * Exibe todas as mensagens registradas na central
     */
    public void exibirMensagens() {
        if (mensagens.isEmpty()) {
            System.out.println("Nenhuma mensagem registrada.");
            return;
        }

        System.out.println("=== Histórico de Mensagens ===");
        for (String mensagem : mensagens) {
            System.out.println(mensagem);
        }
        System.out.println("=============================");
    }

    /**
     * Limpa o histórico de mensagens
     */
    public void limparHistorico() {
        mensagens.clear();
        System.out.println("Histórico de mensagens limpo.");
    }

    /**
     * Retorna o número de mensagens no histórico
     * @return número de mensagens
     */
    public int getNumeroMensagens() {
        return mensagens.size();
    }

    /**
     * Obtém uma cópia da lista de mensagens
     * @return ArrayList com as mensagens
     */
    public ArrayList<String> getMensagens() {
        return new ArrayList<>(mensagens);
    }
} 