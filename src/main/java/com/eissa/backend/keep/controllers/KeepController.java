package com.eissa.backend.keep.controllers;


import com.eissa.backend.accounts.models.classes.entities.User;
import com.eissa.backend.accounts.models.enums.CookieEnum;
import com.eissa.backend.accounts.repos.UserRepo;
import com.eissa.backend.common.utils.CookieUtil;
import com.eissa.backend.common.utils.JwtUtil;
import com.eissa.backend.keep.models.classes.entities.Keep;
import com.eissa.backend.keep.repos.KeepRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/keep")
public class KeepController {

    @Autowired
    KeepRepo keepRepo;
    @Autowired
    UserRepo userRepo;

    @PostMapping("/add")
    public ResponseEntity<String> addKeep(HttpServletRequest httpServletRequest, @RequestBody Keep keep) {
        try {
            //@Todo Check if keep exists
            String accessToken = CookieUtil.getCookie(httpServletRequest, CookieEnum.ACCESS_TOKEN);

            if (accessToken.isEmpty() || accessToken == null)
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

            if (keep.getKeepId() == null && (keep.getTitle() == null || keep.getDescription() == null)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            String userEmail = JwtUtil.validateToken(accessToken);
            User userFromDb = userRepo.getUserFromEmailOrId(userEmail);
            keep.setUserId(userFromDb.getUserId());

            int count = this.keepRepo.addKeep(keep);

            if (count > 0) {
                return ResponseEntity.status(HttpStatus.OK).body("Success");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/")
    ResponseEntity<List<Keep>> getKeeps(HttpServletRequest httpServletRequest, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int limit, @RequestParam(required = false) String searchToken) {
        try {
            String accessToken = CookieUtil.getCookie(httpServletRequest, CookieEnum.ACCESS_TOKEN);

            if (accessToken.isEmpty() || accessToken == null)
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            String userEmail = JwtUtil.validateToken(accessToken);
            User userFromDb = userRepo.getUserFromEmailOrId(userEmail);
            List<Keep> keeps = this.keepRepo.getKeeps(page, limit, searchToken, userFromDb.getUserId());
            return ResponseEntity.status(HttpStatus.OK).body(keeps);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
