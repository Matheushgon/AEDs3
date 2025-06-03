package TP01.Controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import TP01.Model.Episodio;
import TP01.Model.EpisodioCRUD;
import TP01.Model.Serie;
import TP01.Model.SerieCRUD;

public class EpisodioController {
    EpisodioCRUD arquivo;
    SerieCRUD arquivoSerie = new SerieCRUD();
    SerieController serieController = new SerieController();
    private static Scanner console = new Scanner(System.in);

    public EpisodioController() throws Exception {
        arquivo = new EpisodioCRUD();
    }

    public Episodio buscarEpisodio() {
        //Melhorar lógica com arvore B+2
        System.out.println("\nBusca de Episódio");
        System.out.print("Nome da Série: ");
        String nomeSerie = console.nextLine();
        try {
            Serie s = serieController.buscarSerie(nomeSerie);
            if (s == null) {
                System.out.println("\nERRO: Série não encontrada.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("\nERRO ao buscar série.");
            e.printStackTrace();
            return null;
        }
        System.out.print("Nome do Episódio: ");
        String nome = console.nextLine();
        try {
            Episodio episodio = arquivo.read(nome);
            if (episodio != null) {
                return episodio;
            } else {
                System.out.println("\nERRO: Episódio não encontrado.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("\nERRO ao buscar episódio.");
            e.printStackTrace();
            return null;
        }
    }

    public Episodio buscarEpisodio(int idSerie, String nomeEpisodio) {
        try {
            Serie serie = arquivoSerie.read(idSerie);
            if (serie != null) {
                Episodio episodio = arquivo.read(serie.getId(), nomeEpisodio);
                if (episodio != null) {
                    return episodio;
                } else {
                    System.out.println("\nERRO: Episódio não encontrado.");
                    return null;
                }
            } else {
                System.out.println("\nERRO: Série não encontrada.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("\nERRO ao buscar episódio.");
            e.printStackTrace();
            return null;
        }
    }

    public Episodio buscarEpisodio(String NomeSerie, String nomeEpisodio) {
        try {
            Serie serie = arquivoSerie.read(NomeSerie);
            if (serie != null) {
                Episodio episodio = arquivo.read(serie.getId(), nomeEpisodio);
                if (episodio != null) {
                    return episodio;
                } else {
                    System.out.println("\nERRO: Episódio não encontrado.");
                    return null;
                }
            } else {
                System.out.println("\nERRO: Série não encontrada.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("\nERRO ao buscar episódio.");
            e.printStackTrace();
            return null;
        }
        
    }

    public boolean existeEpisodio(int idSerie) {
        try {
            return arquivo.existeEpisodio(idSerie);
        } catch (Exception e) {
            System.out.println("\nERRO ao verificar existência do episódio.");
            e.printStackTrace();
            return false;
        }
    }

    public void incluirEpisodio() {
        System.out.println("\nInclusão de Episódio");

        System.out.print("Nome da Série: ");
        String nomeSerie = console.nextLine();

        try {
            Serie serie = arquivoSerie.read(nomeSerie);

            if (serie != null) {

                System.out.print("Nome do Episódio: ");

                String nome = console.nextLine();

                System.out.print("Temporada: ");
                int temporada = Integer.parseInt(console.nextLine());

                System.out.print("Número do Episódio: ");
                int numero = Integer.parseInt(console.nextLine());

                System.out.print("Data de Exibição (DD/MM/AAAA): ");
                String dataStr = console.nextLine();
                LocalDate dataExibicao = LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                if(dataExibicao.getYear() < serie.getAnoLancamento()){
                    System.out.println("\nERRO: Data de exibição não pode ser anterior ao ano de lançamento da série.");
                    return;
                }

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
                        System.out.println("\nERRO ao incluir episódio.\n");
                        e.printStackTrace();
                    }

                }

            } else {
                System.out.println("\nSérie não encontrada.");
            }
                
        } catch (Exception e) {
            System.out.println("\nERRO ao buscar série.\n");
            e.printStackTrace();
        }
    }

    public String alterarEpisodio(String nome) {
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
                    if (novaDataExibicao.getYear() < arquivoSerie.read(episodio.getIdSerie()).getAnoLancamento()) {
                        System.out.println("\nERRO: Data de exibição não pode ser anterior ao ano de lançamento da série.");
                        return null;
                    }
                    episodio.setDataLancamento(novaDataExibicao);
                }

                System.out.print("Novo duração do episódio (deixe em branco para manter): ");
                String duracao = console.nextLine();
                if (!duracao.isEmpty()) episodio.setDuracao(Integer.parseInt(duracao));

                System.out.print("Confirma alterações? (S/N): ");
                char resp = console.nextLine().charAt(0);
                if (resp == 'S' || resp == 's') {
                    if (arquivo.update(episodio)) {
                        System.out.println("\nEpisódio alterado com sucesso.");
                        return episodio.getNome();
                    } else {
                        System.out.println("\nERRO ao alterar episódio.");
                        return null;
                    }
                } else {
                    System.out.println("\nAlterações canceladas.");
                    return null;
                }

            } else {
                System.out.println("\nERRO: Episódio não encontrado.");
                return null;
            }

        } catch (Exception e) {
            System.out.println("\nERRO ao alterar episódio.");
            e.printStackTrace();
            return null;
        }
    }

    public void excluirEpisodio() {
        System.out.println("\nExclusão de Episódio");

        System.out.print("Nome da Série: ");
        String nomeSerie = console.nextLine();
        Serie serie = null;
        Episodio episodio = null;
        try {
            serie = arquivoSerie.read(nomeSerie);
            if (serie == null) {
                System.out.println("\nERRO: Série não encontrada.");
                return;
            }
        } catch (Exception e) {
            System.out.println("\nERRO ao buscar série.");
            e.printStackTrace();
            return;
        }

        System.out.print("Nome do Episódio: ");
        String nome = console.nextLine();
        try {
            episodio = arquivo.read(nome);
            if (episodio == null) {
                System.out.println("\nERRO: Episódio não encontrado.");
                return;
            }
        } catch (Exception e) {
            System.out.println("\nERRO ao buscar episódio.");
            e.printStackTrace();
        }

        if (serie != null && episodio != null) {
            System.out.print("Confirma exclusão? (S/N): ");
            char resp = console.nextLine().charAt(0);
            if (resp == 'S' || resp == 's') {
                try {
                    arquivo.delete(episodio.getId());
                    System.out.println("\nEpisódio excluído com sucesso.");
                } catch (Exception e) {
                    System.out.println("\nERRO ao excluir episódio.");
                    e.printStackTrace();
                    return;
                }
            } else {
                System.out.println("\nExclusão cancelada.");
            }
        } else {
            System.out.println("\nERRO: Episódio não encontrado.");
        }
    }

    public String getNomeSerie(int idSerie) {
        try {
            Serie serie = arquivoSerie.read(idSerie);
            if (serie != null) {
                return serie.getNome();
            } else {
                return "\nSérie não encontrada.";
            }
        } catch (Exception e) {
            return "\nERRO ao buscar série.";
        }
    }

}
