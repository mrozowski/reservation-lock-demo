package com.mrozowski.seatreservation

class ScenarioContext {

  private final Map<String, Object> context = new HashMap<>()

  void put(String key, Object value) {
    context.put(key, value)
  }

  <T> T get(String key) {
    return (T) context.get(key)
  }

  public boolean containsKey(String key) {
    return context.containsKey(key)
  }

  public void clearAllScenarioContext() {
    context.clear()
  }

  private void clear(String key) {
    context.remove(key)
  }
}
