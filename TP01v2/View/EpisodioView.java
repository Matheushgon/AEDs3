package TP01v2.View;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import TP01v2.Model.Episodio;
import TP01v2.Model.EpisodioCRUD;
import TP01v2.Model.SerieCRUD;
import TP01v2.Controller.EpisodioController;

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
                    break;
                case 2:
                    episodioController.incluirEpisodio();
                    break;
                case 3:
                    ep = episodioController.buscarEpisodio();
                    if (ep == null) {
                        System.out.println("Episódio não encontrado.");
                        break;
                    } else {
                        mostraEpisodio(ep);
                    }
                    episodioController.alterarEpisodio(ep.getNome());
                    break;
                case 4:
                    episodioController.excluirEpisodio();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
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
            System.out.println("----------------------");
        }
    }
}
