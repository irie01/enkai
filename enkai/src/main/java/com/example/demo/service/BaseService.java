package com.example.demo.service;

import java.util.List;

import com.example.demo.common.DataNotFoundException;

public interface BaseService<T> {// 中身の処理は、インタフェースクラス　
	public List<T> findAll();

	public T findById(Integer id) throws DataNotFoundException;
	
	public T findByuserId(Integer user_id) throws DataNotFoundException;

	public void save(T t);

	public void deleteById(Integer id);
}
