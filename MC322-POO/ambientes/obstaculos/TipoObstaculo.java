package ambientes.obstaculos;
public enum TipoObstaculo{
    PAREDE(3),
    ARVORE(5),
    PREDIO(6),
    BURACO(0),
    TORRE(80),
    FIACAO(120),
    DEPOSITO(10),
    OUTRO(-1);

    private final int alturaPadrao;
    
    TipoObstaculo(int alturaPadrao){
        this.alturaPadrao = alturaPadrao;
    }

    public int getAlturaPadrao(){
        return alturaPadrao;
    }

}