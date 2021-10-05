package com.exadel.aem.toolkit.samples.models;

import com.exadel.aem.toolkit.api.annotations.layouts.Tab;
import com.exadel.aem.toolkit.api.annotations.layouts.Tabs;
import com.exadel.aem.toolkit.api.annotations.main.AemComponent;
import com.exadel.aem.toolkit.api.annotations.main.Dialog;
import com.exadel.aem.toolkit.samples.constants.GroupConstants;
import com.exadel.aem.toolkit.samples.constants.PathConstants;

import java.util.List;

@AemComponent(
    path = "content/dependson-functions-component",
    title = "DependsOn Number And Functions Component",
    description = "DependsOn",
    resourceSuperType = PathConstants.FOUNDATION_PARBASE_PATH,
    componentGroup = GroupConstants.COMPONENT_GROUP
)
@Dialog
@Tabs(
    value = {
        @Tab(title = "Async"),
        @Tab(title = "Fetch"),
    }
)
public class DependsOnFunctionsComponent {


//    @Place("Functions")
//    @DialogField
//    @DependsOn(query = "ProjectJSUtils.checkSomething(@refField1, @refField2)")
//    @TextField
//    private String field4;
//
//    @Place("Functions")
//    @DialogField
//    @DependsOn(query = "Math.max(@refField1, @refField2) < 10")
//    @TextField
//    private String field5;

}
