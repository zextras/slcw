package com.zextras;

//todo fix when implemented

import com.zextras.persistence.annotations.Id;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.beanutils.BeanUtils;

/**
 * The SlcwBean class is a Data representation of an LDAP entry.
 * Operations on it managed with {@linkplain com.zextras.operations.executors.OperationExecutor}.
 *
 * @author davidefrison
 */
public abstract class SlcwBean {

  private String dn;

  /**
   * Name of the id attribute if any. If present its value is added to make the dn.
   */
  private final String idAttrName;

  public String getIdFieldName() {
    return idFieldName;
  }

  /**
   * Name of the field corresponding to id attribute
   */
  private final String idFieldName;

  public String getIdAttrName() {
    return idAttrName;
  }

  /**
   * Returns the value of filed annotated with {@link Id}.
   * Returns an empty string if value not set or no field has the annotation.
   *
   * @return string of id field
   */
  public String getIdStringValue() {
    if (!Objects.isNull(idAttrName)) {
      try {
        return BeanUtils.getSimpleProperty(this, this.getIdFieldName());
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      }
      return "";
    }
    return "";
  }

  public boolean hasIdAttribute() {
    return (!(Objects.isNull(idAttrName)));
  }

  public SlcwBean() {
    final Optional<Field> idField = Arrays.stream(this.getClass().getDeclaredFields()).filter(
            field -> field.isAnnotationPresent(Id.class))
        .findFirst();
    if (idField.isPresent()) {
      idAttrName = idField.get().getAnnotation(Id.class).name();
      idFieldName = idField.get().getName();
    } else {
      idAttrName = null;
      idFieldName = null;
    }
  };


  public String getDn() {
    return this.dn;
  }

  public void setDn(String dn) {
    this.dn = dn;
  }

}
