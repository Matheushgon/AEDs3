package TP01.Entidades;

import java.io.*;

public class Episodio {

    private int id;
    private int idSerie;
    private String titulo;
    private int temporada;
    private int numero;

    public Episodio() {
        this(-1, -1, "", 0, 0);
    }

    public Episodio(int idSerie, String titulo, int temporada, int numero) {
        this(-1, idSerie, titulo, temporada, numero);
    }

    public Episodio(int id, int idSerie, String titulo, int temporada, int numero) {
        this.id = id;
        this.idSerie = idSerie;
        this.titulo = titulo;
        this.temporada = temporada;
        this.numero = numero;
    }

    public void setId(int id) {
        this.id = id;
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
               "\nTitulo.........: " + this.titulo +
               "\nTemporada......: " + this.temporada +
               "\nNumero.........: " + this.numero;
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeInt(this.id);
        dos.writeInt(this.idSerie);
        dos.writeUTF(this.titulo);
        dos.writeInt(this.temporada);
        dos.writeInt(this.numero);

        return baos.toByteArray();
    }

    public void fromByteArray(byte[] b) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        DataInputStream dis = new DataInputStream(bais);

        this.id = dis.readInt();
        this.idSerie = dis.readInt();
        this.titulo = dis.readUTF();
        this.temporada = dis.readInt();
        this.numero = dis.readInt();
    }
}