/*
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
package edu.cmu.cs.dennisc.croquet.guide;

import edu.cmu.cs.dennisc.tutorial.DialogCloseButtonFeature;

/**
 * @author Dennis Cosgrove
 */
/*package-private*/ class DialogCloseNote extends RequirementNote {
	public static DialogCloseNote createInstance( edu.cmu.cs.dennisc.croquet.DialogOperationContext dialogOperationContext, ParentContextCriterion parentContextCriterion, edu.cmu.cs.dennisc.croquet.SuccessfulCompletionEvent successfulCompletionEvent ) {
		return new DialogCloseNote( 
				dialogOperationContext, 
				new IsChildOfAndInstanceOf( parentContextCriterion, edu.cmu.cs.dennisc.croquet.DialogOperationContext.WindowClosingEvent.class ), 
				new IsAcceptableSuccessfulCompletionOf( parentContextCriterion, successfulCompletionEvent )
		);
	}
	private DialogCloseNote( edu.cmu.cs.dennisc.croquet.DialogOperationContext dialogOperationContext, Requirement< ? >... requirements ) {
		super( requirements );
		edu.cmu.cs.dennisc.croquet.DialogOperation dialogOperation = dialogOperationContext.getModel();
		this.setText( dialogOperation.getTutorialCloseNoteText( dialogOperationContext, ConstructionGuide.getInstance().getUserInformation() ) );
		final ModelFromContextResolver< edu.cmu.cs.dennisc.croquet.DialogOperation > dialogOperationResolver = new ModelFromContextResolver( dialogOperationContext );
		this.addFeature( new DialogCloseButtonFeature( new edu.cmu.cs.dennisc.croquet.RuntimeResolver< edu.cmu.cs.dennisc.croquet.TrackableShape >() {
			public edu.cmu.cs.dennisc.croquet.TrackableShape getResolved() {
				edu.cmu.cs.dennisc.croquet.DialogOperation dialogOperation = dialogOperationResolver.getResolved();
				if( dialogOperation != null ) {
					edu.cmu.cs.dennisc.croquet.Dialog activeDialog = dialogOperation.getActiveDialog();
					if( activeDialog != null ) {
						return activeDialog.getCloseButtonTrackableShape();
					} else {
						return null;
					}
				} else {
					return null;
				}
			}
		} ) );
	}
}
