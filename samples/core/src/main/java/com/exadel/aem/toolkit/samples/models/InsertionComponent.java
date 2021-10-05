package com.exadel.aem.toolkit.samples.models;

import com.exadel.aem.toolkit.api.annotations.editconfig.DropTargetConfig;
import com.exadel.aem.toolkit.api.annotations.editconfig.EditConfig;
import com.exadel.aem.toolkit.api.annotations.layouts.Place;
import com.exadel.aem.toolkit.api.annotations.layouts.Tab;
import com.exadel.aem.toolkit.api.annotations.layouts.Tabs;
import com.exadel.aem.toolkit.api.annotations.main.AemComponent;
import com.exadel.aem.toolkit.api.annotations.main.Dialog;
import com.exadel.aem.toolkit.api.annotations.widgets.DialogField;
import com.exadel.aem.toolkit.api.annotations.widgets.FileUpload;
import com.exadel.aem.toolkit.api.annotations.widgets.Heading;
import com.exadel.aem.toolkit.api.annotations.widgets.common.Size;
import com.exadel.aem.toolkit.api.annotations.widgets.imageupload.ImageUpload;
import com.exadel.aem.toolkit.samples.constants.GroupConstants;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@AemComponent(
    path = "content/insertion-component",
    title = "Insertion Component",
    description = "There are available insertions below",
    componentGroup = GroupConstants.COMPONENT_GROUP
)
@Dialog
@Tabs(value = {
    @Tab(title = "Image Insertion"),
    @Tab(title = "File Insertion")
})
@EditConfig(
    dropTargets = {
        @DropTargetConfig(
            propertyName = "./defaultImage/fileReference",
            nodeName = "insertionComponentImage",
            accept = "image/.*",
            groups = "media"
        )
    }
)
@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class InsertionComponent {
    @Place("Image Insertion")
    @Heading(value = "@ImageUpload", level = 4)
        //@EditConfig(
        //    dropTargets = {
        //        @DropTargetConfig(
        //            propertyName = "./defaultImage/fileReference",
        //            nodeName = "insertionComponentImage",
        //            accept = "image/.*",
        //            groups = "media"
        //        )
        //    }
        //)???
    String heading1;
    @ImageUpload
    @DialogField(
        name = "image/",
        description = "Choose an image for insertion"
    )
    @ValueMapValue(name = "image/fileReference")
    private String image;

    @Place("File Insertion")
    @Heading(value = "@FileUpload()\nUploaded file will be located in /content/etoolbox-authoring-kit/samples/local-storage/uploaded-file", level = 4)
    private String heading2;
    @Place("File Insertion")
    @FileUpload(uploadUrl = "/content/etoolbox-authoring-kit/samples/local-storage/uploaded-file", emptyText = "Download file", size = Size.SMALL)
    @DialogField(
        description = "Choose file for insertion"
    )
    private String file;

    public String getImage() {
        return image;

    }
    public String getFile() {
        if (image != null) {
            return file;
        }
        return StringUtils.EMPTY;
    }
}
