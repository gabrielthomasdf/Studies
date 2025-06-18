package ambientes.robos.robosterrestres;
public class RoboColheitadera extends RoboTerrestre {
    /*Robo de colheita generalista, ele tem apenas um atributo especifico que sinaliza se suas foices de colheita estão ativas. */
    private boolean foice;

    //Declarando métodos construtores, um método default e outro com maior liberdade de parâmetros
    public RoboColheitadera(String nome, int posicaoX, int posicaoY, String direcao, int velocidadeMaxima, boolean foice){
        super(nome, posicaoX, posicaoY, direcao, velocidadeMaxima);
        this.foice = foice;
    }

    public RoboColheitadera(){
        super();
        this.foice = false;
    }
    

    //Declarando método get e interruptor de Foice, foi escolhido um interruptor nesse caso, pois só há duas possibilidades.
    private boolean getFoice(){
        return this.foice;
    }

    private void interruptorFoice(){
        if (this.getFoice()){
            this.foice = false;
        }
        else {
            this.foice = true;
        }
    }

    /*Métodos de inicio de colheita, em que eles iniciam se movimentando para a posição inicial de colheita com o equipamento desligado
    em seguida ativa o equipamento e inicia a colheita. No método com menor quantidade de parâmetros ele colhe o ambiente inteiro
    enquanto o com maior quantidade de parâmetros permite escolha de velocidade, e região de colheita
    */
    protected void colhePlantacao(int largura, int altura){
        if (foice){
            interruptorFoice();
        }
        this.mover(-this.getPosX(), -this.getPosY());

        interruptorFoice();

        while ((this.getPosX() != largura-1) || (this.getPosY() != altura-1)){
            if (this.getPosX() != largura-1) {
                this.mover(largura-1, 0);
            }
            else {
                this.mover(0, 0);
            }
            this.mover(0, 1);
        }
        interruptorFoice();
    }

    protected void preparaSolo(int posIniX, int posIniY, int posFimX, int posFimY, int velocidade){
        if (foice){
            interruptorFoice();
        }
        this.mover((posIniX - this.getPosX()), (posIniY - this.getPosY()), velocidade);
        interruptorFoice();
        while ((this.getPosX() != posFimX) || (this.getPosY() != posFimY)){
            if (this.getPosX() != posFimX) {
                this.mover((posFimX-posIniX), 0, velocidade);
            }
            else {
                this.mover(posIniX, 0, velocidade);
            }
            this.mover(0, 1, velocidade);
        }
        interruptorFoice();
    }
    
    public String toString(){
        String out = "";
        out += "Robo " + getId();
        out += "\n--Posicao: (" + getPosX() + ", " + getPosY() + ", " +getPosZ() +"), direcao: " + getDirecao() + " velocidade máxima: " + getVelocidadeMaxima();
        out += "\n--foice: " + ativaInativa(foice);
        out += "\n--Lista de Sensores ------------------------------------------------------------\n";
        out += "--Número Limite de sensores: " + getLimiteNumSensores() + " Número de Sensores Conectados: " + sensores.size();
        out += "\n";
        for (int i = 0; i < sensores.size(); i++){
            out += sensores.get(i);
            out += "\n";
        }
        return out;
    }
}
