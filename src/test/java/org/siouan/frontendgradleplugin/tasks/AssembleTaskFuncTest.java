package org.siouan.frontendgradleplugin.tasks;

import static org.siouan.frontendgradleplugin.util.Helper.assertTaskIgnored;
import static org.siouan.frontendgradleplugin.util.Helper.assertTaskOutcome;
import static org.siouan.frontendgradleplugin.util.Helper.runGradle;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.TaskOutcome;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.siouan.frontendgradleplugin.FrontendGradlePlugin;
import org.siouan.frontendgradleplugin.util.Helper;

/**
 * Functional tests to verify the {@link AssembleTask} integration in a Gradle build. Test cases uses fake Node/Yarn
 * distributions, to avoid the download overhead. The 'yarn' and 'npm' executables in these distributions simply call
 * the 'node' executable with the same arguments.
 */
class AssembleTaskFuncTest {

    @TempDir
    File tmpDirectory;

    private Path projectDirectory;

    @BeforeEach
    void setUp() {
        projectDirectory = tmpDirectory.toPath();
    }

    @Test
    void shouldDoNothingWhenScriptUndefined() throws IOException, URISyntaxException {
        Files.copy(new File(getClass().getClassLoader().getResource("package-npm.json").toURI()).toPath(),
            projectDirectory.resolve("package.json"));
        final Map<String, Object> properties = new HashMap<>();
        properties.put("nodeVersion", "10.16.0");
        properties.put("nodeDistributionUrl", getClass().getClassLoader().getResource("node-v10.16.0.zip").toString());
        Helper.createBuildFile(projectDirectory, properties);

        final BuildResult result1 = runGradle(projectDirectory, FrontendGradlePlugin.ASSEMBLE_TASK_NAME);

        assertTaskIgnored(result1, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskIgnored(result1, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskIgnored(result1, FrontendGradlePlugin.INSTALL_TASK_NAME);
        assertTaskOutcome(result1, FrontendGradlePlugin.ASSEMBLE_TASK_NAME, TaskOutcome.SUCCESS);
    }

    @Test
    void shouldAssembleWithoutFrontendTasks() throws IOException, URISyntaxException {
        Files.copy(new File(getClass().getClassLoader().getResource("package-npm.json").toURI()).toPath(),
            projectDirectory.resolve("package.json"));
        final Map<String, Object> properties = new HashMap<>();
        properties.put("nodeVersion", "10.16.0");
        properties.put("nodeDistributionUrl", getClass().getClassLoader().getResource("node-v10.16.0.zip").toString());
        Helper.createBuildFile(projectDirectory, properties);

        final BuildResult result1 = runGradle(projectDirectory, FrontendGradlePlugin.GRADLE_ASSEMBLE_TASK_NAME);

        assertTaskIgnored(result1, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME);
        assertTaskIgnored(result1, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskIgnored(result1, FrontendGradlePlugin.INSTALL_TASK_NAME);
        assertTaskIgnored(result1, FrontendGradlePlugin.ASSEMBLE_TASK_NAME);
        assertTaskOutcome(result1, FrontendGradlePlugin.GRADLE_ASSEMBLE_TASK_NAME, TaskOutcome.UP_TO_DATE);
    }

    @Test
    void shouldAssembleFrontendWithNpmOrYarn() throws IOException, URISyntaxException {
        Files.copy(new File(getClass().getClassLoader().getResource("package-npm.json").toURI()).toPath(),
            projectDirectory.resolve("package.json"));
        final Map<String, Object> properties = new HashMap<>();
        properties.put("nodeVersion", "10.16.0");
        properties.put("nodeDistributionUrl", getClass().getClassLoader().getResource("node-v10.16.0.zip").toString());
        properties.put("assembleScript", "run assemble");
        Helper.createBuildFile(projectDirectory, properties);

        final BuildResult result1 = runGradle(projectDirectory, FrontendGradlePlugin.GRADLE_ASSEMBLE_TASK_NAME);

        assertTaskOutcome(result1, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, TaskOutcome.SUCCESS);
        assertTaskIgnored(result1, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskOutcome(result1, FrontendGradlePlugin.INSTALL_TASK_NAME, TaskOutcome.SUCCESS);
        assertTaskOutcome(result1, FrontendGradlePlugin.GRADLE_ASSEMBLE_TASK_NAME, TaskOutcome.SUCCESS);

        final BuildResult result2 = runGradle(projectDirectory, FrontendGradlePlugin.GRADLE_ASSEMBLE_TASK_NAME);

        assertTaskOutcome(result2, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, TaskOutcome.UP_TO_DATE);
        assertTaskIgnored(result2, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME);
        assertTaskOutcome(result2, FrontendGradlePlugin.INSTALL_TASK_NAME, TaskOutcome.SUCCESS);
        assertTaskOutcome(result2, FrontendGradlePlugin.GRADLE_ASSEMBLE_TASK_NAME, TaskOutcome.SUCCESS);

        Files.deleteIfExists(projectDirectory.resolve("package-lock.json"));
        Files.copy(new File(getClass().getClassLoader().getResource("package-yarn.json").toURI()).toPath(),
            projectDirectory.resolve("package.json"), StandardCopyOption.REPLACE_EXISTING);
        properties.put("yarnEnabled", true);
        properties.put("yarnVersion", "1.16.0");
        properties
            .put("yarnDistributionUrl", getClass().getClassLoader().getResource("yarn-v1.16.0.tar.gz").toString());
        Helper.createBuildFile(projectDirectory, properties);

        final BuildResult result3 = runGradle(projectDirectory, FrontendGradlePlugin.GRADLE_ASSEMBLE_TASK_NAME);

        assertTaskOutcome(result3, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, TaskOutcome.UP_TO_DATE);
        assertTaskOutcome(result3, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME, TaskOutcome.SUCCESS);
        assertTaskOutcome(result3, FrontendGradlePlugin.INSTALL_TASK_NAME, TaskOutcome.SUCCESS);
        assertTaskOutcome(result3, FrontendGradlePlugin.GRADLE_ASSEMBLE_TASK_NAME, TaskOutcome.SUCCESS);

        final BuildResult result4 = runGradle(projectDirectory, FrontendGradlePlugin.GRADLE_ASSEMBLE_TASK_NAME);

        assertTaskOutcome(result4, FrontendGradlePlugin.NODE_INSTALL_TASK_NAME, TaskOutcome.UP_TO_DATE);
        assertTaskOutcome(result4, FrontendGradlePlugin.YARN_INSTALL_TASK_NAME, TaskOutcome.UP_TO_DATE);
        assertTaskOutcome(result4, FrontendGradlePlugin.INSTALL_TASK_NAME, TaskOutcome.SUCCESS);
        assertTaskOutcome(result4, FrontendGradlePlugin.GRADLE_ASSEMBLE_TASK_NAME, TaskOutcome.SUCCESS);
    }
}
