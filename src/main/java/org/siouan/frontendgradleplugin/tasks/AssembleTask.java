package org.siouan.frontendgradleplugin.tasks;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;

/**
 * This task assembles frontend artifacts.
 */
public class AssembleTask extends AbstractPredefinedRunScriptTask {

    public AssembleTask() {
        super(false);
    }

    @Input
    @Optional
    public Property<String> getAssembleScript() {
        return script;
    }
}
