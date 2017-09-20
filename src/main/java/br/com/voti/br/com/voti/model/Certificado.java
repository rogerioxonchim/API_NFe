package br.com.voti.br.com.voti.model;

import java.time.LocalDate;

public class Certificado {

    public static final String WINDOWS       = "windows";
    public static final String ARQUIVO       = "arquivo";
    public static final String ARQUIVO_BYTES = "arquivo_bytes";
    public static final String A3            = "a3";

    private String    nome;
    private LocalDate vencimento;
    private Long      diasRestantes;
    private String    arquivo;
    private byte[]    arquivoBytes;
    private String    senha;
    private String    tipo;
    private String    dllA3;
    private String    marcaA3;
    private boolean   ativarProperties = false;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getVencimento() {
        return vencimento;
    }

    public void setVencimento(LocalDate vencimento) {
        this.vencimento = vencimento;
    }

    public Long getDiasRestantes() {
        return diasRestantes;
    }

    public void setDiasRestantes(Long diasRestantes) {
        this.diasRestantes = diasRestantes;
    }

    public String getArquivo() {
        return arquivo;
    }

    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
    }

    public byte[] getArquivoBytes() {
        return arquivoBytes;
    }

    public void setArquivoBytes(byte[] arquivoBytes) {
        this.arquivoBytes = arquivoBytes;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDllA3() {
        return dllA3;
    }

    public void setDllA3(String dllA3) {
        this.dllA3 = dllA3;
    }

    public String getMarcaA3() {
        return marcaA3;
    }

    public void setMarcaA3(String marcaA3) {
        this.marcaA3 = marcaA3;
    }

    public boolean isAtivarProperties() {
        return ativarProperties;
    }

    public void setAtivarProperties(boolean ativarProperties) {
        this.ativarProperties = ativarProperties;
    }
}
