package com.cleanup.todoc.injection;

import android.app.Application;

import com.cleanup.todoc.repository.ProjectRepository;
import com.cleanup.todoc.repository.TaskRepository;
import com.cleanup.todoc.ui.MainActivity;
import com.cleanup.todoc.util.CleanupRoomDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    public static TaskRepository provideTaskData(Application application) {
        CleanupRoomDatabase dBase = CleanupRoomDatabase.getInstance(application);
        return new TaskRepository(dBase.taskDao());
    }

    public static ProjectRepository provideProjectData(Application application){
        CleanupRoomDatabase dBase = CleanupRoomDatabase.getInstance(application);
        return new ProjectRepository(dBase.projectDao());
    }

    public static Executor provideExecutor(){
        return Executors.newSingleThreadExecutor();
    }

    public static  ViewModelFactory provideViewModelFactory(Application application){
        TaskRepository dataSourceTask = provideTaskData(application);
        ProjectRepository dataSourceProject = provideProjectData(application);
        Executor executor = provideExecutor();
        return new ViewModelFactory(dataSourceTask, dataSourceProject, executor);
    }
}
