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
package org.alice.ide.croquet.models.ast.cascade;

/**
 * @author Dennis Cosgrove
 */
public class MoreCascade extends org.lgna.croquet.Cascade< edu.cmu.cs.dennisc.alice.ast.Expression > {
	private static java.util.Map< edu.cmu.cs.dennisc.alice.ast.MethodInvocation, MoreCascade > map = edu.cmu.cs.dennisc.java.util.Collections.newHashMap();
	public static synchronized MoreCascade getInstance( edu.cmu.cs.dennisc.alice.ast.MethodInvocation methodInvocation ) {
		MoreCascade rv = map.get( methodInvocation );
		if( rv != null ) {
			//pass
		} else {
			rv = new MoreCascade( methodInvocation );
			map.put( methodInvocation, rv );
		}
		return rv;
	}
	
	private static edu.cmu.cs.dennisc.alice.ast.AbstractParameter getNextParameter( edu.cmu.cs.dennisc.alice.ast.MethodInvocation methodInvocation ) {
		edu.cmu.cs.dennisc.alice.ast.AbstractMethod method = methodInvocation.method.getValue();
		edu.cmu.cs.dennisc.alice.ast.AbstractMethod nextMethod = (edu.cmu.cs.dennisc.alice.ast.AbstractMethod)method.getNextLongerInChain();
		java.util.ArrayList< ? extends edu.cmu.cs.dennisc.alice.ast.AbstractParameter > parameters = nextMethod.getParameters();
		return parameters.get( parameters.size()-1 );
	}
	
	private final edu.cmu.cs.dennisc.alice.ast.MethodInvocation methodInvocation;
	private final edu.cmu.cs.dennisc.alice.ast.ExpressionStatement expressionStatement;
	private final edu.cmu.cs.dennisc.alice.ast.MethodInvocation nextMethodInvocation;
	private MoreCascade( edu.cmu.cs.dennisc.alice.ast.MethodInvocation methodInvocation ) {
		super( org.alice.ide.IDE.PROJECT_GROUP, java.util.UUID.fromString( "7ed06ae1-3704-4745-afd2-47dc21366412" ), edu.cmu.cs.dennisc.alice.ast.Expression.class, org.alice.ide.croquet.models.cascade.ParameterBlank.getInstance( getNextParameter( methodInvocation ) ) );
		assert methodInvocation != null;
		this.methodInvocation = methodInvocation;
		this.expressionStatement = (edu.cmu.cs.dennisc.alice.ast.ExpressionStatement)this.methodInvocation.getParent();
		assert this.expressionStatement != null : ((edu.cmu.cs.dennisc.alice.ast.MethodDeclaredInJava)this.methodInvocation.method.getValue()).getMethodReflectionProxy().getReification();
		
		edu.cmu.cs.dennisc.alice.ast.AbstractMethod method = this.methodInvocation.method.getValue();
		edu.cmu.cs.dennisc.alice.ast.AbstractMethod nextMethod = (edu.cmu.cs.dennisc.alice.ast.AbstractMethod)method.getNextLongerInChain();
		this.nextMethodInvocation = new edu.cmu.cs.dennisc.alice.ast.MethodInvocation();
		this.nextMethodInvocation.method.setValue( nextMethod );
		for( edu.cmu.cs.dennisc.alice.ast.AbstractParameter parameter : nextMethod.getParameters() ) {
			edu.cmu.cs.dennisc.alice.ast.Argument argument = new edu.cmu.cs.dennisc.alice.ast.Argument( parameter, null );
			this.nextMethodInvocation.arguments.add( argument );
		}
//		this.updateToolTipText();
	}

//	@Override
//	protected edu.cmu.cs.dennisc.croquet.Group getItemGroup() {
//		return org.alice.ide.IDE.PROJECT_GROUP;
//	}

	public edu.cmu.cs.dennisc.alice.ast.ExpressionStatement getExpressionStatement() {
		return this.expressionStatement;
	}
	public edu.cmu.cs.dennisc.alice.ast.MethodInvocation getPrevMethodInvocation() {
		return this.methodInvocation;
	}
	public edu.cmu.cs.dennisc.alice.ast.MethodInvocation getNextMethodInvocation() {
		return this.nextMethodInvocation;
	}
	
//	@Override
//	public edu.cmu.cs.dennisc.alice.ast.Expression getPreviousExpression() {
//		return null;
//	}
//	@Override
//	protected edu.cmu.cs.dennisc.alice.ast.Statement getStatement() {
//		return this.expressionStatement;
//	}
//	
//	private edu.cmu.cs.dennisc.alice.ast.AbstractParameter getLastParameter() {
//		edu.cmu.cs.dennisc.alice.ast.AbstractMethod method = this.nextMethodInvocation.method.getValue();
//		java.util.ArrayList< ? extends edu.cmu.cs.dennisc.alice.ast.AbstractParameter > parameters = method.getParameters();
//		return parameters.get( parameters.size()-1 );
//	}
//
//	@Override
//	protected String getTitle() {
//		edu.cmu.cs.dennisc.alice.ast.AbstractParameter lastParameter = this.getLastParameter();
//		return lastParameter.getName();
//	}
//	
//	
//	@Override
//	protected edu.cmu.cs.dennisc.alice.ast.AbstractType< ?, ?, ? > getDesiredValueType() {
//		edu.cmu.cs.dennisc.alice.ast.AbstractParameter lastParameter = this.getLastParameter();
//		return lastParameter.getDesiredValueType();
//	}
	@Override
	protected org.alice.ide.croquet.edits.ast.FillInMoreEdit createEdit(org.lgna.croquet.history.CascadeCompletionStep<edu.cmu.cs.dennisc.alice.ast.Expression> step, edu.cmu.cs.dennisc.alice.ast.Expression[] values) {
		return new org.alice.ide.croquet.edits.ast.FillInMoreEdit( step, values[ 0 ] );
	}
}
