package ua.com.foxminded.yuriy.schedulewebapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ua.com.foxminded.yuriy.schedulewebapp.entity.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
	
	@Query("SELECT DISTINCT l From Lesson l JOIN Student s ON :wizardId = s.id"
			+ " WHERE l.subject = ANY elements(s.subjects)"
			+ " AND l.house = s.house")
	
	List<Lesson> getByWizardId(@Param("wizardId")Long wizardId);
	
	@Query("SELECT DISTINCT l From Lesson l JOIN Student s ON :wizardId = s.id"
			+ " WHERE l.subject = ANY elements(s.subjects)"
			+ " AND l.house = s.house"
			+ " AND extract(dow from l.time) = :selectedDay")
	List<Lesson>getByWizardIdAndDayOfWeek(@Param("wizardId")Long wizardId, @Param("selectedDay") int selectedDay);
}
