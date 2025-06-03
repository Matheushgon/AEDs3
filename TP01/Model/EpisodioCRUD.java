package TP01.Model;

import java.util.ArrayList;
import java.util.List;

import TP01.Utils.Arquivo;
import TP01.Utils.ArvoreBMais;
import TP01.Utils.HashExtensivel;
import TP01.Utils.ParIntInt;

public class EpisodioCRUD extends TP01.Utils.Arquivo<Episodio>{

    Arquivo<Episodio> arquivoEpisodio;
    HashExtensivel<ParNomeId> indiceIndiretoNome;
    ArvoreBMais<ParIntInt> indiceSerieEpisodio;

    //construtor
    public EpisodioCRUD() throws Exception {
        super("episodios", Episodio.class.getConstructor());
        indiceIndiretoNome = new HashExtensivel<>(
            ParNomeId.class.getConstructor(), 
            4, 
            ".\\dados\\episodios\\indiceNome.d.db",   // diretório
            ".\\dados\\episodios\\indiceNome.c.db"    // cestos 
        );
        indiceSerieEpisodio = new ArvoreBMais<>(
            ParIntInt.class.getConstructor(), 
            4, 
            ".\\dados\\episodios\\indiceSerieEpisodio.db"   // arvore B+
        );

    }
    
    @Override
    public int create(Episodio e) throws Exception {
        int id = super.create(e);
        indiceIndiretoNome.create(new ParNomeId(e.getNome(), id));
        indiceSerieEpisodio.create(new ParIntInt(e.getIdSerie(), id));
        return id;
    }

    public Episodio read(String nome) throws Exception {
        ParNomeId pni = indiceIndiretoNome.read(ParNomeId.hash(nome));
        if(pni == null)
            return null;
        return read(pni.getId());
    }

    public Episodio read(int idSerie, String nome) throws Exception {
        ParNomeId pni = indiceIndiretoNome.read(ParNomeId.hash(nome));
        //procurar o episódio pelo nome e id da série
        if(pni == null)
            return null;
        return read(pni.getId());
    }
    
    public boolean delete(String nome) throws Exception {
        ParNomeId pni = indiceIndiretoNome.read(ParNomeId.hash(nome));
        if(pni != null) 
            if(delete(pni.getId())) 
                return indiceIndiretoNome.delete(ParNomeId.hash(nome));
        return false;
    }

    @Override
    public boolean delete(int id) throws Exception {
        Episodio e = super.read(id);
        if(e != null) {
            if(super.delete(id))
            //Remover índice indireto pelo nome
            return indiceIndiretoNome.delete(ParNomeId.hash(e.getNome()));
            //Remover índice direto pela série
            indiceSerieEpisodio.delete(new ParIntInt(e.getIdSerie(), id));
        }
        return false;
    }

    @Override
    public boolean update(Episodio novoEpisodio) throws Exception {
        Episodio episodioVelho = read(novoEpisodio.getId());
        if (episodioVelho == null) return false;
        if(super.update(novoEpisodio)) {
            // Atualiza índice por nome, se o nome mudou
            if (!novoEpisodio.getNome().equals(episodioVelho.getNome())) {
                indiceIndiretoNome.delete(ParNomeId.hash(episodioVelho.getNome()));
                indiceIndiretoNome.create(new ParNomeId(novoEpisodio.getNome(), novoEpisodio.getId()));
            }
            // Atualiza índice B+, se o idSerie mudou
            if (episodioVelho.getIdSerie() != novoEpisodio.getIdSerie()) {
                indiceSerieEpisodio.delete(new ParIntInt(episodioVelho.getIdSerie(), episodioVelho.getId()));
                indiceSerieEpisodio.create(new ParIntInt(novoEpisodio.getIdSerie(), novoEpisodio.getId()));
            }
            return true;
        }
        return false;
    }

    public List<Episodio> buscarPorIdSerie(int idSerie) throws Exception {
        List<Episodio> episodios = new ArrayList<>();

        ParIntInt chaveParcial = new ParIntInt(idSerie, -1);
        ArrayList<ParIntInt> chaves = indiceSerieEpisodio.read(chaveParcial);

        for (ParIntInt chave : chaves) {
            Episodio ep = this.read(chave.getIdEpisodio());
            if (ep != null) {
                episodios.add(ep);
            }
        }

        return episodios;
    }

    public boolean existeEpisodio(int idSerie) {
        try {
            ParIntInt chaveParcial = new ParIntInt(idSerie, -1);
            ArrayList<ParIntInt> chaves = indiceSerieEpisodio.read(chaveParcial);
            return !chaves.isEmpty();
        } catch (Exception e) {
            System.out.println("Erro ao verificar existência do episódio.");
            e.printStackTrace();
            return false;
        }
    }

}
