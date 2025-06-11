package TP02.Model;

import TP02.Utils.Arquivo;
import TP02.Utils.HashExtensivel;

public class AtorCRUD extends TP02.Utils.Arquivo<Ator> {
    
    //construtor
    public AtorCRUD() throws Exception {
        super("atores", Ator.class.getConstructor());
        //índices direto -> nome id

    }
}
