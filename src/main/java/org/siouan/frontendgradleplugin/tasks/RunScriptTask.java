package org.siouan.frontendgradleplugin.tasks;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.siouan.frontendgradleplugin.FrontendExtension;
import org.siouan.frontendgradleplugin.core.ExecutableNotFoundException;

/**
 * Task provided as a type to let developers implement custom task based on it. The task does not expose Node/Yarn
 * related options to avoid duplicating the plugin configuration. Using this task as a type to register a custom task
 * requires only to define the 'script' attribute, and to make the custom task depend on the 'installFrontend' task.
 * <p>
 * A typical usage of this task in a 'build.gradle' file would be:
 * <pre>
 * tasks.register('mytask', org.siouan.frontendgradleplugin.tasks.RunScriptTask) {
 *     dependsOn tasks.named('installFrontend')
 *     script = 'myscript'
 * }
 * </pre>
 */
public class RunScriptTask extends AbstractRunScriptTask {

    @Input
    public Property<String> getScript() {
        return script;
    }

    @Override
    public void execute() throws ExecutableNotFoundException {
        final FrontendExtension extension = getProject().getExtensions().findByType(FrontendExtension.class);
        yarnEnabled.set(extension.getYarnEnabled());
        nodeInstallDirectory.set(extension.getNodeInstallDirectory());
        yarnInstallDirectory.set(extension.getYarnInstallDirectory());
        super.execute();
    }
}
