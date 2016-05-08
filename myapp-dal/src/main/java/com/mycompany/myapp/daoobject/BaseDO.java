package com.mycompany.myapp.daoobject;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

public abstract class BaseDO implements Serializable {

  public abstract Long getId();
  public abstract void setId(Long id);

  public abstract Date getGmtModified();
  public abstract void setGmtModified(Date gmtModified);

  public abstract Date getGmtCreate();
  public abstract void setGmtCreate(Date gmtCreate);

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }

}
