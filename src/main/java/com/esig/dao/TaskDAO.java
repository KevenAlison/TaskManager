package com.esig.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.esig.model.JPAUtil;
import com.esig.model.Task;

public class TaskDAO {
	
	public void salvar(Task task) {
		EntityManager entity = JPAUtil.getEntityManagerFactory().createEntityManager();
		entity.getTransaction().begin();
		entity.persist(task);
		entity.getTransaction().commit();
		entity.close();
	}
	
	public void atualizar(Task task) {
		EntityManager entity = JPAUtil.getEntityManagerFactory().createEntityManager();
		entity.getTransaction().begin();
		entity.merge(task);
		entity.getTransaction().commit();
		entity.close();
	}
	
	public Task buscarPorId(Long id) {
		EntityManager entity = JPAUtil.getEntityManagerFactory().createEntityManager();
		Task t = new Task();
		t = entity.find(Task.class, id);
		entity.close();
		return t;
	}
	
	public List<Task> bucarTodos() {
		EntityManager entity = JPAUtil.getEntityManagerFactory().createEntityManager();
		List<Task> listaTasks = new ArrayList<>();
		Query q = entity.createQuery("SELECT t FROM Task t ORDER BY t.situacao, t.prioridade DESC, t.deadline, t.dataCriacao");
		listaTasks = q.getResultList();
		entity.close();
		return listaTasks;
	}
	
	public void deletar(Long id) {
		EntityManager entity = JPAUtil.getEntityManagerFactory().createEntityManager();
		entity.getTransaction().begin();
		Task task = entity.find(Task.class, id);
        if (task != null) {
            entity.remove(task);
        }
		entity.getTransaction().commit();
		entity.close();
	}
}
