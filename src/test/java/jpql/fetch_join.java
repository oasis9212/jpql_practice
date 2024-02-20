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
public class fetch_join {



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
            Team t2= new Team();
            t2.setName("teamB");
            em.persist(t2);
            Team t3= new Team();
            t3.setName("teamC");
            em.persist(t3);

            Member member= new Member();
            member.setName("member1");
            member.setTeam(t);
            em.persist(member);

            Member member1= new Member();
            member1.setName("member2");
            member1.setTeam(t);
            em.persist(member1);

            Member member2= new Member();
            member2.setName("member3");
            member2.setTeam(t2);
            em.persist(member2);

            Member member3= new Member();
            member3.setName("member4");
            em.persist(member3);


            em.flush();
            em.clear();
            // 단일 값 연관 경로       n: 1 일경우

            String query = "select m from Member m";   // 만일 이경우면 1+N 문제가 생김
                query = "select m from Member m join fetch m.team";  //

            List<Member> list= em.createQuery(query,Member.class).getResultList();

            for(Member m: list){  // 만일
                System.out.println("memeber =" + m.getName() + " Team??" +m.getTeam().getName());
            }

            // 컬랙션 패치 조인.   1: n 일경우
            // 1: n 일경우는 뻥튀기로 노출된다.
            // distinct 를 추가 한다하더라도 데이터의 결과값은 달라져 중복제거가 정확히 될수 없다고 할수 없다.
            // 같은 팀의 하나로 남기고 나머지는 객체 타입은 삭제.
            query = "select t from Team t  join fetch t.memberList";  //
            List<Team> list2= em.createQuery(query,Team.class).getResultList();
            for(Team team: list2){
                System.out.println("memeber =" + team.getMemberList().size() + " Team??" +team.getName());
                for(Member membered : team.getMemberList()){
                    System.out.println("memebername = "+membered.getName());
                }
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
