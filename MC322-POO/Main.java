import java.util.Scanner;
import ambientes.Ambiente;
import ambientes.obstaculos.TipoObstaculo;
import ambientes.robos.Robo;
import ambientes.robos.robosaereos.RoboAereo;
import ambientes.robos.robosaereos.RoboJato;
import ambientes.robos.robosaereos.RoboDrone;
import ambientes.robos.robosterrestres.RoboColheitadera;
import ambientes.robos.robosterrestres.RoboPreparaSolo;
import ambientes.robos.robosterrestres.RoboTerrestre;
import ambientes.sensores.Sensor;
import ambientes.sensores.SensorProximidade;
import java.util.ArrayList;
import ambientes.robos.RoboAvancado;
import ambientes.Menu;
import ambientes.entidade.Entidade;
import ambientes.exception.ColisaoException;

public class Main {

    //Métodos para o menu interativo ---------------------------------------------------
    public static void menuAmbiente(ArrayList<Ambiente> ecossistema){
        Scanner input = new Scanner(System.in);
        System.out.println("Bem vindo(a) ao simulador de robôs");
        for (int i = 0; i < ecossistema.size(); i++){
            System.out.printf("%s  (%d)     ", ecossistema.get(i).getNome(), (i+1));
        }
        System.out.println("Selecione o ambiente escolhido  \nFechar o menu -> Digite 0");
        int escolhaInt = -12;
        try {
            escolhaInt = input.nextInt();
            System.out.println(escolhaInt);
            input.nextLine();
        }
        catch (NumberFormatException e) {
            System.err.println("Formato do número errado ou um não número foi usado, tente novamente!");
            menuAmbiente(ecossistema);
        }
        if (escolhaInt > 0 && escolhaInt < ecossistema.size()){
            menuOpcoesAmbiente((escolhaInt-1), ecossistema);
        }
        else if (escolhaInt == 0){
            System.out.println("Obrigado por usar nosso simulador!");;
        }
        else if (escolhaInt < 0 && escolhaInt > ecossistema.size()){
            System.out.println("Valor de ambiente não disponível, tente novamente!");
            menuAmbiente(ecossistema);
        }
        input.close();
    }

    public static void menuOpcoesAmbiente(int opcao, ArrayList<Ambiente> ecossistema){
        Scanner input = new Scanner(System.in);
        System.out.println("Opções: \nInformações do Ambiente -> Digite 1");
        System.out.println("Informações dos robôs -> Digite 2");
        System.out.println("Informações dos obstaculos -> Digite 3");
        System.out.println("Opções a cerca de robôs -> Digite 4");
        //System.out.println("Opções a cerca de obstáculos -> Digite 5\n");
        //System.out.println("Adicionar robos ao ambiente -> Digite 6");
        System.out.println("Remover robos do ambiente -> Digite 5");
        //System.out.println("Adicionar obstáculo ao ambiente -> Digite 8");
        System.out.println("Remover obstáculo do ambiente -> Digite 6");
        System.out.println("Voltar para menu anterior -> Digite -1");
        System.out.println("Fechar o menu -> Digite 0");
        int escolhaOpcao = input.nextInt();
        input.nextLine();
        switch (escolhaOpcao) {
            case 1:
                System.out.println(ecossistema.get(opcao));
                menuOpcoesAmbiente(opcao, ecossistema);
                break;
            case 2:
                for (Robo robo : ecossistema.get(opcao).getFrota()){
                    System.out.println(robo);
                }
                menuOpcoesAmbiente(opcao, ecossistema);
                break;
            case 3:
                for (int i = 0; i < ecossistema.get(opcao).getRestricoes().size(); i++){
                    System.out.printf("Obstaculo %d ", (i+1));
                    System.out.println(ecossistema.get(opcao).getRestricoes().get(i));
                }
                menuOpcoesAmbiente(opcao, ecossistema);
                break;
            case 4:
                menuOpcoesRobos(ecossistema.get(opcao), ecossistema);
                break;
            /*case "5":
                menuOpcoesObstaculos();*/
            /*case"6":
                System.out.println("Digite o ");
                ecossistema.get(opcao).adicionarRobo(null);
                break;*/
            case 5:
                System.out.println("Digite o nome do robo a ser removido");
                for (Robo robo : ecossistema.get(opcao).getFrota()){
                    System.out.println(robo.getId() + "  ");
                }
                String roboRemover = input.nextLine();
                ecossistema.get(opcao).removerRobo(roboRemover);
                menuOpcoesAmbiente(opcao, ecossistema);
                break;
            //case"8":
                
            case 6:
                System.out.println("Digite o número do obstáculo a ser removido");
                for (int i = 0; i < ecossistema.get(opcao).getRestricoes().size(); i++){
                    System.out.println("("+(i+1)+")"+ecossistema.get(opcao).getRestricoes().get(i));
                }
                int obstaculoRemover = (Integer.parseInt(input.nextLine())-1);
                ecossistema.get(opcao).removerObstaculo(obstaculoRemover);
                menuOpcoesAmbiente(opcao, ecossistema);
                break;
            case -1:
                menuAmbiente(ecossistema);
                break;
            case 0:
                System.out.println("Obrigado por usar nosso simulador!");
                break;
            default:
                System.out.println("Valor não aceito, tente novamente.");
                menuOpcoesAmbiente(opcao, ecossistema);
                break;
        }
        input.close();
    }

    public static void menuOpcoesRobos(Ambiente ambiente, ArrayList<Ambiente> ecossistema){
        Scanner input = new Scanner(System.in);
        System.out.println("Informações dos robôs -> Digite 1");
        System.out.println("Selecionar Robo -> Digite 2");
        System.out.println("Voltar para menu anterior -> Digite -1");
        System.out.println("Fechar o menu -> Digite 0\n");
        int escolhaRobo = input.nextInt();
        input.nextLine();
        switch (escolhaRobo) {
            case 1:
                for (Robo robo : ambiente.getFrota()){
                    System.out.println(robo);
                }
                menuOpcoesRobos(ambiente, ecossistema);
                break;
            case 2:
                System.out.println("Digite o nome do robo a ser selecionado");
                for (Robo robo : ambiente.getFrota()){
                    System.out.println(robo.getId() + "  ");
                }
                String selecionaRobo = input.nextLine();
                int verifica = 0;
                for (int i = 0; i < ambiente.getFrota().size(); i++){
                    if (ambiente.getFrota().get(i).getId().equals(selecionaRobo)){
                        menuRobo(ambiente.getFrota().get(i), ambiente, ecossistema);
                        verifica = 1;
                    }
                }
                if (verifica == 0){
                    System.out.println("Robo não encontrado, tente novamente.");
                    menuOpcoesRobos(ambiente, ecossistema);
                }
                break;
            case -1:
                for (int i = 0; i < ecossistema.size(); i++){
                    if (ambiente == ecossistema.get(i)){
                        menuOpcoesAmbiente(i, ecossistema);
                        break;
                    }
                }
                break;
            case 0:
                System.out.println("Obrigado por usar nosso simulador!");
                break;
            default:
                System.out.println("Valor não aceito, tente novamente.");
                menuOpcoesRobos(ambiente, ecossistema);
                break;
        }
        input.close();
    }

    public static void menuRobo(Robo robo, Ambiente ambiente, ArrayList<Ambiente> ecossistema){
        Scanner input = new Scanner(System.in);
        robo.exibirPosicao();
        System.out.println("Para mover o robo -> Digite 1");
        System.out.println("Listar sensores -> Digite 2");
        System.out.println("Ativar/Desativar sensor -> Digite 3");
        System.out.println("Voltar para o menu anterior -> Digite -1");
        System.out.println("Fechar o menu -> Digite 0");
        int usaRobo = input.nextInt();
        input.nextLine();
        switch (usaRobo) {
            case 1:
                System.out.println("Digite para que posição você quer que o robô se mova no formato X Y Z");
                String moveRobo = input.nextLine();
                String[] posicoes = moveRobo.split("\\s+");
                robo.mover(Integer.parseInt(posicoes[0]), Integer.parseInt(posicoes[1]), Integer.parseInt(posicoes[2]));
                menuRobo(robo, ambiente, ecossistema);
                break;
            case 2:
                for (int i = 0; i < robo.sensores.size(); i++){
                    System.out.println(robo.sensores.get(i)+"  ");
                }
                menuRobo(robo, ambiente, ecossistema);
                break;
            case 3:
                for (int i = 0; i < robo.sensores.size(); i++){
                    System.out.println(robo.sensores.get(i)+" ("+(i+1)+")  ");
                }
                System.out.println("Digite o número do sensor correspondente");
                int selecionaSensor = input.nextInt();
                input.nextLine();
                if (selecionaSensor <= robo.getSensores().size()){
                    robo.sensores.get(selecionaSensor-1).monitorar();
                }
                else {
                    System.out.println("Não há o sensor selecionado. Tente novamente.");
                }
                menuRobo(robo, ambiente, ecossistema);
                break;
            case -1:
                menuOpcoesRobos(ambiente, ecossistema);
                break;
            case 0:
                System.out.println("Obrigado por usar nosso simulador!");
                break;
            default:
                System.out.println("Valor não aceito, tente novamente.");
                menuRobo(robo, ambiente, ecossistema);
                break;
        }
        input.close();
    }

    public static void main(String[] args) {
        System.out.println("=== Iniciando Testes do Sistema de Robôs ===\n");

        // 1. Criação e configuração do ambiente
        System.out.println("1. Teste de Criação de Ambiente");
        Ambiente ambiente = new Ambiente("Ambiente de Testes", 10, 10, 5);
        System.out.println("Ambiente criado com dimensões: " + ambiente.getLargura() + "x" + 
                          ambiente.getProfundidade() + "x" + ambiente.getAltura());

        // 2. Criação de diferentes tipos de robôs
        System.out.println("\n2. Teste de Criação de Robôs");
        RoboAvancado roboAvancado = new RoboAvancado("RA1", 0, 0, "Norte");
        RoboTerrestre roboTerrestre = new RoboTerrestre("RT1", 2, 2, "Sul", 30);
        RoboAereo roboAereo = new RoboAereo("RAE1", 3, 3, "Leste", 100);
        RoboColheitadera colheitadera = new RoboColheitadera("RC1", 4, 4, "Oeste", 20, false);
        RoboDrone drone = new RoboDrone("RD1", 5, 5, "Norte", 50);

        // 3. Adição de robôs ao ambiente
        System.out.println("\n3. Teste de Adição de Robôs ao Ambiente");
        ambiente.adicionarRobo(roboAvancado);
        ambiente.adicionarRobo(roboTerrestre);
        ambiente.adicionarRobo(roboAereo);
        ambiente.adicionarRobo(colheitadera);
        ambiente.adicionarRobo(drone);
        ambiente.adicionarEntidade(roboAvancado);
        ambiente.adicionarEntidade(roboTerrestre);
        ambiente.adicionarEntidade(roboAereo);
        ambiente.adicionarEntidade(colheitadera);
        ambiente.adicionarEntidade(drone);
        System.out.println("Número de robôs no ambiente: " + ambiente.getFrota().size());

        // 4. Teste de movimentação
        System.out.println("\n4. Teste de Movimentação");
        try {
            System.out.println("4.1 Teste de movimentação normal");
            roboAvancado.ligar();
            roboAvancado.moverPara(1, 1, 0);
            System.out.println("Robô Avançado movido para (1,1,0)");

            System.out.println("\n4.2 Teste de movimentação com robô desligado");
            roboTerrestre.moverPara(3, 3, 0);
            System.out.println("Erro: O robô deveria ter lançado uma exceção");
        } catch (IllegalStateException e) {
            System.out.println("Sucesso: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }

        // 5. Teste de colisão
        System.out.println("\n5. Teste de Colisão");
        try {
            roboTerrestre.ligar();
            roboTerrestre.moverPara(1, 1, 0); // Tentando mover para onde está o roboAvancado
            System.out.println("Erro: Deveria ter detectado colisão");
        } catch (ColisaoException e) {
            System.out.println("Sucesso: Colisão detectada corretamente");
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }

        // 6. Teste de robôs aéreos
        System.out.println("\n6. Teste de Robôs Aéreos");
        try {
            roboAereo.ligar();
            roboAereo.subir(30);
            System.out.println("RoboAereo subiu para altitude: " + roboAereo.getAltitude());
            
            drone.ligar();
            drone.subir(20);
            drone.ativarModoPairar();
            System.out.println("Drone está pairando: " + drone.isPairando());
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }

        // 7. Teste de robôs especializados
        System.out.println("\n7. Teste de Robôs Especializados");
        try {
            roboAvancado.ligar();
            roboAvancado.explorarRegiao(2);
            roboAvancado.executarOperacaoAutonoma("Patrulha");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }

        // 8. Visualização do ambiente
        System.out.println("\n8. Visualização do Ambiente");
        ambiente.visualizarAmbiente();

        // 9. Teste de remoção de robôs
        System.out.println("\n9. Teste de Remoção de Robôs");
        ambiente.removerRobo("RA1");
        System.out.println("Número de robôs após remoção: " + ambiente.getFrota().size());

        // 10. Teste de obstáculos
        System.out.println("\n10. Teste de Obstáculos");
        ambiente.adicionarObstaculo(7, 7, 0, 2, 2, 3, TipoObstaculo.PREDIO);
        try {
            roboTerrestre.moverPara(7, 7, 0);
            System.out.println("Erro: Deveria ter detectado colisão com obstáculo");
        } catch (ColisaoException e) {
            System.out.println("Sucesso: Colisão com obstáculo detectada");
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }

        System.out.println("\n=== Testes Concluídos ===");
        
        // Iniciando o menu interativo
        System.out.println("\n=== Iniciando Menu Interativo ===");
        Menu menu = new Menu(ambiente, roboAvancado);
        menu.exibirMenu();
    }
}