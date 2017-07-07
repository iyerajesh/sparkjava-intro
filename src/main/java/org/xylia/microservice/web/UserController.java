package org.xylia.microservice.web;

import static org.xylia.microservice.web.JsonUtil.json;
import static org.xylia.microservice.web.JsonUtil.toJson;
import static spark.Spark.*;


public class UserController {

    public UserController(final UserService userService) {

        get("/users", (req, res) -> userService.getAllUsers(), json());

        get("/users/:id", (req, res) -> {
            String id = req.params(":id");
            User user = userService.getUser(id);
            if (user != null) {
                return user;
            }
            res.status(400);
            return new ResponseError("No user with id '%s' found", id);
        }, json());

        post("/users", (req, res) -> userService.createUser(
                req.queryParams("name"),
                req.queryParams("email")
        ), json());

        put("/users/:id", (req, res) -> userService.updateUser(
                req.params(":id"),
                req.queryParams("name"),
                req.queryParams("email")
        ), json());

        after((req, res) -> {
            res.type("application/json");
        });

        exception(IllegalArgumentException.class, (e, req, res) -> {
            res.status(400);
            res.body(toJson(new ResponseError(e)));
        });
    }
}
