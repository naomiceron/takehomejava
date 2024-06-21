package com.talentreef.interviewquestions.takehome.repository;

import com.talentreef.interviewquestions.takehome.models.Widget;
import com.talentreef.interviewquestions.takehome.respositories.WidgetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WidgetRepositoryTests {

    private WidgetRepository widgetRepository;

    @BeforeEach
    public void setUp() {
        widgetRepository = new WidgetRepository();
    }

    @Test
    public void when_findAll_expect_allWidgets() {
        Widget widget1 = Widget.builder().name("Widget1").build();
        Widget widget2 = Widget.builder().name("Widget2").build();
        widgetRepository.save(widget1);
        widgetRepository.save(widget2);

        List<Widget> result = widgetRepository.findAll();

        assertTrue(result.contains(widget1));
        assertTrue(result.contains(widget2));
    }

    @Test
    public void when_findById_expect_correctWidget() {
        Widget widget = Widget.builder().name("Widget1").build();
        widgetRepository.save(widget);

        Widget result = widgetRepository.findById("Widget1").orElse(null);

        assertEquals(widget, result);
    }

    @Test
    public void when_save_expect_widgetCreatedOrUpdated() {
        Widget widget = Widget.builder().name("Widget1").build();
        widgetRepository.save(widget);

        Widget result = widgetRepository.findById("Widget1").orElse(null);

        assertEquals(widget, result);
    }

    @Test
    public void when_deleteById_expect_widgetDeleted() {
        Widget widget = Widget.builder().name("Widget1").build();
        widgetRepository.save(widget);
        widgetRepository.deleteById("Widget1");

        Widget result = widgetRepository.findById("Widget1").orElse(null);

        assertNull(result);
    }
}