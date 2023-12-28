package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf =  Persistence.createEntityManagerFactory("hello");

        EntityManager em =  emf.createEntityManager();

        EntityTransaction tx =  em.getTransaction();
        tx.begin();

        try {

            Member member = new Member();
            member.setUsername("관리자1");
            em.persist(member);

            Member member1 = new Member();
            member1.setUsername("관리자2");
            em.persist(member1);

            em.flush();
            em.clear();

            String query =
                    "select group_concat(m.username) From Member m";
            List<String> result = em.createQuery(query, String.class)
                            .getResultList();

            for(String s : result) {
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
