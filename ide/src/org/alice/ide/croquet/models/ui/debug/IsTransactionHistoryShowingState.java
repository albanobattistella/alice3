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
package org.alice.ide.croquet.models.ui.debug;

/**
 * @author Dennis Cosgrove
 */
public class IsTransactionHistoryShowingState extends org.alice.ide.croquet.models.IsFrameShowingState {
	private static class SingletonHolder {
		private static IsTransactionHistoryShowingState instance = new IsTransactionHistoryShowingState();
	}
	public static IsTransactionHistoryShowingState getInstance() {
		return SingletonHolder.instance;
	}

	private final org.alice.ide.croquet.models.ui.debug.components.TransactionHistoryPanel transactionHistoryPanel = new org.alice.ide.croquet.models.ui.debug.components.TransactionHistoryPanel();
	private final org.lgna.croquet.State.ValueListener< org.lgna.project.Project > projectListener = new org.lgna.croquet.State.ValueListener< org.lgna.project.Project >() {
		public void changing( org.lgna.croquet.State< org.lgna.project.Project > state, org.lgna.project.Project prevValue, org.lgna.project.Project nextValue, boolean isAdjusting ) {
		}
		public void changed( org.lgna.croquet.State< org.lgna.project.Project > state, org.lgna.project.Project prevValue, org.lgna.project.Project nextValue, boolean isAdjusting ) {
			org.lgna.croquet.history.TransactionHistory transactionHistory = org.alice.ide.IDE.getActiveInstance().getProjectTransactionHistory();
			IsTransactionHistoryShowingState.this.transactionHistoryPanel.setTransactionHistory( transactionHistory );
		}
	};
	private IsTransactionHistoryShowingState( ) {
		super( org.alice.ide.ProjectApplication.INFORMATION_GROUP, java.util.UUID.fromString( "a584d3f3-2fbd-4991-bbc6-98fb68c74e6f" ), false );
		org.alice.ide.project.ProjectState.getInstance().addAndInvokeValueListener( this.projectListener );
	}
	@Override
	protected void localize() {
		super.localize();
		this.setTextForBothTrueAndFalse( "Transaction History" );
	}
	@Override
	protected java.awt.Component createPane() {
		return this.transactionHistoryPanel.getAwtComponent();
	}
	@Override
	protected javax.swing.JFrame createFrame() {
		javax.swing.JFrame rv = super.createFrame();
		final int SCREEN_INDEX = 1;
		java.awt.Rectangle bounds = edu.cmu.cs.dennisc.java.awt.GraphicsDeviceUtilities.getScreenDeviceDefaultConfigurationBounds( SCREEN_INDEX );
		if( bounds != null ) {
			rv.setBounds( bounds );
		} else {
			org.lgna.croquet.Application application = org.lgna.croquet.Application.getActiveInstance();
			if( application != null ) {
				org.lgna.croquet.components.Frame frame = application.getFrame();
				if( frame != null ) {
					java.awt.Rectangle bounds2 = frame.getBounds();
					bounds2.x += bounds2.width;
					bounds2.width = 200;
					rv.setBounds( bounds2 );
				}
			}
		}
		return rv;
	}
}
