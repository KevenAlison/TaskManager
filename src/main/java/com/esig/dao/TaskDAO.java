package com.esig.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.esig.model.JPAUtil;
import com.esig.model.Situacao;
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
	
	public List<Task> buscarTodos(String filtroTitulo, String filtroResponsavel, Long filtroNumero, String filtroSituacao) {
        EntityManager entity = JPAUtil.getEntityManagerFactory().createEntityManager();

        StringBuilder queryBusca = new StringBuilder("SELECT t FROM Task t WHERE 1=1");
        
        if (filtroTitulo != null && !filtroTitulo.isEmpty()) {
        	queryBusca.append(" AND LOWER(t.titulo) LIKE :filtroTitulo");
        }
        if (filtroResponsavel != null && !filtroResponsavel.isEmpty()) {
        	queryBusca.append(" AND LOWER(t.responsavel) LIKE :filtroResponsavel");
        }
        if (filtroNumero != null) {
        	queryBusca.append(" AND t.id = :filtroNumero");
        }
        if (filtroSituacao != null && !filtroSituacao.isEmpty()) {
        	queryBusca.append(" AND t.situacao = :filtroSituacao");
        }

        queryBusca.append(" ORDER BY t.situacao, t.prioridade DESC, t.deadline, t.dataCriacao");

        Query query = entity.createQuery(queryBusca.toString());

        // Define os parâmetros
        if (filtroTitulo != null && !filtroTitulo.isEmpty()) {
            query.setParameter("filtroTitulo", "%" + filtroTitulo.toLowerCase() + "%");
        }
        if (filtroResponsavel != null && !filtroResponsavel.isEmpty()) {
            query.setParameter("filtroResponsavel", "%" + filtroResponsavel.toLowerCase() + "%");
        }
        if (filtroNumero != null) {
            query.setParameter("filtroNumero", filtroNumero);
        }
        if (filtroSituacao != null && !filtroSituacao.isEmpty()) {
            query.setParameter("filtroSituacao", Situacao.valueOf(filtroSituacao));
        }

        List<Task> listaTasks = query.getResultList();
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
