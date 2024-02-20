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
public class case_when {

    @Test
    public void case_when_ex1(){

        EntityManagerFactory emf= Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx= em.getTransaction();
        tx.begin();

        try{
            // coalesce 하나씩 조회해서 null 아니라면 반환
            // nullif : 두 값이 같으면 null , 다르다면 첫번째 값 반환


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



            String query = "select " +
                    "    case " +
                    "        when m.age <= 10 then '학생요금' " +
                    "        when m.age >= 60 then '노인요금' " +
                    "        else '일반요금' " +
                    "    end " +
                    "from Member m";


            List<String> result = em.createQuery(query, String.class).getResultList();


            for(String i: result){
                System.out.println("objects = :" + i);

            }
            //  coalesce 형식
            query ="select  coalesce(m.name, '이름이 없는 회원') from Member m";

            result = em.createQuery(query, String.class).getResultList();
            for(String i: result){
                System.out.println("objects = :" + i);

            }


            //  nullif  형식
            query ="select  nullif(m.name, 'member2') from Member m";

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





}
