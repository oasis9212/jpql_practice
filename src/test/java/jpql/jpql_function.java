package jpql;


import Entity.Member;
import Entity.MemberType;
import Entity.Team;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class jpql_function {

    @Test
    public void jpql_function_ex1(){

        EntityManagerFactory emf= Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx= em.getTransaction();
        tx.begin();

        try{

            //사용자 정의 함수 호출 할때.
            // select function('group_concat',i.name) from Item i;

            Team t= new Team();
            t.setName("teamA");
            em.persist(t);

            Member member= new Member();
            member.setAge(12);
            member.setName("member1");
            member.setMemberType(MemberType.admin);
            member.setTeam(t);
            em.persist(member);

            Member member1= new Member();
            member1.setAge(12);
            member.setName("member2");
            member1.setTeam(t);
            em.persist(member1);


            em.flush();
            em.clear();
            String query =" select 'a' || 'b' From Member";
            List<String>   result = em.createQuery(query, String.class).getResultList();

            for(String i: result){
                System.out.println("objects = :" + i);

            }

            query =" select loacte('de','abcdefg') From Member";  // 어느 위치에서 시작하는가
            List<Integer>  result2 = em.createQuery(query, Integer.class).getResultList();

            for(Integer i: result2){
                System.out.println("objects = :" + i);

            }


            query =" select size(t.members) From Team t";  // 몇개가 연관관계가 있는가?
            result2 = em.createQuery(query, Integer.class).getResultList();

            for(Integer i: result2){
                System.out.println("objects = :" + i);
            }

            query =" select function('group_concat', m.name) From Member m";  // 몇개가 연관관계가 있는가?
            result = em.createQuery(query, String.class).getResultList();
            for(String i: result){
                System.out.println("objects = :" + i);
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
    public void jpql_function_ex2(){

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
            member.setMemberType(MemberType.admin);
            member.setTeam(t);
            em.persist(member);

            Member member1= new Member();
            member1.setAge(12);
            member.setName("member2");
            member1.setTeam(t);
            em.persist(member1);


            em.flush();
            em.clear();
           String query =" select group_concat(m.name) From Member m";  // 몇개가 연관관계가 있는가?
          List<String>  result = em.createQuery(query, String.class).getResultList();
            for(String i: result){
                System.out.println("objects = :" + i);
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
