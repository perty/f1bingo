package se.artcomputer.f1.bingo.controller.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import se.artcomputer.f1.bingo.entity.Fan;
import se.artcomputer.f1.bingo.repository.FanRepository;

import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping
public class AuthAdminController {
    private final FanRepository fanRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthAdminController(FanRepository ourUserRepo, PasswordEncoder passwordEncoder) {
        this.fanRepository = ourUserRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/users/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> saveUser(@RequestBody SaveUserRequest saveUserRequest) {
        Optional<Fan> checkUser = fanRepository.findByName(saveUserRequest.name());
        if (checkUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }
        Fan ourUser = new Fan(saveUserRequest.name(), saveUserRequest.roles(), passwordEncoder.encode(saveUserRequest.password()));
        Fan result = fanRepository.save(ourUser);
        if (result.getId() > 0) {
            return ResponseEntity.ok(new UserSingleResponse(result.getName(), result.getRoles()));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error, User Not Saved");
    }

    @GetMapping("/users/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AllUsersResponse> getAllUsers() {
        return ResponseEntity.ok(AllUsersResponse.from(fanRepository.findAll()));
    }

    @GetMapping("/users/single")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<UserSingleResponse> getMyDetails() {
        Fan fan = fanRepository.findByName(getLoggedInUserDetails().name()).orElseThrow();
        return ResponseEntity.ok(new UserSingleResponse(fan.getName(), fan.getRoles()));
    }

    @PostMapping("/users/reset-password")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        Optional<Fan> userOptional = fanRepository.findByName(resetPasswordRequest.name());
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        Fan user = userOptional.get();
        user.setPassword(passwordEncoder.encode(resetPasswordRequest.newPassword()));
        fanRepository.save(user);
        return ResponseEntity.ok("Password reset successfully");
    }

    @PostMapping("/users/change-password")
    public ResponseEntity<Object> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        FanDetails userDetails = getLoggedInUserDetails();
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        Fan user = fanRepository.findByName(userDetails.name()).orElseThrow();
        if (!passwordEncoder.matches(changePasswordRequest.currentPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(changePasswordRequest.newPassword()));
        fanRepository.save(user);
        return ResponseEntity.ok("Password changed successfully");
    }

    public FanDetails getLoggedInUserDetails() {
        Fan fan = UserContext.get().orElseThrow();
        return new FanDetails(fan.getName(), Arrays.stream(fan.getRoles().split(",")).toList());
    }
}
