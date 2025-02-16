package se.artcomputer.f1.bingo.controller.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import se.artcomputer.f1.bingo.entity.Fan;
import se.artcomputer.f1.bingo.repository.FanRepository;

import java.util.Optional;

@RestController
@RequestMapping
public class AuthController {
    private final FanRepository fanRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(FanRepository ourUserRepo, PasswordEncoder passwordEncoder) {
        this.fanRepository = ourUserRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public ResponseEntity<Object> login() {
        return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/user/profile.html")
                .build();
    }

    @GetMapping("/f1login")
    public ResponseEntity<Object> getLoginForm(@RequestParam(name = "error", required = false) String error) {
        String formUrl = "/f1login-form.html" + (error != null ? "?error" : "");
        return ResponseEntity.status(HttpStatus.FOUND).header("Location", formUrl)
                .build();
    }

    @PostMapping("/users/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> saveUser(@RequestBody SaveUserRequest saveUserRequest) {
        Optional<Fan> checkUser = fanRepository.findByName(saveUserRequest.email());
        if (checkUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }
        Fan ourUser = new Fan(saveUserRequest.email(), saveUserRequest.roles(), passwordEncoder.encode(saveUserRequest.password()));
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
        Fan byEmail = fanRepository.findByName(getLoggedInUserDetails().getUsername()).orElseThrow();
        return ResponseEntity.ok(new UserSingleResponse(byEmail.getName(), byEmail.getRoles()));
    }

    @PostMapping("/users/reset-password")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        Optional<Fan> userOptional = fanRepository.findByName(resetPasswordRequest.email());
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        Fan user = userOptional.get();
        user.setPassword(passwordEncoder.encode(resetPasswordRequest.newPassword()));
        fanRepository.save(user);
        return ResponseEntity.ok("Password reset successfully");
    }

    @PostMapping("/users/change-password")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Object> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        UserDetails userDetails = getLoggedInUserDetails();
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        Fan user = fanRepository.findByName(userDetails.getUsername()).orElseThrow();
        if (!passwordEncoder.matches(changePasswordRequest.currentPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(changePasswordRequest.newPassword()));
        fanRepository.save(user);
        return ResponseEntity.ok("Password changed successfully");
    }

    public UserDetails getLoggedInUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return (UserDetails) authentication.getPrincipal();
        }
        return null;
    }
}
