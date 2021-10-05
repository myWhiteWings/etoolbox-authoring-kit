package com.exadel.aem.toolkit.samples.models;

import com.exadel.aem.toolkit.api.annotations.main.AemComponent;
import com.exadel.aem.toolkit.api.annotations.main.Dialog;
import com.exadel.aem.toolkit.api.annotations.widgets.Alert;
import com.exadel.aem.toolkit.api.annotations.widgets.Heading;
import com.exadel.aem.toolkit.api.annotations.widgets.attribute.Attribute;
import com.exadel.aem.toolkit.api.annotations.widgets.common.Size;
import com.exadel.aem.toolkit.api.annotations.widgets.common.StatusVariant;
import com.exadel.aem.toolkit.samples.constants.GroupConstants;
import com.exadel.aem.toolkit.samples.constants.PathConstants;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@AemComponent(
    path = "content/alert-component",
    title = "Alert Component",
    description = "This component shows possibilities of interaction with alerts",
    resourceSuperType = PathConstants.FOUNDATION_PARBASE_PATH,
    componentGroup = GroupConstants.COMPONENT_GROUP
)
@Dialog(extraClientlibs = "samples.alert-component.authoring")
@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AlertComponent {
    @Heading(value = "Default Alert Example\n@Alert(title,text)", level = 4)
    private String defaultHeading;
    @Alert(title = "Alert's title", text = "Alert's text")
    @ValueMapValue
    private String defaultAlert;
    @Heading(value = "Alerts Size Examples\n@Alert(title,text,size)", level = 4)
    private String alertsSizesHeading;
    @Attribute(className = "size")
    @Alert(title = "Alert's title", text = "Large Alert ", size = Size.LARGE)
    @ValueMapValue
    private String largeAlert;

    @Attribute(className = "size")
    @Alert(title = "Alert's title", text = "Small Alert ", size = Size.SMALL)
    @ValueMapValue
    private String smallAlert;

    @Heading(value = "Alerts Types Examples\n@Alert(variant, text)", level = 4)
    private String alertsVariantHeading;

    @Attribute(className = "variant")
    @Alert(variant = StatusVariant.SUCCESS, text = "Success")
    @ValueMapValue
    private String alertSuccess;

    @Attribute(className = "variant")
    @Alert(variant = StatusVariant.WARNING, text = "Warning")
    @ValueMapValue
    private String alertWarning;

    @Attribute(className = "variant")
    @Alert(variant = StatusVariant.ERROR, text = "Error")
    @ValueMapValue
    private String alertError;

    @Attribute(className = "variant")
    @Alert(variant = StatusVariant.HELP, text = "Help")
    @ValueMapValue
    private String alertHelp;

    @Attribute(className = "variant")
    @Alert(variant = StatusVariant.INFO, text = "Info")
    @ValueMapValue
    private String alertInfo;
}
