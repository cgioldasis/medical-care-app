package gr.aueb.cf.medicalcare.controller;

import gr.aueb.cf.medicalcare.dto.user.UserReadOnlyDTO;
import gr.aueb.cf.medicalcare.dto.user.UserRegisterDTO;
import gr.aueb.cf.medicalcare.dto.user.UserUpdateDTO;
import gr.aueb.cf.medicalcare.mapper.UserMapper;
import gr.aueb.cf.medicalcare.model.User;
import gr.aueb.cf.medicalcare.service.IUserService;
import gr.aueb.cf.medicalcare.service.exception.EntityAlreadyExistsException;
import gr.aueb.cf.medicalcare.service.exception.UserNotFoundException;
import gr.aueb.cf.medicalcare.validator.UserValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserRestController {

    private final IUserService userService;
    private final UserValidator userValidator;

    /**
     * Rest endpoint to get a user by username
     * @param username                  The username
     * @return                          The user
     * @throws UserNotFoundException    If the user is not found
     */
    @Operation(summary = "Get a User by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserReadOnlyDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)})
    @GetMapping("/user/{username}")
    public ResponseEntity<UserReadOnlyDTO> getUserByUsername(@PathVariable("username") String username) throws UserNotFoundException {
        try {
            User user;
            user = userService.getUserByUsername(username);
            UserReadOnlyDTO userReadOnlyDTO = UserMapper.mapToUserReadOnlyDTO(user);
            return ResponseEntity.ok(userReadOnlyDTO);
        } catch (UserNotFoundException e) {
            throw e;
        }
    }

    /**
     * Rest endpoint to get a user by id
     * @param id                                The id of the user
     * @return                                  The user
     * @throws UserNotFoundException            If the user is not found
     */

    @Operation(summary = "Get a User by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Teacher Found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserReadOnlyDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)})
    @GetMapping("/user/{id}")
    public ResponseEntity<UserReadOnlyDTO> getUserById(@PathVariable("id") Long id) throws UserNotFoundException {
        try {
            User user;
            user = userService.getUserById(id);
            UserReadOnlyDTO userReadOnlyDTO = UserMapper.mapToUserReadOnlyDTO(user);
            return ResponseEntity.ok(userReadOnlyDTO);
        } catch (UserNotFoundException e) {
            throw e;
        }
    }

    /**
     * Rest endpoint to get all users
     * @return                          The users
     * @throws UserNotFoundException    If any user exist.
     */
    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users Found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserReadOnlyDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "No users found",
                    content = @Content)})
    @GetMapping("/users/all")
    public ResponseEntity<List<UserReadOnlyDTO>> getAllUsers() throws UserNotFoundException {
        List<User> users;
        try {
            users = userService.getAllUsers();
            List<UserReadOnlyDTO> userReadOnlyDTOS = new ArrayList<>();
            for (User user : users) {
                 userReadOnlyDTOS.add(UserMapper.mapToUserReadOnlyDTO(user));
            }
            return new ResponseEntity<>(userReadOnlyDTOS, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw e;
        }
    }

    /**
     * Rest endpoint to add a user
     * @param registerDTO                       The user to add
     * @param bindingResult                     The binding result
     * @return                                  The user
     * @throws EntityAlreadyExistsException     If the user already exists
     */
    @Operation(summary = "Add a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserReadOnlyDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input was supplied",
                    content = @Content),
            @ApiResponse(responseCode = "503", description = "Service Unavailable",
                    content = @Content)})
    @PostMapping("/user")
    public ResponseEntity<UserReadOnlyDTO> registerUserAdmin(
            @Valid @RequestBody UserRegisterDTO registerDTO, BindingResult bindingResult) throws EntityAlreadyExistsException {
        userValidator.validate(registerDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        User user = userService.registerNewAdminUser(registerDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}")
                .buildAndExpand(user.getUsername()).toUri();
        return ResponseEntity.created(location).body(UserMapper.mapToUserReadOnlyDTO(user));
    }

    /**
     * Rest endpoint to update a user
     * @param id                                The id of the user
     * @param userUpdateDTO                     The user to update
     * @param bindingResult                     The binding result
     * @return                                  The updated user
     * @throws UserNotFoundException            If the user is not found
     */
    @Operation(summary = "Update a User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Teacher updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserReadOnlyDTO.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized user",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input was supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content) })
    @PutMapping("/user/{id}")
    public ResponseEntity<UserReadOnlyDTO> updateUser(@PathVariable("id") Long id,
                                                      @Valid @RequestBody UserUpdateDTO userUpdateDTO,
                                                      BindingResult bindingResult) throws UserNotFoundException {

        if (!Objects.equals(id, userUpdateDTO.getId())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (bindingResult.hasErrors()) {
            throw new RuntimeException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        try {
            User user = userService.updateUser(userUpdateDTO);
            UserReadOnlyDTO userReadOnlyDTO = UserMapper.mapToUserReadOnlyDTO(user);
            return new ResponseEntity<>(userReadOnlyDTO, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw e;
        }
    }

    /**
     * Rest endpoint to delete a user
     * @param id                                The id of the user
     * @return                                  The deleted user
     * @throws UserNotFoundException            If the user is not found
     */
    @Operation(summary = "Delete a User by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Teacher Deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserRegisterDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)})
    @DeleteMapping("user/{id}")
    public ResponseEntity<UserReadOnlyDTO> deleteUser(@PathVariable("id") Long id) throws UserNotFoundException {
        User user = userService.deleteUser(id);
        UserReadOnlyDTO userReadOnlyDTO = UserMapper.mapToUserReadOnlyDTO(user);
        return ResponseEntity.ok(userReadOnlyDTO);
    }

    /**
     * Rest endpoint to count all users
     * @return                                  The number of users
     */
    @Operation(summary = "Count all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users counted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Long.class)) })})
    @GetMapping("/users/count/all")
    public ResponseEntity<Long> countUsers() {
        return ResponseEntity.ok(userService.countUsers());
    }
}
