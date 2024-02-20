package jpql;


import Entity.Member;
import org.junit.Test;

import javax.persistence.*;
import java.util.List;


public class select {

    @Test
    public void Example(){

        EntityManagerFactory emf= Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx= em.getTransaction();
        tx.begin();

        try{

            Member member = new Member();
            member.setName("member1");
            member.setAge(19);
            em.persist(member);

            em.flush();
            em.clear();

            List<Member> mqery= em.createQuery("select m from Member m",Member.class).getResultList();

            Member membered= mqery.get(0);

            membered.setAge(20);  // 업데이트 된다.




            tx.commit();

        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        em.close();
        emf.close();
    }


    @Test
    public void Example2(){

        EntityManagerFactory emf= Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx= em.getTransaction();
        tx.begin();

        try{

           List<Object[]> resultlist = em.createQuery("select m.name, m.age from Member m").getResultList();
           Object[] reuslt = resultlist.get(0);

            System.out.println("name :: " +  reuslt[0]);
            System.out.println("age ::" +  reuslt[1]);

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
