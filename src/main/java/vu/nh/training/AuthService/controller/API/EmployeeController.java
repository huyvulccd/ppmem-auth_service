package vu.nh.training.AuthService.controller.API;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vu.nh.training.AuthService.controller.dtos.requests.RegisterRequest;

@RestController
@RequestMapping("/api/management-employees")
public class EmployeeController {

    //    201 created, 400 bad request validation
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        // Implement registration logic here
        String username = request.username();
        return ResponseEntity.ok(username);
    }

    @PostMapping("/assign-roleUser")
    public ResponseEntity<String> assignRole(@RequestHeader("Authorization") String authToken, @RequestParam String role) {
        // Use the authToken for roleUser assignment, if needed
        // Return the 401 Unauthorized
        return ResponseEntity.ok("RoleUser assigned successfully.");
    }

    @GetMapping("")
    public ResponseEntity<String> getAllEmployees() {
        // Retrieve all employees from the database
        return ResponseEntity.ok("All employees retrieved successfully.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getEmployeeById(@PathVariable Long id) {
        // Retrieve employee by id from the database
        return ResponseEntity.ok("Employee retrieved successfully.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable Long id) {
        // Delete employee by id from the database
        return ResponseEntity.ok("Employee deleted successfully.");
    }

}
