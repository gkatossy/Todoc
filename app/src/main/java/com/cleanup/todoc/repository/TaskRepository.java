package com.cleanup.todoc.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.data.TaskDao;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.util.CleanupRoomDatabase;

import java.util.List;

public class TaskRepository {
    private TaskDao taskDao;
    private LiveData<List<Task>> tasks;

    public TaskRepository(TaskDao taskDao) {
    }

    public TaskRepository(Application application) {
        CleanupRoomDatabase database = CleanupRoomDatabase.getInstance(application);
        taskDao = database.taskDao();

        tasks = taskDao.getAllTasks();
    }

    public LiveData<List<Task>> getTasks() {
        return tasks;
    }
    public void insert(Task task) {
        CleanupRoomDatabase.databaseWriterExecutor.execute(() -> {
            taskDao.insert(task);
        });
    }

    public void update(Task task) {
        CleanupRoomDatabase.databaseWriterExecutor.execute(() -> {
            taskDao.update(task);
        });
    }
    public void delete(Task task) {
        CleanupRoomDatabase.databaseWriterExecutor.execute(() -> {
            taskDao.delete(task);
        });
    }

    public void deleteAllTasks() {
        CleanupRoomDatabase.databaseWriterExecutor.execute(() -> {
            taskDao.deleteAllTasks();
        });
    }
}
