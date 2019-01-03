package com.cts.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.cts.entity.Book;
import com.cts.entity.Subject;
import com.cts.util.JPAUtil;

public class HibernateDao {
	
	public void add(Object object) {
		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction txn = em.getTransaction();
        txn.begin();
        em.persist(object);
        txn.commit();
		//JPAUtil.shutdown();
	}
	
	public void delete(Object object) {
		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction txn = em.getTransaction();
        txn.begin();
        em.remove(em.merge(object));
        txn.commit();
		//JPAUtil.shutdown();
	}
	
	public Book findBook(long id) {
		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		return em.find(Book.class, id);
	}
	
	public Subject findSubject(long id) {
		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		return em.find(Subject.class, id);
	}
	
	public List<Subject> getAllSubjects(){
		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		return em.createQuery("FROM Subject s").getResultList();
	}
	
	public List<Book> getAllBooksSortByTitle(){
		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		return em.createQuery("FROM Book b order by b.title").getResultList();
	}
	
	public List<Subject> getAllSubjectsSortBySubTitle(){
		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		return em.createQuery("FROM Subject s order by s.subtitle").getResultList();
	}
	
	public List<Book> getAllBooksSortByPublishDate(){
		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		return em.createQuery("FROM Book b order by b.publishDate").getResultList();
	}

}
