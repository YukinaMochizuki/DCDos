package tw.yukina.dcdos.manager.telegram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import tw.yukina.dcdos.entity.account.User;
import tw.yukina.dcdos.entity.account.UserCache;
import tw.yukina.dcdos.repository.UserCacheRepository;
import tw.yukina.dcdos.repository.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class TelegramUserInfoManager {

    Set<Update> updates = new HashSet<>();

    @Autowired
    private UserCacheRepository userCacheRepository;

    @Autowired
    private UserRepository userRepository;

    public void input(Update update){
        Integer userId = update.getMessage().getFrom().getId();
        User user = userRepository.findByTelegramUserId(userId);
        if(user == null)return;

        Optional<Update> findUpdate = updates.stream().filter(update1 -> userId.equals(update1.getMessage().getFrom().getId()))
                .findAny();
        findUpdate.ifPresent(value -> updates.remove(value));
        updates.add(update);

        if(userCacheRepository.findByUser(user) == null) updateCache(update, user);
    }

    public void updateCache(User user){
        Integer userId = user.getGitLabUserId();

        Optional<Update> findUpdate = updates.stream()
                .filter(update1 -> userId.equals(update1.getMessage().getFrom().getId()))
                .findAny();

        findUpdate.ifPresent(value -> updateCache(value, user));
    }

    public void updateCache(Update update, User user){
        String firstName = update.getMessage().getFrom().getFirstName();
        String lastName = update.getMessage().getFrom().getLastName();

        StringBuilder stringBuilder = new StringBuilder();
        if(firstName != null) stringBuilder.append(firstName);
        if(lastName != null)stringBuilder.append(" ").append(lastName);

        UserCache.UserCacheBuilder userCacheBuilder = UserCache.builder()
                .user(user)
                .telegramUserName(update.getMessage().getFrom().getUserName())
                .displayName(stringBuilder.toString());

        userCacheRepository.save(userCacheBuilder.build());
    }
}