/*
 * Alice 3 End User License Agreement
 *
 * Copyright (c) 2006-2015, Carnegie Mellon University. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * 3. Products derived from the software may not be called "Alice", nor may "Alice" appear in their name, without prior written permission of Carnegie Mellon University.
 *
 * 4. All advertising materials mentioning features or use of this software must display the following acknowledgement: "This product includes software developed by Carnegie Mellon University"
 *
 * 5. The gallery of art assets and animations provided with this software is contributed by Electronic Arts Inc. and may be used for personal, non-commercial, and academic use only. Redistributions of any program source code that utilizes The Sims 2 Assets must also retain the copyright notice, list of conditions and the disclaimer contained in The Alice 3.0 Art Gallery License.
 *
 * DISCLAIMER:
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND.  ANY AND ALL EXPRESS, STATUTORY OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,  FITNESS FOR A PARTICULAR PURPOSE, TITLE, AND NON-INFRINGEMENT ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHORS, COPYRIGHT OWNERS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, PUNITIVE OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING FROM OR OTHERWISE RELATING TO THE USE OF OR OTHER DEALINGS WITH THE SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package org.lgna.story.resources.prop;

import org.lgna.project.annotations.FieldTemplate;
import org.lgna.project.annotations.Visibility;
import org.lgna.story.SJointedModel;
import org.lgna.story.implementation.BasicJointedModelImp;
import org.lgna.story.implementation.JointedModelImp;
import org.lgna.story.resources.ImplementationAndVisualType;
import org.lgna.story.resources.JointId;
import org.lgna.story.resources.JointedModelResource;
import org.lgna.story.resources.PropResource;

public enum ArmoireResource implements PropResource {
  LOFT_RED_FINISH_BLACK_TRIM(ImplementationAndVisualType.SIMS2),
  LOFT_RED_FINISH_DARK_HONEY_TRIM(ImplementationAndVisualType.SIMS2),
  LOFT_DARK_WOOD_BLACK_TRIM(ImplementationAndVisualType.SIMS2),
  LOFT_DARK_WOOD_DARK_HONEY_TRIM(ImplementationAndVisualType.SIMS2),
  LOFT_OAK_BLACK_TRIM(ImplementationAndVisualType.SIMS2),
  LOFT_OAK_DARK_HONEY_TRIM(ImplementationAndVisualType.SIMS2),
  QUAINT_BLUE(ImplementationAndVisualType.SIMS2),
  QUAINT_GREEN(ImplementationAndVisualType.SIMS2),
  QUAINT_RED(ImplementationAndVisualType.SIMS2),
  QUAINT_LEAVES(ImplementationAndVisualType.SIMS2),
  QUAINT_ROSES(ImplementationAndVisualType.SIMS2),
  CENTRAL_ASIAN_DRAGON(ImplementationAndVisualType.SIMS2),
  CENTRAL_ASIAN_GREEN_FLORAL(ImplementationAndVisualType.SIMS2),
  CENTRAL_ASIAN_LATTICE(ImplementationAndVisualType.SIMS2),
  CENTRAL_ASIAN_LION(ImplementationAndVisualType.SIMS2),
  CENTRAL_ASIAN_SIMPLE_FLORAL(ImplementationAndVisualType.SIMS2),
  CENTRAL_ASIAN_STORY(ImplementationAndVisualType.SIMS2),
  COLONIAL_REDWOOD(ImplementationAndVisualType.SIMS2),
  COLONIAL_DARK_WOOD(ImplementationAndVisualType.SIMS2),
  COLONIAL_LIGHT_WOOD(ImplementationAndVisualType.SIMS2),
  MOROCCAN_GREEN(ImplementationAndVisualType.SIMS2),
  MOROCCAN_BLUE(ImplementationAndVisualType.SIMS2),
  MOROCCAN_RED(ImplementationAndVisualType.SIMS2);

  @FieldTemplate(visibility = Visibility.COMPLETELY_HIDDEN) public static final JointId ROOT = new JointId(null, ArmoireResource.class);
  @FieldTemplate(visibility = Visibility.PRIME_TIME) public static final JointId RIGHT_DOOR = new JointId(ROOT, ArmoireResource.class);
  @FieldTemplate(visibility = Visibility.PRIME_TIME) public static final JointId LEFT_DOOR = new JointId(ROOT, ArmoireResource.class);

  @FieldTemplate(visibility = Visibility.COMPLETELY_HIDDEN) public static final JointId[] JOINT_ID_ROOTS = {ROOT};

  private final ImplementationAndVisualType resourceType;

  ArmoireResource() {
    this(ImplementationAndVisualType.ALICE);
  }

  ArmoireResource(ImplementationAndVisualType resourceType) {
    this.resourceType = resourceType;
  }

  @Override
  public JointId[] getRootJointIds() {
    return ArmoireResource.JOINT_ID_ROOTS;
  }

  @Override
  public JointedModelImp.JointImplementationAndVisualDataFactory<JointedModelResource> getImplementationAndVisualFactory() {
    return this.resourceType.getFactory(this);
  }

  @Override
  public BasicJointedModelImp createImplementation(SJointedModel abstraction) {
    return new BasicJointedModelImp(abstraction, this.resourceType.getFactory(this));
  }
}
