package org.webanwendungbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@SpringBootApplication
@RestController
@RequestMapping("/api/v1")
public class WebanwendungBackendApplication {

    private Database db = new Database();

    @PostMapping("/auth")
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
        if(user.role == null) {
            user.setRole(Role.USER);
        }
        if(user.getOrders() == null) {
            List<Order> orders = new ArrayList<>();
            user.setOrders(orders);
        }
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
            return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
        } else {
            return new ResponseEntity<List<User>>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/meals")
    public ResponseEntity<List<Meal>> getAllMeals() {
        List<Meal> mealList = db.getMeals();
        if (mealList != null)  {
            return new ResponseEntity<List<Meal>>(mealList, HttpStatus.OK   );
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
        if (updatedUser != null) {
            return new ResponseEntity<Response>(new Response(true, "Order created"), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<Response>(new Response(false, "Bad Request"), HttpStatus.BAD_REQUEST);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(WebanwendungBackendApplication.class, args);
    }

}
