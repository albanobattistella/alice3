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

package org.lgna.croquet;

/**
 * @author Dennis Cosgrove
 */
public abstract class OperationWizardDialogCoreComposite extends WizardDialogCoreComposite implements OperationOwningComposite<org.lgna.croquet.components.Panel> {
	private final OwnedByCompositeOperation operation;

	public OperationWizardDialogCoreComposite( java.util.UUID migrationId, Group operationGroup, WizardPageComposite<?>... wizardPages ) {
		super( migrationId, wizardPages );
		this.operation = new OwnedByCompositeOperation( operationGroup, this );
	}

	public OwnedByCompositeOperation getOperation() {
		return this.operation;
	}

	@Override
	protected String getName() {
		return this.getOperation().getName();
	}

	public boolean isToolBarTextClobbered( boolean defaultValue ) {
		return defaultValue;
	}

	public void clobberLocalizationIfDesired( OwnedByCompositeOperation operation ) {
	}

	protected boolean isAutoCommitEnabled() {
		return false;
	}

	protected abstract org.lgna.croquet.edits.Edit createEdit( org.lgna.croquet.history.CompletionStep<?> completionStep );

	private void createAndCommitEdit( org.lgna.croquet.history.CompletionStep<?> completionStep ) {
		try {
			org.lgna.croquet.edits.Edit edit = this.createEdit( completionStep );
			if( edit != null ) {
				completionStep.commitAndInvokeDo( edit );
			} else {
				completionStep.finish();
			}
		} catch( CancelException ce ) {
			completionStep.cancel();
		}
	}

	public void perform( org.lgna.croquet.history.CompletionStep<?> completionStep ) {
		boolean isAutoCommitDesired;
		if( this.isAutoCommitEnabled() ) {
			java.util.Iterator<WizardPageComposite<?>> iterator = this.getWizardPageIterator();
			isAutoCommitDesired = true;
			while( iterator.hasNext() ) {
				WizardPageComposite<?> page = iterator.next();
				if( page.isAutoAdvanceDesired( completionStep ) ) {
					//pass
				} else {
					isAutoCommitDesired = false;
				}
			}
		} else {
			isAutoCommitDesired = false;
		}
		if( isAutoCommitDesired ) {
			this.createAndCommitEdit( completionStep );
		} else {
			org.lgna.croquet.dialog.DialogUtilities.showDialog( new DialogOwner( this ) {
				@Override
				public void handlePostHideDialog( org.lgna.croquet.history.CompletionStep<?> completionStep ) {
					super.handlePostHideDialog( completionStep );
					Boolean isCommited = completionStep.getEphemeralDataFor( IS_COMMITED_KEY );
					if( isCommited != null ) { // close button condition
						if( isCommited ) {
							createAndCommitEdit( completionStep );
						} else {
							completionStep.cancel();
						}
					} else {
						completionStep.cancel();
					}
				}
			}, completionStep );
		}
	}
}
