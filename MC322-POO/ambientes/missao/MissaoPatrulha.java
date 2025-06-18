package ambientes.missao;
import ambientes.missao.*;
import ambientes.Ambiente;
import ambientes.robos.*;
import java.util.ArrayList;
import java.util.List;


public class MissaoPatrulha implements Missao {
    final public TipoMissao tipoMissao = TipoMissao.PATRULHA;
    private ArrayList<List<Integer>> positions = new ArrayList<>();
    private int estaAtivo = 0;

    public void executar(RoboSensorial r, Ambiente a){
        while (estaAtivo == 0){
            for (int aux = 0; aux < positions.size(); aux++){
            r.moverPara(positions.get(aux).get(0), positions.get(aux).get(1), positions.get(aux).get(2));
            r.acionarSensores();
            }
        }
    }
    
}
