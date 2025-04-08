package TP01.Entidades;

import java.io.*;
import TP01.index.Registro;

public class Episodio implements Registro {

    private int id;
    private int idSerie;
    private String nome;
    private int temporada;
    private int duracao;

    public Episodio() {
        this(-1, -1, "", 0, 0);
    }

    public Episodio(int idSerie, String nome, int temporada, int duracao) {
        this(-1, idSerie, nome, temporada, duracao);
    }

    public Episodio(int id, int idSerie, String nome, int temporada, int duracao) {
        this.id = id;
        this.idSerie = idSerie;
        this.nome = nome;
        this.temporada = temporada;
        this.duracao = duracao;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getduracao() {
        return duracao;
    }
    
    public void setduracao(int duracao) {
        this.duracao = duracao;
    }
    
    public String getnome() {
        return nome;
    }
    
    public void setnome(String nome) {
        this.nome = nome;
    }
    
    public int getTemporada() {
        return temporada;
    }
    
    public void setTemporada(int temporada) {
        this.temporada = temporada;
    }    

    public int getId() {
        return id;
    }

    public int getIdSerie() {
        return idSerie;
    }

    public String toString() {
        return "\nID Episodio....: " + this.id +
               "\nID Serie.......: " + this.idSerie +
               "\nnome.........: " + this.nome +
               "\nTemporada......: " + this.temporada +
               "\nduracao.........: " + this.duracao;
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeInt(this.id);
        dos.writeInt(this.idSerie);
        dos.writeUTF(this.nome);
        dos.writeInt(this.temporada);
        dos.writeInt(this.duracao);

        return baos.toByteArray();
    }

    public void fromByteArray(byte[] b) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        DataInputStream dis = new DataInputStream(bais);

        this.id = dis.readInt();
        this.idSerie = dis.readInt();
        this.nome = dis.readUTF();
        this.temporada = dis.readInt();
        this.duracao = dis.readInt();
    }
}