package TP01.Entidades;

import java.io.*;
import TP01.index.Registro;

public class Serie implements Registro {

    private int id;
    private String nome;
    private int anoLancamento;
    private String sinopse;
    private String streaming;

    public Serie() {
        this(-1, "", 0, "", "");
    }

    public Serie(String nome, int anoLancamento, String sinopse, String streaming) {
        this(-1, nome, anoLancamento, sinopse, streaming);
    }

    public Serie(int id, String nome, int anoLancamento, String sinopse, String streaming) {
        this.id = id;
        this.nome = nome;
        this.anoLancamento = anoLancamento;
        this.sinopse = sinopse;
        this.streaming = streaming;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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
        return "\nID.............: " + this.id +
               "\nNome...........: " + this.nome +
               "\nAno Lançamento.: " + this.anoLancamento +
               "\nSinopse........: " + this.sinopse +
               "\nStreaming......: " + this.streaming;
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

    public void fromByteArray(byte[] b) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        DataInputStream dis = new DataInputStream(bais);

        this.id = dis.readInt();
        this.nome = dis.readUTF();
        this.anoLancamento = dis.readInt();
        this.sinopse = dis.readUTF();
        this.streaming = dis.readUTF();
    }
}