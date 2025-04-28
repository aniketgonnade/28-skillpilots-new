package com.skillpilots.service.impl;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.skillpilots.Dto.StudentDetailsDTO;
import com.skillpilots.model.Student;
import com.skillpilots.model.StudentPast;
import com.skillpilots.repository.StudentPastRepository;
import com.skillpilots.repository.StudentRepository;
import com.skillpilots.service.StudentService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private StudentPastRepository studentPastRepository;
	
	@Override
	public Student saveStudent(Student student) {
		return studentRepository.save(student);
	}
	@Override
    public Student getStudentById(Long studentId) {
    	
      return  studentRepository.findById(studentId).orElseThrow(()-> new RuntimeException("Student not found"));
           
        }
	@Override
	public void saveStudent(StudentPast studentPast) {
		studentPastRepository.save(studentPast);
		
	}
	@Override
	public List<Map<String, Object>> findAllByStudentId(Long studentId) {
		return studentPastRepository.getStudentPast(studentId);
	}
	@Override
	public void deleteStudentPastById(Long studPastId) {
		studentPastRepository.deleteById(studPastId);
		
	}
	@Override
	public void saveStudentById(Student student, Long a) {
		studentRepository.save(null);
	}
	@Override
	public Student getStudentByUserId(Long userId) {
		return studentRepository.findBystudent_id(userId);
	}
	@Override
	public List<Student> findAllByDept(Long deptId,Long college_id) {
		return studentRepository.findAllByDept(deptId,college_id);
	}
	@Override
	public List<Student> findAllByDept2(Long deptId,Long college_id) {
		return studentRepository.findAllByDept2(deptId,college_id);
	}
	@Override
	public List<Student> getStudentsByDept(Long dept, Long college_id) {
		return studentRepository.getStudentsByDept(dept, college_id);
	}
	@Override
	public Student getSpecificStudent(Long student_id) {
		// TODO Auto-generated method stub
		return studentRepository.findBystudent_id(student_id);
	}
	@Override
	public List<StudentPast> getStudentPastEducation(Long student_id) {
		// TODO Auto-generated method stub
		return studentPastRepository.findAllByStudentId(student_id);
	}
	@Override
	public List<Object[]> getStudentDetails(Long student_id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<StudentPast> findAllByStudentIds(Long studentId) {
		// TODO Auto-generated method stub
		return studentPastRepository.findAllByStudentId(studentId);
	}
	 
}
	
	
	
	









