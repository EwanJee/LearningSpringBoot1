package com.learning.webservices.user;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserJpaResource {


    private final UserRepository userRepository;
    private final PostRepository postRepository;
    @Autowired
    public UserJpaResource(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }
    @GetMapping("/jpa/users")
    public List<User> retrieveUserList(){
        return userRepository.findAll();
    }
    /*@GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id){
        User user = userDaoService.findById(id);
        if(user == null){
            throw new UserNotFoundException("id:" + id);
        }
        return user;
    }*/
    @GetMapping("/jpa/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("id:" + id);
        }
        EntityModel<User> entityModel = EntityModel.of(user.get());
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveUserList());
        entityModel.add(link.withRel("all-users"));
        return entityModel;
    }
    @DeleteMapping("/jpa/users/{id}")
    public void deleteUser(@PathVariable int id){
        userRepository.deleteById(id);
    }
    @PostMapping("/jpa/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User savedUser = userRepository.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedUser.getId()).toUri();
        // -> location header
        return ResponseEntity.created(location).build();
    }
    @GetMapping("/jpa/users/{id}/posts")
    public List<Post> retrievePostsForUser(@PathVariable int id){
        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty()){
            throw new UserNotFoundException("id: " + id);
        }
        return user.get().getPosts();
    }
    @PostMapping("/jpa/users/{id}/posts")
    public ResponseEntity<Post> createPostsForUser(@PathVariable int id, @Valid @RequestBody Post post){
        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty()){
            throw new UserNotFoundException("id: " + id);
        }

        post.setUser(user.get());
        Post savedPost = postRepository.save(post);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedPost.getId()).toUri();
        // -> location header
        return ResponseEntity.created(location).build();
    }
}
