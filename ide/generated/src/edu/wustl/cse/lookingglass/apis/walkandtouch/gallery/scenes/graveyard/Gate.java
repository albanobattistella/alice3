/*
 * Copyright (c) 2008-2010, Washington University in St. Louis. All rights reserved.
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
 * 3. Products derived from the software may not be called "Looking Glass", nor 
 *    may "Looking Glass" appear in their name, without prior written permission
 *    of Washington University in St. Louis.
 *
 * 4. All advertising materials mentioning features or use of this software must
 *    display the following acknowledgement: "This product includes software 
 *    developed by Washington University in St. Louis"
 *
 * 5. The gallery of art assets and animations provided with this software is 
 *    contributed by Electronic Arts Inc. and may be used for personal, 
 *    non-commercial, and academic use only. Redistributions of any program 
 *    source code that utilizes The Sims 2 Assets must also retain the copyright
 *    notice, list of conditions and the disclaimer contained in 
 *    The Alice 3.0 Art Gallery License.
 *
 * DISCLAIMER:
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND.  ANY AND ALL 
 * EXPRESS, STATUTORY OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY,  FITNESS FOR A PARTICULAR PURPOSE, 
 * TITLE, AND NON-INFRINGEMENT ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHORS, 
 * COPYRIGHT OWNERS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, 
 * INCIDENTAL, SPECIAL, EXEMPLARY, PUNITIVE OR CONSEQUENTIAL DAMAGES 
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND 
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING FROM OR OTHERWISE RELATING TO 
 * THE USE OF OR OTHER DEALINGS WITH THE SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 */
package edu.wustl.cse.lookingglass.apis.walkandtouch.gallery.scenes.graveyard;

public class Gate extends edu.wustl.cse.lookingglass.apis.walkandtouch.gallery.GalleryModel { 
	private final edu.cmu.cs.dennisc.alice.virtualmachine.Variable<Boolean> Open= new edu.cmu.cs.dennisc.alice.virtualmachine.Variable<Boolean>( false );  
	public Gate() {
		super( "graveyard/gate" );
}
	public enum Part {
		Fence_Post15_Fence_Post( "Fence_Post15", "Fence_Post" ),
		Fence_Post15_Fence_Post01( "Fence_Post15", "Fence_Post01" ),
		Fence_Post15_Fence_Post02( "Fence_Post15", "Fence_Post02" ),
		Fence_Post15_Fence_Post03( "Fence_Post15", "Fence_Post03" ),
		Fence_Post15_Fence_Post04( "Fence_Post15", "Fence_Post04" ),
		Fence_Post15_Fence_Post05( "Fence_Post15", "Fence_Post05" ),
		Fence_Post15_Fence_Post06( "Fence_Post15", "Fence_Post06" ),
		Fence_Post15_Fence_Post07( "Fence_Post15", "Fence_Post07" ),
		Fence_Post15_Fence_Post08( "Fence_Post15", "Fence_Post08" ),
		Fence_Post15_Fence_Post09( "Fence_Post15", "Fence_Post09" ),
		Fence_Post15_Fence_Post10( "Fence_Post15", "Fence_Post10" ),
		Fence_Post15_Fence_Post11( "Fence_Post15", "Fence_Post11" ),
		Fence_Post15_Fence_Post12( "Fence_Post15", "Fence_Post12" ),
		Fence_Post15_Fence_Post13( "Fence_Post15", "Fence_Post13" ),
		Fence_Post15_Fence_Post14( "Fence_Post15", "Fence_Post14" ),
		Fence_Post15( "Fence_Post15" ),
		Gate_Fence_Post19( "gate", "Fence_Post19" ),
		Gate_Fence_Post18( "gate", "Fence_Post18" ),
		Gate_Fence_Post16( "gate", "Fence_Post16" ),
		Gate_Fence_Post17( "gate", "Fence_Post17" ),
		Gate( "gate" ),
		FenceEnd_Fence_Post20( "fenceEnd", "Fence_Post20" ),
		FenceEnd( "fenceEnd" );
		private String[] m_path;
		Part( String... path ) {
			m_path = path;
		}
		public String[] getPath() {
			return m_path;
		}
	}
	public edu.wustl.cse.lookingglass.apis.walkandtouch.PolygonalModel getPart( Part part ) {
		return getDescendant( edu.wustl.cse.lookingglass.apis.walkandtouch.PolygonalModel.class, part.getPath() );
	}

	public void OpenGate( ) {
		if (Open.value ) {		} else { 
			this.getPart(Gate.Part.Gate).turn( org.alice.apis.moveandturn.TurnDirection.RIGHT, new org.alice.apis.moveandturn.AngleInRevolutions(0.25) );
			Open.value = true;
		}

	}

	public void CloseGate( ) {
		if (Open.value ) {			this.getPart(Gate.Part.Gate).turn( org.alice.apis.moveandturn.TurnDirection.LEFT, new org.alice.apis.moveandturn.AngleInRevolutions(0.25) );
			Open.value = false;
		} else { 
		}

	}
}
