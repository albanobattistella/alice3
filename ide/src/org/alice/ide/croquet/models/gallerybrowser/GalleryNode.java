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

package org.alice.ide.croquet.models.gallerybrowser;

/**
 * @author Dennis Cosgrove
 */
public abstract class GalleryNode extends org.alice.ide.croquet.models.IdeDragModel implements Iterable< GalleryNode > {
	public GalleryNode( java.util.UUID id ) {
		super( id );
	}
	public abstract GalleryNode getParent();
	public abstract GalleryNode getChild( int index );
	public abstract int getChildCount();
	public abstract int getIndexOfChild( GalleryNode child );
	public abstract String getText();
	public abstract javax.swing.Icon getSmallIcon();
	public abstract javax.swing.Icon getLargeIcon();
	public java.util.Iterator< GalleryNode > iterator() {
		return new java.util.Iterator< GalleryNode >() {
			private int index = 0;
			public boolean hasNext() {
				return this.index < GalleryNode.this.getChildCount();
			}
			public GalleryNode next() {
				GalleryNode rv = GalleryNode.this.getChild( this.index );
				this.index++;
				return rv;
			}
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	@Override
	public java.util.List< ? extends org.lgna.croquet.DropReceptor > createListOfPotentialDropReceptors( org.lgna.croquet.components.DragComponent dragSource ) {
		org.alice.ide.sceneeditor.AbstractSceneEditor sceneEditor = org.alice.ide.IDE.getActiveInstance().getSceneEditor();
		if( sceneEditor instanceof org.lgna.croquet.DropReceptor ) {
			return edu.cmu.cs.dennisc.java.util.Collections.newArrayList( (org.lgna.croquet.DropReceptor)sceneEditor );
		} else {
			return null;
		}
	}
	public abstract org.lgna.croquet.Model getLeftButtonClickModel();
}
