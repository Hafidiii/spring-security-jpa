package com.example.springsec.controller;

import com.example.springsec.constants.Constants;
import com.example.springsec.dto.JwtRequest;
import com.example.springsec.dto.JwtResponse;
import com.example.springsec.dto.UserDto;
import com.example.springsec.enums.ERole;
import com.example.springsec.model.Role;
import com.example.springsec.model.User;
import com.example.springsec.repository.RoleRepository;
import com.example.springsec.repository.UserRepository;
import com.example.springsec.service.UserServices;
import com.example.springsec.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
public class JwtAuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserServices userDetailService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;


    @PostMapping(value = "/authenticate")
    public Object getAuthenticationToken(@RequestBody JwtRequest request) {

        Map<String, Object> map = new HashMap<>();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        } catch (BadCredentialsException e) {
            map.put(Constants.MESSAGE, "Oups!!! user or password incorrect");
            map.put(Constants.STATUS, false);

            return map;
        }

        UserDetails userDetails = userDetailService.loadUserByUsername(request.getUsername());
        String token = jwtUtil.generateToken(userDetails);

        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
        map.put("jwt", new JwtResponse(token).getJwt());
        map.put("username", userDetails.getUsername());
        map.put("roles", roles);
        map.put(Constants.MESSAGE, "user authenticated");
        map.put(Constants.STATUS, true);

        return map;

    }

    @PostMapping(value = "/signup")
    public Object signUp(@RequestBody UserDto userDto) {

        Map<String, Object> map = new HashMap<>();

        User user = new User(userDto.getEmail(), userDto.getUsername(), encoder.encode(userDto.getPassword()));

        Optional<Role> role = roleRepository.findByName(ERole.ROLE_USER);

        Set<Role> roles = new HashSet<>();

        roles.add(role.isPresent() ? role.get() : new Role(ERole.ROLE_USER));

        user.setRoles(roles);
        userRepository.save(user);
        map.put(Constants.MESSAGE, "User registered");
        map.put(Constants.STATUS, true);

        return map;

    }

    @GetMapping(value = "/403")
    public Object accesssDenied(Principal user) {
        Map<String, Object> json = new HashMap();

        json.put(Constants.STATUS, "403 Forbidden");
        if (user != null) {
            json.put(Constants.MESSAGE, "Hi " + user.getName() + ", you do not have permission to access this page!");
        } else {
            json.put(Constants.MESSAGE,
                    "You do not have permission to access this page!");
        }
        return json;
    }

}
