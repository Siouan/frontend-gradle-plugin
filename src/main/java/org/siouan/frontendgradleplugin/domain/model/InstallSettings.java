package org.siouan.frontendgradleplugin.domain.model;

import java.net.Proxy;
import java.net.URL;
import java.nio.file.Path;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Settings to install a distribution.
 *
 * @since 1.1.2
 */
public class InstallSettings {

    private final Platform platform;

    private final String version;

    private final URL downloadUrl;

    private final String downloadUrlPattern;

    private final Proxy proxy;

    private final String authorizationHeader;

    private final Path temporaryDirectoryPath;

    private final Path installDirectoryPath;

    /**
     * Builds an installer.
     *
     * @param platform Underlying platform.
     * @param version Version of the distribution.
     * @param downloadUrl URL to download the distribution.
     * @param downloadUrlPattern URL pattern to download the distribution.
     * @param proxy Proxy used for downloads.
     * @param temporaryDirectoryPath Path to a temporary directory.
     * @param installDirectoryPath Path to a directory where the distribution shall be installed.
     * @param authorizationHeader optional authorization header to send with the request.
     */
    public InstallSettings(@Nonnull final Platform platform, @Nonnull final String version,
        @Nullable final URL downloadUrl, @Nullable final String downloadUrlPattern, @Nullable final Proxy proxy,
        @Nullable final String authorizationHeader, @Nonnull final Path temporaryDirectoryPath,
        @Nonnull final Path installDirectoryPath) {
        this.platform = platform;
        this.version = version;
        this.downloadUrl = downloadUrl;
        this.downloadUrlPattern = downloadUrlPattern;
        this.proxy = proxy;
        this.authorizationHeader = authorizationHeader;
        this.temporaryDirectoryPath = temporaryDirectoryPath;
        this.installDirectoryPath = installDirectoryPath;
    }

    /**
     * Gets the underlying platform.
     *
     * @return Platform.
     */
    @Nonnull
    public Platform getPlatform() {
        return platform;
    }

    /**
     * Gets the version of the distribution.
     *
     * @return Version.
     */
    @Nonnull
    public String getVersion() {
        return version;
    }

    /**
     * Gets the URL to download the distribution.
     *
     * @return URL.
     */
    @Nullable
    public URL getDownloadUrl() {
        return downloadUrl;
    }

    /**
     * Gets the URL pattern to download the distribution.
     *
     * @return URL pattern.
     */
    @Nullable
    public String getDownloadUrlPattern() {
        return downloadUrlPattern;
    }

    /**
     * Gets the proxy used for the connection.
     *
     * @return Proxy.
     */
    @Nonnull
    public Proxy getProxy() {
        return proxy;
    }

    /**
     * Gets the optional authorization header to send with the request.
     *
     * @return authorization header.
     */
    @Nullable
    public String getAuthorizationHeader() {
        return authorizationHeader;
    }

    /**
     * Gets the path to a temporary directory.
     *
     * @return Path.
     */
    @Nonnull
    public Path getTemporaryDirectoryPath() {
        return temporaryDirectoryPath;
    }

    /**
     * Gets the path to a directory where the distribution shall be installed.
     *
     * @return Path.
     */
    @Nonnull
    public Path getInstallDirectoryPath() {
        return installDirectoryPath;
    }
}
