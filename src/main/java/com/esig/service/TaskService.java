package com.esig.service;

import com.esig.dao.TaskDAO;
import com.esig.model.Situacao;
import com.esig.model.Task;
import com.esig.util.NegocioException;

import java.util.Date;
import java.util.List;

public class TaskService {
    private TaskDAO taskDAO = new TaskDAO();

    public List<Task> filtrarTasks(String filtroTitulo, String filtroResponsavel, Long filtroNumero, String filtroSituacao) {
    	List<Task> tasks = taskDAO.buscarTodos(filtroTitulo, filtroResponsavel, filtroNumero);
    	if (filtroSituacao != null && !filtroSituacao.isEmpty()) {
            tasks.removeIf(t -> !t.getSituacao().getDescricao().equals(filtroSituacao));
        }
    	return tasks;
    	
    }

    public void salvarTask(Task task) throws NegocioException {
    	task.setDataCriacao(new Date());
    	task.setSituacao(Situacao.EM_ANDAMENTO);
        if (task.getDeadline() != null && task.getDeadline().before(task.getDataCriacao())) {
            throw new NegocioException("O deadline da tarefa deve ser posterior à data atual.");
        }
        taskDAO.salvar(task);
    }

    public void atualizarTask(Task task) throws NegocioException {
        if (task.getDeadline() != null && task.getDeadline().before(task.getDataCriacao())) {
            throw new NegocioException("O deadline da tarefa deve ser posterior à data atual.");
        }
        taskDAO.atualizar(task);
    }

    public Task buscarPorId(Long id) throws NegocioException {
        Task task = taskDAO.buscarPorId(id);
        if (task == null) {
            throw new NegocioException("Nenhuma tarefa encontrada para os filtros aplicados.");
        }
        return task;
    }

    public void deletarTask(Long id) throws NegocioException {
        Task task = taskDAO.buscarPorId(id);
        if (task == null) {
            throw new NegocioException("Não foi possível encontrar a tarefa solicitada para exclusao.");
        }
        taskDAO.deletar(id);
    }
}
