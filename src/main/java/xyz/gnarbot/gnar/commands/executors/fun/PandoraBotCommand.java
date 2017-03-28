package xyz.gnarbot.gnar.commands.executors.fun;

import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;
import org.apache.commons.lang3.StringUtils;
import xyz.gnarbot.gnar.Constants;
import xyz.gnarbot.gnar.commands.handlers.Command;
import xyz.gnarbot.gnar.commands.handlers.CommandExecutor;
import xyz.gnarbot.gnar.utils.Note;

import java.util.List;

@Command(aliases = "pbot")
public class PandoraBotCommand extends CommandExecutor {
    private static final ChatterBotFactory factory = new ChatterBotFactory();
    private ChatterBot bot = null;

    private ChatterBotSession session = null;

    @Override
    public void execute(Note note, List<String> args) {
        try {
            if (bot == null) {
                bot = factory.create(ChatterBotType.PANDORABOTS, "b0dafd24ee35a477");
                session = bot.createSession();
                note.respond().info("Pandora-Bot session created for the server.").queue();
            }

            String input = StringUtils.join(args, " ");

            String output = session.think(input);
            note.respond().embed("PandoraBot")
                    .setColor(Constants.COLOR)
                    .setDescription(output)
                    .rest().queue();

        } catch (Exception e) {
            note.respond().error("PandoraBot has encountered an exception. Resetting PandoraBot.").queue();
            bot = null;
        }
    }

}
