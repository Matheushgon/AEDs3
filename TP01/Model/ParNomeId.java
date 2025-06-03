package TP01.Model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ParNomeId implements TP01.Utils.RegistroHashExtensivel<ParNomeId> {
    
    private String nome; // chave
    private int id;      // valor
    private final short TAMANHO = 44;  // nome (40 bytes) + id (4 bytes)

    public ParNomeId() {
        this.nome = "";
        this.id = -1;
    }

    public ParNomeId(String nome, int id) {
        if (nome.length() > 40) {
            throw new IllegalArgumentException("Nome deve ter no máximo 40 caracteres.");
        }
        this.nome = nome;
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return hash(this.nome);
    }

    public short size() {
        return this.TAMANHO;
    }

    public String toString() {
        return "(" + this.nome + ";" + this.id + ")";
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        byte[] nomeBytes = new byte[40];
        byte[] nomeOriginal = this.nome.getBytes(StandardCharsets.UTF_8);
        int len = Math.min(nomeOriginal.length, 40);
        System.arraycopy(nomeOriginal, 0, nomeBytes, 0, len);
        dos.write(nomeBytes);

        dos.writeInt(this.id);

        return baos.toByteArray();
    }

    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);

        byte[] nomeBytes = new byte[40];
        dis.read(nomeBytes);
        this.nome = new String(nomeBytes, StandardCharsets.UTF_8).trim();

        this.id = dis.readInt();
    }

    public static int hash(String nome) {
        // Simples função de hash baseada em caracteres
        int hash = 7;
        for (int i = 0; i < nome.length(); i++) {
            hash = hash * 31 + nome.charAt(i);
        }
        return Math.abs(hash);
    }
}
