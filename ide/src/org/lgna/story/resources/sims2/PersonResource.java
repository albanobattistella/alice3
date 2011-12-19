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

package org.lgna.story.resources.sims2;

/**
 * @author Dennis Cosgrove
 */
public abstract class PersonResource implements org.lgna.story.resources.BipedResource {
	private final Gender gender;
	private final SkinTone skinTone;
	private final EyeColor eyeColor;
	private final Hair hair;
	private final double obesityLevel;
	private final Outfit outfit;

	public PersonResource( Gender gender, SkinTone skinTone, EyeColor eyeColor, Hair hair, Number obesityLevel, Outfit outfit ) {
		this.gender = gender;
		this.skinTone = skinTone;
		this.eyeColor = eyeColor;
		this.hair = hair;
		this.obesityLevel = obesityLevel.doubleValue();
		this.outfit = outfit;
	}
	public abstract LifeStage getLifeStage();
	public Gender getGender() {
		return this.gender;
	}
	public SkinTone getSkinTone() {
		return this.skinTone;
	}
	public EyeColor getEyeColor() {
		return this.eyeColor;
	}
	public Hair getHair() {
		return this.hair;
	}
	public Double getObesityLevel() {
		return this.obesityLevel;
	}
	public Outfit getOutfit() {
		return this.outfit;
	}
	public org.lgna.story.resources.JointId[] getRootJointIds() {
		return org.lgna.story.resources.BipedResource.JOINT_ID_ROOTS;
	}

	public final org.lgna.story.implementation.BipedImp createImplementation( org.lgna.story.Biped abstraction ) {
		org.lgna.story.implementation.BipedImp rv = new org.lgna.story.implementation.BipedImp( abstraction, org.lgna.story.implementation.sims2.JointImplementationAndVisualDataFactory.getInstance( this ) );
//		org.lgna.story.implementation.sims2.NebulousPersonVisualData visualData = (org.lgna.story.implementation.sims2.NebulousPersonVisualData)rv.getVisualData();
//		visualData.setGender( this.getGender() );
//		visualData.setOutfit( this.getOutfit() );
//		visualData.setSkinTone( this.getSkinTone() );
//		visualData.setObesityLevel( this.getObesityLevel() );
//		visualData.setHair( org.lgna.story.resources.sims2.FemaleAdultHairBraids.BLACK );
//		visualData.setEyeColor( org.lgna.story.resources.sims2.BaseEyeColor.getRandom() );
		return rv;
	}
}
