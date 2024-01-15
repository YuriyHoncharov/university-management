package ua.com.foxminded.yuriy.schedulewebapp.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.com.foxminded.yuriy.schedulewebapp.entity.Grade;
import ua.com.foxminded.yuriy.schedulewebapp.repository.GradeRepository;
import ua.com.foxminded.yuriy.schedulewebapp.service.GradeService;

@Service
public class GradeServiceImpl implements GradeService {
	
	private final GradeRepository gradeRepository;

	@Autowired
	public GradeServiceImpl(GradeRepository gradeRepository) {
		this.gradeRepository = gradeRepository;
	}

	@Override
	public List<Grade> getAllGrades() {
		return gradeRepository.findAll();
	}

	@Override
	public Optional<Grade> getGradeById(Long id) {
		return gradeRepository.findById(id);
	}

	@Override
	public Grade saveGrade(Grade grade) {
		return gradeRepository.save(grade);
	}

	@Override
	public void deleteGrade(Long id) {
		gradeRepository.deleteById(id);
	}
}