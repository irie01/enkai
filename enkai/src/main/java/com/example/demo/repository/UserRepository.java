package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	public User findByEmail(String email); //名前を見てレコードを取ってくるJPA find = SELECT、 by = WHERE句 基本はSQL分を書く
}

