package ambientes.robos.interfaces;

import ambientes.exception.AcaoNaoPermitidaException;

public interface Atacante {
    /**
     * Executa um ataque nas coordenadas especificadas
     * @param alvoX coordenada X do alvo
     * @param alvoY coordenada Y do alvo
     * @param alvoZ coordenada Z do alvo
     * @param intensidade intensidade do ataque
     * @return true se o ataque foi bem sucedido
     * @throws AcaoNaoPermitidaException se o ataque não for permitido
     */
    boolean atacar(int alvoX, int alvoY, int alvoZ, int intensidade) 
        throws AcaoNaoPermitidaException;
} 