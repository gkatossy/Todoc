package com.cleanup.todoc.repository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.ProjectRepository;
import com.cleanup.todoc.repository.TaskRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {

    @Nullable
    private LiveData<List<Project>> currentProjects;

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final Executor executor;

    public TaskViewModel(TaskRepository taskRepository, ProjectRepository projectRepository, Executor executor) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.executor = executor;
    }
    public void init() {
        if (this.currentProjects != null) {
            return;
        }
        currentProjects = projectRepository.allProjects();
    }
    // FOR PROJECTS
    public LiveData<List<Project>> getAllProjects(){
        return this.currentProjects;
    }

    // FOR TASKS
    public LiveData<List<Task>> allTasks(){
        return taskRepository.getTasks();
    }

    public void insert(Task task){
        executor.execute(()->{
            taskRepository.insert(task);
        });
    }

    public void update(Task task){
        executor.execute(()->{
            taskRepository.update(task);
        });
    }

    public void delete(Task task){
        executor.execute(()->{
            taskRepository.delete(task);
        });
    }

    public void deleteAllTasks(){
        executor.execute(()->{
            taskRepository.deleteAllTasks();
        });
    }



}