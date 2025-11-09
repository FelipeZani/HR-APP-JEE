package org.employee.dea;

import java.util.List;

import org.employee.model.Post;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.util.HibernateUtil;

public class PostDAO {

    public void addPost(Post post) {

        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            session.beginTransaction();
            session.persist(post);
            session.getTransaction().commit();
        } catch (Exception e) {
          e.printStackTrace();
        }

    }

    public List<Post> getPostList() {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            List<Post> postsList = session.createQuery("from Post", Post.class).list();

            if (postsList.size() <= 0) {
                throw new Exception("Empty employee list at getPostList() function.");
            }

            return postsList;

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }

    }

    public Post getPostByLabel(String postLabel) throws Exception {
        if (postLabel == null || postLabel.equals("")) {
            throw new Exception("PostLabel is equal to null or it s empty");
        }
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            String hql = "from Post p where p.label = :postLabel";

            Query<Post> query = session.createQuery(hql, Post.class);
            query.setParameter("postLabel", postLabel);

            Post post = query.uniqueResult();

            return post;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
