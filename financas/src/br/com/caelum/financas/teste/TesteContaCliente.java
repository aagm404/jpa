package br.com.caelum.financas.teste;

import javax.persistence.EntityManager;

import br.com.caelum.financas.modelo.Cliente;
import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.util.JPAUtil;

public class TesteContaCliente {

	public static void main(String[] args) {
		
		Conta conta  = new Conta();
		conta.setId(2);
		
		Cliente cliente1 = new Cliente("Leonardo", "Professor", "Rua Fulano, 123", conta);
//		Cliente cliente2 = new Cliente("Douglas", "Manobrista", "Rua Fulano, 234", conta);
		
		EntityManager em = new JPAUtil().getEntityManager();
		
		em.getTransaction().begin();
		
		em.persist(cliente1);
//		em.persist(cliente2); - Nao permite outro cliente com a mesma conta
		em.getTransaction().commit();
		
		em.close();
	}
}
