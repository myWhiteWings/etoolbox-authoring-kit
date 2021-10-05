package com.exadel.aem.toolkit.samples.models;

import com.exadel.aem.toolkit.api.annotations.assets.dependson.DependsOn;
import com.exadel.aem.toolkit.api.annotations.assets.dependson.DependsOnRef;
import com.exadel.aem.toolkit.api.annotations.assets.dependson.DependsOnTab;
import com.exadel.aem.toolkit.api.annotations.layouts.Tab;
import com.exadel.aem.toolkit.api.annotations.layouts.Tabs;
import com.exadel.aem.toolkit.api.annotations.main.AemComponent;
import com.exadel.aem.toolkit.api.annotations.main.Dialog;
import com.exadel.aem.toolkit.api.annotations.widgets.DialogField;
import com.exadel.aem.toolkit.api.annotations.widgets.Heading;
import com.exadel.aem.toolkit.api.annotations.widgets.Switch;
import com.exadel.aem.toolkit.api.annotations.widgets.TextField;
import com.exadel.aem.toolkit.api.annotations.widgets.select.Option;
import com.exadel.aem.toolkit.api.annotations.widgets.select.Select;
import com.exadel.aem.toolkit.samples.constants.GroupConstants;
import com.exadel.aem.toolkit.samples.constants.PathConstants;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@AemComponent(
    path = "content/dependson-simple-bindings-component",
    title = "DependsOn Simple Bindings Component",
    description = "DependsOn",
    resourceSuperType = PathConstants.FOUNDATION_PARBASE_PATH,
    componentGroup = GroupConstants.COMPONENT_GROUP
)
@Dialog
@DependsOnTab(tabTitle = "Second Tab", query = "@switch2")
@Tabs(
    value = {
        @Tab(title = "First Tab"),
        @Tab(title = "Second Tab")
    }
)
@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class DependsOnSimpleBindingsComponent {
    @Heading(value = "Add new text field")
    private String heading1;

    @Switch
    @ValueMapValue
    @DependsOnRef(name = "switch1")
    private String switch1;

    @TextField
    @DependsOn(query = "@switch1")
    @ValueMapValue
    private String textField;

    @Heading(value = "Show/Hide field")
    private String heading2;

    @DependsOnRef(name = "selectbox")
    @DialogField
    @Select(
        options = {
            @Option(text = "Hide Field", value = "hideval"),
            @Option(text = "Show Field", value = "showval")
        }
    )
    private String select;

    @DependsOn(query = "@selectbox === 'showval'")
    @DialogField
    @TextField
    private String text;

    @Heading(value = "Add new tab")
    private String heading3;
    @Switch
    @ValueMapValue
    @DependsOnRef(name = "switch2")
    private String switch2;


}
