package TP01.index;

import java.util.Scanner;
import TP01.CRUD.*;
import TP01.Entidades.*;

public class MenuSeries {

    ArquivoSerie arqSeries;
    private static Scanner console = new Scanner(System.in);

    public MenuSeries() throws Exception {
        arqSeries = new ArquivoSerie();
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
                opcao = Integer.valueOf(console.nextLine());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    buscarSerie();
                    break;
                case 2:
                    incluirSerie();
                    break;
                case 3:
                    alterarSerie();
                    break;
                case 4:
                    excluirSerie();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        } while (opcao != 0);
    }

    public void buscarSerie() {
        System.out.println("\nBusca de série");
        System.out.print("\nID da série: ");
        
        int id;
        try {
            id = Integer.valueOf(console.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }
    
        try {
            Serie serie = arqSeries.read(id);
            if (serie != null) {
                mostraSerie(serie);
            } else {
                System.out.println("Série não encontrada.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    public void incluirSerie() {
        System.out.println("\nInclusão de série");
    
        try {
            System.out.print("Nome: ");
            String nome = console.nextLine();
            System.out.print("Ano de lançamento: ");
            int ano = Integer.valueOf(console.nextLine());
            System.out.print("Sinopse: ");
            String sinopse = console.nextLine();
            System.out.print("Streaming: ");
            String streaming = console.nextLine();
    
            Serie s = new Serie(nome, ano, sinopse, streaming);
            int id = arqSeries.create(s);
            System.out.println("Série incluída com ID: " + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    

    public void alterarSerie() {
        System.out.println("\nAlteração de série");
        System.out.print("\nID da série: ");
        
        int id;
        try {
            id = Integer.valueOf(console.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }
    
        try {
            Serie s = arqSeries.read(id);
            if (s == null) {
                System.out.println("Série não encontrada.");
                return;
            }
    
            System.out.print("Novo nome: ");
            String novoNome = console.nextLine();
            System.out.print("Nova sinopse: ");
            String novaSinopse = console.nextLine();
            System.out.print("Novo ano de lançamento: ");
            int novoAno = Integer.valueOf(console.nextLine());
            System.out.print("Novo streaming: ");
            String novoStreaming = console.nextLine();
    
            s.setNome(novoNome);
            s.setSinopse(novaSinopse);
            s.setAnoLancamento(novoAno);
            s.setStreaming(novoStreaming);
    
            if (arqSeries.update(s))
                System.out.println("Série atualizada!");
            else
                System.out.println("Não foi possível atualizar.");
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    

    public void excluirSerie() {
        System.out.println("\nExclusão de série");
        System.out.print("\nID da série: ");
    
        int id;
        try {
            id = Integer.valueOf(console.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }
    
        try {
            if (arqSeries.delete(id))
                System.out.println("Série excluída!");
            else
                System.out.println("Série não encontrada.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    

    public void mostraSerie(Serie s) {
        System.out.println("\nSérie:");
        System.out.println("ID: " + s.getId());
        System.out.println("Nome: " + s.getNome());
        System.out.println("Ano de Lançamento: " + s.getAnoLancamento());
        System.out.println("Sinopse: " + s.getSinopse());
        System.out.println("Streaming: " + s.getStreaming());
    }
    
}