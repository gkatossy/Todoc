package com.cleanup.todoc.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.data.ProjectDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.util.CleanupRoomDatabase;

import java.util.List;

public class ProjectRepository {
    private ProjectDao projectDao;
    private LiveData<List<Project>> projects;

    public ProjectRepository(ProjectDao projectDao) {
    }

    public ProjectRepository(Application application) {
        CleanupRoomDatabase database = CleanupRoomDatabase.getInstance(application);
        projectDao = database.projectDao();

        projects = projectDao.getAllProjects();
    }

    public LiveData<List<Project>> allProjects() {
        return projects;
    }
    public void insert(Project project) {
        CleanupRoomDatabase.databaseWriterExecutor.execute(() -> {
            projectDao.insert(project);
        });
        Log.d("TAGRepo", "insert: ProjectRepository");
    }

    public void update(Project project) {
        CleanupRoomDatabase.databaseWriterExecutor.execute(() -> {
            projectDao.update(project);
        });
    }

    public void delete(Project project) {
        CleanupRoomDatabase.databaseWriterExecutor.execute(() -> {
            projectDao.delete(project);
        });
    }

    public void deletAllProjects() {
        CleanupRoomDatabase.databaseWriterExecutor.execute(() -> {
            projectDao.deleteAllProjects();
        });
    }
}
