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
package org.alice.ide.operations.run;

/**
 * @author Dennis Cosgrove
 */
public class PreviewMethodOperation extends org.alice.ide.operations.AbstractActionOperation {
	private org.alice.ide.memberseditor.templates.ProcedureInvocationTemplate procedureInvocationTemplate;
	public PreviewMethodOperation( org.alice.ide.memberseditor.templates.ProcedureInvocationTemplate procedureInvocationTemplate ) {
		super( edu.cmu.cs.dennisc.zoot.ZManager.UNKNOWN_GROUP, java.util.UUID.fromString( "9414c780-1ba2-4b00-8cb2-3c066f0063d5" ) );
		this.setName( "Preview..." );
		this.procedureInvocationTemplate = procedureInvocationTemplate;
	}
	@Override
	protected void perform( final edu.cmu.cs.dennisc.croquet.Context context, java.awt.event.ActionEvent e, edu.cmu.cs.dennisc.croquet.KAbstractButton< ? > button ) {
		java.awt.event.MouseEvent mouseEvent = new java.awt.event.MouseEvent( this.procedureInvocationTemplate.getJComponent(), 0, 0, 0, this.procedureInvocationTemplate.getWidth(), this.procedureInvocationTemplate.getHeight(), 0, false );
		edu.cmu.cs.dennisc.zoot.event.DragAndDropEvent dragAndDropEvent = new edu.cmu.cs.dennisc.zoot.event.DragAndDropEvent( this.procedureInvocationTemplate.getJComponent(), null, mouseEvent );
		this.procedureInvocationTemplate.createStatement( dragAndDropEvent, null, new edu.cmu.cs.dennisc.task.TaskObserver< edu.cmu.cs.dennisc.alice.ast.Statement >() {
			public void handleCompletion( edu.cmu.cs.dennisc.alice.ast.Statement statement ) {
				edu.cmu.cs.dennisc.alice.ast.ExpressionStatement expressionStatement = edu.cmu.cs.dennisc.java.lang.ClassUtilities.getInstance( statement, edu.cmu.cs.dennisc.alice.ast.ExpressionStatement.class );
				edu.cmu.cs.dennisc.alice.ast.MethodInvocation methodInvocation = edu.cmu.cs.dennisc.java.lang.ClassUtilities.getInstance( expressionStatement.expression.getValue(), edu.cmu.cs.dennisc.alice.ast.MethodInvocation.class );
				methodInvocation.expression.setValue( null );
				PreviewMethodOperation.this.getIDE().handlePreviewMethod( context, methodInvocation );
				context.commit();
			}
			public void handleCancelation() {
				context.cancel();
			}
		} );
	}
}
