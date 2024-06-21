package com.talentreef.interviewquestions.takehome.services;

import com.talentreef.interviewquestions.takehome.models.Widget;
import com.talentreef.interviewquestions.takehome.respositories.WidgetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
@Service
public class WidgetService {

  private final WidgetRepository widgetRepository;

  @Autowired
  private WidgetService(WidgetRepository widgetRepository) {
    Assert.notNull(widgetRepository, "widgetRepository must not be null");
    this.widgetRepository = widgetRepository;
  }

  public List<Widget> getAllWidgets() {
    return widgetRepository.findAll();
  }

  public Widget getWidgetById(String name) {
    return widgetRepository.findById(name)
            .orElseThrow(() -> new RuntimeException("Widget not found"));
  }

  public Widget createOrUpdateWidget(Widget widget) {
    return widgetRepository.save(widget);
  }

  public void deleteWidget(String name) {
    widgetRepository.deleteById(name);
  }

  public Widget updateWidget(Widget widget) {
    return widgetRepository.save(widget);
  }
}
