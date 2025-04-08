package TP01.CRUD;

import TP01.index.Arquivo;
import TP01.Entidades.Episodio;

public class ArquivoEpisodio extends Arquivo<Episodio> {

    public ArquivoEpisodio() throws Exception {
        super("episodios", Episodio.class.getConstructor());
    }

    // CREATE
    @Override
    public int create(Episodio e) throws Exception {
        return super.create(e);
    }

    // READ por ID
    @Override
    public Episodio read(int id) throws Exception {
        return super.read(id);
    }

    // DELETE por ID
    @Override
    public boolean delete(int id) throws Exception {
        return super.delete(id);
    }

    // UPDATE por ID
    @Override
    public boolean update(Episodio e) throws Exception {
        return super.update(e);
    }
}
