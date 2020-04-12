package org.siouan.frontendgradleplugin.infrastructure.gradle;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;

/**
 * This task installs frontend environment (by executing a {@code npm/yarn} command). Optionally, the command may be
 * customized to pass other parameter (e.g. {@code npm ci} command).
 */
public class InstallTask extends AbstractPredefinedRunScriptTask {

    @Input
    @Optional
    public Property<String> getInstallScript() {
        return script;
    }
}
