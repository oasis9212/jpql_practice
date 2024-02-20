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
public class JQPL_another {

    @Test
    public void another(){

        EntityManagerFactory emf= Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx= em.getTransaction();
        tx.begin();

        try{
            // from subquery 불가능
            // 조인으로 해결하는것이 좋다. 
            // jqpl 타입 효현
            // 문자: 'Hello', 'she's'
            // 숫자 : 10L , 10D, 10F
            // bool : true, false
            // Enum : jpabook.MemberType.Admin (패키지명 포함)
            // 엔티티 : TYPE(m) = Member (상속 관계 사용)


            // 기본적인 표준 sql 문법은 다지원함 ex) exist / not null / like
            Team t= new Team();
            t.setName("teamA");
            em.persist(t);

            Member member= new Member();
            member.setAge(12);
            member.setName("member1");
            member.setMemberType(MemberType.admin);
            member.setTeam(t);
            em.persist(member);

            em.flush();
            em.clear();



            String query= "select m.name, 'HEllo', true from Member m "+
                          "where m.memberType = :param";


            List<Object[]> result = em.createQuery(query).setParameter("param",MemberType.admin).getResultList();

            for(Object[] objects: result){
                System.out.println("objects = :" + objects[0]);
                System.out.println("objects = :" + objects[1]);
                System.out.println("objects = :" + objects[2]);
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
