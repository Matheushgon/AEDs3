package TP01v2.Controller;

import TP01v2.Model.EpisodioCRUD;
import TP01v2.Model.Serie;
import TP01v2.Model.SerieCRUD;

import java.util.Scanner;

public class SerieController {
    SerieCRUD arquivo;
    EpisodioCRUD episodioCRUD = new EpisodioCRUD();

    private static Scanner console = new Scanner(System.in);

    public SerieController() throws Exception {
        arquivo = new SerieCRUD();
    }

    public Serie buscarSerie() {
        System.out.print("Nome da Série: ");
        String nome = console.nextLine();

        try {
            Serie serie = arquivo.read(nome);
            if (serie != null) {
                return serie;
            } else {
                System.out.println("Série não encontrada.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar série.");
            e.printStackTrace();
            return null;
        }
    }

    public Serie buscarSerie(String nome) {
        try {
            Serie serie = arquivo.read(nome);
            if (serie != null) {
                return serie;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public void incluirSerie() {
        System.out.println("\nInclusão de Série");

        System.out.print("Nome: ");
        String nome = console.nextLine();

        if(buscarSerie(nome) != null) {
            System.out.println("\nERRO: Série com esse nome já existe. Inclusão não permitida.");
            return;
        }

        System.out.print("Ano de Lançamento: ");
        int ano = Integer.parseInt(console.nextLine());

        System.out.print("Sinopse: ");
        String sinopse = console.nextLine();

        System.out.print("Streaming: ");
        String streaming = console.nextLine();

        System.out.print("Confirma inclusão? (S/N): ");
        char resp = console.nextLine().charAt(0);

        if (resp == 'S' || resp == 's') {
            try {
                Serie serie = new Serie(-1, nome, ano, sinopse, streaming);
                arquivo.create(serie);
                System.out.println("Série incluída com sucesso.");
            } catch (Exception e) {
                System.out.println("Erro ao incluir série.");
                e.printStackTrace();
            }
        }
    }

    public void alterarSerie(String nome) {
        System.out.println("\nAlteração de Série");

        try {
            Serie serie = arquivo.read(nome);
            if (serie != null) {
                System.out.print("Novo nome (deixe em branco para manter): ");
                String novoNome = console.nextLine();
                if (!novoNome.isEmpty()) serie.setNome(novoNome);

                System.out.print("Novo ano de lançamento (deixe em branco): ");
                String novoAno = console.nextLine();
                if (!novoAno.isEmpty()) serie.setAnoLancamento(Integer.parseInt(novoAno));

                System.out.print("Nova sinopse (deixe em branco): ");
                String novaSinopse = console.nextLine();
                if (!novaSinopse.isEmpty()) serie.setSinopse(novaSinopse);

                System.out.print("Novo streaming (deixe em branco): ");
                String novoStreaming = console.nextLine();
                if (!novoStreaming.isEmpty()) serie.setStreaming(novoStreaming);

                System.out.print("Confirma alterações? (S/N): ");
                char resp = console.nextLine().charAt(0);
                if (resp == 'S' || resp == 's') {
                    if (arquivo.update(serie)) {
                        System.out.println("Série alterada com sucesso.");
                    } else {
                        System.out.println("Erro ao alterar série.");
                    }
                } else {
                    System.out.println("Alterações canceladas.");
                }

            } else {
                System.out.println("Série não encontrada.");
            }

        } catch (Exception e) {
            System.out.println("Erro ao alterar série.");
            e.printStackTrace();
        }
    }

    public void excluirSerie() {
        System.out.println("\nExclusão de Série");

        System.out.print("Nome da Série a excluir: ");
        String nome = console.nextLine();

        try {
            Serie serie = arquivo.read(nome);
            if (serie != null) {
                //Procurar na Árvore B+
                if(episodioCRUD.existeEpisodio(serie.getId())) {
                    System.out.println("A série possui episódios associados. Exclusão não permitida.");
                    return;
                } else {
                    System.out.print("Confirma exclusão? (S/N): ");
                    char resp = console.nextLine().charAt(0);

                    if (resp == 'S' || resp == 's') {
                        if (arquivo.delete(nome)) {
                            System.out.println("Série excluída com sucesso.");
                        } else {
                            System.out.println("Erro ao excluir série.");
                        }
                    } else {
                        System.out.println("Exclusão cancelada.");
                    }
                }
            } else {
                System.out.println("Série não encontrada.");
            }

        } catch (Exception e) {
            System.out.println("Erro ao excluir série.");
            e.printStackTrace();
        }
    }

}
