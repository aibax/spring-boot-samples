package jp.aibax.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit4.SpringRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleUserStoreTest
{
    @Autowired
    SampleUserStore sampleUserStore;

    @Test
    public void testAutowire()
    {
        assertNotNull(sampleUserStore);
    }

    @Test
    public void testFindByLoginId()
    {
        User nullUser = sampleUserStore.findByLoginId(null);
        assertNull(nullUser);

        User emptyUser = sampleUserStore.findByLoginId("");
        assertNull(emptyUser);

        User admin = sampleUserStore.findByLoginId("admin");
        assertNotNull(admin);
        assertEquals(admin.getUsername(), "admin");

        User nobody = sampleUserStore.findByLoginId("nobody");
        assertNull(nobody);
    }
}
