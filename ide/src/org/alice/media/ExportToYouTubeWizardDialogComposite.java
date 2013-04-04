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
package org.alice.media;

import java.io.File;

import org.alice.ide.IDE;

import edu.cmu.cs.dennisc.matt.EventScript;

/**
 * @author Dennis Cosgrove
 */
public class ExportToYouTubeWizardDialogComposite extends org.lgna.croquet.OperationWizardDialogCoreComposite {
	private static class SingletonHolder {
		private static ExportToYouTubeWizardDialogComposite instance = new ExportToYouTubeWizardDialogComposite();
	}

	public static ExportToYouTubeWizardDialogComposite getInstance() {
		return SingletonHolder.instance;
	}

	private EventRecordComposite eventRecordComposite = new EventRecordComposite( this );
	private ImageRecordComposite imageRecordComposite = new ImageRecordComposite( this );
	private UploadComposite uploadComposite = new UploadComposite( this );

	private org.lgna.project.Project project;
	private EventScript script;
	private File file;
	private long randomSeed;

	private ExportToYouTubeWizardDialogComposite() {
		super( java.util.UUID.fromString( "c3542871-3346-4228-a872-1c5641c14e9d" ), org.alice.ide.IDE.EXPORT_GROUP );
		this.addPage( this.eventRecordComposite );
		this.addPage( this.imageRecordComposite );
		this.addPage( this.uploadComposite );
	}

	public org.lgna.project.Project getProject() {
		if( this.project != null ) {
			//pass
		} else if( IDE.getActiveInstance() != null ) {
			this.project = IDE.getActiveInstance().getProject();
		}
		return this.project;
	}

	public void setProject( org.lgna.project.Project project ) {
		this.project = project;
	}

	public EventScript getScript() {
		return this.script;
	}

	public void setScript( EventScript script ) {
		this.script = script;
	}

	public File getFile() {
		return this.file;
	}

	public void setFile( File file ) {
		this.file = file;
		if( this.file != null ) {
			edu.cmu.cs.dennisc.java.io.FileUtilities.createParentDirectoriesIfNecessary( this.file );
		}
	}

	@Override
	protected Integer getWiderGoldenRatioSizeFromWidth() {
		return 900;
	}

	@Override
	protected org.lgna.croquet.DialogCoreComposite.GoldenRatioPolicy getGoldenRatioPolicy() {
		return null;
	}

	@Override
	protected org.lgna.croquet.edits.Edit createEdit( org.lgna.croquet.history.CompletionStep<?> completionStep ) {
		return null;
	}

	public void setRandomSeed( long currentTimeMillis ) {
		this.randomSeed = currentTimeMillis;
	}

	public long getRandomSeed() {
		return this.randomSeed;
	}

	@Override
	protected boolean isClearedForCommit() {
		return super.isClearedForCommit() && uploadComposite.tryToUpload();
	}
}
