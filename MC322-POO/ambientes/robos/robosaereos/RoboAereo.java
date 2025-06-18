package ambientes.robos.robosaereos;

import ambientes.robos.Robo;
import ambientes.robos.EstadoRobo;

public class RoboAereo extends Robo {

    // Atributos adicionais
    protected int altitudeMaxima;

    // Declarando construtor do RoboAereo
    public RoboAereo(String nome, int posicaoX, int posicaoY, String direcao, int altitudeMaxima) {
        super(nome, posicaoX, posicaoY, direcao);
        this.posicaoZ = 0;
        this.altitudeMaxima = altitudeMaxima;
    }

    // Métodos adicionais para subir e para descer
    public void subir(int metros) {
        if (this.posicaoZ + metros <= this.altitudeMaxima) {
            this.posicaoZ += metros;
        } else {
            System.out.println("Altitude máxima atingida!");
        }
    }

    public void mover(int X, int Y, int Z){
        this.posicaoX = X;
        this.posicaoY = Y;
        this.posicaoZ = Z;
    }

    public void descer(int metros) {
        if (this.posicaoZ - metros >= 0) {
            this.posicaoZ -= metros;
        } else {
            System.out.println("O robô já está no solo!");
        }
    }

    // Métodos Getters
    public int getAltitude() {
        return this.posicaoZ;
    }

    public int getAltitudeMaxima() {
        return this.altitudeMaxima;
    }

    // Fazendo override para usar como default a exibição da posição incluindo a posicaoZ
    @Override
    public void exibirPosicao() {
        System.out.println(this.id + " está em: (" + this.posicaoX + ", " + this.posicaoY + ", " + this.posicaoZ + ")");
    }

    public String toString(){
        String out = "";
        out += "Robo " + getId();
        out += "\n--Posicao: (" + getPosX() + ", " + getPosY() + ", " + getAltitude() + "), direcao: " + getDirecao() + " altitude máxima: " + getAltitudeMaxima();
        out += "\n--Lista de Sensores ------------------------------------------------------------\n";
        out += "--Número Limite de sensores: " + getLimiteNumSensores() + "Número de Sensores Conectados: " + sensores.size();
        out += "\n";
        for (int i = 0; i < sensores.size(); i++){
            out += sensores.get(i);
            out += "\n";
        }
        return out;
    }

    @Override
    public void executarTarefa() {
        if (this.estado == EstadoRobo.DESLIGADO) {
            throw new IllegalStateException("Robô aéreo desligado não pode executar tarefas");
        }
        System.out.println("Robô aéreo " + this.id + " executando tarefa de patrulhamento aéreo");
        this.estado = EstadoRobo.EM_TAREFA;
        // Simula execução da tarefa
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        this.estado = EstadoRobo.LIGADO;
    }
}
