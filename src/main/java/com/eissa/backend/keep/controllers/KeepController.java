package com.eissa.backend.keep.controllers;


import com.eissa.backend.keep.models.classes.entities.Keep;
import com.eissa.backend.keep.repos.KeepRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/keep")
public class KeepController {

    @Autowired
    KeepRepo keepRepo;

    @PostMapping("/add")
    public ResponseEntity<String> addKeep(@RequestBody Keep keep) {
        try {
            //@Todo Check if keep exists

            if (keep.getKeepId() == null && (keep.getTitle() == null || keep.getDescription() == null)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
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

}
