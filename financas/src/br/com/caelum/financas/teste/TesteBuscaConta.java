package br.com.caelum.financas.teste;

import java.util.Map;

import javax.persistence.EntityManager;

import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.util.JPAUtil;

public class TesteBuscaConta {

	public static void main(String[] args) {

		/*
		 * O contexto de persistencia do EntityManager abaixo e', por default, do tipo
		 * "TRANSACTION".
		 * 
		 * Isso signifca que, o contexto de persistencia esta' associado a um
		 * EntityManager e so' vai existir entre transacoes, ou seja, entre a chamada
		 * dos metodos "manager.getTransaction().begin();" e
		 * "manager.getTransaction().commit();"
		 */
		EntityManager manager = new JPAUtil().getEntityManager();
		Map<String, Object> properties = manager.getProperties();

		for (Map.Entry<String, Object> property : properties.entrySet()) {
			System.out.println("Key = " + property.getKey() + ", Value = " + property.getValue());
		}

		/* Inicio do contexto de persistencia do objeto manager */
		manager.getTransaction().begin();

		/*
		 * O metodo "find()" da classe "EntityManager" devolve uma instancia de um
		 * objeto do tipo "Conta", no estado "Managed".
		 * 
		 * "Managed" e' um estado da JPA (das entidades da JPA) em que, AUTOMATICAMENTE,
		 * os dados deste objeto em memoria sao sincronizados com os dados do banco de
		 * dados.
		 * 
		 * Isso significa que qualquer alteracao no objeto devolvido, sera' sincronizada
		 * com a entidade contida no banco de dados.
		 * 
		 * O proprio provedor de persistencia (Hibernate, neste caso) faz um "UPDATE" no
		 * banco.
		 */
		Conta conta = manager.find(Conta.class, 1);
		System.out.println(conta);

		/*
		 * O metodo "find()" acima retornou um objeto do tipo Conta, no estado
		 * "Managed". Logo, o "set" abaixo vai ser sincronizado com o banco de dados,
		 * automaticamente.
		 * 
		 * E' importante ressaltar que uma query "UPDATE" vai ser executada pelo
		 * provedor de JPA se, e somente se, as alteracoes feitas aqui, no objeto em
		 * memoria, alterarem o valor dos dados que estao armazenados na entidade. Se eu
		 * chamar um metodo "set()" do objeto em memoria, mas atribuir um valor igual ao
		 * valor que ja' esta' armazenado no BD, nenhuma query "UPDATE" sera' executada.
		 * 
		 * Outro ponto importante e' o fato de que a JPA agrupa todas as alteracoes
		 * feitas no objeto em memoria, antes da declaracao do "commit()", para realizar
		 * uma unica query "UPDATE"
		 * 
		 * Logo, para concluir, a JPA ao fazer o commit abaixo, verifica se o objeto
		 * Java, que foi populado por um retorno de uma consulta (SELECT) no banco de
		 * dados, teve os dados alterados em relacao a entidade que se encotra no bd. Se
		 * houver diferencas entre o objeto e a entidade, na hora do commit, a JPA faz
		 * um unico "UPDATE" para alterar a entidade que esta' salva no banco de dados
		 */

		conta.setTitular("João da Silva");
		conta.setBanco("104 - CAIXA ECONOMICA FEDERAL");
		conta.setAgencia("7532");
		conta.setNumero("45678-9");
		System.out.println(conta);

		manager.getTransaction().commit();
		/* Fim do contexto de persistencia do objeto manager */

		/*
		 * DETACHED:
		 * 
		 * Com o commit realizado acima, todos objetos aqui estao fora do contexto de
		 * persistencia. O estado de um objeto nesta situacao e' "DETACHED". Para tornar
		 * um objeto "DETACHED" para "MANAGED" novamente, e' necessario abrir uma
		 * transacao e utilizar o metodo "merge().
		 * 
		 * DETACHED --> merge() --> MANAGED
		 * 
		 * Tudo o que acontecer daqui ate' o final com o objeto em memoria nao sera'
		 * sincronizado com a entidade armazenada no banco de dados
		 */

		conta.setAgencia("AAAA");
		System.out.println(conta);

		/*
		 * O objeto conta2 abaixo nao esta' no contexto de persistencia (TRANSACTION),
		 * ou seja, e' do tipo "DETACHED". Logo, qualquer alteracao no objeto em memoria
		 * nao sera sincronizada com a entidade no banco de dados
		 */
		Conta conta2 = manager.find(Conta.class, 2);
		System.out.println(conta2);

		conta2.setTitular("Maxwell");
		System.out.println(conta2);

		manager.close();
	}
}
