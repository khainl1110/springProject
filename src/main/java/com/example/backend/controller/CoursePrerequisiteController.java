package com.example.backend.controller;

import com.example.backend.entity.Course;
import com.example.backend.repository.CourseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/courses/{courseId}/prerequisites")
public class CoursePrerequisiteController {

    private final CourseRepository courseRepository;

    public CoursePrerequisiteController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping
    public ResponseEntity<?> getPrerequisites(@PathVariable Long courseId) {
        Optional<Course> opt = courseRepository.findById(courseId);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }
        Course course = opt.get();
        List<Map<String, Object>> response = course.getPrerequisites().stream()
                .map(c -> {
                    Map<String, Object> m = new HashMap<>();
                    m.put("id", c.getId());
                    m.put("name", c.getName());
                    m.put("description", c.getDescription());
                    return m;
                }).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    // Replace the full prerequisite list for a course
    @PutMapping
    @Transactional
    public ResponseEntity<?> replacePrerequisites(@PathVariable Long courseId,
                                                  @RequestBody List<Long> prerequisiteIds) {
        Optional<Course> opt = courseRepository.findById(courseId);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }
        Course course = opt.get();

        if (prerequisiteIds.contains(courseId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Course cannot be a prerequisite of itself");
        }

        List<Course> found = new ArrayList<>();
        courseRepository.findAllById(prerequisiteIds).forEach(found::add);
        if (found.size() != prerequisiteIds.size()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("One or more prerequisite course IDs not found");
        }

        course.setPrerequisites(new HashSet<>(found));
        courseRepository.save(course);
        return ResponseEntity.ok("Prerequisites replaced");
    }

    // Add one or more prerequisites to the existing set
    @PostMapping
    @Transactional
    public ResponseEntity<?> addPrerequisites(@PathVariable Long courseId,
                                              @RequestBody List<Long> prerequisiteIds) {
        Optional<Course> opt = courseRepository.findById(courseId);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }
        Course course = opt.get();

        if (prerequisiteIds.contains(courseId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Course cannot be a prerequisite of itself");
        }

        List<Course> found = new ArrayList<>();
        courseRepository.findAllById(prerequisiteIds).forEach(found::add);
        if (found.size() != prerequisiteIds.size()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("One or more prerequisite course IDs not found");
        }

        Set<Course> current = course.getPrerequisites();
        current.addAll(found);
        course.setPrerequisites(current);
        courseRepository.save(course);
        return ResponseEntity.status(HttpStatus.CREATED).body("Prerequisites added");
    }

    // Remove a single prerequisite
    @DeleteMapping("/{prereqId}")
    @Transactional
    public ResponseEntity<?> removePrerequisite(@PathVariable Long courseId, @PathVariable Long prereqId) {
        Optional<Course> opt = courseRepository.findById(courseId);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }
        Course course = opt.get();

        Optional<Course> p = courseRepository.findById(prereqId);
        if (p.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Prerequisite course not found");
        }

        if (!course.getPrerequisites().contains(p.get())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Course is not a prerequisite");
        }

        course.removePrerequisite(p.get());
        courseRepository.save(course);
        return ResponseEntity.ok("Prerequisite removed");
    }

    // Remove multiple prerequisites by IDs
    @DeleteMapping
    @Transactional
    public ResponseEntity<?> removePrerequisites(@PathVariable Long courseId, @RequestBody List<Long> prerequisiteIds) {
        Optional<Course> opt = courseRepository.findById(courseId);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }
        Course course = opt.get();

        List<Course> found = new ArrayList<>();
        courseRepository.findAllById(prerequisiteIds).forEach(found::add);
        if (found.size() != prerequisiteIds.size()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("One or more prerequisite course IDs not found");
        }

        Set<Course> current = course.getPrerequisites();
        current.removeAll(found);
        course.setPrerequisites(current);
        courseRepository.save(course);
        return ResponseEntity.ok("Prerequisites removed");
    }
}
