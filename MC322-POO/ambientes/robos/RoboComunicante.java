package ambientes.robos;

import ambientes.exception.CentralComunicacao;
import ambientes.exception.Comunicavel;
import ambientes.exception.ErroComunicacaoException;

public abstract class RoboComunicante extends Robo {
    protected CentralComunicacao central;
    private boolean comunicacaoHabilitada;

    protected RoboComunicante(String id, int posicaoX, int posicaoY, String direcao) {
        super(id, posicaoX, posicaoY, direcao);
        this.central = CentralComunicacao.getInstancia();
        this.comunicacaoHabilitada = true;
    }

    protected RoboComunicante(String id, int posicaoX, int posicaoY, String direcao, int limiteNumSensores) {
        super(id, posicaoX, posicaoY, direcao, limiteNumSensores);
        this.central = CentralComunicacao.getInstancia();
        this.comunicacaoHabilitada = true;
    }

    /**
     * Habilita ou desabilita a capacidade de comunicação do robô
     * @param habilitado true para habilitar, false para desabilitar
     */
    public void setComunicacaoHabilitada(boolean habilitado) {
        this.comunicacaoHabilitada = habilitado;
        if (!habilitado) {
            central.registrarMensagem("SISTEMA", "Comunicação do robô " + this.id + " foi desabilitada");
        } else {
            central.registrarMensagem("SISTEMA", "Comunicação do robô " + this.id + " foi habilitada");
        }
    }

    /**
     * Verifica se o robô tem capacidade de comunicação
     * @return true se a comunicação está habilitada
     */
    public boolean isComunicacaoHabilitada() {
        return this.comunicacaoHabilitada;
    }

    @Override
    public void enviarMensagem(Comunicavel destinatario, String mensagem) 
            throws RoboDesligadoException, ErroComunicacaoException {
        // Verifica se o robô está ligado
        if (this.estado == EstadoRobo.DESLIGADO) {
            throw new RoboDesligadoException("Robô " + this.id + " está desligado e não pode enviar mensagens.");
        }

        // Verifica se o robô tem capacidade de comunicação
        if (!this.comunicacaoHabilitada) {
            throw new ErroComunicacaoException("Robô " + this.id + " está com a comunicação desabilitada.");
        }

        try {
            // Verifica se o destinatário é um RoboComunicante e se sua comunicação está habilitada
            if (destinatario instanceof RoboComunicante) {
                RoboComunicante roboDestino = (RoboComunicante) destinatario;
                if (!roboDestino.isComunicacaoHabilitada()) {
                    throw new ErroComunicacaoException("Robô destinatário está com a comunicação desabilitada.");
                }
            } else {
                throw new ErroComunicacaoException("Destinatário não tem capacidade de comunicação.");
            }

            destinatario.receberMensagem(mensagem);
            central.registrarMensagem(this.id, "Para " + destinatario + ": " + mensagem);
        } catch (RoboDesligadoException e) {
            central.registrarMensagem(this.id, "Falha ao enviar para " + destinatario + ": " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void receberMensagem(String mensagem) throws RoboDesligadoException, ErroComunicacaoException {
        // Verifica se o robô está ligado
        if (this.estado == EstadoRobo.DESLIGADO) {
            throw new RoboDesligadoException("Robô " + this.id + " está desligado e não pode receber mensagens.");
        }

        // Verifica se o robô tem capacidade de comunicação
        if (!this.comunicacaoHabilitada) {
            throw new ErroComunicacaoException("Robô " + this.id + " está com a comunicação desabilitada.");
        }
        
        central.registrarMensagem("SISTEMA", "Robô " + this.id + " recebeu: " + mensagem);
        processarMensagem(mensagem);
    }

    /**
     * Método para processamento específico da mensagem recebida
     * Pode ser sobrescrito pelas subclasses para comportamento personalizado
     * @param mensagem a mensagem recebida
     */
    protected void processarMensagem(String mensagem) {
        System.out.println("Robô " + this.id + " processando mensagem: " + mensagem);
    }

    @Override
    public String toString() {
        return "Robô " + this.id;
    }
} 