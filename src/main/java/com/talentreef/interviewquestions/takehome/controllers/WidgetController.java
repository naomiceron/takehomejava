package com.talentreef.interviewquestions.takehome.controllers;

import com.talentreef.interviewquestions.takehome.models.Widget;
import com.talentreef.interviewquestions.takehome.services.WidgetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping(value = "/v1/widgets", produces = MediaType.APPLICATION_JSON_VALUE)
public class WidgetController {

  private final WidgetService widgetService;

  public WidgetController(WidgetService widgetService) {
    Assert.notNull(widgetService, "widgetService must not be null");
    this.widgetService = widgetService;
  }

  @GetMapping
  public ResponseEntity<List<Widget>> getAllWidgets() {
    return ResponseEntity.ok(widgetService.getAllWidgets());
  }

  @GetMapping("/{name}")
  public ResponseEntity<Widget> getWidgetById(@PathVariable String name) {
    try {
      return ResponseEntity.ok(widgetService.getWidgetById(name));
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping
  public ResponseEntity<Widget> createOrUpdateWidget(@RequestBody Widget widget) {
    return ResponseEntity.ok(widgetService.createOrUpdateWidget(widget));
  }

  @DeleteMapping("/{name}")
  public ResponseEntity<Void> deleteWidget(@PathVariable String name) {
    try {
      widgetService.deleteWidget(name);
      return ResponseEntity.noContent().build();
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PatchMapping
  public ResponseEntity<Widget> updateWidget(@RequestBody Widget widget) {
    return ResponseEntity.ok(widgetService.updateWidget(widget));
  }
}
