package com.mrozowski.seatreservation

import groovy.text.SimpleTemplateEngine

class JsonTemplate {

  private static final SimpleTemplateEngine ENGINE = new SimpleTemplateEngine()
  private final String template
  private final Map<String, Object> binding

  private JsonTemplate(String templateBody, Map map) {
    template = templateBody
    binding = map
  }

  static newTemplate(String template) {
    return new JsonTemplate(template, new HashMap())
  }

  JsonTemplate binding(Map<String, Object> map) {
    binding.putAll(map)
    return this
  }

  String generate() {
    return ENGINE.createTemplate(template).make(binding).toString()
  }
}
