package br.com.caelum.financas.modelo.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.modelo.TipoMovimentacao;
import br.com.caelum.financas.util.JPAUtil;

public class MovimentacaoDao {

	private EntityManager em;

	public MovimentacaoDao() {
		this.em = new JPAUtil().getEntityManager();
	}

	public MovimentacaoDao(EntityManager em) {
		this.em = em;
	}

	public BigDecimal getSomaPorTipo(TipoMovimentacao tipoMovimentacao, Conta conta) {

		String jpql = "select sum(m.valor) from Movimentacao m where m.conta = :pConta" 
				+ " and m.tipo = :pTipo"
				+ " order by m.valor desc";

		Query query = em.createQuery(jpql);
		query.setParameter("pConta", conta);
		query.setParameter("pTipo", tipoMovimentacao);

		return (BigDecimal) query.getSingleResult();
	}

	public Double getMediaPorTipo(TipoMovimentacao tipoMovimentacao, Conta conta) {
		String jpql = "select avg(m.valor) from Movimentacao m where m.conta = :pConta" 
				+ " and m.tipo = :pTipo"
				+ " order by m.valor desc";

		Query query = em.createQuery(jpql);
		query.setParameter("pConta", conta);
		query.setParameter("pTipo", tipoMovimentacao);

		return (Double) query.getSingleResult();
	}

	public BigDecimal getMaxPorTipo(TipoMovimentacao tipoMovimentacao, Conta conta) {
		String jpql = "select max(m.valor) from Movimentacao m where m.conta = :pConta" 
				+ " and m.tipo = :pTipo"
				+ " order by m.valor desc";

		Query query = em.createQuery(jpql);
		query.setParameter("pConta", conta);
		query.setParameter("pTipo", tipoMovimentacao);

		return (BigDecimal) query.getSingleResult();
	}

	public Long getContagem(Conta conta) {
		String jpql = "select count(m) from Movimentacao m where m.conta = :pConta";

		Query query = em.createQuery(jpql);
		query.setParameter("pConta", conta);

		return (Long) query.getSingleResult();
	}

	public List<Double> getMediasPorDiaETipo(TipoMovimentacao tipoMovimentacao, Conta conta) {
		String jpql = "select avg(m.valor) from Movimentacao m where m.conta=:pConta " 
				+ "and m.tipo = :pTipo "
				+ "group by day(m.data), month(m.data), year(m.data)";

		TypedQuery<Double> query = em.createQuery(jpql, Double.class);
		query.setParameter("pConta", conta);
		query.setParameter("pTipo", tipoMovimentacao);

		return (List<Double>) query.getResultList();
	}
}
