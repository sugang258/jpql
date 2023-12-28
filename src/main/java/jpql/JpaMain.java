package jpql;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf =  Persistence.createEntityManagerFactory("hello");

        EntityManager em =  emf.createEntityManager();

        EntityTransaction tx =  em.getTransaction();
        tx.begin();

        try {

            Team team = new Team();
            em.persist(team);

            Member member = new Member();
            member.setUsername("관리자1");
            member.setTeam(team);
            em.persist(member);

            Member member1 = new Member();
            member1.setUsername("관리자2");
            member1.setTeam(team);
            em.persist(member1);

            em.flush();
            em.clear();

            /*
                가급적 묵시적 조인 대신에 명시적 조인 사용
                조인은 SQL 튜닝에 중요 포인트
                묵시적 조인은 조인이 일어나는 상황을 한눈에 파악하기 어려움
             */

            String query =
                    "select m.username From Team t join t.members m";
            List<String> result = em.createQuery(query, String.class)
                    .getResultList();

            for(Object s : result) {
                System.out.println(s);
            }

            tx.commit();
        }catch (Exception e) {
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();

    }
}
