package com.exadel.aem.toolkit.samples.models;

import com.exadel.aem.toolkit.api.annotations.layouts.Place;
import com.exadel.aem.toolkit.api.annotations.layouts.Tab;
import com.exadel.aem.toolkit.api.annotations.layouts.Tabs;
import com.exadel.aem.toolkit.api.annotations.main.AemComponent;
import com.exadel.aem.toolkit.api.annotations.main.Dialog;
import com.exadel.aem.toolkit.api.annotations.widgets.DialogField;
import com.exadel.aem.toolkit.api.annotations.widgets.Heading;
import com.exadel.aem.toolkit.api.annotations.widgets.NumberField;
import com.exadel.aem.toolkit.api.annotations.widgets.common.TypeHint;
import com.exadel.aem.toolkit.api.annotations.widgets.datepicker.DatePicker;
import com.exadel.aem.toolkit.api.annotations.widgets.datepicker.DatePickerType;
import com.exadel.aem.toolkit.api.annotations.widgets.datepicker.DateTimeValue;
import com.exadel.aem.toolkit.samples.constants.GroupConstants;
import com.exadel.aem.toolkit.samples.constants.PathConstants;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@AemComponent(
    path = "content/numeric-component",
    title = "Numeric Component",
    description = "Numeric",
    resourceSuperType = PathConstants.FOUNDATION_PARBASE_PATH,
    componentGroup = GroupConstants.COMPONENT_GROUP
)
@Dialog
@Tabs(
    value = {
        @Tab(title = "Numeric Field"),
        @Tab(title = "Date Picker")
    }
)
@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class NumericComponent {
    @Place("Numeric Field")
    @Heading(value = "@NumberField(min = 0, max = 100, value =\"10\")")
    private String heading1;

    @Place("Numeric Field")
    @DialogField(
        label = "Numeric field",
        name = "num1"
    )
    @NumberField(min = 0, max = 100, value = "10")
    @ValueMapValue
    private int num1;

    @Place("Numeric Field")
    @Heading(value = "@NumberField(min = 0, max = 100, value = \"4\",step = 2")
    private String heading2;

    @Place("Numeric Field")
    @DialogField(
        label = "Numeric field",
        name = "num2"
    )
    @NumberField(min = 0, max = 100, value = "4", step = 2)
    @ValueMapValue
    private int num2;

    @Place("Date Picker")
    @Heading(value = "@DatePicker(type = DatePickerType.DATE)")
    String heading3;

    @Place("Date Picker")
    @DialogField(
        label = "Date Picker"
    )
    @DatePicker(
        type = DatePickerType.DATE,
        displayedFormat = "DD.MM.YYYY",
        valueFormat = "YYYY.MM.DD",
        maxDate = @DateTimeValue(day = 1, month = 12, year = 2025),
        typeHint = TypeHint.STRING
    )
    @ValueMapValue
    String selectDate1;

    @Place("Date Picker")
    @Heading(value = "@DatePicker(type = DatePickerType.TIME)")
    String heading4;

    @Place("Date Picker")
    @DialogField(
        label = "Time Picker"
    )
    @DatePicker(
        type = DatePickerType.TIME,
        displayedFormat = "hh:mm",
        valueFormat = "hh:mm",
        maxDate = @DateTimeValue(hour = 00, minute = 00),
        typeHint = TypeHint.STRING
    )
    @ValueMapValue
    String selectDate2;

    @Place("Date Picker")
    @Heading(value = "@DatePicker(type = DatePickerType.DATETIME)")
    String heading5;

    @Place("Date Picker")
    @DialogField(
        label = "Date Time Picker"
    )
    @DatePicker(
        type = DatePickerType.DATETIME,
        displayedFormat = "DD-MM-YYYY hh:mm",
        valueFormat = "hh:mm DD-MM-YYYY",
        maxDate = @DateTimeValue(hour = 23, minute = 59, day = 1, month = 12, year = 2025),
        typeHint = TypeHint.STRING
    )
    @ValueMapValue
    String selectDate3;

    public int getNum1() {
        return num1;
    }
    public int getNum2() {
        return num2;
    }
    public String getSelectDate1() {
        return StringUtils.defaultIfBlank(selectDate1, "01.01.1999");
    }
    public String getSelectDate2() {
        return selectDate2;
    }
    public String getSelectDate3() {
        return selectDate3;
    }
}
