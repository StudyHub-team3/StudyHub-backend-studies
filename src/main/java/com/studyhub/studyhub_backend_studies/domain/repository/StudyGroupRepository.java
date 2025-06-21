package com.studyhub.studyhub_backend_studies.domain.repository;

import com.studyhub.studyhub_backend_studies.domain.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyGroupRepository extends JpaRepository<StudyGroup,Long> {
    StudyGroup findByUserId(String userId);
    List<StudyGroup> findAllByCreatedBy(Long userId);
}
