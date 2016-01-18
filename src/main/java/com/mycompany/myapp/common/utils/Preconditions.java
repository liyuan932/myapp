package com.mycompany.myapp.common.utils;

import com.mycompany.myapp.common.enums.CommonExceptionEnum;
import com.mycompany.myapp.common.exception.ServiceException;

public class Preconditions {

	public static void checkArgument(boolean... expressions) {
		for (boolean expression : expressions) {
			if (!expression) {
				throw new ServiceException(CommonExceptionEnum.FAIL_BIZ_PARAM_ERROR);
			}
		}
	}

	public static void checkArgument(boolean expression, Object errorMessage) {
		if (!expression) {
			throw new ServiceException(String.valueOf(errorMessage),
					CommonExceptionEnum.FAIL_BIZ_PARAM_ERROR.getCode());
		}

	}

	public static void checkNotNull(Object... args) {
		for (Object o : args) {
			if (o == null) {
				throw new ServiceException(CommonExceptionEnum.FAIL_BIZ_PARAM_ERROR);
			}
		}
	}

	public static <T> T checkNotNull(T reference, Object errorMessage) {
		if (reference == null) {
			throw new ServiceException(String.valueOf(errorMessage),
					CommonExceptionEnum.FAIL_BIZ_PARAM_ERROR.getCode());
		}
		return reference;
	}
}
