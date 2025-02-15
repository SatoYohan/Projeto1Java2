package entities;

import java.sql.Date;

public class Evento {

	private int codigoEvento;
    private String nomeEvento;
    private String descEvento;
    private Date dataEvento;
    private int duracaoEvento;
    private String localEvento;
    private int capacidadeMaxima;
    private StatusEvento statusEvento;
    private CategoriaEvento categoriaEvento;
    private float precoEvento;
    private Administrador administrador;

    public Evento() {
    }

    public Evento(int codigoEvento, String nomeEvento, String descEvento, Date dataEvento, int duracaoEvento,
                  String localEvento, int capacidadeMaxima, StatusEvento statusEvento, CategoriaEvento categoriaEvento,
                  float precoEvento, Administrador administrador) {
        this.codigoEvento = codigoEvento;
        this.nomeEvento = nomeEvento;
        this.descEvento = descEvento;
        this.dataEvento = dataEvento;
        this.duracaoEvento = duracaoEvento;
        this.localEvento = localEvento;
        this.capacidadeMaxima = capacidadeMaxima;
        this.statusEvento = statusEvento;
        this.categoriaEvento = categoriaEvento;
        this.precoEvento = precoEvento;
        this.administrador = administrador;
    }

    public int getCodigoEvento() {
        return codigoEvento;
    }

    public void setCodigoEvento(int codigoEvento) {
        this.codigoEvento = codigoEvento;
    }

    public String getNomeEvento() {
        return nomeEvento;
    }

    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
    }

    public String getDescEvento() {
        return descEvento;
    }

    public void setDescEvento(String descEvento) {
        this.descEvento = descEvento;
    }

    public Date getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(Date dataEvento) {
        this.dataEvento = dataEvento;
    }

    public int getDuracaoEvento() {
        return duracaoEvento;
    }

    public void setDuracaoEvento(int duracaoEvento) {
        this.duracaoEvento = duracaoEvento;
    }

    public String getLocalEvento() {
        return localEvento;
    }

    public void setLocalEvento(String localEvento) {
        this.localEvento = localEvento;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public void setCapacidadeMaxima(int capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
    }

    public StatusEvento getStatusEvento() {
        return statusEvento;
    }

    public void setStatusEvento(StatusEvento statusEvento) {
        this.statusEvento = statusEvento;
    }

    public CategoriaEvento getCategoriaEvento() {
        return categoriaEvento;
    }

    public void setCategoriaEvento(CategoriaEvento categoriaEvento) {
        this.categoriaEvento = categoriaEvento;
    }

    public float getPrecoEvento() {
        return precoEvento;
    }

    public void setPrecoEvento(float precoEvento) {
        this.precoEvento = precoEvento;
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }
}
