package org.siouan.frontendgradleplugin.tasks;

import static org.siouan.frontendgradleplugin.util.FunctionalTestHelper.assertTaskOutcome;
import static org.siouan.frontendgradleplugin.util.FunctionalTestHelper.runGradle;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.TaskOutcome;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.siouan.frontendgradleplugin.FrontendGradlePlugin;
import org.siouan.frontendgradleplugin.util.FunctionalTestHelper;

/**
 * Functional tests to verify the {@link RunScriptTask} integration in a Gradle build.
 */
class RunScriptTaskFuncTest {

    @TempDir
    protected File projectDirectory;

    @Test
    public void shouldRunScriptFrontendWithNpmOrYarn() throws IOException, URISyntaxException {
        Files.copy(new File(getClass().getClassLoader().getResource("package-npm.json").toURI()).toPath(),
            projectDirectory.toPath().resolve("package.json"));
        final Map<String, Object> properties = new HashMap<>();
        properties.put("nodeVersion", "10.15.3");
        final String customTaskName = "e2e";
        final StringBuilder customTaskDefinition = new StringBuilder("tasks.register('");
        customTaskDefinition.append(customTaskName);
        customTaskDefinition.append("', org.siouan.frontendgradleplugin.tasks.RunScriptTask) {\n");
        customTaskDefinition.append("dependsOn tasks.named('installFrontend')\n");
        customTaskDefinition.append("script = 'run test'\n");
        customTaskDefinition.append("}\n");
        FunctionalTestHelper.createBuildFile(projectDirectory, properties, customTaskDefinition.toString());

        final BuildResult result1 = runGradle(projectDirectory, customTaskName);

        assertTaskOutcome(result1, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, TaskOutcome.SUCCESS);
        assertTaskOutcome(result1, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME, TaskOutcome.SKIPPED);
        assertTaskOutcome(result1, FrontendGradlePlugin.INSTALL_TASK_NAME, TaskOutcome.SUCCESS);
        assertTaskOutcome(result1, customTaskName, TaskOutcome.SUCCESS);

        final BuildResult result2 = runGradle(projectDirectory, customTaskName);

        assertTaskOutcome(result2, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, TaskOutcome.UP_TO_DATE);
        assertTaskOutcome(result2, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME, TaskOutcome.SKIPPED);
        assertTaskOutcome(result2, FrontendGradlePlugin.INSTALL_TASK_NAME, TaskOutcome.SUCCESS);
        assertTaskOutcome(result2, customTaskName, TaskOutcome.SUCCESS);

        Files.deleteIfExists(projectDirectory.toPath().resolve("package-lock.json"));
        Files.copy(new File(getClass().getClassLoader().getResource("package-yarn.json").toURI()).toPath(),
            projectDirectory.toPath().resolve("package.json"), StandardCopyOption.REPLACE_EXISTING);
        properties.put("yarnEnabled", true);
        properties.put("yarnVersion", "1.15.2");
        FunctionalTestHelper.createBuildFile(projectDirectory, properties, customTaskDefinition.toString());

        final BuildResult result3 = runGradle(projectDirectory, customTaskName);

        assertTaskOutcome(result3, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, TaskOutcome.UP_TO_DATE);
        assertTaskOutcome(result3, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME, TaskOutcome.SUCCESS);
        assertTaskOutcome(result3, FrontendGradlePlugin.INSTALL_TASK_NAME, TaskOutcome.SUCCESS);
        assertTaskOutcome(result3, customTaskName, TaskOutcome.SUCCESS);

        final BuildResult result4 = runGradle(projectDirectory, customTaskName);

        assertTaskOutcome(result4, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, TaskOutcome.UP_TO_DATE);
        assertTaskOutcome(result4, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME, TaskOutcome.UP_TO_DATE);
        assertTaskOutcome(result4, FrontendGradlePlugin.INSTALL_TASK_NAME, TaskOutcome.SUCCESS);
        assertTaskOutcome(result4, customTaskName, TaskOutcome.SUCCESS);
    }
}
