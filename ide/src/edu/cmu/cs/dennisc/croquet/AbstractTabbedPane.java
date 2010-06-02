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
public abstract class AbstractTabbedPane<E,D extends AbstractTabbedPane.TabItemDetails> extends ItemSelectable<E, D> {
	private static class CloseButtonUI extends javax.swing.plaf.basic.BasicButtonUI {
		private static final java.awt.Color BASE_COLOR = new java.awt.Color( 127, 63, 63 );
		private static final java.awt.Color HIGHLIGHT_COLOR = edu.cmu.cs.dennisc.java.awt.ColorUtilities.shiftHSB( BASE_COLOR, 0, 0, +0.25f );
		private static final java.awt.Color PRESS_COLOR = edu.cmu.cs.dennisc.java.awt.ColorUtilities.shiftHSB( BASE_COLOR, 0, 0, -0.125f );

		private int getIconWidth() {
			return 14;
		}
		private int getIconHeight() {
			return getIconWidth();
		}

		@Override
		public void paint(java.awt.Graphics g, javax.swing.JComponent c) {
			javax.swing.AbstractButton button = (javax.swing.AbstractButton)c;
			javax.swing.ButtonModel model = button.getModel();

			java.awt.Graphics2D g2 = (java.awt.Graphics2D)g;

			float size = Math.min( this.getIconWidth(), this.getIconHeight() ) * 0.9f;

			float w = size;
			float h = size * 0.25f;
			float xC = -w * 0.5f;
			float yC = -h * 0.5f;
			java.awt.geom.RoundRectangle2D.Float rr = new java.awt.geom.RoundRectangle2D.Float( xC, yC, w, h, h, h );

			java.awt.geom.Area area0 = new java.awt.geom.Area( rr );
			java.awt.geom.Area area1 = new java.awt.geom.Area( rr );

			java.awt.geom.AffineTransform m0 = new java.awt.geom.AffineTransform();
			m0.rotate( Math.PI * 0.25 );
			area0.transform( m0 );

			java.awt.geom.AffineTransform m1 = new java.awt.geom.AffineTransform();
			m1.rotate( Math.PI * 0.75 );
			area1.transform( m1 );

			area0.add( area1 );

			int x0 = 0;
			int y0 = 0;
			
			java.awt.geom.AffineTransform m = new java.awt.geom.AffineTransform();
			m.translate( x0 + getIconWidth() / 2, y0 + getIconWidth() / 2 );
			area0.transform( m );

			java.awt.Paint prevPaint = g2.getPaint();
			g2.setRenderingHint( java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON );
			if( model.isRollover() ) {
				if( model.isPressed() ) {
					g2.setPaint( PRESS_COLOR );
				} else {
					g2.setPaint( HIGHLIGHT_COLOR );
				}
			} else {
				g2.setPaint( java.awt.Color.WHITE );
			}
			javax.swing.AbstractButton parent = (javax.swing.AbstractButton)button.getParent();
			if( parent.isSelected() ) {
				g2.fill( area0 );
				g2.setPaint( java.awt.Color.BLACK );
			} else {
				g2.setPaint( java.awt.Color.DARK_GRAY );
			}
			g2.draw( area0 );
			g2.setPaint( prevPaint );
		}
		@Override
		public java.awt.Dimension getPreferredSize(javax.swing.JComponent c) {
			return new java.awt.Dimension( this.getIconWidth(), this.getIconHeight() );
		}
	}

	protected static abstract class JTabTitle extends javax.swing.AbstractButton {
		private java.awt.event.MouseListener mouseListener = new java.awt.event.MouseListener() {
			public void mouseEntered( java.awt.event.MouseEvent e ) {
				JTabTitle.this.getModel().setArmed( true );
			}
			public void mouseExited( java.awt.event.MouseEvent e ) {
				JTabTitle.this.getModel().setArmed( false );
			}
			public void mousePressed( java.awt.event.MouseEvent e ) {
				JTabTitle.this.getModel().setPressed( true );
			}
			public void mouseReleased( java.awt.event.MouseEvent e ) {
				JTabTitle.this.getModel().setPressed( false );
			}
			public void mouseClicked(java.awt.event.MouseEvent e) {
				JTabTitle.this.select();
			}
		};
		private void select() {
			this.setSelected( true );
			this.getParent().repaint();
			//this.getParent().repaint( this.getX(), this.getY(), this.getWidth() + EAST_TAB_PAD, this.getHeight() );
		}

		private javax.swing.JComponent jComponent;
		private javax.swing.JButton closeButton;
		private java.awt.event.ActionListener closeActionListener;
		public JTabTitle( javax.swing.JComponent jComponent, java.awt.event.ActionListener closeActionListener ) {
			this.jComponent = jComponent;
			this.setModel( new javax.swing.JToggleButton.ToggleButtonModel() );
			this.setLayout( new javax.swing.BoxLayout( this, javax.swing.BoxLayout.LINE_AXIS ) );
			this.add( this.jComponent );
			this.closeActionListener = closeActionListener;
			if( this.closeActionListener != null ) {
				this.closeButton = new javax.swing.JButton();
				this.closeButton.setUI( new CloseButtonUI() );
				this.closeButton.setBorder( javax.swing.BorderFactory.createEmptyBorder() );
				this.closeButton.setOpaque( false );
				this.add( this.closeButton );
			}
		}
		@Override
		public void setFont(java.awt.Font font) {
			super.setFont(font);
			this.jComponent.setFont( font );
		}
		@Override
		public void addNotify() {
			super.addNotify();
			this.addMouseListener( this.mouseListener );
			if( this.closeButton != null ) {
				assert this.closeActionListener != null;
				this.closeButton.addActionListener( this.closeActionListener );
			}
		}
		@Override
		public void removeNotify() {
			if( this.closeButton != null ) {
				assert this.closeActionListener != null;
				this.closeButton.removeActionListener( this.closeActionListener );
			}
			this.removeMouseListener( this.mouseListener );
			super.removeNotify();
		}
	}

	@Deprecated
	private final static BooleanState TAB_TITLE_BOOLEAN_STATE = null;
	
	protected static abstract class TabTitle extends AbstractButton< javax.swing.AbstractButton, BooleanState > {
		private JTabTitle jTabTitle;
		public TabTitle( JTabTitle jTabTitle ) {
			super( TAB_TITLE_BOOLEAN_STATE );
			edu.cmu.cs.dennisc.print.PrintUtilities.println( "todo: TAB_TITLE_BOOLEAN_STATE" );
			this.jTabTitle = jTabTitle;
		}
		@Override
		protected final javax.swing.AbstractButton createAwtComponent() {
			return this.jTabTitle;
		}
	}
	
	protected static java.awt.event.ActionListener getCloseButtonActionListener( boolean isCloseAffordanceDesired ) {
		if( isCloseAffordanceDesired ) {
			return new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					edu.cmu.cs.dennisc.print.PrintUtilities.println( "close" );
				}
			};
		} else {
			return null;
		}
	}

	
	protected class TabItemDetails extends ItemDetails {
		private java.util.UUID id;
		private JComponent<?> innerTitleComponent;
		private JComponent<?> mainComponent;
		private ScrollPane scrollPane;
		private boolean isCloseAffordanceDesired;
		public TabItemDetails( E item, AbstractButton< ?,? > button, java.util.UUID id, JComponent<?> innerTitleComponent, ScrollPane scrollPane, JComponent<?> mainComponent, boolean isCloseAffordanceDesired ) {
			super( item, button );
			assert id != null;
			this.id = id;
			this.innerTitleComponent = innerTitleComponent;
			this.mainComponent = mainComponent;
			this.scrollPane = scrollPane;
			this.isCloseAffordanceDesired = isCloseAffordanceDesired;

			button.setBackgroundColor( this.mainComponent.getBackgroundColor() );
			this.scrollPane.setViewportView( this.mainComponent );
		}
		public java.util.UUID getId() {
			return this.id;
		}
		public JComponent< ? > getInnerTitleComponent() {
			return this.innerTitleComponent;
		}
		public JComponent< ? > getMainComponent() {
			return this.mainComponent;
		}
		public ScrollPane getScrollPane() {
			return this.scrollPane;
		}
		public boolean isCloseAffordanceDesired() {
			return this.isCloseAffordanceDesired;
		}
	}
	private ItemSelectionState.TabCreator< E > tabCreator;
	@Override
	public void setFont(java.awt.Font font) {
		super.setFont( font );
		for( D tabItemDetails : this.getAllItemDetails() ) {
			tabItemDetails.getInnerTitleComponent().setFont( font );
		}
	}

	protected abstract D createTabItemDetails( E item, java.util.UUID id, JComponent<?> innerTitleComponent, ScrollPane scrollPane, JComponent<?> mainComponent, boolean isCloseAffordanceDesired );
	@Override
	protected final D createItemDetails(E item) {
		java.util.UUID id = this.tabCreator.getId( item );
		assert id != null : item;
		return createTabItemDetails( item, id, this.tabCreator.createInnerTitleComponent( item ), this.tabCreator.createScrollPane( item ), this.tabCreator.createMainComponent( item ), this.tabCreator.isCloseAffordanceDesired() );
	}
	
	public AbstractTabbedPane( ItemSelectionState<E> model, ItemSelectionState.TabCreator< E > tabCreator ) {
		super( model );
		this.tabCreator = tabCreator;
	}

	
	@Deprecated
	public AbstractButton<?,?> getTabTitle( E item ) {
		TabItemDetails tabItemDetails = this.getItemDetails( item );
		if( tabItemDetails != null ) {
			return tabItemDetails.getButton();
		} else {
			return null;
		}
	}
	@Deprecated
	public JComponent<?> getMainComponent( E item ) {
		TabItemDetails tabItemDetails = this.getItemDetails( item );
		if( tabItemDetails != null ) {
			return tabItemDetails.getMainComponent();
		} else {
			return null;
		}
	}
	@Deprecated
	public ScrollPane getScrollPane( E item ) {
		TabItemDetails tabItemDetails = this.getItemDetails( item );
		if( tabItemDetails != null ) {
			return tabItemDetails.getScrollPane();
		} else {
			return null;
		}
	}
	
		
//	protected abstract Tab<E> createTab( E item, ItemSelectionOperation.TabCreator< E > tabCreator );
//	protected abstract void addTab( Tab<E> tab );
//	protected abstract void removeTab( Tab<E> tab );
//	@Override
//	protected AbstractButton<?> createButton(E item) {
//		Tab< E > tab = createTab( item, this.tabCreator );
//		AbstractButton<?> rv = tab.getOuterTitleComponent();
//		map.put(rv, tab);
//		return rv;
//	}
//	@Override
//	protected void removeAllButtons() {
//		for( Tab<E> tab : this.map.values() ) {
//			this.removeTab( tab );
//		}
//	}
//	@Override
//	protected void addPrologue( int count ) {
//	}
//	@Override
//	protected final void addButton(edu.cmu.cs.dennisc.croquet.AbstractButton<?> button) {
//		Tab< E > tab = this.map.get( button );
//		assert tab != null;
//		this.addTab( tab );
//	}
//	@Override
//	protected void addEpilogue() {
//	}
//	
//	@Override
//	protected void handleItemSelected(E item) {
//		javax.swing.JOptionPane.showMessageDialog( null, "todo: handleItemSelected" );
//	}
	
//	/* package-private */ void addTab( E item ) {
//		Tab<E> tab = this.map.get( item );
//		if( tab != null ) {
//			//pass
//		} else {
//			tab = this.createTab( item, this.tabCreator );
//			this.map.put( item, tab );
//		}
//		this.revalidateAndRepaint();
//	}
//	/* package-private */ void removeTab( E item ) {
//		this.revalidateAndRepaint();
//	}
//	/* package-private */ void selectTab( E item ) {
//		Tab<E> tab = this.map.get( item );
//		assert tab != null;
//		tab.select();
//	}
}
