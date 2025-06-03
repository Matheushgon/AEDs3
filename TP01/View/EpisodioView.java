package TP01.View;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import TP01.Model.Episodio;
import TP01.Model.EpisodioCRUD;
import TP01.Model.SerieCRUD;
import TP01.Controller.EpisodioController;

public class EpisodioView {

    EpisodioCRUD arquivo;
    SerieCRUD arquivoSerie = new SerieCRUD();
    EpisodioController episodioController = new EpisodioController();
    Episodio ep = new Episodio();
    private static Scanner console = new Scanner(System.in);

    public EpisodioView() throws Exception {
        arquivo = new EpisodioCRUD();
    }

    public void menu() {

        int opcao;
        do {
            System.out.println("\n\nAEDsIII");
            System.out.println("-------");
            System.out.println("> Início > Episódios");
            System.out.println("\n1 - Buscar");
            System.out.println("2 - Incluir");
            System.out.println("3 - Alterar");
            System.out.println("4 - Excluir");
            System.out.println("0 - Voltar");

            System.out.print("\nOpção: ");
            try {
                opcao = Integer.valueOf(console.nextLine());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    ep = episodioController.buscarEpisodio();
                    if (ep != null) {
                        mostraEpisodio(ep);
                    }
                    System.out.println("\nPressione Enter para continuar...");
                    console.nextLine();
                    break;
                case 2:
                    episodioController.incluirEpisodio();
                    System.out.println("\nPressione Enter para continuar...");
                    console.nextLine();
                    break;
                case 3:
                    ep = episodioController.buscarEpisodio();
                    if (ep != null) {
                        mostraEpisodio(ep);
                        String nome = episodioController.alterarEpisodio(ep.getNome());
                        if (nome != null) ep = episodioController.buscarEpisodio(ep.getIdSerie(), nome);
                    }
                    System.out.println("\nPressione Enter para continuar...");
                    console.nextLine();
                    break;
                case 4:
                    episodioController.excluirEpisodio();
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

    public void mostraEpisodio(Episodio ep) {
        if (ep != null) {
            System.out.println("\nDetalhes do Episódio:");
            System.out.println("----------------------");
            System.out.println("Nome.........: " + ep.getNome());
            System.out.println("Nome da Série: " + episodioController.getNomeSerie(ep.getIdSerie()));
            System.out.println("Temporada....: " + ep.getTemporada());
            System.out.println("Número.......: " + ep.getNumeroEpisodio());
            System.out.println("Exibido em...: " + ep.getDataLancamento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            System.out.println("Duração......: " + ep.getDuracao() + " minutos");
            System.out.println("----------------------");
        }
    }
}
