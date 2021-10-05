package com.exadel.aem.toolkit.samples.models;

import com.exadel.aem.toolkit.api.annotations.main.AemComponent;
import com.exadel.aem.toolkit.api.annotations.main.Dialog;
import com.exadel.aem.toolkit.api.annotations.widgets.Checkbox;
import com.exadel.aem.toolkit.api.annotations.widgets.DialogField;
import com.exadel.aem.toolkit.api.annotations.widgets.Heading;
import com.exadel.aem.toolkit.api.annotations.widgets.Switch;
import com.exadel.aem.toolkit.api.annotations.widgets.common.Position;
import com.exadel.aem.toolkit.samples.constants.GroupConstants;
import com.exadel.aem.toolkit.samples.constants.PathConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@AemComponent(
    path = "content/switch-checkbox-component",
    title = "Switch Component",
    description = "This component shows possibilities of interaction with switch and checkbox handlers",
    resourceSuperType = PathConstants.FOUNDATION_PARBASE_PATH,
    componentGroup = GroupConstants.COMPONENT_GROUP
)
@Dialog(extraClientlibs = "etoolbox-authoring-kit.samples.authoring")
@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SwitchComponent {
    @Heading(value = "Check Box Example", level = 3)
    private String headingCheckBox;
    @Checkbox()
    @ValueMapValue
    private String variableNameCheckBox;

    @Checkbox(text = "@CheckBox (text =)")
    @ValueMapValue
    private String text;

    @Checkbox(text = "@CheckBox (checked = true)", checked = true)
    @ValueMapValue
    private String checkbox3;

    @Checkbox(text = "@CheckBox (autosubmit = true)", autosubmit = true)
    @ValueMapValue
    private String checkbox4;
    @DialogField(description = "Bottom")
    @Checkbox(text = "Bottom", tooltipPosition = Position.BOTTOM)
    @ValueMapValue
    private String checkbox6;
    @DialogField(description = "Top")
    @Checkbox(text = "Top", tooltipPosition = Position.TOP)
    @ValueMapValue
    private String checkbox7;
    @DialogField(description = "Right")
    @Checkbox(text = "Right", tooltipPosition = Position.RIGHT)
    @ValueMapValue
    private String checkbox8;
    //    @Checkbox(text = "CheckBox5", disconnectedSublist = true)
//    @ValueMapValue
//    private String checkbox5;
//    @Checkbox(text = "CheckBox9", sublist)
//    @ValueMapValue
//    private String checkbox9;


    @Heading(value = "Switch Example", level = 3)
    private String headingSwitch;
    @Switch()
    @ValueMapValue
    private String defaultSwitch;
    @Switch(checked = true)
    @ValueMapValue
    private String switch2;
}
