package com.exadel.aem.toolkit.samples.models;

import com.exadel.aem.toolkit.api.annotations.layouts.Accordion;
import com.exadel.aem.toolkit.api.annotations.layouts.AccordionPanel;
import com.exadel.aem.toolkit.api.annotations.main.AemComponent;
import com.exadel.aem.toolkit.api.annotations.main.Dialog;
import com.exadel.aem.toolkit.api.annotations.widgets.DialogField;
import com.exadel.aem.toolkit.api.annotations.widgets.Heading;
import com.exadel.aem.toolkit.api.annotations.widgets.radio.RadioButton;
import com.exadel.aem.toolkit.api.annotations.widgets.radio.RadioGroup;
import com.exadel.aem.toolkit.api.annotations.widgets.select.Option;
import com.exadel.aem.toolkit.api.annotations.widgets.select.Select;
import com.exadel.aem.toolkit.samples.constants.GroupConstants;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@AemComponent(
    path = "content/dropdown-component",
    title = "Dropdown Component",
    description = "Dropdown component",
    resourceSuperType = "etoolbox-authoring-kit/samples/components/content/parent-select-component",
    componentGroup = GroupConstants.COMPONENT_GROUP
)
@Dialog
@Accordion(value = @AccordionPanel(title = "@Accordion(value = @AccordionPanel()"))
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class DropdownComponent {
    //    @Default(values = "1")
    //@DialogField(label = "Dropdown select")
    @Heading(value = "@Select(options = {@Option(),@Option()}", level = 4)
    String heading1;
    @DialogField(label = "Dropdown select")
    @Select(options = {
        @Option(text = "Variant 1", value = "1"),
        @Option(text = "Variant 2", value = "2")
    })
    @ValueMapValue
    String variantsSelect;
    @Heading(value = "@RadioGroup(buttons = {@RadioButton(),@RadioButton()}", level = 4)
    String heading2;
    @DialogField(label = "Radio Buttons")
    @RadioGroup(buttons = {@RadioButton(text = "one", value = "1"), @RadioButton(text = "two", value = "2")}, vertical = false)
    @ValueMapValue
    String radioButton;

    public String getVariantsSelect() {
        return variantsSelect;
    }
    public String getRadioButton() {
        return radioButton;
    }
}

