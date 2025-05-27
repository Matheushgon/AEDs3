package TP01v2.Model;

import TP01v2.Utils.Registro;

import java.io.*;

public class Serie implements Registro{
    //atributos
    int id;
    String nome;
    int anoLancamento;
    String sinopse;
    String streaming;

    //construtores
    public Serie() {
        this.id = -1;
        this.nome = "";
        this.anoLancamento = 0;
        this.sinopse = "";
        this.streaming = "";
    }

    public Serie(int id, String nome, int anoLancamento, String sinopse, String streaming) {
        this.id = id;
        this.nome = nome;
        this.anoLancamento = anoLancamento;
        this.sinopse = sinopse;
        this.streaming = streaming;
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

    public int getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(int anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getStreaming() {
        return streaming;
    }

    public void setStreaming(String streaming) {
        this.streaming = streaming;
    }

    public String toString() {
        return  "\n Nome.............:" + this.nome +
                "\n Ano de Lancamento:" + this.anoLancamento +
                "\n Sinopse..........:" + this.sinopse +
                "\n Streaming........:" + this.streaming;
    }

    public void fromByteArray(byte[] b) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        DataInputStream dis = new DataInputStream(bais);

        this.id = dis.readInt();
        this.nome = dis.readUTF();
        this.anoLancamento = dis.readInt();
        this.sinopse = dis.readUTF();
        this.streaming = dis.readUTF();
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeInt(this.id);
        dos.writeUTF(this.nome);
        dos.writeInt(this.anoLancamento);
        dos.writeUTF(this.sinopse);
        dos.writeUTF(this.streaming);

        return baos.toByteArray();
    }
}
