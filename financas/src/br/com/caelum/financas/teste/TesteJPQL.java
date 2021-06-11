package br.com.caelum.financas.teste;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.modelo.Movimentacao;
import br.com.caelum.financas.modelo.TipoMovimentacao;
import br.com.caelum.financas.util.JPAUtil;

/*
 * JPQL standos for Java Persistence Query Language
 * 
 * Linguagem de query para acessar um banco relacional (SQL), porem utilizando sintaxe de CLASSES e OBJETOS Java
 */
public class TesteJPQL {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		EntityManager em = new JPAUtil().getEntityManager();

		em.getTransaction().begin();

		/*
		 * Estamos acessando o objeto "Movimentacao". Se a entidade (tabela) que
		 * representa o objeto Movimentacao tivesse outro nome, como, por exemplo,
		 * "tab_mov", a query (jpql) abaixo funcionaria da mesma forma que ja funciona
		 */
		String jpqlSintaxe1 = "select m from Movimentacao m where m.conta.id = 1";
		Query query1 = em.createQuery(jpqlSintaxe1);
		List<Movimentacao> resultList1 = query1.getResultList();
		resultList1.forEach(System.out::println);

		Conta conta = new Conta();
		conta.setId(1);
		String jpqlSintaxe2 = "select m from Movimentacao m where m.conta = :pConta and m.tipo = :pTipo order by m.valor desc";
		Query query2 = em.createQuery(jpqlSintaxe2);
		query2.setParameter("pConta", conta);
		query2.setParameter("pTipo", TipoMovimentacao.SAIDA);
		List<Movimentacao> resultList2 = query2.getResultList();
		for (Movimentacao movimentacao : resultList2) {
			System.out.println(
					"Descricao: " + movimentacao.getDescricao() + ", Conta.id: " + movimentacao.getConta().getId());
		}

		em.getTransaction().commit();
		em.close();
	}
}
