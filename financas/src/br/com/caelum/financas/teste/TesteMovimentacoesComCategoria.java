package br.com.caelum.financas.teste;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;

import javax.persistence.EntityManager;

import br.com.caelum.financas.modelo.Categoria;
import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.modelo.Movimentacao;
import br.com.caelum.financas.modelo.TipoMovimentacao;
import br.com.caelum.financas.util.JPAUtil;

public class TesteMovimentacoesComCategoria {

	public static void main(String[] args) {

		Categoria categoria1 = new Categoria("Viagem");
		Categoria categoria2 = new Categoria("Negocios");

		Conta conta = new Conta();
		conta.setId(4);

		Movimentacao movimentacao1 = new Movimentacao(new BigDecimal("100.0"), TipoMovimentacao.SAIDA,
				Calendar.getInstance(), "Viagem a SP", conta, Arrays.asList(categoria1, categoria2));

		Movimentacao movimentacao2 = new Movimentacao(new BigDecimal("300.0"), TipoMovimentacao.SAIDA,
				Calendar.getInstance(), "Viagem a RJ", conta, Arrays.asList(categoria1, categoria2));

		EntityManager em = new JPAUtil().getEntityManager();
		em.getTransaction().begin();
		em.persist(categoria1);
		em.persist(categoria2);
		em.persist(movimentacao1);
		em.persist(movimentacao2);
		em.getTransaction().commit();
		
		em.close();
	}
}
