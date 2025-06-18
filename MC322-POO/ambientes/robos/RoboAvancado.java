package ambientes.robos;

import java.util.HashMap;
import java.util.Map;

import ambientes.entidade.TipoEntidade;
import ambientes.exception.AcaoNaoPermitidaException;
import ambientes.exception.Comunicavel;
import ambientes.exception.ErroComunicacaoException;
import ambientes.exception.RecargaNecessariaException;
import ambientes.exception.RoboDesligadoException;
import ambientes.robos.interfaces.Autonomo;
import ambientes.robos.interfaces.Explorador;
import ambientes.robos.interfaces.Atacante;
import ambientes.sensores.Sensoreavel;

public class RoboAvancado extends RoboSensorial implements Autonomo, Explorador, Atacante {
    private int nivelEnergia;
    private int nivelAgressividade;
    private boolean modoAutonomo;

    public RoboAvancado(String id, int posicaoX, int posicaoY, String direcao) {
        super(id, posicaoX, posicaoY, direcao);
        this.nivelEnergia = 100;
        this.nivelAgressividade = 50;
        this.modoAutonomo = false;
    }

    @Override
    public boolean executarOperacaoAutonoma(String objetivo) {
        if (nivelEnergia < 20) {
            System.out.println("Energia insuficiente para operação autônoma.");
            return false;
        }
        
        this.modoAutonomo = true;
        System.out.println("Iniciando operação autônoma: " + objetivo);
        // Implementação da lógica de operação autônoma
        nivelEnergia -= 10;
        return true;
    }

    @Override
    public Map<String, TipoEntidade> explorarRegiao(int raioExploracao) {
        if (nivelEnergia < 30) {
            System.out.println("Energia insuficiente para exploração.");
            return new HashMap<>();
        }

        Map<String, TipoEntidade> mapaRegiao = new HashMap<>();
        // Implementação da lógica de exploração
        for (int x = getX() - raioExploracao; x <= getX() + raioExploracao; x++) {
            for (int y = getY() - raioExploracao; y <= getY() + raioExploracao; y++) {
                for (int z = getZ() - raioExploracao; z <= getZ() + raioExploracao; z++) {
                    String coordenada = x + "," + y + "," + z;
                    // Aqui você implementaria a lógica real de detecção
                    mapaRegiao.put(coordenada, TipoEntidade.VAZIO);
                }
            }
        }
        nivelEnergia -= 20;
        return mapaRegiao;
    }

    @Override
    public boolean atacar(int alvoX, int alvoY, int alvoZ, int intensidade) throws AcaoNaoPermitidaException {
        if (nivelEnergia < 50) {
            throw new AcaoNaoPermitidaException("Energia insuficiente para ataque");
        }
        if (intensidade > nivelAgressividade) {
            throw new AcaoNaoPermitidaException("Intensidade de ataque maior que nível de agressividade permitido");
        }

        System.out.println("Executando ataque nas coordenadas (" + alvoX + "," + alvoY + "," + alvoZ + 
                          ") com intensidade " + intensidade);
        nivelEnergia -= 30;
        return true;
    }

    @Override
    public void executarTarefa() {
        if (nivelEnergia < 10) {
            try {
                throw new RecargaNecessariaException("Energia muito baixa para executar tarefas");
            } catch (RecargaNecessariaException e) {
                System.err.println("Erro: " + e.getMessage());
                return;
            }
        }

        System.out.println("Executando tarefa específica do RoboAvancado");
        nivelEnergia -= 5;
    }

    public void recarregar() {
        this.nivelEnergia = 100;
        System.out.println("Robô " + getId() + " recarregado completamente.");
    }

    public int getNivelEnergia() {
        return this.nivelEnergia;
    }

    public void setNivelAgressividade(int nivel) {
        this.nivelAgressividade = Math.max(0, Math.min(100, nivel));
    }

    public int getNivelAgressividade() {
        return this.nivelAgressividade;
    }

    @Override
    public String toString() {
        return super.toString() + "\nNível de Energia: " + nivelEnergia + 
               "\nNível de Agressividade: " + nivelAgressividade +
               "\nModo Autônomo: " + (modoAutonomo ? "Ativado" : "Desativado");
    }
} 