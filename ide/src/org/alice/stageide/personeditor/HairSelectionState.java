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
package org.alice.stageide.personeditor;

/**
 * @author Dennis Cosgrove
 */
class HairSelectionState extends AbstractListSelectionState<org.alice.apis.stage.Hair> {
	public HairSelectionState() {
		super( java.util.UUID.fromString( "682e4dea-91f3-4b0a-8004-51942613c643" ), new edu.cmu.cs.dennisc.croquet.Codec< org.alice.apis.stage.Hair >(){
			public org.alice.apis.stage.Hair decode( edu.cmu.cs.dennisc.codec.BinaryDecoder binaryDecoder ) {
				throw new RuntimeException( "todo" );
			}
			public void encode( edu.cmu.cs.dennisc.codec.BinaryEncoder binaryEncoder, org.alice.apis.stage.Hair t ) {
				throw new RuntimeException( "todo" );
			}
		} );
	}
	/*package-private*/ void handleCataclysmicChange( org.alice.apis.stage.LifeStage lifeStage, org.alice.apis.stage.Gender gender, final String hairColor ) {
		this.setListData( -1, 
				edu.cmu.cs.dennisc.java.util.CollectionUtilities.createArray( 
						edu.cmu.cs.dennisc.java.lang.EnumUtilities.getEnumConstants( 
								org.alice.apis.stage.HairManager.getSingleton().getImplementingClasses( lifeStage, gender ), 
								new edu.cmu.cs.dennisc.pattern.Criterion< org.alice.apis.stage.Hair >() {
									public boolean accept( org.alice.apis.stage.Hair hair ) {
										return hair.toString().equals( hairColor );
									}
								} 
						),
						org.alice.apis.stage.Hair.class
				) 
		);
	}
	@Override
	protected int getVisibleRowCount() {
		return -1;
	}
	@Override
	public edu.cmu.cs.dennisc.croquet.List<org.alice.apis.stage.Hair> createList() {
		edu.cmu.cs.dennisc.croquet.List<org.alice.apis.stage.Hair> rv = super.createList();
		rv.setCellRenderer( new HairListCellRenderer() );
		return rv;
	}
}
