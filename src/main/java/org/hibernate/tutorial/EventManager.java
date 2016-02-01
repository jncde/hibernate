package org.hibernate.tutorial;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.tutorial.hbm.Event;
import org.hibernate.tutorial.hbm.Person;
import org.hibernate.tutorial.util.HibernateUtil;

public class EventManager {

  public static void main (String[] args) {
    EventManager mgr = new EventManager ();

    long eventID = mgr.createAndStoreEvent ("My Event", new Date ());
    long personId = mgr.createAndStorePerson (18, "mueller", "perter");

    mgr.addPersonToEvent (personId, eventID);

    long eventID2 = mgr.createAndStoreEvent ("My Event2", new Date ());
    long personId2 = mgr.createAndStorePerson (18, "mueller2", "perter2");

    mgr.addPersonToEvent2 (personId2, eventID2);

    HibernateUtil.getSessionFactory ().close ();
  }

  private long createAndStoreEvent (String title,
                                    Date theDate) {
    Session session = HibernateUtil.getSessionFactory ().getCurrentSession ();
    session.beginTransaction ();

    Event theEvent = new Event ();
    theEvent.setTitle (title);
    theEvent.setDate (theDate);
    session.save (theEvent);

    session.getTransaction ().commit ();
    return theEvent.getId ();
  }

  private long createAndStorePerson (int age,
                                     String firstname,
                                     String lastname) {
    Session session = HibernateUtil.getSessionFactory ().getCurrentSession ();
    session.beginTransaction ();

    Person person = new Person ();
    person.setAge (age);
    person.setFirstname (firstname);
    person.setLastname (lastname);

    session.save (person);

    session.getTransaction ().commit ();
    return person.getId ();
  }

  private void addPersonToEvent (Long personId,
                                 Long eventId) {
    Session session = HibernateUtil.getSessionFactory ().getCurrentSession ();
    session.beginTransaction ();

    Person aPerson = (Person) session.load (Person.class, personId);
    Event anEvent = (Event) session.load (Event.class, eventId);
    aPerson.getEvents ().add (anEvent);

    session.getTransaction ().commit ();
  }

  private void addPersonToEvent2 (Long personId,
                                  Long eventId) {
    Session session = HibernateUtil.getSessionFactory ().getCurrentSession ();
    session.beginTransaction ();

    Person aPerson = (Person) session.createQuery ("select p from Person p left join fetch p.events where p.id = :pid")
                                     .setParameter ("pid", personId)
                                     .uniqueResult (); // Eager fetch the collection so we can use it detached
    Event anEvent = (Event) session.load (Event.class, eventId);

    session.getTransaction ().commit ();

    // End of first unit of work

    aPerson.getEvents ().add (anEvent); // aPerson (and its collection) is detached

    // Begin second unit of work

    Session session2 = HibernateUtil.getSessionFactory ().getCurrentSession ();
    session2.beginTransaction ();
    session2.update (aPerson); // Reattachment of aPerson

    session2.getTransaction ().commit ();
  }

}
