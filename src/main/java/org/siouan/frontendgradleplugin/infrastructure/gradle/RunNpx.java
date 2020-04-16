package org.siouan.frontendgradleplugin.infrastructure.gradle;

import java.util.Objects;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.siouan.frontendgradleplugin.domain.exception.ExecutableNotFoundException;
import org.siouan.frontendgradleplugin.domain.model.ExecutableType;
import org.siouan.frontendgradleplugin.infrastructure.BeanInstanciationException;
import org.siouan.frontendgradleplugin.infrastructure.TooManyCandidateBeansException;
import org.siouan.frontendgradleplugin.infrastructure.ZeroOrMultiplePublicConstructorsException;

/**
 * Task type allowing developers to implement custom task and run a {@code npx} command. To do so, the {@code script}
 * property must be defined, and custom task shall depend either on the {@code installNode} task or on the {@code
 * installFrontend} task, depending on the user need. Running a custom task with this type fails if Yarn is enabled.
 * <p>
 * A typical usage of this task in a 'build.gradle' file would be:
 * <pre>
 * import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNpx
 * tasks.register('mytask', RunNpx) {
 *     dependsOn tasks.named('installFrontend')
 *     script = 'myscript'
 * }
 * </pre>
 *
 * @since 1.2.0
 */
public class RunNpx extends AbstractRunScriptTask {

    public RunNpx() {
        super();
        final FrontendExtension extension = Objects.requireNonNull(
            getProject().getExtensions().findByType(FrontendExtension.class));
        packageJsonDirectory.set(extension.getPackageJsonDirectory());
        nodeInstallDirectory.set(extension.getNodeInstallDirectory());
        yarnEnabled.set(extension.getYarnEnabled());
    }

    @Input
    public Property<String> getScript() {
        return script;
    }

    @Override
    protected ExecutableType getExecutableType() {
        return ExecutableType.NPX;
    }

    @Override
    public void execute()
        throws TooManyCandidateBeansException, BeanInstanciationException, ExecutableNotFoundException,
        ZeroOrMultiplePublicConstructorsException {
        if (yarnEnabled.get()) {
            throw new UnsupportedOperationException("Running a NPX command is not supported when Yarn is enabled"
                + " to avoid mixing dependencies from both package managers. An equivalent command with Yarn may be"
                + " 'yarn create', runnable with the '" + RunNpmYarn.class.getSimpleName() + "' task type.");
        }

        super.execute();
    }
}
