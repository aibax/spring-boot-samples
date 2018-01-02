package jp.aibax.data.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import jp.aibax.data.domain.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest
{
    @Autowired
    UserRepository userRepository;

    @Test
    public void testAutowire()
    {
        assertNotNull(userRepository);
    }

    @Test
    public void testFindAll()
    {
        List<User> beacons = userRepository.findAll();
        assertTrue(beacons.size() > 0);

        beacons.forEach(System.out::println);
    }

    @Test
    public void testCount()
    {
        long count = userRepository.count();
        assertEquals(count, 4);
    }

    @Test
    public void testFindById()
    {
        User admin = userRepository.findByLoginId("admin");
        assertNotNull(admin);
        System.out.println(admin);

        User manager = userRepository.findByLoginId("manager");
        assertNotNull(manager);
        System.out.println(manager);

        User developer = userRepository.findByLoginId("developer");
        assertNotNull(developer);
        System.out.println(developer);

        User guest = userRepository.findByLoginId("guest");
        assertNotNull(guest);
        System.out.println(guest);
    }

    @Test
    public void testSave()
    {
        Integer userId = null;

        /* Save */
        {
            /* Before */
            assertEquals(userRepository.count(), 4);

            /* Do */
            User user = new User();
            user.setLoginId("user");
            user.setName("New User");
            user.setPassword("dummy");

            user = userRepository.save(user);
            assertNotNull(user);
            assertNotNull(user.getId());

            /* After */
            assertEquals(userRepository.count(), 5);

            userId = user.getId();
        }

        /* Find */
        if (userId != null)
        {
            /* By ID */
            User user1 = userRepository.findOne(userId);
            assertNotNull(user1);
            assertEquals(user1.getLoginId(), "user");
            assertEquals(user1.getName(), "New User");

            /* By Login ID */
            User user2 = userRepository.findByLoginId("user");
            assertNotNull(user2);
            assertEquals(user2.getLoginId(), "user");
            assertEquals(user2.getName(), "New User");
        }

        /* Delete */
        if (userId != null)
        {
            /* Before */
            assertEquals(userRepository.count(), 5);

            User user = userRepository.findOne(userId);
            assertNotNull(user);

            /* Do */
            userRepository.delete(user);

            /* After */
            assertEquals(userRepository.count(), 4);

            User deletedUser = userRepository.findOne(userId);
            assertNull(deletedUser);
        }
    }
}
