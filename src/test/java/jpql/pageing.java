package jpql;


import Entity.Member;
import org.junit.Test;

import javax.persistence.*;
import java.util.List;


public class pageing {

    @Test
    public void Example(){

        EntityManagerFactory emf= Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx= em.getTransaction();
        tx.begin();

        try{

           List<Member> result= em.createQuery("select m from Member m  order by m.age desc" ,Member.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList();

            System.out.println("result size=" + result.size());

            for(Member member1 : result){
                System.out.println("member1 = " + member1.toString());
            }


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
