package com.ic.app;

public class Controller {

    private static Main2 app;

    public synchronized static Main2 getInstance() {

        if (app == null) {
            app = new Main2();
            return app;
        }
        return app;
    }
}
