package org.drools.workbench.screens.guided.rule.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import org.kie.guvnor.commons.ui.client.resources.ItemImages;
import org.drools.workbench.screens.guided.rule.client.resources.css.GuidedRuleEditorCss;
import org.drools.workbench.screens.guided.rule.client.resources.images.GuidedRuleEditorImages;

/**
 * Resources for the Guided Rule Editor
 */
public interface GuidedRuleEditorResources extends ClientBundle {

    GuidedRuleEditorResources INSTANCE = GWT.create( GuidedRuleEditorResources.class );

    ItemImages itemImages();

    @Source("css/GuidedRuleEditor.css")
    GuidedRuleEditorCss css();

    GuidedRuleEditorImages images();

}
