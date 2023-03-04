package org.webanwendungbackend;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jdk.javadoc.doclet.Reporter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@RestController
@RequestMapping("/api/v1")
public class WebanwendungBackendApplication {

    Database db = new Database();

    @GetMapping("/auth")
    public ResponseEntity<User> authenticate(@RequestBody Authentication auth) {
        User user = db.auth(auth);
        if (user != null)  {
            return new ResponseEntity<User>(user, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<User>(user, HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody User user) {
        boolean success = db.createUser(user);
        if (success)  {
            return new ResponseEntity<Response>(new Response(true, "User was created"), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<Response>(new Response(false, "Bad Request"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> geAlltUsers() {

        List<User> userList = db.getUsers();
        if (userList != null)  {
            return new ResponseEntity<List<User>>(userList, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<List<User>>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/meals")
    public ResponseEntity<List<Meal>> getAllMeals() {
        List<Meal> mealList = db.getMeals();
        if (mealList != null)  {
            return new ResponseEntity<List<Meal>>(mealList, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<List<Meal>>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/meal")
    public ResponseEntity<Response> createMeal(@RequestBody Meal meal) {
        boolean success = db.createMeal(meal);
        if (success)  {
            return new ResponseEntity<Response>(new Response(true, "Meal was created"), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<Response>(new Response(false, "Bad Request"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/order")
    public ResponseEntity<Response> createOrder(@RequestBody Order order, @RequestParam String username) {
        User updatedUser = db.createOrder(order, username);
        if (updatedUser != null)  {
            return new ResponseEntity<Response>(new Response(true, "Order created"), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<Response>(new Response(false, "Bad Request"), HttpStatus.BAD_REQUEST);
        }
    }






    public static void main(String[] args) {
        SpringApplication.run(WebanwendungBackendApplication.class, args);
    }

}
