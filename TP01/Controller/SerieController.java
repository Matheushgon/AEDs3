package TP01.Controller;

import TP01.Model.Episodio;
import TP01.Model.EpisodioCRUD;
import TP01.Model.Serie;
import TP01.Model.SerieCRUD;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

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
                System.out.println("\nERRO: Série não encontrada.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("\nERRO ao buscar série.");
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
                System.out.println("\nSérie incluída com sucesso.");
            } catch (Exception e) {
                System.out.println("\nERRO ao incluir série.");
                e.printStackTrace();
            }
        }
    }

    public String alterarSerie(String nome) {
        System.out.println("\nAlteração de Série");

        try {
            Serie serie = arquivo.read(nome);
            if (serie != null) {
                System.out.print("Novo nome (deixe em branco para manter): ");
                String novoNome = console.nextLine();
                // Se o novo nome já existir, não permite a alteração
                if (!novoNome.isEmpty() && buscarSerie(novoNome) != null) {
                    System.out.println("\nERRO: Já existe uma série com esse nome. Alteração não permitida.");
                    return null;
                }
                // Se o novo nome for vazio, mantém o nome atual
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
                        System.out.println("\nSérie alterada com sucesso.");
                        return novoNome.isEmpty() ? nome : novoNome;
                    } else {
                        System.out.println("\nERRO ao alterar série.");
                    }
                } else {
                    System.out.println("\nAlterações canceladas.");
                }

            } else {
                System.out.println("\nERRO: Série não encontrada.");
            }

        } catch (Exception e) {
            System.out.println("\nErro ao alterar série.");
            e.printStackTrace();
        }
        return null;
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
                    System.out.println("\nERRO: A série possui episódios associados. Exclusão não permitida.");
                    return;
                } else {
                    System.out.print("Confirma exclusão? (S/N): ");
                    char resp = console.nextLine().charAt(0);

                    if (resp == 'S' || resp == 's') {
                        if (!arquivo.delete(nome)) {
                            System.out.println("\nSérie excluída com sucesso.");
                        } else {
                            System.out.println("\nERRO ao excluir série.");
                        }
                    } else {
                        System.out.println("\nExclusão cancelada.");
                    }
                }
            } else {
                System.out.println("\nERRO: Série não encontrada.");
            }

        } catch (Exception e) {
            System.out.println("\nERRO ao excluir série.");
            e.printStackTrace();
        }
    }

    public void mostraEpisodios(Serie s) {
        if (s == null) {
            System.out.println("\nERRO: Série não encontrada.");
            return;
        }

        System.out.println("\nEpisódios da Série: " + s.getNome());
        System.out.println("----------------------");

        try {
            List<Episodio> episodios = episodioCRUD.buscarPorIdSerie(s.getId());

            if (episodios.isEmpty()) {
                System.out.println("\nNenhum episódio encontrado para esta série.");
                return;
            }

            // Agrupar por temporada
            Map<Integer, List<Episodio>> episodiosPorTemporada = new TreeMap<>();
            for (Episodio ep : episodios) {
                episodiosPorTemporada
                    .computeIfAbsent(ep.getTemporada(), k -> new ArrayList<>())
                    .add(ep);
            }

            // Mostrar episódios agrupados e ordenados
            for (Map.Entry<Integer, List<Episodio>> entrada : episodiosPorTemporada.entrySet()) {
                int temporada = entrada.getKey();
                List<Episodio> listaEpisodios = entrada.getValue();

                // Ordenar os episódios por número do episódio
                listaEpisodios.sort(Comparator.comparingInt(Episodio::getNumeroEpisodio));

                System.out.println("Temporada " + temporada + ":");
                for (Episodio ep : listaEpisodios) {
                    System.out.println("  Episódio " + ep.getNumeroEpisodio() + ": " + ep.getNome());
                }
            }

        } catch (Exception e) {
            System.out.println("\nERRO ao buscar episódios.");
            e.printStackTrace();    
        }
    }

}
