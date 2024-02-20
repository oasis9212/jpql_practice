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
import java.util.Collection;
import java.util.List;

@Transactional
public class 경로_표현식 {



    @Test
    public void ex1(){

//        select m.username -> 상태 필드 단순히 값을 저장하기 위한 필드.
//        from Member m
//        join m.team t -> 단일 값 연관 필드   연관관계를 위한 필드.
//        join m.orders o -> 컬렉션 값 연관 필드
//        where t.name = '팀A

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
            member1.setName("member2");
            member1.setTeam(t);
            em.persist(member1);


            em.flush();
            em.clear();
            // 단일 값 연관 경로
            String query =" select m.team From Member m";  // 묵시적인 내부 조인이 일어남.  XX 좀 좋지않다.

            //컬랙션 연관경로  1:n 이라서 탐색 자체가 안된다.
            query =" select t.memberList From Team t";

            Collection result = em.createQuery(query, Collection.class).getResultList();
            System.out.println("result :: "+ result);


            // from 절에 조인을 시작하는 방법 밖에 없다.
            query =" select m.name From Team t join t.memberList m";
            List<String> result2 = em.createQuery(query, String.class).getResultList();
            for (String i : result2){
                System.out.println("resutl2 :: "+ i);
            }


            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
            tx.rollback();
        }finally {
            em.close();
        }

        em.close();
        emf.close();
    }


//    select o.member.team from Order o -> 성공
// select t.members from Team -> 성공  좋지 않는 방법이다.
// select t.members.username from Team t -> 실패
// select m.username from Team t join t.members m -> 성공
}
