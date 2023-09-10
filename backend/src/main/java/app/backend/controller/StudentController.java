package app.backend.controller;

import app.backend.model.dto.in.StudentDtoIn;
import app.backend.model.dto.out.ShortStudentDtoOut;
import app.backend.model.dto.out.StudentDtoOut;
import app.backend.model.entity.Student;
import app.backend.service.StudentService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;
    HttpHeaders headers = new HttpHeaders();

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * List all students
     *
     * @return list of students
     */
    @GetMapping
    public ResponseEntity<List<StudentDtoOut>> listStudents() {

        List<Student> result = studentService.findAll();
        List<StudentDtoOut> studentsDtoOut = result.stream().map(StudentDtoOut::new).collect(
                Collectors.toList());
        return new ResponseEntity<>(studentsDtoOut, headers, HttpStatus.OK);
    }

    /**
     * Get student by id
     *
     * @param id int
     * @return student
     */
    @GetMapping("/{id}")
    public ResponseEntity<StudentDtoOut> getStudent(@PathVariable int id) {
        Student student = studentService.findById(id);
        if (student == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        StudentDtoOut studentDtoOut = new StudentDtoOut(student);
        return new ResponseEntity<>(studentDtoOut, HttpStatus.OK);
    }

    /**
     * List all students with short info
     *
     * @return list of students
     */
    @GetMapping("/short")
    public ResponseEntity<List<ShortStudentDtoOut>> listShortStudents() {
        List<Student> result = studentService.findAll();
        List<ShortStudentDtoOut> studentsDtoOut = result.stream()
                .map(ShortStudentDtoOut::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(studentsDtoOut, headers, HttpStatus.OK);
    }


    /**
     * Create student
     *
     * @param studentDtoIn StudentDtoIn
     * @return student
     */
    @PostMapping
    public ResponseEntity<StudentDtoOut> addStudent(@RequestBody StudentDtoIn studentDtoIn) {

        Student student = new Student(studentDtoIn.getFirstName(), studentDtoIn.getLastName(), studentDtoIn.getEmail(), studentDtoIn.getAddress(), studentDtoIn.getPhone());
        Student result = studentService.createStudent(student);
        StudentDtoOut StudentDtoOut = new StudentDtoOut(result);
        return new ResponseEntity<>(StudentDtoOut, headers, HttpStatus.CREATED);
    }

    /**
     * Update student
     *
     * @param id      int
     * @param student Student
     * @return student
     */
    // ou @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody Student student) {
        Student currentStudent = studentService.findById(id);
        if (currentStudent == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        currentStudent.setFirstName(student.getFirstName());
        currentStudent.setLastName(student.getLastName());
        currentStudent.setEmail(student.getEmail());
        currentStudent.setAddress(student.getAddress());
        currentStudent.setPhone(student.getPhone());
        studentService.updateStudent(currentStudent);
        return new ResponseEntity<>(currentStudent, HttpStatus.OK);
    }

    /**
     * Delete student by id
     *
     * @param id int
     * @return response entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable int id) {
        Student student = studentService.findById(id);
        if (student == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /**
     * Delete all students
     *
     * @return response entity
     */
    @DeleteMapping
    public ResponseEntity<Student> deleteAllStudents() {
        studentService.deleteAllStudents();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}