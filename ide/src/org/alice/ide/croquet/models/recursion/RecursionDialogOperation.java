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
package org.alice.ide.croquet.models.recursion;

class RecursionPanel extends edu.cmu.cs.dennisc.croquet.BorderPanel {
	private static final int SPACING = 16;
	private static final float FONT_SCALE_FACTOR = 1.5f;
	private static final int INDENT = 64;
	private static class RecursionAccessPanel extends edu.cmu.cs.dennisc.croquet.PageAxisPanel {
		private edu.cmu.cs.dennisc.croquet.Label label;
		private edu.cmu.cs.dennisc.croquet.Button button = RecursionDialogOperation.getInstance().createButton();
		private edu.cmu.cs.dennisc.croquet.CheckBox checkBox = IsRecursionAllowedState.getInstance().createCheckBox();
		public RecursionAccessPanel( String explanationB ) {
			this.label = new edu.cmu.cs.dennisc.croquet.Label( explanationB, edu.cmu.cs.dennisc.java.awt.font.TextPosture.OBLIQUE );
			//bottomPanel.addComponent( explanationBLabel );
			edu.cmu.cs.dennisc.croquet.LineAxisPanel lineAxisPanel = new edu.cmu.cs.dennisc.croquet.LineAxisPanel(
					label, button
			);
			//lineAxisPanel.setBorder( javax.swing.BorderFactory.createEmptyBorder( SPACING, 0, SPACING, 0 ) );
			checkBox.scaleFont( FONT_SCALE_FACTOR );
			this.addComponent( checkBox );
			this.addComponent( lineAxisPanel );
			this.setBorder( javax.swing.BorderFactory.createEmptyBorder( 8,INDENT,8,0 ) );
		}
		
		@Override
		protected javax.swing.JPanel createJPanel() {
			return new DefaultJPanel() {
				@Override
				public boolean contains( int x, int y ) {
					if( IsAccessToRecursionAllowedEnabledState.getInstance().getValue() ) {
						return super.contains( x, y );
					} else {
						return false;
					}
				}
				@Override
				public void paint(java.awt.Graphics g) {
					super.paint(g);
					if( IsAccessToRecursionAllowedEnabledState.getInstance().getValue() ) {
						//pass
					} else {
						java.awt.Graphics2D g2 = (java.awt.Graphics2D)g;
						java.awt.Paint prevPaint = g2.getPaint();
						try {
							g2.setPaint( edu.cmu.cs.dennisc.croquet.PaintUtilities.getDisabledTexturePaint() );
							g2.fill( g2.getClipBounds() );
						} finally {
							g2.setPaint( prevPaint );
						}
						
					}
				}
			};
		}
		private edu.cmu.cs.dennisc.croquet.BooleanState.ValueObserver valueObserver = new edu.cmu.cs.dennisc.croquet.BooleanState.ValueObserver() {
			public void changing(boolean nextValue) {
			}
			public void changed(boolean nextValue) {
				if( nextValue ) {
					//pass
				} else {
					IsRecursionAllowedState.getInstance().setValue( false );
				}
				label.getAwtComponent().setEnabled( nextValue );
				button.getAwtComponent().setEnabled( nextValue );
				checkBox.getAwtComponent().setEnabled( nextValue );
				RecursionAccessPanel.this.repaint();
			}
		};
		@Override
		protected void handleDisplayable() {
			super.handleDisplayable();
			IsAccessToRecursionAllowedEnabledState.getInstance().addAndInvokeValueObserver( valueObserver );
		}
		@Override
		protected void handleUndisplayable() {
			IsAccessToRecursionAllowedEnabledState.getInstance().removeValueObserver( valueObserver );
			super.handleUndisplayable();
		}
	}

	public RecursionPanel( String explanationA, String explanationB ) {
		//todo
		org.alice.ide.croquet.models.help.BrowserOperation browserOperation = new org.alice.ide.croquet.models.help.BrowserOperation( java.util.UUID.fromString( "30e5e6e1-39ca-4c0f-a4a5-17e3f0e8212d" ), "http://help.alice.org/recursion" );
		edu.cmu.cs.dennisc.croquet.Hyperlink hyperlink = browserOperation.createHyperlink();
		hyperlink.scaleFont( FONT_SCALE_FACTOR );
		hyperlink.setBorder( javax.swing.BorderFactory.createEmptyBorder( SPACING, INDENT, SPACING, 0 ) );
		
		
		edu.cmu.cs.dennisc.croquet.CheckBox checkBox = IsAccessToRecursionAllowedEnabledState.getInstance().createCheckBox(); 
		checkBox.scaleFont( FONT_SCALE_FACTOR );

		
		edu.cmu.cs.dennisc.croquet.PageAxisPanel pageAxisPanel = new edu.cmu.cs.dennisc.croquet.PageAxisPanel();
		pageAxisPanel.addComponent( new edu.cmu.cs.dennisc.croquet.Label( explanationA, FONT_SCALE_FACTOR ) );
		pageAxisPanel.addComponent( hyperlink );
		pageAxisPanel.addComponent( checkBox );

		
		edu.cmu.cs.dennisc.croquet.BorderPanel borderPanel = new edu.cmu.cs.dennisc.croquet.BorderPanel();
		borderPanel.addComponent( pageAxisPanel, Constraint.PAGE_START );
		borderPanel.addComponent( new RecursionAccessPanel( explanationB ), Constraint.CENTER );

		borderPanel.setBorder( javax.swing.BorderFactory.createEmptyBorder(0,16,0,0));
		this.addComponent( borderPanel, Constraint.CENTER );
		this.addComponent( new edu.cmu.cs.dennisc.croquet.Label( new javax.swing.ImageIcon( RecursionPanel.class.getResource( "images/key.png" ) ) ), Constraint.LINE_START );

		this.setBorder( javax.swing.BorderFactory.createEmptyBorder( 8,8,8,8 ) );
	}
}

/**
 * @author Dennis Cosgrove
 */
public class RecursionDialogOperation extends edu.cmu.cs.dennisc.croquet.PlainDialogOperation {
	private static class SingletonHolder {
		private static RecursionDialogOperation instance = new RecursionDialogOperation();
	}
	public static RecursionDialogOperation getInstance() {
		return SingletonHolder.instance;
	}
	private RecursionDialogOperation() {
		super( edu.cmu.cs.dennisc.croquet.Application.UI_STATE_GROUP, java.util.UUID.fromString( "a5e1ded2-18c7-4ae5-8676-e6deca5650fe" ) );
	}
	@Override
	protected RecursionPanel createContentPane(edu.cmu.cs.dennisc.croquet.PlainDialogOperationContext context, edu.cmu.cs.dennisc.croquet.Dialog dialog) {
		String explanationA = "<html>Recursion is disabled by default because otherwise many users unwittingly and mistakenly make recursive calls.<p><p>Recursion is a powerful tool in computer science.  It is not to be feared.  It simply needs to be understood.<p><p>For more information on recursion, please see:</html>";
		String explanationB = "Hopefully, this button makes sense to you:  ";
		return new RecursionPanel( explanationA, explanationB );
	}
	
	private static int getDepth( edu.cmu.cs.dennisc.croquet.HistoryNode node, int depth ) {
		edu.cmu.cs.dennisc.croquet.HistoryNode parent = node.getParent();
		if( parent != null ) {
			return getDepth( parent, depth+1 );
		} else {
			return depth;
		}
	}
	
	@Override
	protected void tweakDialog(edu.cmu.cs.dennisc.croquet.Dialog dialog, edu.cmu.cs.dennisc.croquet.PlainDialogOperationContext context ) {
		super.tweakDialog(dialog, context);
		int depth = getDepth( context, 1 );
		int offset = (depth-5)*24;
		java.awt.Point p = dialog.getLocation();
		p.x += offset;
		p.y += offset;
		dialog.setLocation( p );
	}
	@Override
	protected void releaseContentPane(edu.cmu.cs.dennisc.croquet.PlainDialogOperationContext context, edu.cmu.cs.dennisc.croquet.Dialog dialog, edu.cmu.cs.dennisc.croquet.Container<?> contentPane) {
	}
	@Override
	protected java.awt.Dimension getDesiredDialogSize(edu.cmu.cs.dennisc.croquet.Dialog dialog) {
		return new java.awt.Dimension( 760, 400 );
	}
//	public static void main(String[] args) {
//		org.alice.stageide.StageIDE ide = new org.alice.stageide.StageIDE();
//		RecursionDialogOperation.getInstance().fire();
//		System.exit( 0 );
//	}
}
