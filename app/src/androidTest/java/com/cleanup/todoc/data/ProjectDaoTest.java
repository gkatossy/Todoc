package com.cleanup.todoc.data;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.cleanup.todoc.model.Project;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ProjectDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private CleanupRoomDatabase testdatabase;
    private Project[] projects = Project.getAllProjects();

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
    public void insertAndGetProject() throws InterruptedException {
        List<Project> projects = LiveDataTestUtil.getValue(this.testdatabase.projectDao().getProjects());
        assertTrue(projects.isEmpty());
        this.testdatabase.projectDao().insert(this.projects);

        projects = LiveDataTestUtil.getValue(this.testdatabase.projectDao().getProjects());
        assertEquals(projects.get(0).getName(), this.projects[0].getName());
        assertEquals(projects.get(0).getId(), this.projects[0].getId());
        assertEquals(projects.get(0).getColor(), this.projects[0].getColor());

        assertEquals(projects.get(1).getName(), this.projects[1].getName());
        assertEquals(projects.get(1).getId(), this.projects[1].getId());
        assertEquals(projects.get(1).getColor(), this.projects[1].getColor());

        assertEquals(projects.get(2).getName(), this.projects[2].getName());
        assertEquals(projects.get(2).getId(), this.projects[2].getId());
        assertEquals(projects.get(2).getColor(), this.projects[2].getColor());


    }
}
