package com.spring.genie.Dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spring.genie.entities.ContactDetail;

@Repository
public interface ContactsRepository extends JpaRepository<ContactDetail, Integer> {

	@Query("from ContactDetail as c where c.user.id = :userId")
	public Page<ContactDetail> findContactsByUser(@Param("userId") int userId,Pageable pageable);

}
