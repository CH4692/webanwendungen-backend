package org.webanwendungbackend;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import java.io.IOException;
import java.util.List;

public class Database {

    private ObjectMapper objectMapper = new ObjectMapper();
    private Resource mealsDB = new ClassPathResource("MealsDB.json");
    private Resource usersDB = new ClassPathResource("UsersDB.json");


        public boolean createUser(User user) {
            boolean success = false;
            try {
                List<User> userList = objectMapper.readValue(usersDB.getFile(), new TypeReference<List<User>>() {});
                userList.add(user);
                objectMapper.writeValue(usersDB.getFile(),userList);
                success = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return success;
        }

    public List<User> getUsers() {
        List<User> userList = null;
        try {
            userList = objectMapper.readValue(usersDB.getFile(), new TypeReference<List<User>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public User auth(Authentication auth) {
        User currentUser = null;
        try {
            List<User> userList = objectMapper.readValue(usersDB.getFile(), new TypeReference<List<User>>() {});
            for (User user: userList) {
                if (user.getEmail().equals(auth.getEmail())) {
                    if(user.getPassword().equals(auth.getPassword())) {
                        currentUser = user;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currentUser;
    }

    public List<Meal> getMeals() {
        List<Meal> mealList = null;
        try {
            mealList = objectMapper.readValue(mealsDB.getFile(), new TypeReference<List<Meal>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mealList;
    }

    public boolean createMeal(Meal meal) {
        boolean success = false;
        try {
            List<Meal> mealList = objectMapper.readValue(mealsDB.getFile(), new TypeReference<List<Meal>>() {});
            mealList.add(meal);
            objectMapper.writeValue(mealsDB.getFile(),mealList);
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return success;

    }

    public User createOrder(Order order, String username) {
        User currentUser = null;
        try {
            List<User> userList = objectMapper.readValue(usersDB.getFile(), new TypeReference<List<User>>() {});
            for (User foundUser: userList) {
                if (foundUser.getUsername().equals(username)) {
                    currentUser = foundUser;
                    foundUser.getOrders().add(order);
                    userList.set(userList.indexOf(foundUser), foundUser);
                    objectMapper.writeValue(usersDB.getFile(),userList);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currentUser;
    }
}
