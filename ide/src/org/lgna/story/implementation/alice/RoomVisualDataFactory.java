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
package org.lgna.story.implementation.alice;

/**
 * @author alice
 *
 */
public class RoomVisualDataFactory implements org.lgna.story.implementation.RoomImp.VisualDataFactory<org.lgna.story.resources.RoomResource> {
	private static java.util.Map< org.lgna.story.resources.RoomResource, RoomVisualDataFactory > map = edu.cmu.cs.dennisc.java.util.Collections.newHashMap();

	private static class VisualData implements org.lgna.story.implementation.JointedModelImp.VisualData {
		private final edu.cmu.cs.dennisc.scenegraph.TexturedAppearance[] texturedAppearances;
		private final edu.cmu.cs.dennisc.scenegraph.SkeletonVisual sgSkeletonVisual;

		public VisualData( org.lgna.story.resources.JointedModelResource resource ) {
			assert resource != null;
			this.texturedAppearances = AliceResourceUtilties.getTexturedAppearances( resource );
			//Get the copy of the original geometry (this makes a new skeleton, appearance and whatnot, and keeps references to static data like the meshes)
			this.sgSkeletonVisual = AliceResourceUtilties.getVisualCopy( resource );
			//Set the texture data to be the texture info specified by the resource
			this.sgSkeletonVisual.textures.setValue(this.texturedAppearances);
		}
		public edu.cmu.cs.dennisc.scenegraph.SimpleAppearance[] getSgAppearances() {
			return new edu.cmu.cs.dennisc.scenegraph.SimpleAppearance[] { (edu.cmu.cs.dennisc.scenegraph.SimpleAppearance)this.sgSkeletonVisual.frontFacingAppearance.getValue() };
		}
		public edu.cmu.cs.dennisc.scenegraph.Visual[] getSgVisuals() {
			return new edu.cmu.cs.dennisc.scenegraph.Visual[] { this.sgSkeletonVisual };
		}
		public double getBoundingSphereRadius() {
			return 1.0;
		}
		
		public void setSGParent(edu.cmu.cs.dennisc.scenegraph.Composite parent) {
			sgSkeletonVisual.setParent(parent);
		}
	}

	public static RoomVisualDataFactory getInstance( org.lgna.story.resources.RoomResource resource ) {
		synchronized( map ) {
			RoomVisualDataFactory rv = map.get( resource );
			if( rv != null ) {
				//pass
			} else {
				rv = new RoomVisualDataFactory( resource );
				map.put( resource, rv );
			}
			return rv;
		}
	}

	private final org.lgna.story.resources.RoomResource resource;

	private RoomVisualDataFactory( org.lgna.story.resources.RoomResource resource ) {
		this.resource = resource;
	}
	public org.lgna.story.resources.RoomResource getResource() {
		return this.resource;
	}

	public org.lgna.story.implementation.JointedModelImp.VisualData createVisualData( org.lgna.story.implementation.RoomImp roomImp ) {
		return new VisualData( this.resource );
	}
}