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
package org.lgna.croquet.views;

import edu.cmu.cs.dennisc.javax.swing.components.JSuggestivePasswordField;
import org.lgna.croquet.Operation;
import org.lgna.croquet.StringState;

import java.awt.Dimension;

/**
 * @author Dennis Cosgrove
 */
public class PasswordField extends AbstractTextField<JSuggestivePasswordField> {
  public PasswordField(StringState model, Operation operation) {
    super(model, operation);
  }

  public PasswordField(StringState model) {
    this(model, null);
  }

  @Override
  public void updateTextForBlankCondition(String textForBlankCondition) {
    this.getAwtComponent().setTextForBlankCondition(textForBlankCondition);
  }

  @Override
  protected JSuggestivePasswordField createAwtComponent() {
    JSuggestivePasswordField rv = new JSuggestivePasswordField() {
      @Override
      public Dimension getPreferredSize() {
        return constrainPreferredSizeIfNecessary(super.getPreferredSize());
      }

      @Override
      public Dimension getMaximumSize() {
        if (PasswordField.this.isMaximumSizeClampedToPreferredSize()) {
          return this.getPreferredSize();
        } else {
          return super.getMaximumSize();
        }
      }
    };
    rv.setTextForBlankCondition(this.getModel().getTextForBlankCondition());
    return rv;
  }

  private static final char EXPOSE_CHAR = 0;

  public boolean isExposed() {
    return this.getAwtComponent().getEchoChar() != EXPOSE_CHAR;
  }

  public void setExposed(boolean isExposed) {
    this.checkEventDispatchThread();
    char c = isExposed ? EXPOSE_CHAR : '*';
    this.getAwtComponent().setEchoChar(c);
  }

}
