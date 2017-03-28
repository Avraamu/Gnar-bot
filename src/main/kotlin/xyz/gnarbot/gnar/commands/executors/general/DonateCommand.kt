package xyz.gnarbot.gnar.commands.executors.general

import xyz.gnarbot.gnar.Constants
import xyz.gnarbot.gnar.commands.handlers.Command
import xyz.gnarbot.gnar.commands.handlers.CommandExecutor
import xyz.gnarbot.gnar.utils.Note

@Command(aliases = arrayOf("donate"), description = "Show the getBot's uptime.")
class DonateCommand : CommandExecutor() {
    override fun execute(note: Note, args: List<String>) {
        note.respond().embed("Donations") {
            color = Constants.COLOR
            description = "Want to donate to support Gnar? __**[Click here to donate.](https://gnarbot.xyz/donate)**__"
        }.rest().queue()
    }
}
