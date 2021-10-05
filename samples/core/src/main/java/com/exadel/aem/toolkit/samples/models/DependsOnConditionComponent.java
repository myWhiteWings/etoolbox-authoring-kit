package com.exadel.aem.toolkit.samples.models;

import com.exadel.aem.toolkit.api.annotations.assets.dependson.DependsOn;
import com.exadel.aem.toolkit.api.annotations.assets.dependson.DependsOnActions;
import com.exadel.aem.toolkit.api.annotations.assets.dependson.DependsOnRef;
import com.exadel.aem.toolkit.api.annotations.assets.dependson.DependsOnRefTypes;
import com.exadel.aem.toolkit.api.annotations.layouts.Place;
import com.exadel.aem.toolkit.api.annotations.layouts.Tab;
import com.exadel.aem.toolkit.api.annotations.layouts.Tabs;
import com.exadel.aem.toolkit.api.annotations.main.AemComponent;
import com.exadel.aem.toolkit.api.annotations.main.Dialog;
import com.exadel.aem.toolkit.api.annotations.widgets.DialogField;
import com.exadel.aem.toolkit.api.annotations.widgets.Heading;
import com.exadel.aem.toolkit.api.annotations.widgets.Hidden;
import com.exadel.aem.toolkit.api.annotations.widgets.NumberField;
import com.exadel.aem.toolkit.api.annotations.widgets.TextField;
import com.exadel.aem.toolkit.samples.constants.GroupConstants;
import com.exadel.aem.toolkit.samples.constants.PathConstants;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

@AemComponent(
    path = "content/dependson-condition-component",
    title = "DependsOn Conditions Component",
    description = "DependsOn",
    resourceSuperType = PathConstants.FOUNDATION_PARBASE_PATH,
    componentGroup = GroupConstants.COMPONENT_GROUP
)
@Dialog
@Tabs(
    value = {
        @Tab(title = "Text Values"),
        @Tab(title = "Number Values")
    }
)
@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class DependsOnConditionComponent {

    @Place("Text Values")
    @Heading(value = "Field #3 will be shown if fields #1 and #2 are equals")
    private String heading1;

    @Place("Text Values")
    @Heading(value = " @DependsOn(query = \"@text1.toLowerCase() === @text2.toLowerCase()\")", level = 4)
    private String heading2;

    @Place("Text Values")
    @DependsOnRef(name = "text1")
    @DialogField
    @TextField
    private String text1;

    @Place("Text Values")
    @DependsOnRef(name = "text2")
    @DialogField
    @TextField
    private String text2;

    @Place("Text Values")
    @DependsOn(query = "@text1.toLowerCase() === @text2.toLowerCase()")
    @DialogField
    @TextField
    private String text3;

    @Place("Number Values")
    @Heading(value = "Field #3 will be shown if sum of fields #1 and #2 is bigger than 0")
    private String heading3;

    @Place("Number Values")
    @Heading(value = " @DependsOn(query = \"@sum > 0\")", level = 4)
    private String heading4;

    @Place("Number Values")
    @DependsOnRef(name = "field1", type = DependsOnRefTypes.NUMBER)
    @DialogField
    @NumberField
    private int field1;

    @Place("Number Values")
    @DependsOnRef(name = "field2", type = DependsOnRefTypes.NUMBER)
    @DialogField
    @NumberField
    private int field2;

    @Place("Number Values")
    @DependsOn(
        query = "@field1 + @field2",
        action = DependsOnActions.SET
    )
    @DependsOnRef(
        name = "sum",
        type = DependsOnRefTypes.NUMBER
    )
    @Hidden
    private int conditionGlobal;

    @Place("Number Values")
    @DialogField
    @DependsOn(query = "@sum > 0")
    @TextField
    private String field3;
}
