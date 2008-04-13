package info.manandbytes.architecturerules.mojo;

import com.seventytwomiles.architecturerules.configuration.ConfigurationFactory;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;

public class ArchitectureRulesMojoTest extends AbstractMojoTestCase {

    public void testAssert() throws Exception {

        String pom = "src/test/resources/simple/pom.xml";

        ArchitectureRulesMojo
                mojo = (ArchitectureRulesMojo) lookupMojo("assert", pom);

        assertNotNull(mojo);

        assertEquals(ConfigurationFactory.DEFAULT_CONFIGURATION_FILE_NAME,
                mojo.getConfigurationFileName());
    }
}
