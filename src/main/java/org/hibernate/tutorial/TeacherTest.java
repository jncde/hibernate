package org.hibernate.tutorial;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.tutorial.hbm.Teacher;


public class TeacherTest {

    public static void main(String[] args){

      Teacher teacher = new Teacher ();
      teacher.setId (1);
      teacher.setName ("t1");
      teacher.setTitle ("middle class");

      Configuration cfg = new AnnotationConfiguration ();

      SessionFactory sf = cfg.configure ().buildSessionFactory ();
      Session session = sf.openSession ();
      session.beginTransaction ();
      session.save (teacher);

      session.getTransaction ().commit ();
      session.close ();
      sf.close ();;


    }
}
