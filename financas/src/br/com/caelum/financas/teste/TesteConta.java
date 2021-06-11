package br.com.caelum.financas.teste;

import javax.persistence.EntityManager;

import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.util.JPAUtil;

public class TesteConta {

	public static void main(String[] args) {
		/*
		 * NEW -> estado de um objeto que foi criado em memoria pelo uso da keyword
		 * "new" e que nao existe no banco de dados
		 */
		Conta conta1 = new Conta();
		conta1.setTitular("Leonardo");
		conta1.setAgencia("123");
		conta1.setBanco("Caixa Economica");
		conta1.setNumero("456");

		/*
		 * A declaracao abaixa faz o Hibernate lancar uma excecao, no contexto em que se
		 * encontra a esta classe. Acima, demos um "new" em um objeto que nao existe no
		 * banco de dados. Logo, o seu estado e' "NEW". Se setarmos, na mao, o id,
		 * conforme a declaracao abaixo, o estado do objeto em memoria passa a ser do
		 * tipo "DETACHED". Logo, quando chamamos o metodo "persist()", obtemos uma
		 * excessao, pois nao e' possivel excutar o metodo persist para objetos do tipo
		 * "DETACHED". Apenas para objetos de estado "NEW".
		 * 
		 * Se quisermos que o objeto do tipo "DETACHED" vire do tipo "MANAGED", temos de
		 * utilizar o metodo "merge()"
		 * 
		 * NEW --> persist() --> MANAGED DETACHED --> persist() --> [EXCEPTION]
		 * 
		 * NEW --> merge() --> MANAGED DETACHED --> merge() --> MANAGED
		 * 
		 * Como observacao, quando eu chamar o metodo "setId()" abaixo, posso passar o
		 * ID que eu quiser, que o banco de dados, a JPA e o Hibernate vao respeitar a
		 * ordem que se segue no AUTO_INCREMENT
		 */
//		conta.setId(27);

		EntityManager em = new JPAUtil().getEntityManager();

		em.getTransaction().begin();
		em.persist(conta1); // NEW --> persist() --> MANAGED && DETACHED --> persist() --> [EXCEPTION]
//		em.merge(conta); // NEW --> merge() --> MANAGED && DETACHED --> merge() --> MANAGED
		em.getTransaction().commit();

		/*
		 * Testando a transformacao de um objeto "NEW" e de um objeto "DETACHED" em
		 * "MANAGED", utilizando o metodo "merge()";
		 */

		// "NEW"
		Conta conta2 = new Conta();
		conta2.setAgencia("AAAA");
		conta2.setBanco("Banco objeto New");
		conta2.setNumero("00000-0");
		conta2.setTitular("Objeto \"NEW\"");

		Conta conta3 = new Conta();
		conta3.setAgencia("BBBB");
		conta3.setBanco("Banco objeto Detached");
		conta3.setNumero("00000-1");
		conta3.setTitular("Objeto \"DETACHED\"");
		conta3.setId(1250);
		/*
		 * Posso setar o Id com o numero que eu quiser, que o JPA vai respeitar o
		 * AUTO_INCREMENT e garantir que o Id deste objeto seja o numero posterior
		 * subsequente da numeracao do bando de dados
		 */

		em.getTransaction().begin();
		em.merge(conta2);
		em.merge(conta3);
		em.getTransaction().commit();

		em.close();
	}
}
