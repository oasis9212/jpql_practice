package jpql;


import Entity.Member;
import org.junit.Test;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;


public class Example {

    @Test
    public void collectionTypeExample(){

        EntityManagerFactory emf= Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx= em.getTransaction();
        tx.begin();

        try{

            Member member = new Member();
            member.setName("member1");
            member.setAge(19);
            em.persist(member);
            // 반환 타입이 명활할때
            TypedQuery<Member> mqery= em.createQuery("select m from Member m",Member.class);
            TypedQuery<String> mqery2= em.createQuery("select m.name from Member m",String.class);
            // 반환타입이 명확하지 않을때
            Query mqery3= em.createQuery("select m.name,m.age from Member m");


            // 반환이 리스트로
            List<Member> memberList= mqery.getResultList();
            // 반환이 하나일때  결과값이 하나일때만 써라  왠만하면 안쓸것이다.
           // Member result= mqery3.getSingleResult();


            // 파라미터 삽입
            TypedQuery<Member> mqeryParam= em.createQuery("select m from Member m where m.name = :name",Member.class);
            mqeryParam.setParameter("name","memeber1");
            memberList= mqeryParam.getResultList();



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
