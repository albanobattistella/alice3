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
package org.alice.ide.common;

/**
 * @author Dennis Cosgrove
 */
public abstract class NodeLikeSubstance extends org.alice.ide.Component {
	protected static final int KNURL_WIDTH = 8;
	private java.awt.Paint enabledBackgroundPaint;
	public NodeLikeSubstance() {
		if( this.isKnurlDesired() ) {
			this.setCursor( java.awt.Cursor.getPredefinedCursor( java.awt.Cursor.HAND_CURSOR ) );
		}
		this.setEnabledBackgroundPaint( null );
	}
	protected void setEnabledBackgroundPaint( java.awt.Paint enabledBackgroundPaint ) {
		this.enabledBackgroundPaint = enabledBackgroundPaint;
	}

	@Override
	protected java.awt.LayoutManager createLayoutManager( javax.swing.JPanel jPanel ) {
		return new javax.swing.BoxLayout( jPanel, javax.swing.BoxLayout.LINE_AXIS );
	}

	protected edu.cmu.cs.dennisc.java.awt.BevelState getBevelState() {
		if( this.isActive() ) {
			if( this.isPressed() ) {
				return edu.cmu.cs.dennisc.java.awt.BevelState.SUNKEN;
			} else {
				return edu.cmu.cs.dennisc.java.awt.BevelState.RAISED;
			}
		} else {
			return edu.cmu.cs.dennisc.java.awt.BevelState.FLUSH;
		}
	}

	protected abstract int getDockInsetLeft();
	protected final int getKnurlInsetLeft() {
		if( this.isKnurlDesired() ) {
			return KNURL_WIDTH;
		} else {
			return 0;
		}
	}
	protected abstract int getInternalInsetLeft();
	@Override
	protected final int getInsetLeft() {
		int rv = 0;
		rv += this.getDockInsetLeft();
		rv += this.getKnurlInsetLeft();
		rv += this.getInternalInsetLeft();
		return rv;
	}
	
	protected boolean isKnurlDesired() {
		//todo: check for dragOperation
		return true;
	}
//	protected boolean isCullingContainsDesired() {
//		return isKnurlDesired() == false;
//	}
	
//	@Override
//	public boolean contains( int x, int y ) {
//		if( isCullingContainsDesired() ) {
//			return false;
//		} else {
//			return super.contains( x, y );
//		}
//	}
	protected java.awt.Paint getEnabledBackgroundPaint(int x, int y, int width, int height) {
		return this.enabledBackgroundPaint;
	}
	protected boolean isInScope() {
		return true;
	}
	@Override
	protected final java.awt.Paint getBackgroundPaint(int x, int y, int width, int height) {
		if( this.isInScope() ) {
			return this.getEnabledBackgroundPaint(x, y, width, height);
		} else {
			return java.awt.Color.WHITE;
		}
	}

	private static final java.awt.Stroke ACTIVE_STROKE = new java.awt.BasicStroke( 3.0f, java.awt.BasicStroke.CAP_ROUND, java.awt.BasicStroke.JOIN_ROUND );
	private static final java.awt.Stroke PASSIVE_STROKE = new java.awt.BasicStroke( 1.0f, java.awt.BasicStroke.CAP_ROUND, java.awt.BasicStroke.JOIN_ROUND );
	private static final java.awt.Color HIGHLIGHT_COLOR = new java.awt.Color( 255, 255, 255 );
	private static final java.awt.Color SHADOW_COLOR = new java.awt.Color( 0, 0, 0 );
	protected void paintOutline( java.awt.Graphics2D g2, java.awt.Shape shape ) {
		if( shape != null ) {
			java.awt.Stroke prevStroke = g2.getStroke();
			if( this.isActive() ) {
//				g2.setPaint( SHADOW_COLOR );
//				g2.setStroke( ACTIVE_STROKE );
//				g2.draw( shape );
				edu.cmu.cs.dennisc.java.awt.GraphicsUtilities.draw3DishShape(g2, shape, HIGHLIGHT_COLOR, SHADOW_COLOR, ACTIVE_STROKE );
			} else {
				g2.setPaint( java.awt.Color.GRAY );
				g2.setStroke( PASSIVE_STROKE );
				g2.draw( shape );
			}
			g2.setStroke( prevStroke );
		}
	}
	protected abstract java.awt.Shape createShape( int x, int y, int width, int height );
	@Override
	protected void paintEpilogue( java.awt.Graphics2D g2, int x, int y, int width, int height ) {
		this.paintOutline( g2, this.createShape(x, y, width, height) );
		if( isKnurlDesired() ) {
			int grayscale;
			if( this.isActive() ) {
				grayscale = 0;
			} else {
				grayscale = 127;
			}
			g2.setColor( edu.cmu.cs.dennisc.java.awt.ColorUtilities.createGray( grayscale ) );
			edu.cmu.cs.dennisc.java.awt.KnurlUtilities.paintKnurl5( g2, x+this.getDockInsetLeft(), y + 2, KNURL_WIDTH, height - 5 );
		}
	}
}
