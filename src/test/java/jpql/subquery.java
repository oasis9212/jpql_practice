package jpql;


import Entity.Member;
import Entity.Team;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class subquery {

    @Test
    public void Subquery(){

        EntityManagerFactory emf= Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx= em.getTransaction();
        tx.begin();

        try{
            // from subquery 불가능
            // 조인으로 해결하는것이 좋다.
            String query= "select m from (select m.age from Member m) as mm ";  // 이거 자체가 불가능


            List<Member> result= em.createQuery(query ,Member.class).getResultList();



            tx.commit();

        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        em.close();
        emf.close();
    }





}
