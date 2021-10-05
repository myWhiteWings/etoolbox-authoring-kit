package com.exadel.aem.toolkit.samples.models;

import com.exadel.aem.toolkit.api.annotations.layouts.Place;
import com.exadel.aem.toolkit.api.annotations.layouts.Tab;
import com.exadel.aem.toolkit.api.annotations.layouts.Tabs;
import com.exadel.aem.toolkit.api.annotations.main.AemComponent;
import com.exadel.aem.toolkit.api.annotations.main.Dialog;
import com.exadel.aem.toolkit.api.annotations.widgets.DialogField;
import com.exadel.aem.toolkit.api.annotations.widgets.Heading;
import com.exadel.aem.toolkit.api.annotations.widgets.TextField;
import com.exadel.aem.toolkit.api.annotations.widgets.textarea.TextArea;
import com.exadel.aem.toolkit.samples.constants.GroupConstants;
import com.exadel.aem.toolkit.samples.constants.PathConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@AemComponent(
    path = "content/text-fields-component",
    title = "Text Fields Component",
    description = "This component shows possibilities of interaction with text fields",
    resourceSuperType = PathConstants.FOUNDATION_PARBASE_PATH,
    componentGroup = GroupConstants.COMPONENT_GROUP
)
@Dialog(extraClientlibs = "authoring-toolkit.samples.authoring")
@Tabs(value = { @Tab(title = "Headings"),
    @Tab(title = "Text Fields"),
    @Tab(title = "Configurations"),
    @Tab(title = "Text Area")})
@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class TextFieldsComponent {
    @Place("Headings")
    @Heading(value = "@Heading (value = Heading, level = 2)",level = 3)
    String headingDefault;

    @Place("Headings")
    @Heading(value = "Heading",level = 2)
    String headingDefaultValue;

    @Place("Text Fields")
    @Heading(value = "@TextField (value = ' ')",level = 3)
    String headingDefaultTextField;

    @Place("Text Fields")
    @DialogField(
        name = "defaultFieldWithAKAnnotation",
        description = "Additional info to the field"
    )
    @TextField(value = "Default text")
    @ValueMapValue
    private String defaultFieldWithAKAnnotation;

    @Place("Text Fields")
    @Heading(value = "@TextField (emptyText = )",level = 3)
    String headingEmptyTextField;

    @Place("Text Fields")
    @DialogField(
        name = "optionalField",
        label = "This field is optional",
        description = "Text field below can stay empty"
    )
    @TextField(emptyText = "empty text")
    @ValueMapValue
    private String emptyTextField;

    @Place("Configurations")
    @Heading(value = "@DialogField (required = true)",level = 3)
    String headingRequiredField;

    @Place("Configurations")
    @DialogField(
        name = "requiredField",
        label = "This field is required",
        description = "Text field below has to be filled before tab closing",
        required = true
    )
    @TextField()
    @ValueMapValue
    private String requiredField;

    @Place("Text Area")
    @Heading(value = "Here you can fill existing area with text or add some additional areas", level = 3)
    private String textAreaHeading;

    @Place("Text Area")
    @Heading(value = " @TextArea(emptyText, value)", level = 4)
    private String headingDefaultTextArea;
    @Place("Text Area")
    @DialogField(
        name = "defaultTextArea",
        description = "Text area with default quantity of columns(1) and rows(5)"
    )
    @TextArea(emptyText = "Default text area",value = "Default text that was generated by @TextArea annotation")
    @ValueMapValue
    private String defaultTextArea;

    @Place("Text Area")
    @Heading(value = " @TextArea(emptyText, rows)", level = 4)
    private String rowLimitsTextArea;

    @Place("Text Area")
    @DialogField(
        name = "rowsLimitTextArea",
        description = "Text area with limitation to 2 rows"
    )
    @TextArea(emptyText = "Default text area", rows = 2)
    @ValueMapValue
    private String rowsLimitTextArea;

    public String getRequiredField() {
        return requiredField;
    }
    public String getEmptyTextField() {
        return emptyTextField;
    }
    public String getDefaultFieldWithAKAnnotation() {
        return defaultFieldWithAKAnnotation;
    }
    public String getDefaultTextArea() {
        return defaultTextArea;
    }
    public String getRowsLimitTextArea() {
        return rowsLimitTextArea;
    }

}
