package ambientes.sensores;

import ambientes.robos.RoboDesligadoException;

public interface Sensoreavel {
    /**
     * Aciona todos os sensores do robô
     * @throws RoboDesligadoException se o robô estiver desligado
     */
    void acionarSensores() throws RoboDesligadoException;
} 