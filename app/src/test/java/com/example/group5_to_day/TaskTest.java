package com.example.group5_to_day;

import com.example.group5_to_day.models.Task;

import org.junit.Test;

import static org.junit.Assert.*;

public class TaskTest {
    @Test
    public void does_tostring_of_task_return_title_of_task() {
        //ARRANGE
        String title = "Title";
        Task task = new Task(title, "Description", "2021-01-01 01:01:01", "Owner", "Urgent");
        String expected = task.getTitle() + "  " + task.getLabel() + "\nDeadline:" + task.getDeadline();
        //ACT
        String result = task.toString();
        //ASSERT
        assertEquals(result, expected);
    }
}
