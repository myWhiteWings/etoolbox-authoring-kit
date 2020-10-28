/*
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.exadel.aem.toolkit.core.util.writer;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.transform.Transformer;

import com.exadel.aem.toolkit.api.annotations.meta.PropertyScope;
import com.exadel.aem.toolkit.api.handlers.TargetFacade;
import com.exadel.aem.toolkit.core.util.DialogConstants;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;

import com.exadel.aem.toolkit.api.annotations.main.Dialog;
import com.exadel.aem.toolkit.api.annotations.main.DialogLayout;
import com.exadel.aem.toolkit.api.annotations.meta.DialogAnnotation;
import com.exadel.aem.toolkit.api.annotations.meta.ResourceTypes;
import com.exadel.aem.toolkit.api.annotations.widgets.common.XmlScope;
import com.exadel.aem.toolkit.api.handlers.DialogHandler;
import com.exadel.aem.toolkit.core.handlers.assets.dependson.DependsOnTabHandler;
import com.exadel.aem.toolkit.core.handlers.container.DialogContainer;
import com.exadel.aem.toolkit.core.maven.PluginRuntime;

/**
 * The {@link PackageEntryWriter} implementation for storing AEM TouchUI dialog definition (writes data to the
 * {@code _cq_dialog.xml} file within the current component folder before package is uploaded
 */
class CqDialogWriter extends ContentXmlWriter {
    /**
     * Basic constructor
     *
     * @param transformer {@code Transformer} instance used to serialize XML DOM document to an output stream
     */
    CqDialogWriter(Transformer transformer) {
        super(transformer);
    }

    /**
     * Gets {@code XmlScope} value of current {@code PackageEntryWriter} implementation
     *
     * @return {@link XmlScope} value
     */
    @Override
    XmlScope getXmlScope() {
        return XmlScope.CQ_DIALOG;
    }

    /**
     * Overrides {@link PackageEntryWriter#populateDomDocument(Class, TargetFacade)} abstract method to write down contents
     * of {@code _cq_dialog.xml} file. To the targetFacade node, several XML building routines are applied in sequence: the predefined
     * dialog container builder, the common properties writer, {@code DependsOn} handlers and any {@code CustomHandler}s defined for
     * this component class
     *
     * @param componentClass The {@code Class} being processed
     * @param targetFacade   The targetFacade element of DOM {@link Document} to feed data to
     */
    @Override
    void populateDomDocument(Class<?> componentClass, TargetFacade targetFacade) {
        Dialog dialog = componentClass.getDeclaredAnnotation(Dialog.class);
        targetFacade.mapProperties(dialog, Arrays.stream(Dialog.class.getDeclaredMethods())
                .filter(m -> !fitsInScope(m, getXmlScope())).map(Method::getName).collect(Collectors.toList()));
        targetFacade.setAttribute(DialogConstants.PN_SLING_RESOURCE_TYPE, ResourceTypes.DIALOG);

        DialogLayout dialogLayout = ArrayUtils.isEmpty(dialog.tabs()) ? dialog.layout() : DialogLayout.TABS;
        DialogContainer.getContainer(dialogLayout).build(componentClass, targetFacade);

        new DependsOnTabHandler().accept(targetFacade, componentClass);
        if (!classHasCustomDialogAnnotation(componentClass)) {
            return;
        }
        List<DialogAnnotation> customAnnotations = getCustomDialogAnnotations(componentClass);
        PluginRuntime.context().getReflectionUtility().getCustomDialogHandlers().stream()
                .filter(handler -> customAnnotations.stream()
                        .anyMatch(annotation -> customAnnotationMatchesHandler(annotation, handler)))
                .forEach(handler -> handler.accept(targetFacade, componentClass));
    }

    /**
     * Retrieves list of {@link DialogAnnotation} instances defined for the current {@code Class}
     *
     * @param componentClass The {@code Class} being processed
     * @return List of values, empty or non-empty
     */
    private static List<DialogAnnotation> getCustomDialogAnnotations(Class<?> componentClass) {
        return Arrays.stream(componentClass.getDeclaredAnnotations())
                .filter(annotation -> annotation.annotationType().getDeclaredAnnotation(DialogAnnotation.class) != null)
                .map(annotation -> annotation.annotationType().getDeclaredAnnotation(DialogAnnotation.class))
                .collect(Collectors.toList());
    }

    /**
     * Gets whether current {@code Class} has a custom dialog annotation attached
     *
     * @param componentClass The {@code Class} being processed
     * @return True or false
     */
    private static boolean classHasCustomDialogAnnotation(Class<?> componentClass) {
        return Arrays.stream(componentClass.getDeclaredAnnotations())
                .anyMatch(a -> a.annotationType().getDeclaredAnnotation(DialogAnnotation.class) != null);
    }

    /**
     * Used while enumerating available {@code CustomDialogHandler}s to set matching between a handler and a {@code CustomDialogAnnotation},
     * since one handler may serve for several annotations, and, optionally, vice versa
     *
     * @param annotation {@link DialogAnnotation} instance
     * @param handler    {@link DialogHandler} instance
     * @return True if the two arguments are "matching" via their properties, otherwise, false
     */
    private static boolean customAnnotationMatchesHandler(DialogAnnotation annotation, DialogHandler handler) {
        return StringUtils.equals(annotation.source(), handler.getName());
    }

    private static boolean fitsInScope(Method method, XmlScope scope) {
        if (!method.isAnnotationPresent(PropertyScope.class)) {
            return true;
        }
        return Arrays.asList(method.getAnnotation(PropertyScope.class).value()).contains(scope);
    }
}
