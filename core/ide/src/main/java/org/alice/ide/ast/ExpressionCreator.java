/*******************************************************************************
 * Copyright (c) 2006, 2015, Carnegie Mellon University. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Products derived from the software may not be called "Alice", nor may
 *    "Alice" appear in their name, without prior written permission of
 *    Carnegie Mellon University.
 *
 * 4. All advertising materials mentioning features or use of this software must
 *    display the following acknowledgement: "This product includes software
 *    developed by Carnegie Mellon University"
 *
 * 5. The gallery of art assets and animations provided with this software is
 *    contributed by Electronic Arts Inc. and may be used for personal,
 *    non-commercial, and academic use only. Redistributions of any program
 *    source code that utilizes The Sims 2 Assets must also retain the copyright
 *    notice, list of conditions and the disclaimer contained in
 *    The Alice 3.0 Art Gallery License.
 *
 * DISCLAIMER:
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND.
 * ANY AND ALL EXPRESS, STATUTORY OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,  FITNESS FOR A
 * PARTICULAR PURPOSE, TITLE, AND NON-INFRINGEMENT ARE DISCLAIMED. IN NO EVENT
 * SHALL THE AUTHORS, COPYRIGHT OWNERS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, PUNITIVE OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING FROM OR OTHERWISE RELATING TO
 * THE USE OF OR OTHER DEALINGS WITH THE SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *******************************************************************************/

package org.alice.ide.ast;

import edu.cmu.cs.dennisc.java.lang.DoubleUtilities;
import edu.cmu.cs.dennisc.java.lang.EnumUtilities;
import org.lgna.project.ast.DoubleLiteral;
import org.lgna.project.ast.Expression;
import org.lgna.project.ast.FieldAccess;
import org.lgna.project.ast.IntegerLiteral;
import org.lgna.project.ast.JavaField;
import org.lgna.project.ast.NullLiteral;
import org.lgna.project.ast.StringLiteral;
import org.lgna.project.ast.TypeExpression;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author Dennis Cosgrove
 */
public abstract class ExpressionCreator {
  public static final int MILLI_DECIMAL_PLACES = 3;
  public static final int MICRO_DECIMAL_PLACES = 6;
  public static final int DEFAULT_DECIMAL_PLACES = MICRO_DECIMAL_PLACES;

  public static final class CannotCreateExpressionException extends Exception {
    private final Object value;

    public CannotCreateExpressionException(Object value) {
      this.value = value;
    }

    public Object getValue() {
      return this.value;
    }
  }

  protected FieldAccess createPublicStaticFieldAccess(Field fld) {
    int modifiers = fld.getModifiers();
    if (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers)) {
      TypeExpression typeExpression = new TypeExpression(fld.getDeclaringClass());
      JavaField field = JavaField.getInstance(fld);
      return new FieldAccess(typeExpression, field);
    } else {
      throw new RuntimeException(fld.toGenericString());
    }
  }

  protected final Expression createDoubleExpression(Double value, int decimalPlaces) {
    value = DoubleUtilities.round(value, decimalPlaces);
    return new DoubleLiteral(value);
  }

  protected final Expression createDoubleExpression(Double value) {
    return this.createDoubleExpression(value, DEFAULT_DECIMAL_PLACES);
  }

  protected final Expression createIntegerExpression(Integer value) {
    return new IntegerLiteral(value);
  }

  //todo:
  public final Expression createStringExpression(String value) {
    if (value != null) {
      return new StringLiteral(value);
    } else {
      return new NullLiteral();
    }
  }

  //todo:
  public final Expression createEnumExpression(Enum<?> value) {
    if (value != null) {
      return this.createPublicStaticFieldAccess(EnumUtilities.getFld(value));
      //      org.lgna.project.ast.JavaType type = org.lgna.project.ast.JavaType.getInstance( value.getClass() );
      //      org.lgna.project.ast.AbstractField field = type.getDeclaredField( type, value.name() );
      //      return new org.lgna.project.ast.FieldAccess( new org.lgna.project.ast.TypeExpression( type ), field );
    } else {
      return new NullLiteral();
    }
  }

  protected abstract Expression createCustomExpression(Object value) throws CannotCreateExpressionException;

  public Expression createExpression(Object value) throws CannotCreateExpressionException {
    if (value != null) {
      if (value instanceof Double) {
        return this.createDoubleExpression((Double) value);
      } else if (value instanceof Integer) {
        return this.createIntegerExpression((Integer) value);
      } else if (value instanceof String) {
        return this.createStringExpression((String) value);
      } else if (value instanceof Enum<?>) {
        return this.createEnumExpression((Enum<?>) value);
      } else {
        return this.createCustomExpression(value);
      }
    } else {
      return new NullLiteral();
    }
  }
}
