package app.backend.service;

import app.backend.dao.StudentRepository;
import app.backend.exception.DuplicateEmailException;
import app.backend.exception.StudentNotFoundException;
import app.backend.model.entity.Student;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    // define field for studentRepository
    private final StudentRepository studentRepository;

    // inject studentRepository using constructor injection
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // implement methods

    /**
     * Create student
     *
     * @param student Student
     * @return student
     */
    public Student createStudent(Student student) {
        try {
            studentRepository.save(student);
            return student;
        } catch (DataIntegrityViolationException e) {
            // handle the error
            throw new DuplicateEmailException("The email already exists.", e);
        }
    }

    /**
     * Find all students
     *
     * @return list of students
     */
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    /**
     * Find student by id
     *
     * @param id int
     * @return student
     */
    public Student findById(int id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new StudentNotFoundException("Student id not found - " + id);
        }
        return student.get();
    }

    /**
     * Update student
     *
     * @param student Student
     */
    public void updateStudent(Student student) {
        try {
            studentRepository.save(student);
        } catch (DataIntegrityViolationException e) {
            // handle the error
            throw new DuplicateEmailException("The email already exists.", e);
        }
    }

    /**
     * Delete student by id
     *
     * @param id int
     */
    public void deleteStudent(int id) {
        studentRepository.deleteById(id);
    }

    /**
     * Delete all students
     */
    public void deleteAllStudents() {
        studentRepository.deleteAll();
    }
}
