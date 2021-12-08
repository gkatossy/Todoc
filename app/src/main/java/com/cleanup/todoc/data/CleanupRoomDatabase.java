package com.cleanup.todoc.data;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class CleanupRoomDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
    public abstract ProjectDao projectDao();

    public static Project[] getAllProjects() {
        return Project.getAllProjects();
    }
    private static volatile CleanupRoomDatabase INSTANCE;


    public static CleanupRoomDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (CleanupRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CleanupRoomDatabase.class, "cleanups.db")
                            .addCallback(sRoomDatabaseCallback)

                            .build();
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

                    Project[] allProjects = getAllProjects();

                    for (Project element: allProjects) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("id", element.getId());
                        contentValues.put("name", element.getName());
                        contentValues.put("color", element.getColor());
                        db.insert("project_table", OnConflictStrategy.IGNORE, contentValues);

                    }

                    Log.d("TAG", "onCreate: CleanupRoomDatabase");


                }


            };
}
