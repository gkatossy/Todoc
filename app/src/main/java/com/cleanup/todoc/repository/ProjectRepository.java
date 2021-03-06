package com.cleanup.todoc.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.data.ProjectDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.data.CleanupRoomDatabase;

import java.util.List;

public class ProjectRepository {
    private ProjectDao projectDao;

    public ProjectRepository(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    public LiveData<List<Project>> getProjects() {
        return this.projectDao.getProjects();
    }
}
