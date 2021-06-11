package br.com.caelum.financas.teste;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.caelum.financas.modelo.Categoria;
import br.com.caelum.financas.modelo.Movimentacao;
import br.com.caelum.financas.util.JPAUtil;

public class TesteMovimentacoesPorCategoria {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		EntityManager em = new JPAUtil().getEntityManager();

		em.getTransaction().begin();

		Categoria categoria = new Categoria();
		categoria.setId(1);
		String jpqlSintaxe = "select m from Movimentacao m join m.categorias c where c = :pCategoria";
		Query query = em.createQuery(jpqlSintaxe);
		query.setParameter("pCategoria", categoria);
		List<Movimentacao> resultList = query.getResultList();
		for (Movimentacao movimentacao : resultList) {
//			System.out.println(movimentacao);
			System.out.println(
					"Descricao: " + movimentacao.getDescricao() + ", Conta.id: " + movimentacao.getConta().getId());
		}

		em.getTransaction().commit();
		em.close();
	}
}
