package br.com.caelum.financas.teste;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.modelo.TipoMovimentacao;
import br.com.caelum.financas.modelo.dao.MovimentacaoDao;
import br.com.caelum.financas.util.JPAUtil;

public class TesteFuncoesJPQL {

	public static void main(String[] args) {
		

		EntityManager em = new JPAUtil().getEntityManager();
		em.getTransaction().begin();
		
		Conta conta = new Conta();
		conta.setId(1);
		
		MovimentacaoDao dao = new MovimentacaoDao(em);
				
		BigDecimal soma = dao.getSomaPorTipo(TipoMovimentacao.ENTRADA, conta);
		Double media = dao.getMediaPorTipo(TipoMovimentacao.SAIDA, conta);
		BigDecimal max = dao.getMaxPorTipo(TipoMovimentacao.ENTRADA, conta);
		Long count = dao.getContagem(conta);
		List<Double> medias = dao.getMediasPorDiaETipo(TipoMovimentacao.ENTRADA, conta);
		
		System.out.println();
		System.out.println("******************************************");
		System.out.println("SOMA: " + soma);
		System.out.println("MEDIA: " + media);
		System.out.println("MAXIMO: " + max);
		System.out.println("CONTAGEM: " + count);
		System.out.println("MEDIA DIA 07/12/2020: " + medias.get(0));
//		System.out.println("MEDIA DIA 08/12/2020: " + medias.get(1));
		System.out.println("******************************************");
		
		em.getTransaction().commit();
		em.close();
	}
}
