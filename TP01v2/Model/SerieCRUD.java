package TP01v2.Model;

import TP01v2.Utils.Arquivo;
import TP01v2.Utils.HashExtensivel;

public class SerieCRUD extends TP01v2.Utils.Arquivo<Serie>{

    Arquivo<Serie> arquivoSerie;
    HashExtensivel<ParNomeId> indiceIndiretoNome;

    //construtor
    public SerieCRUD() throws Exception {
        super("series", Serie.class.getConstructor());
        indiceIndiretoNome = new HashExtensivel<>(
            ParNomeId.class.getConstructor(), 
            4, 
            ".\\dados\\series\\indiceNome.d.db",   // diretório
            ".\\dados\\series\\indiceNome.c.db"    // cestos 
        );
    }
    
    @Override
    public int create(Serie s) throws Exception {
        int id = super.create(s);
        indiceIndiretoNome.create(new ParNomeId(s.getNome(), id));
        return id;
    }

    public Serie read(String nome) throws Exception {
        ParNomeId pni = indiceIndiretoNome.read(ParNomeId.hash(nome));
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
        Serie s = super.read(id);
        if(s != null) {
            if(super.delete(id))
                return indiceIndiretoNome.delete(ParNomeId.hash(s.getNome()));
        }
        return false;
    }

    @Override
    public boolean update(Serie novaSerie) throws Exception {
        Serie serieVelha = read(novaSerie.getId());
        if(super.update(novaSerie)) {
            if(novaSerie.getNome().compareTo(serieVelha.getNome())!=0) {
                indiceIndiretoNome.delete(ParNomeId.hash(serieVelha.getNome()));
                indiceIndiretoNome.create(new ParNomeId(novaSerie.getNome(), novaSerie.getId()));
            }
            return true;
        }
        return false;
    }
}
