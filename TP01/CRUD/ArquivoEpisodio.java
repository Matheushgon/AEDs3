package TP01.CRUD;

import java.io.IOException;
import TP01.index.Arquivo;
import TP01.Entidades.Serie;

public class ArquivoSerie extends Arquivo<Serie> {

    public ArquivoSerie() throws Exception {
        super("series", Serie.class.getConstructor());
    }

    // CREATE
    @Override
    public int create(Serie s) throws Exception {
        return super.create(s);
    }

    // READ por ID
    @Override
    public Serie read(int id) throws Exception {
        return super.read(id);
    }

    // DELETE por ID
    @Override
    public boolean delete(int id) throws Exception {
        return super.delete(id);
    }

    // UPDATE por ID
    @Override
    public boolean update(Serie s) throws Exception {
        return super.update(s);
    }
}