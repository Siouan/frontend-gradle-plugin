package org.siouan.frontendgradleplugin.domain.usecase;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.exception.InvalidNodeDistributionException;
import org.siouan.frontendgradleplugin.domain.exception.NodeDistributionShasumNotFoundException;
import org.siouan.frontendgradleplugin.domain.exception.ResourceDownloadException;
import org.siouan.frontendgradleplugin.domain.model.DistributionValidator;
import org.siouan.frontendgradleplugin.domain.model.DistributionValidatorSettings;
import org.siouan.frontendgradleplugin.domain.model.DownloadSettings;
import org.siouan.frontendgradleplugin.domain.model.Logger;
import org.siouan.frontendgradleplugin.domain.provider.FileManager;

/**
 * Validates a Node distribution by comparing its SHA-256 hash against the one officially published.
 */
public class ValidateNodeDistribution implements DistributionValidator {

    public static final String SHASUMS_FILENAME = "SHASUMS256.txt";

    private final FileManager fileManager;

    private final BuildTemporaryFileName buildTemporaryFileName;

    private final DownloadResource downloadResource;

    private final ReadNodeDistributionShasum readNodeDistributionShasum;

    private final HashFile hashFile;

    private final Logger logger;

    public ValidateNodeDistribution(final FileManager fileManager, final BuildTemporaryFileName buildTemporaryFileName,
        final DownloadResource downloadResource, final ReadNodeDistributionShasum readNodeDistributionShasum,
        final HashFile hashFile, final Logger logger) {
        this.fileManager = fileManager;
        this.buildTemporaryFileName = buildTemporaryFileName;
        this.downloadResource = downloadResource;
        this.readNodeDistributionShasum = readNodeDistributionShasum;
        this.hashFile = hashFile;
        this.logger = logger;
    }

    /**
     * {@inheritDoc} This implementation downloads the Node official file providing shasums - one for each supported
     * platform, resolves the expected shasum matching the distribution file name, and verifies the actual shasum of the
     * distribution file matches this expected shasum.
     */
    @Override
    public void execute(@Nonnull final DistributionValidatorSettings distributionValidatorSettings)
        throws InvalidNodeDistributionException, IOException, NodeDistributionShasumNotFoundException,
        ResourceDownloadException {
        // Resolve the URL to download the shasum file
        final Path shasumsFilePath = distributionValidatorSettings
            .getTemporaryDirectoryPath()
            .resolve(SHASUMS_FILENAME);
        final String expectedShasum;
        try {
            final URL shasumsFileUrl = new URL(distributionValidatorSettings.getDistributionUrl(), SHASUMS_FILENAME);

            // Download the shasum file
            logger.debug("Downloading shasums at '{}'", shasumsFileUrl);
            final Path temporaryFilePath = distributionValidatorSettings
                .getTemporaryDirectoryPath()
                .resolve(buildTemporaryFileName.execute(shasumsFilePath.getFileName().toString()));
            downloadResource.execute(
                new DownloadSettings(shasumsFileUrl, distributionValidatorSettings.getDistributionServerCredentials(),
                    distributionValidatorSettings.getProxySettings(), temporaryFilePath, shasumsFilePath));

            // Verify the distribution integrity
            logger.info("Verifying distribution integrity");
            final String distributionFileName = distributionValidatorSettings
                .getDistributionFilePath()
                .getFileName()
                .toString();
            expectedShasum = readNodeDistributionShasum
                .execute(shasumsFilePath, distributionFileName)
                .orElseThrow(() -> new NodeDistributionShasumNotFoundException(distributionFileName));
        } finally {
            fileManager.deleteIfExists(shasumsFilePath);
        }

        if (!hashFile.execute(distributionValidatorSettings.getDistributionFilePath()).equals(expectedShasum)) {
            throw new InvalidNodeDistributionException();
        }
    }
}
