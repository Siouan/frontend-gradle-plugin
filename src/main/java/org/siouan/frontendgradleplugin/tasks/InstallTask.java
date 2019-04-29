package org.siouan.frontendgradleplugin.tasks;

/**
 * This task installs frontend environment (by executing {@code npm/yarn install}).
 */
public class InstallTask extends AbstractPredefinedRunScriptTask {

    public static final String INSTALL_SCRIPT = "install";

    public InstallTask() {
        super();
        script.set(INSTALL_SCRIPT);
    }
}