package ambientes.robos.robosaereos;

import ambientes.robos.EstadoRobo;

// Classe RoboDrone, que herda de RoboAereo
public class RoboDrone extends RoboAereo {
    private boolean pairando;

    // Declarando o construtor do RoboDrone
    public RoboDrone(String id, int posicaoX, int posicaoY, String direcao, int altitudeMaxima) {
        super(id, posicaoX, posicaoY, direcao, altitudeMaxima);
        this.pairando = false;
    }

    // Método para ativar o modo de pairar
    public void ativarModoPairar() {
        this.pairando = true;
        System.out.println(this.id + " está pairando no ar.");
    }

    // Método para desativar o modo de pairar
    public void desativarModoPairar() {
        this.pairando = false;
        System.out.println(this.id + " saiu do modo de pairar.");
    }

    // Método getter para saber se o drone está pairando
    public boolean isPairando() {
        return this.pairando;
    }

    @Override
    public void executarTarefa() {
        if (this.estado == EstadoRobo.DESLIGADO) {
            throw new IllegalStateException("Drone desligado não pode executar tarefas");
        }
        System.out.println("Drone " + this.id + " executando tarefa de reconhecimento aéreo");
        this.estado = EstadoRobo.EM_TAREFA;
        // Simula execução da tarefa
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        this.estado = EstadoRobo.LIGADO;
    }

    @Override
    public String toString() {
        String out = super.toString();
        out += "\nModo Pairar: " + (pairando ? "Ativado" : "Desativado");
        return out;
    }
}
