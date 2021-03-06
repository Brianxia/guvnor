/*
 * Copyright 2012 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.workbench.screens.guided.template.service;

import org.drools.workbench.models.guided.template.shared.TemplateModel;
import org.drools.workbench.screens.guided.template.model.GuidedTemplateEditorContent;
import org.jboss.errai.bus.server.annotations.Remote;
import org.kie.guvnor.commons.service.source.ViewSourceService;
import org.kie.guvnor.commons.service.validation.ValidationService;
import org.kie.guvnor.services.file.SupportsCopy;
import org.kie.guvnor.services.file.SupportsCreate;
import org.kie.guvnor.services.file.SupportsDelete;
import org.kie.guvnor.services.file.SupportsRead;
import org.kie.guvnor.services.file.SupportsRename;
import org.kie.guvnor.services.file.SupportsUpdate;
import org.uberfire.backend.vfs.Path;

@Remote
public interface GuidedRuleTemplateEditorService
        extends
        ViewSourceService<TemplateModel>,
        ValidationService<TemplateModel>,
        SupportsCreate<TemplateModel>,
        SupportsRead<TemplateModel>,
        SupportsUpdate<TemplateModel>,
        SupportsDelete,
        SupportsCopy,
        SupportsRename {

    GuidedTemplateEditorContent loadContent( final Path path );

}
