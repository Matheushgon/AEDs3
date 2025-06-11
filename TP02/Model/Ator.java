package TP02.Model;

import TP02.Utils.Registro;

import java.io.*;
import java.time.LocalDate;

public class Ator implements Registro{
    //atributos
    private int id;
    private int idSerie; //chave estrangeira
    private String nome;
    private String nacionalidade;
    private LocalDate nascimento;

    //construtores
    public Ator() {
        this(-1, -1, "", "", LocalDate.now());
    }

    public Ator(int idSerie, String nome, String nacionalidade, LocalDate nascimento) {
        this(-1, idSerie, nome, nacionalidade, nascimento);
    }

    public Ator(int id, int idSerie, String nome, String nacionalidade, LocalDate nascimento) {
        this.id = id;
        this.idSerie = idSerie;
        this.nome = nome; 
        this.nacionalidade = nacionalidade;
        this.nascimento = nascimento;
    }

    //getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdSerie() {
        return idSerie;
    }

    public void setIdSerie(int idSerie) {
        this.idSerie = idSerie;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNascionalidade(String nascionalidade) {
        this.nacionalidade = nascionalidade;
    }

    public String getNascionalidade() {
        return nacionalidade;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public String toString() {
        return "\nNome.: " + this.nome +
               "\nIdade: " + ( ( LocalDate.now().getDayOfYear() >= this.nascimento.getDayOfYear() )?
                                (LocalDate.now().getYear() - this.nascimento.getYear()):
                                (LocalDate.now().getYear() - this.nascimento.getYear() - 1) ) +
               "\nPaís.: " + this.nacionalidade;
    }

    public void fromByteArray(byte[] b) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        DataInputStream dis = new DataInputStream(bais);

        this.id = dis.readInt();
        this.idSerie = dis.readInt();
        this.nome = dis.readUTF();
        this.nacionalidade = dis.readUTF();
        this.nascimento = LocalDate.ofEpochDay(dis.readInt());
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeInt(this.id);
        dos.writeInt(this.idSerie);
        dos.writeUTF(this.nome);
        dos.writeUTF(this.nacionalidade);
        dos.writeInt((int) this.nascimento.toEpochDay());

        return baos.toByteArray();
    }
}
