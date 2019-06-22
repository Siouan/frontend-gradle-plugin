package org.siouan.frontendgradleplugin.core;

import java.io.File;

import org.gradle.api.Task;

/**
 * This abstract class provides the reusable logic to run a NPM/Yarn script.
 */
public class RunScriptJob extends AbstractTaskJob {

    /**
     * Executor use to run the script.
     */
    private final Executor executor;

    /**
     * Directory where the Node distribution is installed.
     */
    private final File nodeInstallDirectory;

    /**
     * Directory where the Yarn distribution is installed.
     */
    private final File yarnInstallDirectory;

    /**
     * The script run by the job with NPM or Yarn.
     */
    private final String script;

    /**
     * O/S name.
     */
    private final String osName;

    /**
     * Builds a job to run a script.
     *
     * @param task Parent task.
     * @param executor Executor to use to run the script.
     * @param nodeInstallDirectory Node install directory.
     * @param yarnInstallDirectory Yarn install directory.
     * @param script The script run by the job.
     * @param osName O/S name.
     */
    public RunScriptJob(final Task task, final Executor executor, final File nodeInstallDirectory,
        final File yarnInstallDirectory, final String script, final String osName) {
        super(task);
        this.executor = executor;
        this.nodeInstallDirectory = nodeInstallDirectory;
        this.yarnInstallDirectory = yarnInstallDirectory;
        this.script = script;
        this.osName = osName;
    }

    public void run() throws ExecutableNotFoundException {
        task.getProject()
            .exec(new ExecSpecAction(executor, nodeInstallDirectory, yarnInstallDirectory, osName, script, execSpec -> {
                logDebug(execSpec.getEnvironment().toString());
                logLifecycle(
                    "Running '" + execSpec.getExecutable() + ' ' + String.join(" ", execSpec.getArgs()) + '\'');
            })).rethrowFailure().assertNormalExitValue();
    }
}
