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
package org.alice.ide.member.views;

/**
 * @author Dennis Cosgrove
 */
public abstract class MemberTabView extends org.lgna.croquet.components.MigPanel {
	private final java.util.Map<org.lgna.project.ast.Member, org.lgna.croquet.components.JComponent<?>> map = edu.cmu.cs.dennisc.java.util.Collections.newHashMap();

	public MemberTabView( org.alice.ide.member.MemberTabComposite<?> composite ) {
		super( composite, "insets 0, fill", "[]", "[grow 0][]" );
	}

	private static org.lgna.croquet.components.JComponent<?> createDragView( org.lgna.project.ast.Member member ) {
		return new org.lgna.croquet.components.Label( member.getName() );
	}

	protected org.lgna.croquet.components.JComponent<?> getComponentFor( org.lgna.project.ast.Member member ) {
		synchronized( this.map ) {
			org.lgna.croquet.components.JComponent<?> rv = this.map.get( member );
			if( rv != null ) {
				//pass
			} else {
				rv = createDragView( member );
				this.map.put( member, rv );
			}
			return rv;
		}
	}

	@Override
	protected void internalRefresh() {
		super.internalRefresh();
		org.alice.ide.member.MemberTabComposite<?> composite = (org.alice.ide.member.MemberTabComposite<?>)this.getComposite();
		this.removeAllComponents();

		org.lgna.croquet.components.ComboBox<String> comboBox = composite.getSortState().getPrepModel().createComboBox();
		this.addComponent( comboBox, "align right, wrap" );

		org.lgna.croquet.components.MigPanel scrollPaneView = new org.lgna.croquet.components.MigPanel( null, "insets 0", "[]", "[]0[]" );
		for( org.alice.ide.member.MethodsSubComposite subComposite : composite.getSubComposites() ) {
			if( subComposite != org.alice.ide.member.MemberTabComposite.SEPARATOR ) {
				if( subComposite.isShowingDesired() ) {
					org.lgna.croquet.components.ToolPaletteView view = subComposite.getOuterComposite().getView();
					if( subComposite instanceof org.alice.ide.member.FunctionsOfReturnTypeSubComposite ) {
						view.getTitle().setHorizontalTextPosition( org.lgna.croquet.components.HorizontalTextPosition.LEADING );
					}
					view.getTitle().changeFont( edu.cmu.cs.dennisc.java.awt.font.TextPosture.OBLIQUE );
					if( org.alice.ide.member.MemberTabComposite.ARE_TOOL_PALETTES_INERT ) {
						view.getTitle().setInert( true );
					} else {
						view.getTitle().setRenderingStyle( org.lgna.croquet.components.ToolPaletteTitle.RenderingStyle.LIGHT_UP_ICON_ONLY );
					}
					view.setBackgroundColor( this.getBackgroundColor() );
					scrollPaneView.addComponent( view, "wrap" );
				}
			} else {
				scrollPaneView.addComponent( new org.lgna.croquet.components.HorizontalSeparator(), "wrap" );
			}
		}
		scrollPaneView.setBackgroundColor( this.getBackgroundColor() );
		org.lgna.croquet.components.ScrollPane scrollPane = new org.lgna.croquet.components.ScrollPane( scrollPaneView );
		this.addComponent( scrollPane, "grow" );
	}
}
