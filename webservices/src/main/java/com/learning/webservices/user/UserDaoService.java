package com.learning.webservices.user;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class UserDaoService {

    private static List<User> userList = new ArrayList<>();

    private static int count = 0;

    static{
        userList.add(new User(++count,"Adam", LocalDate.now()));
        userList.add(new User(++count,"Eve", LocalDate.now().minusYears(1)));
        userList.add(new User(++count,"Jim", LocalDate.now().minusYears(2)));
    }
    public List<User> findAll(){
        return userList;
    }

    public User save(User user){
        user.setId(++count);
        userList.add(user);
        return user;
    }
    public User findById(int id){
        Predicate<? super User> predicate = user -> user.getId().equals(id);
        return userList
                .stream()
                .filter(predicate).findFirst().orElse(null);
    }
    public void deleteById(int id){
        Predicate<? super User> predicate = user -> user.getId().equals(id);
        userList.removeIf(predicate);

    }
}
