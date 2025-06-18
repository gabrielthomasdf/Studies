package ambientes.robos.robosterrestres;
public class RoboPreparaSolo extends RoboTerrestre{
    /*robo para agricultura que prepara o solo usando arados, grades e subsoladores. Como há uma distância entre as plantações esse atributo
    foi adicionado como atributo especifico desse robo, que será usado durante a movimentação de preparação de solo.
    Esses robos ainda terão atributo especifico que sinaliza a ativação dos equipamentos de preparo do solo*/
    private int distanciaCorredores; //em metros
    private boolean arado = false;
    private boolean grades = false;
    private boolean subsolador = false;

    //Declarando métodos construtores, um com todas as opções disponíveis, e o outro (overload) sem parametros para ser usado como default
    public RoboPreparaSolo(String nome, int posicaoX, int posicaoY, String direcao, int velocidadeMaxima, int distanciaCorredores){
        super(nome, posicaoX, posicaoY, direcao, velocidadeMaxima);
        this.distanciaCorredores = distanciaCorredores;
    }

    public RoboPreparaSolo(){
        super();
        this.distanciaCorredores = 1;
    }

    //Definindo métodos get e set para o atributo de distancia entre corredores
    protected int getDistanciaCorredores(){
        return this.distanciaCorredores;
    }

    protected void setDistanciaCorredores(int distanciaCorredores){
        this.distanciaCorredores = distanciaCorredores;
    }

    //Declarando get e set dos equipamentos para preparo de solo, como só há duas opções para esses atributos (true/false) então eu o tratei como
    //interruptor
    protected void interruptorArado() {
        if (this.getArado()){
            this.arado = false;
        }
        else {
            this.arado = true;
        }
    }

    protected void interruptorGrades() {
        if (this.getGrades()){
            this.grades = false;
        }
        else {
            this.grades = true;
        }
    }

    protected void interruptorSubsolador () {
        if (this.getSubsolador()){
            this.subsolador = false;
        }
        else {
            this.subsolador = true;
        }
    }

    protected boolean getArado() {
        return this.arado;
    }

    protected boolean getGrades() {
        return this.grades;
    }

    protected boolean getSubsolador() {
        return this.subsolador;
    }

    /*Declarando métodos para o preparo de solo esses métodos estão bem simples no momento, em que o que contém apenas 2 parametrôs
     * prepara o solo do ambiente inteiro, desativa os equipamentos se eles estiverem ativos, e se movimento para o ponto (0, 0), dai começa a 
     * se movimentar de linha em linha até o fim. A velocidade nesse caso é uma velocidade baixa que permite um preparo de solo generalista
     * para diversos solos, e também leva em conta uma baixa velocidade para não acontecer acidentes.
     * O método com mais liberdade de parâmetros permite também só o preparo do solo numa regiao, velocidade e quais preparos de solo serão feitos
     * Ambos finalizam desligando os equipamentos de preparo.
     */
    protected void preparaSolo(int largura, int altura){
        if (getArado()){
            interruptorArado();
        }
        if (getGrades()){
            interruptorGrades();
        }
        if (getSubsolador()){
            interruptorSubsolador();
        }
        this.mover(-this.getPosX(), -this.getPosY());
        System.out.println("a");
        interruptorArado();
        interruptorGrades();
        interruptorSubsolador();
        
        while ((this.getPosX() != largura-1) || (this.getPosY() != altura-1)){
            if (this.getPosX() != largura-1) {
                this.mover(largura-1, 0);
            }
            else {
                this.mover(0, 0);
            }
            this.mover(0, this.getDistanciaCorredores());
        }
        interruptorArado();
        interruptorGrades();
        interruptorSubsolador();
    }

    protected void preparaSolo(int posIniX, int posIniY, int posFimX, int posFimY, int velocidade, boolean arado, boolean grades, boolean subsolador){
        if (arado && !getArado()){
            interruptorArado();
        }
        if (grades && !getGrades()){
            interruptorGrades();
        }
        if (subsolador && !getSubsolador()){
            interruptorSubsolador();
        }
        this.mover((posIniX - this.getPosX()), (posIniY - this.getPosY()), velocidade);
        while ((this.getPosX() != posFimX) || (this.getPosY() != posFimY)){
            if (this.getPosX() != posFimX) {
                this.mover((posFimX-posIniX), 0, velocidade);
            }
            else {
                this.mover(posIniX, 0, velocidade);
            }
            this.mover(0, this.getDistanciaCorredores(), velocidade);
            
        }
        interruptorArado();
        interruptorGrades();
        interruptorSubsolador();
    }

    public String toString(){
        String out = "";
        out += "Robo " + getId();
        out += "\n--Posicao: (" + getPosX() + ", " + getPosY() + ", " +getPosZ() +"), direcao: " + getDirecao() + " velocidade máxima: " + getVelocidadeMaxima();
        out += "\n--arado: " + ativaInativa(arado) + " grades: " + ativaInativa(grades) + " subsolador: " + ativaInativa(subsolador);
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
