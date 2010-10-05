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
package org.alice.ide.croquet.models.project;

/**
 * @author Dennis Cosgrove
 */
public class ManageResourcesOperation extends edu.cmu.cs.dennisc.croquet.InputDialogOperation<org.alice.ide.resource.manager.ResourceManagerPane> {
	private static class SingletonHolder {
		private static ManageResourcesOperation instance = new ManageResourcesOperation();
	}
	public static ManageResourcesOperation getInstance() {
		return SingletonHolder.instance;
	}
	private ManageResourcesOperation() {
		super( ENCLOSING_INPUT_DIALOG_GROUP, java.util.UUID.fromString( "ec7dc4b0-d1f8-420d-b6f0-7a25bd92639d" ) );
		edu.cmu.cs.dennisc.print.PrintUtilities.println( "todo: ManageResourcesOperation handle cancel" );
	}
	@Override
	protected org.alice.ide.resource.manager.ResourceManagerPane prologue(edu.cmu.cs.dennisc.croquet.InputDialogOperationContext<org.alice.ide.resource.manager.ResourceManagerPane> context) {
		return new org.alice.ide.resource.manager.ResourceManagerPane();
	}
	@Override
	protected void epilogue(edu.cmu.cs.dennisc.croquet.InputDialogOperationContext<org.alice.ide.resource.manager.ResourceManagerPane> context, boolean isOk) {
		if( isOk ) {
			context.finish();
		} else {
			edu.cmu.cs.dennisc.print.PrintUtilities.println( "todo: handle cancel" );
			context.cancel();
		}
	}
//	@Override
//	protected void performInternal(edu.cmu.cs.dennisc.croquet.Context context, java.util.EventObject e, edu.cmu.cs.dennisc.croquet.Component<?> component) {
//		org.alice.ide.resource.manager.ResourceManagerPane resourcesPane = new org.alice.ide.resource.manager.ResourceManagerPane();
//		this.getIDE().showMessageDialog( resourcesPane, "Project Resources", edu.cmu.cs.dennisc.croquet.MessageType.PLAIN, null );
//	}	
}
