package org.xylia.microservice.web;

public class SparkApplication {
    public static void main(String[] args) {
        new UserController(new UserService());
    }
}
