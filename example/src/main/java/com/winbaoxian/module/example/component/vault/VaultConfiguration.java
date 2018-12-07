package com.winbaoxian.module.example.component.vault;

import com.winbaoxian.vault.VaultConfig;
import com.winbaoxian.vault.VaultTools;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VaultConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "winbaoxian.vault")
    public VaultConfig vaultConfig() {
        return new VaultConfig();
    }

    @Bean
    public VaultTools vaultTools() throws Exception {
        return new VaultTools(vaultConfig());
    }

}
