package com.kishore.jws.project.entity;

import java.sql.Timestamp;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@RequiredArgsConstructor
@Getter
@Setter
public class BaseEntity {
	
	@Id
	@GeneratedValue(generator = "UUID")
	@Column(name = "id")
	private UUID id;
	
	@Column(name = "created_on")
	private Timestamp createdOn;
	
	@Column(name = "modified_on")
	private Timestamp modifiedOn;
	
	@Column(name = "created_by")
	private UUID createdBy;
	
	@Column(name = "modified_by")
	private UUID modifedBy;
	
	@Column(name = "is_user_active")
	private boolean isUserActive = true;

}