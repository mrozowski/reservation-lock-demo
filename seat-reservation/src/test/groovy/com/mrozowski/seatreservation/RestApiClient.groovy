package com.mrozowski.seatreservation


class RestApiClient implements AutoCloseable {

  private int timeoutMs = 0
  private String endpoint
  private RequestMethod method = RequestMethod.GET
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

  RestApiClient connect() {
    def url = new URL(endpoint)
    connection = url.openConnection() as HttpURLConnection
    if (timeoutMs != 0) {
      connection.setConnectTimeout(timeoutMs)
    }
    connection.setRequestMethod(method.name())
    connection.connect()
    return this
  }

  void assertStatusCode(int expectedStatusCode) {
    def statusCode = connection.responseCode
    assert statusCode == expectedStatusCode: "Expected status code ${expectedStatusCode}, but got $statusCode"
  }

  InputStream getResponseAsInputStream() {
    return connection.inputStream
  }

  String getResponseAsText() {
    return connection.inputStream.text
  }


  @Override
  void close() throws Exception {
    if (connection != null) {
      connection.disconnect()
    }
  }

  static enum RequestMethod {
    GET,
    POST,
    PUT,
    DELETE
  }
}
