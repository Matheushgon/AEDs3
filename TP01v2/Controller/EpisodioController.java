package TP01v2.Controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import TP01v2.Model.Episodio;
import TP01v2.Model.EpisodioCRUD;
import TP01v2.Model.Serie;
import TP01v2.Model.SerieCRUD;

public class EpisodioController {
    EpisodioCRUD arquivo;
    SerieCRUD arquivoSerie = new SerieCRUD();
    private static Scanner console = new Scanner(System.in);

    public EpisodioController() throws Exception {
        arquivo = new EpisodioCRUD();
    }

    public Episodio buscarEpisodio() {
        System.out.println("\nBusca de Episódio");
        System.out.print("Nome do Episódio: ");
        String nome = console.nextLine();

        try {
            Episodio episodio = arquivo.read(nome);
            if (episodio != null) {
                return episodio;
            } else {
                System.out.println("Episódio não encontrado.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar episódio.");
            e.printStackTrace();
            return null;
        }
    }

    public boolean existeEpisodio(int idSerie) {
        try {
            return arquivo.existeEpisodio(idSerie);
        } catch (Exception e) {
            System.out.println("Erro ao verificar existência do episódio.");
            e.printStackTrace();
            return false;
        }
    }

    public void incluirEpisodio() {
        System.out.println("\nInclusão de Episódio");

        System.out.print("Nome da Serie: ");
        String nomeSerie = console.nextLine();

        try {
            Serie serie = arquivoSerie.read(nomeSerie);

            if (serie != null) {

                System.out.print("Nome: ");
                String nome = console.nextLine();

                System.out.print("Temporada: ");
                int temporada = Integer.parseInt(console.nextLine());

                System.out.print("Número do Episódio: ");
                int numero = Integer.parseInt(console.nextLine());

                System.out.print("Data de Exibição (DD/MM/AAAA): ");
                String dataStr = console.nextLine();
                LocalDate dataExibicao = LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                System.out.print("Duração do Episódio em minutos: ");
                int duracao = Integer.parseInt(console.nextLine());

                System.out.print("Confirma inclusão? (S/N): ");
                char resp = console.nextLine().charAt(0);

                if (resp == 'S' || resp == 's') {
                    try {
                        Episodio ep = new Episodio(nome, serie.getId(), temporada, numero, dataExibicao, duracao);
                        arquivo.create(ep);
                        System.out.println("\nEpisódio incluído com sucesso.");
                    } catch (Exception e) {
                        System.out.println("\nErro ao incluir episódio.\n");
                        e.printStackTrace();
                    }

                }

            } else {
                System.out.println("\nSérie não encontrada.");
            }
                
        } catch (Exception e) {
            System.out.println("\nErro ao buscar série.\n");
            e.printStackTrace();
        }
    }

    public Episodio alterarEpisodio(String nome) {
        System.out.println("\nAlteração de Episódio");

        try {
            Episodio episodio = arquivo.read(nome);
            if (episodio != null) {

                System.out.print("Novo nome (deixe em branco para manter): ");
                String novoNome = console.nextLine();
                if (!novoNome.isEmpty()) episodio.setNome(novoNome);

                System.out.print("Nova temporada (deixe em branco para manter): ");
                String novaTemporada = console.nextLine();
                if (!novaTemporada.isEmpty()) episodio.setTemporada(Integer.parseInt(novaTemporada));

                System.out.print("Novo número do episódio (deixe em branco para manter): ");
                String novoNumero = console.nextLine();
                if (!novoNumero.isEmpty()) episodio.setNumeroEpisodio(Integer.parseInt(novoNumero));

                System.out.print("Nova data de exibição (DD/MM/AAAA) (deixe em branco para manter): ");
                String novaData = console.nextLine();
                if (!novaData.isEmpty()) {
                    LocalDate novaDataExibicao = LocalDate.parse(novaData, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    episodio.setDataLancamento(novaDataExibicao);
                }

                System.out.print("Novo duração do episódio (deixe em branco para manter): ");
                String duracao = console.nextLine();
                if (!duracao.isEmpty()) episodio.setDuracao(Integer.parseInt(duracao));

                System.out.print("Confirma alterações? (S/N): ");
                char resp = console.nextLine().charAt(0);
                if (resp == 'S' || resp == 's') {
                    if (arquivo.update(episodio)) {
                        System.out.println("Episódio alterado com sucesso.");
                        return episodio;
                    } else {
                        System.out.println("Erro ao alterar episódio.");
                        return null;
                    }
                } else {
                    System.out.println("Alterações canceladas.");
                    return null;
                }

            } else {
                System.out.println("Episódio não encontrado.");
                return null;
            }

        } catch (Exception e) {
            System.out.println("Erro ao alterar episódio.");
            e.printStackTrace();
            return null;
        }
    }

    public void excluirEpisodio() {
        System.out.println("\nExclusão de Episódio");

        System.out.print("Nome do Episódio a excluir: ");
        String nome = console.nextLine();

        try {
            Episodio episodio = arquivo.read(nome);
            if (episodio != null) {
                System.out.print("Confirma exclusão? (S/N): ");
                char resp = console.nextLine().charAt(0);

                if (resp == 'S' || resp == 's') {
                    if (arquivo.delete(nome)) {
                        System.out.println("Episódio excluído com sucesso.");
                    } else {
                        System.out.println("Erro ao excluir episódio.");
                    }
                } else {
                    System.out.println("Exclusão cancelada.");
                }
            } else {
                System.out.println("Episódio não encontrado.");
            }

        } catch (Exception e) {
            System.out.println("Erro ao excluir episódio.");
            e.printStackTrace();
        }
    }

    public String getNomeSerie(int idSerie) {
        try {
            Serie serie = arquivoSerie.read(idSerie);
            if (serie != null) {
                return serie.getNome();
            } else {
                return "Série não encontrada.";
            }
        } catch (Exception e) {
            return "Erro ao buscar série.";
        }
    }

}
