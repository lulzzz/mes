package com.qcadoo.mes.core.data.internal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcadoo.mes.core.data.api.DataDefinitionService;
import com.qcadoo.mes.core.data.definition.DataDefinition;
import com.qcadoo.mes.core.data.definition.FieldDefinition;
import com.qcadoo.mes.core.data.types.FieldType;
import com.qcadoo.mes.core.data.types.FieldTypeFactory;
import com.qcadoo.mes.core.data.validation.FieldValidatorFactory;

@Service
public final class DataDefinitionServiceImpl implements DataDefinitionService {

    @Autowired
    private FieldTypeFactory fieldTypeFactory;

    @Autowired
    private FieldValidatorFactory fieldValidationFactory;

    @Override
    public void save(final DataDefinition dataDefinition) {
        throw new UnsupportedOperationException("implement me");
    }

    @Override
    public DataDefinition get(final String entityName) {
        if ("products.product".equals(entityName)) {
            return createProductDefinition();
        } else if ("products.substitute".equals(entityName)) {
            return createSubstituteDefinition();
        } else if ("products.substituteComponent".equals(entityName)) {
            return createSubstituteComponentDefinition();
        } else if ("users.user".equals(entityName)) {
            return createUserDefinition();
        } else if ("users.group".equals(entityName)) {
            return createUserGroupDefinition();
        }
        return null;
    }

    private DataDefinition createProductDefinition() {
        DataDefinition dataDefinition = new DataDefinition("products.product");
        // GridDefinition gridDefinition = new GridDefinition("products");

        FieldDefinition fieldNumber = createFieldDefinition("number", fieldTypeFactory.stringType());
        fieldNumber.setValidators(fieldValidationFactory.required());
        FieldDefinition fieldName = createFieldDefinition("name", fieldTypeFactory.textType());
        fieldName.setValidators(fieldValidationFactory.required());
        FieldDefinition fieldTypeOfMaterial = createFieldDefinition("typeOfMaterial",
                fieldTypeFactory.enumType("product", "intermediate", "component"));
        fieldTypeOfMaterial.setValidators(fieldValidationFactory.required());
        FieldDefinition fieldEan = createFieldDefinition("ean", fieldTypeFactory.stringType());
        FieldDefinition fieldCategory = createFieldDefinition("category", fieldTypeFactory.dictionaryType("categories"));
        FieldDefinition fieldUnit = createFieldDefinition("unit", fieldTypeFactory.stringType());

        dataDefinition.setFullyQualifiedClassName("com.qcadoo.mes.core.data.beans.Product");
        // dataDefinition.setGrids(Arrays.asList(new GridDefinition[] { gridDefinition }));
        dataDefinition.addField(fieldNumber);
        dataDefinition.addField(fieldName);
        dataDefinition.addField(fieldTypeOfMaterial);
        dataDefinition.addField(fieldEan);
        dataDefinition.addField(fieldCategory);
        dataDefinition.addField(fieldUnit);

        // ColumnDefinition columnNumber = createColumnDefinition("number", fieldNumber, null);
        // ColumnDefinition columnName = createColumnDefinition("name", fieldName, null);
        // ColumnDefinition columnType = createColumnDefinition("typeOfMaterial", fieldTypeOfMaterial, null);
        // ColumnDefinition columnEan = createColumnDefinition("ean", fieldEan, null);

        // gridDefinition.setColumns(Arrays.asList(new ColumnDefinition[] { columnNumber, columnName, columnType, columnEan }));

        return dataDefinition;
    }

    private DataDefinition createSubstituteDefinition() {
        DataDefinition dataDefinition = new DataDefinition("products.substitute");
        // GridDefinition gridDefinition = new GridDefinition("substitutes");

        FieldDefinition fieldNumber = createFieldDefinition("number", fieldTypeFactory.stringType(), true);
        FieldDefinition fieldName = createFieldDefinition("name", fieldTypeFactory.textType(), true);
        FieldDefinition fieldPriority = createFieldDefinition("priority", fieldTypeFactory.integerType(), true);
        FieldDefinition fieldEffectiveDateFrom = createFieldDefinition("effectiveDateFrom", fieldTypeFactory.dateTimeType());
        FieldDefinition fieldEffectiveDateTo = createFieldDefinition("effectiveDateTo", fieldTypeFactory.dateTimeType());
        FieldDefinition fieldProduct = createFieldDefinition("product",
                fieldTypeFactory.eagerBelongsToType("products.product", "name"));
        fieldProduct.setHidden(true);

        dataDefinition.setFullyQualifiedClassName("com.qcadoo.mes.core.data.beans.Substitute");
        // dataDefinition.setGrids(Arrays.asList(new GridDefinition[] { gridDefinition }));
        dataDefinition.addField(fieldNumber);
        dataDefinition.addField(fieldName);
        dataDefinition.addField(fieldPriority);
        dataDefinition.addField(fieldEffectiveDateFrom);
        dataDefinition.addField(fieldEffectiveDateTo);
        dataDefinition.addField(fieldProduct);

        // ColumnDefinition columnNumber = createColumnDefinition("number", fieldNumber, null);
        // ColumnDefinition columnName = createColumnDefinition("name", fieldName, null);
        // ColumnDefinition columnPriority = createColumnDefinition("priority", fieldPriority, null);
        //
        // gridDefinition.setColumns(Arrays.asList(new ColumnDefinition[] { columnNumber, columnName, columnPriority }));

        return dataDefinition;
    }

    private DataDefinition createSubstituteComponentDefinition() {
        DataDefinition dataDefinition = new DataDefinition("products.substituteComponent");
        // GridDefinition gridDefinition = new GridDefinition("substituteComponents");

        FieldDefinition fieldProduct = createFieldDefinition("product",
                fieldTypeFactory.eagerBelongsToType("products.product", "name"), true);
        FieldDefinition fieldSubstitute = createFieldDefinition("substitute",
                fieldTypeFactory.eagerBelongsToType("products.substitute", "name"));
        fieldSubstitute.setHidden(true);
        FieldDefinition fieldQuantity = createFieldDefinition("quantity", fieldTypeFactory.decimalType(), true);

        dataDefinition.setFullyQualifiedClassName("com.qcadoo.mes.core.data.beans.SubstituteComponent");
        // dataDefinition.setGrids(Arrays.asList(new GridDefinition[] { gridDefinition }));
        dataDefinition.addField(fieldProduct);
        dataDefinition.addField(fieldSubstitute);
        dataDefinition.addField(fieldQuantity);

        // ColumnDefinition columnSubstituteNumber = createColumnDefinition("number", fieldProduct,
        // "fields['product'].fields['number']");
        // ColumnDefinition columnProductName = createColumnDefinition("name", fieldProduct, "fields['product'].fields['name']");
        // ColumnDefinition columnQuantity = createColumnDefinition("quantity", fieldQuantity, null);
        // gridDefinition.setColumns(Arrays.asList(new ColumnDefinition[] { columnSubstituteNumber, columnProductName,
        // columnQuantity }));

        return dataDefinition;
    }

    // private ColumnDefinition createColumnDefinition(final String name, final FieldDefinition field, final String expression) {
    // ColumnDefinition columnDefinition = new ColumnDefinition(name);
    // columnDefinition.setFields(Arrays.asList(new FieldDefinition[] { field }));
    // columnDefinition.setExpression(expression);
    // return columnDefinition;
    // }

    private DataDefinition createUserDefinition() {
        DataDefinition dataDefinition = new DataDefinition("users.user");

        FieldDefinition fieldFirstName = createFieldDefinition("firstName", fieldTypeFactory.stringType());
        fieldFirstName.setValidators(fieldValidationFactory.required());
        FieldDefinition fieldLastName = createFieldDefinition("lastName", fieldTypeFactory.stringType());
        fieldLastName.setValidators(fieldValidationFactory.required());
        FieldDefinition fieldLogin = createFieldDefinition("login", fieldTypeFactory.stringType());
        fieldLogin.setValidators(fieldValidationFactory.required());
        FieldDefinition fieldPassword = createFieldDefinition("password", fieldTypeFactory.passwordType());
        fieldPassword.setValidators(fieldValidationFactory.required());
        FieldDefinition fieldEmail = createFieldDefinition("email", fieldTypeFactory.stringType());
        // TODO KRNA zamienic na relacje
        FieldDefinition fieldGroup = createFieldDefinition("group",
                fieldTypeFactory.enumType("Administrator", "Operator - Full", "Operator - ReadOnly"));

        dataDefinition.setFullyQualifiedClassName("com.qcadoo.mes.core.data.beans.User");

        dataDefinition.addField(fieldFirstName);
        dataDefinition.addField(fieldLastName);
        dataDefinition.addField(fieldLogin);
        dataDefinition.addField(fieldPassword);
        dataDefinition.addField(fieldEmail);
        dataDefinition.addField(fieldGroup);

        return dataDefinition;
    }

    private DataDefinition createUserGroupDefinition() {
        DataDefinition dataDefinition = new DataDefinition("users.group");

        FieldDefinition fieldName = createFieldDefinition("name", fieldTypeFactory.stringType());
        fieldName.setValidators(fieldValidationFactory.required());
        FieldDefinition fieldDescription = createFieldDefinition("description", fieldTypeFactory.textType());
        // TODO KRNA zamienic na relacje
        FieldDefinition fieldRole = createFieldDefinition("role", fieldTypeFactory.enumType("read", "write", "delete"));

        dataDefinition.setFullyQualifiedClassName("com.qcadoo.mes.core.data.beans.Group");
        dataDefinition.addField(fieldName);
        dataDefinition.addField(fieldDescription);
        dataDefinition.addField(fieldRole);

        return dataDefinition;
    }

    private FieldDefinition createFieldDefinition(final String name, final FieldType type) {
        FieldDefinition fieldDefinition = new FieldDefinition(name);
        fieldDefinition.setType(type);
        return fieldDefinition;
    }

    private FieldDefinition createFieldDefinition(final String name, final FieldType type, final boolean required) {
        FieldDefinition fieldDefinition = new FieldDefinition(name);
        fieldDefinition.setType(type);
        fieldDefinition.setValidators(fieldValidationFactory.required());
        return fieldDefinition;
    }

    @Override
    public void delete(final String entityName) {
        throw new UnsupportedOperationException("implement me");
    }

    @Override
    public List<DataDefinition> list() {
        throw new UnsupportedOperationException("implement me");
    }

}
