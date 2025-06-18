package ambientes.sensores;
import ambientes.robos.Robo;

public abstract class Sensor{
    protected double alcance;
    protected Robo robo;
    

    Sensor(double alcance){
        this.alcance = alcance;
    }

    double getAlcance(){
        return this.alcance;
    }

    public void setRobo(Robo robo){
        this.robo = robo;
    }

    public Robo getRobo(){
        return this.robo;
    }

    public abstract void monitorar();

    public String toString(){
        String out = "";
        out += "---"+super.toString();
        out += " alcance: "+this.getAlcance();
        return out;
    }
}