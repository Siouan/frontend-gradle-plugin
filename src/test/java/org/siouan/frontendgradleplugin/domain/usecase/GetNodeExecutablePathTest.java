package org.siouan.frontendgradleplugin.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.provider.FileManager;

@ExtendWith(MockitoExtension.class)
class GetNodeExecutablePathTest {

    @Mock
    private FileManager fileManager;

    @InjectMocks
    private GetNodeExecutablePath usecase;

    @Test
    void shouldReturnTwoExecutableWhenOsIsWindows() {
        assertThat(usecase.getWindowsRelativeExecutablePath()).isEqualTo(Paths.get("node.exe"));

        verifyNoMoreInteractions(fileManager);
    }

    @Test
    void shouldReturnTwoExecutableWhenOsIsNotWindows() {
        assertThat(usecase.getNonWindowsRelativeExecutablePath()).isEqualTo(Paths.get("bin", "node"));

        verifyNoMoreInteractions(fileManager);
    }
}
