package jp.aibax.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import jp.aibax.data.domain.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
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
        List<User> users = userRepository.findAll();
        assertTrue(users.size() > 0);

        users.forEach(System.out::println);
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
        Optional<User> admin = userRepository.findByLoginId("admin");
        assertTrue(admin.isPresent());
        System.out.println(admin.get());

        Optional<User> manager = userRepository.findByLoginId("manager");
        assertTrue(manager.isPresent());
        System.out.println(manager.get());

        Optional<User> developer = userRepository.findByLoginId("developer");
        assertTrue(developer.isPresent());
        System.out.println(developer.get());

        Optional<User> guest = userRepository.findByLoginId("guest");
        assertTrue(guest.isPresent());
        System.out.println(guest.get());
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
            Optional<User> user1 = userRepository.findById(userId);
            assertTrue(user1.isPresent());
            user1.ifPresent(u -> {
                assertEquals(u.getLoginId(), "user");
                assertEquals(u.getName(), "New User");
            });

            /* By Login ID */
            Optional<User> user2 = userRepository.findByLoginId("user");
            assertTrue(user2.isPresent());
            user2.ifPresent(u -> {
                assertEquals(u.getLoginId(), "user");
                assertEquals(u.getName(), "New User");
            });
        }

        /* Delete */
        if (userId != null)
        {
            /* Before */
            assertEquals(userRepository.count(), 5);

            Optional<User> user = userRepository.findById(userId);
            assertTrue(user.isPresent());

            /* Do */
            user.ifPresent(u -> userRepository.delete(u));

            /* After */
            assertEquals(userRepository.count(), 4);

            Optional<User> deletedUser = userRepository.findById(userId);
            assertFalse(deletedUser.isPresent());
        }
    }
}
