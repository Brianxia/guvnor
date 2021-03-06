package org.kie.guvnor.datamodel.backend.server.builder.projects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.workbench.models.commons.shared.oracle.DataType;
import org.kie.guvnor.datamodel.model.FieldAccessorsAndMutators;
import org.kie.guvnor.datamodel.model.ModelField;
import org.kie.guvnor.datamodel.oracle.ProjectDataModelOracleImpl;

/**
 * Base FactBuilder containing common code
 */
public abstract class BaseFactBuilder implements FactBuilder {

    private final ProjectDataModelOracleBuilder builder;

    private final String type;
    private final List<ModelField> fields = new ArrayList<ModelField>();

    private final boolean isCollection;
    private final boolean isEvent;
    private final boolean isDeclaredType;

    public BaseFactBuilder( final ProjectDataModelOracleBuilder builder,
                            final Class<?> clazz,
                            final boolean isEvent,
                            final boolean isDeclaredType ) {
        this.builder = builder;
        this.type = clazz.getName();
        this.isCollection = isCollection( clazz );
        this.isEvent = isEvent;
        this.isDeclaredType = isDeclaredType;

        addField( new ModelField( DataType.TYPE_THIS,
                                  type,
                                  ModelField.FIELD_CLASS_TYPE.REGULAR_CLASS,
                                  FieldAccessorsAndMutators.ACCESSOR,
                                  DataType.TYPE_THIS ) );
    }

    public BaseFactBuilder( final ProjectDataModelOracleBuilder builder,
                            final String type,
                            final boolean isCollection,
                            final boolean isEvent,
                            final boolean isDeclaredType ) {
        this.builder = builder;
        this.type = type;
        this.isCollection = isCollection;
        this.isEvent = isEvent;
        this.isDeclaredType = isDeclaredType;

        addField( new ModelField( DataType.TYPE_THIS,
                                  type,
                                  ModelField.FIELD_CLASS_TYPE.REGULAR_CLASS,
                                  FieldAccessorsAndMutators.ACCESSOR,
                                  DataType.TYPE_THIS ) );
    }

    private boolean isCollection( final Class<?> clazz ) {
        return ( clazz != null && Collection.class.isAssignableFrom( clazz ) );
    }

    public String getType() {
        return type;
    }

    protected String getType( final Class<?> clazz ) {
        return clazz.getName();
    }

    protected FactBuilder addField( final ModelField field ) {
        this.fields.add( field );
        return this;
    }

    @Override
    public ProjectDataModelOracleBuilder end() {
        return builder;
    }

    @Override
    public void build( final ProjectDataModelOracleImpl oracle ) {
        oracle.addFactsAndFields( buildFactsAndFields() );
        oracle.addCollectionTypes( buildCollectionTypes() );
        oracle.addEventTypes( buildEventTypes() );
        oracle.addDeclaredTypes( buildDeclaredTypes() );
    }

    public ProjectDataModelOracleBuilder getDataModelBuilder() {
        return this.builder;
    }

    private Map<String, ModelField[]> buildFactsAndFields() {
        final Map<String, ModelField[]> loadableFactsAndFields = new HashMap<String, ModelField[]>();
        final ModelField[] loadableFields = new ModelField[ fields.size() ];
        fields.toArray( loadableFields );
        loadableFactsAndFields.put( type,
                                    loadableFields );
        return loadableFactsAndFields;
    }

    private Map<String, Boolean> buildCollectionTypes() {
        final Map<String, Boolean> loadableCollectionTypes = new HashMap<String, Boolean>();
        loadableCollectionTypes.put( type,
                                     isCollection );
        return loadableCollectionTypes;
    }

    private Map<String, Boolean> buildEventTypes() {
        final Map<String, Boolean> loadableEventTypes = new HashMap<String, Boolean>();
        loadableEventTypes.put( type,
                                isEvent );
        return loadableEventTypes;
    }

    private Map<String, Boolean> buildDeclaredTypes() {
        final Map<String, Boolean> loadableDeclaredTypes = new HashMap<String, Boolean>();
        loadableDeclaredTypes.put( type,
                                   isDeclaredType );
        return loadableDeclaredTypes;
    }

}
