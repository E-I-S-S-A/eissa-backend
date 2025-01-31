package com.eissa.backend.keep.controllers;

import com.eissa.backend.accounts.models.classes.entities.User;
import com.eissa.backend.accounts.models.enums.CookieEnum;
import com.eissa.backend.accounts.repos.UserRepo;
import com.eissa.backend.accounts.services.UserService;
import com.eissa.backend.common.utils.CookieUtil;
import com.eissa.backend.common.utils.JwtUtil;
import com.eissa.backend.keep.models.classes.entities.Keep;
import com.eissa.backend.keep.repos.KeepRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/keep")
public class KeepController {

    @Autowired
    KeepRepo keepRepo;
    @Autowired
    UserService userService;

    @PostMapping("/add")
    public ResponseEntity<String> addKeep(HttpServletRequest httpServletRequest, @RequestBody Keep keep) {
        try {
            String accessToken = CookieUtil.getCookie(httpServletRequest, CookieEnum.ACCESS_TOKEN);
            if (accessToken.isEmpty() || accessToken == null)
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

            if (keep.getKeepId() == null && (keep.getTitle() == null || keep.getDescription() == null)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            String userId = userService.getUserIdFromAccessToken(accessToken);
            keep.setUserId(userId);

            int count = this.keepRepo.addKeep(keep);
            return count > 0 ? ResponseEntity.ok("Success") : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<Keep>> getKeeps(HttpServletRequest httpServletRequest, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int limit, @RequestParam(required = false) String searchToken) {
        try {
            String accessToken = CookieUtil.getCookie(httpServletRequest, CookieEnum.ACCESS_TOKEN);
            if (accessToken.isEmpty() || accessToken == null)
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

            String userId = userService.getUserIdFromAccessToken(accessToken);
            List<Keep> keeps = this.keepRepo.getKeeps(page, limit, searchToken, userId);

            return ResponseEntity.ok(keeps);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateKeep(HttpServletRequest httpServletRequest, @RequestBody Keep keep) {
        try {
            String accessToken = CookieUtil.getCookie(httpServletRequest, CookieEnum.ACCESS_TOKEN);
            if (accessToken.isEmpty() || accessToken == null)
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

            String userId = userService.getUserIdFromAccessToken(accessToken);
            keep.setUserId(userId);

            int count = keepRepo.updateKeep(keep);
            return count > 0 ? ResponseEntity.ok("Updated successfully") : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Keep not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete/{keepId}")
    public ResponseEntity<String> deleteKeep(HttpServletRequest httpServletRequest, @PathVariable String keepId) {
        try {
            String accessToken = CookieUtil.getCookie(httpServletRequest, CookieEnum.ACCESS_TOKEN);
            if (accessToken.isEmpty() || accessToken == null)
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

            String userId = userService.getUserIdFromAccessToken(accessToken);

            int count = keepRepo.deleteKeep(keepId, userId);
            return count > 0 ? ResponseEntity.ok("Deleted successfully") : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Keep not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
