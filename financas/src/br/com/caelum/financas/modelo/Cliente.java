package br.com.caelum.financas.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String nome;
	private String profissao;
	private String endereco;

	/*
	 * A anotacao imediatamente abaixo garante a ida e a volta da relacao OneToOne,
	 * ou seja, uma conta so' pode ser associada a um unico cliente e um clinete so'
	 * pode ser associado a uma unica conta
	 * 
	 * Esse comportamento so' funciona na hora da criacao da database (do schema).
	 * Se as tabelas ja foram criadas anteriormente, este comportamente nao se
	 * aplicara
	 */
	@JoinColumn(unique = true)
	@OneToOne
	private Conta conta;

	public Cliente() {
		super();
	}

	public Cliente(String nome, String profissao, String endereco) {
		super();
		this.nome = nome;
		this.profissao = profissao;
		this.endereco = endereco;
	}

	public Cliente(String nome, String profissao, String endereco, Conta conta) {
		super();
		this.nome = nome;
		this.profissao = profissao;
		this.endereco = endereco;
		this.conta = conta;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getProfissao() {
		return profissao;
	}

	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}
}
