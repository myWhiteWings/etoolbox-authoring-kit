package com.exadel.aem.toolkit.samples.models;

import com.exadel.aem.toolkit.api.annotations.layouts.Place;
import com.exadel.aem.toolkit.api.annotations.layouts.Tab;
import com.exadel.aem.toolkit.api.annotations.layouts.Tabs;
import com.exadel.aem.toolkit.api.annotations.main.AemComponent;
import com.exadel.aem.toolkit.api.annotations.main.Dialog;
import com.exadel.aem.toolkit.api.annotations.widgets.Heading;
import com.exadel.aem.toolkit.api.annotations.widgets.TextField;
import com.exadel.aem.toolkit.api.annotations.widgets.attribute.Attribute;
import com.exadel.aem.toolkit.api.annotations.widgets.button.Button;
import com.exadel.aem.toolkit.api.annotations.widgets.button.ButtonType;
import com.exadel.aem.toolkit.api.annotations.widgets.common.ElementVariant;
import com.exadel.aem.toolkit.api.annotations.widgets.common.Size;
import com.exadel.aem.toolkit.samples.constants.GroupConstants;
import com.exadel.aem.toolkit.samples.constants.PathConstants;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@AemComponent(
    path = "content/button-component",
    title = "Button Component",
    description = "This component shows possibilities of interaction with button",
    resourceSuperType = PathConstants.FOUNDATION_PARBASE_PATH,
    componentGroup = GroupConstants.COMPONENT_GROUP
)
@Dialog(extraClientlibs = "authoring-toolkit.samples.authoring")
@Tabs(value = {@Tab(title = "Starter tab"),
    @Tab(title = "Button Variants"),
    @Tab(title = "Button size"),
    @Tab(title = "Button Types"),
    @Tab(title = "Initial state types"),
    @Tab(title = "IDK")})
@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ButtonComponent {
    @Heading(value = "Default Button Example\n@Button(text)", level = 4)
    private String defaultHeading;
    @Button(text = "default button", textComment = "textComment")
    @ValueMapValue
    private String defaultButton;

    @Heading(value = "Button with icon and hided text\n@Button(text,hidedText=true,icon)", level = 4)
    private String hidedTextButtonHeading;
    @Button(text = "Hided text", hideText = true, icon = "search")
    private String hidedTextButton;

    @Heading(value = "Button with displayed hotkeys\n@Button(text,command)", level = 4)
    private String hotkeysButtonHeading;
    @Button(command = "ctrl+c", text = "Command")
    private String hotkeysButton;

    @Place("Button Variants")
    @Heading(value = "@Button(variant = ElementVariant.ACTION_BAR)", level = 4)
    private String headingsVariants1;
    @Place("Button Variants")
    @Button(variant = ElementVariant.ACTION_BAR, text = "Action Bar")
    private String actionBar;
    @Place("Button Variants")
    @Heading(value = "@Button(variant = ElementVariant.MINIMAL)", level = 4)
    private String headingsVariants2;
    @Place("Button Variants")
    @Attribute(className = "button-item")
    @Button(variant = ElementVariant.MINIMAL, text = "Minimal")
    private String minimal;
    @Place("Button Variants")
    @Heading(value = "@Button(variant = ElementVariant.PRIMARY)", level = 4)
    private String headingsVariants3;
    @Place("Button Variants")
    @Attribute(className = "button-item")
    @Button(variant = ElementVariant.PRIMARY, text = "Primary")
    private String primary;
    @Place("Button Variants")
    @Heading(value = "@Button(variant = ElementVariant.QUIET)", level = 4)
    private String headingsVariants4;
    @Place("Button Variants")
    @Attribute(className = "button-item")
    @Button(variant = ElementVariant.QUIET, text = "Quiet")
    private String quiet;
    @Place("Button Variants")
    @Heading(value = "@Button(variant = ElementVariant.WARNING)", level = 4)
    private String headingsVariants5;
    @Place("Button Variants")
    @Attribute(className = "button-item")
    @Button(variant = ElementVariant.WARNING, text = "Warning")
    private String warning;
    @Place("Button Variants")
    @Heading(value = "@Button(disabled = true)", level = 4)
    private String headingsVariants6;
    @Place("Button Variants")
    @Button(disabled = true, text = "Disabled")
    private String disabled;

    @Place("Button size")
    @Heading(value = "@Button (size = Size.LARGE)", level = 4)
    private String headingSize1;
    @Place("Button size")
    @Attribute(className = "buttonSize")
    @Button(size = Size.LARGE, text = "Large")
    private String large;
    @Place("Button size")
    @Heading(value = "@Button (size = Size.SMALL)", level = 4)
    private String headingSize2;
    @Place("Button size")
    @Attribute(className = "buttonSize")
    @Button(size = Size.SMALL, text = "Small")
    private String small;
    @Place("Button size")
    @Heading(value = "@Button (block = true)", level = 4)
    private String headingSize3;
    @Place("Button size")
    @Attribute(className = "buttonSize")
    @Button(block = true, text = "Block")
    private String block;

    //    @Button(iconSize = Size.LARGE,text = "Large")
//    private String button13;
//    @Button(iconSize = Size.SMALL,text = "Small")
//    private String button14;
    @Place("Button Types")
    @Heading(value = "@Button (type = ButtonType.BUTTON)", level = 4)
    private String headingType1;
    @Place("Button Types")
    @Button(type = ButtonType.BUTTON, text = "Button")
    private String button;
    @Place("Button Types")
    @Heading(value = "@Button (type = ButtonType.RESET)", level = 4)
    private String headingType2;
    @Place("Button Types")
    @Attribute(className = "button-item")
    @Button(type = ButtonType.RESET, text = "Reset")
    private String reset;
    @Place("Button Types")
    @TextField(emptyText = "Text bellow can be borrowed by Reset button")
    private String resetField;
    @Place("Button Types")
    @Heading(value = "@Button (type = ButtonType.SUBMIT)", level = 4)
    private String headingType3;
    @Place("Button Types")
    @Attribute(className = "button-item")
    @Button(type = ButtonType.SUBMIT, text = "Submit")
    private String submit;

    //textComment?

    //    @Button(icon = "Icon",text = "Text")
//    private String button15;
    @Place("Initial state types")
    @Heading(value = "@Button(active=false)", level = 4)
    private String headingsTypes1;
    @Place("Initial state types")
    @Button(active = false, text = "Disabled")
    private String activeFalse;
    @Place("Initial state types")
    @Heading(value = "@Button(active=true)", level = 4)
    private String headingsTypes2;
    @Place("Initial state types")
    @Button(active = true, text = "Enabled")
    private String activeTrue;

    @Place("IDK")
    @Heading(value = "IDK", level = 4)
    private String idk;
    //TODO
    @Place("IDK")
    @Button(trackingElement = "element", text = "Track Element")
    private String button22;
    @Place("IDK")
    @Button(trackingFeature = "feature", text = "Track Feature")
    private String button23;
    @Place("IDK")
    @Button(actionConfigName = "copy", text = "Action Config Name")
    private String button16;
    @Place("IDK")
    @Button(formId = "1234", text = "data")
    private String button21;


}
