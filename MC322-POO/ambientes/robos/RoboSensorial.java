package ambientes.robos;

import ambientes.sensores.Sensoreavel;
import ambientes.sensores.Sensor;

public abstract class RoboSensorial extends Robo implements Sensoreavel {
    
    protected RoboSensorial(String id, int posicaoX, int posicaoY, String direcao) {
        super(id, posicaoX, posicaoY, direcao);
    }

    protected RoboSensorial(String id, int posicaoX, int posicaoY, String direcao, int limiteNumSensores) {
        super(id, posicaoX, posicaoY, direcao, limiteNumSensores);
    }

    @Override
    public void acionarSensores() throws RoboDesligadoException {
        if (this.estado == EstadoRobo.DESLIGADO) {
            throw new RoboDesligadoException("Robô " + this.id + " está desligado e não pode acionar sensores.");
        }

        if (sensores.isEmpty()) {
            System.out.println("Robô " + this.id + " não possui sensores instalados.");
            return;
        }

        for (Sensor sensor : sensores) {
            try {
                sensor.monitorar();
            } catch (Exception e) {
                System.err.println("Erro ao acionar sensor no robô " + this.id + ": " + e.getMessage());
            }
        }
    }

    /**
     * Verifica se o robô possui um determinado tipo de sensor
     * @param tipoSensor classe do sensor a ser verificado
     * @return true se possui o sensor
     */
    public boolean possuiSensor(Class<? extends Sensor> tipoSensor) {
        return sensores.stream()
            .anyMatch(sensor -> tipoSensor.isInstance(sensor));
    }
} 