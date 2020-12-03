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

package com.exadel.aem.toolkit.core.handlers.container.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.jcr.resource.api.JcrResourceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import com.google.common.collect.ImmutableMap;

import com.exadel.aem.toolkit.api.annotations.container.AccordionPanel;
import com.exadel.aem.toolkit.api.annotations.container.IgnoreTabs;
import com.exadel.aem.toolkit.api.annotations.container.PlaceOn;
import com.exadel.aem.toolkit.api.annotations.container.PlaceOnTab;
import com.exadel.aem.toolkit.api.annotations.container.Tab;
import com.exadel.aem.toolkit.api.annotations.main.Dialog;
import com.exadel.aem.toolkit.api.annotations.main.JcrConstants;
import com.exadel.aem.toolkit.api.annotations.meta.ResourceTypes;
import com.exadel.aem.toolkit.api.annotations.widgets.attribute.Attribute;
import com.exadel.aem.toolkit.core.exceptions.InvalidTabException;
import com.exadel.aem.toolkit.core.handlers.Handler;
import com.exadel.aem.toolkit.core.maven.PluginRuntime;
import com.exadel.aem.toolkit.core.util.DialogConstants;
import com.exadel.aem.toolkit.core.util.PluginObjectPredicates;
import com.exadel.aem.toolkit.core.util.PluginObjectUtility;
import com.exadel.aem.toolkit.core.util.PluginReflectionUtility;
import com.exadel.aem.toolkit.core.util.PluginXmlContainerUtility;


public abstract class ContainerHandler implements Handler, BiConsumer<Class<?>, Element> {
    private static final Logger LOG = LoggerFactory.getLogger(ContainerHandler.class);

    public static final String ACCORDION = "accordion";
    private static final String TABS = "tabs";
    public static final String TAB = "tab";
    private static final String TITLE = "title";
    public static final String TABS_EXCEPTION = "No tabs defined for the dialog at ";
    public static final String ACCORDION_EXCEPTON = "No accordions defined for the dialog at ";

    /**
     * Appends tab attributes to a pre-built tab-defining XML element
     * @param tabCollectionElement {@link Element} instance representing a TouchUI dialog tab
     * @param tab                  {@link Tab} annotation that contains settings
     * @param defaultTabName       {@link String} name of current container tab
     */
    private static void appendTab(Element tabCollectionElement, ContainerInfo tab, List<Field> fields, String defaultTabName) {
        String nodeName = PluginRuntime.context().getXmlUtility().getUniqueName(tab.getTitle(), defaultTabName, tabCollectionElement);
        Element tabElement = PluginRuntime.context().getXmlUtility().createNodeElement(
            nodeName,
            ImmutableMap.of(
                JcrConstants.PN_TITLE, tab.getTitle(),
                JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY, ResourceTypes.CONTAINER
            ));
        if (defaultTabName.equals(TAB)) {
            Tab newTab = PluginObjectUtility.create(Tab.class,
                tab.getAttributes());
            appendTabAttributes(tabElement, newTab);
        } else if (defaultTabName.equals(ACCORDION)) {
            AccordionPanel accordionPanel = PluginObjectUtility.create(AccordionPanel.class,
                tab.getAttributes());
            Element parentConfig = PluginRuntime.context().getXmlUtility().createNodeElement(
                "parentConfig");
            List<String> skippedList = new ArrayList<>();
            skippedList.add(TITLE);
            PluginRuntime.context().getXmlUtility().mapProperties(parentConfig, accordionPanel, skippedList);
            tabElement.appendChild(parentConfig);
        }
        tabCollectionElement.appendChild(tabElement);
        PluginXmlContainerUtility.append(tabElement, fields);
    }
    /**
     * Render XML markup for all existing tabs
     * @param allTabInstances {@code Map<String, ContainerInfo >} Map where we store names of tabs as keys and all its fields as values
     * @param allFields       {@code List<Field>} All *non-nested* fields from superclasses and the current class
     * @param ignoredTabs     {@link String[]} Array of ignored tabs for the current class
     * @param tabItemsElement {@link Element} instance representing a TouchUI dialog tab
     * @param defaultTabName  {@link String} name of current container tab
     */
    public static void addTabs(Map<String, ContainerInfo> allTabInstances, List<Field> allFields, String[] ignoredTabs, Element tabItemsElement, String defaultTabName) {
        // Iterate tab registry, from the first ever defined tab to the last
        // Within the iteration loop, we
        // 1) add fields from the "all fields" collection that are applicable to the current tab, to the tab's field collection
        // 2) re-sort the current tab's fields collection with the field ranking comparator
        // 3) remove managed fields from the "all fields" collection
        // 4) render XML markup for the current tab
        Iterator<Map.Entry<String, ContainerInfo>> tabInstanceIterator = allTabInstances.entrySet().iterator();
        int iterationStep = 0;
        while (tabInstanceIterator.hasNext()) {
            final boolean isFirstTab = iterationStep++ == 0;
            ContainerInfo currentTabInstance
                = tabInstanceIterator.next().getValue();
            List<Field> storedCurrentTabFields = new ArrayList<>();
            for (String key : currentTabInstance.getFields().keySet()) {
                storedCurrentTabFields.add((Field) currentTabInstance.getFields().get(key));
            }
            List<Field> moreCurrentTabFields = allFields.stream()
                .filter(field -> isFieldForTab(field, currentTabInstance.getTitle(), isFirstTab))
                .collect(Collectors.toList());
            boolean needResort = !storedCurrentTabFields.isEmpty() && !moreCurrentTabFields.isEmpty();
            storedCurrentTabFields.addAll(moreCurrentTabFields);
            if (needResort) {
                storedCurrentTabFields.sort(PluginObjectPredicates::compareByRanking);
            }
            allFields.removeAll(moreCurrentTabFields);
            if (ArrayUtils.contains(ignoredTabs, currentTabInstance.getTitle())) {
                continue;
            }
            appendTab(tabItemsElement, currentTabInstance, storedCurrentTabFields, defaultTabName);
        }
    }

    /**
     * Appends tab attributes to a pre-built tab-defining XML element
     * @param tabElement {@link Element} instance representing a TouchUI dialog tab
     * @param tab        {@link Tab} annotation that contains settings
     */
    private static void appendTabAttributes(Element tabElement, Tab tab) {
        tabElement.setAttribute(JcrConstants.PN_TITLE, tab.title());
        Attribute attribute = tab.attribute();
        PluginRuntime.context().getXmlUtility().mapProperties(tabElement, attribute);
        PluginRuntime.context().getXmlUtility().appendDataAttributes(tabElement, attribute.data());
    }
    protected void acceptParent(Class<?> componentClass, Element parentElement, Class<? extends Annotation> annotation) {
        String containerName = annotation.equals(Tab.class) ? TABS : ACCORDION;
        String defaultTabName = annotation.equals(Tab.class) ? TAB : ACCORDION;
        String exceptionMessage = annotation.equals(Tab.class) ? TABS_EXCEPTION : ACCORDION_EXCEPTON;
        String resourceType = annotation.equals(Tab.class) ? ResourceTypes.TABS : ResourceTypes.ACCORDION;

        Element tabItemsElement = (Element) parentElement.appendChild(getXmlUtil().createNodeElement(DialogConstants.NN_CONTENT, ResourceTypes.CONTAINER))
            .appendChild(getXmlUtil().createNodeElement(DialogConstants.NN_ITEMS))
            .appendChild(getXmlUtil().createNodeElement(containerName, resourceType))
            .appendChild(getXmlUtil().createNodeElement(DialogConstants.NN_ITEMS));

        // Initialize ignored tabs list for the current class if IgnoreTabs annotation is present.
        // Note that "ignored tabs" setting is not inherited and is for current class only, unlike tabs collection
        String[] ignoredTabs = componentClass.isAnnotationPresent(IgnoreTabs.class)
            ? componentClass.getAnnotation(IgnoreTabs.class).value()
            : new String[]{};


        // Retrieve superclasses of the current class, from top of the hierarchy to the most immediate ancestor,
        // populate tab registry and store fields that are within @Tab-marked nested classes
        // (because we will not have access to them later)
        Map<String, ContainerInfo> tabInstancesFromSuperClasses = getTabInstances(PluginReflectionUtility.getClassHierarchy(componentClass, false), annotation, containerName);

        // Retrieve tabs of the current class same way
        Map<String, ContainerInfo> tabInstancesFromCurrentClass = getTabInstances(Collections.singletonList(componentClass), annotation, containerName);

        // Compose the "overall" registry of tabs.
        Map<String, ContainerInfo> allTabInstances = getAllTabInstances(tabInstancesFromCurrentClass, tabInstancesFromSuperClasses);

        // Get all *non-nested* fields from superclasses and the current class
        List<Field> allFields = PluginReflectionUtility.getAllFields(componentClass);

        // If tabs collection is empty and yet there are fields to be placed, fire an exception and create a default tab
        if (allTabInstances.isEmpty() && !allFields.isEmpty()) {
            PluginRuntime.context().getExceptionHandler().handle(new InvalidTabException(
                exceptionMessage + componentClass.getSimpleName()
            ));
            if (containerName.equals(TABS)) {
                allTabInstances.put(StringUtils.EMPTY, new ContainerInfo("newTab"));
            }
        }

        // Render XML markup for all existing tabs
        addTabs(allTabInstances, allFields, ignoredTabs, tabItemsElement, defaultTabName);

        // Afterwards there still can be "orphaned" fields in the "all fields" collection. They are probably fields
        // for which a non-existent tab was specified. Handle an InvalidTabException for each of them
        handleInvalidTabException(allFields);
    }

    /**
     * The predicate to match a {@code Field} against particular {@code Tab}
     * @param field        {@link Field} instance to analyze
     * @param tabTitle     String annotation to analyze
     * @param isDefaultTab True if the current tab accepts fields for which no tab was specified; otherwise, false
     * @return True or false
     */
    private static boolean isFieldForTab(Field field, String tabTitle, boolean isDefaultTab) {
        if (!field.isAnnotationPresent(PlaceOnTab.class) && !field.isAnnotationPresent(PlaceOn.class)) {
            return isDefaultTab;
        }
        if (field.isAnnotationPresent(PlaceOn.class)) {
            return tabTitle.equalsIgnoreCase(field.getAnnotation(PlaceOn.class).value());
        }
        return tabTitle.equalsIgnoreCase(field.getAnnotation(PlaceOnTab.class).value());
    }

    /**
     * Handle an InvalidTabException for fields for which a non-existent tab was specified
     * @param allFields {@code List<Field>} all stored dialog fields
     */
    public static void handleInvalidTabException(List<Field> allFields) {
        for (Field field : allFields) {
            if (field.isAnnotationPresent(PlaceOnTab.class)) {
                PluginRuntime.context().getExceptionHandler().handle(new InvalidTabException(field.getAnnotation(PlaceOnTab.class).value()));
            } else if (field.isAnnotationPresent(PlaceOn.class)) {
                PluginRuntime.context().getExceptionHandler().handle(new InvalidTabException(field.getAnnotation(PlaceOn.class).value()));
            } else {
                PluginRuntime.context().getExceptionHandler().handle(new InvalidTabException(StringUtils.EMPTY));
            }
        }
    }
    /**
     * Retrieves a collection of tabs derived from the specified hierarchical collection of classes. Calls to this
     * method are used to compile a "tab registry" consisting of all tabs from the current class and/or its superclasses
     * @param classes       The {@code Class<?>}-es to search for defined tabs
     * @param annotation    The annotation are searching for
     * @param containerName The name of current container
     * @return Map of entries, each specified by a tab title and containing a {@link ContainerInfo} aggregate object
     */
    private Map<String, ContainerInfo> getTabInstances(List<Class<?>> classes, Class<? extends Annotation> annotation, String containerName) {
        Map<String, ContainerInfo> result = new LinkedHashMap<>();
        Map<String, Object> annotationMap;
        try {
            for (Class<?> cls : classes) {
                List<Class<?>> tabClasses = Arrays.stream(cls.getDeclaredClasses())
                    .filter(nestedCls -> nestedCls.isAnnotationPresent(annotation))
                    .collect(Collectors.toList());
                Collections.reverse(tabClasses);
                for (Class<?> tabClass : tabClasses) {
                    Annotation annotation2 = tabClass.getDeclaredAnnotation(annotation);
                    annotationMap = PluginObjectUtility.getAnnotationFields(annotation2);
                    ContainerInfo containerInfo = new ContainerInfo(annotationMap.get(TITLE).toString());
                    containerInfo.setAttributes(annotationMap);
                    Arrays.stream(tabClass.getDeclaredFields()).forEach(field -> containerInfo.setFields(field.getName(), field));
                    result.put(annotationMap.get(TITLE).toString(), containerInfo);
                }
                if (cls.isAnnotationPresent(Dialog.class)) {
                    if (containerName.equals(TABS)) {
                        Arrays.stream(cls.getAnnotation(Dialog.class).tabs())
                            .forEach(tab -> {
                                ContainerInfo containerInfo = new ContainerInfo(tab.title());
                                try {
                                    containerInfo.setAttributes(PluginObjectUtility.getAnnotationFields(tab));
                                    result.put(tab.title(), containerInfo);
                                } catch (IllegalAccessException | NoSuchFieldException exception) {
                                    LOG.error(exception.getMessage());
                                }

                            });
                    } else {
                        Arrays.stream(cls.getAnnotation(Dialog.class).panels())
                            .forEach(tab -> {
                                ContainerInfo containerInfo = new ContainerInfo(tab.title());
                                try {
                                    containerInfo.setAttributes(PluginObjectUtility.getAnnotationFields(tab));
                                    result.put(tab.title(), containerInfo);
                                } catch (IllegalAccessException | NoSuchFieldException exception) {
                                    LOG.error(exception.getMessage());
                                }
                            });
                    }
                }
            }
        } catch (IllegalAccessException | NoSuchFieldException exception) {
            LOG.error(exception.getMessage());
        }
        return result;
    }
    /**
     * Compose the "overall" registry of tabs
     * @param tabInstancesFromCurrentClass {@code Map<String, ContainerInfo >} Map where names of tabs
     *                                     from current class are stored as keys and all its fields as values
     * @param tabInstancesFromSuperClasses {@code Map<String, ContainerInfo >} Map where names of tabs
     *                                     from super classes are stored as keys and all their fields as values
     * @return {@code Map<String,TabContainerInstance>} map containing all tabs
     */
    private Map<String, ContainerInfo> getAllTabInstances(Map<String, ContainerInfo> tabInstancesFromCurrentClass, Map<String, ContainerInfo> tabInstancesFromSuperClasses) {
        // Compose the "overall" registry of tabs.
        // Whether the current class has any tabs that match tabs from superclasses,we consider that the "right" order
        // of tabs is defined herewith, and place tabs from the current class first, then rest of the tabs.
        // Otherwise, we consider the tabs of the current class to be an "addendum" of tabs from superclasses, and put
        // them in the end
        if (tabInstancesFromCurrentClass.keySet().stream().anyMatch(tabInstancesFromSuperClasses::containsKey)) {
            return Stream.concat(tabInstancesFromCurrentClass.entrySet().stream(), tabInstancesFromSuperClasses.entrySet().stream())
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (child, parent) -> parent.merge(child),
                    LinkedHashMap::new));
        } else {
            return Stream.concat(tabInstancesFromSuperClasses.entrySet().stream(), tabInstancesFromCurrentClass.entrySet().stream())
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    ContainerInfo::merge,
                    LinkedHashMap::new));
        }
    }
}
