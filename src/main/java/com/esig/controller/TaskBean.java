package com.esig.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.esig.dao.TaskDAO;
import com.esig.model.Situacao;
import com.esig.model.Task;
import com.esig.util.Messages;

@ManagedBean(name="taskBean")
@RequestScoped()
public class TaskBean {
    private Task task = new Task();
    private String filtroTitulo;
    private String filtroResponsavel;
    private Integer filtroNumero;
    private String filtroSituacao;
    TaskDAO taskDAO = new TaskDAO();
    
    public List<Task> buscaTasks(){
        List<Task> tasks = new ArrayList<>();
        tasks = taskDAO.bucarTodos();

        if (filtroTitulo != null && !filtroTitulo.isEmpty()) {
            tasks.removeIf(t -> !t.getTitulo().toLowerCase().contains(filtroTitulo.toLowerCase()));
        }
        if (filtroResponsavel != null && !filtroResponsavel.isEmpty()) {
            tasks.removeIf(t -> !t.getResponsavel().toLowerCase().contains(filtroResponsavel.toLowerCase()));
        }
        if (filtroNumero != null) {
            tasks.removeIf(t -> !t.getId().equals(filtroNumero));
        }
        if (filtroSituacao != null && !filtroSituacao.isEmpty()) {
            tasks.removeIf(t -> !t.getSituacao().toString().equals(filtroSituacao));
        }

        return tasks;
    }
    
    public String salvarTask(){
        task.setDataCriacao(new Date());
        task.setSituacao(Situacao.EM_ANDAMENTO);
        taskDAO.salvar(task);
        task = new Task();
        Messages.info("Tarefa salva com sucesso");
        return "";
    }
    
    public String removerTask(Long id){
        taskDAO.deletar(id);
        return "";
    }
    
    public String editarTask(Long id){
        task = taskDAO.buscarPorId(id);
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        sessionMap.put("task", task);
        return "editar.xhtml?faces-redirect=true";
    }
    
    public String atualizarTask(Task task){
        taskDAO.atualizar(task);
        return "index.xhtml?faces-redirect=true";
    }
    
    public String concluirTask(Task task){
        if(task.getSituacao()!=Situacao.CONCLUIDA) {
            task.setSituacao(Situacao.CONCLUIDA);
            task.setDataConclusao(new Date());
        }
        taskDAO.atualizar(task);
        return "";
    }
    
    public String detalharTask(Long id){
    	task = taskDAO.buscarPorId(id);
        return null;
    }
    
    public String cancelar(){
        return "index.xhtml?faces-redirect=true";
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public TaskDAO getTaskDAO() {
        return taskDAO;
    }

    public void setTaskDAO(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    // Getters e Setters dos filtros
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

    public Integer getFiltroNumero() {
        return filtroNumero;
    }

    public void setFiltroNumero(Integer filtroNumero) {
        this.filtroNumero = filtroNumero;
    }

    public String getFiltroSituacao() {
        return filtroSituacao;
    }

    public void setFiltroSituacao(String filtroSituacao) {
        this.filtroSituacao = filtroSituacao;
    }
}
