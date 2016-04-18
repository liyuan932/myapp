package com.mycompany.myapp.base;

public class BaseService {

	protected <T> BaseResult<T> success(T info){
		return new BaseResult<>(info);
	}
}
