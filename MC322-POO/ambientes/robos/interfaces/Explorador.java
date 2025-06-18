package ambientes.robos.interfaces;

import java.util.Map;
import ambientes.entidade.TipoEntidade;

@FunctionalInterface
public interface Explorador {
    /**
     * Explora uma região do ambiente e retorna um mapa das entidades encontradas
     * @param raioExploracao raio máximo de exploração a partir da posição atual
     * @return mapa com as coordenadas e tipos de entidades encontradas
     */
    Map<String, TipoEntidade> explorarRegiao(int raioExploracao);
} 