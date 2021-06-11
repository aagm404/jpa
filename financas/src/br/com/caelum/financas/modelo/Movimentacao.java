package br.com.caelum.financas.modelo;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQuery(query="select sum(m.valor) from Movimentacao m where m.conta = :pConta"
		+ " and m.tipo = :pTipo"
		+ " order by m.valor desc", name="SomaPorTipo")
public class Movimentacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private BigDecimal valor;

	@Enumerated(EnumType.STRING)
	private TipoMovimentacao tipo;

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar data;

	private String descricao;

	@ManyToOne
	private Conta conta;

	// Em relacionamentos "Many to many", a JPA cria uma tabela intermediaria,
	// chamada de "Tabela de Relacionamento", que armazena somente o id das colunas
	// relacionadas
	@ManyToMany
	private List<Categoria> categorias;

	public Movimentacao() {
		super();
	}

	public Movimentacao(BigDecimal valor, TipoMovimentacao tipo, Calendar data, String descricao) {
		super();
		this.valor = valor;
		this.tipo = tipo;
		this.data = data;
		this.descricao = descricao;
	}

	public Movimentacao(BigDecimal valor, TipoMovimentacao tipo, Calendar data, String descricao, Conta conta,
			List<Categoria> categorias) {
		super();
		this.valor = valor;
		this.tipo = tipo;
		this.data = data;
		this.descricao = descricao;
		this.conta = conta;
		this.categorias = categorias;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public TipoMovimentacao getTipo() {
		return tipo;
	}

	public void setTipo(TipoMovimentacao tipo) {
		this.tipo = tipo;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	@Override
	public String toString() {
		return "Movimentacao [id=" + id + ", valor=" + valor + ", tipo=" + tipo + ", data=" + data + ", descricao="
				+ descricao + ", conta=" + conta + ", categorias=" + categorias + "]";
	}
}
