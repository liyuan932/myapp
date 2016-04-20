package com.mycompany.myapp.service.common;

public class BaseService {

	protected <T> Result<T> success(T info){
		return new Result<>(info);
	}
}
