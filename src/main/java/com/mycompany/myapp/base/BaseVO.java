package com.mycompany.myapp.base;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class BaseVO {
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.JSON_STYLE);
	}
}
