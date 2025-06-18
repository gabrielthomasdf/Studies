package ambientes.robos;
import java.util.ArrayList;
import ambientes.Ambiente;
import ambientes.sensores.Sensor;
import ambientes.entidade.Entidade;
import ambientes.entidade.TipoEntidade;
import ambientes.exception.ColisaoException;
import ambientes.exception.Comunicavel;
import ambientes.exception.ErroComunicacaoException;
import ambientes.exception.RoboDesligadoException;

public abstract class Robo implements Entidade, Comunicavel {
    protected String id;  // Identificador único
    protected EstadoRobo estado;
    protected int posicaoX;
    protected int posicaoY;
    protected int posicaoZ = 0;
    protected String direcao;
    protected int limiteNumSensores;
    private Ambiente ambiente;
    public static int numeroDeRobos = 0;
    public ArrayList<Sensor> sensores = new ArrayList<>();

    //declarando construtores ------------------------------------------------------
    public Robo(String id, int posicaoX, int posicaoY, String direcao) {
        this.id = id;
        this.posicaoX = posicaoX;
        this.posicaoY = posicaoY;
        this.posicaoZ = 0;
        this.direcao = direcao;
        this.estado = EstadoRobo.DESLIGADO;
        Robo.numeroDeRobos++;
    }

    protected Robo(String id, int posicaoX, int posicaoY, String direcao, int limiteNumSensores){
        this.id = id;
        this.posicaoX = posicaoX;
        this.posicaoY = posicaoY;
        this.direcao = direcao;
        this.limiteNumSensores = limiteNumSensores;
        this.estado = EstadoRobo.DESLIGADO;
        Robo.numeroDeRobos++;
    }

    protected Robo(){
        this.id = "Robo" + String.valueOf(Robo.numeroDeRobos+1);
        this.posicaoX = 0;
        this.posicaoY = 0;
        this.direcao = "Não definida";
        this.estado = EstadoRobo.DESLIGADO;
        Robo.numeroDeRobos++;
    }

    // Métodos da interface Entidade
    @Override
    public int getX() {
        return this.posicaoX;
    }

    @Override
    public int getY() {
        return this.posicaoY;
    }

    @Override
    public int getZ() {
        return this.posicaoZ;
    }

    @Override
    public TipoEntidade getTipo() {
        return TipoEntidade.ROBO;
    }

    @Override
    public String getDescricao() {
        return "Robô " + this.id + " na posição (" + this.posicaoX + ", " + this.posicaoY + ", " + this.posicaoZ + ")";
    }

    @Override
    public char getRepresentacao() {
        return 'R';
    }

    // Novos métodos requeridos
    public void moverPara(int x, int y, int z) throws ColisaoException {
        if (estado == EstadoRobo.DESLIGADO) {
            throw new IllegalStateException("Robô desligado não pode se mover");
        }
        
        estado = EstadoRobo.EM_MOVIMENTO;
        try {
            ambiente.moverEntidade(this, x, y, z);
            this.posicaoX = x;
            this.posicaoY = y;
            this.posicaoZ = z;
        } finally {
            estado = EstadoRobo.LIGADO;
        }
    }

    public void ligar() {
        this.estado = EstadoRobo.LIGADO;
        System.out.println("Robô " + this.id + " ligado.");
    }

    public void desligar() {
        this.estado = EstadoRobo.DESLIGADO;
        System.out.println("Robô " + this.id + " desligado.");
    }

    public EstadoRobo getEstado() {
        return estado;
    }

    // Método abstrato para ações específicas
    public abstract void executarTarefa();

    // Métodos existentes mantidos
    public String getId() {
        return this.id;
    }

    public String getDirecao() {
        return this.direcao;
    }

    public void setDirecao(String direcao) {
        this.direcao = direcao;
    }

    public void setLimiteNumSensores(int limite) {
        this.limiteNumSensores = limite;
    }

    protected int getLimiteNumSensores() {
        return this.limiteNumSensores;
    }

    protected boolean getSensoresCheio() {
        return this.limiteNumSensores == sensores.size();
    }

    public void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;
    }

    public Ambiente getAmbiente() {
        return this.ambiente;
    }

    public void adicionarSensor(Sensor sensor) {
        if (sensores.size() < limiteNumSensores) {
            sensores.add(sensor);
            sensor.setRobo(this);
        }
    }

    protected void removerSensor(Sensor sensor) {
        sensores.remove(sensor);
    }

    public ArrayList<Sensor> getSensores() {
        return this.sensores;
    }

    @Override
    public String toString() {
        return "Robô " + this.id + " em (" + this.posicaoX + "," + this.posicaoY + "," + this.posicaoZ + 
               ") direção " + this.direcao + " estado " + this.estado;
    }

    public void exibirPosicao() {
        System.out.println(this.id + " está em: (" + this.posicaoX + ", " + this.posicaoY + ", " + this.posicaoZ + ")");
    }

    public int getPosX() {
        return this.posicaoX;
    }

    public int getPosY() {
        return this.posicaoY;
    }

    public int getPosZ() {
        return this.posicaoZ;
    }

    public void mover(int x, int y, int z) {
        this.posicaoX = x;
        this.posicaoY = y;
        this.posicaoZ = z;
    }

    public String ativaInativa(boolean estado) {
        return estado ? "ativo" : "inativo";
    }

    @Override
    public void enviarMensagem(Comunicavel destinatario, String mensagem) {
        try {
            if (this.estado == EstadoRobo.DESLIGADO) {
                throw new RoboDesligadoException("Robô " + this.id + " está desligado e não pode enviar mensagens");
            }
            System.out.println("Robô " + this.id + " enviando mensagem para " + destinatario + ": " + mensagem);
            destinatario.receberMensagem(mensagem);
        } catch (RoboDesligadoException e) {
            System.err.println("Erro: " + e.getMessage());
        }
         catch (ErroComunicacaoException e){
            System.err.println("Erro: " + e.getMessage());
         }
    }

    @Override
    public void receberMensagem(String mensagem) {
        try {
            if (this.estado == EstadoRobo.DESLIGADO) {
                throw new RoboDesligadoException("Robô " + this.id + " está desligado e não pode receber mensagens");
            }
            System.out.println("Robô " + this.id + " recebeu mensagem: " + mensagem);
        } catch (RoboDesligadoException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}