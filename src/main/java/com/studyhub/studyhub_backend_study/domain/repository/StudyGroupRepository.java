package com.studyhub.studyhub_backend_study.domain.repository;

import com.studyhub.studyhub_backend_study.domain.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyGroupRepository extends JpaRepository<StudyGroup,Long> {
    List<StudyGroup> findAllByCreatedBy(Long createdBy);
}
