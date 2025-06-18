package ambientes.obstaculos;
import ambientes.entidade.Entidade;
import ambientes.entidade.TipoEntidade;

public class Obstaculo implements Entidade {
    private int x, y, z;
    private int x1, y1, z1;
    private TipoObstaculo tipo;
    public static int numeroDeObstaculos = 0;

    public Obstaculo(int x, int y, int z, int largura, int comprimento, int altura, TipoObstaculo tipo){
        this.x = x;
        this.y = y;
        this.z = z;
        this.x1 = x + largura;
        this.y1 = y + comprimento;
        this.z1 = z + altura;
        this.tipo = tipo;
        Obstaculo.numeroDeObstaculos++;
    }

    @Override
    public TipoEntidade getTipo() {
        return TipoEntidade.OBSTACULO;
    }

    @Override
    public String getDescricao() {
        return "Obstáculo do tipo " + this.tipo + " na posição (" + this.x + ", " + this.y + ", " + this.z + ")";
    }

    @Override
    public char getRepresentacao() {
        switch(this.tipo) {
            case PAREDE: return 'P';
            case ARVORE: return 'A';
            case PREDIO: return 'E';
            case BURACO: return 'B';
            case TORRE: return 'T';
            case FIACAO: return 'F';
            case DEPOSITO: return 'D';
            default: return 'O';
        }
    }

    public int getX(){
        return this.x;
    }

    public int getX1(){
        return this.x1;
    
    }

    public int getY(){
        return this.y;
    }

    public int getY1(){
        return this.y1;
    
    }

    public int getZ(){
        return this.z;
    }

    public int getZ1(){
        return this.z1;
    
    }

    public boolean bloqueiaPassagem(int posicaoX, int posicaoY, int posicaoZ){
        if (posicaoX >= this.x && posicaoX <= this.x1){
            if (posicaoY >= this.y && posicaoY <= this.y1){
                if (posicaoZ >= this.z && posicaoZ <= this.z1){
                    return true;
                }
                else {
                    return false;
                }
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    public String toString(){
        String out = "";
        out += "Tipo de obstáculo: "+ getTipo() +" posicao inicial: (" + getX() + ", " + getY() + ", " + getZ() +")";
        out += "\n--largura: " +(getX1()-getX()) + " comprimento: " + (getY1()-getY()) + " altura: " + (getZ1()-getZ());
        out += "\n";
        return out;
    }


}