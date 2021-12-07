package com.cleanup.todoc.repository;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.data.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskRepository {
    private TaskDao taskDao;
    private LiveData<List<Task>> tasks;

    public TaskRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public LiveData<List<Task>> getTasks() {
        return this.taskDao.getAllTasks();
    }
    public void insert(Task task) {
        taskDao.insert(task);
    }
    public void delete(Task task) {
            taskDao.delete(task);
    }

    public void deleteAllTasks() {
            taskDao.deleteAllTasks();
    }
}
