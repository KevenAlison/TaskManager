package com.esig.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import com.esig.model.Situacao;
import com.esig.model.Task;
import com.esig.service.TaskService;
import com.esig.util.Messages;
import com.esig.util.NegocioException;

@ManagedBean(name="taskBean")
@RequestScoped
public class TaskBean {
    private Task task = new Task();
    private String filtroTitulo;
    private String filtroResponsavel;
    private Long filtroNumero;
    private String filtroSituacao;
    private TaskService taskService = new TaskService();

    public List<Task> buscaTasks() {
        return taskService.filtrarTasks(filtroTitulo, filtroResponsavel, filtroNumero, filtroSituacao);
    }

    public String salvarTask() {
        try {
            taskService.salvarTask(task);
            task = new Task();
            Messages.info("Tarefa salva com sucesso.");
        } catch (NegocioException e) {
            Messages.error(e.getMessage());
        }
        return "";
    }

    public String removerTask(Long id) {
        try {
            taskService.deletarTask(id);
            Messages.info("Tarefa " + id + " removida com sucesso.");
        } catch (NegocioException e) {
            Messages.error(e.getMessage());
        }
        return "";
    }

    public String editarTask(Long id) {
        try {
            task = taskService.buscarPorId(id);
            Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            sessionMap.put("task", task);
        } catch (NegocioException e) {
            Messages.error(e.getMessage());
        }
        return "editar.xhtml?faces-redirect=true";
    }

    public String atualizarTask(Task task) {
        try {
            taskService.atualizarTask(task);
            Messages.info("Tarefa " + task.getId() + " atualizada com sucesso.");
            return "index.xhtml?faces-redirect=true";
        } catch (NegocioException e) {
            Messages.error(e.getMessage());
            return "";
        }
        
    }

    public String concluirTask(Task task) {
        try {
            if (task.getSituacao() != Situacao.CONCLUIDA) {
                task.setSituacao(Situacao.CONCLUIDA);
                task.setDataConclusao(new Date());
                taskService.atualizarTask(task);
            }
        } catch (NegocioException e) {
            Messages.error(e.getMessage());
        }
        return "";
    }

    public String detalharTask(Long id) {
        try {
            task = taskService.buscarPorId(id);
        } catch (NegocioException e) {
            Messages.error(e.getMessage());
        }
        return null;
    }

    public String cancelar() {
        return "index.xhtml?faces-redirect=true";
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public TaskService getTaskService() {
        return taskService;
    }

    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    public String getFiltroTitulo() {
        return filtroTitulo;
    }

    public void setFiltroTitulo(String filtroTitulo) {
        this.filtroTitulo = filtroTitulo;
    }

    public String getFiltroResponsavel() {
        return filtroResponsavel;
    }

    public void setFiltroResponsavel(String filtroResponsavel) {
        this.filtroResponsavel = filtroResponsavel;
    }

    public Long getFiltroNumero() {
        return filtroNumero;
    }

    public void setFiltroNumero(Long filtroNumero) {
        this.filtroNumero = filtroNumero;
    }

    public String getFiltroSituacao() {
        return filtroSituacao;
    }

    public void setFiltroSituacao(String filtroSituacao) {
        this.filtroSituacao = filtroSituacao;
    }
}
