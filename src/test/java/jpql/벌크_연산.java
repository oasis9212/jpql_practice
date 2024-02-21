package jpql;


import Entity.Member;
import Entity.MemberType;
import Entity.Team;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class 벌크_연산 {

    @Test
    public void 벌크_ex1(){

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




            // 영향 받은 칼럼 횟수
            // 영속성 컨텍스트 무시하고 바로 db 집접 쿼리 날림.
            // 벌크 연상을 먼저 실행이후 영속성 컨텍스트 를 조작.

            // 플러쉬 없더다로 자동 플러쉬 한다.
           int resultcount= em.createQuery("update Member m set m.age= 20").executeUpdate();
            em.flush();
            em.clear();



            System.out.println("memeber1 age"+member.getAge());  // 업데이트 이전 데이터로 남음.
            System.out.println("memeber2 age"+member1.getAge());

            System.out.println(resultcount);

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
