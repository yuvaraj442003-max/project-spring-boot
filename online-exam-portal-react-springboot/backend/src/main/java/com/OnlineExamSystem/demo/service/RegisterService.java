package com.OnlineExamSystem.demo.service;

import com.OnlineExamSystem.demo.model.Register;
import com.OnlineExamSystem.demo.repo.RegisterRepo;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final RegisterRepo registerRepo;

    public Register userSignup(Register register, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            register.setImage(imageFile.getBytes()); // Set image only if present
        } else {
            register.setImage(null); // Ensure image is null if not uploaded
        }
        return registerRepo.save(register);
    }

    public Map<String, Object> userLogin(Register register, HttpSession session) {
        Optional<Register> optional = registerRepo.findByEmail(register.getEmail());
        if (optional.isPresent()) {
            Register register1 = optional.get();
            if (register1.getPassword().equals(register.getPassword())) {
                session.setAttribute("userId", register1.getId());
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Login Success");
                response.put("userId", register1.getId());
                response.put("role", register1.getRole());
                response.put("userName", register1.getName());
                return response;
            } else {
                throw new RuntimeException("Invalid Password");
            }
        } else {
            throw new RuntimeException("User Not Found");
        }
    }

    public Register userProfile(Long userId) {
        return registerRepo.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
    }
}