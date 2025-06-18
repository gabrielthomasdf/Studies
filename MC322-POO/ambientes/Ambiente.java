package ambientes;
import java.util.ArrayList;
import ambientes.entidade.*;
import ambientes.exception.ColisaoException;
import ambientes.exception.ForaDosLimitesException;
import ambientes.obstaculos.*;
import ambientes.robos.*;
import ambientes.sensores.*;

public class Ambiente
{
    private String nome;
    private int largura;
    private int profundidade;
    private int altura;
    private TipoEntidade[][][] mapa;
    private ArrayList<Robo> frota;
    private ArrayList<Obstaculo> restricoes;
    private ArrayList<Entidade> entidades = new ArrayList<>();

    //construtor
    
    public Ambiente(String nome, int largura, int profundidade, int altura) {
        this.nome = nome;
        this.largura = largura;
        this.profundidade = profundidade;
        this.altura = altura;
        this.mapa = new TipoEntidade[largura][profundidade][altura];
        this.frota = new ArrayList<>();
        this.restricoes = new ArrayList<>();
        this.inicializaMapa();
    }

    private void inicializaMapa(){
        for (int x = 0; x < largura; x++) {
            for (int y = 0; y < profundidade; y++) {
                for (int z = 0; z < altura; z++) {
                    mapa[x][y][z] = TipoEntidade.VAZIO;
                }
            }
        }
        for (int i = 0; i < entidades.size(); i++){
            mapa[entidades.get(i).getX()][entidades.get(i).getY()][entidades.get(i).getZ()] = entidades.get(i).getTipo();
        }
    }

    //metodos

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getNome(){
        return this.nome;
    }

    public int getLargura(){
        return this.largura;
    }

    public int getProfundidade(){
        return this.profundidade;
    }

    public int getAltura(){
        return this.altura;
    }

    public void setAltura(int altura){
        this.altura = altura;
    }

    public ArrayList<Robo> getFrota(){
        return this.frota;
    }

    public ArrayList<Obstaculo> getRestricoes(){
        return this.restricoes;
    }

    private boolean dentroDosLimites(int x, int y, int z) throws ForaDosLimitesException {
        if (x < 0 || x >= largura || y < 0 || y >= profundidade || z < 0 || z >= altura) {
            throw new ForaDosLimitesException("Coordenadas (" + x + "," + y + "," + z + ") fora dos limites do ambiente");
        }
        return true;
    }

    private boolean estaOcupado(int x, int y, int z) throws ColisaoException {
        try {
            if (!dentroDosLimites(x, y, z)) {
                throw new ColisaoException("Posição fora dos limites");
            }
            return mapa[x][y][z] != TipoEntidade.VAZIO;
        } catch (ForaDosLimitesException e) {
            throw new ColisaoException(e.getMessage());
        }
    }

    public void moverEntidade(Entidade e, int novoX, int novoY, int novoZ) throws ColisaoException {
        try {
            if (!dentroDosLimites(novoX, novoY, novoZ)) {
                throw new ColisaoException("Nova posição fora dos limites");
            }
            // Verifica se há colisão
            if (mapa[novoX][novoY][novoZ] != TipoEntidade.VAZIO) {
                throw new ColisaoException("Colisão detectada na posição (" + novoX + "," + novoY + "," + novoZ + ")");
            }
            // Remove da posição atual
            mapa[e.getX()][e.getY()][e.getZ()] = TipoEntidade.VAZIO;
            // Move para nova posição
            if (e instanceof Robo) {
                ((Robo) e).mover(novoX, novoY, novoZ);
            }
            mapa[novoX][novoY][novoZ] = e.getTipo();
        } catch (ForaDosLimitesException ex) {
            throw new ColisaoException(ex.getMessage());
        }
    }

    public void executarSensores() {
        for (Robo robo : frota) {
            for (Sensor sensor : robo.getSensores()) {
                sensor.monitorar();
            }
        }
    }

    public void verificarColisoes() throws ColisaoException {
        for (Entidade e1 : entidades) {
            for (Entidade e2 : entidades) {
                if (e1 != e2 && 
                    e1.getX() == e2.getX() && 
                    e1.getY() == e2.getY() && 
                    e1.getZ() == e2.getZ()) {
                    throw new ColisaoException(
                        "Colisão detectada entre " + e1.getDescricao() + " e " + e2.getDescricao()
                    );
                }
            }
        }
    }

    public void visualizarAmbiente() {
        System.out.println("Ambiente " + nome + " (" + largura + "x" + profundidade + ")");
        System.out.println("Legenda: [.] Vazio, [R] Robô, [O] Obstáculo");
        
        for (int y = profundidade - 1; y >= 0; y--) {
            for (int x = 0; x < largura; x++) {
                boolean posicaoVazia = true;
                // Procura entidade em qualquer altura nesta posição x,y
                for (int z = 0; z < altura; z++) {
                    if (mapa[x][y][z] != TipoEntidade.VAZIO) {
                        // Encontra a entidade correspondente para pegar sua representação
                        for (Entidade e : entidades) {
                            if (e.getX() == x && e.getY() == y && e.getZ() == z) {
                                System.out.print("[" + e.getRepresentacao() + "]");
                                posicaoVazia = false;
                                break;
                            }
                        }
                        break;
                    }
                }
                if (posicaoVazia) {
                    System.out.print("[.]");
                }
            }
            System.out.println();
        }
    }

    public void adicionarRobo(Robo robo){
        frota.add(robo);
        robo.setAmbiente(this);
        adicionarEntidade(robo);
    }

    public void removerRobo(Robo robo){
        removerEntidade(robo);
        frota.remove(robo);
    }

    public void removerRobo(String robo){
        for (int i = 0; i < frota.size(); i++) {
            if (frota.get(i).getId().equals(robo)) {
                System.out.println("Robo " + frota.get(i).getId() + " removido.");
                removerEntidade(frota.get(i));
                frota.remove(i);
                break;
            }
        }
    }

    public void adicionarObstaculo(int x, int y, int z, int largura, int profundidade, int altura, TipoObstaculo tipo) {
        try {
            if (!dentroDosLimites(x, y, z)) {
                System.out.println("Posição do obstáculo fora dos limites do ambiente");
                return;
            }
            Obstaculo obstaculo = new Obstaculo(x, y, z, largura, profundidade, altura, tipo);
            restricoes.add(obstaculo);
            adicionarEntidade(obstaculo);
        } catch (ForaDosLimitesException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removerObstaculo(Obstaculo obstaculo){
        removerEntidade(obstaculo);
        restricoes.remove(obstaculo);
    }

    public void removerObstaculo(int obstaculo){
        if (restricoes.size() > obstaculo && obstaculo > -1){
            removerEntidade(restricoes.get(obstaculo));
            restricoes.remove(obstaculo);
        }
        else{
            System.out.println("Não há esse obstáculo no ambiente.");
        }
    }

    public void adicionarEntidade(Entidade e) {
        try {
            if (!dentroDosLimites(e.getX(), e.getY(), e.getZ())) {
                System.out.println("Posição da entidade fora dos limites do ambiente");
                return;
            }
            if (e.getTipo() == TipoEntidade.OBSTACULO){
                arrumaMapaObstaculo(e, e.getTipo());
            }
            else{
                mapa[e.getX()][e.getY()][e.getZ()] = e.getTipo();
            }
        } catch (ForaDosLimitesException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void removerEntidade(Entidade entidade) {
        try {
            if (!dentroDosLimites(entidade.getX(), entidade.getY(), entidade.getZ())) {
                System.out.println("Posição da entidade fora dos limites do ambiente");
                return;
            }
            if (entidade.getTipo() == TipoEntidade.VAZIO){
                arrumaMapaObstaculo(entidade, TipoEntidade.VAZIO);
            }
            else{
                mapa[entidade.getX()][entidade.getY()][entidade.getZ()] = TipoEntidade.VAZIO;
            }
            entidades.remove(entidade);
        } catch (ForaDosLimitesException e) {
            System.out.println(e.getMessage());
        }
    }

    private void arrumaMapaObstaculo(Entidade e, TipoEntidade tipo){
        for (int i = 0; i < restricoes.size(); i++){
                if (e.getX() == restricoes.get(i).getX() && e.getY() == restricoes.get(i).getY() && e.getZ() == restricoes.get(i).getZ()){
                    for (int a = restricoes.get(i).getX(); a < restricoes.get(i).getX1(); a++){
                        for (int b = restricoes.get(i).getY(); b < restricoes.get(i).getY1(); b++){
                            for (int c = restricoes.get(i).getZ(); a < restricoes.get(i).getZ1(); c++){
                                this.mapa[a][b][c] = tipo;
                            }
                        }
                    }
                }
                break;
            }
    }

    public void removerEntidade(int entidade) {
        try {
            if (entidades.size() > entidade && entidade > -1) {
                if (!dentroDosLimites(entidades.get(entidade).getX(), entidades.get(entidade).getY(), entidades.get(entidade).getZ())) {
                    System.out.println("Posição da entidade fora dos limites do ambiente");
                    return;
                }
                if (entidades.get(entidade).getTipo() == TipoEntidade.VAZIO){
                    arrumaMapaObstaculo(entidades.get(entidade), TipoEntidade.VAZIO);
                }
                else{
                    mapa[entidades.get(entidade).getX()][entidades.get(entidade).getY()][entidades.get(entidade).getZ()] = TipoEntidade.VAZIO;
                }
                restricoes.remove(entidade);
            }
        } catch (ForaDosLimitesException e) {
            System.out.println(e.getMessage());
        }
    }

    public String toString(){
        String out = "";
        out += "Ambiente " + getNome();
        out += "\nprofundidade: " + getProfundidade() + " Largura: " + getLargura() + " Altura: " + getAltura();
        out += "\nLista de Robôs presentes no ambiente --------------------------------------------\n";
        for (int i = 0; i < frota.size(); i++){
            out += "-"+frota.get(i) + "\n";
        }
        out += "Lista de Obstáculos presentes no ambiente -----------------------------------------\n";
        for (int i = 0; i < restricoes.size(); i++){
            out += "-"+restricoes.get(i) + "\n";
        }
        return out;
    }

    // Método de exemplo para verificar tipo de entidade em uma posição
    public boolean isRoboNaPosicao(int x, int y, int z) {
        try {
            if (!dentroDosLimites(x, y, z)) {
                return false;
            }
            Entidade e = entidades.stream()
                .filter(ent -> ent.getX() == x && ent.getY() == y && ent.getZ() == z)
                .findFirst()
                .orElse(null);
            return e != null && e.getTipo() == TipoEntidade.ROBO;
        } catch (ForaDosLimitesException e) {
            return false;
        }
    }
}
