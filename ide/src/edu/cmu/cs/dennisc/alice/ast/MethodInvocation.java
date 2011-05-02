/*
 * Copyright (c) 2006-2010, Carnegie Mellon University. All rights reserved.
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
 */

package edu.cmu.cs.dennisc.alice.ast;

/**
 * @author Dennis Cosgrove
 */
public class MethodInvocation extends Expression {
	public ExpressionProperty expression = new ExpressionProperty( this ) {
		@Override
		public AbstractType<?,?,?> getExpressionType() {
			return method.getValue().getDeclaringType();
		}
	};
	public DeclarationProperty< AbstractMethod > method = new DeclarationProperty< AbstractMethod >( this );
	public ArgumentListProperty arguments = new ArgumentListProperty( this );

	public MethodInvocation() {
	}
	public MethodInvocation( Expression expression, AbstractMethod method, Argument... arguments ){
		if( expression instanceof NullLiteral ) {
			//pass
		} else {
			AbstractType<?,?,?> expressionType = expression.getType();
			if( expressionType != null ) {
				AbstractType<?,?,?> declaringType = method.getDeclaringType();
				if( declaringType != null ) {
					//todo
					//assert declaringType.isAssignableFrom( expressionType );
				}
			}
		}
		this.expression.setValue( expression );
		this.method.setValue( method );
		this.arguments.add( arguments );
	}
	@Override
	public AbstractType<?,?,?> getType() {
		return method.getValue().getReturnType();
	}
	
	public boolean isValid() {
		boolean rv = false;
		Expression e = expression.getValue();
		AbstractMethod m = method.getValue();
		if( e != null && m != null ) {
			if( m.isValid() ) {
				if( m.isStatic() ) {
					//todo
					rv = true;
				} else {
					AbstractType<?,?,?> declaringType = m.getDeclaringType();
					AbstractType<?,?,?> expressionType = e.getType();
					if( expressionType instanceof AnonymousInnerTypeDeclaredInAlice ) {
						//todo
						rv = true;
					} else {
						if( declaringType != null && expressionType != null ) {
							rv = declaringType.isAssignableFrom( expressionType );
						} else {
							rv = false;
						}
					}
				}
			} else {
				rv = false;
			}
		} else {
			rv = false;
		}
		return rv;
	}
	
	@Override
	protected StringBuilder appendRepr( StringBuilder rv, java.util.Locale locale ) {
//		NodeUtilities.safeAppendRepr( rv, this.expression.getValue(), locale );
//		rv.append( "." );
		NodeUtilities.safeAppendRepr( rv, this.method.getValue(), locale );
		rv.append( "(" );
		String separator = "";
		for( Argument argument : this.arguments ) {
			rv.append( separator );
			NodeUtilities.safeAppendRepr( rv, argument.expression.getValue(), locale );
			separator = ", ";
		}
		rv.append( ")" );
		return rv;
	}
	
}
