package ambientes.sensores;
public class SensorTemperatura extends Sensor {
    public SensorTemperatura(double alcance) {
        super(alcance);
    }

    @Override
    public void monitorar() {
        System.out.println("Monitorando temperatura... Temperatura: 24°C");
    }
}