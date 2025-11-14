package com.ic.app;

public class Controller {

  private static GUI app;

  public synchronized static GUI getInstance() {

    if (app == null) {
      app = new GUI();
      return app;
    }
    return app;
  }
}
