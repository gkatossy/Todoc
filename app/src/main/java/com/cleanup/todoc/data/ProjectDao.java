package com.cleanup.todoc.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cleanup.todoc.model.Project;

import java.util.List;

@Dao
public interface ProjectDao {

    // Adding CRUD to Project
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Project[] project);

    @Delete()
    void delete(Project project);

    @Query("DELETE FROM project_table")
    void deleteAllProjects();

    @Query("SELECT * FROM project_table ORDER BY name ASC")
    LiveData<List<Project>> getProjects();
}
