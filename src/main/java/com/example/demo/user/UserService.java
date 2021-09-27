package com.example.demo.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
/**
 * Класс работы с БД пользователей.
 */
@Transactional
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    /**
     * Репозиторий пользователей.
     */
    private final UserRepository userRepository;
    /**
     * Кодировщик паролей.
     */
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    /**
     * Метод загрузки пользователя по имени.
     * @param s Имя пользователя.
     * @return Вернуть объект пользователя.
     * @throws UsernameNotFoundException Класс, проверяющий подлинность при помощи имени пользователя.
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User u = userRepository.findByName(s);
        return u;
    }
    /**
     * Метод нового сохранения пользователя.
     * @param name Имя пользователя.
     * @param pass Пароль пользователя.
     * @param email Почта пользователя.
     * @param type Тип пользователя.
     */
    public void create(String name, String pass, String email, String type) {
        User u = new User();
        u.setName(name);
        u.setPassword(bCryptPasswordEncoder.encode(pass));
        u.setEmail(email);
        u.setType(type);
        userRepository.save(u);
    }
    /**
     * Метод сохранения изменений данных о пользователе.
     * @param user Объект пользователя.
     */
    public void saveChanges(User user) {
        userRepository.save(user);
    }
    /**
     * Метод удаления пользователя по имени.
     * @param Name Имя пользователя.
     */
    public void delete(String Name) {
        userRepository.deleteByName(Name);
    }
    /**
     * Метод поиска пользователя по имени.
     * @param name Имя пользователя.
     * @return Вернуть искомого пользователя.
     */
    public User findByName(String name) {
        return userRepository.findByName(name);
    }
    /**
     * Метод вывода всех пользователей.
     * @return Вернуть всех пользователей.
     */
    public List<User> readAll() {
        return userRepository.findAll();
    }
}