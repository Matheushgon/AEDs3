package TP02.Model;

import TP02.Utils.Registro;

import java.io.*;
import java.time.LocalDate;

public class Episodio implements Registro{
    //atributos
    private int id;
    private int idSerie; //chave estrangeira
    private String nome;
    private int temporada;
    private int duracao;
    private int numeroEpisodio;
    private LocalDate dataLancamento;

    //construtores
    public Episodio() {
        this("", -1, 0, 0, LocalDate.now(), 0);
    }

    public Episodio(String n, int is, int t, int ne, LocalDate dl, int d) {
        this(-1, is, n, t, ne, dl, d);
    }

    public Episodio(int id, int idSerie, String nome, int temporada, int numeroEpisodio, LocalDate dataLancamento, int duracao) {
        this.id = id;
        this.idSerie = idSerie;
        this.nome = nome;
        this.temporada = temporada;
        this.numeroEpisodio = numeroEpisodio;
        this.dataLancamento = dataLancamento;
        this.duracao = duracao;
    }

    //getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getTemporada() {
        return temporada;
    }

    public void setTemporada(int temporada) {
        this.temporada = temporada;
    }

    public int getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public void setNumeroEpisodio(int numeroEpisodio) {
        this.numeroEpisodio = numeroEpisodio;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }
    
    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public int getIdSerie() {
        return idSerie;
    }

    public void setIdSerie(int idSerie) {
        this.idSerie = idSerie;
    }

    public String toString() {
        return "\nEpisódio..........: " + this.nome +
               "\nTemporada.........: " + this.temporada +
               "\nNúmero do Episódio: " + this.numeroEpisodio +
               "\nData de Lançamento: " + this.dataLancamento +
               "\nDuração...........: " + this.duracao;
    }

    public void fromByteArray(byte[] b) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        DataInputStream dis = new DataInputStream(bais);

        this.id = dis.readInt();
        this.idSerie = dis.readInt();
        this.nome = dis.readUTF();
        this.temporada = dis.readInt();
        this.numeroEpisodio = dis.readInt();
        this.dataLancamento = LocalDate.ofEpochDay(dis.readInt());
        this.duracao = dis.readInt();
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeInt(this.id);
        dos.writeInt(this.idSerie);
        dos.writeUTF(this.nome);
        dos.writeInt(this.temporada);
        dos.writeInt(this.numeroEpisodio);
        dos.writeInt((int) this.dataLancamento.toEpochDay());
        dos.writeInt(this.duracao);

        return baos.toByteArray();
    }
}
