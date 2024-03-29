package jpql;

import Entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class App 
{
    public static void main( String[] args )
    {
        EntityManagerFactory emf= Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx= em.getTransaction();
        tx.begin();

        try{

            Member member = new Member();
            member.setName("member1");
            em.persist(member);


            tx.commit();

        }catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }

        em.close();
        emf.close();

    }
}
