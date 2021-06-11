package br.com.caelum;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import br.com.caelum.model.Produto;

public class TestaCache {

	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(JpaConfigurator.class);

		EntityManagerFactory entityManagerFactory = ctx.getBean(EntityManagerFactory.class);

		EntityManager em = entityManagerFactory.createEntityManager();
		
		/*
		 * CACHE DE PRIMEIRO NIVEL
		 * 
		 * Definido para o mesmo EntityManager Repare, pelo console, quantas querys
		 * serao executadas para os dois "find()" abaixo
		 */
		Produto produto = em.find(Produto.class, 1);
		Produto produto2 = em.find(Produto.class, 1);

		System.out.println(produto);
		System.out.println(produto2);

		em.close();
		((EntityManager) ctx).close();
	}
}
