package com.example.group5_to_day;

import com.example.group5_to_day.models.Label;

import org.junit.Test;

import static org.junit.Assert.*;

public class LabelTest {
    @Test
    public void tostring_returns_label_description() {
        //ARRANGE
        String description = "URGENT";
        Label label = new Label("URGENT");
        //ACT
        String result = label.toString();
        //ASSERT
        assertEquals(result, description);
    }

    @Test
    public void tostring_does_not_return_anything_else() {
        //ARRANGE
        String description = "URGENT";
        Label label = new Label("URGENT");
        //ACT
        String result = label.toString();
        //ASSERT
        assertNotEquals(result, "Label");
    }
}