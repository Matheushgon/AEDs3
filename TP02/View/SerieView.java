package TP02.View;

import TP02.Model.Serie;
import TP02.Model.SerieCRUD;
import TP02.Controller.SerieController;

import java.util.Scanner;

public class SerieView {

    SerieCRUD arquivo;
    SerieController serieController = new SerieController();
    Serie s = new Serie();
    private static Scanner console = new Scanner(System.in);

    public SerieView() throws Exception {
        arquivo = new SerieCRUD();
    }

    public void menu() {
        int opcao;
        do {
            System.out.println("\n\nAEDsIII");
            System.out.println("-------");
            System.out.println("> Início > Séries");
            System.out.println("\n1 - Buscar");
            System.out.println("2 - Incluir");
            System.out.println("3 - Alterar");
            System.out.println("4 - Excluir");
            System.out.println("0 - Voltar");

            System.out.print("\nOpção: ");
            try {
                opcao = Integer.parseInt(console.nextLine());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    s = serieController.buscarSerie();
                    if (s != null) {
                        mostraSerie(s);
                        System.out.println("\nDeseja vizualizar os episódios desta série? (S/N)");
                        char resp = console.nextLine().charAt(0);
                        if (resp == 'S' || resp == 's') {
                            serieController.mostraEpisodios(s);
                        }
                    }
                    System.out.println("\nPressione Enter para continuar...");
                    console.nextLine();
                    break;
                case 2:
                    serieController.incluirSerie();
                    System.out.println("\nPressione Enter para continuar...");
                    console.nextLine();
                    break;
                case 3:
                    s = serieController.buscarSerie();
                    if (s != null) {
                        mostraSerie(s);
                        String nome = serieController.alterarSerie(s.getNome());
                        s = serieController.buscarSerie(nome);
                        if (s != null) mostraSerie(s);
                    } else {
                        System.out.println("ERRO: Série não encontrada.");
                    }
                    System.out.println("\nPressione Enter para continuar...");
                    console.nextLine();
                    break;
                case 4:
                    serieController.excluirSerie();
                    System.out.println("\nPressione Enter para continuar...");
                    console.nextLine();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
                    System.out.println("\nPressione Enter para continuar...");
                    console.nextLine();
                    break;
            }

        } while (opcao != 0);
    }

    public void mostraSerie(Serie serie) {
        if (serie != null) {
            System.out.println("\nDetalhes da Série:");
            System.out.println("----------------------");
            System.out.println("Nome..........: " + serie.getNome());
            System.out.println("Ano...........: " + serie.getAnoLancamento());
            System.out.println("Sinopse.......: " + serie.getSinopse());
            System.out.println("Streaming.....: " + serie.getStreaming());
            System.out.println("----------------------");
        }
    }
}
