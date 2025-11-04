package org.employee.dea;


import java.util.List;

import org.employee.model.Post;
import org.hibernate.Session;
import org.util.HibernateUtil;




public class PostDAO {



    public void addPost(Post etu){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.persist(etu);
        session.getTransaction().commit();

        session.close();
        

    }
    public List<Post> getPostList(){
       try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            List<Post> postsList = session.createQuery("from Post",Post.class).list();

            if( postsList.size() <= 0){
                throw new Exception("Empty employee list at getPostList() function.");
            }

            return postsList;
            
       } catch (Exception e) {
            e.printStackTrace();
            return null;
        
       }


    }

    
}
