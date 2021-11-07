import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import javax.security.auth.login.LoginException;

public class DiscordBot extends ListenerAdapter {


    public static void main(String[] args) throws LoginException {
        String token = "";

        MessageListener messageListener = new MessageListener();

        JDABuilder.createDefault(token)
                .addEventListeners(messageListener)
                .build();
    }

}
