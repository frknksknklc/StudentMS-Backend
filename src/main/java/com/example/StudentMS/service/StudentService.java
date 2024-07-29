package com.example.StudentMS.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.StudentMS.exception.StudentAlreadyExistsException;
import com.example.StudentMS.exception.StudentNotFoundException;
import com.example.StudentMS.model.Student;
import com.example.StudentMS.repository.StudentRepository;



@Service
public class StudentService implements IStudentService  {
	
	private final StudentRepository studentRepository;
	

	public StudentService(StudentRepository studentRepository) {
		super();
		this.studentRepository = studentRepository;
	}

	@Override
	public Student addStudent(Student student) {
		
		if(studentAlreadyExists(student.getEmail())) {
			throw new StudentAlreadyExistsException(student.getEmail() + "already exists !");
		}
		
		return studentRepository.save(student);
	}

	private boolean studentAlreadyExists(String email) {
		
		return studentRepository.findByEmail(email).isPresent();
	}

	@Override
	public List<Student> getStudents() {
		
		return studentRepository.findAll();
	}

	@Override
	public Student updateStudent(Student student, Long id) {
		
		return studentRepository.findById(id).map(st -> {
		
		st.setFirstName(student.getFirstName());
		st.setLastName(student.getLastName());
		st.setEmail(student.getEmail());
		st.setDepartment(student.getDepartment());
		return studentRepository.save(st);
		}).orElseThrow(() -> new StudentNotFoundException("Sorry, this student could not be found")); 
	}

	@Override
	public Student getStudentById(Long id) {
	
		return studentRepository.findById(id).
				orElseThrow(() -> new StudentNotFoundException("Sorry, no student found with the id " + id));
	}

	@Override
	public void deleteStudent(Long id) {
		if(!studentRepository.existsById(id)) {
			throw new StudentNotFoundException("Sorry, student not found");
		}
        studentRepository.deleteById(id);
		
	}

}
