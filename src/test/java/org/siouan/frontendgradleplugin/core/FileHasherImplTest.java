package org.siouan.frontendgradleplugin.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Unit tests for the {@link FileHasherImpl} class.
 */
class FileHasherImplTest {

    private static final String DATA = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.";

    private static final String DATA_SHA256_HASH = "a58dd8680234c1f8cc2ef2b325a43733605a7f16f288e072de8eae81fd8d6433";

    @TempDir
    File temporaryDirectory;

    @Test
    void shouldFailWhenFileCannotBeRead() {
        assertThatThrownBy(() -> new FileHasherImpl().hash(Paths.get("/dummy"))).isInstanceOf(IOException.class);
    }

    @Test
    void shouldReturnValidSha256HashWithDefaultBufferingCapacity() throws IOException, NoSuchAlgorithmException {
        final Path temporaryFile = Files
            .write(Paths.get(temporaryDirectory.getAbsolutePath(), "file-for-hashing.txt"), DATA.getBytes());
        final String hash = new FileHasherImpl().hash(temporaryFile);
        assertThat(hash).isEqualTo(DATA_SHA256_HASH);
    }
}
