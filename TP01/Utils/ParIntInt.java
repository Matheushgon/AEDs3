/*
Esta classe representa um objeto para uma entidade
que será armazenado em uma árvore B+

Neste caso em particular, este objeto é representado
por dois números inteiros para que possa conter
relacionamentos entre dois IDs de entidades quaisquer
 
Implementado pelo Prof. Marcos Kutova
v1.0 - 2021
*/

package TP01.Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ParIntInt implements TP01.Utils.RegistroArvoreBMais<ParIntInt> {

  private int idSerie;
  private int idEpisodio;
  private short TAMANHO = 8;

  public ParIntInt() {
    this(-1, -1);
  }

  public ParIntInt(int n1) {
    this(n1, -1);
  }

  public ParIntInt(int n1, int n2) {
    try {
      this.idSerie = n1;
      this.idEpisodio = n2;
    } catch (Exception ec) {
      ec.printStackTrace();
    }
  }

  @Override
  public ParIntInt clone() {
    return new ParIntInt(this.idSerie, this.idEpisodio);
  }

  public short size() {
    return this.TAMANHO;
  }

  public int compareTo(ParIntInt chave) {
    if (this.idSerie != chave.idSerie)
      return this.idSerie - chave.idSerie;
    else
      // Só compara os valores de Num2, se o Num2 da busca for diferente de -1
      // Isso é necessário para que seja possível a busca de lista
      return this.idEpisodio == -1 ? 0 : this.idEpisodio - chave.idEpisodio;
  }

  public String toString() {
    return String.format("%3d", this.idSerie) + ";" + String.format("%-3d", this.idEpisodio);
  }

  public byte[] toByteArray() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);
    dos.writeInt(this.idSerie);
    dos.writeInt(this.idEpisodio);
    return baos.toByteArray();
  }

  public void fromByteArray(byte[] ba) throws IOException {
    ByteArrayInputStream bais = new ByteArrayInputStream(ba);
    DataInputStream dis = new DataInputStream(bais);
    this.idSerie = dis.readInt();
    this.idEpisodio = dis.readInt();
  }

  public int getIdSerie() {
      return this.idSerie;
  }

  public int getIdEpisodio() {
      return this.idEpisodio;
  }
}