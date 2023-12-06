package com.mrozowski.seatreservation

import org.apache.commons.lang3.StringUtils

class RestApiClient implements AutoCloseable {

  private static final String COOKIE_KEY = "Cookie"
  private static final String HTTP_ONLY_FLAG = "HttpOnly"
  private int timeoutMs = 5000
  private String jsonBody
  private String endpoint
  private RequestMethod method = RequestMethod.GET
  private List<HTTPHeader> headers = new ArrayList<>()
  private HttpURLConnection connection

  static RestApiClient newConnection() {
    return new RestApiClient()
  }

  RestApiClient url(String url) {
    endpoint = url
    return this
  }

  RestApiClient connectionTimeout(int timeoutMs) {
    this.timeoutMs = timeoutMs
    return this
  }

  RestApiClient requestMethod(RequestMethod method) {
    this.method = method
    return this
  }

  RestApiClient addHeader(HTTPHeader header) {
    headers.add(header)
    return this
  }

  RestApiClient addJsonBody(String json) {
    headers.add(HTTPHeader.of("Content-Type", "application/json"))
    jsonBody = json
    return this
  }

  RestApiClient connect() {
    def url = new URL(endpoint)
    connection = url.openConnection() as HttpURLConnection

    if (!headers.isEmpty()) {
      processHeaders()
    }

    if (StringUtils.isNotBlank(jsonBody)) {
      connection.setDoOutput(true)
      try (OutputStream os = connection.getOutputStream()) {
        os.write(jsonBody.getBytes("UTF-8"))
      }
    }

    connection.setRequestMethod(method.name())
    connection.setConnectTimeout(timeoutMs)
    connection.connect()
    return this
  }

  void assertStatusCode(int expectedStatusCode) {
    def statusCode = connection.responseCode
    assert statusCode == expectedStatusCode: "Expected status code ${expectedStatusCode}, but got $statusCode"
  }

  String getResponseAsText() {
    return connection.inputStream.text
  }

  @Override
  void close() throws Exception {
    headers.clear()
    if (connection != null) {
      connection.disconnect()
    }
  }

  private void processHeaders() {
    headers.each { processHeader(it) }
  }

  private void processHeader(HTTPHeader header) {
    def headerValue = header.hasFlag() ? "${header.value}; ${header.flag}" : "${header.value}"
    connection.setRequestProperty(header.name, headerValue)
  }

  static class HTTPHeader {

    private final String name
    private final String value
    private final String flag

    HTTPHeader(String name, String value, String flag = null) {
      this.name = name
      this.value = value
      this.flag = flag
    }

    static HTTPHeader of(String name, String value) {
      return new HTTPHeader(name, value)
    }

    static HTTPHeader withFlag(String name, String value, String flag) {
      return new HTTPHeader(name, value, flag)
    }

    static HTTPHeader sessionToken(String sessionToken) {
      return new HTTPHeader(COOKIE_KEY, "session_token=$sessionToken", HTTP_ONLY_FLAG)
    }

    boolean hasFlag() {
      return !flag.isBlank()
    }
  }

  static enum RequestMethod {
    GET,
    POST,
    PUT,
    DELETE
  }
}
