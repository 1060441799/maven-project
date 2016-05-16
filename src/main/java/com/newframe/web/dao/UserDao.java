package com.newframe.web.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.newframe.core.pojo.pojoimpl.impl.User;

public interface UserDao extends PagingAndSortingRepository<User, Serializable>,JpaRepository<User, Serializable>{
	List<User> findAll();
}
