package com.cleanup.todoc.util;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.data.ProjectDao;
import com.cleanup.todoc.data.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class CleanupRoomDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
    public abstract ProjectDao projectDao();
    public static final int NUMBER_OF_THREADS = 4;

    private static volatile CleanupRoomDatabase INSTANCE;

    public static final ExecutorService databaseWriterExecutor
            = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static CleanupRoomDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (CleanupRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CleanupRoomDatabase.class, "cleanup_database")
                            .addCallback(sRoomDatabaseCallback)

                            .build();
                    databaseWriterExecutor.execute(() ->{
                        TaskDao taskDao = INSTANCE.taskDao();
                        ProjectDao projectDao = INSTANCE.projectDao();

                        taskDao.deleteAllTasks();
                        projectDao.deleteAllProjects();

                        projectDao.insert(new Project( 0,"Projet Tartampion", 0xFFEADAD1));
                        projectDao.insert(new Project( 0, "Projet Lucidia", 0xFFB4CDBA));
                        projectDao.insert(new Project( 0,"Projet Circus", 0xFFA3CED2));
                        Log.d("datalog", "PROJECTS INSERTED");

                        taskDao.insert(new Task( 0, 1, "Changer eau dans bombones", 1634854145));
                        taskDao.insert(new Task( 0, 3, "Laver hangar", 1834854145));
                    });
                }
            }
        }
        return INSTANCE;
    }

    public static final RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);


                    Log.d("TAG", "onCreate: CleanupRoomDatabase");


                }
            };
}
