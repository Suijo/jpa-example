package person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {


    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-example");

        EntityManager em = emf.createEntityManager();

        try{
            em.getTransaction().begin();
            for (int i = 0; i < 50; i++){
                if (i % 10 == 0 && i > 0){
                    em.getTransaction().commit();
                    em.getTransaction().begin();
                    em.clear();
                }
                em.persist(Person.randomPerson());
            }
            em.getTransaction().commit();
        }catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
        finally {
            em.close();
        }

        emf.close();


    }
}
