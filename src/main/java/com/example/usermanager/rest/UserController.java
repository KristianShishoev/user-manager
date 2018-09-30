package com.example.usermanager.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.usermanager.entity.User;
import com.example.usermanager.repository.UserRepository;
import com.example.usermanager.repository.UserService;
import com.example.usermanager.util.EncryptionUtil;

@RequestMapping("/user")
@RestController
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<String> createUser(@RequestBody User user){
		user.setPassword(EncryptionUtil.getEncryptedPass(user.getPassword()));
		userRepository.save(user);
		return new ResponseEntity<String>(HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteUser(@RequestParam("email") String email){
		User userToBeDeleted = userRepository.findByEmail(email);
		userRepository.delete(userToBeDeleted);
		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(method = RequestMethod.GET, params = { "page", "size" } )
	public Page<User> findAll(@RequestParam("page") int page, @RequestParam("size") int size){
		 Page<User> resultPage = userService.findPaginated(page, size);
	        if (page > resultPage.getTotalPages()) {
	            throw new RuntimeException();
	        }
	        return resultPage;
	}
}
