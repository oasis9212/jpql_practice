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
public class entity_param {



    @Test
    public void ex1(){
        EntityManagerFactory emf= Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx= em.getTransaction();
        tx.begin();

        try{
            Team t= new Team();
            t.setName("teamA");
            em.persist(t);


            Member member= new Member();
            member.setName("member1");
            member.setTeam(t);
            em.persist(member);


            em.flush();
            em.clear();

            String query = "select m from Member m where m = :memeber";
                // team 의 왜래키도 가능하다.
            Member findmemeber= em.createQuery(query,Member.class)
                                 .setParameter("member",member)
                                  .getSingleResult();


            // @NamedQuery쿼리 방식. 엔티티에 설정하고 하는 방식.
            // 기동하자마자 바로 파싱하기 때문에 오류를 무조건 잡을수 있다.
            List<Member> result=em.createNamedQuery("Member.findByname")
                    .setParameter("username","맴버1")
                            .getResultList();

            System.out.println(  findmemeber);





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

}
