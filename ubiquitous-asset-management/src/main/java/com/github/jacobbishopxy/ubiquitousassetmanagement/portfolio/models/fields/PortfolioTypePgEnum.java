/**
 * Created by Jacob Xie on 2/28/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.fields;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.EnumType;

public class PortfolioTypePgEnum extends EnumType<PortfolioType> {

  public void nullSafeSet(
      PreparedStatement st,
      Object value,
      int index,
      SharedSessionContractImplementor session)
      throws HibernateException, SQLException {
    if (value == null) {
      st.setNull(index, Types.OTHER);
    } else {
      st.setObject(
          index,
          value.toString(),
          Types.OTHER);
    }
  }

}
