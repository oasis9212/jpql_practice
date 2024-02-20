package jpql;


import Entity.Member;
import Entity.Team;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;


public class join {

    @Test
    public void InnerJoin(){

        EntityManagerFactory emf= Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx= em.getTransaction();
        tx.begin();

        try{

            Team t= new Team();
            t.setName("teamA");
            em.persist(t);

            Member member= new Member();
            member.setAge(12);
            member.setName("member1");

            member.setTeam(t);
            em.persist(member);

            em.flush();
            em.clear();

            String query= "select m from Member m inner join m.team t";

           List<Member> result= em.createQuery(query ,Member.class).getResultList();


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



    @Test
    public void OnJoin(){

        EntityManagerFactory emf= Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx= em.getTransaction();
        tx.begin();

        try{

            Team t= new Team();
            t.setName("teamA");
            em.persist(t);

            Member member= new Member();
            member.setAge(12);
            member.setName("member1");

            member.setTeam(t);
            em.persist(member);

            em.flush();
            em.clear();
            String query= "select m from Member m left join m.team t on t.name = 'teamA'";


            List<Member> result= em.createQuery(query ,Member.class).getResultList();


            System.out.println("result size=" + result.size());

            for(Member member1 : result){
                System.out.println("member1 = " + member1.toString());
            }


             query= "select m from Member m left join Team t on m.name = t.name";

            result= em.createQuery(query ,Member.class).getResultList();


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
