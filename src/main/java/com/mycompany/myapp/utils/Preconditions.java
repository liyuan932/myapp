package com.mycompany.myapp.utils;

import com.mycompany.myapp.common.enums.msg.CommonMsgEnum;
import com.mycompany.myapp.service.common.ServiceException;

public class Preconditions {

	public static void checkArgument(boolean... expressions) {
		for (boolean expression : expressions) {
			if (!expression) {
				throw new ServiceException(CommonMsgEnum.FAIL_BIZ_PARAM_ERROR);
			}
		}
	}

	public static void checkArgument(boolean expression, Object errorMessage) {
		if (!expression) {
			throw new ServiceException(String.valueOf(errorMessage),
					CommonMsgEnum.FAIL_BIZ_PARAM_ERROR.getCode());
		}

	}

	public static void checkNotNull(Object... args) {
		for (Object o : args) {
			if (o == null) {
				throw new ServiceException(CommonMsgEnum.FAIL_BIZ_PARAM_ERROR);
			}
		}
	}

	public static <T> T checkNotNull(T reference, Object errorMessage) {
		if (reference == null) {
			throw new ServiceException(String.valueOf(errorMessage),
					CommonMsgEnum.FAIL_BIZ_PARAM_ERROR.getCode());
		}
		return reference;
	}
}
