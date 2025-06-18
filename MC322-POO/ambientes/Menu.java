package ambientes;

import ambientes.robos.Robo;
import ambientes.robos.EstadoRobo;
import java.util.Scanner;

public class Menu {
    private Ambiente ambiente;
    private Robo roboAtual;
    private Scanner scanner;

    public Menu(Ambiente ambiente, Robo roboInicial) {
        this.ambiente = ambiente;
        this.roboAtual = roboInicial;
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n=== Menu Principal ===");
            System.out.println("1. Visualizar ambiente");
            System.out.println("2. Listar robôs");
            System.out.println("3. Selecionar robô");
            System.out.println("4. Mover robô atual");
            System.out.println("5. Ligar/Desligar robô atual");
            System.out.println("0. Sair");
            
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer
            
            switch (opcao) {
                case 1:
                    ambiente.visualizarAmbiente();
                    break;
                case 2:
                    listarRobos();
                    break;
                case 3:
                    selecionarRobo();
                    break;
                case 4:
                    moverRoboAtual();
                    break;
                case 5:
                    alternarEstadoRobo();
                    break;
                case 0:
                    continuar = false;
                    System.out.println("Encerrando o programa...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void listarRobos() {
        System.out.println("\nRobôs no ambiente:");
        for (Robo robo : ambiente.getFrota()) {
            System.out.println("- " + robo.getId());
        }
    }

    private void selecionarRobo() {
        System.out.print("Digite o ID do robô: ");
        String id = scanner.nextLine();
        
        for (Robo robo : ambiente.getFrota()) {
            if (robo.getId().equals(id)) {
                roboAtual = robo;
                System.out.println("Robô " + id + " selecionado.");
                return;
            }
        }
        System.out.println("Robô não encontrado!");
    }

    private void moverRoboAtual() {
        if (roboAtual == null) {
            System.out.println("Nenhum robô selecionado!");
            return;
        }

        try {
            System.out.println("Digite as coordenadas (x y z): ");
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            int z = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            roboAtual.moverPara(x, y, z);
            System.out.println("Robô movido com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao mover robô: " + e.getMessage());
        }
    }

    private void alternarEstadoRobo() {
        if (roboAtual == null) {
            System.out.println("Nenhum robô selecionado!");
            return;
        }

        if (roboAtual.getEstado() == EstadoRobo.DESLIGADO) {
            roboAtual.ligar();
            System.out.println("Robô ligado!");
        } else {
            roboAtual.desligar();
            System.out.println("Robô desligado!");
        }
    }
} 