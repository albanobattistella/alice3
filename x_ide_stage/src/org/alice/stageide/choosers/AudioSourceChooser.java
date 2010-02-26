/*
 * Copyright (c) 2006-2009, Carnegie Mellon University. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 * 
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 
 * 3. Products derived from the software may not be called "Alice",
 *    nor may "Alice" appear in their name, without prior written
 *    permission of Carnegie Mellon University.
 * 
 * 4. All advertising materials mentioning features or use of this software
 *    must display the following acknowledgement:
 *    "This product includes software developed by Carnegie Mellon University"
 */
package org.alice.stageide.choosers;

class TimeSlider extends javax.swing.JSlider {
	private javax.swing.event.ChangeListener changeAdapter = new javax.swing.event.ChangeListener() {
		public void stateChanged(javax.swing.event.ChangeEvent e) {
			TimeSlider.this.updateTime();
		}
	};
	private double duration;
	private double time;
	public TimeSlider() {
		this.setMaximum( 1000 );
		this.setDuration( Double.NaN );
	}
	
	@Override
	public void addNotify() {
		super.addNotify();
		this.addChangeListener( this.changeAdapter );
	}
	@Override
	public void removeNotify() {
		this.removeChangeListener( this.changeAdapter );
		super.removeNotify();
	}
	private void updateValue() {
		if( Double.isNaN( this.time ) || Double.isNaN( this.duration ) ) {
			this.setValue( this.getMaximum() );
		} else {
			this.setValue( (int)((this.time/this.duration)*this.getMaximum()) );
		}
	}
	private void updateTime() {
		if( Double.isNaN( this.duration ) ) {
			//pass
		} else {
			this.time = this.getValue()*this.duration/this.getMaximum();
		}
	}
	
	public double getDuration() {
		return this.duration;
	}
	public void setDuration( double duration ) {
		this.duration = duration;
		this.setEnabled( Double.isNaN( this.duration )==false );
		this.updateValue();
	}
	private static java.text.NumberFormat format = new java.text.DecimalFormat( "0.00" );
	public double getTime() {
		if( Double.isNaN( this.time ) ) {
			return this.time;
		} else {
			return Double.parseDouble( format.format( this.time ) );
		}
	}
	public void setTime( double time ) {
		this.time = time;
		this.updateValue();
	}
}

/**
 * @author Dennis Cosgrove
 */
public class AudioSourceChooser extends org.alice.ide.choosers.AbstractChooser< org.alice.apis.moveandturn.AudioSource > {
	class BogusNode extends edu.cmu.cs.dennisc.alice.ast.Node {
		private edu.cmu.cs.dennisc.alice.ast.ExpressionProperty bogusProperty = new edu.cmu.cs.dennisc.alice.ast.ExpressionProperty( this ) {
			@Override
			public edu.cmu.cs.dennisc.alice.ast.AbstractType getExpressionType() {
				return edu.cmu.cs.dennisc.alice.ast.TypeDeclaredInJava.get( org.alice.virtualmachine.resources.AudioResource.class );
			}
		};
		@Override
		public void firePropertyChanged( edu.cmu.cs.dennisc.property.event.PropertyEvent e ) {
			super.firePropertyChanged( e );
			AudioSourceChooser.this.startTimeSlider.setTime( 0.0 );
			AudioSourceChooser.this.stopTimeSlider.setTime( Double.NaN );
			edu.cmu.cs.dennisc.croquet.KInputPane< ? > inputPane = AudioSourceChooser.this.getInputPane();
			if( inputPane != null ) {
				inputPane.updateOKButton();
			}
		}
	}
	
	private BogusNode bogusNode = new BogusNode();
	private java.awt.Component dropDown;
	
	private org.alice.stageide.controls.VolumeLevelControl volumeLevelControl = new org.alice.stageide.controls.VolumeLevelControl();
	private TimeSlider startTimeSlider = new TimeSlider();
	private TimeSlider stopTimeSlider = new TimeSlider();
	
	class TestOperation extends edu.cmu.cs.dennisc.zoot.InconsequentialActionOperation {
		public TestOperation() {
			this.putValue( javax.swing.Action.NAME, "test" );
		}
		@Override
		protected void performInternal( edu.cmu.cs.dennisc.zoot.ActionContext actionContext ) {
			org.alice.apis.moveandturn.AudioSource audioSource = getValue();
			edu.cmu.cs.dennisc.media.MediaFactory mediaFactory = edu.cmu.cs.dennisc.media.jmf.MediaFactory.getSingleton();
			edu.cmu.cs.dennisc.media.Player player = mediaFactory.createPlayer( audioSource.getAudioResource(), audioSource.getVolume(), audioSource.getStartTime(), audioSource.getStopTime() );
			player.test( AudioSourceChooser.this.getInputPane() );
		}
	};
	private TestOperation testOperation = new TestOperation();
	private javax.swing.JButton test = edu.cmu.cs.dennisc.zoot.ZManager.createButton( testOperation );

	private static edu.cmu.cs.dennisc.alice.ast.Expression getArgumentExpressionAt( edu.cmu.cs.dennisc.alice.ast.InstanceCreation instanceCreation, int index ) {
		assert instanceCreation.arguments.size() >= index;
		edu.cmu.cs.dennisc.alice.ast.Argument arg = instanceCreation.arguments.get( index );
		assert arg != null;
		return arg.expression.getValue();
	}
	public AudioSourceChooser() {
		edu.cmu.cs.dennisc.alice.ast.ResourceExpression resourceExpression = null;
		org.alice.virtualmachine.resources.AudioResource audioResource = null;
		double volumeLevel = 1.0;
		double startTime = 0.0;
		double stopTime = Double.NaN;

		edu.cmu.cs.dennisc.alice.ast.Expression previousExpression = this.getPreviousExpression();
		if( previousExpression instanceof edu.cmu.cs.dennisc.alice.ast.InstanceCreation ) {
			edu.cmu.cs.dennisc.alice.ast.InstanceCreation instanceCreation = (edu.cmu.cs.dennisc.alice.ast.InstanceCreation)previousExpression;
			int n = instanceCreation.arguments.size();
			if( n > 0 ) {
				edu.cmu.cs.dennisc.alice.ast.Expression expression0 = getArgumentExpressionAt( instanceCreation, 0 );
				if( expression0 instanceof edu.cmu.cs.dennisc.alice.ast.ResourceExpression ) {
					resourceExpression = (edu.cmu.cs.dennisc.alice.ast.ResourceExpression)expression0;
					org.alice.virtualmachine.Resource resource = resourceExpression.resource.getValue();
					if( resource instanceof org.alice.virtualmachine.resources.AudioResource ) {
						audioResource = (org.alice.virtualmachine.resources.AudioResource)resource;
					}
					if( n > 1 ) {
						if( n > 2 ) {
							if( n > 3 ) {
								edu.cmu.cs.dennisc.alice.ast.Expression expression3 = getArgumentExpressionAt( instanceCreation, 3 );
								if( expression3 instanceof edu.cmu.cs.dennisc.alice.ast.DoubleLiteral ) {
									edu.cmu.cs.dennisc.alice.ast.DoubleLiteral stopTimeLiteral = (edu.cmu.cs.dennisc.alice.ast.DoubleLiteral)expression3;
									stopTime = stopTimeLiteral.value.getValue();
								}
							}
							edu.cmu.cs.dennisc.alice.ast.Expression expression2 = getArgumentExpressionAt( instanceCreation, 2 );
							if( expression2 instanceof edu.cmu.cs.dennisc.alice.ast.DoubleLiteral ) {
								edu.cmu.cs.dennisc.alice.ast.DoubleLiteral startTimeLiteral = (edu.cmu.cs.dennisc.alice.ast.DoubleLiteral)expression2;
								startTime = startTimeLiteral.value.getValue();
							}
						}
						edu.cmu.cs.dennisc.alice.ast.Expression expression1 = getArgumentExpressionAt( instanceCreation, 1 );
						if( expression1 instanceof edu.cmu.cs.dennisc.alice.ast.DoubleLiteral ) {
							edu.cmu.cs.dennisc.alice.ast.DoubleLiteral volumeLevelLiteral = (edu.cmu.cs.dennisc.alice.ast.DoubleLiteral)expression1;
							volumeLevel = volumeLevelLiteral.value.getValue();
						}
					}
				}
			}
		}
		this.bogusNode.bogusProperty.setValue( resourceExpression );
		this.volumeLevelControl.setVolumeLevel( volumeLevel );
		this.startTimeSlider.setTime( startTime );
		this.stopTimeSlider.setTime( stopTime );

		org.alice.ide.common.Factory factory = org.alice.ide.IDE.getSingleton().getCodeFactory();
		this.dropDown = factory.createExpressionPropertyPane( bogusNode.bogusProperty, null );
		
		if( audioResource != null ) {
			double duration = audioResource.getDuration();
			if( Double.isNaN( duration ) ) {
				//pass
			} else {
				this.startTimeSlider.setDuration( duration );
				this.stopTimeSlider.setDuration( duration );
			}
		}

		this.volumeLevelControl.addChangeListener( new javax.swing.event.ChangeListener() {
			public void stateChanged( javax.swing.event.ChangeEvent e ) {
				AudioSourceChooser.this.getInputPane().updateOKButton();
			}
		} );
		this.startTimeSlider.addChangeListener( new javax.swing.event.ChangeListener() {
			public void stateChanged( javax.swing.event.ChangeEvent e ) {
				if( startTimeSlider.getValue() > stopTimeSlider.getValue() ) {
					stopTimeSlider.setValue( startTimeSlider.getValue() );
				}
				AudioSourceChooser.this.getInputPane().updateOKButton();
			}
		} );
		this.stopTimeSlider.addChangeListener( new javax.swing.event.ChangeListener() {
			public void stateChanged( javax.swing.event.ChangeEvent e ) {
				if( startTimeSlider.getValue() > stopTimeSlider.getValue() ) {
					startTimeSlider.setValue( stopTimeSlider.getValue() );
				}
				AudioSourceChooser.this.getInputPane().updateOKButton();
			}
		} );
	}
	public String getTitleDefault() {
		return "Enter Custom Audio Source";
	}
	public boolean isInputValid() {
		edu.cmu.cs.dennisc.alice.ast.ResourceExpression resourceExpression = (edu.cmu.cs.dennisc.alice.ast.ResourceExpression)bogusNode.bogusProperty.getValue();
		if( resourceExpression != null ) {
			return resourceExpression.resource.getValue() instanceof org.alice.virtualmachine.resources.AudioResource;
		} else {
			return false;
		}
	}
	@Override
	public String[] getLabelTexts() {
		return new String[] { 
				"resource:", 
				"volume:", 
				"", 
				"start marker:", 
				"stop marker:" };
	}
	@Override
	public java.awt.Component[] getComponents() {
		return new java.awt.Component[] { 
				this.dropDown, 
				new edu.cmu.cs.dennisc.croquet.swing.LineAxisPane( this.volumeLevelControl, javax.swing.Box.createHorizontalGlue() ), 
				javax.swing.Box.createVerticalStrut( 16 ), 
				this.startTimeSlider, 
				this.stopTimeSlider };
	}
	@Override
	public java.util.List< java.awt.Component[] > updateRows( java.util.List< java.awt.Component[] > rv ) {
		super.updateRows( rv );
		rv.add( edu.cmu.cs.dennisc.swing.SpringUtilities.createRow( javax.swing.Box.createRigidArea( new java.awt.Dimension( 0, 32 ) ), null ) );
		rv.add( edu.cmu.cs.dennisc.swing.SpringUtilities.createRow( this.test, null ) );
		return rv;
	}
		
	public org.alice.apis.moveandturn.AudioSource getValue() {
		org.alice.virtualmachine.resources.AudioResource audioResource;
		double volume = this.volumeLevelControl.getVolumeLevel();
		double start;
		double stop;
		double duration;
		edu.cmu.cs.dennisc.alice.ast.ResourceExpression resourceExpression = (edu.cmu.cs.dennisc.alice.ast.ResourceExpression)bogusNode.bogusProperty.getValue();
		if( resourceExpression != null ) {
			org.alice.virtualmachine.Resource resource = resourceExpression.resource.getValue();
			if( resource instanceof org.alice.virtualmachine.resources.AudioResource ) {
				audioResource = (org.alice.virtualmachine.resources.AudioResource)resource;
				duration = audioResource.getDuration();
			} else {
				audioResource = null;
				duration = Double.NaN;
			}
		} else {
			audioResource = null;
			duration = Double.NaN;
		}
		if( Double.isNaN( duration ) ) {
			//todo
			start = 0.0;
			stop = Double.NaN;
		} else {
			if( this.startTimeSlider.getValue() == this.startTimeSlider.getMinimum() ) {
				start = 0.0;
			} else {
				start = this.startTimeSlider.getTime();
			}
			if( this.stopTimeSlider.getValue() == this.stopTimeSlider.getMaximum() ) {
				stop = Double.NaN;
			} else {
				stop = this.stopTimeSlider.getTime();
			}
		}
		return new org.alice.apis.moveandturn.AudioSource( audioResource, volume, start, stop );
	}
	public static void main( String[] args ) {
		org.alice.ide.IDE ide = new org.alice.ide.FauxIDE();
		try {
			org.alice.stageide.cascade.customfillin.CustomizeAudioSourceFillIn fillIn = new org.alice.stageide.cascade.customfillin.CustomizeAudioSourceFillIn();
			if( fillIn != null ) {
				edu.cmu.cs.dennisc.print.PrintUtilities.println( fillIn.getValue() );
			}
		} catch( edu.cmu.cs.dennisc.cascade.CancelException ce ) {
		}
	}
	
}
