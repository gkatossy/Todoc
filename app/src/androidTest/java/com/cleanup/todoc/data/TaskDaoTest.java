package com.cleanup.todoc.data;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private CleanupRoomDatabase testdatabase;
    private Project[] projects = Project.getAllProjects();
    private final Task testtask1 = new Task( 1,1, "TestTask1", new Date().getTime());
    private final Task testtask2 = new Task( 2,2, "TestTask2", new Date().getTime());
    private final Task testtask3 = new Task( 3,3, "TestTask3", new Date().getTime());

    @Before
    public void initDatabase() throws  InterruptedException {
        this.testdatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                CleanupRoomDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDatabase() {
        this.testdatabase.close();
    }

    @Test
    public void getTaskIsEmpty() throws InterruptedException {
        this.testdatabase.projectDao().insert(this.projects);

        List<Task> tasks = LiveDataTestUtil.getValue(this.testdatabase.taskDao().getAllTasks());
        assertTrue(tasks.isEmpty());
    }

    @Test
    public void insertAndGetProject() throws InterruptedException {
        this.testdatabase.projectDao().insert(this.projects);
        List<Project> projects = LiveDataTestUtil.getValue(this.testdatabase.projectDao().getProjects());

        this.testdatabase.taskDao().insert(testtask1);
        this.testdatabase.taskDao().insert(testtask2);

        List<Task> tasks = LiveDataTestUtil.getValue(this.testdatabase.taskDao().getAllTasks());
        assertEquals(2, tasks.size());

        tasks = LiveDataTestUtil.getValue(this.testdatabase.taskDao().getAllTasks());
    }

    @Test
    public void deleteProject() throws InterruptedException {
        this.testdatabase.projectDao().insert(this.projects);
        List<Project> projects = LiveDataTestUtil.getValue(this.testdatabase.projectDao().getProjects());

        this.testdatabase.taskDao().insert(testtask1);
        this.testdatabase.taskDao().insert(testtask2);

        List<Task> tasks = LiveDataTestUtil.getValue(this.testdatabase.taskDao().getAllTasks());
        assertEquals(2, tasks.size());
        Task toDelete = tasks.get(1);

        this.testdatabase.taskDao().delete(toDelete);

        tasks = LiveDataTestUtil.getValue(this.testdatabase.taskDao().getAllTasks());
        assertEquals(1, tasks.size());
    }
}
