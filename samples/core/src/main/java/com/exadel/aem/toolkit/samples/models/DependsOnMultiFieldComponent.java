package com.exadel.aem.toolkit.samples.models;

import com.exadel.aem.toolkit.api.annotations.assets.dependson.DependsOn;
import com.exadel.aem.toolkit.api.annotations.assets.dependson.DependsOnRef;
import com.exadel.aem.toolkit.api.annotations.layouts.Place;
import com.exadel.aem.toolkit.api.annotations.main.AemComponent;
import com.exadel.aem.toolkit.api.annotations.main.Dialog;
import com.exadel.aem.toolkit.api.annotations.widgets.DialogField;
import com.exadel.aem.toolkit.api.annotations.widgets.Heading;
import com.exadel.aem.toolkit.api.annotations.widgets.MultiField;
import com.exadel.aem.toolkit.api.annotations.widgets.Switch;
import com.exadel.aem.toolkit.api.annotations.widgets.TextField;
import com.exadel.aem.toolkit.samples.constants.GroupConstants;
import com.exadel.aem.toolkit.samples.constants.PathConstants;

import java.util.List;

@AemComponent(
    path = "content/dependson-multifield-component",
    title = "DependsOn Multie Field Component",
    description = "DependsOn",
    resourceSuperType = PathConstants.FOUNDATION_PARBASE_PATH,
    componentGroup = GroupConstants.COMPONENT_GROUP
)
@Dialog
public class DependsOnMultiFieldComponent {
    @DialogField(label = "Fields can be hided or shown after adding")
    @MultiField(value = DependsOnMultiFieldComponent.MultiFieldItem.class)
    List<String> listOfStrings;
    @Heading(value = " @Switch\n" +
        "        @DependsOnRef\n" +
        "        private boolean showText;", level = 4)
    private String heading1;
    @Heading(value = " @TextField\n" +
        "        @DependsOn(query = \"@showText(coral-multifield-item-content)\")\n" +
        "        private String text;", level = 4)
    private String heading2;
    private static class MultiFieldItem {

        @DialogField(label = "Show/Hide field")
        @Switch
        @DependsOnRef
        private boolean showText;

        @DialogField(label = "Field added")
        @TextField
        @DependsOn(query = "@showText(coral-multifield-item-content)")
        private String text;
    }
}
