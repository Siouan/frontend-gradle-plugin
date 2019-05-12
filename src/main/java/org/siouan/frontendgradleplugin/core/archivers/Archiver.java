package org.siouan.frontendgradleplugin.core.archivers;

import org.siouan.frontendgradleplugin.core.ExplodeSettings;

/**
 * Interface of a component capable to process archives.
 *
 * @since 1.1.3
 */
public interface Archiver {

    /**
     * Explodes an archive into a target directory.
     *
     * @param settings Explode settings.
     * @throws SlipAttackException If a slip attack is detected.
     * @throws UnsupportedEntryException If the archive contains an unsupported entry (not a symbolic link, a regular
     * file or a directory).
     * @throws ArchiverException If the extraction fails.
     */
    void explode(ExplodeSettings settings) throws SlipAttackException, UnsupportedEntryException, ArchiverException;
}
