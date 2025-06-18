package ambientes.testes;
import ambientes.robos.robosaereos.RoboJato;

public class TesteRoboJato {
    public static void main(String[] args) {
        // Criando um objeto RoboJato
        RoboJato robo = new RoboJato("Jato1", 10, 20, "Norte", 1000, 200);
        
        // Testando os métodos
        robo.exibirPosicao(); // Deve mostrar a posição inicial
        
        robo.acelerar(50); // Deve aumentar a velocidade para 250 km/h
        robo.reduzirVelocidade(100); // Deve reduzir para 150 km/h
        robo.reduzirVelocidade(200); // Deve reduzir para 0 km/h
        
        robo.subir(500); // Testando subida
        robo.descer(600); // Deve mostrar "O robô já está no solo!"
        
        robo.exibirPosicao(); // Deve exibir a posição final e altitude
    }
}
