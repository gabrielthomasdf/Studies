import java.util.Scanner;
import ambientes.*;
import ambientes.exception.ColisaoException;
import ambientes.robos.*;

public class Menu {
    private Scanner scanner;
    private Ambiente ambiente;
    private RoboAvancado roboAvancado;

    public Menu(Ambiente ambiente, RoboAvancado roboAvancado) {
        this.scanner = new Scanner(System.in);
        this.ambiente = ambiente;
        this.roboAvancado = roboAvancado;
    }

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("\n=== Menu de Operações ===");
            System.out.println("1. Visualizar ambiente");
            System.out.println("2. Mover robô");
            System.out.println("3. Acionar sensores");
            System.out.println("4. Explorar região");
            System.out.println("5. Executar operação autônoma");
            System.out.println("6. Recarregar robô");
            System.out.println("7. Verificar nível de energia");
            System.out.println("8. Ligar/Desligar robô");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            try {
                switch (opcao) {
                    case 1:
                        ambiente.visualizarAmbiente();
                        break;
                    case 2:
                        moverRobo();
                        break;
                    case 3:
                        roboAvancado.acionarSensores();
                        break;
                    case 4:
                        System.out.print("Digite o raio de exploração: ");
                        int raio = scanner.nextInt();
                        roboAvancado.explorarRegiao(raio);
                        break;
                    case 5:
                        roboAvancado.executarOperacaoAutonoma("Patrulha");
                        break;
                    case 6:
                        roboAvancado.recarregar();
                        break;
                    case 7:
                        System.out.println("Nível de energia: " + roboAvancado.getNivelEnergia() + "%");
                        break;
                    case 8:
                        alternarEstadoRobo();
                        break;
                    case 0:
                        System.out.println("Encerrando o programa...");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } while (opcao != 0);
    }

    private void moverRobo() throws ColisaoException {
        System.out.println("\nPosição atual: (" + roboAvancado.getX() + "," + roboAvancado.getY() + "," + roboAvancado.getZ() + ")");
        System.out.print("Nova posição X: ");
        int x = scanner.nextInt();
        System.out.print("Nova posição Y: ");
        int y = scanner.nextInt();
        System.out.print("Nova posição Z: ");
        int z = scanner.nextInt();
        
        roboAvancado.moverPara(x, y, z);
    }

    private void alternarEstadoRobo() {
        if (roboAvancado.getEstado() == EstadoRobo.DESLIGADO) {
            roboAvancado.ligar();
            System.out.println("Robô ligado!");
        } else {
            roboAvancado.desligar();
            System.out.println("Robô desligado!");
        }
    }
} 