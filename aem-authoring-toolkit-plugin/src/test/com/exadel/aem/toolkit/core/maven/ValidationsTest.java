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

package com.exadel.aem.toolkit.core.maven;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.exadel.aem.toolkit.api.annotations.main.Dialog;
import com.exadel.aem.toolkit.api.annotations.widgets.NumberField;
import com.exadel.aem.toolkit.api.annotations.widgets.fileupload.FileUpload;
import com.exadel.aem.toolkit.api.annotations.widgets.imageupload.ImageUpload;
import com.exadel.aem.toolkit.api.annotations.widgets.rte.Characters;
import com.exadel.aem.toolkit.api.annotations.widgets.rte.ParagraphFormat;
import com.exadel.aem.toolkit.api.annotations.widgets.rte.RichTextEditor;
import com.exadel.aem.toolkit.api.annotations.widgets.textarea.TextArea;
import com.exadel.aem.toolkit.core.exceptions.ValidationException;
import com.exadel.aem.toolkit.test.component.ValidationTestCases;
import com.exadel.aem.toolkit.test.custom.CustomAnnotationAutomapping;

import static com.exadel.aem.toolkit.core.util.TestConstants.DEFAULT_COMPONENT_NAME;
import static com.exadel.aem.toolkit.core.util.TestConstants.DEFAULT_COMPONENT_TITLE;

public class ValidationsTest extends ExceptionTestBase {
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testNonBlankValidation() {
        exceptionRule.expectCause(IsInstanceOf.instanceOf(ValidationException.class));
        exceptionRule.expectMessage("'' provided");
        testComponent(ValidationTestCases.InvalidTitleDialog.class);
    }

    @Test
    public void testAllNotBlankValidation() {
        exceptionRule.expectCause(IsInstanceOf.instanceOf(ValidationException.class));
        exceptionRule.expectMessage("string properties must not be blank");
        testComponent(ValidationTestCases.InvalidRteParaformatDialog.class);
    }

    @Test
    public void testNumberValidation() {
        exceptionRule.expectCause(IsInstanceOf.instanceOf(ValidationException.class));
        exceptionRule.expectMessage("'not-a-number' provided");
        testComponent(ValidationTestCases.InvalidNumberFieldDialog.class);
    }

    @Test
    public void testNonNegativeNumberValidation() {
        exceptionRule.expectCause(IsInstanceOf.instanceOf(ValidationException.class));
        exceptionRule.expectMessage("'-99' provided");
        testComponent(ValidationTestCases.InvalidImageUploadDialog.class);
    }

    @Test
    public void testPositiveNumberValidation() {
        exceptionRule.expectCause(IsInstanceOf.instanceOf(ValidationException.class));
        exceptionRule.expectMessage("'0' provided");
        testComponent(ValidationTestCases.InvalidTextAreaDialog.class);
    }


    @Test
    public void testCharactersValidation() {
        exceptionRule.expectCause(IsInstanceOf.instanceOf(ValidationException.class));
        exceptionRule.expectMessage("a character range (start < end) or entity definition must be set");
        testComponent(ValidationTestCases.InvalidRteCharactersDialog.class);
    }

    @Test
    public void testJcrPathValidation() {
        exceptionRule.expectCause(IsInstanceOf.instanceOf(ValidationException.class));
        exceptionRule.expectMessage("'wrong path' provided");
        testComponent(ValidationTestCases.InvalidPathDialog.class);
    }


    @Test
    public void testCustomValidation() {
        exceptionRule.expectCause(IsInstanceOf.instanceOf(ValidationException.class));
        exceptionRule.expectMessage("one of 'red', 'green', or 'blue' must be provided");
        testComponent(ValidationTestCases.InvalidCustomAnnotationDialog.class);
    }
}