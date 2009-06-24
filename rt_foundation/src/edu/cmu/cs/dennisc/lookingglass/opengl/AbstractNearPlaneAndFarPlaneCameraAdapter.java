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

package edu.cmu.cs.dennisc.lookingglass.opengl;

/**
 * @author Dennis Cosgrove
 */
public abstract class AbstractNearPlaneAndFarPlaneCameraAdapter< E extends edu.cmu.cs.dennisc.scenegraph.AbstractNearPlaneAndFarPlaneCamera > extends AbstractCameraAdapter< E > {
	private float m_near;
	private float m_far;

	protected abstract void setupProjection( Context context, java.awt.Rectangle actualViewport, float near, float far );

	@Override
	protected void setupProjection( edu.cmu.cs.dennisc.lookingglass.opengl.Context context, java.awt.Rectangle actualViewport ) {
		setupProjection( context, actualViewport, m_near, m_far );
	}
	
	@Override
	protected void propertyChanged( edu.cmu.cs.dennisc.property.InstanceProperty<?> property ) {
		if( property == m_element.nearClippingPlaneDistance ) {
			m_near = m_element.nearClippingPlaneDistance.getValue().floatValue();
		} else if( property == m_element.farClippingPlaneDistance ) {
			m_far = m_element.farClippingPlaneDistance.getValue().floatValue();
		} else {
			super.propertyChanged( property );
		}
	}
}
