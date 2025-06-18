package ambientes.robos.interfaces;

@FunctionalInterface
public interface Autonomo {
    /**
     * Executa uma operação autônoma sem supervisão humana
     * @param objetivo descrição do objetivo a ser alcançado
     * @return true se o objetivo foi alcançado com sucesso
     */
    boolean executarOperacaoAutonoma(String objetivo);
} 