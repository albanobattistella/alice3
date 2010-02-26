/*
 * Copyright (c) 2006-2009, Carnegie Mellon University. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 * 
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 
 * 3. Products derived from the software may not be called "Alice",
 *    nor may "Alice" appear in their name, without prior written
 *    permission of Carnegie Mellon University.
 * 
 * 4. All advertising materials mentioning features or use of this software
 *    must display the following acknowledgement:
 *    "This product includes software developed by Carnegie Mellon University"
 */

package org.alice.ide.openprojectpane;

/**
 * @author Dennis Cosgrove
 */
public abstract class TabContentPane extends edu.cmu.cs.dennisc.croquet.swing.Pane {
	//	protected boolean isTabEnabled() {
	//		return this.isEnabled();
	//	}
	private edu.cmu.cs.dennisc.croquet.KInputPane< java.net.URI > inputPane;
	public TabContentPane() {
		this.setBackground( new java.awt.Color( 191, 191, 255 ) );
		this.setOpaque( true );
		final int INSET = 8;
		this.setBorder( javax.swing.BorderFactory.createEmptyBorder( INSET, INSET, INSET, INSET ) );
	}
	public void setInputPane( edu.cmu.cs.dennisc.croquet.KInputPane< java.net.URI > inputPane ) {
		this.inputPane = inputPane;
	}
	public abstract java.net.URI getSelectedURI();
	public javax.swing.Icon getTabTitleIcon() {
		return null;
	}
	public abstract String getTabTitleText();
	protected void updateOKButton() {
		if( this.inputPane != null ) {
			this.inputPane.updateOKButton();
		}
	}
	protected void fireOKButtonIfPossible() {
		if( this.inputPane != null ) {
			this.inputPane.fireOKButtonIfPossible();
		}
	}
}
