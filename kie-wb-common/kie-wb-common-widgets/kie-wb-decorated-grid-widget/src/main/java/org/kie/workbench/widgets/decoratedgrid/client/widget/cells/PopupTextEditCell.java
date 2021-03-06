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
package org.kie.workbench.widgets.decoratedgrid.client.widget.cells;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.PopupPanel.PositionCallback;
import com.google.gwt.user.client.ui.TextBox;

/**
 * A Popup Text Editor.
 */
public class PopupTextEditCell extends AbstractPopupEditCell<String, String> {

    private final TextBox textBox;

    public PopupTextEditCell() {
        this( false );
    }

    public PopupTextEditCell( boolean isReadOnly ) {
        super( isReadOnly );
        this.textBox = new TextBox();

        // Tabbing out of the TextBox commits changes
        textBox.addKeyDownHandler( new KeyDownHandler() {

            public void onKeyDown( KeyDownEvent event ) {
                boolean keyTab = event.getNativeKeyCode() == KeyCodes.KEY_TAB;
                boolean keyEnter = event.getNativeKeyCode() == KeyCodes.KEY_ENTER;
                if ( keyTab
                        || keyEnter ) {
                    commit();
                }
            }

        } );
        vPanel.add( textBox );
    }

    @Override
    public void render( Context context,
                        String value,
                        SafeHtmlBuilder sb ) {
        if ( value != null ) {
            sb.append( renderer.render( value ) );
        }
    }

    // Commit the change
    @Override
    protected void commit() {

        // Update values
        String text = textBox.getValue();
        if ( text.length() == 0 ) {
            text = null;
        }
        setValue( lastContext,
                  lastParent,
                  text );
        if ( valueUpdater != null ) {
            valueUpdater.update( text );
        }
        panel.hide();
    }

    // Start editing the cell
    @Override
    protected void startEditing( final Context context,
                                 final Element parent,
                                 String value ) {

        textBox.setValue( value );

        panel.setPopupPositionAndShow( new PositionCallback() {
            public void setPosition( int offsetWidth,
                                     int offsetHeight ) {
                panel.setPopupPosition( parent.getAbsoluteLeft()
                                                + offsetX,
                                        parent.getAbsoluteTop()
                                                + offsetY );

                // Focus the first enabled control
                Scheduler.get().scheduleDeferred( new ScheduledCommand() {

                    public void execute() {
                        String text = textBox.getValue();
                        textBox.setFocus( true );
                        textBox.setCursorPos( text.length() );
                        textBox.setSelectionRange( 0,
                                                   text.length() );
                    }

                } );
            }
        } );

    }
}
