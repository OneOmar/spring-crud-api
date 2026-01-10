//package com.omar.demoapi.repository;
//
//import com.omar.demoapi.entity.User;
//import org.springframework.stereotype.Repository;
//
//import java.util.*;
//
//@Repository
//public class InMemoryUserRepository implements UserRepository {
//
//    // In-memory data store
//    private final Map<Long, User> userStore = new HashMap<>();
//    private Long nextId = 1L;
//
//    @Override
//    public List<User> findAll() {
//        return new ArrayList<>(userStore.values());
//    }
//
//    @Override
//    public Optional<User> findById(Long id) {
//        return Optional.ofNullable(userStore.get(id));
//    }
//
//    @Override
//    public User save(User user) {
//        if (user.getId() == null) {
//            user.setId(nextId);
//            nextId++;
//        }
//        userStore.put(user.getId(), user);
//        return user;
//    }
//
//    @Override
//    public void deleteById(Long id) {
//        userStore.remove(id);
//    }
//}
