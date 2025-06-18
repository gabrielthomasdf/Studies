package ambientes.robos.robosterrestres;

import ambientes.robos.Robo;
import ambientes.robos.EstadoRobo;

public class RoboTerrestre extends Robo{
    protected int velocidadeMaxima;

    //Declarando construtor do RoboTerrestre
    public RoboTerrestre(String nome, int posicaoX, int posicaoY, String direcao, int velocidadeMaxima){
        super(nome, posicaoX, posicaoY, direcao);
        this.velocidadeMaxima = velocidadeMaxima;
    }

    //Declarando um método construtor com overload para o caso default/sem conhecimento de uso
    public RoboTerrestre(){
        super();
        this.velocidadeMaxima = 20;
    }

    //Declarando método get para a velocidade máxima

    protected int getVelocidadeMaxima(){
        return velocidadeMaxima;
    }

    //Declarando os métodos de mover do RoboTerrestre, tomando cuidado para não permitir o uso do método da classe mãe para que não se mova sem velocidade definida
    //fazendo override para usar como default a metade da velocidade máxima
    protected void mover (int deltaX, int deltaY){
        this.posicaoX = posicaoX + deltaX;
        this.posicaoY = posicaoY + deltaY;
        System.out.printf("Se movendo para (%d, %d) com velocidade %.2f\n", posicaoX, posicaoY, ((float) velocidadeMaxima/2));
    }


    protected void mover (int deltaX, int deltaY, int deltaZ, int velocidade){
        if (velocidadeMaxima >= velocidade) {
            this.posicaoX = this.posicaoX + deltaX;
            this.posicaoY = this.posicaoY + deltaY;
        }
        else {
            System.out.println("Não é permitido se mover acima da velocidade máxima, movimento negado!");
        }
    }

    public String toString(){
        String out = "";
        out += "Robo " + getId();
        out += "\n--Posicao: (" + getPosX() + ", " + getPosY() + ", " +getPosZ() +"), direcao: " + getDirecao() + " velocidade máxima: " + getVelocidadeMaxima();
        out += "\n--Lista de Sensores ------------------------------------------------------------\n";
        out += "--Número Limite de sensores: " + getLimiteNumSensores() + " Número de Sensores Conectados: " + sensores.size();
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
            throw new IllegalStateException("Robô terrestre desligado não pode executar tarefas");
        }
        System.out.println("Robô terrestre " + this.id + " executando tarefa de patrulhamento terrestre");
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
