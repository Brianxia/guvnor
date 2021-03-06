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

package org.drools.workbench.screens.drltext.client.widget;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;
import org.drools.workbench.screens.drltext.client.resources.Resources;

/**
 * This is the default rule editor widget (just text editor based) - more to come later.
 */
public class RuleContentWidget
        extends Composite {

    private final TextArea text;
    private boolean isDirty;

    public RuleContentWidget() {
        text = new TextArea();
        text.setWidth( "100%" );

        text.getElement().setAttribute( "spellcheck",
                                        "false" );

        text.setStyleName( Resources.INSTANCE.CSS().defaultTextArea() );

        text.addChangeHandler( new ChangeHandler() {
            public void onChange( ChangeEvent event ) {
                isDirty = true;
            }
        } );

        text.addKeyDownHandler( new KeyDownHandler() {

            public void onKeyDown( KeyDownEvent event ) {
                if ( event.getNativeKeyCode() == KeyCodes.KEY_TAB ) {
                    int pos = text.getCursorPos();
                    insertText( "\t" );
                    text.setCursorPos( pos + 1 );
                    text.cancelKey();
                    text.setFocus( true );
                }
            }
        } );

        initWidget( text );
    }

    public void setContent( final String input,
                            final int visibleLines ) {
        final String content;
        if ( input == null ) {
            content = "";
        } else {
            content = input;
        }

        text.setVisibleLines( ( visibleLines == -1 ) ? 16 : visibleLines );
        text.setText( content );
    }

    public void insertText( String ins ) {
        int i = text.getCursorPos();
        String left = text.getText().substring( 0,
                                                i );
        String right = text.getText().substring( i,
                                                 text.getText().length() );
        text.setText( left + ins + right );
    }

    public boolean isDirty() {
        return isDirty;
    }

    public void setNotDirty() {
        this.isDirty = false;
    }

    public String getContent() {
        return text.getValue();
    }
}
