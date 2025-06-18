package ambientes.testes;
import ambientes.robos.robosaereos.RoboAereo;

public class TesteRoboAereo {
    public static void main(String[] args) {
        RoboAereo robo = new RoboAereo("Drone1", 0, 0, "Norte", 100);
        
        System.out.println("Teste subir 20 metros");
        robo.subir(20);
        System.out.println("Altitude esperada: 20, Altitude atual: " + robo.getAltitude());
        
        System.out.println("Teste descer 10 metros");
        robo.descer(10);
        System.out.println("Altitude esperada: 10, Altitude atual: " + robo.getAltitude());
        
        System.out.println("Teste subir além do limite");
        robo.subir(90);
        System.out.println("Altitude esperada: 100 (limite), Altitude atual: " + robo.getAltitude());
        
        System.out.println("Teste descer abaixo do solo");
        robo.descer(110);
        System.out.println("Altitude esperada: 0, Altitude atual: " + robo.getAltitude());
        
        System.out.println("Teste exibir posição");
        robo.exibirPosicao();
    }
}
