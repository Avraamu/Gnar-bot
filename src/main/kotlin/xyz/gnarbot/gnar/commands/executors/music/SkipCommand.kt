package xyz.gnarbot.gnar.commands.executors.music

import xyz.gnarbot.gnar.Bot
import xyz.gnarbot.gnar.commands.Category
import xyz.gnarbot.gnar.commands.Command
import xyz.gnarbot.gnar.commands.CommandExecutor
import xyz.gnarbot.gnar.commands.Scope
import xyz.gnarbot.gnar.utils.Context
import xyz.gnarbot.gnar.utils.TrackContext

@Command(
        aliases = arrayOf("skip"),
        description = "Skip the current music track if you're the requester.",
        category = Category.MUSIC,
        scope = Scope.VOICE
)
class SkipCommand : CommandExecutor() {
    override fun execute(context: Context, args: Array<String>) {
        val manager = Bot.getPlayers().getExisting(context.guild)
        if (manager == null) {
            context.send().error("There's no music player in this guild.\n" +
                    "\uD83C\uDFB6` _play (song/url)` to start playing some music!").queue()
            return
        }

        if (manager.player.playingTrack.getUserData(TrackContext::class.java).requester != context.member) {
            context.send().error("You did not request this track.").queue()
            return
        }

        manager.scheduler.nextTrack()

        context.send().embed("Skip Current Track") {
            setColor(Bot.CONFIG.musicColor)
            setDescription("The track was skipped.")
        }.action().queue()
    }
}
