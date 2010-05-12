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

package edu.cmu.cs.dennisc.croquet;

/**
 * @author Dennis Cosgrove
 */
//todo: better name
public abstract class Root<W extends java.awt.Window> {
	private static java.util.Map< java.awt.Component, Root<?> > map = edu.cmu.cs.dennisc.java.util.Collections.newWeakHashMap();
	/*package-private*/ static Root<?> lookup( java.awt.Component component ) {
		if( component != null ) {
			return Root.map.get( component );
		} else {
			return null;
		}
	}

	private W window;
	public Root( W window ) {
		this.window = window;
		this.getRootPane().setContentPane( this.contentPanel.getJComponent() );
		Root.map.put( window, this );
	}

	/*protected*/ public final W getAwtWindow() {
		return this.window;
	}
	protected abstract javax.swing.JRootPane getRootPane();

	private BorderPanel contentPanel = new BorderPanel(); 
	public BorderPanel getContentPanel() {
		return this.contentPanel;
	}
	
	public void addWindowListener( java.awt.event.WindowListener listener ) {
		this.window.addWindowListener( listener );
	}
	public void removeWindowListener( java.awt.event.WindowListener listener ) {
		this.window.removeWindowListener( listener );
	}
	public void addWindowStateListener( java.awt.event.WindowStateListener listener ) {
		this.window.addWindowStateListener( listener );
	}
	public void removeWindowStateListener( java.awt.event.WindowStateListener listener ) {
		this.window.removeWindowStateListener( listener );
	}

	public boolean isVisible() {
		return this.window.isVisible();
	}
	public void setVisible( boolean isVisible ) {
		this.window.setVisible( isVisible );
	}
	
	public Button getDefaultButton() {
		return (Button)Component.lookup( this.getRootPane().getDefaultButton() );
	}
	public void setDefaultButton( Button button ) {
		this.getRootPane().setDefaultButton( button.getJComponent() );
	}
	
	public void pack() {
		this.getAwtWindow().pack();
	}
}
