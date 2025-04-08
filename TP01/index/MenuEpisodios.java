package TP01.index;

import java.util.Scanner;
import TP01.CRUD.*;
import TP01.Entidades.*;

public class MenuEpisodios {

    ArquivoEpisodio arqEpisodios;
    private static Scanner console = new Scanner(System.in);

    public MenuEpisodios() throws Exception {
        arqEpisodios = new ArquivoEpisodio();
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
                    buscarEpisodio();
                    break;
                case 2:
                    incluirEpisodio();
                    break;
                case 3:
                    alterarEpisodio();
                    break;
                case 4:
                    excluirEpisodio();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        } while (opcao != 0);
    }

    public void buscarEpisodio() {
        System.out.println("\nBusca de episódio");
    
        System.out.print("\nID do episódio: ");
        int id;
        try {
            id = Integer.valueOf(console.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }
    
        try {
            Episodio ep = arqEpisodios.read(id);
            if (ep != null) {
                mostraEpisodio(ep);
            } else {
                System.out.println("Episódio não encontrado.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void incluirEpisodio() {
        System.out.println("\nInclusão de episódio");

        try {
            System.out.print("ID da Série: ");
            int idSerie = Integer.valueOf(console.nextLine());
            System.out.print("Título: ");
            String nome = console.nextLine();
            System.out.print("Duração (minutos): ");
            int duracao = Integer.valueOf(console.nextLine());
            System.out.print("Número do Episódio: ");
            int TemporadasetTemporada = Integer.valueOf(console.nextLine());

            Episodio ep = new Episodio(-1, idSerie, nome, duracao, TemporadasetTemporada);
            int id = arqEpisodios.create(ep);
            System.out.println("Episódio incluído com ID: " + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void alterarEpisodio() {
        System.out.println("\nAlteração de episódio");
        System.out.print("\nID do episódio: ");
        int id;
        try {
            id = Integer.valueOf(console.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }
    
        try {
            Episodio ep = arqEpisodios.read(id);
            if (ep == null) {
                System.out.println("Episódio não encontrado.");
                return;
            }
    
            System.out.print("Novo título: ");
            String novonome = console.nextLine();
            System.out.print("Nova duração: ");
            int novaduracao = Integer.valueOf(console.nextLine());
            System.out.print("Novo número do episódio: ");
            int novoTemporadasetTemporada = Integer.valueOf(console.nextLine());
    
            ep.setnome(novonome);
            ep.setduracao(novaduracao);
            ep.setTemporada(novoTemporadasetTemporada);
    
            if (arqEpisodios.update(ep))
                System.out.println("Episódio atualizado!");
            else
                System.out.println("Não foi possível atualizar.");
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    public void excluirEpisodio() {
        System.out.println("\nExclusão de episódio");
        System.out.print("\nID do episódio: ");
    
        int id;
        try {
            id = Integer.valueOf(console.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }
    
        try {
            if (arqEpisodios.delete(id))
                System.out.println("Episódio excluído!");
            else
                System.out.println("Episódio não encontrado.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    

    public void mostraEpisodio(Episodio ep) {
        System.out.println("\nEpisódio:");
        System.out.println("ID: " + ep.getId());
        System.out.println("ID Série: " + ep.getIdSerie());
        System.out.println("Título: " + ep.getnome());
        System.out.println("Duração: " + ep.getduracao() + " min");
        System.out.println("Número: " + ep.getTemporada());
    }
}