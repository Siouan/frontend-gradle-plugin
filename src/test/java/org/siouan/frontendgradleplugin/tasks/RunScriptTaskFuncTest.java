package org.siouan.frontendgradleplugin.tasks;

import static org.siouan.frontendgradleplugin.util.Helper.assertTaskFailed;
import static org.siouan.frontendgradleplugin.util.Helper.assertTaskSkipped;
import static org.siouan.frontendgradleplugin.util.Helper.assertTaskSuccess;
import static org.siouan.frontendgradleplugin.util.Helper.assertTaskUpToDate;
import static org.siouan.frontendgradleplugin.util.Helper.runGradle;
import static org.siouan.frontendgradleplugin.util.Helper.runGradleAndExpectFailure;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

import org.gradle.api.logging.LogLevel;
import org.gradle.testkit.runner.BuildResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.siouan.frontendgradleplugin.FrontendGradlePlugin;
import org.siouan.frontendgradleplugin.util.Helper;

/**
 * Functional tests to verify the {@link RunScriptTask} integration in a Gradle build. This functional test relies on
 * real Node/Yarn distributions.
 */
class RunScriptTaskFuncTest {

    @TempDir
    Path projectDirectory;

    private Path packageJsonDirectory;

    @BeforeEach
    void setUp() throws IOException {
        packageJsonDirectory = Files.createDirectory(projectDirectory.resolve("frontend"));
    }

    @Test
    void shouldFailRunningFrontendScriptWhenScriptIsUndefined() throws IOException, URISyntaxException {
        Files.copy(Paths.get(getClass().getClassLoader().getResource("package-npm.json").toURI()),
            packageJsonDirectory.resolve("package.json"));
        final Map<String, Object> properties = new HashMap<>();
        final String customTaskName = "e2e";
        final String customTaskDefinition =
            "tasks.register('" + customTaskName + "', org.siouan.frontendgradleplugin.tasks.RunScriptTask) {}\n";
        Helper.createBuildFile(projectDirectory, properties, customTaskDefinition);

        final BuildResult result = runGradleAndExpectFailure(projectDirectory, customTaskName);

        assertTaskFailed(result, customTaskName);
    }

    @Test
    void shouldRunScriptFrontendWithNpmOrYarn() throws IOException, URISyntaxException {
        Files.copy(Paths.get(getClass().getClassLoader().getResource("package-npm.json").toURI()),
            packageJsonDirectory.resolve("package.json"));
        final Map<String, Object> properties = new HashMap<>();
        properties.put("packageJsonDirectory", packageJsonDirectory);
        properties.put("loggingLevel", LogLevel.LIFECYCLE);
        properties.put("nodeVersion", "12.16.1");
        properties.put("nodeInstallDirectory", projectDirectory.resolve("node-dist"));
        final String customTaskName = "e2e";
        final StringBuilder customTaskDefinition = new StringBuilder("tasks.register('");
        customTaskDefinition.append(customTaskName);
        customTaskDefinition.append("', org.siouan.frontendgradleplugin.tasks.RunScriptTask) {\n");
        customTaskDefinition.append("dependsOn tasks.named('installFrontend')\n");
        customTaskDefinition.append("script = 'run another-script'\n");
        customTaskDefinition.append("}\n");
        Helper.createBuildFile(projectDirectory, properties, customTaskDefinition.toString());

        final BuildResult result1 = runGradle(projectDirectory, customTaskName);

        assertTaskSuccess(result1, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSkipped(result1, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskSuccess(result1, FrontendGradlePlugin.INSTALL_TASK_NAME);
        assertTaskSuccess(result1, customTaskName);

        final BuildResult result2 = runGradle(projectDirectory, customTaskName);

        assertTaskUpToDate(result2, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSkipped(result2, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskSuccess(result2, FrontendGradlePlugin.INSTALL_TASK_NAME);
        assertTaskSuccess(result2, customTaskName);

        Files.deleteIfExists(projectDirectory.resolve("package-lock.json"));
        Files.copy(Paths.get(getClass().getClassLoader().getResource("package-yarn.json").toURI()),
            packageJsonDirectory.resolve("package.json"), StandardCopyOption.REPLACE_EXISTING);
        properties.put("yarnEnabled", true);
        properties.put("yarnVersion", "1.22.4");
        properties.put("yarnInstallDirectory", projectDirectory.resolve("yarn-dist"));
        Helper.createBuildFile(projectDirectory, properties, customTaskDefinition.toString());

        final BuildResult result3 = runGradle(projectDirectory, customTaskName);

        assertTaskUpToDate(result3, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskSuccess(result3, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskSuccess(result3, FrontendGradlePlugin.INSTALL_TASK_NAME);
        assertTaskSuccess(result3, customTaskName);

        final BuildResult result4 = runGradle(projectDirectory, customTaskName);

        assertTaskUpToDate(result4, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskUpToDate(result4, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskSuccess(result4, FrontendGradlePlugin.INSTALL_TASK_NAME);
        assertTaskSuccess(result4, customTaskName);
    }
}
