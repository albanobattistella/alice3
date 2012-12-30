/**
 * Copyright (c) 2006-2012, Carnegie Mellon University. All rights reserved.
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
package org.alice.ide.member;

/**
 * @author Dennis Cosgrove
 */
public abstract class UserMethodsSubComposite extends MethodsSubComposite {
	private final org.lgna.project.ast.NamedUserType type;
	private final org.lgna.croquet.Operation addMethodOperation;

	private final edu.cmu.cs.dennisc.property.event.ListPropertyListener<org.lgna.project.ast.UserMethod> methodPropertyListener = new edu.cmu.cs.dennisc.property.event.SimplifiedListPropertyAdapter<org.lgna.project.ast.UserMethod>() {
		@Override
		protected void changing( edu.cmu.cs.dennisc.property.event.ListPropertyEvent<org.lgna.project.ast.UserMethod> e ) {
		}

		@Override
		protected void changed( edu.cmu.cs.dennisc.property.event.ListPropertyEvent<org.lgna.project.ast.UserMethod> e ) {
			getView().refreshLater();
		}
	};

	public UserMethodsSubComposite( java.util.UUID migrationId, org.lgna.project.ast.NamedUserType type, org.lgna.croquet.Operation addMethodOperation ) {
		super( migrationId, true );
		this.type = type;
		this.addMethodOperation = addMethodOperation;
		this.getOuterComposite().getIsExpandedState().setIconForBothTrueAndFalse( new org.alice.ide.common.TypeIcon( this.type ) );

		//todo: move to handlePreActivation/handlePostDeactivation
		type.methods.addListPropertyListener( this.methodPropertyListener );
	}

	public final org.lgna.croquet.Operation getAddMethodOperation() {
		return this.addMethodOperation;
	}

	protected abstract boolean isAcceptable( org.lgna.project.ast.AbstractMethod method );

	@Override
	public java.util.List<? extends org.lgna.project.ast.AbstractMethod> getMethods() {
		java.util.List<org.lgna.project.ast.UserMethod> rv = edu.cmu.cs.dennisc.java.util.Collections.newLinkedList();
		for( org.lgna.project.ast.UserMethod method : this.type.getDeclaredMethods() ) {
			if( this.isAcceptable( method ) ) {
				rv.add( method );
			}
		}
		return rv;
	}

	@Override
	protected org.alice.ide.member.views.UserMethodsSubView createView() {
		return new org.alice.ide.member.views.UserMethodsSubView( this );
	}

	//	@Override
	//	public void handlePreActivation() {
	//		super.handlePreActivation();
	//		type.methods.addListPropertyListener( this.methodPropertyListener );
	//	}
	//
	//	@Override
	//	public void handlePostDeactivation() {
	//		type.methods.removeListPropertyListener( this.methodPropertyListener );
	//		super.handlePostDeactivation();
	//	}
}
