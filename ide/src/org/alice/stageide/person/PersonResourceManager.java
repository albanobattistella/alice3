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

package org.alice.stageide.person;

/**
 * @author Dennis Cosgrove
 */
public enum PersonResourceManager {
	SINGLETON;
	private final PersonImp personImp = new PersonImp();
	private final edu.cmu.cs.dennisc.map.MapToMap< org.lgna.story.resources.sims2.LifeStage, org.lgna.story.resources.sims2.Gender, org.lgna.story.resources.sims2.PersonResource > mapToMap = edu.cmu.cs.dennisc.map.MapToMap.newInstance();
	private final org.lgna.croquet.State.ValueObserver<org.lgna.story.resources.sims2.LifeStage> lifeStageObserver = new org.lgna.croquet.State.ValueObserver<org.lgna.story.resources.sims2.LifeStage>() {
		public void changing( org.lgna.croquet.State< org.lgna.story.resources.sims2.LifeStage > state, org.lgna.story.resources.sims2.LifeStage prevValue, org.lgna.story.resources.sims2.LifeStage nextValue, boolean isAdjusting ) {
			pushAtomic();
		}
		public void changed( org.lgna.croquet.State< org.lgna.story.resources.sims2.LifeStage > state, org.lgna.story.resources.sims2.LifeStage prevValue, org.lgna.story.resources.sims2.LifeStage nextValue, boolean isAdjusting ) {
//			handleCataclysm( true, false, false );
			popAtomic();
		}
	};
	private final org.lgna.croquet.State.ValueObserver<org.lgna.story.resources.sims2.Gender> genderObserver = new org.lgna.croquet.State.ValueObserver<org.lgna.story.resources.sims2.Gender>() {
		public void changing( org.lgna.croquet.State< org.lgna.story.resources.sims2.Gender > state, org.lgna.story.resources.sims2.Gender prevValue, org.lgna.story.resources.sims2.Gender nextValue, boolean isAdjusting ) {
			pushAtomic();
		}
		public void changed( org.lgna.croquet.State< org.lgna.story.resources.sims2.Gender > state, org.lgna.story.resources.sims2.Gender prevValue, org.lgna.story.resources.sims2.Gender nextValue, boolean isAdjusting ) {
//			handleCataclysm( false, true, false );
			popAtomic();
		}
	};
	private final org.lgna.croquet.State.ValueObserver<org.lgna.story.resources.sims2.BaseSkinTone> baseSkinToneObserver = new org.lgna.croquet.State.ValueObserver<org.lgna.story.resources.sims2.BaseSkinTone>() {
		public void changing( org.lgna.croquet.State< org.lgna.story.resources.sims2.BaseSkinTone > state, org.lgna.story.resources.sims2.BaseSkinTone prevValue, org.lgna.story.resources.sims2.BaseSkinTone nextValue, boolean isAdjusting ) {
			pushAtomic();
		}
		public void changed( org.lgna.croquet.State< org.lgna.story.resources.sims2.BaseSkinTone > state, org.lgna.story.resources.sims2.BaseSkinTone prevValue, org.lgna.story.resources.sims2.BaseSkinTone nextValue, boolean isAdjusting ) {
			popAtomic();
		}
	};
	private final org.lgna.croquet.State.ValueObserver<org.lgna.story.resources.sims2.BaseEyeColor> baseEyeColorObserver = new org.lgna.croquet.State.ValueObserver<org.lgna.story.resources.sims2.BaseEyeColor>() {
		public void changing( org.lgna.croquet.State< org.lgna.story.resources.sims2.BaseEyeColor > state, org.lgna.story.resources.sims2.BaseEyeColor prevValue, org.lgna.story.resources.sims2.BaseEyeColor nextValue, boolean isAdjusting ) {
			pushAtomic();
		}
		public void changed( org.lgna.croquet.State< org.lgna.story.resources.sims2.BaseEyeColor > state, org.lgna.story.resources.sims2.BaseEyeColor prevValue, org.lgna.story.resources.sims2.BaseEyeColor nextValue, boolean isAdjusting ) {
			popAtomic();
		}
	};
	private final org.lgna.croquet.State.ValueObserver<String> hairColorNameObserver = new org.lgna.croquet.State.ValueObserver<String>() {
		public void changing( org.lgna.croquet.State< String > state, String prevValue, String nextValue, boolean isAdjusting ) {
			pushAtomic();
		}
		public void changed( org.lgna.croquet.State< String > state, String prevValue, String nextValue, boolean isAdjusting ) {
//			handleCataclysm( false, false, true );
			popAtomic();
		}
	};
	private final org.lgna.croquet.State.ValueObserver<org.lgna.story.resources.sims2.Hair> hairObserver = new org.lgna.croquet.State.ValueObserver<org.lgna.story.resources.sims2.Hair>() {
		public void changing( org.lgna.croquet.State< org.lgna.story.resources.sims2.Hair > state, org.lgna.story.resources.sims2.Hair prevValue, org.lgna.story.resources.sims2.Hair nextValue, boolean isAdjusting ) {
			pushAtomic();
		}
		public void changed( org.lgna.croquet.State< org.lgna.story.resources.sims2.Hair > state, org.lgna.story.resources.sims2.Hair prevValue, org.lgna.story.resources.sims2.Hair nextValue, boolean isAdjusting ) {
			popAtomic();
		}
	};
	private final org.lgna.croquet.State.ValueObserver<org.lgna.story.resources.sims2.FullBodyOutfit> fullBodyOutfitObserver = new org.lgna.croquet.State.ValueObserver<org.lgna.story.resources.sims2.FullBodyOutfit>() {
		public void changing( org.lgna.croquet.State< org.lgna.story.resources.sims2.FullBodyOutfit > state, org.lgna.story.resources.sims2.FullBodyOutfit prevValue, org.lgna.story.resources.sims2.FullBodyOutfit nextValue, boolean isAdjusting ) {
			pushAtomic();
		}
		public void changed( org.lgna.croquet.State< org.lgna.story.resources.sims2.FullBodyOutfit > state, org.lgna.story.resources.sims2.FullBodyOutfit prevValue, org.lgna.story.resources.sims2.FullBodyOutfit nextValue, boolean isAdjusting ) {
			popAtomic();
		}
	};
	private final org.lgna.croquet.State.ValueObserver<Integer> obesityPercentStateObserver = new org.lgna.croquet.State.ValueObserver<Integer>() {
		public void changing( org.lgna.croquet.State< Integer > state, Integer prevValue, Integer nextValue, boolean isAdjusting ) {
			pushAtomic();
		}
		public void changed( org.lgna.croquet.State< Integer > state, Integer prevValue, Integer nextValue, boolean isAdjusting ) {
			popAtomic();
		}
	};
	private void addListeners() {
		org.alice.stageide.person.models.LifeStageState.getInstance().addValueObserver( this.lifeStageObserver );
		org.alice.stageide.person.models.GenderState.getInstance().addValueObserver( this.genderObserver );
		org.alice.stageide.person.models.BaseSkinToneState.getInstance().addValueObserver( this.baseSkinToneObserver );
		org.alice.stageide.person.models.BaseEyeColorState.getInstance().addValueObserver( this.baseEyeColorObserver );
		org.alice.stageide.person.models.HairColorNameState.getInstance().addValueObserver( this.hairColorNameObserver );
		org.alice.stageide.person.models.HairState.getInstance().addValueObserver( this.hairObserver );
		org.alice.stageide.person.models.FullBodyOutfitState.getInstance().addValueObserver( this.fullBodyOutfitObserver );
		org.alice.stageide.person.models.ObesityPercentState.getInstance().addValueObserver( this.obesityPercentStateObserver );
	}
	private void removeListeners() {
		org.alice.stageide.person.models.LifeStageState.getInstance().removeValueObserver( this.lifeStageObserver );
		org.alice.stageide.person.models.GenderState.getInstance().removeValueObserver( this.genderObserver );
		org.alice.stageide.person.models.BaseSkinToneState.getInstance().removeValueObserver( this.baseSkinToneObserver );
		org.alice.stageide.person.models.BaseEyeColorState.getInstance().removeValueObserver( this.baseEyeColorObserver );
		org.alice.stageide.person.models.HairColorNameState.getInstance().removeValueObserver( this.hairColorNameObserver );
		org.alice.stageide.person.models.HairState.getInstance().removeValueObserver( this.hairObserver );
		org.alice.stageide.person.models.FullBodyOutfitState.getInstance().removeValueObserver( this.fullBodyOutfitObserver );
		org.alice.stageide.person.models.ObesityPercentState.getInstance().removeValueObserver( this.obesityPercentStateObserver );
	}
	private int activeCount = 0;
	private void addListenersIfAppropriate() {
		if( activeCount > 0 ) {
			this.addListeners();
		}
	}
	private void removeListenersIfAppropriate() {
		if( activeCount > 0 ) {
			this.removeListeners();
		}
	}

	public org.alice.stageide.person.components.PersonViewer allocatePersonViewer( org.lgna.story.resources.sims2.PersonResource personResource ) {
		assert activeCount == 0; //todo
		this.setStates( personResource );
		if( activeCount == 0 ) {
			this.addListeners();
		}
		this.activeCount++;
		this.personImp.updateNebPerson();
		return new org.alice.stageide.person.components.PersonViewer( this.personImp ) ;
	}
	public void releasePersonViewer( org.alice.stageide.person.components.PersonViewer personViewer ) {
		this.activeCount--;
		if( activeCount == 0 ) {
			this.removeListeners();
		}
		personViewer.setPerson( null );
		assert activeCount == 0; //todo
	}
	

	private org.lgna.story.resources.sims2.LifeStage getLifeStage() {
		return org.alice.stageide.person.models.LifeStageState.getInstance().getValue();
	}
	private org.lgna.story.resources.sims2.Gender getGender() {
		return org.alice.stageide.person.models.GenderState.getInstance().getValue();
	}
	private org.lgna.story.resources.sims2.EyeColor getEyeColor() {
		return org.alice.stageide.person.models.BaseEyeColorState.getInstance().getValue();
	}
	private double getFitnessLevel() {
		return 1.0 - org.alice.stageide.person.models.ObesityPercentState.getInstance().getValue() * 0.01;
	}
	private org.lgna.story.resources.sims2.Hair getHair() {
		return org.alice.stageide.person.models.HairState.getInstance().getValue();
	}
	private org.lgna.story.resources.sims2.Outfit getOutfit() {
		return org.alice.stageide.person.models.FullBodyOutfitState.getInstance().getValue();
	}

	private static String getHairColor( org.lgna.story.resources.sims2.PersonResource personResource ) {
		org.lgna.story.resources.sims2.Hair hair = personResource.getHair();
		return hair!=null ? hair.toString() : null;
	}
	private static final String[] getHairColors( org.lgna.story.resources.sims2.LifeStage lifeStage ) {
		return lifeStage != null ? lifeStage.getHairColors() : null;
	}
	
	private org.lgna.story.resources.sims2.PersonResource prevPersonResource;
	private int atomicCount = 0;
	public void pushAtomic() {
		if( this.atomicCount == 0 ) {
			this.prevPersonResource = this.createResourceFromStates();
			this.mapToMap.put( this.prevPersonResource.getLifeStage(), this.prevPersonResource.getGender(), this.prevPersonResource );
		}
		this.atomicCount++;
	}
	public void popAtomic() {
		this.atomicCount--;
		if( this.atomicCount == 0 ) {
			this.removeListenersIfAppropriate();
			try {
				org.lgna.story.resources.sims2.LifeStage lifeStage = this.getLifeStage();
				boolean isLifeStageChanged = this.prevPersonResource.getLifeStage() != lifeStage;
				if( isLifeStageChanged ) {
					String[] hairColors = getHairColors( lifeStage );
					if( hairColors != getHairColors( this.prevPersonResource.getLifeStage() ) ) {
						String hairColor = org.alice.stageide.person.models.HairColorNameState.getInstance().getSelectedItem();
						int index = java.util.Arrays.asList( hairColors ).indexOf( hairColor );
						if( index != -1 ) {
							//pass
						} else {
							index = org.alice.random.RandomUtilities.nextIntegerFrom0ToNExclusive( hairColors.length );
						}
						org.alice.stageide.person.models.HairColorNameState.getInstance().setListData( index, hairColors );
					}
				}
				org.lgna.story.resources.sims2.Gender gender = this.getGender();
				boolean isGenderChanged = this.prevPersonResource.getGender() != gender;

				final String hairColor = org.alice.stageide.person.models.HairColorNameState.getInstance().getSelectedItem();
				boolean isHairColorChanged = edu.cmu.cs.dennisc.equivalence.EquivalenceUtilities.areNotEquivalent( getHairColor( this.prevPersonResource ), hairColor );
				if( isLifeStageChanged || isGenderChanged || isHairColorChanged ) {
					java.util.List< org.lgna.story.resources.sims2.Hair > list = edu.cmu.cs.dennisc.java.lang.EnumUtilities.getEnumConstants( 
							org.lgna.story.resources.sims2.HairManager.getSingleton().getImplementingClasses( lifeStage, gender ), 
							new edu.cmu.cs.dennisc.pattern.Criterion< org.lgna.story.resources.sims2.Hair >() {
								public boolean accept( org.lgna.story.resources.sims2.Hair hair ) {
									return hair.toString().equals( hairColor );
								}
							} 
					);
					int index;
					org.lgna.story.resources.sims2.Hair hair = this.getHair();
					if( hair != null ) {
						index = list.indexOf( hair );
					} else {
						index = -1;
					}
					org.alice.stageide.person.models.HairState.getInstance().setListData( 
							index, 
							edu.cmu.cs.dennisc.java.util.CollectionUtilities.createArray(
									list,
									org.lgna.story.resources.sims2.Hair.class
							) 
					);
				}
				if( isLifeStageChanged || isGenderChanged ) {
					java.util.List< org.lgna.story.resources.sims2.FullBodyOutfit > list = edu.cmu.cs.dennisc.java.lang.EnumUtilities.getEnumConstants( 
							org.lgna.story.resources.sims2.FullBodyOutfitManager.getSingleton().getImplementingClasses( lifeStage, gender ), 
							null 
					);
					org.alice.stageide.person.models.FullBodyOutfitState.getInstance().setListData( -1, edu.cmu.cs.dennisc.java.util.CollectionUtilities.createArray( 
							list,
							org.lgna.story.resources.sims2.FullBodyOutfit.class
					) );
				}
			} finally {
				this.addListenersIfAppropriate();
			}
			this.personImp.updateNebPerson();
		}
	}
	
	
	public org.lgna.story.resources.sims2.PersonResource createResourceFromStates() {
		org.lgna.story.resources.sims2.LifeStage lifeStage = org.alice.stageide.person.models.LifeStageState.getInstance().getValue();
		org.lgna.story.resources.sims2.Gender gender = org.alice.stageide.person.models.GenderState.getInstance().getValue();
		org.lgna.story.resources.sims2.SkinTone skinTone = org.alice.stageide.person.models.BaseSkinToneState.getInstance().getValue();
		org.lgna.story.resources.sims2.EyeColor eyeColor = org.alice.stageide.person.models.BaseEyeColorState.getInstance().getValue();
		org.lgna.story.resources.sims2.Outfit outfit = org.alice.stageide.person.models.FullBodyOutfitState.getInstance().getValue();
		org.lgna.story.resources.sims2.Hair hair = org.alice.stageide.person.models.HairState.getInstance().getValue();
		double obesityLevel = org.alice.stageide.person.models.ObesityPercentState.getInstance().getValue() * 0.01;
		return lifeStage.createResource( gender, skinTone, eyeColor, hair, obesityLevel, outfit );
	}

	public void setStates( org.lgna.story.resources.sims2.PersonResource personResource ) {
		this.removeListenersIfAppropriate();
		try {
			org.alice.stageide.person.models.LifeStageState.getInstance().setSelectedItem( personResource.getLifeStage() );
			org.alice.stageide.person.models.GenderState.getInstance().setSelectedItem( personResource.getGender() );
			org.alice.stageide.person.models.BaseEyeColorState.getInstance().setSelectedItem( (org.lgna.story.resources.sims2.BaseEyeColor)personResource.getEyeColor() );
			org.alice.stageide.person.models.BaseSkinToneState.getInstance().setSelectedItem( (org.lgna.story.resources.sims2.BaseSkinTone)personResource.getSkinTone() );
			org.alice.stageide.person.models.FullBodyOutfitState.getInstance().setSelectedItem( (org.lgna.story.resources.sims2.FullBodyOutfit)personResource.getOutfit() );
			
			org.lgna.story.resources.sims2.Hair hair = personResource.getHair();
			org.alice.stageide.person.models.HairState.getInstance().setSelectedItem( hair );
			org.alice.stageide.person.models.HairColorNameState.getInstance().setSelectedItem( hair != null ? hair.toString() : null );
			org.alice.stageide.person.models.ObesityPercentState.getInstance().setValue( (int)(personResource.getObesityLevel()*100) );
		} finally {
			this.addListenersIfAppropriate();
		}
	}
	
//	private void handleCataclysm( boolean isLifeStageChange, boolean isGenderChange, boolean isHairColorChange ) {
//		this.removeListenersIfAppropriate();
//		try {
//			org.lgna.story.resources.sims2.LifeStage lifeStage = org.alice.stageide.person.models.LifeStageState.getInstance().getSelectedItem();
//			org.lgna.story.resources.sims2.Gender gender = org.alice.stageide.person.models.GenderState.getInstance().getSelectedItem();
//			String hairColor = org.alice.stageide.person.models.HairColorNameState.getInstance().getSelectedItem();
//			if( isLifeStageChange || isGenderChange || isHairColorChange ) {
//				org.alice.stageide.person.models.HairState.getInstance().handleCataclysmicChange( lifeStage, gender, hairColor );
//			}
//			if( isLifeStageChange || isGenderChange ) {
//				org.alice.stageide.person.models.FullBodyOutfitState.getInstance().handleCataclysmicChange( lifeStage, gender );
//			}
//			if( isLifeStageChange ) {
//				org.alice.stageide.person.models.HairColorNameState.getInstance().handleCataclysmicChange( lifeStage );
//			}
//			//org.lgna.story.resources.sims2.Hair hair = //todo;
//			if( isLifeStageChange || isGenderChange || isHairColorChange ) {
//				org.alice.stageide.person.models.HairState.getInstance().setSelectedItem( hair );
//			}
//			if( isLifeStageChange || isGenderChange ) {
//				org.alice.stageide.person.models.FullBodyOutfitState.getInstance().setSelectedItem( (org.lgna.story.resources.sims2.FullBodyOutfit)visualData.getOutfit() );
//			}
//			if( isLifeStageChange ) {
//				org.alice.stageide.person.models.HairColorNameState.getInstance().setSelectedItem( hair != null ? hair.toString() : null );
//			}
//		} finally {
//			this.addListenersIfAppropriate();
//		}
//	}

//	private boolean isAlreadyHandlingCataclysm = false;
//	private void handleCataclysm( boolean isLifeStageChange, boolean isGenderChange, boolean isHairColorChange ) {
//		if( org.alice.stageide.person.components.PersonViewer.getSingleton().getPerson() != null ) {
//			if( this.isAlreadyHandlingCataclysm ) {
//				//pass
//			} else {
//				this.isAlreadyHandlingCataclysm = true;
//				try {
//					org.lgna.story.resources.sims2.LifeStage lifeStage = org.alice.stageide.person.models.LifeStageState.getInstance().getSelectedItem();
//					org.lgna.story.resources.sims2.Gender gender = org.alice.stageide.person.models.GenderState.getInstance().getSelectedItem();
//					String hairColor = org.alice.stageide.person.models.HairColorNameState.getInstance().getSelectedItem();
//					if( isLifeStageChange || isGenderChange || isHairColorChange ) {
//						org.alice.stageide.person.models.HairState.getInstance().handleCataclysmicChange( lifeStage, gender, hairColor );
//					}
//					if( isLifeStageChange || isGenderChange ) {
//						org.alice.stageide.person.models.FullBodyOutfitState.getInstance().handleCataclysmicChange( lifeStage, gender );
//					}
//					if( isLifeStageChange ) {
//						org.alice.stageide.person.models.HairColorNameState.getInstance().handleCataclysmicChange( lifeStage );
//					}
//					this.updatePerson();
//					
////					org.lgna.story.resources.sims2.Hair hair = //todo;
////					if( isLifeStageChange || isGenderChange || isHairColorChange ) {
////						org.alice.stageide.person.models.HairState.getInstance().setSelectedItem( hair );
////					}
////					if( isLifeStageChange || isGenderChange ) {
////						org.alice.stageide.person.models.FullBodyOutfitState.getInstance().setSelectedItem( (org.lgna.story.resources.sims2.FullBodyOutfit)visualData.getOutfit() );
////					}
////					if( isLifeStageChange ) {
////						org.alice.stageide.person.models.HairColorNameState.getInstance().setSelectedItem( hair != null ? hair.toString() : null );
////					}
//				} finally {
//					this.isAlreadyHandlingCataclysm = false;
//				}
//			}
//		}
//	}
//	
//	private boolean isAlreadyUpdating = false;
//	private void updatePerson() {
//		if( this.isAlreadyUpdating ) {
//			//pass
//		} else {
//			this.isAlreadyUpdating = true;
//			//edu.cmu.cs.dennisc.lookingglass.opengl.LookingGlassFactory.getSingleton().acquireRenderingLock();
//			try {
//				org.lgna.story.resources.sims2.LifeStage lifeStage = org.alice.stageide.person.models.LifeStageState.getInstance().getSelectedItem();
//				org.lgna.story.resources.sims2.Gender gender = org.alice.stageide.person.models.GenderState.getInstance().getSelectedItem();
//				String hairColor = org.alice.stageide.person.models.HairColorNameState.getInstance().getSelectedItem();
//
//				org.lgna.story.resources.sims2.FullBodyOutfit fullBodyOutfit = org.alice.stageide.person.models.FullBodyOutfitState.getInstance().getSelectedItem();
//				org.lgna.story.resources.sims2.BaseEyeColor baseEyeColor = org.alice.stageide.person.models.BaseEyeColorState.getInstance().getSelectedItem();
//				org.lgna.story.resources.sims2.BaseSkinTone baseSkinTone = org.alice.stageide.person.models.BaseSkinToneState.getInstance().getSelectedItem();
//				org.lgna.story.resources.sims2.Hair hair = org.alice.stageide.person.models.HairState.getInstance().getSelectedItem();
//				double fitnessLevel = org.alice.stageide.person.models.ObesityPercentState.getInstance().getValue()*0.01;
//
//				PersonImp visualData = org.alice.stageide.person.components.PersonViewer.getSingleton().getPerson();
//				visualData.pushAtomic();
//				visualData.popAtomic();
////				if( visualData != null ) {
////					if( gender != null ) {
////						visualData.setGender( gender );
////					}
////					if( baseSkinTone != null ) {
////						visualData.setSkinTone( baseSkinTone );
////						visualData.setObesityLevel( fitnessLevel );
////						if( fullBodyOutfit != null && org.lgna.story.resources.sims2.FullBodyOutfitManager.getSingleton().isApplicable( fullBodyOutfit, lifeStage, gender ) ) {
////							//pass
////						} else {
//////							org.lookingglassandalice.storytelling.Outfit outfit = person.getOutfit();
//////							if( outfit instanceof org.lookingglassandalice.storytelling.FullBodyOutfit ) {
//////								fullBodyOutfit = ( org.lookingglassandalice.storytelling.FullBodyOutfit )outfit;
//////							} else {
////								fullBodyOutfit = org.lgna.story.resources.sims2.FullBodyOutfitManager.getSingleton().getRandomEnumConstant( lifeStage, gender );
//////							}
////						}
////						visualData.setOutfit( fullBodyOutfit );
////					}
////					if( baseEyeColor != null ) {
////						visualData.setEyeColor( baseEyeColor );
////					}
////					if( gender != null ) {
////						if( hair != null && org.lgna.story.resources.sims2.HairManager.getSingleton().isApplicable( hair, lifeStage, gender ) ) {
////							//pass
////						} else {
////							try {
////								Class<? extends org.lgna.story.resources.sims2.Hair> cls = org.lgna.story.resources.sims2.HairManager.getSingleton().getRandomClass(lifeStage, gender);
////								java.lang.reflect.Field field = cls.getField( hairColor );
////								hair = (org.lgna.story.resources.sims2.Hair)field.get( null );
////							} catch( Exception e ) {
////								hair = org.lgna.story.resources.sims2.HairManager.getSingleton().getRandomEnumConstant(lifeStage, gender);
////							}
////						}
////						visualData.setHair( hair );
////					}
////					org.alice.stageide.person.components.PersonViewer.getSingleton().setPersonVisualData( visualData );
////				}
//			} finally {
//				//edu.cmu.cs.dennisc.lookingglass.opengl.LookingGlassFactory.getSingleton().releaseRenderingLock();
//				this.isAlreadyUpdating = false;
//			}
//		}
//	}
//	
}
